package swingpublishbarra;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Principal {

	public static void crearGUI() {         
		JFrame ventana = new JFrame("Practica Evaluable");
		Panel panel = new Panel();
		Controlador ctr = new Controlador(panel);
		panel.setControlador(ctr);
		ventana.setContentPane(panel);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.pack();
		ventana.setVisible(true);
	}

	public static void main(String[] args) {
		// Hebra dispacher...
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				crearGUI();
			}
		});

	}

}