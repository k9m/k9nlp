package org.k9m.k9nlp.stanford;

import java.util.List;
import java.util.Map;

import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.MentionsAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.OriginalTextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentClass;
import edu.stanford.nlp.util.CoreMap;


public class StanfordCorpusProcessor {

	private static Logger LOG = LoggerFactory.getLogger(StanfordCorpusProcessor.class);

	private Annotation document;

	public StanfordCorpusProcessor(Annotation document) {
		this.document = document;
	}

	public void processDocument(){		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		if(sentences.size() > 0){ LOG.info("SentenceKeys: {}\n", TokenUtils.printKeys(sentences.get(0))); }		
		int index = 0;
		for(CoreMap sentence : sentences) {
			if(index++ == 0){
				if(sentence.size() > 0){ LOG.info("TokenKeys: {}\n", TokenUtils.printKeys(sentence)); }
			}

			String sentenceTxt = sentence.get(TextAnnotation.class);			
			String sentimentTxt = sentence.get(SentimentClass.class);

			LOG.info("Sentence:  {},\n", sentenceTxt);
			LOG.info("Sentiment: {},\n", sentimentTxt);


			List<CoreMap> mentions = sentence.get(MentionsAnnotation.class);

			processMentions(mentions);
			processTokens(sentence);

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
		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);


	}

	private static void processTokens(CoreMap sentence){		
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

			String word = token.get(OriginalTextAnnotation.class);			
			String pos = token.get(PartOfSpeechAnnotation.class);
			String ne = token.get(NamedEntityTagAnnotation.class);			

			if(ifAnyOf(new String[]{"FW","JJ","JJR","JJS","NN","NNS","VB","VBD","VBG","VBN","VBP","VBZ","UH"}, pos)){
				LOG.info("Word:   {}",word);
				LOG.info("POS:    {}",pos);
				LOG.info("NE:     {}",ne);			
				LOG.info("Lemma:  {}",token.lemma());

				LOG.info("===========================");
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
	}

	private static void processMentions(List<CoreMap> mentions){
		if(mentions.size() > 0){ LOG.info("MentionsKeys: {}\n", TokenUtils.printKeys(mentions.get(0))); }
		for (CoreMap mention : mentions) {

			String word = mention.get(TextAnnotation.class);
			String ne = mention.get(NamedEntityTagAnnotation.class);			

			LOG.info("Word:   {}",word);			
			LOG.info("NE:     {}",ne);		
			

			LOG.info("----------------------------");


		}

	}
	
	private static boolean ifAnyOf(String[] matchedTokens, String partOfSpeechType){
		for (String string : matchedTokens) {
			if(string.equalsIgnoreCase(partOfSpeechType)){
				return true;
			}
		}
		
		return false;
	}









}
