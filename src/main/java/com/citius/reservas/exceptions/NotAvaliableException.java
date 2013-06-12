/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class NotAvaliableException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotAvaliableException</code> without detail message.
     */
    public NotAvaliableException() {
    }

    /**
     * Constructs an instance of
     * <code>NotAvaliableException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotAvaliableException(String msg) {
        super(msg);
    }
}
