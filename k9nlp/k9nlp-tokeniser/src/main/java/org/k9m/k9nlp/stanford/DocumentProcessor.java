package org.k9m.k9nlp.stanford;

import java.util.List;

import org.k9m.k9nlp.model.document.DocumentProfile;
import org.k9m.k9nlp.model.document.sentence.Sentence;
import org.k9m.k9nlp.stanford.element.EntityProcessor;
import org.k9m.k9nlp.stanford.element.SentenceProcessor;
import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.MentionsAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class DocumentProcessor {

	private static Logger LOG = LoggerFactory.getLogger(DocumentProcessor.class);
		
	private final Annotation document;
	
	private DocumentProfile documentProfile;	

	public DocumentProcessor(String documentId, Annotation document) {
		this.document = document;
		
		documentProfile = new DocumentProfile(documentId);
	}

	public DocumentProfile processDocument(){				
		List<CoreMap> stanfordSentences = document.get(SentencesAnnotation.class);
				
		int index = 0;
		for(final CoreMap stanfordSentence : stanfordSentences) {			
			final List<CoreMap> mentions = stanfordSentence.get(MentionsAnnotation.class);			
			
			if(index++ == 0){
				LOG.debug("SentenceKeys: {}\n", TokenUtils.printKeys(stanfordSentence));
				if(stanfordSentence != null && stanfordSentence.size() > 0){ LOG.debug("TokenKeys: {}\n", TokenUtils.printKeys(stanfordSentence)); }
				if(mentions != null && mentions.size() > 0){ LOG.debug("MentionsKeys: {}\n", TokenUtils.printKeys(mentions.get(0))); }
			}
			
			this.processSentence(stanfordSentence);
			
			printGraphs(stanfordSentence);
		}
		
		// This is the coreference link graph
		// Each chain stores a set of mentions that link to each other,
		// along with a method for getting the most representative mention
		// Both sentence and token offsets start at 1!
		//Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);

		return documentProfile;
	}
	
	private void processSentence(CoreMap stanfordSentence){
		final List<CoreMap> entities = stanfordSentence.get(MentionsAnnotation.class);
		if(stanfordSentence != null){
			
			final String sentenceText = stanfordSentence.get(TextAnnotation.class);
			final Integer offsetStart = stanfordSentence.get(CharacterOffsetBeginAnnotation.class);
			final Integer sentenceEndOffset = stanfordSentence.get(CharacterOffsetEndAnnotation.class);
			Sentence sentence = new Sentence(sentenceText, offsetStart, sentenceEndOffset);
			
			LOG.debug("Sentence[{},{}]:  {}", new Object[]{offsetStart, sentenceEndOffset, sentenceText});
						
			if(entities != null && entities.size() > 0){				
				new EntityProcessor().process(sentence, entities.toArray(new CoreMap[entities.size()]));
			}
			
			if(stanfordSentence != null && stanfordSentence.size() > 0){
				new SentenceProcessor().process(stanfordSentence, sentence);
			}
			
			documentProfile.addSentence(sentence);
		}
	}
	
	private static void printGraphs(CoreMap stanfordSentence){
		final SemanticGraph semanticGraph = stanfordSentence.get(BasicDependenciesAnnotation.class);
		if(semanticGraph != null){
			semanticGraph.prettyPrint();
		}
		final Tree tree = stanfordSentence.get(TreeAnnotation.class);
		if(tree != null){
			tree.pennPrint();		
		}
		
		
	}
	
}
