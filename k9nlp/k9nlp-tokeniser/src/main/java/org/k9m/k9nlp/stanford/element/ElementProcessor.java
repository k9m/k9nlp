package org.k9m.k9nlp.stanford.element;

import org.k9m.k9nlp.model.document.sentence.Sentence;

import edu.stanford.nlp.util.CoreMap;

public interface ElementProcessor {

	void process(Sentence sentence, CoreMap... mentions);

}
