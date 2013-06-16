/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author esther
 */
@Entity
@Table(name = "users")
@NamedQueries(value = {
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUniqueName", query = "SELECT u FROM User u WHERE u.uniqueName = :uniqueName"),
    @NamedQuery(name = "User.findByFullName", query = "SELECT u FROM User u WHERE u.fullName = :fullName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
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
    
    @JoinColumn(referencedColumnName = "name", table="roles")
    private String role;
//
//    @OneToMany(mappedBy = "owner")
//    private List<Reservation> reservations;
    
    public User() {
    }

    public User(String uniqueName, String fullName, String email) {
        this.uniqueName = uniqueName;
        this.fullName = fullName;
        this.email = email;
    }
    
    public User(String uniqueName, String fullName, String email, String role) {
        this.uniqueName = uniqueName;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
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

//    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public void setReservations(List<Reservation> reservations) {
//        this.reservations = reservations;
//    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.uniqueName);
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
        final User other = (User) obj;
        if (!Objects.equals(this.uniqueName, other.uniqueName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "uniqueName=" + uniqueName + ", fullName=" + fullName + ", email=" + email + ", role=" + role + '}';
    }

}
