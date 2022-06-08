package mvcancelar;

import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class CalcularNumeros extends SwingWorker<NumeroThreshold, Object> {

    private NumeroThreshold modelInternal;
    private static Random r = new Random();
    private int cantidad;
    private Vista panel;
    private float umbral;

    public CalcularNumeros(NumeroThreshold nc, Vista p) {
        panel = p;
        umbral = panel.obtenerUmbral();
        cantidad = panel.obtenerCantidad();
        modelInternal = new NumeroThreshold();
        modelInternal.establecerUmbral(umbral);
    }

    @Override
    protected NumeroThreshold doInBackground() throws Exception {
        int i = 0;

        while ((i < cantidad) && !isCancelled()) {
            Thread.sleep(100);
            modelInternal.anyadirNumero(r.nextFloat());
            i++;
        }
        return modelInternal;
    }

    public void done() {
        try {
            panel.actualizar(this.get());    
        } catch (InterruptedException | ExecutionException e) {        
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CancellationException ce) {    //El problema es que, si se cancela antes de terminar no mostrará nada pues no se 
            System.out.println("Cancelation REgistrada, pierdo todo...");    //ha llega a hacer el get, pues se captura la excepcion
        }                                                                       //para que se cumpla la exclusion 

    }

}
