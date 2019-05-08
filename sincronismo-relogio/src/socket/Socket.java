/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author mackleaps
 */
public class Socket {

    private static DatagramSocket clientSocket;
    private static String ipSlave;
    private static int portSlave, socketPort;

    private static int receivedPort;
    private static InetAddress receivedIp;

    protected static DatagramSocket openSocket(int port) throws Exception {
        DatagramSocket newSocket = new DatagramSocket(port);
        System.out.println("Socket Aberto");
        return newSocket;
    }

    protected static void closeSocket() {
        Socket.clientSocket.close();
        System.out.println("Socket Fechado");
    }

    /**
     * @return the receivedPort
     */
    public static int getReceivedPort() {
        return receivedPort;
    }

    /**
     * @return the receivedIp
     */
    public static InetAddress getReceivedIp() {
        return receivedIp;
    }

    /**
     * envia mensagem
     *
     * @param mensagem String com a mensagem
     * @throws Exception
     */
    public void enviarMensagem(String mensagem) throws Exception {
        InetAddress IPAddress;
        byte[] sendData = new byte[1024];
        DatagramPacket newPacket;

        IPAddress = InetAddress.getByName(Socket.ipSlave);// input ip
        sendData = mensagem.getBytes();// transformar em bytes

        newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Socket.portSlave);
        Socket.clientSocket.send(newPacket); // enviar mensagem
        System.out.println("Mensagem Enviada");
    }

    public String receberMensagem() throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida;
        DatagramPacket receivePacket;

        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        Socket.clientSocket.receive(receivePacket);
        System.out.println("Mensagem Recebida");

        Socket.receivedIp = receivePacket.getAddress();
        Socket.receivedPort = receivePacket.getPort();

        mensagemRecebida = new String(receivePacket.getData());
        return mensagemRecebida;
    }

    /**
     * construtor
     *
     * @param socketPort valor inteiro da porta que será aberta
     * @param ipSlave String com ip do escravo
     * @param portSlave valor int da porta do escravo
     */
    public Socket(int socketPort, String ipSlave, int portSlave) {
        Socket.socketPort = socketPort;
        Socket.portSlave = portSlave;
        Socket.ipSlave = ipSlave;

        try {
            Socket.clientSocket = openSocket(socketPort);
        } catch (Exception e) {
            System.out.println(e);

        }
    }

}
