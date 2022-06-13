package swingworkerdone;

import java.util.Random;

import javax.swing.SwingWorker;

public class WorkerMontecarlo extends SwingWorker<Double, Double> {

    private int iteraciones;
    private Random r ;
    private int R = 2;
    private Panel panel;
    private double total = 0;
    private double in = 0;
    private double res = 0;

    public WorkerMontecarlo(Panel p) {
        this.panel = p;
        iteraciones = panel.getIteraciones();
        r = new Random();
    }

    protected Double doInBackground() throws InterruptedException {
        int i = 0;
        
        while ((i < iteraciones) && !isCancelled()) {
            Thread.sleep(100);
            float x = r.nextFloat(-R,R);
            float y = r.nextFloat(-R,R);
            if(x*x + y*y < R*R)
                in++;
            total++;
            i++;
        }
        
        res = 4*in/total;

        return res;
    }

    public void done() {
        panel.escribePI1(res);
    }
}
