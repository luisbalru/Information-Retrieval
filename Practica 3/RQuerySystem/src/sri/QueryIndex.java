package sri;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

public class QueryIndex extends Index {
	public QueryIndex(String path) throws IOException, ParseException {
		Map<String, Analyzer> analyzerPerField = new HashMap<>();
		analyzerPerField.put("title", new StandardAnalyzer());
		analyzerPerField.put("body", new StandardAnalyzer());
		analyzerPerField.put("code", new WhitespaceAnalyzer());
		PerFieldAnalyzerWrapper aWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), 
																		analyzerPerField);
		Similarity similarity = new ClassicSimilarity();
		setupIndex(aWrapper, similarity, path);
	}

	
	public void indexDoc(Query q) throws ParseException, IOException {
		Document doc = new Document();
		doc.add(new IntPoint("ID-q",Integer.parseInt(q.getID_q())));
		doc.add(new StringField("ID-q", q.getID_q(), Field.Store.YES));
		doc.add(new StringField("ID-user", q.getID_user(),Field.Store.YES));
		Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(q.getDate());
		doc.add(new LongPoint("date",date.getTime()));
		doc.add(new StringField("rate", q.getRate(), Field.Store.YES));
		doc.add(new TextField("title",q.getTitle(), Field.Store.YES));
		doc.add(new TextField("body",q.getBody(), Field.Store.YES));
		doc.add(new TextField("codes", q.getCodes(),Field.Store.YES));
			
		writer.addDocument(doc);

	}
}
