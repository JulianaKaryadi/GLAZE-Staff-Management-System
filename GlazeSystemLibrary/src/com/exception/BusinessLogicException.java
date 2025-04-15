/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.exception;

/**
 *
 * @author LENOVO
 */
public class BusinessLogicException extends Exception {
    
    public BusinessLogicException() {
        super("Business logic validation failed");
    }
    
    public BusinessLogicException(String message) {
        super(message);
    }
    
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
