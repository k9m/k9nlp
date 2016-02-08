package org.k9m.k9nlp.model.document.sentence;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.k9m.k9nlp.model.document.sentence.entity.Entity;
import org.k9m.k9nlp.model.document.sentence.entity.Keyword;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;

public class Sentence {
	
	private final String sentence;	
	final Integer sentenceStartOffset;
	final Integer sentenceEndOffset;
	
	private Set<Keyword> keywords;	
	private Set<Entity> entities;
	
	
	public Sentence(String sentence, Integer sentenceStartOffset, Integer sentenceEndOffset) {
		this.sentence = sentence;
		this.sentenceStartOffset = sentenceStartOffset;
		this.sentenceEndOffset = sentenceEndOffset;
		
		this.keywords = new HashSet<>();
		this.entities = new HashSet<>();
	}


	public String getSentence() {
		return sentence;
	}
	
	public Integer getSentenceStartOffset() {
		return sentenceStartOffset;
	}
	
	public Integer getSentenceEndOffset() {
		return sentenceEndOffset;
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
