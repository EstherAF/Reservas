/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class DataNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>DataNotFoundException</code> without detail message.
     */
    public DataNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>DataNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DataNotFoundException(String msg) {
        super(msg);
    }
}
