package Webapplication.Assignments02;

import java.util.Random;

public class Spieler implements Runnable {

    //RandomChoice mit den 3 auswahlen
    private Random randomchoice = new Random();
    //threadName da hier der name reinkommt
    private String threadName;
    //Value für die Random Choice weil es von 0-2 geht
    private int value;
    //Counter ist dann dafür da um runterzuzählen für die Runden
    private int counter;

    public Spieler(String threadName, int round) {
        this.threadName = threadName;
        this.counter = round;
    }


    public synchronized void run() {

        while (true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Hier wird die Random Zahl generiert
            value = randomchoice.nextInt(3);

            //Thread wird sonst nicht aufgeweckt
            notify();

            //Hier wird die eingegebene Zahl von Runden gemacht und durchgezählt
            counter--;
            if (counter <= 0) {
                break;
            }
        }
    }


    public synchronized Choice getChoice() throws InterruptedException {

        //Thread wird aufgeweckt
        notify();
        //2 Sekunden warten bis Value generiert wird
        wait(2000);

        //Hier wird die Auswahl eingeloggt
        Choice choiceofspieler = Choice.values()[value];

        //Wir bekommen generierte Auswahl zurück
        return choiceofspieler;
    }

    public int getCounter() {
        return counter;
    }

}