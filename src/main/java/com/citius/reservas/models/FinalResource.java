/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Esther
 */
@Entity
@DiscriminatorValue("0")    //No es un grupo
@Table(name = "final_resources")
@NamedQueries(value = {
    @NamedQuery(name="FinalResource.findById", query = "SELECT fr FROM FinalResource fr WHERE fr.id = :id"),
    @NamedQuery(name="FinalResource.findByName", query = "SELECT fr FROM FinalResource fr WHERE fr.name = :name"),
    @NamedQuery(name="FinalResource.findByDescription", query = "SELECT fr FROM FinalResource fr WHERE fr.description = :description"),
    @NamedQuery(name="FinalResource.findAll", query = "SELECT fr FROM FinalResource fr"),
    @NamedQuery(name="FinalResource.findByParent", query = "SELECT fr FROM FinalResource fr WHERE fr.parent = :parent"),
    @NamedQuery(name="FinalResource.findByQuantity", query = "SELECT fr FROM FinalResource fr WHERE fr.quantity = :quantity"),
    @NamedQuery(name="FinalResource.findWithoutParent", query = "SELECT fr FROM FinalResource fr WHERE fr.parent IS NULL")
})
@XmlRootElement
public class FinalResource extends Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Size(min = 1, max = 250)
    private String description;
    
    @Min(0)
    private Integer quantity;

    public FinalResource() {
        super();
    }

    public FinalResource(Integer id, String name, Resource parent) {
        super(id, name, parent);
    }

    public FinalResource(Integer id, String name, Resource parent, String description, Integer quantity) {
        super(id, name, parent);
        this.description = description;
        this.quantity = quantity;
    }
    
    public FinalResource(String name, Resource parent, String description, Integer quantity) {
        super(name, parent);
        this.description = description;
        this.quantity = quantity;
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

    @Override
    public Boolean isGroup() {
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.id != null ? super.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FinalResource)) {
            return false;
        }
        FinalResource other = (FinalResource) object;
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {

        String s = new String();
        s += "id:" + id + "\n";
        s += "name:" + name + "\n";
        s += "parent:" + parent.id + "\n";
        s += "description:" + description + "\n";
        s += "quantity:" + quantity + "\n";
        return s;
    }
}
