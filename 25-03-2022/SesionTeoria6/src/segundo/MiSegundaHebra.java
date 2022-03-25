package segundo;

public class MiSegundaHebra extends Thread{
    Character myChar;

    public MiSegundaHebra(Character myChar){
        this.myChar = myChar;
    }

    public void run(){
        for(int i = 0; i < 20; i++){
            System.out.println(myChar);
        }
    }
}
