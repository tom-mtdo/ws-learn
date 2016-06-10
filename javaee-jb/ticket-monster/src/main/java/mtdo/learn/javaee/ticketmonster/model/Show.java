package mtdo.learn.javaee.ticketmonster.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Appearance", uniqueConstraints = @UniqueConstraint(columnNames={"event_id", "venue_id"}))
public class Show {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@NotNull
	private Event event;
	
	@ManyToOne
	@NotNull
	private Venue venue;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "show", cascade = CascadeType.ALL)
	@OrderBy("date")
	private Set<Performance> performances = new HashSet<Performance>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "show", cascade = CascadeType.ALL)
	private Set<TicketPrice> ticketPrices = new HashSet<TicketPrice>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public Set<Performance> getPerformances() {
		return performances;
	}

	public void setPerformances(Set<Performance> performances) {
		this.performances = performances;
	}

	public Set<TicketPrice> getTicketPrices() {
		return ticketPrices;
	}

	public void setTicketPrices(Set<TicketPrice> ticketPrices) {
		this.ticketPrices = ticketPrices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((venue == null) ? 0 : venue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Show))
			return false;
		Show other = (Show) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (venue == null) {
			if (other.venue != null)
				return false;
		} else if (!venue.equals(other.venue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return event + " at " + venue;
	}
	
}
