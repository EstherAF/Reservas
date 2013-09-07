/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions;

import com.citius.reservas.models.ReservationInstance;
import java.util.Date;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class NotAvaliableResources {

    private String resourceName;
    private Date start;
    private Date end;
    private Integer quantityAvaliable;

    public NotAvaliableResources(String resourceName, ReservationInstance instance, Integer quantity) {
        this.resourceName = resourceName;
        this.start = instance.getStartTimeDate();
        this.end = instance.getEndTimeDate();
        this.quantityAvaliable = (quantity == null) ? 0 : quantity;
    }

    public NotAvaliableResources(String resourceName, Date start, Date end) {
        this.resourceName = resourceName;
        this.start = start;
        this.end = end;
    }

    public NotAvaliableResources(String resourceName, Date start, Date end, Integer quantityAvaliable) {
        this.resourceName = resourceName;
        this.start = start;
        this.end = end;
        this.quantityAvaliable = quantityAvaliable;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getQuantityAvaliable() {
        return quantityAvaliable;
    }

    public void setQuantityAvaliable(Integer quantityAvaliable) {
        this.quantityAvaliable = quantityAvaliable;
    }
}
