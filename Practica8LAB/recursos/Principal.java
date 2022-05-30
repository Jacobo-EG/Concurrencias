

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 10;
		Control c = new ControlSemaphore(N);
		Thread[] p = new Thread[10];
		for (int i = 0; i < p.length; i++)
			p[i] = new Thread(new Proceso(i, c, N));
		for (int i = 0; i < p.length; i++)
			p[i].start();
	}

}
