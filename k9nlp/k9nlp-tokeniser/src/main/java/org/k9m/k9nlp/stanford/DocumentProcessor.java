package org.k9m.k9nlp.stanford;

import static org.k9m.k9nlp.model.KeywordType.*;

import java.util.Arrays;
import java.util.List;

import org.k9m.k9nlp.model.DocumentProfile;
import org.k9m.k9nlp.model.Entity;
import org.k9m.k9nlp.model.Keyword;
import org.k9m.k9nlp.model.KeywordType;
import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations.MentionsAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.OriginalTextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;


public class DocumentProcessor {

	private static Logger LOG = LoggerFactory.getLogger(DocumentProcessor.class);

	private final DocumentProfile documentProfile;
	private final Annotation document;
	
	private List<KeywordType> parsedKeywordTypes = Arrays.asList(new KeywordType[]{FW,JJ,JJR,JJS,NN,NNS,VB,VBD,VBG,VBN,VBP,VBZ,UH});

	public DocumentProcessor(String documentId, Annotation document) {		
		this.documentProfile = new DocumentProfile(documentId);
		this.document = document;
	}
	
	//TODO writing up tests
//	public DocumentProcessor setParsedKeywordTypes(List<KeywordType> parsedKeywordTypes) {
//		this.parsedKeywordTypes = parsedKeywordTypes;
//		return this;
//	}
//	
//	public List<KeywordType> getParsedKeywordTypes() {
//		return parsedKeywordTypes;
//	}

	public DocumentProfile processDocument(){		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		if(sentences.size() > 0){ LOG.debug("SentenceKeys: {}\n", TokenUtils.printKeys(sentences.get(0))); }		
		int index = 0;
		for(CoreMap sentence : sentences) {
			List<CoreMap> mentions = sentence.get(MentionsAnnotation.class);			
			
			if(index++ == 0){
				if(sentence != null && sentence.size() > 0){ LOG.debug("TokenKeys: {}\n", TokenUtils.printKeys(sentence)); }
				if(mentions != null && mentions.size() > 0){ LOG.debug("MentionsKeys: {}\n", TokenUtils.printKeys(mentions.get(0))); }
			}
			
			LOG.debug("Sentence:  {}", sentence.get(TextAnnotation.class));
			if(mentions != null && mentions.size() > 0){
				this.processMentions(mentions);
			}
			
			if(sentence != null && sentence.size() > 0){
				this.processSentence(sentence);
			}

			// this is the parse tree of the current sentence
			//Tree tree = sentence.get(TreeAnnotation.class);

			// this is the Stanford dependency graph of the current sentence
			//SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

			//IndexedWord ind = dependencies.getFirstRoot();
		}
		
		// This is the coreference link graph
		// Each chain stores a set of mentions that link to each other,
		// along with a method for getting the most representative mention
		// Both sentence and token offsets start at 1!
		//Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);

		return documentProfile;
	}

	private void processSentence(CoreMap sentence){
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

			String word = token.get(OriginalTextAnnotation.class);			
			String pos = token.get(PartOfSpeechAnnotation.class);
			String ne = token.get(NamedEntityTagAnnotation.class);			

			if(TokenUtils.ifAnyOf(parsedKeywordTypes, pos)){				
				String lemma = token.lemma();
				
				Keyword keyword = new Keyword();
				
				keyword.setKeywordType(ne);
				keyword.setPos(pos);
				keyword.setWord(word);
				keyword.setLemma(lemma);
				
				LOG.debug("Word:   {}",word);
				LOG.debug("POS:    {}",pos);
				LOG.debug("NE:     {}",ne);			
				LOG.debug("Lemma:  {}",lemma);

				LOG.debug("===========================\n");
				
				documentProfile.addKeyword(keyword);
			}
		
		}
	}

	private void processMentions(List<CoreMap> mentions){		
		for (CoreMap mention : mentions) {			
			String entity = mention.get(TextAnnotation.class);
			String ne = mention.get(NamedEntityTagAnnotation.class);

			LOG.debug("Entity: {}",entity);			
			LOG.debug("NE:     {}",ne);		
			LOG.debug("----------------------------");
			
			documentProfile.addEntity(new Entity(entity, ne));
		}
	}
	
}
