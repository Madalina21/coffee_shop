package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.exception.CartItemException;
import com.coffee.coffee_shop.model.CartItem;
import com.coffee.coffee_shop.model.Product;
import com.coffee.coffee_shop.model.User;
import com.coffee.coffee_shop.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional//pt ca am folosit @modifying in service pt operatia de update cartitem
public class CartItemService {

    public static final int MAXIMUM_QUANTITY_ALLOWED = 15;

    private final CartItemRepository cartItemRepository;

    private final ProductService productService;

    public List<CartItem> findCartItemsByUser(User user) {//dc user si nu userid
        return cartItemRepository.findCartItemsByUserId(user.getId());
    }

    public String addProductToCart(User user, long productId, short quantity) {
        if(quantity > MAXIMUM_QUANTITY_ALLOWED) {
            throw new CartItemException("Could not add " + quantity + " items " +
                    "to your shopping cart. Maximum allowed quantity is 15.");
        }

        short updatedQuantity = quantity;//daca cantitatea <15 updateaza cu cea curenta
        Product product = productService.findById(productId);
        CartItem cartItem = cartItemRepository.findCartItemByUserIdAndProductId(user.getId(), productId);

        if (cartItem != null) { // daca cartItemu nu e null, adica daca prod exista deja in cos
            updatedQuantity = (short) (cartItem.getQuantity() + quantity);//permite sa adaugam cantitate mai mare//mai multe prod de//acelasi tip
            if (updatedQuantity > MAXIMUM_QUANTITY_ALLOWED) {//daca era cantitatea ok dar acum dupa adaugare s-a depasit din nou
                throw new CartItemException("Could not add more " + quantity + " item(s) " +
                        " because there's already " + cartItem.getQuantity() + " item(s) " +
                        "in your shopping cart. Maximum allowed quantity is 15.");
            }
        } else {//daca nu mai avem produsul respectiv
            cartItem = new CartItem();//cream o noua linie pt noul prod
            cartItem.setUser(user);//si ii setam useru si productu
            cartItem.setProduct(product);
        }

        cartItem.setQuantity(updatedQuantity);//seteaza noua cantitate

        cartItemRepository.save(cartItem);//salveaza in db

        return "Product " + product.getName() + " x" + updatedQuantity + " has been added to the cart.";//afiseaza noul mesaj
    }

    public String updateProductQuantity(User user, long productId, short quantity) {
        CartItem cartItem = cartItemRepository.findCartItemByUserIdAndProductId(user.getId(), productId);//am gasit cosu pt useru respectiv(doar 1 prod)
        if (cartItem == null) {
            return "The product is not in the cart.";
        }
        if (quantity > 15) {
            throw new CartItemException("Could not update quantity to " + quantity +
                    ". Maximum allowed quantity is 15.");
        }
        cartItemRepository.updateProductQuantity(quantity, user.getId(), productId);
        return "Quantity has been updated! New quantity: " + quantity;
    }

    public String removeProductFromCart(User user, long productId) {//sterge o linie
        if (cartItemRepository.findCartItemByUserIdAndProductId(user.getId(), productId) == null) {
            return "The product is not in the cart.";
        }
        cartItemRepository.deleteCartItemByUserIdAndProductId(user.getId(), productId);
        return "The product has been removed from your shopping cart.";
    }

    public String deleteCartByUser(User user) {
        if (findCartItemsByUser(user).isEmpty()) {
            return "Cart is already empty";
        }
        cartItemRepository.deleteCartItemsByUserId(user.getId());
        return "Cart has been deleted!";
    }
}