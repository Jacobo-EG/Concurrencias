package mvc2;

import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class CalcularNumeros extends SwingWorker<NumeroThreshold, Object>{

   private float umbral;
   private int num;
   private Random r;
   private NumeroThreshold modalInternal;
   private Vista vista;

    public CalcularNumeros(Vista vista){
        this.umbral = vista.obtenerUmbral();
        this.num = vista.obtenerCantidad();
        this.vista = vista;
        r = new Random();
    }

    @Override
    public NumeroThreshold doInBackground() {
        try{
            modalInternal = new NumeroThreshold();

            modalInternal.establecerUmbral(umbral);

            for(int i = 0; i < num; i++){
                Thread.sleep(100);
                modalInternal.anyadirNumero(r.nextFloat(0,1));
            }
        } catch (NullPointerException ne) {
            JOptionPane.showMessageDialog(new JFrame(), "Se debe introducir el umbral y la cantidad", "Dialog",
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nf) {
            JOptionPane.showMessageDialog(new JFrame(), "El umbral es un número con punto y la cantidad un entero",
                "Dialog", JOptionPane.ERROR_MESSAGE);
        }catch(Exception e1){

        }
        return modalInternal;
    }

    @Override
    public void done(){
        List<Float> lf1 = modalInternal.verListaMenor();
		List<Float> lf2 = modalInternal.verListaMayor();

				// Imprimir 5 en línea y luego un \n
		for (int i = 0; i < lf1.size(); i++) {
			vista.anyadirListaMenores(String.format("%.02f", lf1.get(i)) + " ");
			if ((i + 1) % 5 == 0)
				vista.anyadirListaMenores("\n");
		}
		for (int i = 0; i < lf2.size(); i++) {
			vista.anyadirListaMayores(String.format("%.02f", lf2.get(i)) + " ");
			if ((i + 1) % 5 == 0)
				vista.anyadirListaMayores("\n");
		}
    }

}