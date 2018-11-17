package sri;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Index {
	protected IndexWriter writer;
	
	protected void setupIndex(PerFieldAnalyzerWrapper aWrapper, Similarity similarity,
							String path) throws IOException{
		
		IndexWriterConfig iwc = new IndexWriterConfig(aWrapper);
		iwc.setSimilarity(similarity);
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		
		Directory dir = FSDirectory.open(Paths.get(path));
		writer = new IndexWriter(dir,iwc);
	}
	
	protected void close() {
		try {
			writer.commit();
			writer.close();
		} catch(IOException e) {
			System.out.println("Error closing the index.");
		}
	}
}
