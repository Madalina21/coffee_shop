package com.coffee.coffee_shop.controller;

import com.coffee.coffee_shop.model.User;
import com.coffee.coffee_shop.service.CheckoutService;
import com.coffee.coffee_shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {//controller folosit la plasarea comenzii

    private final CheckoutService checkoutService;

    private final UserService userService;

    @PostMapping()
    public String placeOrder(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());//identifica userul care a plasat comanda
        return checkoutService.placeOrder(user);
    }

}

