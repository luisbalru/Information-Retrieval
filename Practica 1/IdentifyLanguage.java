/*
 * Java Class to identify a file's language
 */

/**
 *
 * @author Luis Balderas Ruiz
 */


package preprocessing;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;

public class IdentifyLanguage{

  public String identifyLanguage(String text) throws IOException {
        LanguageDetector identifier  = new  OptimaizeLangDetector().loadModels();
        LanguageResult idioma = identifier.detect(text);
        return idioma.getLanguage();
    }
}
