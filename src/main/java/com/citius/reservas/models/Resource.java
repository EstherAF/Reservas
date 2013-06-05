/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "IS_GROUP")
@Table(name = "resources")
@NamedQueries(value = {
    @NamedQuery(name = "Resource.findById", query = "SELECT r FROM Resource r WHERE r.id = :id"),
    @NamedQuery(name = "Resource.findByName", query = "SELECT r FROM Resource r WHERE r.name = :name"),
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findByParent", query = "SELECT r FROM Resource r WHERE r.parent = :parent"),
    @NamedQuery(name = "Resource.findWithoutParent", query = "SELECT r FROM Resource r WHERE r.parent IS NULL")})
@XmlRootElement
public abstract class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    protected Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    protected String name;
    @NotNull
    @Min(0)
    protected Integer level;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", referencedColumnName = "id")
    protected Resource parent;

    public Resource() {
    }

    public Resource(String name, Resource parent) {
        this.name = name;
        this.parent = parent;
    }
    
    public Resource(Integer id, String name, Resource parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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

    public abstract Boolean isGroup();

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        s += "level:" + level + "\n";
        s += "parent:" + parent.id + "\n";

        return s;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
