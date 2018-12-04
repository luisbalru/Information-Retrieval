package definitivo;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.codecs.lucene70.Lucene70Codec;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;

public class Index {
	protected IndexWriter writer;
	protected DirectoryTaxonomyWriter taxoWriter;

	protected void setupIndex(PerFieldAnalyzerWrapper aWrapper, Similarity similarity,
							String path, String taxopath) throws IOException{
		
		Path taxo = FileSystems.getDefault().getPath(taxopath);
		Directory taxoDir = FSDirectory.open(taxo);
		
		IndexWriterConfig iwc = new IndexWriterConfig(aWrapper);
		iwc.setCodec(new Lucene70Codec());
		iwc.setSimilarity(similarity);
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

		taxoWriter = new DirectoryTaxonomyWriter(taxoDir);
		Directory dir = FSDirectory.open(Paths.get(path));
		writer = new IndexWriter(dir,iwc);
	}

	protected void close() {
		try {
			writer.commit();
			writer.close();
			taxoWriter.commit();
			taxoWriter.close();
		} catch(IOException e) {
			System.out.println("Error closing the index.");
		}
	}
}
