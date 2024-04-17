package com.mycompany.multithreading_c.s_tcp.Server;

/**
 *
 * @author Banella Lorenzo
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiServer server=new MultiServer(1789);
        server.start();
    }
    
}
