package socket;

/**
 * @author 31616720
 */
//package javaapplication1;
import java.net.*;

class Escravo {

    private static String hora = "18:00";
    private static InetAddress ipDoMestre;
    private static int portaDoMestre;
    private static int minhaPorta;

    private static DatagramSocket openSocket() throws Exception {
        int port = 9501;
        minhaPorta = port;
        System.out.println("port " + port);
        DatagramSocket newSocket = new DatagramSocket(port);
        return newSocket;
    }

    private static void closeSocket(DatagramSocket serverSocket) {
        serverSocket.close();
    }

    public static void responder(DatagramSocket serverSocket, String mensagem) throws Exception {
        DatagramPacket newPacket;
        byte[] sendData = new byte[1024];

        sendData = mensagem.getBytes();// transformar em bytes
        try {
            newPacket = new DatagramPacket(sendData, sendData.length, ipDoMestre, portaDoMestre);
            //Thread.sleep(1000);
            serverSocket.send(newPacket); // enviar mensagem

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("");
        System.out.println("mensagem respondida");
    }

    private static String receberMensagem(DatagramSocket serverSocket) throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida;
        DatagramPacket receivePacket;
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

        Escravo.ipDoMestre = receivePacket.getAddress();
        Escravo.portaDoMestre = receivePacket.getPort();

        mensagemRecebida = new String(receivePacket.getData());
        return mensagemRecebida;
        // return Arrays.toString(receivePacket.getData());
    }

    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = openSocket();

        String mensagemRecebida;
        System.out.println("Up");
        while (true) {

            mensagemRecebida = receberMensagem(serverSocket);
            System.out.println(" resposta do " + portaDoMestre + " -> A mensagem recebida é " + mensagemRecebida);

            if (mensagemRecebida.charAt(0) == "-1".charAt(0) ) {
                System.out.println("sim");
                responder(serverSocket, Escravo.hora);
            } else {
                Escravo.hora = mensagemRecebida;
                System.out.println("hora atual = " + Escravo.hora);
                System.out.println("Nova hora = " + mensagemRecebida);
                responder(serverSocket, "Hora Atualizada");
            }

        }
        // serverSocket.close(); fechar socket
    }
}
