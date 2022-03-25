package tercero;

class DormirHebra {

    public static void main(String args[]) throws InterruptedException {
        java.util.Random rnd = new java.util.Random();
        int valor;

        for (int i = 0; i < 20; i++) {
            valor = rnd.nextInt(100);
            System.out.println("Valor: " + valor);
            Thread.sleep(4000);
        }
    }

}
