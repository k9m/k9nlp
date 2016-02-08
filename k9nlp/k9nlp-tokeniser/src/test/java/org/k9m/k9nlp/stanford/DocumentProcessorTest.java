package org.k9m.k9nlp.stanford;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.k9m.k9nlp.model.document.DocumentProfile;
import org.k9m.k9nlp.model.document.sentence.Sentence;
import org.k9m.k9nlp.model.document.sentence.entity.Entity;
import org.k9m.k9nlp.model.document.sentence.entity.Keyword;
import org.k9m.k9nlp.util.TokenUtils;

import edu.stanford.nlp.pipeline.Annotation;

public class DocumentProcessorTest {
	
	private static final String[] TEST_ENTITIES = {"Arnold Schwarzenegger","Vikings","New York City","1987","John Woo","102 mins","Jerome L'Burgouis","Mara","80s"}; 
	private static final String[] TEST_KEYWORDS = {"die","contain","director","beast","platoon","carnage","lead","alien","superslick","classic","min","action","extravaganza","hard","hd","unseen","violence"};
	
	private static Annotation document;
	private static String documentId;
	private static DocumentProfile documentProfile;

	@BeforeClass
    public static void onceExecutedBeforeAll() {
		Arrays.sort(TEST_ENTITIES);
		Arrays.sort(TEST_KEYWORDS);
		
		final String textBody = TokenUtils.readFileContent("sample.txt");
		final DocumentProcessorFactory documentProcessorFactory = DocumentProcessorFactory.getInstance();
		
		document = documentProcessorFactory.constructDocument(textBody);
		
		documentId = "test_id";
		documentProfile = new DocumentProcessor(documentId, document).processDocument();
    }
	
	@Test
	public void testDocumentProperties(){
		assertEquals("Document id doesn't match", documentId, documentProfile.getDocumentId());
	}
	
	
	@Test
	public void testEntities(){
		int totalEntities = 0;
		for(Sentence sentence : documentProfile.getSentences()){
			Set<Entity> entities = sentence.getEntities();
			for (Entity entity : entities) {
				final String entityValue = entity.getEntity();
				boolean isInEntities = Arrays.binarySearch(TEST_ENTITIES, entityValue) > -1;			
				assertTrue("Not in entities: " + entityValue, isInEntities);					
			}
			totalEntities += entities.size();		
		}
		assertEquals("Entity mismatch", TEST_ENTITIES.length, totalEntities);
	}
	
	
	@Test
	public void testKeywords(){		
		int totalKeywords = 0;
		for(Sentence sentence : documentProfile.getSentences()){		
			Set<Keyword> keywords = sentence.getKeywords();
			for (Keyword entity : keywords) {
				final String entityValue = entity.getLemma();
				boolean isInEntities = Arrays.binarySearch(TEST_KEYWORDS, entityValue) > -1;			
				assertTrue("Not in entities: " + entityValue, isInEntities);					
			}
			totalKeywords += keywords.size();		
		}
		assertEquals("Entity mismatch", TEST_KEYWORDS.length, totalKeywords);
	}
	
//  TODO implementing setting types at runtime
//	@Test
//	public void testSetkeywordType(){
//		
//	}
//	
//	@Test
//	public void testSetEntityType(){
//		
//	}

}
