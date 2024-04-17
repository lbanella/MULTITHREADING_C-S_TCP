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
    
    private BufferedReader input;
    private BufferedWriter output;
    private Scanner scanner;
    private String nomeClientConnesso;
    private String colore;
    private static final String RESET = "\u001B[0m";
    

    public ServerThread(Socket clientSocket) {
       
        
        this.scanner = new Scanner(System.in);
        try {
            this.clientSocket=clientSocket;
            if(clientSocket != null){
                output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
            }
        }
        catch (IOException ex) {
            System.err.println("Errore nella fase di connessione con il client "+nomeClientConnesso);
            System.err.println(ex.getMessage());
        }
    }
    
    

    

    @Override
    public void run(){
        leggiNome();
        leggiColore();
        System.out.println(colore+"Connessione stabilita con il Client "+nomeClientConnesso+RESET);
        comunica();
    }

    public Socket getSocket(){
        return clientSocket;
    }
    
    public void comunica(){
        
        while(!clientSocket.isClosed() ){
            leggi();
            scrivi();
        }           
        
    }

    public void scrivi() {
        
        if(!clientSocket.isClosed()){
           
            System.err.println(colore+"Scrivi al client  "+nomeClientConnesso+RESET);
            String messaggio = scanner.nextLine();
            System.out.println(colore+"Messaggio inviato al client   "+nomeClientConnesso+ " :"+messaggio+RESET);
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

    public void leggiNome() {
        if(!clientSocket.isClosed()){
            String messaggioRicevuto=null;
            try {
                messaggioRicevuto = input.readLine(); 
                this.nomeClientConnesso=messaggioRicevuto;
            } catch (IOException ex) {
                 System.err.println(ex.getMessage());
            }
        }
       
    }



    public void leggiColore() {
        if(!clientSocket.isClosed()){
            String messaggioRicevuto=null;
            try {
                messaggioRicevuto = input.readLine(); 
                this.colore=messaggioRicevuto;
            } catch (IOException ex) {
                 System.err.println(ex.getMessage());
            }
        }
       
    }



    public void leggi() {
        if(!clientSocket.isClosed()){
            String messaggioRicevuto=null;
            try {
                messaggioRicevuto = input.readLine(); //assegna alla variabile il messaggio ricevuto dal client
                System.out.println(colore+" Messaggio inviato dal client "+nomeClientConnesso+ " : " + messaggioRicevuto+RESET); 
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
                System.out.println("Connessione chiusa con il client "+nomeClientConnesso);
            
            } catch (IOException ex) {
                System.err.println("Errore");
                System.err.println(ex.getMessage());
            }
    }

    

}