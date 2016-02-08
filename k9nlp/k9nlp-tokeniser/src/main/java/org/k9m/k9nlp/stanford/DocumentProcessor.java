package org.k9m.k9nlp.stanford;

import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.FW;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.JJ;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.JJR;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.JJS;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.NN;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.NNS;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.UH;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.VB;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.VBD;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.VBG;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.VBN;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.VBP;
import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.VBZ;

import java.util.Arrays;
import java.util.List;

import org.k9m.k9nlp.model.document.DocumentProfile;
import org.k9m.k9nlp.model.document.sentence.Sentence;
import org.k9m.k9nlp.model.document.sentence.entity.Entity;
import org.k9m.k9nlp.model.document.sentence.entity.Keyword;
import org.k9m.k9nlp.model.document.sentence.entity.KeywordType;
import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
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
		List<CoreMap> stanfordSentences = document.get(SentencesAnnotation.class);

		if(stanfordSentences.size() > 0){ LOG.debug("SentenceKeys: {}\n", TokenUtils.printKeys(stanfordSentences.get(0))); }		
		int index = 0;
		for(CoreMap stanfordSentence : stanfordSentences) {			
			final List<CoreMap> mentions = stanfordSentence.get(MentionsAnnotation.class);
			
			if(index++ == 0){
				if(stanfordSentence != null && stanfordSentence.size() > 0){ LOG.debug("TokenKeys: {}\n", TokenUtils.printKeys(stanfordSentence)); }
				if(mentions != null && mentions.size() > 0){ LOG.debug("MentionsKeys: {}\n", TokenUtils.printKeys(mentions.get(0))); }
			}
			
			final String sentenceText = stanfordSentence.get(TextAnnotation.class);
			final Integer sentenceStartOffset = stanfordSentence.get(CharacterOffsetBeginAnnotation.class);
			final Integer sentenceEndOffset = stanfordSentence.get(CharacterOffsetEndAnnotation.class);
			LOG.debug("Sentence[{},{}]:  {}", new Object[]{sentenceStartOffset, sentenceEndOffset, sentenceText});
			
			Sentence sentence = new Sentence(sentenceText, sentenceStartOffset, sentenceEndOffset);
			if(mentions != null && mentions.size() > 0){
				sentence = this.processMentions(mentions, sentence);
			}
			
			if(stanfordSentence != null && stanfordSentence.size() > 0){
				sentence = this.processSentence(stanfordSentence, sentence);
			}
			
			documentProfile.addSentence(sentence);

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

	private Sentence processSentence(CoreMap stanfordSentence, Sentence sentence){
		for (CoreLabel token : stanfordSentence.get(TokensAnnotation.class)) {

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
				
				sentence.addKeyword(keyword);				
			}
		
		}
		
		return sentence;
	}

	private Sentence processMentions(List<CoreMap> mentions, Sentence sentence){		
		for (CoreMap mention : mentions) {			
			String entity = mention.get(TextAnnotation.class);
			String ne = mention.get(NamedEntityTagAnnotation.class);

			LOG.debug("Entity: {}",entity);			
			LOG.debug("NE:     {}",ne);		
			LOG.debug("----------------------------");
			
			sentence.addEntity(new Entity(entity, ne));
		}
		
		return sentence;
	}
	
}
