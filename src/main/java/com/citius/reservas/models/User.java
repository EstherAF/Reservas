/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
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

    private static final long serialVersionUID = 2L;
    
    @Id
    @NotNull(message = "error.form.user.uniqueName.required")
    @Size(min = 1, max = 50, message = "error.form.user.uniqueName.size")
    @Column(name = "unique_name")
    private String uniqueName;
    
    @Size(min = 1, max = 150, message = "error.form.user.fullName.size")
    @Column(name = "full_name")
    private String fullName;
    
    @Size(min = 1, max = 100,message = "error.form.user.email.size")
    private String email;
    
//    @JoinColumn(referencedColumnName = "name", table="roles")
    @Transient
    private String role;
    
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
