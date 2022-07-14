package com.coffee.coffee_shop.controller;

import com.coffee.coffee_shop.model.CartItem;
import com.coffee.coffee_shop.model.User;
import com.coffee.coffee_shop.service.CartItemService;
import com.coffee.coffee_shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController//permite clasei sa gestioneze cererile facute de client
@RequestMapping("/cart")//localhost:8080/cart//mapare cereri HTTP
public class CartItemController {

    private final CartItemService cartItemService;

    private final UserService userService;
    //daca pe /cart vine o solicitare de get atunci se executa viewCart
    @GetMapping()//gasesc userul in fct de email => gasesc cartu in fct de user//afisez cartul pt un anumit user
    public List<CartItem> viewCart(Principal principal) {//principal - clasa din spring security: preiau datele
        User user = userService.findUserByEmail(principal.getName());
        return cartItemService.findCartItemsByUser(user);
    }
    //daca vine solicitare post pe url
    @PostMapping("/add/{productId}/{quantity}")//endpoint adaugare prod si cantitate in cart
    public String addProductToCart(@PathVariable long productId, @PathVariable short quantity,
                                   Principal principal) {//iau idprod si cantitate din URL
        User user = userService.findUserByEmail(principal.getName());//gasesc useru in fct de email
        return cartItemService.addProductToCart(user, productId, quantity);//apelez met din service de adaugare
    }

    @PutMapping("/update/{productId}/{quantity}")//endpoint updatez prod din cart in fct de cantitate
    public String updateProductQuantity(@PathVariable long productId, @PathVariable short quantity,
                                        Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return cartItemService.updateProductQuantity(user, productId, quantity);
    }

    @DeleteMapping("/remove/{productId}")//endpoint pt stergere 1 prod din cart
    public String removeProductFromCart(@PathVariable long productId,
                                        Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return cartItemService.removeProductFromCart(user, productId);
    }

    @DeleteMapping()//endpoint stergere tot cartu
    public String deleteCart(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return cartItemService.deleteCartByUser(user);
    }

}