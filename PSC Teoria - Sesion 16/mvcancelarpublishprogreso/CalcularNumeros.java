package mvcancelarpublishprogreso;

import java.util.Random;
import javax.swing.SwingWorker;
import java.util.List;

/* 
 * https://refactoring.guru/design-patterns/observer   Publisher ->SwingWorker thread, Subscriber and Client-> the controller (Dispacher thread)
 * https://docs.oracle.com/en/java/javase/18/docs/api/java.desktop/javax/swing/SwingWorker.html#setProgress(int)
 * 
 */

public class CalcularNumeros extends SwingWorker<Void, Float> {

    private static Random r = new Random();
    private int cantidad;
    private Vista panel;
    private float umbral;
    private int contMenor = 0;
    private int contMayor = 0;

    public CalcularNumeros(Vista p) {
        panel = p;
        umbral = panel.obtenerUmbral();
        cantidad = panel.obtenerCantidad();
    }

    protected Void doInBackground() throws InterruptedException {
        int i = 0;

        while ((i < cantidad) && !isCancelled()) {
            Thread.sleep(100);
            float numero = r.nextFloat();
            publish(numero);
            i++;
            this.setProgress(100 * i / cantidad);
        }
        return null;

    }

    public void process(List<Float> lf) {
        float sig;
        while(!lf.isEmpty()){
            sig = lf.remove(0);
            if(sig < umbral){
                contMenor++;
				panel.anyadirListaMenores(String.format("%.02f", sig) + " ");
				if ((contMenor) % 5 == 0)
					panel.anyadirListaMenores("\n");
            }else{
                contMayor++;
				panel.anyadirListaMayores(String.format("%.02f", sig) + " ");
				if ((contMayor) % 5 == 0)
					panel.anyadirListaMayores("\n");
            }
        }   
    }

}
