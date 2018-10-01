package preprocessing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;




 public class FileExtraction{

   public String[] getPaths(String dir){
     File file = null;
     String[] paths = null;

     try{
       // create new file object
       file = new File(dir);

       // array of files and directory (in this case, only files)
       paths = file.list();
     } catch (Exception e){
        e.printStackTrace();
     }
     return paths;
   }
 }
