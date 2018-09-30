/*
 * Java Class to mantain a Fichero object
 */

/**
*
* @author Luis Balderas Ruiz
*/

package preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.apache.tika.Tika;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.metadata.Metadata;


import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.sax.Link;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.List;

import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.parser.epub;
import org.apache.tika.parser.pdf;
import org.apache.tika.parser.html;

public class Fichero{
  String name;
  String[] metadata;
  String content;
  String charset;
  String type;
  String lang;

  Fichero(String file){
    Tika tika = new Tika();
    File f = new File(file);
    name = f.getName();
    content = parseFile(f);
    type = tika.detect(f);
    setLang(content);
  }

  private void setLang(String text){
    IdentifyLanguage ident = new IdentifyLanguage();
    lang = ident.identifyLanguage(text);
  }

  public String getLang(){
    return lang;
  }

  public String getCharset(){
    return charset;
  }

  public String getType(){
    return type;
  }

  public String getName(){
    return name;
  }

     // Parsing the file to Plain text
  private String parseFile(File file) throws IOException, SAXException, TikaException{
    Parser parser = new AutoDetectParser();
    BodyContentHandler handler = new BodyContentHandler();
    Metadata metadata = new Metadata();
    FileInputStream inputstream = new FileInputStream(file);
    AutoDetectReader detector = new AutoDetectReader(inputstream);
    Charset charSet = detector.getCharset();
    charset = charSet.toString();
    ParseContext context = new ParseContext();
    parser.parse(inputstream,handler,metadata,context);
    metadata = metadata.names();
    return handler.toString();
  }
