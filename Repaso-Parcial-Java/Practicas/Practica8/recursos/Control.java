public interface Control {


	public void qRecursos(int id, int num) throws InterruptedException;

	public void libRecursos(int id, int num) ;
}
// CS-1: un proceso tiene que esperar su turno para coger los recursos
// CS-2: cuando es su turno el proceso debe esperar hasta haya recursos
// suficiente
// para él
