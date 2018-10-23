package LA;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

public class JavaAnalyzer extends Analyzer{
	public int maxTokenLength = 50;
	private static final List<String> stopwords = Arrays.asList("assert",
			"boolean", "break", "byte", "case", "catch", "char", "class",
			"continue", "default", "do", "double", "else", "enum", "extends",
			"final", "float", "for", "if", "implements", "import","int", "interface",
			"long", "native", "new", "package", "private", "protected", "public", "return",
			"short", "static", "super", "switch", "this", "throw", "try", "void", "while");
	
	public JavaAnalyzer() {
		
	}
	
	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		final Tokenizer source = new LetterDigitTokenizer();
		TokenStream result = new LowerCaseFilter(source);
		result = new StopFilter(result, new CharArraySet(stopwords,false));
		return new TokenStreamComponents(source,result);
	}
}
