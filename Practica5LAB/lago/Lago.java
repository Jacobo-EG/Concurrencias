//Jacobo Elicha Garrucho, 29-04-2022

package lago;

public class Lago {
	private volatile int nivel = 0;
	private volatile boolean f0IncDec = false;
	private volatile boolean f0Inc = false;
	private volatile boolean f1Inc = false;
	private volatile boolean f1IncDec = false;
	private volatile boolean f0Dec = false;
	private volatile boolean f1Dec = false;
	private int turno = 0;
	private int turno2;


	public Lago(int valorInicial) {
		nivel = valorInicial;
	}

	// f0IncDec, f0Inc
	public void incRio0() {
		f0Inc = true;
		turno = 1;
		

		while(f1Inc && turno == 1) Thread.yield();

		f0IncDec = true;
		turno2 = 0;

		while(f1IncDec && turno2 == 0) Thread.yield();

		nivel++;

		f0IncDec = false;
		f0Inc = false;
	}

	// f0IncDec, f1Inc
	public void incRio1() {
		f1Inc = true;
		turno = 0;

		while(f0Inc && turno == 0) Thread.yield();

		f0IncDec = true;
		turno2 = 0;
		while(f1IncDec && turno2 == 0) Thread.yield();

		nivel++;

		f0IncDec = false;
		f1Inc = false;
	}

	// f1IncDec, f0Dec
	public void decPresa0() {


		f0Dec = true;
		turno = 1;

		while(f1Dec && turno == 1) Thread.yield();

		while(nivel == 0) Thread.yield();

		f1IncDec = true;
		turno2 = 1;
		while(f0IncDec && turno2 == 1) Thread.yield();

		nivel--;

		f0Dec = false;
		f1IncDec = false;

	}

	// f1IncDec, f1Dec
	public void decPresa1() {


		f1Dec = true;
		turno = 0;

		while(f0Dec && turno == 0) Thread.yield();

		while(nivel == 0) Thread.yield();

		f1IncDec = true;
		turno2 = 1;
		while(f0IncDec && turno2 == 1) Thread.yield();

		nivel--;

		f1Dec = false;
		f1IncDec = false;
	}

	public int nivel() {
		return nivel;
	}
}
