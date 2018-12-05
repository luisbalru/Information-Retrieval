package src;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class PanelFacets extends JPanel {
	private JTextField tffacet;
	private JButton bfacetsearch;
	private InterfazRQuerySystem principal;

	/**
	 * Create the panel.
	 */
	public PanelFacets(InterfazRQuerySystem p) {
		this();
		principal = p;
	}
	public PanelFacets() {
		setBorder(new TitledBorder(null, "Facetas", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setForeground(Color.BLUE);
		setLayout(new GridLayout(0, 2, 2, 2));
		
		tffacet = new JTextField();
		add(tffacet);
		tffacet.setColumns(10);
		
		bfacetsearch = new JButton("Buscar");
		bfacetsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					principal.Faceta(getFaceta());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(bfacetsearch);

	}

	public String getFaceta() {
		return tffacet.getText();
	}
	
	public void clean() {
		tffacet.setText("");
	}
}
