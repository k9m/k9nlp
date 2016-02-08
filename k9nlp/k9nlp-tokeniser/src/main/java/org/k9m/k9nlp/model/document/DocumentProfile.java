package org.k9m.k9nlp.model.document;

import java.util.ArrayList;
import java.util.List;

import org.k9m.k9nlp.model.document.sentence.Sentence;

public class DocumentProfile {
	
	private List<Sentence> sentences;
	
	private final String documentId;
		

	public DocumentProfile(String documentId) {		
		this.sentences = new ArrayList<>();
		this.documentId = documentId;
	}
	

	public String getDocumentId() {
		return documentId;
	}

	public List<Sentence> getSentences() {
		return sentences;
	}
	
	
	public DocumentProfile addSentence(Sentence sentence){
		sentences.add(sentence);
		return this;
	}
	
	
	
	
	
}
