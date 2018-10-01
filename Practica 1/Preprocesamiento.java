/**
 * Clase principal de la práctica 1, dedicada al preprocesamiento
 */

/**
 * @author Luis Balderas Ruiz
 */

package preprocessing;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;


public class Preprocesamiento{

  public static void main(String[] args) throws IOException, SAXException, TikaException{
    FileExtraction extract = new FileExtraction();
    String[] paths = extract.getPaths(args[0]);
    ArrayList<Fichero> ficheros = new ArrayList<Fichero>();
    for(int i=0; i<paths.length; i++){
      paths[i] = "//data//" + paths[i];
    }


    /*for(String path : paths){
      System.out.println(path);
      Fichero f = new Fichero(path);
      ficheros.add(f);
    }*/
    Fichero f = new Fichero("/home/luisbalru/pg11.epub");
    ficheros.add(f);

    String name = ficheros.get(0).getName();
    String type = ficheros.get(0).getType();
    String charset = ficheros.get(0).getCharset();
    String lang = ficheros.get(0).getLang();
    System.out.println("Nombre::"+name+" Tipo::"+type+" Charset::"+charset+"  Idioma::"+lang);
  }
}
