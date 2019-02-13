
/**
 * @author 31616720
 */

import java.io.*;
import java.net.*;

class UDPClient {
   private static String[][] colegaList = { { "Mario", "localhost" }, { "Maria", "localhost" } };

   private static String getIpColega(String colega) {
      for (int i = 0; i < colegaList.length; i++) {
         if (colegaList[i][0].equals(colega)) {
            return colegaList[i][1];
         }
      }
      ;
      return null;
   };

   private static void enviarMensagem(String mensagem) {
      byte[] sendData = new byte[1024];
      DatagramPacket newPacket;

      sendData = mensagem.getBytes();// transformar em bytes

   }

   public static void main(String args[]) throws Exception {
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String colega, ipColega, saudacao, mensagemRecebida;
      BufferedReader userInput;
      DatagramSocket clientSocket;
      InetAddress IPAddress;
      DatagramPacket newPacket, receivePacket; // pacote(ou datagrama) a ser enviado e recebido, respectivamente

      System.out.println("Digite o nome do seu colega:");
      userInput = new BufferedReader(new InputStreamReader(System.in));// receber nome
      colega = userInput.readLine(); // ler mensagem digitada no terminal
      ipColega = getIpColega(colega);
      if (ipColega == null) {
         throw new Exception("Colega não encontrado");
      }
      ;

      IPAddress = InetAddress.getByName(ipColega);// input ip

      saudacao = "Oi, aqui é o " + colega + ". Alguma questão?";
      sendData = saudacao.getBytes();// transformar em bytes

      /* enviar mensagem */
      newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
      clientSocket = new DatagramSocket();
      clientSocket.send(newPacket); // enviar mensagem

      /* receber mensagem */
      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket); // receber pacote

      mensagemRecebida = new String(receivePacket.getData());

      while (mensagemRecebida.toUpperCase() != "NAO") {
         System.out.println("Mensagem Recebida:" + mensagemRecebida);

         /* enviar mensagem */
         colega = userInput.readLine();// ler nova mensagem
         sendData = colega.getBytes();// transformar em bytes
         newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
         clientSocket.send(newPacket);// enviar novo pacote com a mensagem

         /* receber mensagem */
         receivePacket = new DatagramPacket(receiveData, receiveData.length);
         clientSocket.receive(receivePacket); // receber pacote

         mensagemRecebida = new String(receivePacket.getData());
      }
      ;

      System.out.println("FROM SERVER:" + mensagemRecebida);
      clientSocket.close(); // fechar socket

   }
}