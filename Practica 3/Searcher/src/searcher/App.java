package searcher;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

public class App {
	
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		Buscador buscador = new Buscador("/home/luisbalru/Universidad/RI/Indices/Queries","/home/luisbalru/Universidad/RI/Indices/Answers",
				"/home/luisbalru/Universidad/RI/Indices/Tags","/home/luisbalru/Universidad/RI/Indices/TaxoT");
		String q1="vector";
		String q2 = "R";
		String f1 ="body";
		String f2 ="title";
		String resultado = "";
		ArrayList<Document> facetas = buscador.SearchbyFacet("vector", 5);
		/*for(int i=0; i<facetas.size();i++) {
			if(facetas.get(i).get("ID-a")==null)
				resultado = resultado + "ID-q: " + facetas.get(i).get("ID-q") + " ID-user: " + facetas.get(i).get("ID-user") + " Rate: " + facetas.get(i).get("rate") + " Title: " + facetas.get(i).get("title") + " Body: " + facetas.get(i).get("body") + "\n";
		}	
		System.out.println(resultado);*/
		Boolean must = true;
		Boolean should = false;
		Boolean not = false;
		int num_q = 20;
		/*ArrayList<Document> documentos = buscador.SearchQuery(q1, q2, f1, f2, must, should, not, num_q);
		for(int i=0; i<documentos.size(); i++) {
			System.out.println(documentos.get(i).toString());
		}*/
	}

}
