/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.exception;

/**
 *
 * @author LENOVO
 */
public class DataValidationException extends Exception {
    
    public DataValidationException() {
        super("Data validation failed");
    }
    
    public DataValidationException(String message) {
        super(message);
    }
    
    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
