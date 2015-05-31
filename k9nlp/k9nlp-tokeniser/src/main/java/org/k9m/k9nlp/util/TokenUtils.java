package org.k9m.k9nlp.util;

import edu.stanford.nlp.util.CoreMap;

public class TokenUtils {
	
	public static String printKeys(CoreMap map){
		StringBuffer log = new StringBuffer(map.getClass().getSimpleName()).append("\n");
		for (Class clazz : map.keySet()) {
			log.append(String.format("Keys: %s\n", clazz.getSimpleName()));
		}
		log.append("*****************************");
		
		return log.toString();
	}
	
	public static boolean ifAnyOf(String[] matchedTokens, String partOfSpeechType){
		for (String string : matchedTokens) {
			if(string.equalsIgnoreCase(partOfSpeechType)){
				return true;
			}
		}
		
		return false;
	}

}
