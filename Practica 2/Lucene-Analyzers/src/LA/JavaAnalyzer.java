package LA;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;

public class JavaAnalyzer extends Analyzer{
	public int maxTokenLength = 50;
	private static final List<String> stopwords = (List<String>) Arrays.asList("assert",
			"boolean", "break", "byte", "case", "catch", "char", "class",
			"continue", "default", "do", "double", "else", "enum", "extends",
			"final", "float", "for", "if", "implements", "import","int", "interface",
			"long", "native", "new", "package", "private", "protected", "public", "return",
			"short", "static", "super", "switch", "this", "throw", "try", "void", "while");
	
	public JavaAnalyzer() {
		
	}
	

	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		// TODO Auto-generated method stub
		
		
		return null;
	}
}
