package org.k9m.k9nlp.main;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
//		Logger LOG = LoggerFactory.getLogger(Main.class);
//		
//		String exampleString = "Lisa Londer is on a journey round the Korean peninsula where he explores the country's current trends and meets an unlikely TV cult heroine. Also in HD. [S]";
//				
//		
//		InputStream libNameFinder = new FileInputStream("src/main/resources/en-ner-person.bin");
//		InputStream libTokeniser = new FileInputStream("src/main/resources/en-token.bin");
//
//		try {
//			TokenizerModel modeTokeniser = new TokenizerModel(libTokeniser);
//			Tokenizer tokeniser = new TokenizerME(modeTokeniser);
//			
//			String tokens[] = tokeniser.tokenize(exampleString);			
//			for (String token : tokens) {
//				LOG.debug(token);
//			}
//			
//			TokenNameFinderModel modelNameFinder = new TokenNameFinderModel(libNameFinder);
//			NameFinderME nameFinder = new NameFinderME(modelNameFinder);
//			
//			Span nameSpans[] = nameFinder.find(tokens);			
//			for (Span span : nameSpans) {
//				LOG.debug(ReflectionToStringBuilder.toString(span, ToStringStyle.MULTI_LINE_STYLE));
//			}			
//
//			nameFinder.clearAdaptiveData();
//		}
//		catch (IOException e) {
//		  e.printStackTrace();
//		}
//		finally {
//		  if (libNameFinder != null) {
//		    try {
//		      libNameFinder.close();
//		    }
//		    catch (IOException e) {
//		    }
//		  }
//		}

	}

}

