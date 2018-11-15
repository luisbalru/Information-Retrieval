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

public class AnswerIndex extends Index {
	public AnswerIndex(ArrayList<Answer> answers, String path) throws IOException, ParseException {
		Map<String, Analyzer> analyzerPerField = new HashMap<>();
		analyzerPerField.put("body", new StandardAnalyzer());
		analyzerPerField.put("code", new WhitespaceAnalyzer());
		
		PerFieldAnalyzerWrapper aWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), 
																		analyzerPerField);
		Similarity similarity = new ClassicSimilarity();
		setupIndex(aWrapper, similarity, path);
		indexDoc(answers);
		close();
	}
	
	public void indexDoc(ArrayList<Answer> answers) throws ParseException, IOException {
		for(Answer q : answers) {
			Document doc = new Document();
			doc.add(new IntPoint("ID-a", Integer.parseInt(q.getID_a())));
			doc.add(new IntPoint("ID-q",Integer.parseInt(q.getID_q())));
			doc.add(new IntPoint("ID-user", Integer.parseInt(q.getID_user())));
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(q.getDate());
			doc.add(new LongPoint("date",date.getTime()));
			doc.add(new IntPoint("puntuacion", Integer.parseInt(q.getPuntuacion())));
			doc.add(new StringField("aceptada", q.getAceptada(), Field.Store.YES));
			doc.add(new TextField("body",q.getBody(), Field.Store.YES));
			doc.add(new TextField("codes", q.getCodes(),Field.Store.YES));
			
			writer.addDocument(doc);
		}
	}
}
