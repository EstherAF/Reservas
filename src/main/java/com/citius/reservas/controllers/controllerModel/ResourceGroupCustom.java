/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.controllerModel;

import com.citius.reservas.models.ResourceGroup;
import javax.validation.constraints.Min;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ResourceGroupCustom extends ResourceGroup{
    @Min(1)
    private Integer quantity;

    public ResourceGroupCustom() {
    }
    
    public ResourceGroupCustom(ResourceGroup resource, Integer quantity) {
        this.setId(resource.getId());
        this.setName(resource.getName());
        this.setDescription(resource.getDescription());
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
