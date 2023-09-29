package Webapplication.Assignments01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Scanner;

//PING [-s] {IP_address | host_name} [size] [quantity]
//PING -s localhost 64 12

public class PingClient {

    public static void main(String[] args) throws IOException {

        //Hier erstellen wir ein neuen Datagram Socket und wir verbinden ihn mit einem freien Port
        //deswegen schreiben wir nichts in die Klammer (socket und bind)
        DatagramSocket ds = new DatagramSocket();

        //wenn ich keine antwort innerhalb von 10 sekunden kriege, gehe ich davon aus, dass ich nix bekomme
        ds.setSoTimeout(1000);

        //InetAddress repräsentiert die Klasse als IPAdresse
        InetAddress IPA;

        //Hier bestimmen wir den Port
        int port = 7777;

        //Hier sind alle definitionen damit man PING eingeben muss und es somit auch eine Exception gibt
        Scanner scan = new Scanner(System.in);
        String command;
        command = scan.nextLine();

        //Hier wird der command mit PING gestartet
        if(command.startsWith("PING")){
            //wenn beim Pingen NICHT der Parameter -s an der ersten Stelle steht der Parameter -s an der ersten Stelle steht
            if(!args[1].equals("-s")){

                //hier holen wir uns die IP-Adresse und den Hostname
                IPA = InetAddress.getByName((args[1]));

                //Hier die Länge der einträge und austräge
                byte[] request = new byte[256];
                byte[] response = new byte[256];

                // Wir erstellen ein neues Datagram Packet um zu versenden
                DatagramPacket output = new DatagramPacket(request, request.length, IPA, port);
                ds.send(output);
                // Wir erstellen ein neues Datagram Packet um zu erhalten
                DatagramPacket input = new DatagramPacket(response, response.length);
                ds.receive(input);

                //Wie beim Übungsblatt bekommen wir damit den Host und wir können damit sehen, dass er aktiv ist und läuft
                //das passiert jedoch nur, wenn der zweite Parameter NICHT -s ist
                System.out.println(IPA.getHostName() + " is alive");

            }
            //wenn beim Pingen der Parameter -s an der ersten Stelle steht
            else if (args[1].equals("-s")){
                //Hier bekommen wir wieder die IP-Adresse
                IPA = InetAddress.getByName(args[2]);

                //An der dritten Stelle wo size steht
                //parseInt = Analysiert das String-Argument als dezimale Ganzzahl mit Vorzeichen.
                int size = Integer.parseInt(args[3]);
                //An der vierten Stelle wo quantity steht
                int quantity = Integer.parseInt(args[4]);
                //hier ist ein counter wo man sehen kann wie viele Packete man bekomemn hat
                int counter = 0;
                //Hier sieht man eine Liste durch die wir die Dauer der Antwort bekommen
                LinkedList<Float> TotalTimes = new LinkedList<>();

                byte[] request = new byte[size];
                byte[] response = new byte[size];
                //in der Angabe wird angezeigt, dass 64 bytes genommen werden aber nur 56 ankommen
                System.out.println("PING " + IPA.getHostName() + "(" + IPA + ")" + ": " + (size - 8) + " data bytes");

                for(int x = 0; x < quantity; x++){
                    // wir erstellen ein neues DatagramPacket um zu senden
                    DatagramPacket output = new DatagramPacket(request, size, IPA, port);
                    long stime = System.nanoTime();
                    ds.send(output);

                    // wir erstellen ein neues DatagramPacket um zu empfangen
                    DatagramPacket input = new DatagramPacket(response, size);
                    ds.receive(input);


                    float etime = (System.nanoTime() - stime) / 1000;
                    TotalTimes.add(etime);
                    //hier wird der counter hochgezählt (Anzahl der bekommenen Packete)
                    counter++;

                    System.out.printf(input.getLength() + " bytes from " + IPA + ": icmp_seq= " + x + "time = %.0f ms \n", etime);
                }
                //mit packetlost wird angezeigt welche Packets verloren gegangen sind
                int packetslost = ((counter * 100) / quantity) - 100;
                //hier werden das minimum und maximum und der durchschnitt berechnet
                float min = minimum(TotalTimes);
                float avg = average(TotalTimes, counter);
                float max = maximum(TotalTimes);

                // Hier werden die Kommazahlen weggemacht
                int min1 = (int)min;
                int avg1 = (int)avg;
                int max1 = (int)max;

                //Hier werden die untersten 3 Zeilen ausgegeben
                System.out.println("\n----" + IPA.getHostName() + " PING Statistics----");
                System.out.println(quantity + " packets transmitted, " + counter + " packets recieved, " + packetslost + "% packet loss");
                System.out.println("round-trip (ms)   min/avg/max = " + min1  + "/" + avg1 + "/" + max1 );


            }
            //Client wird geschlossen
            ds.close();
        } else {
            //Falls man nicht PING am anfang eingibt wird eine RuntimeException ausgegeben und dazu noch das in ""
            throw new RuntimeException("\nPlease Start with PING :)");
        }


    }

    //Hier wird das minimum berechnent
    public static float minimum(LinkedList<Float> TotalTimes){

        float min = TotalTimes.getFirst();
        for(Float number : TotalTimes){
            if(number < min){
                min = number;
            }
        }
        return min;
    }

    //Hier wird der Durchschnitt berechnet summe/ anzahl der datenpackete
    public static float average(LinkedList<Float> TotalTimes, int counter){
        float avg;
        float sum = 0;
        for(Float number : TotalTimes){
            sum = sum + number;
        }
        avg = sum/counter;
        return avg;
    }

    //Hier wird das maximum genau gleich wie das minimum berechnet
    public static float maximum(LinkedList<Float> TotalTimes){
        float max = 0;
        for(Float number : TotalTimes){
            if(number > max){
                max = number;
            }
        }
        return max;
    }



}
