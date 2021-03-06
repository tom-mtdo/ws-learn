package mtdo.learn.javaeejb.ticketmonster.service;

import java.util.List;

import mtdo.learn.javaeejb.ticketmonster.model.Seat;
import mtdo.learn.javaeejb.ticketmonster.model.SectionAllocation;

/**
 * A transient object which represents a collection of pre-allocated seats
 *
 * @author Marius Bogoevici
 */
public class AllocatedSeats {

    private final SectionAllocation sectionAllocation;

    private final List<Seat> seats;

    public AllocatedSeats(SectionAllocation sectionAllocation, List<Seat> seats) {
        this.sectionAllocation = sectionAllocation;
        this.seats = seats;
    }

    public SectionAllocation getSectionAllocation() {
        return sectionAllocation;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void markOccupied() {
        sectionAllocation.markOccupied(seats);
    }
}
