import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Buscador {
	private Path queries_path;
	private Path answers_path;
	
	public Buscador(String q_path, String ans_path) {
		queries_path = FileSystems.getDefault().getPath(q_path);
		answers_path = FileSystems.getDefault().getPath(ans_path);
	}
	
	public ArrayList<Document> SearchQuery(String q1, String q2, String f1, 
			String f2, boolean must, boolean should, boolean not, int num_q) throws IOException, ParseException {
		ArrayList<Document> queries = new ArrayList<Document>();	
		Directory dir = FSDirectory.open(queries_path);
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);

		QueryParser parser1 = new QueryParser(f1,new StandardAnalyzer());
		Query query1 = parser1.parse(q1);
		QueryParser parser2 = new QueryParser(f2,new StandardAnalyzer());
		Query query2 = parser2.parse(q2);
		
		BooleanQuery.Builder Cons = new BooleanQuery.Builder();
		Cons.add(query1, BooleanClause.Occur.MUST);
		if(must)
			Cons.add(query2,BooleanClause.Occur.MUST);
		else if(should)
			Cons.add(query2,BooleanClause.Occur.SHOULD);
		else if(not)
			Cons.add(query2,BooleanClause.Occur.MUST_NOT);
		
		BooleanQuery q = Cons.build();
		System.out.println(q);
		TopDocs hits = isearcher.search(q, num_q);
		for(int i=0; i<hits.scoreDocs.length; i++) {
			Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
			queries.add(hitDoc);
		}
		ireader.close();
		dir.close();
		
		return queries;
	}
}
