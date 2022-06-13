package swingworkerdone;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * PropertyChangeListener interface
 * Different event may ocurrs, check the correct one ("progress") with https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/java/beans/PropertyChangeEvent.html#getPropertyName() 
 */

public class Controlador implements ActionListener {

	private Panel panel;
	WorkerMontecarlo wm;
    WorkerSeries ws;

	public Controlador(Panel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("COMENZAR")) {
			try {
				panel.limpia1();
                panel.limpia2();

				wm = new WorkerMontecarlo(panel);
                ws = new WorkerSeries(panel);
				wm.execute();
                ws.execute();

			} catch (NullPointerException ne) {
				JOptionPane.showMessageDialog(new JFrame(), "Se debe introducir el numero de iteraciones", "Dialog",
						JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(new JFrame(), "El numero de iteraciones debe ser entero",
						"Dialog", JOptionPane.ERROR_MESSAGE);
			}
		
		} 

	}
}