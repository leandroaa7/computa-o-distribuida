package old;

/**
 * @author 31616720
 */
//package javaapplication1;
import java.io.*;
import java.net.*;
import java.util.Arrays;
//import socket.UDPServer;

class UDPServer2 {

    private static String hora = "23:30";

    private static String getInputUser() throws Exception {
        String input;
        BufferedReader userInput;
        userInput = new BufferedReader(new InputStreamReader(System.in));// receber nome
        input = userInput.readLine(); // ler mensagem digitada no terminal
        return input;
    }

    ;

   private static DatagramSocket openSocket() throws Exception {
        DatagramSocket newSocket = new DatagramSocket(9000);
        return newSocket;
    }

    ;

   private static void closeSocket(DatagramSocket serverSocket) {
        serverSocket.close();
    }

    public static void responder(DatagramSocket serverSocket, String mensagem) throws Exception {
        DatagramPacket newPacket;
        byte[] sendData = new byte[1024];

        sendData = mensagem.getBytes();// transformar em bytes
        newPacket = new DatagramPacket(sendData, sendData.length, ipDoMestre, portaDoMestre);
        serverSocket.send(newPacket); // enviar mensagem

    }

    private static InetAddress ipDoMestre;
    private static int portaDoMestre;

    private static void enviarMensagem(DatagramSocket serverSocket, DatagramPacket receivePacket, String mensagem)
            throws Exception {
        InetAddress IPAddress;
        byte[] sendData = new byte[1024];
        DatagramPacket newPacket;
        int port;

        // IPAddress = InetAddress.getByName(ipColega);// input ip
        IPAddress = receivePacket.getAddress();// pegar endereco
        port = receivePacket.getPort();

        //System.out.println(port);
        sendData = mensagem.getBytes();// transformar em bytes
        newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(newPacket); // enviar mensagem

    }

    ;

   private static String getMensagemTexto(DatagramSocket serverSocket, DatagramPacket receivePacket) throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida;

        //receivePacket = new DatagramPacket(receiveData, receiveData.length);
        // serverSocket.receive(receivePacket); // receber pacote
        mensagemRecebida = new String(receivePacket.getData());

        return mensagemRecebida;
    }

    ;
   private static DatagramPacket getMensagemPorPacote(DatagramSocket serverSocket) throws Exception {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket; // pacote a ser recebido
        receivePacket = new DatagramPacket(receiveData, receiveData.length);

        serverSocket.receive(receivePacket);

        return receivePacket;
    }

    ;
   
   private static String receberMensagem(DatagramSocket serverSocket) throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida;
        DatagramPacket receivePacket;
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

  //      UDPServer.ipDoMestre = receivePacket.getAddress();
//        UDPServer.portaDoMestre = receivePacket.getPort();

        mensagemRecebida = new String(receivePacket.getData());
        return mensagemRecebida;
//   return Arrays.toString(receivePacket.getData()); 
    }

    ;
   



    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = openSocket();

        String mensagemRecebida, mensagemEnviada;
        DatagramPacket receivePacket = null;
        System.out.println("Up");
        while (true) {

            mensagemRecebida = receberMensagem(serverSocket);

            if (mensagemRecebida.charAt(0) == "0".charAt(0)) {
                System.out.println("sim");
                //responder(serverSocket, UDPServer.hora);
            } else {
                //System.out.println("hora atual = " + UDPServer.hora);
                System.out.println("Nova hora = " + mensagemRecebida);
            };
        }

        // serverSocket.close(); fechar socket
    }
}
