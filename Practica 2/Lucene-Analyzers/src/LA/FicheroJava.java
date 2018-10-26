package LA;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class FicheroJava extends Fichero{
	public FicheroJava(String path) throws IOException, SAXException, TikaException {
		Tika tika = new Tika();
	    File f = new File(path);
	    name = f.getName();
	    metadata = new ArrayList<String>();
	    lista = new ArrayList<List>();
	    word_count = new ArrayList<HashMap<String, Integer> >();
	    getContent_Metadata(f);
		//cuentaPalabras();
	    /*for(int i=0; i<word_count.size();i++)
	    	lista.add(ordena(i));*/
	}
	
	
}
