package Sep20;

import java.util.concurrent.Semaphore;

public class CursoSemp implements Curso{

	//Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;
	
	//Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;
	private int numAlumIni = 0;		//numero de alumnos en iniciacion
	private int numAlumAva = 0;		//numero de alumnos en avanzado
	private int numEsperandoIni = 0;
	private int numEsperandoPro = 0;
	private boolean realizando = false;		//hay un grupo en avanzado => true

	private Semaphore protectVar = new Semaphore(1,true);
	private Semaphore iniciacion = new Semaphore(0,true);
	private Semaphore avanzado = new Semaphore(0,true);
	private Semaphore proyecto = new Semaphore(0,true);

	public CursoSemp(){
	}
	
	//El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de iniciacion
	public void esperaPlazaIniciacion(int id) throws InterruptedException{
		//Espera si ya hay 10 alumnos cursando esta parte
		protectVar.acquire();
		if(numAlumIni >= MAX_ALUMNOS_INI){
			numEsperandoIni++;
			protectVar.release();
			iniciacion.acquire();
			protectVar.acquire();
		}

		numAlumIni++;
		//Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de iniciacion
		System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion");
		protectVar.release();
	}

	//El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public void finIniciacion(int id) throws InterruptedException{
		protectVar.acquire();
		//Mensaje a mostrar para indicar que el alumno ha terminado la parte de principiantes
		System.out.println("PARTE INICIACION: Alumno " + id + " termina parte iniciacion");
		if (numAlumIni >= MAX_ALUMNOS_INI && numEsperandoIni > 0) {
			numEsperandoIni--;
			iniciacion.release();
		}
		numAlumIni--;
		numEsperandoIni--;
		//Libera la conexion para que otro alumno pueda usarla
		protectVar.release();
	}
	
	/* El alumno tendra que esperar:
	 *   - si ya hay un grupo realizando la parte avanzada
	 *   - si todavia no estan los tres miembros del grupo conectados
	 */
	public void esperaPlazaAvanzado(int id) throws InterruptedException{
		protectVar.acquire();
		//Espera a que no haya otro grupo realizando esta parte
		if(realizando){
			protectVar.release();
			avanzado.acquire();
			protectVar.acquire();
		}
		numAlumAva++;

		//Espera a que haya tres alumnos conectados
		if(numAlumAva < ALUMNOS_AV) {
			//Mensaje a mostrar si el alumno tiene que esperar al resto de miembros en el grupo
			System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");
			numEsperandoPro++;
			protectVar.release();
			proyecto.acquire();
			protectVar.acquire();
		}else{
			//Mensaje a mostrar cuando el alumno pueda empezar a cursar la parte avanzada
			System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
		}

		if(numEsperandoPro > 0){
			proyecto.release();
			numEsperandoPro--;
		}
		protectVar.release();
	}
	
	/* El alumno:
	 *   - informa que ya ha terminado de cursar la parte avanzada 
	 *   - espera hasta que los tres miembros del grupo hayan terminado su parte 
	 */ 
	public void finAvanzado(int id) throws InterruptedException{
		//Espera a que los 3 alumnos terminen su parte avanzada
		protectVar.acquire();
		numAlumAva--;
		if(numAlumAva > 0){
			//Mensaje a mostrar si el alumno tiene que esperar a que los otros miembros del grupo terminen
			System.out.println("PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");
			numEsperandoPro++;
			protectVar.release();
			proyecto.acquire();
			protectVar.acquire();
		}

		if(numEsperandoPro > 0){
			proyecto.release();
			numEsperandoPro--;
		}else{
			//Mensaje a mostrar cuando los tres alumnos del grupo han terminado su parte
			System.out.println("PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
			realizando = false;
		}
		protectVar.release();

	}
}