package reseau.serveur;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class testServeur {
    public static void main(String[] args) {
        //premiere etapes

        Socket maSocket=null;

        String adresse="localhost";
        int noport=1234;

        try {
            maSocket=new Socket(adresse,noport);
            System.out.println("Connection avec succes");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);

        }

        //seconde etapes
        OutputStream outputStream=null;
        try {
            outputStream=maSocket.getOutputStream();
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
        BufferedOutputStream bos=new BufferedOutputStream(outputStream);

        byte unOctet=37;

        try {
            bos.write(unOctet);
            bos.flush();
            System.out.println("Ecriture reussi");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-3);
        }
        try{
            bos.close();
            maSocket.close();

        }
        catch (Exception exception){

        }




    }
}
