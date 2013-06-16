/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class Ejemplo {
    private String name;
    private String description;
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Ejemplo() {
    
    }

    public Ejemplo(String name, String description, Integer quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ejemplo{" + "name=" + name + ", description=" + description + ", quantity=" + quantity + '}';
    }
    
    
}
