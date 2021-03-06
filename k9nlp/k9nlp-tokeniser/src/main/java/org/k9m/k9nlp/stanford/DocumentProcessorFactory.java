package org.k9m.k9nlp.stanford;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class DocumentProcessorFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(DocumentProcessorFactory.class);

	private final StanfordCoreNLP pipeline;	
	private static DocumentProcessorFactory self = new DocumentProcessorFactory();

	private DocumentProcessorFactory(){
		LOG.info("Initiliasing pipeline..."); 
		
		Properties props = new Properties();
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, entitymentions");
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, entitymentions, parse, relation");

		pipeline = new StanfordCoreNLP(props);	
		
		LOG.info("Pipeline initialised");
	}
	
	public static synchronized DocumentProcessorFactory getInstance(){
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
