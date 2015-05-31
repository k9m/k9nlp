package org.k9m.k9nlp.stanford;

import java.util.Properties;

import org.k9m.k9nlp.main.MainStanford;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class StanfordCorpusProcessorFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainStanford.class);

	private final StanfordCoreNLP pipeline;	
	private static StanfordCorpusProcessorFactory self = new StanfordCorpusProcessorFactory();

	private StanfordCorpusProcessorFactory(){
		LOG.info("Initiliasing pipeline..."); 
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, entitymentions");
		pipeline = new StanfordCoreNLP(props);	
		
		LOG.info("Pipeline initialised");
	}
	
	public static StanfordCorpusProcessorFactory getInstance(){
		return self;
	}

	public Annotation constructDocument(String corpusText){
		LOG.info("Constructing document...");
		
		Annotation document = new Annotation(corpusText);		
		pipeline.annotate(document);
		
		LOG.info("Document Constructed");
		
		return document;
	}
	

}
