package org.k9m.k9nlp.stanford;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class StanfordCorpusProcessorFactory {

	private final StanfordCoreNLP pipeline;	
	private static StanfordCorpusProcessorFactory self = new StanfordCorpusProcessorFactory();

	private StanfordCorpusProcessorFactory(){
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, entitymentions");
		pipeline = new StanfordCoreNLP(props);		
	}
	
	public static StanfordCorpusProcessorFactory getInstance(){
		return self;
	}

	public Annotation constructDocument(String corpusText){
		Annotation document = new Annotation(corpusText);		
		pipeline.annotate(document);
		
		return document;
	}
	

}
