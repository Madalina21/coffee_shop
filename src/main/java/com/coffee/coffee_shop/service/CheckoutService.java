package com.coffee.coffee_shop.service;

import com.coffee.coffee_shop.model.CartItem;
import com.coffee.coffee_shop.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CheckoutService {

    private final OrderService orderService;

    private final CartItemService cartItemService;

    public String placeOrder(User user) {
        List<CartItem> cartItems = cartItemService.findCartItemsByUser(user);//ia toate prod din cos coresp userului respectiv
        if (cartItems.isEmpty()) {
            return "Cart is empty.";
        }
        orderService.createOrder(user, cartItems);
        cartItemService.deleteCartByUser(user);//ii sterge prod din cos dupa crearea comenzii

        return "Order has been placed. Thank you for purchase.";
    }

}

