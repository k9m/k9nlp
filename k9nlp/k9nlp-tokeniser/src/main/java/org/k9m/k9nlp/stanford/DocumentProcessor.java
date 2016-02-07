package org.k9m.k9nlp.stanford;

import java.util.List;

import org.k9m.k9nlp.model.DocumentProfile;
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

	private DocumentProfile corpusProfile;
	private Annotation document;
	

	public DocumentProcessor(String corpusId, Annotation document) {
		corpusProfile = new DocumentProfile(corpusId);
		this.document = document;
	}

	public DocumentProfile processDocument(){		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		if(sentences.size() > 0){ LOG.debug("SentenceKeys: {}\n", TokenUtils.printKeys(sentences.get(0))); }		
		int index = 0;
		for(CoreMap sentence : sentences) {
			List<CoreMap> mentions = sentence.get(MentionsAnnotation.class);
			
			//Printing Keys
			if(index++ == 0){
				if(sentence != null && sentence.size() > 0){ LOG.debug("TokenKeys: {}\n", TokenUtils.printKeys(sentence)); }
				if(mentions != null && mentions.size() > 0){ LOG.debug("MentionsKeys: {}\n", TokenUtils.printKeys(mentions.get(0))); }
			}
			
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

		return corpusProfile;
	}

	private void processSentence(CoreMap sentence){				
		LOG.debug("Sentence:  {},\n", sentence.get(TextAnnotation.class));		
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

			String word = token.get(OriginalTextAnnotation.class);			
			String pos = token.get(PartOfSpeechAnnotation.class);
			String ne = token.get(NamedEntityTagAnnotation.class);			

			if(TokenUtils.ifAnyOf(new String[]{"FW","JJ","JJR","JJS","NN","NNS","VB","VBD","VBG","VBN","VBP","VBZ","UH"}, pos)){
				
				String lemma = token.lemma();
				corpusProfile.addKeyword(lemma);
				
				LOG.debug("Word:   {}",word);
				LOG.debug("POS:    {}",pos);
				LOG.debug("NE:     {}",ne);			
				LOG.debug("Lemma:  {}",lemma);

				LOG.debug("===========================");
			}
		
		}
	}

	private void processMentions(List<CoreMap> mentions){		
		for (CoreMap mention : mentions) {			
			String entity = mention.get(TextAnnotation.class);
			String ne = mention.get(NamedEntityTagAnnotation.class);
			
			corpusProfile.addEntity(entity);						

			LOG.debug("Entity: {}",entity);			
			LOG.debug("NE:     {}",ne);		
			LOG.debug("----------------------------");
		}
	}
	

	/*	
	CC 	coordinating conjunction 	and
	CD 	cardinal number 	1, third
	DT 	determiner 	the
	EX 	existential there 	there is
	FW 	foreign word 	d’hoevre
	IN 	preposition/subordinating conjunction 	in, of, like
	JJ 	adjective 	big
	JJR 	adjective, comparative 	bigger
	JJS 	adjective, superlative 	biggest
	LS 	list marker 	1)
	MD 	modal 	could, will
	NN 	noun, singular or mass 	door
	NNS 	noun plural 	doors
	NNP 	proper noun, singular 	John
	NNPS 	proper noun, plural 	Vikings
	PDT 	predeterminer 	both the boys
	POS 	possessive ending 	friend‘s
	PRP 	personal pronoun 	I, he, it
	PRP$ 	possessive pronoun 	my, his
	RB 	adverb 	however, usually, naturally, here, good
	RBR 	adverb, comparative 	better
	RBS 	adverb, superlative 	best
	RP 	particle 	give up
	TO 	to 	to go, to him
	UH 	interjection 	uhhuhhuhh
	VB 	verb, base form 	take
	VBD 	verb, past tense 	took
	VBG 	verb, gerund/present participle 	taking
	VBN 	verb, past participle 	taken
	VBP 	verb, sing. present, non-3d 	take
	VBZ 	verb, 3rd person sing. present 	takes
	WDT 	wh-determiner 	which
	WP 	wh-pronoun 	who, what
	WP$ 	possessive wh-pronoun 	whose
	WRB 	wh-abverb 	where, when
	*/
	
}
