package Merge;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        Random r = new Random();
        List<Integer> l = new ArrayList<Integer>();

        for(int i = 0;i<10;i++){
            l.add(r.nextInt(100));
        }

        System.out.println(l.toString());

        Nodo n = new Nodo(l);
        n.start();
        n.join();
        System.out.println(n.getLista().toString());
    }
}
