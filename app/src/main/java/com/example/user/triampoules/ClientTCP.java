package com.example.user.triampoules;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.Socket;     

/**
 * Created by Thibault on 26/03/2018.
 */

public class ClientTCP implements Runnable {

    private IHM_Commande commandeActivity;

    //Attribut
    private String message ;
    private String reception;


    //Constructeur
    public ClientTCP() {
        this.commandeActivity = commandeActivity;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public void run() {
        PrintWriter out;


        BufferedReader in;

        //Port de connexion
        int port = 4444;

        //Adresse IP de la machine
        String IP ="172.30.121.17";


        //Adresse IP de Denis pour les test
        //String IP ="172.30.1.12";
        //String IP =  "192.168.43.495";

        try {

            //Recupération de l'addresse IP communicante
            InetAddress serverAddr = InetAddress.getByName(IP);

            //Création de la socker
            Socket socket = new Socket(serverAddr, port);

            out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try {

                //ecrire un message au serveur
                out.println(message);


                //lire un message envoyé depuis le serveur
                reception = in.readLine();
                commandeActivity.afficher(reception);

            } catch(Exception e) {


            } finally {

                //Fermeture de la communication
                in.close();
               out.close();

               //Fermuture de la socket
               socket.close();
            }

        } catch (Exception e) {

        }

    }

    //Lien entre classe
    public void setLienClientTCP_To_IHM_Commande(IHM_Commande commandeActivity)
    {
        this.commandeActivity = commandeActivity;
    }
}
