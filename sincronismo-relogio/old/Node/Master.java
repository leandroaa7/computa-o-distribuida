/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

/**
 * @author 31616720
 */
import java.net.*;

import file.GerenciadorArquivo;
import java.util.ArrayList;

public class Master {

    private String hora;
    private String[] horasSlaves;

    private static DatagramSocket openSocket() throws Exception {
        DatagramSocket newSocket = new DatagramSocket(9500);
        return newSocket;
    }

    private static void closeSocket(DatagramSocket clientSocket) {
        clientSocket.close();
    }

    private static void convocarSlaves(ArrayList<String[]> enderecos) throws Exception {
        int port;
        String ip;
        String mensagem = "hora";
        InetAddress IPAddress;
        DatagramPacket newPacket;
        byte[] sendData = new byte[1024];
        DatagramSocket clientSocket = null;

        sendData = mensagem.getBytes();// transforma a mensagem em bytes
        clientSocket = openSocket();

        for (String[] endereco : enderecos) {
            ip = endereco[0];
            port = Integer.parseInt(endereco[1]);

            IPAddress = InetAddress.getByName(ip);// input ip

            newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); // prepararando pacote
            clientSocket.send(newPacket); // enviar mensagem
        }
        closeSocket(clientSocket);

    }

    private static String receberMensagem() throws Exception {
        String mensagemRecebida;
        DatagramPacket receivePacket; // pacote a ser recebido
        DatagramSocket clientSocket = null;
        byte[] receiveData = new byte[1024];

        clientSocket = openSocket();
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket); // receber pacote
        mensagemRecebida = new String(receivePacket.getData());
        closeSocket(clientSocket);

        return mensagemRecebida;
    }

    private static void atualizarHoras(){

    }

    private static void gerarLog(){
        
    }
    

    private void buscarHorariosDosSlaves() {
        try {
            ArrayList<String[]> enderecos = GerenciadorArquivo.getIps();
            convocarSlaves(enderecos);

            /**
             * FALTA IMPLEMENTAR:
             * thread para aguardar em n segundos as respostas dos slaves
             * atualizar horas
             */


        } catch (Exception ex) {
            System.out.println("master error " + ex);
        }
    }

    public Master(String hora) {
        this.hora = hora;
        buscarHorariosDosSlaves();
    }

}
