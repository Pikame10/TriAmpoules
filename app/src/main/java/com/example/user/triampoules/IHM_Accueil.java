package com.example.user.triampoules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Thibault on 15/01/2018.
 */

public class IHM_Accueil extends Activity {


    //Création des composant graphique
    private Button btConnect;
    private EditText Nom,MDP;


    //Attributs
    private String et_Nom;
    private String et_MDP;



    //Composition
    private Gestionnaire_de_Connexion_BDD refbdd = new Gestionnaire_de_Connexion_BDD();


    //Création de l'IHM
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Composition graphique Button d'EditText

        btConnect = findViewById(R.id.button);
        Nom = findViewById(R.id.editText);
        MDP = findViewById(R.id.editText2);




        btConnect.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

               //Intent intent = new Intent(IHM_Accueil.this, IHM_Commande.class );startActivity(intent);

                //Obtention des EditText saisit par l'opérateur
                et_Nom = Nom.getText().toString();
                et_MDP = MDP.getText().toString();

                //Reférence de lien de la classe Gestionnaire_de_Connexion_BDD
                //afin d'utiliser la méthode initConnection
                refbdd.initConnection("ampoules", et_Nom, et_MDP);


                //Création du Thread
                Thread th = new Thread(refbdd);

                th.start();

                while (th.isAlive());//Maintien le thread en vie



                String resultat = refbdd.getResultat();

                //Si le résultat est égale à connecté
                if (resultat.equals("connecté")) {

                    //Envoie de la notification sous forme de popup
                    Toast.makeText(IHM_Accueil.this, "Vous êtes connecté", Toast.LENGTH_SHORT).show();

                    //Passage l'ihm suivante quand la connexion est effectué
                   Intent intent = new Intent(IHM_Accueil.this, IHM_Commande.class );startActivity(intent);

                } else {

                    //Si les editText sont vide
                    if (et_Nom.isEmpty() || et_MDP.isEmpty()) {

                        Toast.makeText(IHM_Accueil.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();

                    } else
                    //Ou sinon si erreur de saisit
                    {
                        Toast.makeText(IHM_Accueil.this, "Identifiant et/ou mot passe incorrect", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }


}



