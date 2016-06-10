package mtdo.learn.javaee.ticketmonster.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Entity
public class Event implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	@NotNull
	@Size(min = 5, max = 50, message = "An event's name must contain between 5 and 50 characters")
	private String name;

	@Column
	@NotNull
	@Size(min = 20, max = 1000, message = "An event's description must be between 20 and 1000 characters")
	private String description;

	@ManyToOne
	private MediaItem mediaItem;
	
	@ManyToOne
	private EventCategory category;
	
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public MediaItem getMediaItem() {
		return mediaItem;
	}

	public void setMediaItem(MediaItem mediaItem) {
		this.mediaItem = mediaItem;
	}

	public EventCategory getCategory() {
		return category;
	}

	public void setCategory(EventCategory category) {
		this.category = category;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Event)) {
			return false;
		}
		Event other = (Event) obj;
		if (name != null) {
			if (!name.equals(other.name)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return ((name == null) ? 0 : name.hashCode());
	}

	@Override
	public String toString() {
		return name;
	}
}