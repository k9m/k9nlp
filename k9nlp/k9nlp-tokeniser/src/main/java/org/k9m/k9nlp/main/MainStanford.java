package org.k9m.k9nlp.main;

import java.io.FileNotFoundException;

import org.k9m.k9nlp.model.DocumentProfile;
import org.k9m.k9nlp.stanford.DocumentProcessor;
import org.k9m.k9nlp.stanford.DocumentProcessorFactory;
import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.stanford.nlp.pipeline.Annotation;

public class MainStanford {

	public static void main(String[] args) throws FileNotFoundException, JsonProcessingException {

		Logger LOG = LoggerFactory.getLogger(MainStanford.class);		

		String textBody = TokenUtils.readFileContent("sample.txt");	
		
		DocumentProcessorFactory documentProcessorFactory = DocumentProcessorFactory.getInstance();		
		
		Annotation document = documentProcessorFactory.constructDocument(textBody);
		LOG.debug("DocumentKeys: {}\n", TokenUtils.printKeys(document));
		
		DocumentProcessor documentProcessor = new DocumentProcessor("test_id", document);
		DocumentProfile documentProfile = documentProcessor.processDocument();	
		
		LOG.info(new ObjectMapper().writeValueAsString(documentProfile));

	}

}

