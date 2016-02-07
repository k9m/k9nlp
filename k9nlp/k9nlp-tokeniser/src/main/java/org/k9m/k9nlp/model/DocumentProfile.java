package org.k9m.k9nlp.model;

import java.util.HashSet;
import java.util.Set;

public class DocumentProfile {
	
	private Set<Keyword> keywords;
	
	private Set<Entity> entities;
	
	private final String documentId;
		

	public DocumentProfile(String documentId) {
		this.keywords = new HashSet<>();
		this.entities = new HashSet<>();
		this.documentId = documentId;
	}
	

	public String getDocumentId() {
		return documentId;
	}

	public Set<Keyword> getKeywords() {
		return keywords;
	}
	
	public Set<Entity> getEntities() {
		return entities;
	}
	
	
	public void addKeyword(Keyword keyword) {
		keywords.add(keyword);
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	
	
	
	
	
	
}
