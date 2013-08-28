package com.citius.reservas.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esther Ã?lvarez Feijoo
 */
public class NotAvaliableException extends Exception {

    private List<NotAvaliable> notAvaliableResources;
    
    public NotAvaliableException() {
        this.notAvaliableResources = new ArrayList<>();
    }
    
    public NotAvaliableException(List<NotAvaliable> notAvaliableResources) {
        this.notAvaliableResources = notAvaliableResources;
    }
    
    public void addNotAvaliableResources(NotAvaliable notAvaliable){
        this.notAvaliableResources.add(notAvaliable);
    }

    public List<NotAvaliable> getNotAvaliableResources(){
        return this.notAvaliableResources;
    }
    
    public void setNotAvaliableResources(List<NotAvaliable> notAvaliableResources){
        this.notAvaliableResources=notAvaliableResources;
    }    
   
}
