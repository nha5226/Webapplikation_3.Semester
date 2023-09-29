package Webapplication.Assignments02;

import java.util.Scanner;

public class RSP {

    static int spieler1wins = 0;
    static int spieler2wins = 0;
    static int draws = 0;

    public static void main(String[] args) throws InterruptedException {


        Scanner scan = new Scanner(System.in);
        String command;
        command = scan.nextLine();

        if (command.startsWith("Start")) {

            System.out.println("Bitte Anzahl der Runden eingeben!");

            //Damit wir die Nummer eingeben kann
            String Number = scan.nextLine();
            int round = Integer.parseInt(Number);

            //Anzahl der Spiele
            //Spieler 1 wird erstellt
            Spieler spieler1 = new Spieler("Thread 1", round);
            //Spieler 2 wird erstellt
            Spieler spieler2 = new Spieler("Thread 2", round);

            //Thread für Spieler 1 wird gestartet
            new Thread(spieler1).start();
            //Thread für Spieler 2 wird gestartet
            new Thread(spieler2).start();


            for (int x = 1; x <= round; x++) {
                //Jede Sekunde wird ein neues Spiel gestartet
                Thread.sleep(1000);

                //wir generieren die Auswahl
                Choice choiceofspieler1;
                Choice choiceofspieler2;

                //Wir geben dem Spieler 1 die Auswahl
                choiceofspieler1 = spieler1.getChoice();
                //Wir geben dem Spieler 2 die Auswahl
                choiceofspieler2 = spieler2.getChoice();

                //Zeigt in der Console die Spiele an
                System.out.printf("\n%3d\n" +
                        "Thread 1 - %8s\n" +
                        "Thread 2 - %8s\n",
                        x,
                        choiceofspieler1.name(),
                        choiceofspieler2.name());


                //Wenn die gleiche Auswahl getroffen wurde = draw
                if (choiceofspieler1 == choiceofspieler2) {
                    //draw wird eines raufgezählt damit man am Schluss eine Übersicht von allem hat
                    draws++;
                    System.out.println("Draw! You both picked the same!");
                    System.out.println("-----------------------------------");

                    //Wenn spieler 1 Stein nimmt
                } else if (choiceofspieler1 == Choice.ROCK){
                    //und Spieler 2 Schere hat Spieler 1 gewonnen
                    if (choiceofspieler2 == Choice.SCISSOR) {
                        spieler1wins++;
                        System.out.println("Congrats! Thread 1 wins!");
                        System.out.println("-----------------------------------");

                    } else {
                        //bei Papier gewinnt Spieler 2 und wenn er Stein nimmt, kommt ein draw
                        spieler2wins++;
                        System.out.println("Congrats! Thread 2 wins!");
                        System.out.println("-----------------------------------");
                    }

                    //Wenn Spieler 1 Papier nimmt
                } else if (choiceofspieler1 == Choice.PAPER) {

                    //und Spieler 2 Stein hat Spieler 1 gewonnen
                    if (choiceofspieler2 == Choice.ROCK) {
                        spieler1wins++;
                        System.out.println("Congrats! Thread 1 wins!");
                        System.out.println("-----------------------------------");

                    } else {
                        //bei Schere gewinnt Spieler 2 und bei Papier kommt ein draw
                        spieler2wins++;
                        System.out.println("Congrats! Thread 2 wins!");
                        System.out.println("-----------------------------------");
                    }

                    //Wenn Spieler 1 Schere nimmt
                } else if (choiceofspieler1 == Choice.SCISSOR) {

                    //Und Spieler 2 Papier hat Spieler 1 gewonnen
                    if (choiceofspieler2 == Choice.PAPER) {
                        spieler1wins++;
                        System.out.println("Congrats! Thread 1 wins!");
                        System.out.println("-----------------------------------");

                    } else {
                        //und bei Stein gewinnt Spieler 2 und bei Schere kommt ein draw
                        spieler2wins++;
                        System.out.println("Congrats! Thread 2 wins!");
                        System.out.println("-----------------------------------");
                    }
                }
            }

            //Es wird das Endergebnis augegeben
            System.out.println("---------------------------------------------------------------------------");
            System.out.printf("\n\nThread 1 wins - %3d times;\n" +
                    "Thread 2 wins - %3d times;\n" +
                    "Draw result   - %3d times;\n\n",
                    spieler1wins, spieler2wins, draws);



        }
    }
}