package Fibonacci;

public class Fib extends Thread{
    private int idf;
    private int valor;
    private int previo;

    public Fib(int idf){
        this.idf = idf;
    }

    public int getValor(){
        return valor;
    }

    public int getPrevio(){
        return previo;
    }

    public void run(){
        if(idf == 0){
            valor = 0;
        }else if(idf == 1){
            previo = 0;
            valor = 1;
        }else{
            Fib n1 = new Fib(idf-1);

            n1.start();

            try {
                n1.join();
                previo = n1.getValor();
                valor = previo + n1.getPrevio();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
}
