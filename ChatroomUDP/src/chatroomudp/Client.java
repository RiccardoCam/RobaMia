/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomudp;

import static chatroomudp.MulticastServerThread.PORT;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Il Magnifico
 */
public class Client implements Runnable {

    protected static MulticastSocket socket;
    protected static InetAddress address;

    public Client(){
        
    }
    
    @Override
    public void run() {
        try {
            socket = new MulticastSocket(4446);
            address = InetAddress.getByName("224.0.0.1");
            socket.joinGroup(address);

            System.out.println("OK");
            while (true) {
                DatagramPacket packet;
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
            }
        } catch (IOException ex) {

        }
    }

    public static void main(String[] args) throws IOException {
        Client c=new Client();
        c.run();
        scrivi();
    }

    protected static void scrivi() throws IOException {
        while (true) {
            Scanner tastiera = new Scanner(System.in);
            byte[] buf = new byte[256];
            String dString = tastiera.nextLine();
            buf = dString.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
            socket.send(packet);
        }
    }
}
