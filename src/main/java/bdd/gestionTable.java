package bdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
*
* Au sein de cette class, les fonctions permettent de réaliser la création des tables
* La sélection des données ainsi que l'insertion de données
* Nous nous connecton a une base MySQL avec le connecteur "com.mysql.cj.jdbc.Driver"
*
* */
public class gestionTable {


    private String nomDriverJDBC_duSGBD="com.mysql.cj.jdbc.Driver";


    private String urlJDBC= null;


    private Connection cnx= null;
    private Statement monStatement = null;

    private String userName = null;
    private String password = null;

    //constructeur de la class gestionTable
    public gestionTable(String ip, String NomBDD,String userName, String password) {
        this.urlJDBC = "jdbc:mysql://" + ip + ":3306/"+ NomBDD;
        this.userName = userName;
        this.password = password;
    }

    /*
    * Methode de creation des tables Livres et Lecteurs dans la basse de donnée
    * */
    public void creationTable()
    {

        String requeteTableLecteurs = "CREATE TABLE IF NOT EXISTS Lecteurs(" +
                "Nom varchar(25)," +
                "id int PRIMARY KEY NOT NULL AUTO_INCREMENT" +
                ");";

        String requeteTableLivres = "CREATE TABLE IF NOT EXISTS Livres(" +
                "Nom varchar(25)," +
                "id int PRIMARY KEY NOT NULL AUTO_INCREMENT" +
                ");";

        try {
            cnx= DriverManager.getConnection(urlJDBC,userName,password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            monStatement = cnx.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            monStatement.executeUpdate(requeteTableLecteurs);
            monStatement.executeUpdate(requeteTableLivres);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    * Methode d'insertion d'élément dans la table Livres
    * Cette méthode prend en paramètre un string correspondant au nom du livre ajouter
    * Il n'y a aucun retour
    * */
    public void insertionTableLivre(String name)
    {

        //connection a la base de donnée
        try {
            cnx= DriverManager.getConnection(urlJDBC,userName,password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        PreparedStatement requete = null;

        //on prepare l'insertion afin de pouvoir inserer notre variable
        try {
            //le point d'interrogation est remplacé par la valeur de la variable
            requete = cnx.prepareStatement("INSERT INTO Livres(Nom) VALUES( ? );");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //on remplace la point d'interogation par la valeur de la variable name
            requete.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //insertion dans la base de données
            requete.executeUpdate();

        } catch (SQLException e) {
            System.out.println("insertion non reussi$");
            e.printStackTrace();
        }

        //on ferme la connexion avec la base de données
        try {
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
     * Methode d'insertion d'élément dans la table Lecteurs
     * Cette méthode prend en paramètre un string correspondant au nom du Lecteur ajouter
     * Il n'y a aucun retour
     * */
    public void insertionTableLecteur(String name)
    {

        //connection a la base de donnée
        try {
            cnx= DriverManager.getConnection(urlJDBC,userName,password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        PreparedStatement requete = null;

        //on prepare l'insertion afin de pouvoir inserer notre variable
        try {
            //le point d'interrogation est remplacé par la valeur de la variable
            requete = cnx.prepareStatement("INSERT INTO Lecteurs(Nom) VALUES( ? );");
        } catch (SQLException e) {

            e.printStackTrace();

        }

        try {
            //on remplace la point d'interogation par la valeur de la variable name
            requete.setString(1, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //insertion dans la base de données
            requete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //on ferme la connection a la base de données
        try {
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*
    * Méthode de récupération des Lecteurs dans la base de données
    *
    * On récupère tout les lecteurs dans la base de données
    *
    * On retourne une List<String> de nom de lecteur
    * */
    public List<String> RecuperationLecteur()
    {

        //Notre code sql
        String select = "SELECT * FROM Lecteurs;";

        //connection a la base de données
        try {
            cnx= DriverManager.getConnection(urlJDBC,userName,password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        try {
            monStatement = cnx.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet retour = null;

        //on execute notre requete sql
        try {
            retour = monStatement.executeQuery(select);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> name = new ArrayList<String>();

        /*
        * L'execution de la requete nous retourne un ResultSet
        * Pour parcourir cette élement, il faut le parcourir a l'aide d'une boucle
        * et de la méthode next
        * */

        while(true){
            try {
                if (!retour.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                // on ajoute dans notre list l'élément recupérer correpondant
                // a la colonne Nom de la base de données

                name.add(retour.getString("Nom"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //on ferme la connection
        try {
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }


    /*
     * Méthode de récupération des Livres dans la base de données
     *
     * On récupère tout les livres dans la base de données
     *
     * On retourne une List<String> de nom de livre
     * */
    public List<String> RecuperationLivre()
    {
        // creation de  notre requete sql
        String select = "SELECT * FROM Livres;";

        //connection a la base de donnée
        try {
            cnx= DriverManager.getConnection(urlJDBC,userName,password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ResultSet retour = null;
        try {
            monStatement = cnx.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //on execute notre requete
        try {
            retour = monStatement.executeQuery(select);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> name = new ArrayList<String>();

        /*
         * L'execution de la requete nous retourne un ResultSet
         * Pour parcourir cette élement, il faut le parcourir a l'aide d'une boucle
         * et de la méthode next
         * */
        while(true){
            try {
                if (!retour.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                // on ajoute dans notre list l'élément recupérer correpondant
                // a la colonne Nom de la base de données
                name.add(retour.getString("Nom"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //on ferme la connection
        try {
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

}
