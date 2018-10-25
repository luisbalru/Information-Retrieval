package LA;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
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
			"short", "static", "super", "switch", "this", "throw", "try", "void", "while", "String", "ArrayList",
			"File");
	
	public JavaAnalyzer() {
		
	}
	
	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		final Tokenizer source = new LetterDigitTokenizer();
		TokenStream result = new StopFilter(source, new CharArraySet(stopwords,true));
		return new TokenStreamComponents(source,result);
	}
	
	public static List<String> tokenizeString(Analyzer analyzer, String string) {
	    List<String> result = new ArrayList<String>();

	    String cad;
	    try {
	      TokenStream stream  = analyzer.tokenStream(null, new StringReader(string));
	      OffsetAttribute offsetAtt = stream.addAttribute(OffsetAttribute.class);
	      CharTermAttribute cAtt= stream.addAttribute(CharTermAttribute.class);
	      stream.reset();
	      while (stream.incrementToken()) {
	        result.add( cAtt.toString());
	      }
	      stream.end();
	    } catch (IOException e) {
	      // not thrown b/c we're using a string reader...
	      throw new RuntimeException(e);
	    }
	    return result;
	  }
	
	  public static void main(String[] args) {
		  String s = "public ArrayList<String> getPaths(String dir){\n" + 
		  		"     File file = null;\n" + 
		  		"     ArrayList<String> paths = new ArrayList<String>();\n" + 
		  		"     String[] paths_aux = null;\n" + 
		  		"\n" + 
		  		"     try{\n" + 
		  		"       // create new file object\n" + 
		  		"       file = new File(dir);\n" + 
		  		"\n" + 
		  		"       String aux = file.getAbsolutePath();\n" + 
		  		"       paths_aux = file.list();";
		  List<String> split = tokenizeString(new JavaAnalyzer(),s);
		  for(String s1 : split) {
			  System.out.println(s1);
		  }
	  }
}
