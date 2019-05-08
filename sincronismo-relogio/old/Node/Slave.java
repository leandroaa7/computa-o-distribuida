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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave {

    private static String hora;

    private static void atualizarHora(String hora) {
        Slave.hora = hora;
    }

    private static DatagramSocket openSocket() throws Exception {
        DatagramSocket newSocket = new DatagramSocket(9500);
        return newSocket;
    }

    private static void closeSocket(DatagramSocket clientSocket) {
        clientSocket.close();
    }

    private static void responderMestre(DatagramSocket clientSocket, DatagramPacket receivePacket,Boolean hora) throws Exception {
        int port;
        InetAddress IPAddress;
        DatagramPacket newPacket; //nova mensagem
        byte[] sendData = new byte[1024];
        String mensagem = hora ? Slave.hora : "modificado"; //retornar a hora ou mensagem de que foi modificado      

        sendData = mensagem.getBytes();// transformar a mensagem em bytes

        IPAddress = receivePacket.getAddress();// pegar endereco do pacote recebido
        port = receivePacket.getPort(); //pegar porta do pacote recebido         

        newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(newPacket); // enviar mensagem

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
        if (mensagemRecebida.equals("hora")) {
            responderMestre(clientSocket, receivePacket,true);
        } else {
            atualizarHora(mensagemRecebida);
            responderMestre(clientSocket, receivePacket,false);
        }
        closeSocket(clientSocket);

        return mensagemRecebida;
    }

    private static void gerarLog() {

    }

    private static void esperarChamado() {
        Boolean esperar = true;
        try {
            while (esperar) {
                receberMensagem();
            }
        } catch (Exception ex) {
            System.out.println("Slave error " + ex);
        }
    }

    public Slave(String hora) {
        this.hora = hora;
        esperarChamado();
    }

}
