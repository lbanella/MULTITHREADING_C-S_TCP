package com.mycompany.multithreading_c.s_tcp.Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Banella Lorenzo
 */
public class MultiServer  {
    private ArrayList<ServerThread> clientConnessi;
    private  int clientCheSiSonoCollegati;
    private   ServerSocket serverSocket;
    private  int porta;
   
  


    public MultiServer(int porta){
        this.clientConnessi=new ArrayList<>();
        this. clientCheSiSonoCollegati=0;
        this.porta=porta;
        try {
            this.serverSocket=new ServerSocket(this.porta);
            System.out.println("Il server è in ascolto sulla porta : "+this.porta);
           
        } catch(BindException ex){
            System.err.println("Porta gia in uso");
            System.err.println(ex.getMessage());
        }catch (IOException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public Socket attendi() {
        try {
            return serverSocket.accept();
        }
        catch (NullPointerException ex) {
            System.err.println(ex.getMessage());
            return null;
        } 
        catch (SocketException ex) {
             System.err.println("Errore nella fase di connessione con il client "+clientCheSiSonoCollegati);
            System.err.println(ex.getMessage());
            return null;
        } 
        catch (IOException ex) {
            System.err.println("Errore durante l'attesa della connessione");
            System.err.println(ex.getMessage());
            return null;
        }
    }

   

    public void start(){
        while(true){
           if (clientConnessi.size()==1) {
                System.out.println("1 client connesso ");
            }
            else{
                System.out.println(clientConnessi.size()+" client connessi ");
            }

            Socket socket= this.attendi();
            
           
            ServerThread serverThread=new ServerThread(socket);
            
            clientConnessi.add(serverThread);
            
            serverThread.start();

            
            Iterator<ServerThread> iterator = clientConnessi.iterator();
            while (iterator.hasNext()) {
                ServerThread s = iterator.next();
                if (s.getSocket().isClosed()) {
                    iterator.remove();
                }
            }
        
        }
    }
}