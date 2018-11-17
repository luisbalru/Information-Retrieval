package sri;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SubsistemaCentral {
	QueryIndex index_queries;
	AnswerIndex index_answers;
	TagIndex index_tags;
	
	public SubsistemaCentral(String pathq, String pathans, String pathtags) throws IOException, ParseException {
		crearIndQ(pathq);
		crearIndA(pathans);
	}
	
	public void crearIndQ(String path) throws IOException, ParseException {
		index_queries = new QueryIndex(path);
	 	leerCSV("/home/luisbalru/Universidad/RI/data/rquestions/Questions.csv",true,false,false);
		index_queries.close();
		System.out.println("q");
	}
	
	public void crearIndA(String path) throws IOException, ParseException {
		index_answers = new AnswerIndex(path);
	//	leerCSV("/home/luisbalru/Universidad/RI/data/rquestions/Answers.csv",false,true,false);
		index_answers.close();
		System.out.println("answer");
	}
	
	/*public void crearIndT(String path) throws IOException, ParseException {
		leerCSV("/home/luisbalru/Universidad/RI/data/rquestions/Tags.csv",false,false,true);
		index_tags = new TagIndex(path);
		index_tags.close();
	}*/
	
	public void leerCSV(String path, boolean q, boolean a, boolean t) throws IOException, ParseException {
		System.out.println("entro");
		List<String[]> datos = new LinkedList<String[]>();
				if(q){
					datos = leeCSVQuery(path);
					for(String[] d : datos) {
						Query pregunta = new Query(d[0],d[1],d[2],d[3],d[4],d[5]);
						index_queries.indexDoc(pregunta);
						System.out.println("a");
					}
				}
				else if(a) {
					datos = leeCSVAnswer(path);
					for(String[] d : datos) {
						Answer respuesta = new Answer(d[0],d[1],d[2],d[3],d[4],d[5],d[6]);
						index_answers.indexDoc(respuesta);
					}
				}
				/*else {
					/*datos = leeCSVTag(path);
					for(String[] d : datos) {
						Tag etiq = new Tag(d[0],d[1]);
						ta.add(etiq);
					}*/
	}

	private List<String[]> leeCSVQuery(String path) throws IOException{
		System.out.println("entro2");
		List<String[]> lista_def = new LinkedList<String[]>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		String complete_line = "";
		while((line = br.readLine()) != null) {
			if(line.equals('\"')) {
				System.out.println("fin");
				String[] aux = line.split(",");
				String[] def = new String[6];
				def[0] = aux[0];
				def[1] = aux[1];
				def[2] = aux[2];
				def[3] = aux[3];
				def[4] = aux[4];
				String auxiliar = "";
				for(int i=5; i<aux.length; i++) {
					auxiliar = auxiliar + aux[i];
				}
				def[5] = auxiliar;
				lista_def.add(def);
			}
			else {
				complete_line = complete_line + line;
			}
		}
		br.close();
		return lista_def;
	}
	
	private List<String[]> leeCSVAnswer(String path) throws IOException{
		List<String[]> lista_def = new LinkedList<String[]>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		String complete_line = "";
		while((line = br.readLine()) != null) {
			if(line.equals('\"')) {
				String[] aux = line.split(",");
				String[] def = new String[7];
				def[0] = aux[0];
				def[1] = aux[1];
				def[2] = aux[2];
				def[3] = aux[3];
				def[4] = aux[4];
				def[5] = aux[5];
				String auxiliar = "";
				for(int i=6; i<aux.length; i++) {
					auxiliar = auxiliar + aux[i];
				}
				def[6] = auxiliar;
				lista_def.add(def);
			}
			else
				complete_line = complete_line + line;
		}
		br.close();
		return lista_def;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Dime el directorio donde crear el índice de queries");
		String path_query = sc.next();
		System.out.println("Dime el directorio donde guardar el índice de answers");
		String path_answer = sc.next();*/
		SubsistemaCentral scentral = new SubsistemaCentral("/home/luisbalru/Universidad/RI/Indices/Queries","/home/luisbalru/Universidad/RI/Indices/Answers","");
	}
	
}

