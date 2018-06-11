package com.example.user.triampoules;

import android.app.Activity;

import android.app.DatePickerDialog;

import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;


import static android.content.ContentValues.TAG;


/**
 * Created by Thibault on 30/03/2018.
 */

public class IHM_Liste_Historique extends Activity {


    //Création des composant graphique
    private Button button_afficher_historique;
    private ListView mListView;
    private Button mDisplayDate;
    private TextView Afficher_Date;

    //Composant graphique calendrier
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //Attribut
    private String date;

    //Lien
    private Historique_BDD connectionclass;


    //Création du tableau
    private String[] Liste_Historique = new String[]{};


    //Méthode de fonction d'obtention du String Date
    //méthode utiliser à la classe Historique_BDD
    public String getDate()

    {
        return date;
    }


    //Création de l'IHM
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        //Lien entre la classe IHM_Liste_Historique et Historique_BDD
        connectionclass = new Historique_BDD(this);

        //Composition graphique Button et ListView
        button_afficher_historique = findViewById(R.id.button_afficher_Historique);
        mListView = (ListView) findViewById(R.id.list_View_Historique);
        Afficher_Date = (TextView) findViewById(R.id.Text_View_Date);

        //Création de l'interface permettant les passages des données
        //(de la table archive)

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
       (IHM_Liste_Historique.this, android.R.layout.simple_list_item_1,Liste_Historique);
        mListView.setAdapter(adapter);

        //Composition graphique Button de selection de la date
        mDisplayDate = (Button) findViewById(R.id.button_selectionner_date);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override

            //Obtention des parametre de la date
            //Année, mois et jour
            public void onClick(View view) {
                Calendar calendrier = Calendar.getInstance();
                int Annee = calendrier.get(Calendar.YEAR);
                int Mois = calendrier.get(Calendar.MONTH);
                int Jour = calendrier.get(Calendar.DAY_OF_MONTH);


                //Création de la composition graphique calendrier
                DatePickerDialog dialog = new DatePickerDialog
                (IHM_Liste_Historique.this,android.R.style.Theme_Black,
                mDateSetListener,Annee,Mois,Jour);

                //Ouverture de la composition graphique calendrier
                //En fenetre
                dialog.getWindow().setBackgroundDrawable
                (new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


                //Création du composant Calendrier
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override

                    public void onDateSet(DatePicker datePicker, int Annee, int Mois, int Jour) {

                    Mois = Mois + 1;

                        //Mise en forme du format de la date
                        Log.d(TAG, "yyyy-MM-dd: " + Annee + "-" + Mois + "-" + Jour);


                        String Mois0;

                        //Si le chiffre mois depasse 10
                        //Mettre un 0 devant les mois précédent (06,07,...)

                        if (Mois<10)

                            Mois0 ="0"+Mois;

                        else Mois0=String.valueOf(Mois);

                        //Mise en format de la date avec la selectionner
                         date = Annee + "-" + Mois0 + "-" + Jour;


                        //Ajout des caractères de la date dans un textView
                        Afficher_Date.setText(date);

                       // mDisplayDate.setText(date);


                    }
                };






        button_afficher_historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Reférence de lien de la classe Historique_BDD
                //afin d'utiliser la méthode initConnection2
                connectionclass.initConnexion2("ampoules");


                //Création du Thread
                Thread th = new Thread(connectionclass);
                th.start();

                while (th.isAlive()) ;//Maintien le thread en vie


                //Obtention du tableau de Historique_BDD
                String []tab =connectionclass.getTab();

                //Adaptation de la liste pret a s'etendre selon le nombre de caractére reçu
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(IHM_Liste_Historique.this,
                        android.R.layout.simple_list_item_1,tab);
                mListView.setAdapter(adapter);

            }
        });

    }//Fin onCreate

        //Lien entre classe
        public void setLien_IHM_Liste_HistoriqueToHistoriqueBDD(Historique_BDD historique_bdd)
        {
            this.connectionclass = historique_bdd;
        }



}




