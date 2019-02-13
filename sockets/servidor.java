/**
 * @author 31616720
 */


package javaapplication1;

import java.io.*;
import java.net.*;

class UDPServer {
   public static void main(String args[]) throws Exception {
      DatagramSocket serverSocket = new DatagramSocket(9000);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      String capitalizedMessage, mensagemRecebida;
      DatagramPacket newPacket, receivePacket; // pacote(ou datagrama) a ser enviado e recebido, respectivamente
      InetAddress IPAddress;
      int port;

      while (true) {
         /* receber pacote */
         receivePacket = new DatagramPacket(receiveData, receiveData.length);
         serverSocket.receive(receivePacket);

         mensagemRecebida = new String(receivePacket.getData());

         System.out.println("Mensagem Recebida:" + mensagemRecebida);

         /* falta implementar */
         IPAddress = receivePacket.getAddress();// pegar endereco
         port = receivePacket.getPort();
         capitalizedMessage = mensagemRecebida.toUpperCase(); // mensagem recebida em MAIÚSCULO
         sendData = capitalizedMessage.getBytes();// transformar em bytes

         newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
         serverSocket.send(newPacket); // enviar novo pacote

      }
      // serverSocket.close(); fechar socket
   }
}
