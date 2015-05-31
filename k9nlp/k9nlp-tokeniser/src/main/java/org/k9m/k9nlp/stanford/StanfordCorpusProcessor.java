package org.k9m.k9nlp.stanford;

import java.util.List;
import java.util.Map;

import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.OriginalTextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class StanfordCorpusProcessor {
	
	private static Logger LOG = LoggerFactory.getLogger(StanfordCorpusProcessor.class);

	private Annotation document;

	public StanfordCorpusProcessor(Annotation document) {
		this.document = document;
	}

	public void processSentences(){		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		if(sentences.size() > 0){ LOG.info("SentenceKeys: {}\n", TokenUtils.printKeys(sentences.get(0))); }		
		int index = 0;
		for(CoreMap sentence : sentences) {
			if(index++ == 0){
				if(sentence.size() > 0){ LOG.info("TokenKeys: {}\n", TokenUtils.printKeys(sentence)); }
			}
			LOG.info("Sentence: {}\n", sentence.get(TextAnnotation.class));
			processTokens(sentence);

			// this is the parse tree of the current sentence
			Tree tree = sentence.get(TreeAnnotation.class);

			// this is the Stanford dependency graph of the current sentence
			SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

			IndexedWord ind = dependencies.getFirstRoot();
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



			LOG.info("Word:   {}",word);// {}, {}, {}", new String[]{word, pos, ne, token.lemma()});
			LOG.info("POS:    {}",pos);
			LOG.info("NE:     {}",ne);
			LOG.info("Lemma:  {}",token.lemma());								

			LOG.info("===========================");

		}
	}
	
	
	
	
	
	

}
