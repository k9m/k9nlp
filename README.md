Friendly Java API for extracting Named Entities from a body of text

  public static void main(String[] args) throws Exception {

		Logger LOG = LoggerFactory.getLogger(MainStanford.class);		

		//Input as raw text
		String textBody = TokenUtils.readFileContent("sample.txt");	
		
		DocumentProcessorFactory documentProcessorFactory = DocumentProcessorFactory.getInstance();		
		
		Annotation document = documentProcessorFactory.constructDocument(textBody);
		LOG.debug("DocumentKeys: {}\n", TokenUtils.printKeys(document));
		
		DocumentProcessor documentProcessor = new DocumentProcessor("test_id", document);
		DocumentProfile documentProfile = documentProcessor.processDocument();	
		
		//Output as JSON
		LOG.info(new ObjectMapper().writeValueAsString(documentProfile));

	}

ACKNOWLEDGEMENTS:
Mining of entities is entirely driven by Stanford NLP engine.

"The Stanford NLP Group makes some of our Natural Language Processing software available to everyone! 
We provide statistical NLP, deep learning NLP, and rule-based NLP tools for major computational linguistics problems, 
which can be incorporated into applications with human language technology needs. These packages are widely used in industry, 
academia, and government."

http://nlp.stanford.edu/software/
http://www.gnu.org/licenses/gpl-2.0.html
