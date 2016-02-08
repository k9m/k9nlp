Friendly Java API for extracting Named Entities from a body of text using Stanford NLP NER. (version 3.6.0 and above!!!)

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
		"documentId": "test_id"
		"sentences": [{
			"sentence": "Arnold Schwarzenegger and Jerome L'Burgouis leads Mara's platoon of Vikings into New York City carnage against an unseen alien beast along with John Woo's superslick action extravaganza.",
			"keywords": [{
				"word": "carnage",
				"lemma": "carnage",
				"keywordType": "O",
				"pos": "NN"
			},
			{
				"word": "beast",
				"lemma": "beast",
				"keywordType": "O",
				"pos": "NN"
			},
			{
				"word": "extravaganza",
				"lemma": "extravaganza",
				"keywordType": "O",
				"pos": "NN"
			},
			{
				"word": "superslick",
				"lemma": "superslick",
				"keywordType": "O",
				"pos": "NN"
			},
			{
				"word": "leads",
				"lemma": "lead",
				"keywordType": "O",
				"pos": "VBZ"
			},
			{
				"word": "platoon",
				"lemma": "platoon",
				"keywordType": "O",
				"pos": "NN"
			},
			{
				"word": "action",
				"lemma": "action",
				"keywordType": "O",
				"pos": "NN"
			},
			{
				"word": "unseen",
				"lemma": "unseen",
				"keywordType": "O",
				"pos": "JJ"
			},
			{
				"word": "alien",
				"lemma": "alien",
				"keywordType": "O",
				"pos": "JJ"
			}],
			"entities": [{
				"entity": "Jerome L'Burgouis",
				"entityType": "PERSON"
			},
			{
				"entity": "Arnold Schwarzenegger",
				"entityType": "PERSON"
			},
			{
				"entity": "John Woo",
				"entityType": "PERSON"
			},
			{
				"entity": "Vikings",
				"entityType": "PERSON"
			},
			{
				"entity": "Mara",
				"entityType": "PERSON"
			},
			{
				"entity": "New York City",
				"entityType": "LOCATION"
			}]
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
