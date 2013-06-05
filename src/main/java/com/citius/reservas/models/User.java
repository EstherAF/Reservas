/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author esther
 */
@Entity
@Table(name = "users")
@NamedQueries(value = {
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUniqueName", query = "SELECT u FROM User u WHERE u.uniqueName = :uniqueName"),
    @NamedQuery(name = "User.findByFullName", query = "SELECT u FROM User u WHERE u.fullName = :fullName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "unique_name")
    private String uniqueName;
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "full_name")
    private String fullName;
    // (regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @NotNull
    @Size(min = 1, max = 50)
    private String email;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
        @JoinColumn(name = "rol_id", referencedColumnName = "id")})
    private List<Role> roleList;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
//    @Basic(fetch = FetchType.LAZY)
//    private List<Reservation> reservationList;
    public User() {
        this.roleList = new ArrayList();
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String uniqueName, String fullName, String email) {
        this.id = id;
        this.uniqueName = uniqueName;
        this.fullName = fullName;
        this.email = email;
        this.roleList = new ArrayList();
    }
    
    public User(String uniqueName, String fullName, String email, List<Role> roleList) {
        this.uniqueName = uniqueName;
        this.fullName = fullName;
        this.email = email;
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role role) {
        this.roleList.add(role);
    }

    public Boolean removeRole(Role role) {
        return this.roleList.remove(role);
    }

//    public List<Reservation> getReservationList() {
//        return reservationList;
//    }
//
//    public void setReservationList(List<Reservation> reservationList) {
//        this.reservationList = reservationList;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s = new String();

        s += "Id:" + this.id + "\n";
        s += "UniqueName:" + uniqueName + "\n";
        s += "FullName:" + fullName + "\n";
        s += "Email:" + email + "\n";
        s += "RoleId:" + roleList.get(0).getId() + "\n";

        return s;

    }
}
