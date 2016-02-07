package org.k9m.k9nlp.model;

public class Entity {
	
	final private String entity;
	final private String entityType;

	public Entity(String entity, String entityType) {		
		this.entityType = entityType;
		this.entity = entity;
	}
	
	
	public String getEntity() {
		return entity;
	}

	public String getEntityType() {
		return entityType;
	}		

}
