import java.io.*;
import java.net.*;

class UDPClient {
   public static void main(String args[]) throws Exception {
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String userData, saudacao, mensagemRecebida;
      BufferedReader userInput;
      DatagramSocket clientSocket;
      InetAddress IPAddress;
      DatagramPacket newPacket, receivePacket; // pacote(ou datagrama) a ser enviado e recebido, respectivamente

      userInput = new BufferedReader(new InputStreamReader(System.in));// receber nome
      userData = userInput.readLine();// ler mensagem digitada no terminal
      IPAddress = InetAddress.getByName(userData);// input ip

      saudacao = "Oi. quer conversar?";
      sendData = saudacao.getBytes();// transformar em bytes

      newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
      clientSocket = new DatagramSocket();
      clientSocket.send(newPacket);// enviar mensagem

      /* receber mensagem */
      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket); // receber pacote

      mensagemRecebida = new String(receivePacket.getData());

      while (mensagemRecebida.toUpperCase() != "NAO") {
         System.out.println("Mensagem Recebida:" + mensagemRecebida);

         /* enviar mensagem */
         userData = userInput.readLine();// ler nova mensagem
         sendData = userData.getBytes();// transformar em bytes
         newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
         clientSocket.send(newPacket);// enviar novo pacote com a mensagem

         /* receber mensagem */
         receivePacket = new DatagramPacket(receiveData, receiveData.length);
         clientSocket.receive(receivePacket); // receber pacote

         mensagemRecebida = new String(receivePacket.getData());
      }
      ;

      System.out.println("FROM SERVER:" + mensagemRecebida);
      clientSocket.close();

   }
}