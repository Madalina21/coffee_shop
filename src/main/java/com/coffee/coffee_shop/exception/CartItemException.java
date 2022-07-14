package com.coffee.coffee_shop.exception;

import org.springframework.core.NestedRuntimeException;

public class CartItemException extends NestedRuntimeException {
    public CartItemException(String msg) {
        super(msg);
    }
}
