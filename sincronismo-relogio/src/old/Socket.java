/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author mackleaps
 */
public class Socket {

    private static DatagramSocket clientSocket;
    private static String ipSlave, mensagem;
    private static int portSlave, socketPort;

    private static int receivedPort;
    private static InetAddress receivedIp;

    public static DatagramSocket openSocket(int port) throws Exception {
        DatagramSocket newSocket = new DatagramSocket(port);
        System.out.println("Socket Aberto na porta " + port);
        return newSocket;
    }

    public static void closeSocket() {
        Socket.clientSocket.close();
        System.out.println("Socket Fechado");
    }

    public static int getReceivedPort() {
        return receivedPort;
    }

    public static InetAddress getReceivedIp() {
        return receivedIp;
    }

    public static String getMensagem() {
        return mensagem;
    }

    public void enviarMensagem(String mensagem) throws Exception {
        InetAddress IPAddress;
        byte[] sendData = new byte[1024];
        DatagramPacket newPacket;
        IPAddress = InetAddress.getByName(Socket.ipSlave);// input ip
        sendData = mensagem.getBytes();// transformar em bytes

        newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Socket.portSlave);
        Socket.clientSocket.send(newPacket); // enviar mensagem
        System.out.println("Mensagem Enviada para " + Socket.portSlave);
    }

    public String receberMensagem() throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida;
        DatagramPacket receivePacket;

        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        Socket.clientSocket.receive(receivePacket);

        Socket.receivedIp = receivePacket.getAddress();
        Socket.receivedPort = receivePacket.getPort();

        System.out.println("Mensagem Recebida por " + receivedPort);

        mensagemRecebida = new String(receivePacket.getData());
        Socket.mensagem = mensagemRecebida;
        return mensagemRecebida;
    }

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
