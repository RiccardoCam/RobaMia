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

    private static long FIVE_SECONDS = 5000;
    static final int PORT = 4446;
    protected MulticastSocket socket = null; // out
    InetAddress group;

    public MulticastServerThread() throws IOException {
    }

    public void run() {
        try {
            group = InetAddress.getByName("224.0.0.1");
            try {
                socket = new MulticastSocket(PORT);
            } catch (IOException ex) {
                Logger.getLogger(MulticastServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MulticastServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                byte[] buf = new byte[256];
                String dString = "Server: " + getNextQuote();
                buf = dString.getBytes();
                InetAddress group = InetAddress.getByName("224.0.0.1"); 
                socket = new MulticastSocket(PORT);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
                socket.send(packet);
                Thread.sleep(2000);
            } catch (IOException ex) {
            } catch (InterruptedException ex) {
                Logger.getLogger(MulticastServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();
        }
    }

    protected String getNextQuote() throws IOException {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

}
