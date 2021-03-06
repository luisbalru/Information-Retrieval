package LA;

/*
 * Java Class to list a directory and
 * extract the content of a file 
 */

/**
 *
 * @author Luis Balderas Ruiz
 */



import java.io.File;
import java.util.ArrayList;



 public class FileExtraction{

   public ArrayList<String> getPaths(String dir){
     File file = null;
     ArrayList<String> paths = new ArrayList<String>();
     String[] paths_aux = null;

     try{
       // create new file object
       file = new File(dir);

       // array of files and directory (in this case, only files)
       String aux = file.getAbsolutePath();
       paths_aux = file.list();
       paths.add(aux);
       for(String p : paths_aux) {
    	   paths.add(p);
       }
       
     } catch (Exception e){
        e.printStackTrace();
     }
     return paths;
   }
 }