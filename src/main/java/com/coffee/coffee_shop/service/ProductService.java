package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.exception.ProductNotFound;
import com.coffee.coffee_shop.model.Product;
import com.coffee.coffee_shop.model.TechnicalDetail;
import com.coffee.coffee_shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product: " + id + " not found!"));
    }
    //dadea 500 daca faceam save simplu deoarece technicalDetail din produs nu avea disponibil inca product_id
    //si atunci intai salvam produsul fara techDet, dupa care facem legatura intre techDet si prod
    public Product saveProduct(Product product) {
        // Make a copy of technicalDetails
        Set<TechnicalDetail> technicalDetails = new HashSet<>(product.getTechnicalDetails());
        product.setTechnicalDetails(null);

        // Save without technicalDetails first, in order to get a productId to use in technicalDetails
        productRepository.save(product);

        // Add technicalDetails and then save product
        technicalDetails.forEach(technicalDetail -> technicalDetail.setProduct(product));
        product.setTechnicalDetails(technicalDetails);
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    public Product updateProduct(long id, Product newProduct) {
        Product productToUpdate = findById(id);
        productToUpdate.setName(newProduct.getName());
        productToUpdate.setAlias(newProduct.getAlias());
        productToUpdate.setBrand(newProduct.getBrand());
        productToUpdate.setDescription(newProduct.getDescription());
        productToUpdate.setCategory(newProduct.getCategory());
        productToUpdate.setEnabled(newProduct.isEnabled());
        productToUpdate.setPrice(newProduct.getPrice());
        productToUpdate.setStock(newProduct.getStock());
        productToUpdate.setTechnicalDetails(newProduct.getTechnicalDetails());//nou
        return productRepository.save(productToUpdate);
    }

    public List<Product> findAllByPage(int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);//sorteaza in functie de un anumit field : spre ex ID
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();//ternary

        if (pageNum < 1) pageNum = 1;
        //paginare si sortare - ne folosim de metoda findAll din interfata PagingAndSortingRepository care extinde CRUD
        //PageRequest = clasa care impelementeaza interfata Pageable cu cei 3 parametri
        //primul parametru : de la ce pag porneste(>=0), cate elem sa fie pe pag, in functie de ce id ascendent
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        //returneaza o pagina de entitati care respecta conditiile de paginare oferite de obiectul pageable
        //deci cand vrem sa facem paginare si sortare adaugam ca parametru un obiect pageable in met. de findAll
        Page<Product> pageProducts = productRepository.findAll(pageable);

        return pageProducts.getContent();
    }
}
