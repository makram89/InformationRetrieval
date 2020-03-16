import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenNLP {

    public static String LANG_DETECT_MODEL = "models/langdetect-183.bin";
    public static String TOKENIZER_MODEL = "models/en-token.bin";
    public static String SENTENCE_MODEL = "models/en-sent.bin";
    public static String POS_MODEL = "models/en-pos-maxent.bin";
    public static String LEMMATIZER_DICT = "models/en-lemmatizer.dict";
    public static String CHUNKER_MODEL = "models/en-chunker.bin";
    public static String NAME_MODEL = "models/en-ner-person.bin";
    public static String ENTITY_XYZ_MODEL = "models/en-ner-xxx.bin";

    public static void main(String[] args) throws IOException {
        OpenNLP openNLP = new OpenNLP();
        openNLP.run();
    }

    public void run() throws IOException {

//		languageDetection();
//        tokenization();
//         sentenceDetection();
//         posTagging();
//         lemmatization();
//         stemming();
//         chunking();
         nameFinding();
    }

    private void languageDetection() throws IOException {
//        Confidence also depends one the length of the sentence.
//        But mostly, when we add another language, confidence decreases.
        File modelFile = new File(LANG_DETECT_MODEL);
        LanguageDetectorModel model = new LanguageDetectorModel(modelFile);
        LanguageDetectorME modelME = new LanguageDetectorME(model);
        String text = "";

//		text = "cats";
        // text = "cats like milk";
//		 text = "Many cats like milk because in some ways it reminds them of their  mother's milk.";
        text = "The two things are not really related. Many cats like milk because in some ways it reminds them of their mother's milk.";
//		text = "The two things are not really related. Many cats like milk because in some ways it reminds them of their mother's milk. "
//				+ "It is rich in fat and protein. They like the taste. They like the consistency . "
//				+ "The issue as far as it being bad for them is the fact that cats often have difficulty digesting milk and so it may give them "
//				+ "digestive upset like diarrhea, bloating and gas. After all, cow's milk is meant for baby calves, not cats. "
//				+ "It is a fortunate quirk of nature that human digestive systems can also digest cow's milk. But humans and cats are not cows.";
//		 text = "Many cats like milk because in some ways it reminds them of their"+
//		 "mother's milk. Le lait n'est pas forc�ment mauvais pour les chats";
//		 text = "Many cats like milk because in some ways it reminds them of their"
//		 +"mother's milk. Le lait n'est pas forc�ment mauvais pour les chats. "
//		 + "Der Normalfall ist allerdings der, dass Salonl�wen Milch weder brauchen"
//		 +"noch gut verdauen können.";

        Language language = modelME.predictLanguage(text);
        System.out.println(language.getLang());
        System.out.println(language.getConfidence());
    }

    private void tokenization() throws IOException {

        File modelFile = new File(TOKENIZER_MODEL);
        TokenizerModel model = new TokenizerModel(modelFile);
        TokenizerME modelME = new TokenizerME(model);

        String text = "";

        text = "Since cats were venerated in ancient Egypt, they were commonly believed to have been domesticated there, "
                + "but there may have been instances of domestication as early as the Neolithic from around 9500 years ago (7500 BC).";
//        text = "Since cats were venerated in ancient Egypt, they were commonly believed to have been domesticated there, "
//                + "but there may have been instances of domestication as early as the Neolithic from around 9,500 years ago (7,500 BC).";
//        text = "Since cats were venerated in ancient Egypt, they were commonly believed to have been domesticated there, "
//                + "but there may have been instances of domestication as early as the Neolithic from around 9 500 years ago ( 7 500 BC).";


        String[] output = modelME.tokenize(text);
        double[] probabilities = modelME.getTokenProbabilities();
//        for (String word : output) {
//            System.out.println(word);
//        }
//        for (double p : probabilities) {
//            System.out.println(p);
//        }
    }

    private void sentenceDetection() throws IOException {
//      Sentence "Hi." is merged with next sentence
//      The "(..)We provide multiple . built-in methods for Natural (...)" fragment gets incorrectly splitted output
//        Multiply !! with spaces could give sentence like one symbol ex. "!"
        File modelFile = new File(SENTENCE_MODEL);
        SentenceModel model = new SentenceModel(modelFile);
        SentenceDetectorME modelME = new SentenceDetectorME(model);

        String text = "";
//        text = "Hi. How are you? Welcome to OpenNLP. "
//                + "We provide multiple built-in methods for Natural Language Processing.";
//		text = "Hi. How are you?! Welcome to OpenNLP? "
//				+ "We provide multiple built-in methods for Natural Language Processing.";

//		text = "Hi. How are you? Welcome to OpenNLP.?? 1!!! 1 ! ! "
//				+ "We provide multiple . built-in methods for Natural Language Processing.???";
//		text = "The interrobang, also known as the interabang (often represented by ?! or !?), "
//				+ "is a nonstandard punctuation mark used in various written languages. "
//				+ "It is intended to combine the functions of the question mark (?), or interrogative point, "
//				+ "and the exclamation mark (!), or exclamation point, known in the jargon of printers and programmers as a \"bang\". ";
        String[] output =  modelME.sentDetect(text);
        for (String word : output) {
            System.out.println(word);
        }

    }

    private void posTagging() throws IOException {
//        "Like" is in both examples tagged as 'IN', but in first sentence that is incorrect.

        File modelFile = new File(POS_MODEL);
        POSModel model = new POSModel(modelFile);
        POSTaggerME modelME = new POSTaggerME(model);

        String[] sentence = new String[0];
        sentence = new String[]{"Cats", "like", "milk"};
		sentence = new String[]{"Cat", "is", "white", "like", "milk"};
//		sentence = new String[] { "Hi", "How", "are", "you", "Welcome", "to", "OpenNLP", "We", "provide", "multiple",
//				"built-in", "methods", "for", "Natural", "Language", "Processing" };
//		sentence = new String[] { "She", "put", "the", "big", "knives", "on", "the", "table" };
		var output = modelME.tag(sentence);
        for (var x: output) {
            System.out.println(x);

        }
    }

    private void lemmatization() throws IOException {
//        With incorrect tag it make output 'O'
//        Lemmetizer use tag to get context of a word and to identify it properly.
//        Are ones become 'be' and ones become 'ar'

        File modelFile = new File(LEMMATIZER_DICT);
        DictionaryLemmatizer dictionaryLemmatizer = new DictionaryLemmatizer(modelFile);

        String[] text = new String[0];
        text = new String[]{"Hi", "How", "are", "you", "Welcome", "to", "OpenNLP", "We", "provide", "multiple",
                "built-in", "methods", "for", "Natural", "Language", "Processing"};
        String[] tags = new String[0];
        tags = new String[]{"NN", "WRB", "VBP", "PRP", "VB", "TO", "VP", "PRP", "VB", "JJ", "JJ", "NNS", "IN", "JJ",
                "NN", "VBG"};

        String[] lemmatize = dictionaryLemmatizer.lemmatize(text, tags);
        System.out.println(Arrays.asList(lemmatize));

    }

    private void stemming() {
        File modelFile = new File(LEMMATIZER_DICT);
        PorterStemmer model = new PorterStemmer();

        String[] sentence = new String[0];
        sentence = new String[]{"Hi", "How", "are", "you", "Welcome", "to", "OpenNLP", "We", "provide", "multiple",
                "built-in", "methods", "for", "Natural", "Language", "Processing"};
        ArrayList result = new ArrayList();
        for (var word:sentence) {
            result.add(model.stem(word));
        }
        System.out.println(result);

    }

    private void chunking() throws IOException {
//        I- means "inside the chunk"
//        B- means "inside the chunk, preceding word is part of different chunk"
//        I see at least 3 chunks: "She put, | the big knives, | on the table"

        File modelFile = new File(CHUNKER_MODEL);
        ChunkerModel model = new ChunkerModel(modelFile);
        ChunkerME chunkerME = new ChunkerME(model);

        String[] sentence = new String[0];
        sentence = new String[]{"She", "put", "the", "big", "knives", "on", "the", "table"};

        String[] tags = new String[0];
        tags = new String[]{"PRP", "VBD", "DT", "JJ", "NNS", "IN", "DT", "NN"};

        String[] output = chunkerME.chunk(sentence,tags);
        System.out.println(Arrays.asList(output));

    }

    private void nameFinding() throws IOException {
//      with en-ner-person.bin some "names" were incorrect.
//      Title of film and The were interpreted incorrectly, probably because of capital letters

//      en-ner-xxx is looking for dates

//        File modelFile = new File(NAME_MODEL);
        File modelFile = new File(ENTITY_XYZ_MODEL);
        TokenNameFinderModel model = new TokenNameFinderModel(modelFile);
        NameFinderME nameFinderME = new NameFinderME(model);

        String text = "he idea of using computers to search for relevant pieces of information was popularized in the article "
                + "As We May Think by Vannevar Bush in 1945. It would appear that Bush was inspired by patents "
                + "for a 'statistical machine' - filed by Emanuel Goldberg in the 1920s and '30s - that searched for documents stored on film. "
                + "The first description of a computer searching for information was described by Holmstrom in 1948, "
                + "detailing an early mention of the Univac computer. Automated information retrieval systems were introduced in the 1950s: "
                + "one even featured in the 1957 romantic comedy, Desk Set. In the 1960s, the first large information retrieval research group "
                + "was formed by Gerard Salton at Cornell. By the 1970s several different retrieval techniques had been shown to perform "
                + "well on small text corpora such as the Cranfield collection (several thousand documents). Large-scale retrieval systems, "
                + "such as the Lockheed Dialog system, came into use early in the 1970s.";


        File modelFile2 = new File(TOKENIZER_MODEL);
        TokenizerModel model2 = new TokenizerModel(modelFile2);
        TokenizerME modelME = new TokenizerME(model2);

        var tokened = modelME.tokenize(text);

        var output = nameFinderME.find(tokened);
        System.out.println(Arrays.asList(output));
//        Dates
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,25,26)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,48,49)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,74,75)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,93,94)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,100,101)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,109,110)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,128,129)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,171,172)));



//        Names
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,22,24)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,31,32)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,44,46)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,60,61)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,104,106)));
//        System.out.println(Arrays.asList(Arrays.copyOfRange(tokened,121,123)));

    }

}
