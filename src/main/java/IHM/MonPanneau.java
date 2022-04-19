package IHM;

import bdd.gestionTable;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MonPanneau extends Panel {

    Socket maSocket = null;
    String addresseServeur = "localhost";
    int noPort = 1234;

    //creation des élements de notre ihm
    private MonEcouteurAjoutLivre monEcouteurAjoutLivre;
    private MonEcouteurAjoutLecteur monEcouteurAjoutLecteur;

    private Label monLabelLecteur;
    private Button monButtonLecteur;
    private TextField monInputLecteur;

    private Label monLabelLivre;
    private Button monButtonLivre;
    private TextField monInputLivre;

    private  Button deconnexion;
    private Label vide;

    private String ip = null;
    private String UserName = null;
    private String password = null;
    private String NomBDD = null;

    public MonPanneau() throws IOException {
        //on initialise nos élement de notre IHM
        this.monLabelLecteur = new Label("Ajout de lecteur");
        this.monInputLecteur = new TextField();
        this.monButtonLecteur = new Button("Ajouter le lecteur");

        this.monLabelLivre = new Label("Ajout de livre");
        this.monInputLivre = new TextField();
        this.monButtonLivre = new Button("Ajouter le livre");
        this.deconnexion = new Button("Deconnexion du serveur");
        this.vide = new Label("");


        setLayout(new GridLayout(5, 2));


        //on ajoute nos élement
        add(deconnexion);
        add(vide);
        add(monLabelLecteur);
        add(monLabelLivre);
        add(monInputLecteur);
        add(monInputLivre);
        add(monButtonLecteur);
        add(monButtonLivre);


        //on créer nos listener sur nos boutton
        this.monButtonLivre.addActionListener(
                e -> {
                    try {
                        AjoutLivre();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        );

        this.monButtonLecteur.addActionListener(
                e -> {
                    try {
                        AjoutLecteur();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        );

        this.deconnexion.addActionListener(
                e -> {
                   deconnexion();
                }
        );



    }


    /*
    * Methode de deconnexion du serveur
    * Lors du clic sur le bouton de deconnexion la methode s'execute
    * */
    private void deconnexion() {
        Socket maSocket = null;

        String adresse = "localhost";
        int noport = 1234;

        //connexion au serveur
        try {
            maSocket = new Socket(adresse, noport);
            System.out.println("Connection avec succes");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);

        }

        //seconde etapes
        OutputStream outputStream = null;

        //on créer notre outputstream
        try {
            outputStream = maSocket.getOutputStream();
            System.out.println("OutputStream: OK");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                maSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(-2);
            }
        }

        //on créer notre bufferWritter pour envoyer le message de deconnexion
        BufferedWriter os = null;
        try {
            os = new BufferedWriter(new OutputStreamWriter(maSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedOutputStream bos = new BufferedOutputStream(outputStream);

        try {
            os.write("deconnexion");
            os.newLine();
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            os.close();
            bos.close();
            maSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    *   Methode d'ajout de livre
    * */
    public void AjoutLivre() throws IOException {

        //on récupère la valeur saisie dans l'input text
        String value = this.monInputLivre.getText();

        //on remet a zéro l'input stream
        this.monInputLivre.setText("");


       /* gestionTable MaGestionTable = new gestionTable(ip,NomBDD,UserName,password);
        MaGestionTable.insertionTableLivre(value);


*/

        Socket maSocket = null;

        String adresse = "localhost";
        int noport = 1234;

        //connexion au serveur
        try {
            maSocket = new Socket(adresse, noport);
            System.out.println("Connection avec succes");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);

        }

        //creation d'un output stream
        OutputStream outputStream = null;
        try {
            outputStream = maSocket.getOutputStream();
            System.out.println("OutputStream: OK");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                maSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(-2);
            }
        }

        //creation de notre bufferedWritter pour envoyer notre livre
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(maSocket.getOutputStream()));

        BufferedOutputStream bos = new BufferedOutputStream(outputStream);



        //on envoie notre livre sur le serveur avec un B devant pour traiter la data par la suite
        os.write("B"+value);
        //on ajoute l'element de fin de ligne
        os.newLine();
        //on flush pour vider le buffer
        os.flush();

        //on réalise les fermeture necessaire
        os.close();
        bos.close();
        maSocket.close();


    }


    /*
     *   Methode d'ajout de lecteur
     * */
    public void AjoutLecteur() throws IOException {
        //on récupère la valeur saisie dans l'input text
        String valueLecteur = this.monInputLecteur.getText();

        //on remet a zéro l'input stream
        this.monInputLecteur.setText("");

            Socket maSocket = null;

            String adresse = "localhost";
            int noport = 1234;

            //connexion au serveur
            try {
                maSocket = new Socket(adresse, noport);
                System.out.println("Connection avec succes");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);

            }

            //seconde etapes
            OutputStream outputStream = null;
            try {
                outputStream = maSocket.getOutputStream();
                System.out.println("OutputStream: OK");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    maSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(-2);
                }
            }

            //troisieme etape
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(maSocket.getOutputStream()));

            BufferedOutputStream bos = new BufferedOutputStream(outputStream);


        //on ecrit sur notre serveur la valeur de lecteur avec un R au debut pour traiter la données par la suite
        os.write("R"+valueLecteur);
        os.newLine();
        os.flush();

        //on réalise les fermetures necessaire
        os.close();
        bos.close();
        maSocket.close();


    }


    }
