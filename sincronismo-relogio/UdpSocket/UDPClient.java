/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UdpSocket;

/**
 * @author 31616720
 */
import java.net.*;
import java.util.ArrayList;

class UDPClient {

    private static DatagramSocket openSocket() throws Exception {
        DatagramSocket newSocket = new DatagramSocket(9500);
        return newSocket;
    }

    private static void closeSocket(DatagramSocket clientSocket) {
        clientSocket.close();
    }

    public static void convocarSlaves(ArrayList<String[]> enderecos) throws Exception {
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

    public static void responderMestre(DatagramSocket clientSocket, DatagramPacket receivePacket) throws Exception{
        int port;
        InetAddress IPAddress;
        DatagramPacket newPacket; //nova mensagem
        byte[] sendData = new byte[1024];
        String mensagem = "minha hora é "; //retornar a hora do slave       
        
        sendData = mensagem.getBytes();// transformar a mensagem em bytes
        
        IPAddress = receivePacket.getAddress();// pegar endereco do pacote recebido
        port = receivePacket.getPort(); //pegar porta do pacote recebido         
  
        newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(newPacket); // enviar mensagem
        
    }

    public static String receberMensagem() throws Exception {
        String mensagemRecebida;
        DatagramPacket receivePacket; // pacote a ser recebido
        DatagramSocket clientSocket = null;
        byte[] receiveData = new byte[1024];

        clientSocket = openSocket();
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket); // receber pacote
        mensagemRecebida = new String(receivePacket.getData());

        /**parte do slave apenas */
        responderMestre(clientSocket,receivePacket);
        /**fim da parte do slave */

        closeSocket(clientSocket);


        return mensagemRecebida;
    }

}
