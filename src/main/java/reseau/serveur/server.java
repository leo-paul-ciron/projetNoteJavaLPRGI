package reseau.serveur;

import bdd.gestionTable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class server {

    //variable permettant de gérer la boucle permettant de garder le serveur on
    public static int conditionBloucle = 0;

    public static void main(String[] args) {
        int noPort=1234;//port du serveur
        ServerSocket monServerSocket=null;


        //création du socket serveur sur le port 1234
        try {
            monServerSocket=new ServerSocket(noPort);
            System.out.println("ServerSocket obtenu");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        //phase de parametrage de l'application pour gérer la base de données
        Scanner scan = new Scanner(System.in);

        System.out.println("Entrer l'adresse IP du serveur MySQL (ex: 0.0.0.0)");
        String ip = scan.nextLine();

        System.out.println("Entrer le nom de votre base de données (ex: gestionBibliotheque)");
        String BDDname = scan.nextLine();

        System.out.println("Entrer le nom d'utilisateur de votre base de données");
        String Username = scan.nextLine();

        System.out.println("Entrer le mot de passe de votre base de données");
        String password = scan.nextLine();

        gestionTable MaGestionTable = new gestionTable(ip,BDDname, Username,password);



        //boucle pour garder le serveur on
        while (conditionBloucle == 0) {

            Socket maSocket = null;
            //tentative de récuperation du socket
            try {

                maSocket = monServerSocket.accept();
                System.out.println("Socket obtenu");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    monServerSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(-2);
            }

            //lancement du thread une fois la connexion du client au serveur effectuer
            //on passe notre socket et notre instance de gestion de base de données
            new ServiceThread(maSocket, MaGestionTable).start();

        }

        try {
            monServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServiceThread extends Thread {


        private Socket maSocket;
        private gestionTable MaGestionTable;
        public ServiceThread(Socket socketOfServer, gestionTable maGestionTable) {
            this.maSocket = socketOfServer;
            this.MaGestionTable = maGestionTable;

        }

        @Override
        public void run() {

            String text = "";

            //while (true) {

            BufferedReader is = null;
            //creation du bufferedReader pour recuperer les données envoyé avec nos BufferedWritter
            try {
                 is = new BufferedReader(new InputStreamReader(maSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }



            String octetLu = null;
            try {
                //on recupere la ligne envoyé
                octetLu = is.readLine();
                System.out.println("octet lu " + octetLu);

                //si on a recupérer la ligne de deconnexion alors
                if (octetLu.equals("deconnexion"))
                {
                    System.out.println("Deconnexion du serveur");

                    //on stop la boucle
                    conditionBloucle = 1;
                    //on ferme les élement que l'on dois fermer
                    is.close();
                    maSocket.close();

                }
                else{
                    //si on recupere un élement commencant par un R donc un lecteur alors
                    if (octetLu.charAt(0)=='R')
                    {
                        //on créer un StringBuilder pour avoir accès au methode des StringBuilder
                        StringBuilder sb = new StringBuilder(octetLu);

                        //on supprimer le premier caractère donc ici le R
                        sb.deleteCharAt(0);

                        //on reforme un string
                        String lecteur = sb.toString();

                        //on realise l'insertion
                        MaGestionTable.insertionTableLecteur(lecteur);
                    }
                    else{
                        //si on recupere un élement commencant par un B donc un livre alors
                        if (octetLu.charAt(0)=='B'){
                            //on créer un StringBuilder pour avoir accès au methode des StringBuilder
                            StringBuilder sb = new StringBuilder(octetLu);
                            //on supprimer le premier caractère donc ici le B
                            sb.deleteCharAt(0);
                            //on reforme un string
                            String livres = sb.toString();
                            //on realise l'insertion
                            MaGestionTable.insertionTableLivre(livres);
                        }

                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                is.close();
                maSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}

