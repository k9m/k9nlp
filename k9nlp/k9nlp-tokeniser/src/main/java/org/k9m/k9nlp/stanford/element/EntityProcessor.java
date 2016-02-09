package org.k9m.k9nlp.stanford.element;

import org.k9m.k9nlp.model.document.sentence.Sentence;
import org.k9m.k9nlp.model.document.sentence.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class EntityProcessor implements ElementProcessor{
	
	private static Logger LOG = LoggerFactory.getLogger(EntityProcessor.class);	
	
	public void process(Sentence sentence, CoreMap... mentions){		
		for (CoreMap mention : mentions) {
			
			String entity = mention.get(TextAnnotation.class);
			String ne = mention.get(NamedEntityTagAnnotation.class);

			LOG.debug("  Entity: {}",entity);			
			LOG.debug("  NE:     {}",ne);		
			LOG.debug("  ----------------------------");
			
			sentence.addEntity(new Entity(entity, ne));
		}
	}

}
