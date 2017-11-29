/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomudp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Il Magnifico
 */
public class MulticastServerThread extends Thread {

    static final int PORT = 4446;
    protected MulticastSocket socket = null;
    InetAddress group;

    public MulticastServerThread() throws IOException {
        try {
            group = InetAddress.getByName("224.0.1.1");
            socket = new MulticastSocket(PORT);
        } catch (UnknownHostException ex) {
            System.out.println("fuck...");
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("fuck2...");
            System.exit(0);
        }
    }

    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[256];
                String dString = "Server: " + getFromClients();
                System.out.println("Il messaggio è stato elaborato");
                System.out.println(dString);
                buf = dString.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
                socket.send(packet);
                System.out.println("iviato");
                Thread.sleep(2000);
            } catch (IOException ex) {
                System.out.println("fuck");
                System.exit(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(MulticastServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            //socket.close();
        }
    }

    protected String getFromClients() throws IOException {
        DatagramPacket packet;
        String received="";
        try {
            byte[] buf = new byte[256];
            System.out.println("creato buf");
            packet = new DatagramPacket(buf, buf.length);
            System.out.println("sto per ricevere");
            socket.receive(packet);
            System.out.println("ho ricevuto");
            buf = packet.getData();
            received = new String(buf);
            System.out.println("HO RICEVUTO: " + received);
            return received;
        } catch (IOException ex) {
            System.out.println("errore in ricezione");
        }
        return received;
    }
}
