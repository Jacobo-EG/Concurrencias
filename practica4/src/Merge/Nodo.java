package Merge;
/*
Practica 4, 01-04-2022, Concurrencias, Jacobo Elicha Garrucho
*/

import java.util.*;


class Nodo extends Thread{

    private List<Integer> lista;

    public Nodo(List<Integer> lista) {
        this.lista = lista;
    }

    public void aniade(List<Integer> I, int desde, int hasta){
        for(int i = desde; i <= hasta; i++)
            I.add(lista.get(i));
    }

    public List<Integer> getLista(){
        return lista;
    }

    public void mezclar(List<Integer> I1, List<Integer> I2){

        int i=0,j=0,k=0;

        while(i < I1.size() && j < I2.size()){
            if(I1.get(i) < I2.get(j)){
                lista.set(k, I1.get(i));
                k++;
                i++;
            }else if(I1.get(i) > I2.get(j)){
                lista.set(k, I2.get(j));
                j++;
                k++;
            }else{
                lista.set(k, I1.get(i));
                i++;
                j++;
                k++;
            }
        }

        while(i < I1.size()){
            lista.set(k, I1.get(i));
            i++;
            k++;
        }

        while(j < I2.size()){
            lista.set(k, I2.get(j));
            j++;
            k++;
        }
        
    }

    public void run(){
        if(lista.size()>=2){
            List<Integer> l1 = new ArrayList<Integer>();
            List<Integer> l2 = new ArrayList<Integer>();

            aniade(l1, 0, lista.size()/2-1);
            aniade(l2, lista.size()/2, lista.size()-1);

            Nodo n1 = new Nodo(l1);
            Nodo n2 = new Nodo(l2);

            n1.start();
            n2.start();

            try {
                n1.join();
                n2.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mezclar(n1.getLista(), n2.getLista());

        }
    }

}