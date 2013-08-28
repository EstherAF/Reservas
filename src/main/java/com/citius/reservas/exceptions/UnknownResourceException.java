package com.citius.reservas.exceptions;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class UnknownResourceException extends Exception {

    public UnknownResourceException() {
    }

    public UnknownResourceException(String msg) {
        super(msg);
    }
}
