package org.k9m.k9nlp.model;

import java.util.HashSet;
import java.util.Set;

public class DocumentProfile {
	
	private Set<String> keywords;
	
	private Set<String> entities;
	
	private final String documentId;
		

	public DocumentProfile(String documentId) {
		this.keywords = new HashSet<>();
		this.entities = new HashSet<>();
		this.documentId = documentId;
	}
	

	public String getDocumentId() {
		return documentId;
	}

	public Set<String> getKeywords() {
		return keywords;
	}
	
	public Set<String> getEntities() {
		return entities;
	}
	
	
	public void addKeyword(String keyword) {
		keywords.add(keyword);
	}
	
	public void addEntity(String entity) {
		entities.add(entity);
	}
	
	
	
	
	
	
	
}
