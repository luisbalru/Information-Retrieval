package LA;
/**
 * Clase principal de la práctica 2, dedicada al preprocesamiento con Lucene
 */

/**
 * @author Luis Balderas Ruiz
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
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
	 /*@SuppressWarnings("all") public static void main() {
		  String s;
		 }	*/
	
	
  public static void imprimeDatos(ArrayList<Fichero> ficheros,boolean muchos, String dir) throws FileNotFoundException, UnsupportedEncodingException {
	  PrintWriter writer;
	  if(muchos)
		writer = new PrintWriter(dir+"/datoslibros.md", "UTF-8");
	  else
	  	writer = new PrintWriter(ficheros.get(0).directorio+"/datoslibros.md", "UTF-8");
	  
	  writer.println("| NOMBRE | TIPO | CHARSET | IDIOMA |");
	  writer.println("|--------|--------|---------|---------|");
	  for(int i=0; i<ficheros.size(); i++){
	   	writer.println(" | "+ ficheros.get(i).getName() +" | "+ ficheros.get(i).getType() +
				" | "+ ficheros.get(i).getCharset() +" | " + ficheros.get(i).getLang()+" | " );
	   	
	  }
	  writer.close();
	  
  }
  
  public static void cuentaPalabras(ArrayList<Fichero> f) throws FileNotFoundException, UnsupportedEncodingException {
	  for(int i=0; i<f.size(); i++) {
		  String path = f.get(i).directorio;
		  for(int j=0; i< f.get(i).lista.size(); i++) {
			  PrintWriter writer = new PrintWriter(path+"/word_count_" + f.get(i).getName() + "_" + j +".txt", "UTF-8");
			  Iterator it = new ReverseListIterator(f.get(i).lista.get(j));
			  while(it.hasNext()) {
				  writer.println(it.next().toString());
			  }
			  writer.close();
		  }
	  }
  }
  

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    ArrayList<Fichero> ficheros = new ArrayList<Fichero>();
	try {
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("**************************************");
	    	System.out.println("Bienvenido al sistema de preprocesamiento de archivos");
	    	System.out.println("Crearemos un directorio. ¿Cómo se llamará?");
	    	String dir = sc.next();
	    	File f = new File(dir);
	    	f.mkdir();
	    	System.out.println("Dame el nombre del archivo a estudiar");
	    	String fi = sc.next();
	    	Fichero fichero = new Fichero(fi);
	    	ficheros.add(fichero);
	    	ficheros.get(0).directorio = dir;
	    	imprimeDatos(ficheros,false," ");
	    	cuentaPalabras(ficheros);
    }
	catch (Exception e)
    {
        System.err.println("Error:" + e.getMessage());
                    e.printStackTrace();
    }
  }
}