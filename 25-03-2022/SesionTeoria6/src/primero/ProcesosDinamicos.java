package primero;

//https://docs.oracle.com/javase/8/docs/api/java/lang/Process.html
import java.io.File;
import java.io.IOException;

public class ProcesosDinamicos {

    private static Process start;

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("descifrar.exe", "./imagen/imagen2Encriptadaa.png",
                "./imagen/imagen2.png");

        pb.redirectError(new File("SalidaError.txt"));
        pb.redirectOutput(new File("SalidaNormal.txt"));
        
        Process p = pb.start();
        p.waitFor();
        System.out.println("Salida " + p.exitValue());

    }

    public static Process getStart() {
        return start;
    }

    public static void setStart(Process start) {
        ProcesosDinamicos.start = start;
    }
}