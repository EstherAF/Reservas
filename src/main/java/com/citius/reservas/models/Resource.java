/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;

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
    @NamedQuery(name = "Resource.findWithoutGroup", query = "SELECT r FROM Resource r WHERE r.group IS NULL"),
    @NamedQuery(name = "Resource.findAvaliablesBetweenDatesByResourceGroup",
            query = "SELECT rce "
            + "FROM Resource rce "
            + "WHERE (rce.group.id = :groupId) AND "
            + "rce <> ALL("
                + "SELECT resour "
                + "FROM ReservationInstance inst "
                + "JOIN inst.reservation reserv "
                + "JOIN reserv.resources resour "
                + "WHERE inst.startTimeDate < :end AND"
                + " inst.endTimeDate > :start"
            + ")")
})
public class Resource implements Serializable {

    private static final long serialVersionUID = 2L;
    @Id
    @Min(value = 1, message = "error.form.resource.id.min")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "error.form.resource.name.required")
    @Size(min = 1, max = 100, message = "error.form.resource.name.size")
    private String name;
    
    private String description;
    
    //Parent- not serialized
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private ResourceGroup group;

    public Resource() {
        super();
    }
    
    public Resource(Resource toClone) {
        this.id=toClone.id;
        this.description=toClone.description;
        this.name=toClone.name;
        this.group=toClone.group;
    }

    public Resource(Integer id, String name, ResourceGroup group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }
    
    public Resource(String name, ResourceGroup group) {
        this.name = name;
        this.group = group;
    }

    public Resource(String name, ResourceGroup group, String description) {
        this.name = name;
        this.group = group;
        this.description = description;
    }

    public Resource(Integer id, String name, ResourceGroup group, String description) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + Objects.hashCode(this.group);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.group, other.group)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Resource{" + "id=" + id + ", name=" + name + ", description=" + description + ", group=" + group + '}';
    }
}
