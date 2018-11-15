package sri;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

public class TagIndex extends Index {
	public TagIndex(ArrayList<Tag> tags, String path) throws IOException, ParseException {
		PerFieldAnalyzerWrapper aWrapper = new PerFieldAnalyzerWrapper(new WhitespaceAnalyzer());
		Similarity similarity = new ClassicSimilarity();
		setupIndex(aWrapper, similarity, path);
		indexDoc(tags);
		close();
	}
	public void indexDoc(ArrayList<Tag> tags) throws ParseException, IOException {
		for(Tag q : tags) {
			Document doc = new Document();
			doc.add(new IntPoint("ID-tag",Integer.parseInt(q.getID_q())));
			doc.add(new StringField("tag",q.getSet_tags(),Field.Store.YES));
			writer.addDocument(doc);
		}
	}
	
}
