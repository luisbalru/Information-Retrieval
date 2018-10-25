package LA;

import java.io.IOException;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class NumberFilter extends FilteringTokenFilter{
	
	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	
	public NumberFilter(TokenStream in) {
		super(in);
	}
	
	@Override
	protected boolean accept() throws IOException{
		String token = new String(termAtt.buffer(),0,termAtt.length());
		if(token.matches("[0-9]+")) {
			return false;
		}
		return true;
	}
}
