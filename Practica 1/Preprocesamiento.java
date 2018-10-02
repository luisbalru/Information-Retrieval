/**
 * Clase principal de la práctica 1, dedicada al preprocesamiento
 */

/**
 * @author Luis Balderas Ruiz
 */

package preprocessing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.Link;
import org.xml.sax.SAXException;


public class Preprocesamiento{
	
  public static void imprimeDatos(ArrayList<Fichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
	  PrintWriter writer = new PrintWriter("datoslibros.md", "UTF-8");
	    
	    for(int i=0; i<ficheros.size(); i++){
	    	writer.println("| NOMBRE | TIPO | CHARSET | IDIOMA |");
	    	writer.println("|--------|--------|---------|---------|");
	    	writer.println(" | "+ ficheros.get(i).getName() +" | "+ ficheros.get(i).getType() +
					" | "+ ficheros.get(i).getCharset() +" | " + ficheros.get(i).getLang()+" | " );
	    }
	    writer.close();
  }
  
  public static void imprimeLinks(ArrayList<Fichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
	  for(int i=0; i<ficheros.size(); i++) {
	  	PrintWriter writer = new PrintWriter("links_" + ficheros.get(i).getName()+".txt", "UTF-8");
	  	List<Link> links = ficheros.get(i).getLinks();
	  	for(Link enlace : links)
	  		writer.println(enlace.toString());
	  }	
  }
  
  // Función encontrada en Stackoverflow para acelerar la ordenación de tantas palabras
  private static List ordena(HashMap<String, Integer> m) {
	  List lista = new LinkedList(m.entrySet());
	  Collections.sort(lista, new Comparator() {
		  	public int compare(Object o1, Object o2) {
		  		return ((Comparable) (((Map.Entry) (o1)).getValue())).compareTo(((Map.Entry) (o2)).getValue());
		  	}
	  });
	  
	  return lista;
  }
  
  public static void cuentaPalabras(ArrayList<Fichero> f) throws FileNotFoundException, UnsupportedEncodingException {
	  List list;
	  for(int i=0; i<f.size(); i++) {
		  PrintWriter writer = new PrintWriter("word_count_" + f.get(i).getName() + ".txt", "UTF-8");
		  list = ordena(f.get(i).getPalabras());
		  Iterator it = new ReverseListIterator(list);
		  while(it.hasNext()) {
			  writer.println(it.next().toString());
		  }
		  writer.close();
	  }
  }

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    FileExtraction extract = new FileExtraction();
    ArrayList<String> paths = extract.getPaths(args[0]);
    ArrayList<Fichero> ficheros = new ArrayList<Fichero>();
    	
    /*for(int i=1; i<paths.size(); i++){
    	Fichero f = new Fichero(paths.get(0) + "/" + paths.get(i));
    	ficheros.add(f);
    }*/
    
    Fichero f = new Fichero(paths.get(0) + "/" + paths.get(9));
	ficheros.add(f);
    imprimeDatos(ficheros);
    imprimeLinks(ficheros);
    cuentaPalabras(ficheros);
  }
}

