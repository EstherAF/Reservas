/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class NotPossibleInstancesException extends Exception {

    public NotPossibleInstancesException() {
    }

    /**
     * Constructs an instance of
     * <code>NotPosibleInstancesException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NotPossibleInstancesException(String msg) {
        super(msg);
    }
}
