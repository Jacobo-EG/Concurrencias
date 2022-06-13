package swingcancel;

import java.util.List;

import javax.swing.SwingWorker;

public class WorkerSeries extends SwingWorker<Void, Double>{

    private int iteraciones;
    private Panel panel;
    private double sign = 1;
    private double denom = 1;
    private double sig = 0;

    public WorkerSeries(Panel p) {
        this.panel = p;
        iteraciones = panel.getIteraciones();
    }

    protected Void doInBackground() throws InterruptedException {
        int i = 0;
        while ((i < iteraciones) && !isCancelled()) {
            Thread.sleep(100);
            double x = sign*4/denom;
            publish(x);
            sign *= -1;
            denom += 2;
            i++;
            this.setProgress(100 * i / iteraciones);
        }
        return null;
    }

    public void process(List<Double> lf) {
        while(!lf.isEmpty()){
            sig += lf.remove(0);
            panel.escribePI2(sig);
        }   
    }
}
