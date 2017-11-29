/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomudp;

import java.io.*;
import java.net.*;
import java.util.Random;


/**
 *
 * @author Il Magnifico
 */
public class Client {

    private MulticastSocket socket;
    private final int port = 4446;
    protected static InetAddress address;

    public Client() throws IOException {
        socket = new MulticastSocket(port);
        address = InetAddress.getByName("224.0.1.1");
        socket.joinGroup(address);
        Thread cI = new Thread(new ChatIn());
        Thread cO = new Thread(new ChatOut());
        cI.start();
        cO.start();
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
    }

//_________________________________________________________
    class ChatIn implements Runnable {

        public ChatIn() throws IOException {
        }

        @Override
        public void run() {
            DatagramPacket packet;
            while (true) {
                try {                  
                    byte[] buf = new byte[256];
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    buf = packet.getData();
                    String received = new String(buf);
                    System.out.println(received);
                } catch (IOException ex) {
                    System.out.println("errore in ricezione");
                }
            }
        }
    }

//______________________________________________________________
    
    private String nomeUtente;
    
    class ChatOut implements Runnable {

        private final BufferedReader tastiera;
        private byte[] buf;

        public ChatOut() throws IOException {        
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inserisci nome utente;");
            nomeUtente=tastiera.readLine();
            this.buf = new byte[256];
        }

        public void run() {
            DatagramPacket packet;
            while (true) {
                try {
                    String ris ="Client_"+nomeUtente+": "+ tastiera.readLine();
                    buf = ris.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                    Thread.sleep(3000);
                } catch (IOException ex) {
                    System.out.println("errore in invio");
                } catch (InterruptedException ex) {
                    System.out.println("errore in invio");
                }
            }
        }
    }
}
