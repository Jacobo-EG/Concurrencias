package Fibonacci;

public class Main {
    public static void main(String[] args) throws Exception {
        int fibIndex = 10;
        Fib fibonacci = new Fib(fibIndex);
        fibonacci.start();

        fibonacci.join();

        System.out.println(fibonacci.getValor());
    }
}
