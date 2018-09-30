/*
 * Java Class to list a directory and
 * extract the content of a file
 */

/**
 *
 * @author Luis Balderas Ruiz
 */

package preprocessing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;



 public class FileExtraction{

   public String[] getPaths(String dir){
     File file = null;
     String[] paths;

     try{
       // create new file object
       file = new File(dir);

       // array of files and directory (in this case, only files)
       paths = file.list()
     } catch (Exception e){
        e.printStackTrace();
     }
     return paths;
   }
 }
