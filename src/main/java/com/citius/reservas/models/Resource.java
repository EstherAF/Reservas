/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Resource.findById", query = "SELECT r FROM Resource r WHERE r.id = :id"),
    @NamedQuery(name = "Resource.findByName", query = "SELECT r FROM Resource r WHERE r.name = :name"),
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findByGroup", query = "SELECT r FROM Resource r WHERE r.group.name = :group_name"),
    @NamedQuery(name = "Resource.findWithoutGroup", query = "SELECT r FROM Resource r WHERE r.group IS NULL")
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
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reserved_resources",
            joinColumns = {
        @JoinColumn(name = "resource_id", referencedColumnName = "id")},
            inverseJoinColumns = {
        @JoinColumn(name = "reservation_id", referencedColumnName = "id")})
    private List<Reservation> reservations;

    public Resource() {
        super();
    }

    public Resource(Integer id, String name, ResourceGroup group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public Resource(String name, ResourceGroup group, String description, Integer quantity) {
        this.name = name;
        this.group = group;
        this.description = description;
        this.quantity = quantity;
    }

    public Resource(Integer id, String name, ResourceGroup group, String description, Integer quantity) {
        this.id = id;
        this.name = name;
        this.group = group;
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final Resource other = (Resource) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Resource{" + "id=" + id + ", name=" + name + ", description=" + description + ", quantity=" + quantity + ", group=" + group + '}';
    }

}
