package src;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import org.apache.lucene.queryparser.classic.ParseException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class PanelQueries extends JPanel {
	private JTextField tfq1;
	private JTextField tfq2;
	private JRadioButton rmust;
	private JRadioButton rnot;
	private JRadioButton rshould;
	private JComboBox comboq1;
	private JComboBox comboq2;
	private JButton bquerysearch;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private InterfazRQuerySystem principal;
	
	public PanelQueries(InterfazRQuerySystem p) {
		this();
		principal = p;
	}

	/**
	 * Create the panel.
	 */
	public PanelQueries() {
		setBorder(new TitledBorder(null, "B\u00FAsqueda por campos", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setToolTipText("Búsquedas por campos");
		setForeground(Color.BLUE);
		setLayout(new GridLayout(0, 2, 3, 2));
		
		tfq1 = new JTextField();
		add(tfq1);
		tfq1.setColumns(10);
		
		comboq1 = new JComboBox();
		comboq1.setModel(new DefaultComboBoxModel(new String[] {"ID-q", "ID-user", "rate", "title", "body", "codes"}));
		add(comboq1);
		
		rmust = new JRadioButton("must");
		buttonGroup.add(rmust);
		add(rmust);
		
		rshould = new JRadioButton("should");
		buttonGroup.add(rshould);
		add(rshould);
		
		rnot = new JRadioButton("not");
		buttonGroup.add(rnot);
		add(rnot);
		
		JLabel label = new JLabel("");
		add(label);
		
		tfq2 = new JTextField();
		add(tfq2);
		tfq2.setColumns(10);
		
		comboq2 = new JComboBox();
		comboq2.setModel(new DefaultComboBoxModel(new String[] {"ID-q", "ID-user", "rate", "title", "body", "codes"}));
		add(comboq2);
		
		bquerysearch = new JButton("Buscar");
		bquerysearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					principal.Consulta(getQuery1(),getField1(),getQuery2(),getField2(),radio());
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(bquerysearch);

	}
	
	public String getQuery1() {
		return tfq1.getText();
	}
	
	public String getField1() {
		return comboq1.getSelectedItem().toString();
	}
	
	public String getField2() {
		return comboq2.getSelectedItem().toString();
	}
	public String getQuery2() {
		return tfq2.getText();
	}
	
	public void clean() {
		tfq1.setText("");
		tfq2.setText("");
	}
	
	public String radio() {
		if(rmust.isSelected())
			return "M";
		else if(rshould.isSelected())
			return "S";
		else if(rnot.isSelected())
			return "N";
		else
			return "none";
	}
}
