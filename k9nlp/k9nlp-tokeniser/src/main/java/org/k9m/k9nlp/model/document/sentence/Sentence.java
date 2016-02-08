package org.k9m.k9nlp.model.document.sentence;

import java.util.HashSet;
import java.util.Set;

import org.k9m.k9nlp.model.document.sentence.entity.Entity;
import org.k9m.k9nlp.model.document.sentence.entity.Keyword;

public class Sentence {
	
	private final String sentence;	
	final Integer offsetStart;
	final Integer offsetEnd;
	
	private Set<Keyword> keywords;	
	private Set<Entity> entities;
	
	
	public Sentence(String sentence, Integer offsetStart, Integer offsetEnd) {
		this.sentence = sentence;
		this.offsetStart = offsetStart;
		this.offsetEnd = offsetEnd;
		
		this.keywords = new HashSet<>();
		this.entities = new HashSet<>();
	}


	public String getSentence() {
		return sentence;
	}
	
	public Integer getOffsetStart() {
		return offsetStart;
	}
	
	public Integer getOffsetEnd() {
		return offsetEnd;
	}
	
	public Set<Keyword> getKeywords() {
		return keywords;
	}
	
	public Set<Entity> getEntities() {
		return entities;
	}
	
	
	public Sentence addKeyword(Keyword keyword) {
		keywords.add(keyword);
		return this;
	}
	
	public Sentence addEntity(Entity entity) {
		entities.add(entity);
		return this;
	}
	
	
	

}
