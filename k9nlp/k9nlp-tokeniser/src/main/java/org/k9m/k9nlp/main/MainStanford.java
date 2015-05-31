package org.k9m.k9nlp.main;

import java.io.FileNotFoundException;

import org.k9m.k9nlp.model.CorpusProfile;
import org.k9m.k9nlp.stanford.StanfordCorpusProcessor;
import org.k9m.k9nlp.stanford.StanfordCorpusProcessorFactory;
import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.stanford.nlp.pipeline.Annotation;

public class MainStanford {

	public static void main(String[] args) throws FileNotFoundException, JsonProcessingException {

		Logger LOG = LoggerFactory.getLogger(MainStanford.class);
		
		// read some text in the text variable
		String corpusText = "Arnold Schwarzenegger and Jerome L'Burgouis leads Mara's platoon of Vikings into New York City carnage against an unseen alien beast along with John Woo's superslick action extravaganza. 80s classic from the director of Die Hard. Contains violence. (1987)(102 mins) Also in HD";	
		
		StanfordCorpusProcessorFactory corpusProcessorFactory = StanfordCorpusProcessorFactory.getInstance();		
		
		Annotation document = corpusProcessorFactory.constructDocument(corpusText);
		LOG.debug("DocumentKeys: {}\n", TokenUtils.printKeys(document));
		
		StanfordCorpusProcessor corpusProcessor = new StanfordCorpusProcessor("test_id", document);
		CorpusProfile corpusProfile = corpusProcessor.processDocument();	
		
		LOG.info(new ObjectMapper().writeValueAsString(corpusProfile));


	}

}

