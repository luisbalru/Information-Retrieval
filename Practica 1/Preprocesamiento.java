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
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.Link;
import org.xml.sax.SAXException;


public class Preprocesamiento{

  public static void imprimeDatos(ArrayList<Fichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
	  PrintWriter writer = new PrintWriter("datoslibros.md", "UTF-8");

	    for(int i=0; i<ficheros.size(); i++){
	    	writer.println("Nombre::"+ ficheros.get(i).getName() +" Tipo::"+ ficheros.get(i).getType() +
					" Charset::"+ ficheros.get(i).getCharset() +"  Idioma::" + ficheros.get(i).getLang());
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

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    FileExtraction extract = new FileExtraction();
    ArrayList<String> paths = extract.getPaths(args[0]);
    ArrayList<Fichero> ficheros = new ArrayList<Fichero>();


    /*for(String path : paths){
      System.out.println(path);
      Fichero f = new Fichero(path);
      ficheros.add(f);
    }*/
    for(int i=1; i<paths.size(); i++){
    	Fichero f = new Fichero(paths.get(0) + "/" + paths.get(i));
    	ficheros.add(f);
    }
    imprimeDatos(ficheros);
    imprimeLinks(ficheros);
  }
}
