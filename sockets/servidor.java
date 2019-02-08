/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author 31616720
 */
import java.io.*;
import java.net.*;

class UDPServer
{
   public static void main(String args[]) throws Exception
      {
        DatagramSocket serverSocket = new DatagramSocket(9000);
           byte[] receiveData = new byte[1024];
           byte[] sendData = new byte[1024];
           while(true)
              {
                //receber pacote
                 DatagramPacket pacoteRecebido = new DatagramPacket(receiveData, receiveData.length);
                 serverSocket.receive(pacoteRecebido);

                 String mensagemRecebida = new String( pacoteRecebido.getData());

                 System.out.println("Foi recebido-> " + mensagemRecebida);
                 //falta implementar
                 InetAddress IPAddress = pacoteRecebido.getAddress();//pegar endereco
                 int port = pacoteRecebido.getPort();
                 String capitalizedSentence = mensagemRecebida.toUpperCase();
                 sendData = capitalizedSentence.getBytes();

                 DatagramPacket sendPacket =
                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
                 serverSocket.send(sendPacket);
              }
      }
}
