package com.example.user.triampoules;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Gestionnaire_de_Connexion_BDD implements Runnable {

    //Attribut
    private String url;
    private String log;
    private String pass;
    private String resultat_Nom_str="";
    private String resultat_Motdepasse_str="";
    private String resultat = "rien";


    //Création des composant graphique
    private Statement stmt;
    private Connection connexion;

    //Valeur de retour
    private boolean result_Nom = false;
    private boolean result_MDP = false;



    public String getResultat() {

        return resultat;
    }

    //Méthode de connexion à la bdd
    public void initConnection(String NomBDD, String log, String pass) {
        url = "jdbc:mysql://" + "172.30.1.5" + "/" + NomBDD;
        this.log=log;
        this.pass=pass;
    }



    @Override
    public void run() {


        try {
            Class.forName("com.mysql.jdbc.Driver");

            //Connexion à la bdd
            connexion = (Connection) DriverManager.getConnection(url, "admin", "root");
            stmt = (Statement) connexion.createStatement();


            //Création de la Requete SQL de la table opérateur
            String query = "SELECT Nom, Motdepasse FROM operateur";
            ResultSet rs;

            //Execution de la requete
            rs = stmt.executeQuery(query);


            // Extraction depuis le resultset
            while (rs.next()) {

                //Obtention des identifiant Nom/Mot De Passe
                resultat_Nom_str = rs.getString("Nom");
                resultat_Motdepasse_str = rs.getString("Motdepasse");

                //Comparaison du login saisit/stocké à la bdd
                if (resultat_Nom_str.equals(log))

                {
                    result_Nom = true;

                }

                //Comparaison du Mot de passe saisit/stocké à la bdd
                if (resultat_Motdepasse_str.equals(pass))

                {
                    result_MDP = true;

                }

                //Comparaison Identifiant login / mot de passe
                if (result_Nom && result_MDP)

                {
                    resultat = "connecté";

                }


            }//Fin while

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resultat = "erreur ";
        } catch (SQLException e) {
            e.printStackTrace();
            resultat = "erreur2 ";
        }


    } // fin classe
}