/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.my.jaxrs.rsvp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thangdo
 */
@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Person implements Serializable {
    private static final long serialVersionUID = -6639818335218185860L;
    @OneToMany(mappedBy = "person")
    @XmlTransient
    private List<Response> responses;
    @OneToMany(mappedBy = "owner")
    @XmlTransient
    private List<Event> ownedEvents;
    @XmlTransient
    @ManyToMany(mappedBy = "invitees")
    private List<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Person() {
        this.events = new ArrayList<>();
        this.ownedEvents = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    protected String firstName;

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    protected String lastName;

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rsvp.entity.Person[id=" + id + "]";
    }

    /**
     * @return the events
     */
    @XmlTransient
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * @return the ownedEvents
     */
    @XmlTransient
    public List<Event> getOwnedEvents() {
        return ownedEvents;
    }

    /**
     * @param ownedEvents the ownedEvents to set
     */
    public void setOwnedEvents(List<Event> ownedEvents) {
        this.ownedEvents = ownedEvents;
    }

    /**
     * @return the responses
     */
    @XmlTransient
    public List<Response> getResponses() {
        return responses;
    }

    /**
     * @param responses the responses to set
     */
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

}
