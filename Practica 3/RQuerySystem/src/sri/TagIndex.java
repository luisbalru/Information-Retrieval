package definitivo;

import java.io.IOException;
import java.text.ParseException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;

public class TagIndex extends Index {
	public TagIndex(String path,String taxopath) throws IOException, ParseException {
		PerFieldAnalyzerWrapper aWrapper = new PerFieldAnalyzerWrapper(new WhitespaceAnalyzer());
		Similarity similarity = new BM25Similarity();
		setupIndex(aWrapper, similarity, path,taxopath);
	}
	public void indexDoc(Tag t) throws ParseException, IOException {
		Document doc = new Document();
		FacetsConfig config = new FacetsConfig();
		
		doc.add(new StringField("ID", t.getID_q(),Field.Store.YES));
		doc.add(new StringField("tag", t.getSet_tags(),Field.Store.YES));
		doc.add(new FacetField("tag",t.getSet_tags()));
		
		
		writer.addDocument(config.build(taxoWriter,doc));
	}
	
}
