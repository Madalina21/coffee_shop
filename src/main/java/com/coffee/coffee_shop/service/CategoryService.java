package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.exception.CategoryNotFound;
import com.coffee.coffee_shop.model.Category;
import com.coffee.coffee_shop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFound("Category: " + id + " not found."));
    }

    public List<Category> findAllByParent(Long id) {//afiseaza toate subcategoriile
        Category categoryParent = null;//initializeaza caterogiile superioare cu null(nu au parinti)
        if (id != 0) categoryParent = new Category(id);//creeaza o noua categorie
        return categoryRepository.findAllByParent(categoryParent);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Category updatedCategory = findById(id);

        updatedCategory.setName(category.getName());
        updatedCategory.setAlias(category.getAlias());
        updatedCategory.setParent(category.getParent());

        return categoryRepository.save(updatedCategory);
    }

    public void deleteById(long id){
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}

