package LA;

/*
 * Definición del nuevo T.D.A: Fichero
 */

/*
 * @author Luis Balderas Ruiz
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.tika.Tika;
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.sax.TeeContentHandler;
import org.xml.sax.SAXException;

public class Fichero{
  String name;
  ArrayList<String> metadata;
  String language;
  String type;
  String charset;
  String content;
  static ArrayList<HashMap<String,Integer>> word_count;
  ArrayList<List> lista;
  String directorio;
  
  public Fichero() {}

  public Fichero(String path) throws IOException, SAXException, TikaException{
    Tika tika = new Tika();
    File f = new File(path);
    name = f.getName();
    type = tika.detect(f);
    charset = setCharset(f,tika);
    metadata = new ArrayList<String>();
    lista = new ArrayList<List>();
    word_count = new ArrayList<HashMap<String, Integer> >();
    for(int i=0; i<6; i++) {
    	HashMap<String, Integer> aux = new HashMap<String,Integer>();
    	word_count.add(aux);
    }
	getContent_Metadata(f);
	cuentaPalabras();
    language = identifyLanguage(content);
    for(int i=0; i<word_count.size();i++)
    	lista.add(ordena(i));
  }

  private String setCharset(File f, Tika tika) throws FileNotFoundException{
	FileInputStream inputstream = new FileInputStream(f);
	AutoDetectReader detector = null;
	try {
		detector = new AutoDetectReader(inputstream);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TikaException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Charset charSet = detector.getCharset();
    return charSet.toString();
  }


  public void getContent_Metadata(File f) throws IOException, SAXException, TikaException{
	  // Elimina el límite de contenido
	FileInputStream inputstream = new FileInputStream(f);
	Parser parser = new AutoDetectParser();
    BodyContentHandler handler = new BodyContentHandler(-1);
    LinkContentHandler link_handler = new LinkContentHandler();
    TeeContentHandler teeHandler = new TeeContentHandler(link_handler,handler);
    Metadata met = new Metadata();
    ParseContext context = new ParseContext();
    parser.parse(inputstream, teeHandler, met, context);
    inputstream.close();
    content = handler.toString();
    String[] metadataNames = met.names();
    String aux;
    for(String name : metadataNames){
      aux = name + ": " + met.get(name);
      metadata.add(aux);
    }
  }
  private String identifyLanguage(String text) throws IOException{
	  LanguageDetector identifier  = new  OptimaizeLangDetector().loadModels();
      LanguageResult idioma = identifier.detect(text);
      return idioma.getLanguage();
      
  }

  public void cuentaPalabras() {
	  int j = AnalyzerUtils.analizadores.length+1;
	  for(int i=0; i<j; i++) {
		  List<String> split;
		  if(i==AnalyzerUtils.analizadores.length) {
			  if(language == "es")
				  split = AnalyzerUtils.tokenizeString(new SpanishAnalyzer(), content);
			  else if(language == "en")
				  split = AnalyzerUtils.tokenizeString(new EnglishAnalyzer(), content);
			  else if(language == "pt")
				  split = AnalyzerUtils.tokenizeString(new PortugueseAnalyzer(), content);
			  else if(language == "fr")
				  split = AnalyzerUtils.tokenizeString(new FrenchAnalyzer(), content);
			  else if(language == "it")
				  split = AnalyzerUtils.tokenizeString(new ItalianAnalyzer(), content);
			  else
				  split = AnalyzerUtils.tokenizeString(new GermanAnalyzer(), content);
		  }
		  else 
			  split = AnalyzerUtils.tokenizeString(AnalyzerUtils.analizadores[i], content);

		  for(String st : split) {
				  if(word_count.get(i).containsKey(st)) {
					  int num = word_count.get(i).get(st) + 1;
					  word_count.get(i).put(st, num);
				  }
				  else
					  word_count.get(i).put(st, 1);
		  }
	  }
  }
  
  public static List ordena(int i) {
	  List lista = new LinkedList(word_count.get(i).entrySet());
	  Collections.sort(lista, new Comparator() {
		  	public int compare(Object o1, Object o2) {
		  		return ((Comparable) (((Map.Entry) (o1)).getValue())).compareTo(((Map.Entry) (o2)).getValue());
		  	}
	  });
	  return lista;
  }
  
  public HashMap<String, Integer> getPalabras(int i){
	  return word_count.get(i);
  }

  	
  public String getName(){
    return name;
  }

  public String getType(){
    return type;
  }

  public String getLang(){
    return language;
  }

  public String getCharset(){
    return charset;
  }

  public String getContent(){
    return content;
  }

  public ArrayList<String> getMetadata(){
    return metadata;
  }  
}