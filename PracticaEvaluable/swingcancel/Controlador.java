package swingcancel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * PropertyChangeListener interface
 * Different event may ocurrs, check the correct one ("progress") with https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/java/beans/PropertyChangeEvent.html#getPropertyName() 
 */

public class Controlador implements ActionListener, PropertyChangeListener {

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
				panel.setBotonCancelar();

				wm = new WorkerMontecarlo(panel);
                ws = new WorkerSeries(panel);
				wm.addPropertyChangeListener(this);
                ws.addPropertyChangeListener(this);
				wm.execute();
                ws.execute();

			} catch (NullPointerException ne) {
				JOptionPane.showMessageDialog(new JFrame(), "Se debe introducir el numero de iteraciones", "Dialog",
						JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(new JFrame(), "El numero de iteraciones debe ser entero",
						"Dialog", JOptionPane.ERROR_MESSAGE);
			}
		
		} else if (e.getActionCommand().equals("CANCELAR")) {
			panel.setBotonComenzar();
			if (wm != null)
				wm.cancel(true);
         	if(ws != null)
            	ws.cancel(true);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource().getClass().equals(wm.getClass())){
		    if(evt.getPropertyName().equals("progress")){
			    panel.setProgresoMonteCarlo((Integer) evt.getNewValue());
		    }
        }else{
            if(evt.getPropertyName().equals("progress")){
			    panel.setProgresoLeibniz((Integer) evt.getNewValue());
		    }
        }
	}

}