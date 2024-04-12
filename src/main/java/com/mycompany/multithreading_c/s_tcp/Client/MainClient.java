package com.mycompany.multithreading_c.s_tcp.Client;


import java.util.Scanner;

/**
 *
 * @author Banella Lorenzo
 */

public class MainClient {

    public static void main(String[] args) {
        String colore = scegliColoreTesto();
        Client client= new Client("Lorenzo",colore);
        client.connetti("127.0.0.1",1789);
        client.comunica();
    }

    public static String scegliColoreTesto(){
        Scanner scanner = new Scanner(System.in);
        String[] colori = {"Rosso", "Giallo", "Blu", "Verde", "Magenta"}; // Correzione da BLU a VERDE
        // Scelta del colore tramite switch
        System.out.println("Scegli un colore per il terminale:");
        for (int i = 0; i < colori.length; i++) {
            System.out.println((i + 1) + ". " + colori[i]);
        }
        int scelta =scanner.nextInt();
        String colore = null;
    
        switch (scelta) {
            case 1:
                colore = "\u001B[31m";
                break;
            case 2:
                colore = "\u001B[33m";
                break;
            case 3:
                colore = "\u001B[34m";
                break;
            case 4: 
                colore = "\u001B[32m";
                break;
            case 5:
                colore= "\u001B[35m";
                break;
            default:
                System.out.println("Scelta non valida, utilizzando il colore di default.");
        }
        return colore;
    }
    
}
