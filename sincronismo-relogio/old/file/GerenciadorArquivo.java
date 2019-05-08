/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 31616720
 */
public class GerenciadorArquivo {

    public static ArrayList<String[]> getIps() {
        String[] endereco;
        ArrayList<String[]> enderecos = new ArrayList<>();
        try {
            URL path = GerenciadorArquivo.class.getResource("ips.txt");
            File myObj = new File(path.getFile());
            //ler dados do arquivo
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("localhost")) {
                    System.out.println("sim");
                } else {
                    endereco = data.split(":");
                    enderecos.add(endereco);
                }
            }

            for (String[] end : enderecos) {
                System.out.println("Endereco " + end[0] + " porta " + end[1]);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. " + e);
        }

        return enderecos;
    }

    public static void gravarLog() {
        FileWriter fw;
        BufferedWriter bw;
        try {
            URL path = GerenciadorArquivo.class.getResource("logs.txt");
            File myObj = new File(path.getFile());

            //construtor que recebe o objeto do tipo arquivo
            fw = new FileWriter(myObj, true);
            bw = new BufferedWriter(fw);
            bw.newLine();//INSERIR NOVA LINHA
            bw.write("teste");
            bw.newLine();//INSERIR NOVA LINHA
            bw.write("feito");
            //bw.flush();
            bw.close();
            System.out.println("arquivos salvos");
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
