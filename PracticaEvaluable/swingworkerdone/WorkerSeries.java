package swingworkerdone;


import javax.swing.SwingWorker;

public class WorkerSeries extends SwingWorker<Double, Double>{

    private int iteraciones;
    private Panel panel;
    private double sign = 1;
    private double denom = 1;
    private double res = 0;

    public WorkerSeries(Panel p) {
        this.panel = p;
        iteraciones = panel.getIteraciones();
    }

    protected Double doInBackground() throws InterruptedException {
        int i = 0;
        while ((i < iteraciones) && !isCancelled()) {
            Thread.sleep(100);
            double x = sign*4/denom;
            res += x;
            sign *= -1;
            denom += 2;
            i++;
        }
        return res;
    }

    public void done() {
        panel.escribePI2(res);
    }
}
