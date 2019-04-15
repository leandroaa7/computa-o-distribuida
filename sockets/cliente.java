
/**
 * @author 31616720
 */

import java.io.*;
import java.net.*;

class UDPClient {
   private static String[][] colegaList = { { "Mario", "localhost" }, { "Maria", "localhost" } };

   private static String getInputUser() throws Exception {
      String input;
      BufferedReader userInput;
      userInput = new BufferedReader(new InputStreamReader(System.in));// receber nome
      input = userInput.readLine(); // ler mensagem digitada no terminal
      return input;
   };

   private static String getIpColega(String colega) {
      for (int i = 0; i < colegaList.length; i++) {
         if (colegaList[i][0].equals(colega)) {
            return colegaList[i][1];
         }
      }
      ;
      return null;
   };

   private static DatagramSocket openSocket() throws Exception {
      DatagramSocket newSocket = new DatagramSocket(9500);
      return newSocket;
   };

   private static void closeSocket(DatagramSocket clientSocket) {
      clientSocket.close();
   };

   private static void enviarMensagem(DatagramSocket clientSocket, String ipColega, String mensagem) throws Exception {
      InetAddress IPAddress;
      byte[] sendData = new byte[1024];
      DatagramPacket newPacket;

      IPAddress = InetAddress.getByName(ipColega);// input ip
      sendData = mensagem.getBytes();// transformar em bytes

      newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
      clientSocket.send(newPacket); // enviar mensagem

   };

   private static String receberMensagem(DatagramSocket clientSocket) throws Exception {
      byte[] receiveData = new byte[1024];

      DatagramPacket receivePacket; // pacote a ser recebido
      String mensagemRecebida;

      receivePacket = new DatagramPacket(receiveData, receiveData.length);

      clientSocket.receive(receivePacket); // receber pacote

      mensagemRecebida = new String(receivePacket.getData());

      return mensagemRecebida;
   };

   public static void main(String args[]) throws Exception {
      String colega, ipColega, mensagem, mensagemRecebida = "";
      DatagramSocket clientSocket = null;

      while (mensagemRecebida.trim() != "NAO") {

         System.out.println("Digite o nome do seu colega:");

         colega = getInputUser();
         ipColega = getIpColega(colega);

         if (ipColega == null) {
            throw new Exception("Colega não encontrado");
         }
         ;

         mensagem = "Oi, aqui é o " + colega + ". Alguma questão?";

         clientSocket = openSocket();
         enviarMensagem(clientSocket, ipColega, mensagem);

         mensagemRecebida = receberMensagem(clientSocket);
         System.out.println("-" + mensagemRecebida);

         //loopíng da conversa
         while (mensagemRecebida.trim() != "NAO") {
            mensagem = getInputUser();
            enviarMensagem(clientSocket, ipColega, mensagem);
            mensagemRecebida = receberMensagem(clientSocket);
            System.out.println("-" + mensagemRecebida);
         }
         ;

      }

      closeSocket(clientSocket);

   };

};