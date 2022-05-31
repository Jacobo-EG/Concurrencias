package Examenes.Sep20;

public class CursoSync implements Curso {

	//Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;
	
	//Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;

	private int numAlumIni = 0;		//numero de alumnos en iniciacion

	private int numAlumAva = 0;		//numero de alumnos en avanzado
	private boolean realizando = false;		//hay un grupo en avanzado => true
	
	public CursoSync(){

	}
	
	//El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de iniciacion
	public synchronized void esperaPlazaIniciacion(int id) throws InterruptedException{
		//Espera si ya hay 10 alumnos cursando esta parte
		while(numAlumIni >= MAX_ALUMNOS_INI){
			wait();
		}
		numAlumIni++;
		//Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de iniciacion
		System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion");
	}

	//El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public synchronized void finIniciacion(int id) throws InterruptedException{
		//Mensaje a mostrar para indicar que el alumno ha terminado la parte de principiantes
		System.out.println("PARTE INICIACION: Alumno " + id + " termina parte iniciacion");
		if(numAlumIni >= MAX_ALUMNOS_INI){
			notifyAll();
		}
		numAlumIni--;
		//Libera la conexion para que otro alumno pueda usarla
	}
	
	/* El alumno tendra que esperar:
	 *   - si ya hay un grupo realizando la parte avanzada
	 *   - si todavia no estan los tres miembros del grupo conectados
	 */
	public synchronized void esperaPlazaAvanzado(int id) throws InterruptedException{
		//Espera a que no haya otro grupo realizando esta parte
		while(realizando)
			wait();

		numAlumAva++;

		if(numAlumAva < ALUMNOS_AV)
			//Mensaje a mostrar si el alumno tiene que esperar al resto de miembros en el grupo
			System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");

		if(numAlumAva == ALUMNOS_AV){
			realizando = true;
			notifyAll();
		}

		//Espera a que haya tres alumnos conectados
		while(numAlumAva < ALUMNOS_AV)
			wait();

		//Mensaje a mostrar cuando el alumno pueda empezar a cursar la parte avanzada
		System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
	}
	
	/* El alumno:
	 *   - informa que ya ha terminado de cursar la parte avanzada 
	 *   - espera hasta que los tres miembros del grupo hayan terminado su parte 
	 */ 
	public synchronized void finAvanzado(int id) throws InterruptedException{
		
		numAlumAva--;
		
		if(numAlumAva > 0)
			//Mensaje a mostrar si el alumno tiene que esperar a que los otros miembros del grupo terminen
			System.out.println("PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");

		if(numAlumAva == 0){
			notifyAll();
			//Mensaje a mostrar cuando los tres alumnos del grupo han terminado su parte
			System.out.println("PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
			realizando = false;
		}
		//Espera a que los 3 alumnos terminen su parte avanzada
		while(numAlumAva > 0)
			wait();
	}
}