package com.mycompany.multithreading_c.s_tcp.Server;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Banella Lorenzo
 */

public class ServerThread extends Thread{

    private Socket clientSocket;
    private int numeroClient;
    private BufferedReader input;
    private BufferedWriter output;
    private Scanner scanner;
    

    public ServerThread(Socket clientSocket,int numeroClient) {
        this.numeroClient=numeroClient;
        this.scanner = new Scanner(System.in);
        try {
            this.clientSocket=clientSocket;
            if(clientSocket != null){
                output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
            }
        }
        catch (IOException ex) {
            System.err.println("Errore nella fase di connessione con il client "+numeroClient);
            System.err.println(ex.getMessage());
        }
    }
    
    

    

    @Override
    public void run(){
        comunica();
    }

    
    public synchronized   void comunica(){
        while(!clientSocket.isClosed() ){
                leggi();

                scrivi();
            
        }
    }

    public synchronized  void scrivi() {
        if(!clientSocket.isClosed()){
            System.err.println("Scrivi al client  "+numeroClient);
            String messaggio = scanner.nextLine();
            System.out.println("Messaggio inviato al client   "+numeroClient+ " :"+messaggio);
            try {
                output.write(messaggio);
                output.newLine();
                output.flush(); 
                if(messaggio == null){
                    chiudi();
                    
                }else if(messaggio.equalsIgnoreCase("chiudi") ){
                    chiudi();
                    
                }
                
            } catch (IOException ex) {
                System.err.println( ex.getMessage());
            }
        }
    }

    public void leggi() {
        if(!clientSocket.isClosed()){
            String messaggioRicevuto=null;
            try {
                messaggioRicevuto = input.readLine(); //assegna alla variabile il messaggio ricevuto dal client
                System.out.println("Messaggio inviato dal client "+numeroClient+ " : " + messaggioRicevuto); 
                if(messaggioRicevuto == null){
                    chiudi();
                }else if(messaggioRicevuto.equalsIgnoreCase("chiudi") ){
                    chiudi();
                }
            } catch (IOException ex) {
                 System.err.println(ex.getMessage());
            }
        }
       
    }

    public void chiudi() {
        try {
                // Chiudo il socket 
                this.clientSocket.close();
                System.out.println("Connessione chiusa con il client "+numeroClient);
            
            } catch (IOException ex) {
                System.err.println("Errore");
                System.err.println(ex.getMessage());
            }
    }

    

}

