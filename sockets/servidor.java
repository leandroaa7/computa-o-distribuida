/**
 * @author 31616720
 */

//package javaapplication1;

import java.io.*;
import java.net.*;

class UDPServer {

   private static String getInputUser() throws Exception {
      String input;
      BufferedReader userInput;
      userInput = new BufferedReader(new InputStreamReader(System.in));// receber nome
      input = userInput.readLine(); // ler mensagem digitada no terminal
      return input;
   };

   private static DatagramSocket openSocket() throws Exception {
      DatagramSocket newSocket = new DatagramSocket(9000);
      return newSocket;
   };

   private static void closeSocket(DatagramSocket serverSocket) {
      serverSocket.close();
   };

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

   };

   private static String getMensagemTexto(DatagramSocket serverSocket, DatagramPacket receivePacket) throws Exception {
      byte[] receiveData = new byte[1024];
      String mensagemRecebida;

      //receivePacket = new DatagramPacket(receiveData, receiveData.length);
      // serverSocket.receive(receivePacket); // receber pacote
      mensagemRecebida = new String(receivePacket.getData());

      return mensagemRecebida;
   };

   private static DatagramPacket getMensagemPorPacote(DatagramSocket serverSocket) throws Exception {
      byte[] receiveData = new byte[1024];
      DatagramPacket receivePacket; // pacote a ser recebido
      receivePacket = new DatagramPacket(receiveData, receiveData.length);

      serverSocket.receive(receivePacket);

      return receivePacket;
   };

   public static void main(String args[]) throws Exception {
      DatagramSocket serverSocket = openSocket();

      String mensagemRecebida, mensagemEnviada;
      DatagramPacket receivePacket;

      while (true) {
         /* receber pacote */

         receivePacket = getMensagemPorPacote(serverSocket);

         mensagemRecebida = getMensagemTexto(serverSocket, receivePacket);

         System.out.println(mensagemRecebida);

         mensagemEnviada = getInputUser();
         enviarMensagem(serverSocket, receivePacket, mensagemEnviada);

      }

      // serverSocket.close(); fechar socket
   }
}
