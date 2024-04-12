package com.mycompany.multithreading_c.s_tcp.Client;


import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 *
 * @author Banella Lorenzo
 */

 public class Client extends Thread{

    private String nome;
    private String colore;
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private Scanner scanner;
    private static final String RESET = "\u001B[0m";

    public Client(String nomeDefault, String coloreDefault) {
        this.nome = nomeDefault;
        this.colore = coloreDefault;
        this.scanner = new Scanner(System.in);
    }

    public void connetti(String nomeServer, int portaServer) {
        System.out.println(colore+"Client " + nome + " in esecuzione"+RESET);
        try {
            this.socket = new Socket(nomeServer, portaServer);
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(colore+"Connessione avvenuta con il server"+RESET);
        } catch (ConnectException ex) {
            System.err.println("Il server non è in ascolto!");
            System.err.println(ex.getMessage());
        } catch (UnknownHostException ex) {
            System.err.println("Host sconosciuto!");
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Errore!");
            System.err.println(ex.getMessage());
        }
    }
    

    public void comunica() {
        while (socket != null && !socket.isClosed()) {
            scrivi();
            leggi();
        }
    }
    
    
    @Override
    public void run() {
      comunica();
    }

    public void scrivi() {
        if (socket != null && !socket.isClosed()) {
            System.out.println(colore+"Scrivi :"+RESET);
            String messaggio = scanner.nextLine();
            System.out.println(colore+"Messaggio inviato al server  : "+messaggio+RESET);
            try {
                output.write(messaggio);
                output.newLine();
                output.flush();
                if (messaggio.equalsIgnoreCase("chiudi")) {
                    chiudi();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void leggi() {
        if (socket != null && !socket.isClosed()) {
            String messaggioRicevuto = null;
            try {
                messaggioRicevuto = input.readLine();
                System.out.println(colore+"Messaggio inviato dal server : "+messaggioRicevuto+RESET);
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
            if (socket != null) {
                socket.close();
                System.out.println("Connessione chiusa.");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
