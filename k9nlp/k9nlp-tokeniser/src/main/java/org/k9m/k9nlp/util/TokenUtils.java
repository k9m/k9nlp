package org.k9m.k9nlp.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.k9m.k9nlp.model.KeywordType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import edu.stanford.nlp.util.CoreMap;

public class TokenUtils {

	private static Logger LOG = LoggerFactory.getLogger(TokenUtils.class);

	public static String printKeys(CoreMap map){
		StringBuffer log = new StringBuffer(map.getClass().getSimpleName()).append("\n");
		for (Class clazz : map.keySet()) {
			log.append(String.format("Keys: %s\n", clazz.getSimpleName()));
		}
		log.append("*****************************");

		return log.toString();
	}

	public static boolean ifAnyOf(Enum[] matchedTokens, String partOfSpeechType){
		return ifAnyOf(Arrays.asList(matchedTokens), partOfSpeechType);
	}
	
	public static boolean ifAnyOf(List<? extends Enum> matchedTokens, String partOfSpeechType){
		for (Enum enumName : matchedTokens) {
			if(enumName.toString().equalsIgnoreCase(partOfSpeechType)){
				return true;
			}
		}

		return false;
	}

	public static String readFileContent(String fileLocationInClasspath){
		try {
			Resource resource = new ClassPathResource(fileLocationInClasspath);
			BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()),1024);
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append('\n');
			}
			br.close();
			return stringBuilder.toString();
		} catch (Exception e) {
			LOG.error("Error:", e);
			throw new RuntimeException(e);
		}

	}

}
