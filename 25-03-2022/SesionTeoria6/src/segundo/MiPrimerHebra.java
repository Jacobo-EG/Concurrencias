package segundo;

public class MiPrimerHebra implements Runnable{

    Character myChar;

    public MiPrimerHebra(Character myChar){
        this.myChar = myChar;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            System.out.println(myChar);
        }        
    }
    
}
