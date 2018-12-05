package searcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Buscador {
	private Path queries_path;
	private Path answers_path;
	private Path tags_path;
	private Path taxo_path;
	
	public Buscador(String q_path, String ans_path,String tag_path, String t_path) {
		queries_path = FileSystems.getDefault().getPath(q_path);
		answers_path = FileSystems.getDefault().getPath(ans_path);
		tags_path = FileSystems.getDefault().getPath(tag_path);
		taxo_path = FileSystems.getDefault().getPath(t_path);
	}
	
	public ArrayList<Document> SearchAnswers(String idq) throws IOException{
		ArrayList<Document> answers = new ArrayList<Document>();
		Directory dir = FSDirectory.open(answers_path);
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		
		Query q1 = new TermQuery(new Term("ID-q",idq));
		Query q2 = new TermQuery(new Term("aceptada","TRUE"));
		
		SortField sf = new SortField("punt-num",SortField.Type.INT,true);
		//sf.setMissingValue();
		Sort orden = new Sort(sf);
		
		BooleanQuery.Builder cons = new BooleanQuery.Builder();
		cons.add(q1,BooleanClause.Occur.MUST);
		cons.add(q2,BooleanClause.Occur.MUST);
		BooleanQuery q = cons.build();
		TopDocs hits = isearcher.search(q, 5,orden);
		for(int i=0; i<hits.scoreDocs.length; i++) {
			Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
			answers.add(hitDoc);
		}
		ireader.close();
		dir.close();
		
		return answers;
	}
	
	public ArrayList<Document> SearchbyFacet(String q,int num_resultados) throws IOException{
		ArrayList<Document> facets = new ArrayList<Document>();
		Directory dir = FSDirectory.open(tags_path);
		Directory taxo_dir = FSDirectory.open(taxo_path);
		DirectoryReader indexReader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		TaxonomyReader taxoReader = new DirectoryTaxonomyReader(taxo_dir);
		FacetsCollector fc = new FacetsCollector();
		Term t = new Term("tag",q);
		Query qu = new TermQuery(t);
		TopDocs tdc = FacetsCollector.search(searcher, qu, num_resultados, fc);
		
		
		Directory dir1 = FSDirectory.open(queries_path);
		DirectoryReader indexReader1 = DirectoryReader.open(dir1);
		IndexSearcher searcher1 = new IndexSearcher(indexReader1);
		
		for(ScoreDoc sd : tdc.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			Query new_q = new TermQuery(new Term("ID-q",d.get("ID")));
			BooleanQuery.Builder cons = new BooleanQuery.Builder();
			cons.add(new_q,BooleanClause.Occur.MUST);
			BooleanQuery bq = cons.build();
			TopDocs hits = searcher1.search(bq, 1);
			Document hitDoc = searcher1.doc(hits.scoreDocs[0].doc);
			facets.add(hitDoc);
		}
		
		indexReader.close();
		dir.close();
		taxo_dir.close();
		indexReader1.close();
		dir1.close();
		return facets;
	}
	
	public ArrayList<Document> SearchQuery(String q1, String q2, String f1, 
			String f2, boolean must, boolean should, boolean not, int num_q) throws IOException, ParseException {
		ArrayList<Document> queries = new ArrayList<Document>();	
		ArrayList<Document> answers = new ArrayList<Document>();
		Directory dir = FSDirectory.open(queries_path);
		IndexReader ireader = DirectoryReader.open(dir);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		
		QueryParser parser1 = new QueryParser(f1,new EnglishAnalyzer());
		Query query1 = parser1.parse(q1);
		QueryParser parser2 = new QueryParser(f2,new EnglishAnalyzer());
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
		TopDocs hits = isearcher.search(q, num_q);
		for(int i=0; i<hits.scoreDocs.length; i++) {
			Document hitDoc = isearcher.doc(hits.scoreDocs[i].doc);
			queries.add(hitDoc);
			answers = SearchAnswers(hitDoc.getField("ID-q").stringValue());
			for(int j=0; j<answers.size();j++) {
				queries.addAll(answers);
			}
		}
		ireader.close();
		dir.close();
		
		return queries;
	}
	

}
