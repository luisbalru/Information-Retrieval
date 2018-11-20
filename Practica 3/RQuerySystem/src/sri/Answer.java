package sri;

import java.util.ArrayList;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Answer {
	private String ID_a;
	private String ID_user;
	private String ID_q;
	private String date;
	private String puntuacion;
	private String aceptada;
	private String body;
	private List<String> codes;


	public Answer(String ID_res,String ID_us, String ID_p,String fecha,String punt,
			String acept,String cuerpo) {
		setID_a(ID_res);
		setID_user(ID_us);
		setID_q(ID_p);
		setDate(fecha);
		setPuntuacion(punt);
		setAceptada(acept);
		setBody(cuerpo);
		codes = new ArrayList<String>();
		tokenizeCodesBody();
	}

	public String getID_user() {
		return ID_user;
	}

	private void setID_user(String iD_user) {
		ID_user = iD_user;
	}

	public String getID_a() {
		return ID_a;
	}

	private void setID_a(String iD_a) {
		ID_a = iD_a;
	}
	
	public String getCodes() {
		String aux = "";
		for(String c : codes) {
			aux = aux + c;
		}
		return aux;
	}

	public String getDate() {
		return date;
	}

	private void setDate(String date) {
		this.date = date;
	}

	public String getID_q() {
		return ID_q;
	}

	private void setID_q(String iD_q) {
		ID_q = iD_q;
	}

	public String getPuntuacion() {
		return puntuacion;
	}

	private void setPuntuacion(String puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getAceptada() {
		return aceptada;
	}

	private void setAceptada(String aceptada) {
		this.aceptada = aceptada;
	}

	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		this.body = body;
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
