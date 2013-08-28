/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class RestError {

    private HttpStatus status;
    private int code;
    private String message;
    private String developerMessage;
    private Throwable throwable;

    public RestError() {
        this.status = null;
        this.code = 0;
        this.message = new String();
        this.developerMessage = new String();
        this.throwable = null;
    }

    public RestError(HttpStatus status, int code) {
        if (status == null) {
            throw new NullPointerException("HttpStatus argument can't be null.");
        }

        this.status = status;
        this.code = code;
        this.message = new String();
        this.developerMessage = new String();
        this.throwable = null;
    }

    public RestError(HttpStatus status, int code, String message) {
        if (status == null) {
            throw new NullPointerException("HttpStatus argument can't be null.");
        }

        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = new String();
        this.throwable = null;
    }

    public RestError(HttpStatus status, int code, String message, String developerMessage) {
        if (status == null) {
            throw new NullPointerException("HttpStatus argument can't be null.");
        }

        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
        this.throwable = null;
    }

    public RestError(HttpStatus status, int code, String message, String developerMessage, Throwable throwable) {
        if (status == null) {
            throw new NullPointerException("HttpStatus argument can't be null.");
        }

        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
        this.throwable = throwable;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RestError) {
            RestError re = (RestError) o;
            return ObjectUtils.nullSafeEquals(getStatus(), re.getStatus())
                    && getCode() == re.getCode()
                    && ObjectUtils.nullSafeEquals(getMessage(), re.getMessage())
                    && ObjectUtils.nullSafeEquals(getDeveloperMessage(), re.getDeveloperMessage())
                    && ObjectUtils.nullSafeEquals(getThrowable(), re.getThrowable());
        }

        return false;
    }

    @Override
    public int hashCode() {
        //noinspection ThrowableResultOfMethodCallIgnored
        return ObjectUtils.nullSafeHashCode(new Object[]{
            getStatus(), getCode(), getMessage(), getDeveloperMessage(), getThrowable()
        });
    }

    public String toString() {
        //noinspection StringBufferReplaceableByString
        return new StringBuilder().append(getStatus().value())
                .append(" (").append(getStatus().getReasonPhrase()).append(" )")
                .toString();
    }
}
