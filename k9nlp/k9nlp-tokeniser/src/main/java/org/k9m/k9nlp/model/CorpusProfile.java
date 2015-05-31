package org.k9m.k9nlp.model;

import java.util.HashSet;
import java.util.Set;

public class CorpusProfile {
	
	private final String corpusId;
	
	private Set<String> keywords;
	
	private Set<String> entities;
		

	public CorpusProfile(String corpusId) {
		keywords = new HashSet<>();
		entities = new HashSet<>();
		this.corpusId = corpusId;
	}
	

	public String getCorpusId() {
		return corpusId;
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
