package interfaz;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JTextArea;

public class PanelOutput extends JPanel {
	private JTextArea textArea;
	private JScrollPane scroll;

	/**
	 * Create the panel.
	 */
	public PanelOutput() {
		setBorder(new TitledBorder(null, "Resultados", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		
		textArea = new JTextArea();
		scroll = new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setRows(10);
		textArea.setColumns(40);
		add(scroll);

	}

	public JTextArea getTextArea() {
		return textArea;
	}
	
	public void setResultado(String s) {
		textArea.setText(s);
	}
}
