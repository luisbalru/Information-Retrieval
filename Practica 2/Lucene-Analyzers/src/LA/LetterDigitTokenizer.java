package LA;

import org.apache.lucene.analysis.core.LetterTokenizer;

public class LetterDigitTokenizer extends LetterTokenizer {
	
	public LetterDigitTokenizer() {
		
	}
	
	/** Collects only characters with satisfy 
	 *  {@link Character#isLetterOrDigit(int)}
	 */
	@Override
	protected boolean isTokenChar(int c) {
		return Character.isLetterOrDigit(c);
	}

}
