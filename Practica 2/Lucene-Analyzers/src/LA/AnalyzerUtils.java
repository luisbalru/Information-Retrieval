package LA;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;


import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardTokenizer;

public class AnalyzerUtils {
	String contenido;
	public static final Analyzer [] analizadores = {
            new WhitespaceAnalyzer(),
            new SimpleAnalyzer(),
            new StopAnalyzer(),
            new StandardAnalyzer( ),
            new UAX29URLEmailAnalyzer()
            };
	
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

}
