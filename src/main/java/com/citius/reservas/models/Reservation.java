/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Entity
@Table(name = "reservations")
@NamedQueries(value = {
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
    @NamedQuery(name = "Reservation.findByName", query = "SELECT r FROM Reservation r WHERE r.name = :name"),
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findByOwner", query = "SELECT r FROM Reservation r WHERE r.owner.uniqueName = :ownerUniqueName"),
    @NamedQuery(name = "Reservation.findBetweenDates", query = "SELECT r FROM Reservation r WHERE r.eventTimeDate.startDate <= :endDate OR r.eventTimeDate.endDate >= :startDate"),

})
@XmlRootElement
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Size(max = 50)
    private String name;
    
    @Size(max = 250)
    private String description;
    
    @ManyToOne()
    @JoinColumn(name = "owner",
            referencedColumnName = "unique_name")
    private User owner;
    
    @Embedded
    private EventTimeDate eventTimeDate;
    
    @Embedded
    private Repetition repetition;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "reservation",
            cascade = CascadeType.ALL)
    private List<ReservationInstance> instances;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reserved_resources",
            joinColumns = {
                @JoinColumn(name = "reservation_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "resource_id", referencedColumnName = "id")})
    private List<Resource> resources;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "reservation",
            cascade = CascadeType.ALL)
    private List<Invitation> invitations;

    public Reservation() {
        this.resources = new ArrayList<>();
    }
    
    public Reservation(String name, String description, User owner, EventTimeDate eventTimeDate) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.eventTimeDate = eventTimeDate;
    }

    public Reservation(String name, String description, User owner, EventTimeDate eventTimeDate, Repetition repetition) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.repetition = repetition;
        this.eventTimeDate = eventTimeDate;
    }

    public Reservation(Integer id, String name, String description, User owner, EventTimeDate eventTimeDate, Repetition repetition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.eventTimeDate = eventTimeDate;
        this.repetition = repetition;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public EventTimeDate getEventTimeDate() {
        return eventTimeDate;
    }

    public void setEventTimeDate(EventTimeDate eventTimeDate) {
        this.eventTimeDate = eventTimeDate;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public List<ReservationInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<ReservationInstance> instances) {
        this.instances = instances;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.eventTimeDate);
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
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.eventTimeDate, other.eventTimeDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner + ", eventTimeDate=" + eventTimeDate + ", repetition=" + repetition + '}';
    }

}
