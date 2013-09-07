/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.controllerModel;

import com.citius.reservas.models.Resource;
import javax.validation.constraints.Min;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ResourceCustom extends Resource{
    @Min(1)
    private Integer quantity;

    public ResourceCustom(){
        super();
        this.quantity=0;
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ResourceCustom(Integer quantity, Resource toClone) {
        super(toClone);
        this.quantity = quantity;
    }
    
    

}
