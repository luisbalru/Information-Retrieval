package definitivo;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;

public class AnswerIndex extends Index {
	public AnswerIndex(String path,String taxopath) throws IOException, ParseException {
		Map<String, Analyzer> analyzerPerField = new HashMap<>();
		analyzerPerField.put("body", new EnglishAnalyzer());
		analyzerPerField.put("code", new WhitespaceAnalyzer());
		
		PerFieldAnalyzerWrapper aWrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), 
																		analyzerPerField);
		Similarity similarity = new BM25Similarity();
		setupIndex(aWrapper, similarity, path,taxopath);
	}
	
	public void indexDoc(Answer q) throws ParseException, IOException {
			Document doc = new Document();
			FacetsConfig config = new FacetsConfig();
			
			doc.add(new StringField("ID-a", q.getID_a(),Field.Store.YES));
			doc.add(new StringField("ID-q",q.getID_q(),Field.Store.YES));
			doc.add(new FacetField("ID-q",q.getID_q()));
			doc.add(new StringField("ID-user", q.getID_user(), Field.Store.YES));
			//Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(q.getDate());
			doc.add(new StringField("date",q.getDate(),Field.Store.NO));
			doc.add(new StringField("puntuacion", q.getPuntuacion(),Field.Store.YES));
			doc.add(new IntPoint("punt-num",Integer.parseInt(q.getPuntuacion())));
			doc.add(new StoredField("punt-num",Integer.parseInt(q.getPuntuacion())));
			doc.add(new NumericDocValuesField("punt-num",22L));
			//doc.add(new SortedNumericDocValuesField("punt-num",Integer.parseInt(q.getPuntuacion())));
			//doc.add(new FacetField("punt-num",q.getPuntuacion()));
			doc.add(new StringField("aceptada", q.getAceptada(), Field.Store.YES));
			doc.add(new FacetField("aceptada",q.getAceptada()));
			doc.add(new TextField("body",q.getBody(), Field.Store.YES));
			doc.add(new TextField("codes", q.getCodes(),Field.Store.YES));
			
			writer.addDocument(config.build(taxoWriter,doc));
	}
}
