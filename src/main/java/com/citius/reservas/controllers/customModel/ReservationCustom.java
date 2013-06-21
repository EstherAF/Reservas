/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.customModel;

import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Esther Álvarez Feijoo
*/
public class ReservationCustom{

    private Integer id;
    private String name;
    private String description;
    private String owner;
    private List<String> resources;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
    
    public ReservationCustom() {
    }
    
    public ReservationCustom(Reservation r) {
        this.id = r.getId();
        this.name = r.getName();
        this.description = r.getDescription();
        this.owner = r.getOwner().getUniqueName();
        this.resources = new ArrayList<>();
        for(Resource i:r.getResources()){
            this.resources.add(i.getName());
        }
    }

    public ReservationCustom(Integer id, String name, String description, String owner, List<String> resources) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.resources = resources;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.owner);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservationCustom other = (ReservationCustom) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        return true;
    }
}
