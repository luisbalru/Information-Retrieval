package interfaz;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import searcher.Buscador;

public class InterfazRQuerySystem extends JFrame {

	private JPanel contentPane;
	private final PanelQueries panelQueries;
	private PanelFacets panelFacets;
	private PanelOutput panelOutput;
	private Buscador searcher;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazRQuerySystem frame = new InterfazRQuerySystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazRQuerySystem() {
		searcher = new Buscador("/home/luisbalru/Universidad/RI/Indices/Queries","/home/luisbalru/Universidad/RI/Indices/Answers",
				"/home/luisbalru/Universidad/RI/Indices/Tags","/home/luisbalru/Universidad/RI/Indices/TaxoT");
		setTitle("RQuerySystem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 455);
		panelQueries = new PanelQueries(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelQueries, BorderLayout.CENTER);
		
		panelFacets = new PanelFacets(this);
		contentPane.add(panelFacets, BorderLayout.NORTH);
		
		panelOutput = new PanelOutput();
		panelOutput.getTextArea().setRows(10);
		contentPane.add(panelOutput, BorderLayout.SOUTH);
	}

	public void Consulta(String q1,String f1, String q2, String f2, String radio) throws IOException, ParseException {
		boolean must=false;
		boolean should = false;
		boolean not = false;
		if(radio=="M") {
			must = true;
		}
		else if(radio == "S") {
			should = true;
		}
		
		else if(radio == "N") {
			not = true;
		}
		
		String resultado = "";
		ArrayList<Document> documentos = searcher.SearchQuery(q1, q2, f1, f2, must, should, not, 20);
		for(int i=0; i<documentos.size();i++) {
			if(documentos.get(i).get("ID-a")==null)
				resultado = resultado + "ID-q: " + documentos.get(i).get("ID-q") + " ID-user: " + documentos.get(i).get("ID-user") + " Rate: " + documentos.get(i).get("rate") + " Title: " + documentos.get(i).get("title") + "\n";
			else
				resultado = resultado + "ID-a:" + documentos.get(i).get("ID-a")+   " ID-user: " + documentos.get(i).get("ID-user") + " Rate: " + documentos.get(i).get("puntuacion") +  "\n";
		}
		System.out.println(resultado);
		panelOutput.setResultado(resultado);
	}

	public void Faceta(String faceta) throws IOException {
		String resultado = "";
		ArrayList<Document> documentos = searcher.SearchbyFacet(faceta, 5);
		for(int i=0; i<documentos.size();i++) {
			resultado = resultado + "ID-q: " + documentos.get(i).get("ID-q") + " ID-user: " + documentos.get(i).get("ID-user") + " Rate: " + documentos.get(i).get("rate") + " Title: " + documentos.get(i).get("title") +  "\n";
		}
		panelOutput.setResultado(resultado);
	}

}
