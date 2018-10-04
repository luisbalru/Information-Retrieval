/**
 * Clase principal de la práctica 1, dedicada al preprocesamiento
 */

/**
 * @author Luis Balderas Ruiz
 */

package preprocessing;

import java.io.File;
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
import java.util.Scanner;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.apache.tika.exception.TikaException;
import org.apache.tika.sax.Link;
import org.xml.sax.SAXException;


public class Preprocesamiento{
	
  public static void imprimeDatos(ArrayList<Fichero> ficheros,String path) throws FileNotFoundException, UnsupportedEncodingException {
	  PrintWriter writer = new PrintWriter(path+"/datoslibros.md", "UTF-8");
	    
	    for(int i=0; i<ficheros.size(); i++){
	    	writer.println("| NOMBRE | TIPO | CHARSET | IDIOMA |");
	    	writer.println("|--------|--------|---------|---------|");
	    	writer.println(" | "+ ficheros.get(i).getName() +" | "+ ficheros.get(i).getType() +
					" | "+ ficheros.get(i).getCharset() +" | " + ficheros.get(i).getLang()+" | " );
	    }
	    writer.close();
  }
  
  public static void imprimeLinks(ArrayList<Fichero> ficheros, String path) throws FileNotFoundException, UnsupportedEncodingException {
	  for(int i=0; i<ficheros.size(); i++) {
	  	PrintWriter writer = new PrintWriter(path+"/links_" + ficheros.get(i).getName()+".txt", "UTF-8");
	  	List<Link> links = ficheros.get(i).getLinks();
	  	for(Link enlace : links)
	  		writer.println(enlace.toString());
	  }	
  }
  
  
  private static List ordena(HashMap<String, Integer> m) {
	  List lista = new LinkedList(m.entrySet());
	  Collections.sort(lista, new Comparator() {
		  	public int compare(Object o1, Object o2) {
		  		return ((Comparable) (((Map.Entry) (o1)).getValue())).compareTo(((Map.Entry) (o2)).getValue());
		  	}
	  });
	  
	  return lista;
  }
  
  public static void cuentaPalabras(ArrayList<Fichero> f, String path) throws FileNotFoundException, UnsupportedEncodingException {
	  List list;
	  for(int i=0; i<f.size(); i++) {
		  PrintWriter writer = new PrintWriter(path+"/word_count_" + f.get(i).getName() + ".txt", "UTF-8");
		  list = ordena(f.get(i).getPalabras());
		  Iterator it = new ReverseListIterator(list);
		  while(it.hasNext()) {
			  writer.println(it.next().toString());
		  }
		  writer.close();
	  }
  }

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    boolean keep = true;
    ArrayList<Fichero> ficheros = new ArrayList<Fichero>();
	try {
		while(keep) {
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("***************************************");
	    	System.out.println("Bienvenido al sistema de preprocesamiento de archivos");
	    	System.out.println("Escriba 0 para leer un archivo o 1 para leer de un directorio");
	    	int eleccion = sc.nextInt();
	    	if(eleccion==0)
	    	{
	    		System.out.println("Crearemos un directorio. ¿Cómo se llamará?");
	    		String dir = sc.next();
	    		File f = new File(dir);
	    		f.mkdir();
	    		System.out.println("Dame el nombre del archivo a estudiar");
	    		String fi = sc.next();
	    		Fichero fichero = new Fichero(fi);
	    		ficheros.add(fichero);
	    		System.out.println(dir);
	    		imprimeDatos(ficheros,dir);
	    	    imprimeLinks(ficheros,dir);
	    	    cuentaPalabras(ficheros,dir);
	    	}
	    	else if(eleccion == 1) {
	    		System.out.println("Dime el directorio a procesar");
	    		String dir = sc.next();
	    		FileExtraction extract = new FileExtraction();
	    	    ArrayList<String> paths = extract.getPaths(dir);
	    	    for(int i=1; i<paths.size(); i++){
	    	    	File fi = new File(paths.get(0) + "/" + paths.get(i));
	    	    	fi.mkdir();
	    	    	Fichero f = new Fichero(paths.get(0) + "/" + paths.get(i));
	    	    	ficheros.add(f);
	    	    }
	    	    imprimeDatos(ficheros,dir);
	    	    imprimeLinks(ficheros,dir);
	    	    cuentaPalabras(ficheros,dir);
	    	    
	    	}
	    	else {
	    		System.out.println("No introdujo ningún número correcto. ¿Desea salir (S=1/N=0)?");
	    		if(!sc.nextBoolean())
	    			keep = false;
	    	}
		}
    }
	catch (Exception e)
    {
        System.err.println("Error:" + e.getMessage());
                    e.printStackTrace();
    }
	  

  }
}
