package org.k9m.k9nlp.model.document.sentence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.k9m.k9nlp.model.document.sentence.entity.Entity;
import org.k9m.k9nlp.model.document.sentence.entity.Keyword;

public class Sentence {
	
	private final String sentence;	
	final Integer offsetStart;
	final Integer offsetEnd;
	
	private List<Keyword> keywords;	
	private List<Entity> entities;
	
	
	public Sentence(String sentence, Integer offsetStart, Integer offsetEnd) {
		this.sentence = sentence;
		this.offsetStart = offsetStart;
		this.offsetEnd = offsetEnd;
		
		this.keywords = new ArrayList<>();
		this.entities = new ArrayList<>();
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
	
	public List<Keyword> getKeywords() {
		return keywords;
	}
	
	public List<Entity> getEntities() {
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
