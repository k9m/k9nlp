package org.k9m.k9nlp.stanford;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.k9m.k9nlp.model.DocumentProfile;
import org.k9m.k9nlp.model.Entity;
import org.k9m.k9nlp.model.Keyword;
import org.k9m.k9nlp.util.TokenUtils;

import edu.stanford.nlp.pipeline.Annotation;

public class DocumentProcessorTest {
	
	private static final String[] TEST_ENTITIES = {"Arnold Schwarzenegger","Vikings","New York City","1987","John Woo","102 mins","Jerome L'Burgouis","Mara","80s"}; 
	private static final String[] TEST_KEYWORDS = {"die","contain","director","beast","platoon","carnage","lead","alien","superslick","classic","min","action","extravaganza","hard","hd","unseen","violence"};
	
	private static Annotation document;

	@BeforeClass
    public static void onceExecutedBeforeAll() {
		Arrays.sort(TEST_ENTITIES);
		Arrays.sort(TEST_KEYWORDS);
		
		final String textBody = TokenUtils.readFileContent("sample.txt");
		final DocumentProcessorFactory documentProcessorFactory = DocumentProcessorFactory.getInstance();
		
		document = documentProcessorFactory.constructDocument(textBody);
    }
	
	@Test
	public void testDocumentProcessing(){
		final String documentId = "test_id";
		
		DocumentProcessor documentProcessor = new DocumentProcessor(documentId, document);
		DocumentProfile documentProfile = documentProcessor.processDocument();
		
		assertEquals("Document id doesn't match", documentId, documentProfile.getDocumentId());
		
		Set<Entity> entities = documentProfile.getEntities();
		for (Entity entity : entities) {
			final String entityValue = entity.getEntity();
			boolean isInEntities = Arrays.binarySearch(TEST_ENTITIES, entityValue) > -1;			
			assertTrue("Not in entities: " + entityValue, isInEntities);					
		}
		assertEquals("Entity mismatch", TEST_ENTITIES.length, entities.size());
		
		Set<Keyword> keywords = documentProfile.getKeywords();
		for (Keyword entity : keywords) {
			final String entityValue = entity.getLemma();
			boolean isInEntities = Arrays.binarySearch(TEST_KEYWORDS, entityValue) > -1;			
			assertTrue("Not in entities: " + entityValue, isInEntities);					
		}
		assertEquals("Keyword mismatch", TEST_KEYWORDS.length, keywords.size());
	}

}
