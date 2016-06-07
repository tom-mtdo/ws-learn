package org.jboss.examples.ticketmonster.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Seat seat;

    @ManyToOne
    @NotNull
    private TicketCategory ticketCategory;

    private float price;

    public Ticket() {

    }

    public Ticket(Seat seat, TicketCategory ticketCategory, float price) {
        this.seat = seat;
        this.ticketCategory = ticketCategory;
        this.price = price;
    }

    /* Boilerplate getters and setters */

    public Long getId() {
        return id;
    }

    public TicketCategory getTicketCategory() {
        return ticketCategory;
    }

    public float getPrice() {
        return price;
    }

    public Seat getSeat() {
        return seat;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(getSeat()).append(" @ ").append(getPrice()).append(" (").append(getTicketCategory()).append(")").toString(); 
    }
}
