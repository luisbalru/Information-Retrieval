/*
 * Definición del nuevo T.D.A: Fichero
 */

/*
 * @author Luis Balderas Ruiz
 */

package preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.LinkContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

public class Fichero{
  String name;
  ArrayList<String> metadata;
  String language;
  String type;
  String charset;
  String content;
  List<Link> links;

  public Fichero(String path) throws IOException, SAXException, TikaException{
    Tika tika = new Tika();
    File f = new File(path);
    name = f.getName();
    type = tika.detect(f);
    charset = setCharset(f,tika);
    metadata = new ArrayList<String>();
    links = new ArrayList<Link>();
	getContent_Metadata(f);
	Links(f);
    language = identifyLanguage(content);
  }

  private String setCharset(File f, Tika tika) throws FileNotFoundException{
	FileInputStream inputstream = new FileInputStream(f);
	AutoDetectReader detector = null;
	try {
		detector = new AutoDetectReader(inputstream);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TikaException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Charset charSet = detector.getCharset();
    return charSet.toString();
  }


  private void getContent_Metadata(File f) throws IOException, SAXException, TikaException{
	  // Elimina el límite de contenido
	FileInputStream inputstream = new FileInputStream(f);
	Parser parser = new AutoDetectParser();
    BodyContentHandler handler = new BodyContentHandler(-1);
    Metadata met = new Metadata();
    ParseContext context = new ParseContext();
    parser.parse(inputstream, handler, met, context);
    inputstream.close();
    content = handler.toString();
    String[] metadataNames = met.names();
    String aux;
    for(String name : metadataNames){
      aux = name + ": " + met.get(name);
      metadata.add(aux);
    }
  }

  private void Links(File f) throws IOException, SAXException, TikaException {
	FileInputStream inputstream = new FileInputStream(f);
	Parser parser = new AutoDetectParser();
	BodyContentHandler handler = new BodyContentHandler(-1);
	Metadata met = new Metadata();
	LinkContentHandler link_handler = new LinkContentHandler();
	ParseContext context = new ParseContext();
	parser.parse(inputstream, link_handler, met, context);
    inputstream.close();
    links = link_handler.getLinks();
  }

  private String identifyLanguage(String text) throws IOException{
	  LanguageDetector identifier  = new  OptimaizeLangDetector().loadModels();
      LanguageResult idioma = identifier.detect(text);
      return idioma.getLanguage();
  }

  public String getName(){
    return name;
  }

  public String getType(){
    return type;
  }

  public String getLang(){
    return language;
  }

  public String getCharset(){
    return charset;
  }

  public String getContent(){
    return content;
  }

  public ArrayList<String> getMetadata(){
    return metadata;
  }

  public List<Link> getLinks(){
	  return links;
  }


}
