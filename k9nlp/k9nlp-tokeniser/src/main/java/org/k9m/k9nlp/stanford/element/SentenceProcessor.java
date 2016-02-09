package org.k9m.k9nlp.stanford.element;

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

import org.k9m.k9nlp.model.document.sentence.Sentence;
import org.k9m.k9nlp.model.document.sentence.entity.Keyword;
import org.k9m.k9nlp.model.document.sentence.entity.KeywordType;
import org.k9m.k9nlp.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.OriginalTextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;

public class SentenceProcessor/* implements ElementProcessor<Sentence>*/{
	
	private static Logger LOG = LoggerFactory.getLogger(SentenceProcessor.class);
	
	private static final List<KeywordType> parsedKeywordTypes = Arrays.asList(new KeywordType[]{FW,JJ,JJR,JJS,NN,NNS,VB,VBD,VBG,VBN,VBP,VBZ,UH});
//	private List<KeywordType> parsedKeywordTypes = Arrays.asList(new KeywordType[]{			
//			FW,// 	foreign word 	d’hoevre			
//			JJ,// 	adjective 	big
//			JJR,// 	adjective, comparative 	bigger
//			JJS,// 	adjective, superlative 	biggest			
//			NN,// 	noun, singular or mass 	door
//			NNS,// 	noun plural 	doors
//			NNP,// 	proper noun, singular 	John
//			NNPS,// 	proper noun, plural 	Vikings			
//			PRP$,// 	possessive pronoun 	my, his
//			RB,// 	adverb 	however, usually, naturally, here, good
//			RBR,// 	adverb, comparative 	better
//			RBS,// 	adverb, superlative 	best
//			RP,// 	particle 	give up			
//			UH,//	interjection 	uhhuhhuhh
//			VB,// 	verb, base form 	take
//			VBD,// 	verb, past tense 	took
//			VBG,// 	verb, gerund/present participle 	taking
//			VBN,// 	verb, past participle 	taken
//			VBP,// 	verb, sing. present, non-3d 	take
//			VBZ,// 	verb, 3rd person sing. present 	takes			
//	});
	
	public void process(CoreMap stanfordSentence, Sentence sentence){		
		for (CoreLabel token : stanfordSentence.get(TokensAnnotation.class)) {

			final String word = token.get(OriginalTextAnnotation.class);			
			final String pos = token.get(PartOfSpeechAnnotation.class);
			final String ne = token.get(NamedEntityTagAnnotation.class);
			final String lemma = token.lemma();
			final Integer offsetStart = token.get(CharacterOffsetBeginAnnotation.class);
			final Integer offsetEnd = token.get(CharacterOffsetEndAnnotation.class);

			if(TokenUtils.ifAnyOf(parsedKeywordTypes, pos)){
				
				Keyword keyword = new Keyword()
					.setKeywordType(ne)
					.setPos(pos)
					.setWord(word)
					.setLemma(lemma)
					.setOffsetStart(offsetStart)
					.setOffsetEnd(offsetEnd);
				
				LOG.debug("Word:   {}",word);
				LOG.debug("POS:    {}",pos);
				LOG.debug("NE:     {}",ne);			
				LOG.debug("Lemma:  {}",lemma);
				LOG.debug("[{},{}]", new Object[]{offsetStart, offsetEnd});

				LOG.debug("===========================\n");
				
				sentence.addKeyword(keyword);				
			}
		
		}		
		
	}

}
