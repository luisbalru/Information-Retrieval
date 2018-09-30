/*
 * Main Java Class to preprocess a set of files in a directory
 */

/**
*
* @author Luis Balderas Ruiz
*/

package preprocessing;


public class Preprocesamiento{
  static void main(String[] args){
    FileExtraction extract = new FileExtraction();
    String[] paths = extract.getPaths(args[0]);
    String name;
    String type;
    String charset;
    String lang;
    for(String path:paths)
      Fichero fichero(path);
      name = fichero.getName();
      type = fichero.getType();
      charset = fichero.getType();
      lang = fichero.getLang();
      System.out.println("Nombre::"+name+" Tipo::"+type+" Charset::"+charset+"  Idioma::"+lang);
  }
}
