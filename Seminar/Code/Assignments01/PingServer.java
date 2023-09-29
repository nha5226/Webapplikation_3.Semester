package Webapplication.Assignments01;

import java.net.*;

public class PingServer {
    public static void main(String[] args) throws Exception {

        // Ich mache ein Datagrampacket mit einem fixen Port 7777 wie in der Übung (socket und bind)
        try(DatagramSocket ds = new DatagramSocket(7777)) {

            byte[] request = new byte[256];
            //ist ausgegraut, weil ich bei response sowieso einen array bekomme bei output.getBytes();
            byte[] response = new byte[256];

            while (true) {
                //Solange richtig ist, wird ein neues Objekt(Packet) erstellt damit wir erhalten können (recvfrom)
                DatagramPacket inDP = new DatagramPacket(request, request.length);

                //Wir bekommen Datagram vom Socket
                //wird, als blocked gekennzeichnet bis er eine Anforderung bekommt etwas zu erhalten
                ds.receive(inDP);
                String input = new String(inDP.getData());

                //Hiermit bekommen wir die IP-Adresse und den Port vom Client
                InetAddress iPAddress = inDP.getAddress();
                int port = inDP.getPort();

                //Hier führen wir eine Operation mit Eingabedaten aus und erstellen eine Antwort
                String output = input.toUpperCase();
                response = output.getBytes();

                //Hier machen wir ein neues Objekt damit wir es versenden können
                DatagramPacket outDP =
                        new DatagramPacket(response, response.length, iPAddress, port);

                //Hiermit können wir schauen, ob es wirklich einen Unterschied gibt
                //Thread.sleep(100);

                //Hier senden wir es (sendto)
                ds.send(outDP);
            }
        }
        //falls es nicht funktioniert haben wir heir eine Exception
        catch (Exception e){
            e.printStackTrace();
        }

    }
}