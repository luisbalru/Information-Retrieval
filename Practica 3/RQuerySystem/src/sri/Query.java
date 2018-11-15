package sri;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Query {
	private String ID_q;
	private String ID_user;
	private String date;
	private String rate;
	private String title;
	private String body;
	private List<String> codes;
	
	public Query(String ID, String IDu,String fecha,String punt,String titulo,String cuerpo) {
		setID_q(ID);
		setID_user(IDu);
		setDate(fecha);
		setRate(punt);
		setTitle(titulo);
		codes = new ArrayList<String>();
		tokenizeCodesBody();
	}

	public String getID_q() {
		return ID_q;
	}

	private void setID_q(String iD_q) {
		ID_q = iD_q;
	}

	public String getDate() {
		return date;
	}

	private void setDate(String date) {
		this.date = date;
	}

	public String getID_user() {
		return ID_user;
	}

	private void setID_user(String iD_user) {
		ID_user = iD_user;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}
	
	public String getRate() {
		return rate;
	}

	private void setRate(String rate) {
		this.rate = rate;
	}

	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		this.body = body;
	}
	
	public String getCodes() {
		String aux = "";
		for(String c : codes) {
			aux = aux + c;
		}
		return aux;
	}
	
	public List<String> tokenizeTitle() {
		List<String> tokens = new ArrayList<String>();
		tokens = AnalyzerUtils.tokenizeString(new StandardAnalyzer(), title);
		return tokens;
	}
	
	public void tokenizeCodesBody(){
		Document doc = Jsoup.parse(body);
		Elements cods = doc.getElementsByTag("code");
		for (Element code : cods) {
			codes.add(code.text());
		}
		body = doc.body().text();
	}
	
	
}
