/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Esther
 */
@Entity
@Table(name = "resources")
@NamedQueries(value = {
    @NamedQuery(name="Resource.findById", query = "SELECT r FROM Resource r WHERE r.id = :id"),
    @NamedQuery(name="Resource.findByName", query = "SELECT r FROM Resource r WHERE r.name = :name"),
    @NamedQuery(name="Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name="Resource.findByGroup", query = "SELECT r FROM Resource r WHERE r.group.name = :group_name"),
    @NamedQuery(name="Resource.findWithoutGroup", query = "SELECT r FROM Resource r WHERE r.group IS NULL")
})
@XmlRootElement
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    
    @Size(min = 1, max = 250)
    private String description;
    
    @Min(0)
    private Integer quantity;
    
    @ManyToOne()
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private ResourceGroup group;

    public Resource() {
        super();
    }

    public Resource(Integer id, String name, ResourceGroup group) {
        this.id=id;
        this.name = name;
        this.group=group;
    }
    
    public Resource(String name, ResourceGroup group, String description, Integer quantity) {
        this.name = name;
        this.group=group;
        this.description = description;
        this.quantity = quantity;
    }
    
    public Resource(Integer id, String name, ResourceGroup group, String description, Integer quantity) {
        this.id=id;
        this.name = name;
        this.group=group;
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

    public ResourceGroup getGroup() {
        return group;
    }

    public void setGroup(ResourceGroup group) {
        this.group = group;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
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
        s += "group:" + group.getName() + "\n";
        s += "description:" + description + "\n";
        s += "quantity:" + quantity + "\n";
        return s;
    }
}
