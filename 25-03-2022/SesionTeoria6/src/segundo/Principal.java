package segundo;

public class Principal {
    public static void m(){
        System.out.println("a");
    }

    public static void main(String[] args) throws InterruptedException{
        Thread h1 = new Thread(new MiPrimerHebra('A'));
        Thread h2 = new Thread(new MiPrimerHebra('B'));

        MiSegundaHebra h3 = new MiSegundaHebra('W');

        h1.start();
        h2.start();
        h3.start();

        h1.join();
        h2.join();
        h3.join();

    }
}
