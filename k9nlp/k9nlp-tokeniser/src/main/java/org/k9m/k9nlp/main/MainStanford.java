package org.k9m.k9nlp.main;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.OriginalTextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentenceIndexAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class MainStanford {

	public static void main(String[] args) throws FileNotFoundException {

		Logger LOG = LoggerFactory.getLogger(MainStanford.class);


		StanfordCoreNLP pipeline = init();

		// read some text in the text variable
		String text = "Arnold Schwarzenegger and Jerome L'Burgouis leads a platoon of mercenaries into New York City carnage against an unseen alien beast. 80s classic from the director of Die Hard. Contains violence. (1987)(102 mins) Also in HD";

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);


		for (Class clazz : document.keySet()) {
			LOG.info("DocumentKeys: {}", clazz);
		}
		LOG.info("*****************************");
		
		
		List<CoreLabel> labels = document.get(TokensAnnotation.class);
		for (CoreLabel coreLabel : labels) {
			for (Class clazz : document.keySet()) {
				LOG.info("LabelKeys: {}", clazz);
			}
			LOG.info("*****************************");
		}


		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence : sentences) {

			for (Class clazz : sentence.keySet()) {
				LOG.info("SentenceKeys: {}", clazz);
			}
			LOG.info("*****************************");			
			LOG.info("Sentence: {}\n", sentence.get(TextAnnotation.class));			


			//			for(RelationMention rel : sentence.get(MachineReadingAnnotations.RelationMentionsAnnotation.class)){					
			//				for (String argName : rel.getArgNames()) {
			//					LOG.info("ArgName:{}", new String[]{argName});
			//				}
			//			}

			sentence.get(SentenceIndexAnnotation.class);

			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

				/*for (Class clazz : token.keySet()) {
					LOG.info("TokenKeys: {}", clazz);
				}
				LOG.info("===========================");*/


				String word = token.get(OriginalTextAnnotation.class);				
				String pos = token.get(PartOfSpeechAnnotation.class);
				String ne = token.get(NamedEntityTagAnnotation.class);



				LOG.info("Word:   {}",word);// {}, {}, {}", new String[]{word, pos, ne, token.lemma()});
				LOG.info("POS:    {}",pos);
				LOG.info("NE:     {}",ne);
				LOG.info("Lemma:  {}",token.lemma());								

				LOG.info("===========================");

			}

			// this is the parse tree of the current sentence
			Tree tree = sentence.get(TreeAnnotation.class);

			// this is the Stanford dependency graph of the current sentence
			SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

			IndexedWord ind = dependencies.getFirstRoot();

			LOG.info("##{}", ind);

		}

		// This is the coreference link graph
		// Each chain stores a set of mentions that link to each other,
		// along with a method for getting the most representative mention
		// Both sentence and token offsets start at 1!
		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);


	}

	public static StanfordCoreNLP init(){		

		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, entitymentions");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		return pipeline;
	}

}

