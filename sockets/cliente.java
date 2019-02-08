import java.io.*;
import java.net.*;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {
     BufferedReader inFromUser =
        new BufferedReader(new InputStreamReader(System.in));//receber nome


     DatagramSocket clientSocket = new DatagramSocket();
     //InetAddress IPAddress = InetAddress.getByName("172.16.18.17");
     byte[] sendData = new byte[1024];
     byte[] receiveData = new byte[1024];

     String inputUser = inFromUser.readLine();// ler mensagem

     InetAddress IPAddress = InetAddress.getByName(inputUser);//input ip

     //sendData = inputUser.getBytes();//transformar em bytes

     String saudacao = "Oi. quer conversar?";

     sendData= saudacao.getBytes();

     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);

     clientSocket.send(sendPacket);//enviar mensagem

     //receber mensagem
     DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
     clientSocket.receive(receivePacket);
     String mensagemRecebida = new String(receivePacket.getData());

     while(mensagemRecebida.toUpperCase() != "NAO"){
       System.out.println("Mensagem Recebida:" + mensagemRecebida);
       //enviar mensagem
       inputUser = inFromUser.readLine();// ler mensagem
       sendData= inputUser.getBytes();
       sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
       clientSocket.send(sendPacket);//enviar mensagem
       //receber mensagem
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        mensagemRecebida = new String(receivePacket.getData());
     }
     System.out.println("FROM SERVER:" + mensagemRecebida);
     clientSocket.close();
   }
}
