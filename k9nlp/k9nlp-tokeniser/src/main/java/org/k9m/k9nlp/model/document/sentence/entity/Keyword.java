package org.k9m.k9nlp.model.document.sentence.entity;

public class Keyword {
	
	private String word;
	private String lemma;
	private String keywordType;
	private String pos;
	private Integer offsetStart;
	private Integer offsetEnd;
	
		
	public String getWord() {
		return word;
	}
	public String getLemma() {
		return lemma;
	}
	public String getKeywordType() {
		return keywordType;
	}
	public String getPos() {
		return pos;
	}
	public Integer getOffsetStart() {
		return offsetStart;
	}
	public Integer getOffsetEnd() {
		return offsetEnd;
	}
	
	
	public Keyword setWord(String word) {
		this.word = word;
		return this;
	}
	public Keyword setLemma(String lemma) {
		this.lemma = lemma;
		return this;
	}
	public Keyword setKeywordType(String keywordType) {
		this.keywordType = keywordType;
		return this;
	}
	public Keyword setPos(String pos) {
		this.pos = pos;
		return this;
	}
	public Keyword setOffsetStart(Integer offsetStart) {
		this.offsetStart = offsetStart;
		return this;
	}
	public Keyword setOffsetEnd(Integer offsetEnd) {
		this.offsetEnd = offsetEnd;
		return this;
	}
	
	
	
	
	

	
}
