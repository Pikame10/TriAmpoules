package com.example.user.triampoules;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Created by Thibault on 03/04/2018.
 */

public class Historique_BDD implements Runnable {

    //Attributs
    private String url;
    private String resultat2;
    private String requete_voir;
    private String date;
    private String[] tab;

    //Création d'objets
    private Statement stmt;
    private Connection connexion;
    private ResultSet rs;
    private IHM_Liste_Historique historique;



    //CONSTRUCTEUR
    public Historique_BDD(IHM_Liste_Historique historique)
    {
        this.historique = historique;

    }



    //Méthode de connexion à la bdd
    public void initConnexion2(String NomBDD) {

        url = "jdbc:mysql://" + "172.30.1.5" + "/" + NomBDD;
        // this.log=log;
        //this.pass=pass;

    }


    //Obtention du tableau
    public String[] getTab()
    {
        return tab;
    }


    //Fonction principale
    @Override
    public void run() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            //Lien entre classe
            historique.setLien_IHM_Liste_HistoriqueToHistoriqueBDD(this);

            //Obtention de la date via le DatePickerDialog en String
            date = historique.getDate();

            //Connexion à la bdd
            connexion = (Connection) DriverManager.getConnection(url, "admin", "root");
            stmt = (Statement) connexion.createStatement();

            //Requete SQL de la table archive avec la date
            requete_voir = "SELECT * FROM archive WHERE DATE = '" + date + "'";

            //Execution de la requete
            rs = stmt.executeQuery(requete_voir);

            //Création de la liste afin d'afficher l'historique
            // stocké à la table archive de la bdd
            ArrayList<String> tab_histo = new ArrayList<>();


            while (rs.next()) {

               // resultat2 += rs.getString("Culot_idCulot") + "\n";
                //resultat2 += rs.getString("idAmpoule") + "\n";
                //resultat2 += rs.getString("Materiau_idMateriau") + "\n";
                //resultat2 += rs.getString("") + "\n";
                resultat2="";
                //Incrémentation des colonnes de la table archive dans la ArrayList
               resultat2 += rs.getString("commande") + " ";
               resultat2 += rs.getString("Date") + "\n";

               tab_histo.add(resultat2);


            }


            //Etendre la taille du tableau afin d'afficher une infinité de String
            tab = new String[tab_histo.size()];

            for (int i = 0; i < tab_histo.size(); i++) {
                tab[i] = tab_histo.get(i);

            }



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}