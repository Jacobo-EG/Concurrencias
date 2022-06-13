package swingpublishbarra;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

public class WorkerMontecarlo extends SwingWorker<Void, Boolean> {

    private int iteraciones;
    private Random r ;
    private int R = 2;
    private Panel panel;
    private double total = 0;
    private double in = 0;

    public WorkerMontecarlo(Panel p) {
        this.panel = p;
        iteraciones = panel.getIteraciones();
        r = new Random();
    }

    protected Void doInBackground() throws InterruptedException {
        int i = 0;
        while ((i < iteraciones) && !isCancelled()) {
            Thread.sleep(100);
            float x = r.nextFloat(-R,R);
            float y = r.nextFloat(-R,R);
            publish((x*x + y*y < R*R));
            i++;
            this.setProgress(100 * i / iteraciones);
        }
        return null;
    }

    public void process(List<Boolean> lf) {
        boolean sig;
        while(!lf.isEmpty()){
            sig = lf.remove(0);
            if(sig)
                in++;
            total++;
            panel.escribePI1(4*in/total);
        }   
    }
}
