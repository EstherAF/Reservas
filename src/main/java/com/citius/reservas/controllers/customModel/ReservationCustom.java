/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.customModel;

import com.citius.reservas.models.Reservation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ReservationCustom extends Reservation{

    private List<ResourceGroupCustom> groups;

    public ReservationCustom() {
        super();
        this.groups = new ArrayList<>();
    }

    public List<ResourceGroupCustom> getGroups() {
        return groups;
    }

    public void setGroups(List<ResourceGroupCustom> groups) {
        this.groups = groups;
    }
    
    @JsonIgnore
    public Reservation getParentInstance(){
        Reservation r = new Reservation(this.getName(), this.getDescription(), 
                this.getOwner(), this.getStart(), this.getEnd(), 
                this.getRepetition(), this.getResources());
        r.setId(this.getId());
        r.setInvitations(this.getInvitations());
        return r;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.groups);
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
        
        if (!Objects.equals(this.groups, other.groups)) {
            return false;
        }
        
//        if (!Objects.equals(super, (Reservation) obj)) {
//            return false;
//        }
        return true;
    }

}
