import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

public class App {
	
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		Buscador buscador = new Buscador("/home/luisbalru/Universidad/RI/Indices/Queries","/home/luisbalru/Universidad/RI/Indices/Answers");
		String q1="vector";
		String q2 = "matrix";
		String f1 ="body";
		String f2 ="title";
		Boolean must = true;
		Boolean should = false;
		Boolean not = false;
		int num_q = 1;
		ArrayList<Document> documentos = buscador.SearchQuery(q1, q2, f1, f2, must, should, not, num_q);
		for(int i=0; i<documentos.size(); i++) {
			System.out.println(documentos.get(i).toString());
		}
	}

}
