package sri;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class SubsistemaCentral {
	private ArrayList<Query> queries;
	private ArrayList<Answer> answers;
	private ArrayList<Tag> tags;
	
	public SubsistemaCentral(String pathq, String pathans, String pathtags) {
		queries = new ArrayList<Query>();
		answers = new ArrayList<Answer>();
		tags = new ArrayList<Tag>();
		leerCSV(pathq,true,false,false);
		leerCSV(pathans,false,true,false);
		leerCSV(pathtags,false,false,true);
	}
	
	public void crearIndQ(String path) throws IOException, ParseException {
		QueryIndex index_queries = new QueryIndex(queries,path);
	}
	
	public void crearIndA(String path) throws IOException, ParseException {
		AnswerIndex index_ans = new AnswerIndex(answers,path);
	}
	
	public void crearIndT(String path) throws IOException, ParseException {
		TagIndex index_tag = new TagIndex(tags,path);
	}
	
	public void leerCSV(String path, boolean q, boolean a, boolean t) {
		BufferedReader br = null;
		ArrayList<Query> qu = new ArrayList<Query>();
		ArrayList<Answer> an = new ArrayList<Answer>();
		ArrayList<Tag> ta = new ArrayList<Tag>();
		String line = "";
		String csvSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null) {
				String[] datos = line.split(csvSplitBy);
				if(q){
					Query pregunta = new Query(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5]);
					qu.add(pregunta);
				}
				else if(a) {
					Answer pregunta = new Answer(datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6]);
					an.add(pregunta);
				}
				else {
					Tag etiq = new Tag(datos[0],datos[1]);
					ta.add(etiq);
				}
			}
			if(q)
				queries = qu;
			else if(a)
				answers = an;
			else
				tags = ta;
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (br != null) {
		        try {
		            br.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	}
	
}

