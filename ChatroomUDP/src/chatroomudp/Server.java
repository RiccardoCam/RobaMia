/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomudp;

/**
 *
 * @author Il Magnifico
 */
public class Server{
public static void main(String[] args) throws java.io.IOException {
        new MulticastServerThread().start();
        System.out.println("server start");
    }
    
}
