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
  
  public static void imprimeLinks(ArrayList<Fichero> ficheros) throws FileNotFoundException, UnsupportedEncodingException {
	  for(int i=0; i<ficheros.size(); i++) {
		String path = ficheros.get(i).directorio;
	  	PrintWriter writer = new PrintWriter(path+"/links_" + ficheros.get(i).getName()+".txt", "UTF-8");
	  	List<Link> links = ficheros.get(i).getLinks();
	  	for(Link enlace : links)
	  		writer.println(enlace.toString());
	  	writer.close();
	  }	
  }
  
  public static void cuentaPalabras(ArrayList<Fichero> f) throws FileNotFoundException, UnsupportedEncodingException {
	  for(int i=0; i<f.size(); i++) {
		  String path = f.get(i).directorio;
		  PrintWriter writer = new PrintWriter(path+"/word_count_" + f.get(i).getName() + ".txt", "UTF-8");
		  Iterator it = new ReverseListIterator(f.get(i).lista);
		  while(it.hasNext()) {
			  writer.println(it.next().toString());
		  }
		  writer.close();
	  }
  }
  
  public static void CSV(Fichero f) throws FileNotFoundException {
	  List list;
	  PrintWriter pw = new PrintWriter(new File(f.directorio+"/"+f.getName()+"_datos.csv"));
	  StringBuilder sb = new StringBuilder();
	  Iterator it = new ReverseListIterator(f.lista);
	  int i = 1;
	  while(it.hasNext()) {
		  HashMap.Entry pareja = (HashMap.Entry) it.next();
		  sb.append(Integer.toString(i));
		  sb.append(',');
		  sb.append(pareja.getValue().toString());
		  sb.append('\n');
		  i++;
	  }
	  pw.write(sb.toString());
	  pw.close();
  }
  

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    boolean keep = true;
    ArrayList<Fichero> ficheros = new ArrayList<Fichero>();
	try {
		while(keep) {
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("**************************************");
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
	    		ficheros.get(0).directorio = dir;
	    		imprimeDatos(ficheros,false," ");
	    	    imprimeLinks(ficheros);
	    	    cuentaPalabras(ficheros);
	    	    CSV(ficheros.get(0));
	    	    String csv = ficheros.get(0).directorio+"/"+ficheros.get(0).getName()+"_datos.csv";
	    	    String name = ficheros.get(0).directorio+"/"+ficheros.get(0).getName();
	    	    ProcessBuilder pb = new ProcessBuilder("python", "/home/luisbalru/plot.py", csv,name);
	    	    Process p = pb.start();
	    	    p.waitFor();
	    	    System.out.println(p.exitValue());
	    	    keep = false;
	    	}
	    	else if(eleccion == 1) {
	    		System.out.println("Dime el directorio a procesar");
	    		String dir = sc.next();
	    		FileExtraction extract = new FileExtraction();
	    	    ArrayList<String> paths = extract.getPaths(dir);
	    	    //System.out.println("Dime el directorio donde guardar toda la información");
	    		//String directory = sc.next();
	    	    for(int i=1; i<paths.size(); i++){
	    	    	//System.out.println(paths.get(0) + "/" + paths.get(i));
	    	    	Fichero f = new Fichero(paths.get(0) + "/" + paths.get(i));
	    	    	f.directorio = paths.get(0) + "/" + paths.get(i)+"_dir";
	    	    	File file = new File(f.directorio);
		    	    Files.createDirectory(file.toPath());
	    	    	ficheros.add(f);
	    	    }
	    	    imprimeDatos(ficheros,true,dir);
	    	    imprimeLinks(ficheros);
	    	    cuentaPalabras(ficheros);
	    	    
	    	    for(int i=0; i<ficheros.size();i++) {
	    	    	CSV(ficheros.get(i));
	    	    	String csv = ficheros.get(i).directorio+"/"+ficheros.get(i).getName()+"_datos.csv";
		    	    String name = ficheros.get(i).directorio+"/"+ficheros.get(i).getName();
		    	    ProcessBuilder pb = new ProcessBuilder("python", "/home/luisbalru/plot.py", csv,name);
		    	    Process p = pb.start();
		    	    p.waitFor();
		    	    System.out.println(p.exitValue());
		    	    p.destroy();
	    	    }
	    	    keep = false;
	    	    
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
