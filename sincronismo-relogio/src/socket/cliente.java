package socket;

/**
 * @author 31616720
 */
import java.net.*;

class UDPClient {

    private static DatagramSocket openSocket() throws Exception {
        DatagramSocket newSocket = new DatagramSocket(9500);
        return newSocket;
    }

    private static void closeSocket(DatagramSocket clientSocket) {
        clientSocket.close();
    }

    private static void enviarMensagem(DatagramSocket clientSocket, String ipColega, String mensagem) throws Exception {
        InetAddress IPAddress;
        byte[] sendData = new byte[1024];
        DatagramPacket newPacket;

        IPAddress = InetAddress.getByName(ipColega);// input ip
        sendData = mensagem.getBytes();// transformar em bytes

        newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
        clientSocket.send(newPacket); // enviar mensagem

    }

    private static String receberMensagemMaster(DatagramSocket serverSocket) throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida;
        DatagramPacket receivePacket;
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        mensagemRecebida = new String(receivePacket.getData());
        return mensagemRecebida;
    }

    public static void responderMaster(DatagramSocket serverSocket, String mensagem) throws Exception {
        DatagramPacket newPacket;
        byte[] sendData = new byte[1024];

        sendData = mensagem.getBytes();// transformar em bytes
        newPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), 9000);
        serverSocket.send(newPacket); // enviar mensagem

    }

    public static void main(String args[]) throws Exception {
        String ipColega, mensagem, mensagemRecebida = "";
        DatagramSocket clientSocket = null;
        ipColega = "192.168.0.100";
        mensagem = "0";
        clientSocket = openSocket();

        enviarMensagem(clientSocket, ipColega, mensagem);

        Mensagem threadMensagem = new Mensagem(clientSocket);
        try {
            Thread.sleep(5000);
            threadMensagem.stop();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        mensagemRecebida = receberMensagemMaster(clientSocket);

        System.out.println(mensagemRecebida);
        closeSocket(clientSocket);

    }
;

};
