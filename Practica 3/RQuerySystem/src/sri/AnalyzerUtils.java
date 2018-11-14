package sri;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AnalyzerUtils {
	@SuppressWarnings("deprecation")
	public static final Analyzer [] analizadores = {
            new WhitespaceAnalyzer(),
            new SimpleAnalyzer(),
            new StopAnalyzer(),
            new StandardAnalyzer( ),
            new UAX29URLEmailAnalyzer()
            };
	
	public static List<String> tokenizeString(Analyzer analyzer, String string) {
	    List<String> result = new ArrayList<String>();

	    try {
	      TokenStream stream  = analyzer.tokenStream(null, new StringReader(string));
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

}
