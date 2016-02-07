Friendly Java API for extracting Named Entities from a body of text using Stanford NLP NER.

	Sample Java code:
	
	public static void main(String[] args) throws Exception {	
		//Input as raw text
		String textBody = TokenUtils.readFileContent("sample.txt");	
		
		Annotation document = DocumentProcessorFactory.getInstance().constructDocument(textBody);
		
		DocumentProcessor documentProcessor = new DocumentProcessor("test_id", document);
		DocumentProfile documentProfile = documentProcessor.processDocument();	
		
		//Output as JSON
		LOG.info(new ObjectMapper().writeValueAsString(documentProfile));
	}
	
	Sample JSON output:
	
	{
	"documentId": "test_id",
	"keywords": [{
		"word": "mins",
		"lemma": "min",
		"keywordType": "DURATION",
		"pos": "NNS"
	},	
	{
		"word": "leads",
		"lemma": "lead",
		"keywordType": "O",
		"pos": "VBZ"
	},	
	{
		"word": "Hard",
		"lemma": "hard",
		"keywordType": "O",
		"pos": "JJ"
	}],
	"entities": [{
		"entity": "Jerome L'Burgouis",
		"entityType": "PERSON"
	},	
	{
		"entity": "102 mins",
		"entityType": "DURATION"
	},
	{
		"entity": "1987",
		"entityType": "DATE"
	},
	{
		"entity": "80s",
		"entityType": "NUMBER"
	},
	{
		"entity": "John Woo",
		"entityType": "PERSON"
	},	
	{
		"entity": "New York City",
		"entityType": "LOCATION"
	}]	
}

ACKNOWLEDGEMENTS:
Mining of entities is entirely driven by Stanford NLP engine.

"The Stanford NLP Group makes some of our Natural Language Processing software available to everyone! 
We provide statistical NLP, deep learning NLP, and rule-based NLP tools for major computational linguistics problems, 
which can be incorporated into applications with human language technology needs. These packages are widely used in industry, 
academia, and government."

http://nlp.stanford.edu/software/
http://www.gnu.org/licenses/gpl-2.0.html
