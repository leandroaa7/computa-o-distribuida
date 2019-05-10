/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mackleaps
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
                    endereco = data.split(":");
                    enderecos.add(endereco);
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

    public static void gravarLog2() {
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

    public static void gravarLog(String mensagem, String caminho) {
        String content = mensagem;

        for (int i = 0; i < 10; i++) {
            content += String.valueOf(i) + "\r\n";
        }
        try (FileWriter writer = new FileWriter(caminho);
                BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(content);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

    }

    public static void main(String[] args) {
        //gravarLog();
    }
}
