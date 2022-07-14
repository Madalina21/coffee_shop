package com.coffee.coffee_shop.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//creare exceptie custom prin mostenirea clasei NestedRuntimeException
@ResponseStatus(HttpStatus.NOT_FOUND)//pt a returna 404 in postman = not found
public class TechnicalDetailNotFound extends NestedRuntimeException {

    public TechnicalDetailNotFound(String msg) { super(msg); }//dam ca param un msj de exceptie

}