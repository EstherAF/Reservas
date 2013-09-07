package com.citius.reservas.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class NotAvaliableException extends Exception {

    private List<NotAvaliableResources> notAvaliableResources;
    
    public NotAvaliableException() {
        this.notAvaliableResources = new ArrayList<>();
    }
    
    public NotAvaliableException(List<NotAvaliableResources> notAvaliableResources) {
        this.notAvaliableResources = notAvaliableResources;
    }
    
    public void addNotAvaliableResources(NotAvaliableResources notAvaliable){
        this.notAvaliableResources.add(notAvaliable);
    }

    public List<NotAvaliableResources> getNotAvaliableResources(){
        return this.notAvaliableResources;
    }
    
    public void setNotAvaliableResources(List<NotAvaliableResources> notAvaliableResources){
        this.notAvaliableResources=notAvaliableResources;
    }    
   
}
