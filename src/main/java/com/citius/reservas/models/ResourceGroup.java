/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Esther
 */
@Entity
@DiscriminatorValue("1")    //Es un grupo
@Table(name = "resources")
@NamedQueries(value = {
    @NamedQuery(name="ResourceGroup.findById", query = "SELECT rg FROM ResourceGroup rg WHERE rg.id = :id"),
    @NamedQuery(name="ResourceGroup.findByName", query = "SELECT rg FROM ResourceGroup rg WHERE rg.name = :name"),
    @NamedQuery(name="ResourceGroup.findAll", query = "SELECT rg FROM ResourceGroup rg"),
    @NamedQuery(name="ResourceGroup.findByParent", query = "SELECT rg FROM ResourceGroup rg WHERE rg.parent = :parent"),
    @NamedQuery(name="ResourceGroup.findWithoutParent", query = "SELECT rg FROM ResourceGroup rg WHERE rg.parent IS NULL"),
})
@XmlRootElement
public class ResourceGroup extends Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "parent")
    private List<Resource> childs;

    public ResourceGroup() {
    }

    public ResourceGroup(Integer id, String name, Resource parent) {
        super(id, name, parent);
    }
    
    public ResourceGroup(String name, Resource parent) {
        super(name, parent);
    }
    
    public ResourceGroup(Integer id, String name, Resource parent, List<Resource> childs) {
        super(id, name, parent);
        this.childs = childs;
    }

    @XmlTransient
    public List<Resource> getChilds() {
        return childs;
    }

    public void setChilds(List<Resource> childs) {
        this.childs = childs;
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
        if (!(object instanceof ResourceGroup)) {
            return false;
        }
        ResourceGroup other = (ResourceGroup) object;
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
        if (parent != null) {
            s += "parent:" + parent.id + "\n";
        } else {
            s += "parent:" + null + "\n";
        }
        for (Resource r : childs) {
            s += "child:" + r.id + "\n";
        }
        return s;
    }

    @Override
    public Boolean isGroup() {
        return true;
    }
}
