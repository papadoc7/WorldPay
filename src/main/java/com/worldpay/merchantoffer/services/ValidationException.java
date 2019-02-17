package com.worldpay.merchantoffer.services;

/**
 *
 * Validation exception
 */
public class ValidationException extends Exception{
    private Object data;

    public ValidationException(String message, Object data) {
        super(message);
        this.data = data;
    }
}
