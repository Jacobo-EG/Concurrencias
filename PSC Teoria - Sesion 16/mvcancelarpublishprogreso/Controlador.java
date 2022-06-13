package mvcancelarpublishprogreso;

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

	private Vista vista;
	CalcularNumeros cn;

	public Controlador(Vista panel) {
		this.vista = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Vista.calcular())) {
			try {
				vista.limpiar();

				cn = new CalcularNumeros(vista);
				cn.addPropertyChangeListener(this);
				cn.execute();

			} catch (NullPointerException ne) {
				JOptionPane.showMessageDialog(new JFrame(), "Se debe introducir el umbral y la cantidad", "Dialog",
						JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException nf) {
				JOptionPane.showMessageDialog(new JFrame(), "El umbral es un número con punto y la cantidad un entero",
						"Dialog", JOptionPane.ERROR_MESSAGE);
			}

		} else if (e.getActionCommand().equals(Vista.cancelar())) {
			if (cn != null)
				cn.cancel(true);
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("progress")){
			vista.establecerProgreso((Integer) evt.getNewValue());
		}
	}

}