package com.example.user.triampoules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 19/03/2018.
 */

public class IHM_Commande extends Activity{

    //Création des composant graphique
    private Button button_direction_droite;
    private Button button_direction_gauche;
    private Button button_direction_haut;
    private Button button_direction_bas;
    private Button button_rotation;
    private Button button_reboot_machine;
    private Button button_cycle_auto;
    private Button button_historique_commande;
    private Button button_aspirer;
    private TextView tv_rep_server;
    private Thread myNet;

    //Référence entre classe
    private ClientTCP refClientTcp = new ClientTCP();

    //Constructeur
   /* public Commande(ClientTCP refClientTcp) {
        this.refClientTcp = refClientTcp;
    }*/


    //Création de l'IHM
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        //Composition graphique Button , Textview et Listener
        button_direction_haut = findViewById(R.id.button_haut);
        button_direction_bas = findViewById(R.id.button_Bas);
        button_direction_droite= findViewById(R.id.button_Droite);
        button_direction_gauche = findViewById(R.id.button_Gauche);
        button_rotation = findViewById(R.id.button_Rotation);
        button_reboot_machine = findViewById(R.id.button_reboot);
        button_cycle_auto = findViewById(R.id.button_auto);
        button_historique_commande = findViewById(R.id.button_historique);
        button_aspirer = findViewById(R.id.button_aspirer);

        tv_rep_server = findViewById(R.id.textview_response2);

        button_direction_haut.setOnClickListener(Haut_Listener);
        button_direction_bas.setOnClickListener(Bas_Listener);
        button_direction_droite.setOnClickListener(Droite_Listener);
        button_direction_gauche.setOnClickListener(Gauche_Listerner);
        button_rotation.setOnClickListener(Rotation_Listener);
        button_reboot_machine.setOnClickListener(Reboot_Listener);
        button_cycle_auto.setOnClickListener(Auto_Listener);
        button_historique_commande.setOnClickListener(Historique_Listener);
        button_aspirer.setOnClickListener(Aspirer_Listener);

        //Référence entre classe
        refClientTcp.setLienClientTCP_To_IHM_Commande(this);

    }//Fin onCreate


    //Création de méthode Thread pour éviter
    // de recrée des Thread différents

    private void lancerThread()
    {
        myNet = new Thread(refClientTcp);
        myNet.start();
       while (myNet.isAlive());

    }


    //Envoie de la chaine de caratère de commande
   private View.OnClickListener Droite_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                   refClientTcp.setMessage("droite");
                  lancerThread();

                }
            };


    private View.OnClickListener Gauche_Listerner
            = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            refClientTcp.setMessage("gauche");
           lancerThread();

        }
    };

    private View.OnClickListener Haut_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    refClientTcp.setMessage("haut");
                    lancerThread();

                }
            };

    private View.OnClickListener Bas_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    refClientTcp.setMessage("bas");
                    lancerThread();

                }
            };

    private View.OnClickListener Rotation_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    refClientTcp.setMessage("tourner");
                    lancerThread();

                }
            };

    private View.OnClickListener Reboot_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    refClientTcp.setMessage("reboot");
                    lancerThread();

                }
            };

    private View.OnClickListener Auto_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    refClientTcp.setMessage("auto");
                    lancerThread();

                }
            };


    private View.OnClickListener Aspirer_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refClientTcp.setMessage("aspirer");
            lancerThread();
        }
    };

    private View.OnClickListener Historique_Listener =
            new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(IHM_Commande.this, IHM_Liste_Historique.class );
                   startActivity(intent);

                }
            };



    //Affichage de la réponse du serveur
    public void afficher (String s)
    {

        tv_rep_server.setText(s);


    }



}





