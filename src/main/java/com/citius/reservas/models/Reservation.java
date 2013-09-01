/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;

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
    @NamedQuery(name = "Reservation.findBetweenDates", query = "SELECT r FROM Reservation r WHERE r.start <= :endDate OR r.end >= :startDate"),

})
@XmlRootElement
public class Reservation implements Serializable {

    private static final long serialVersionUID = 2L;
    @Id
    @Min(value = 1, message = "error.form.reservation.id.min")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "error.form.reservation.name.empty")
    @Size(min=1, max = 50, message = "error.form.reservation.name.size")
    private String name;
    
    private String description;
    
    @ManyToOne()
    @JoinColumn(name = "owner",
            referencedColumnName = "unique_name")
    private User owner;
    
    @Future(message = "error.form.reservation.start.after")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="start_time_date")
    private Date start;
    
    @Future(message = "error.form.reservation.end.after")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="end_time_date")
    private Date end;
    
    @Transient
    @JsonIgnore
    private Long duration;
    
    @Valid
    @Embedded
    private Repetition repetition;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "reservation",
            cascade = CascadeType.ALL)
    @Size(min=1)
    private Set<ReservationInstance> instances;
    
    //,cascade = CascadeType.MERGE
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "reserved_resources",
            joinColumns = {
                @JoinColumn(name = "reservation_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "resource_id")})
    private Set<Resource> resources;

    @Valid
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "reservation",
            cascade = CascadeType.ALL)
    private Set<Invitation> invitations;

    public Reservation() {
        this.resources = new LinkedHashSet<>();
        this.invitations = new LinkedHashSet<>();
        this.owner = new User();
    }
    
    public Reservation(String name, String description, User owner, Date start, Date end) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.duration = this.end.getTime()-this.start.getTime();
    }
    
    public Reservation(String name, String description, User owner, Date start, Date end, Repetition repetition, Set<Resource> resources) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.repetition = repetition;
        this.start = start;
        this.end = end;
        this.duration = this.end.getTime()-this.start.getTime();
        this.resources=resources;
    }

    public Reservation(Integer id, String name, String description, User owner, Date start, Date end, Repetition repetition, Set<Resource> resources) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.duration = this.end.getTime()-this.start.getTime();
        this.repetition = repetition;
        this.resources=resources;
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

    //@JsonProperty
    public User getOwner() {
        return owner;
    }

    //@JsonIgnore
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getStart() {
        return start;
    }
    
    public Calendar startAsACalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        return c;
    }

    public void setStart(Date start) {
        this.start = start;
        if(this.end!=null)
            this.duration = this.end.getTime()-this.start.getTime();
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
        if(this.start!=null)
            this.duration = this.end.getTime()-this.start.getTime();
        
//        if(this.repetition== null)
            this.repetition = new Repetition();
        
        if(this.repetition.getEndDate()==null)
            this.repetition.setEndDate(this.end);
            
    }

    public Long getDuration() {
        return duration;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public Set<ReservationInstance> getInstances() {
        return instances;
    }

    public void setInstances(Set<ReservationInstance> instances) {
        this.instances = instances;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Set<Invitation> invitations) {
        this.invitations = invitations;
    }
    
    /*ADD*/
    public void addResource(Resource r){
        this.getResources().add(r);
    }
    
    public void addResources(Collection<? extends Resource> resources){
        this.getResources().addAll(resources);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.owner);
        hash = 59 * hash + Objects.hashCode(this.start);
        hash = 59 * hash + Objects.hashCode(this.end);
        hash = 59 * hash + Objects.hashCode(this.duration);
        hash = 59 * hash + Objects.hashCode(this.repetition);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(this.repetition, other.repetition)) {
            return false;
        }
        if (!Objects.equals(this.instances, other.instances)) {
            return false;
        }
        if (!Objects.equals(this.resources, other.resources)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner + ", start=" + start + ", end=" + end + ", duration=" + duration + ", repetition=" + repetition + '}';
    }

}
