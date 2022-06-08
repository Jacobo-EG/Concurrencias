package mvcancelarpublish;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador implements ActionListener {

	private Vista vista;
	CalcularNumeros cn;

	public Controlador(Vista panel) {
		this.vista = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Vista.calcular())) {
			try {
				cn = new CalcularNumeros(vista);
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

}
