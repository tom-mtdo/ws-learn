package org.jboss.examples.ticketmonster.model;
public enum MediaType {
	IMAGE("Image", true);
	
	private final String description;
	private final Boolean cacheable;

	private MediaType(String description, Boolean cacheable) {
		this.description = description;
		this.cacheable = cacheable;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getCacheable() {
		return cacheable;
	}
		
}