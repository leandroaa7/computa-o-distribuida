/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author mackleaps
 */
public class Mestre3 {

    private static ArrayList<String[]> enderecos;
    private static int socketPort = 9000;
    private static ArrayList<String> mensagensHoras;

    private static ArrayList<String[]> gerarEnderecos() {
        ArrayList<String[]> enderecos = new ArrayList<>();
        String[] endereco2 = {"localhost", "9500"};
        String[] endereco = {"localhost", "9501"};
        enderecos.add(endereco);
        enderecos.add(endereco2);
        return enderecos;
    }

    public static DatagramSocket openSocket(int port) throws Exception {
        DatagramSocket newSocket = new DatagramSocket(port);
        String msg = "Socket Aberto na porta " + port + " no thread é " + Thread.currentThread().getId();
        System.out.println(msg);
        System.out.println();
        return newSocket;
    }

    public static void enviarMensagem(DatagramSocket socket, String mensagem, String ip, int port) {
        InetAddress IPAddress;
        byte[] sendData = new byte[1024];
        DatagramPacket newPacket;
        try {

            IPAddress = InetAddress.getByName("localhost");
            sendData = mensagem.getBytes();

            newPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            socket.send(newPacket);

        } catch (Exception e) {
            System.out.println("erro ao enviar" + e);
        }
    }

    public static String receberMensagem(DatagramSocket socket) throws Exception {
        byte[] receiveData = new byte[1024];
        String mensagemRecebida = null;
        DatagramPacket receivePacket;

        System.out.println("esperando mensagem no thread: " + Thread.currentThread().getId());
        try {
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            mensagemRecebida = new String(receivePacket.getData());
            System.out.println("Mensagem " + mensagemRecebida + " no thread: " + Thread.currentThread().getId());
            System.out.println(" ");
            socket.close();
        } catch (Exception e) {
            throw new Exception("erro ao receber" + e);
        }
        return mensagemRecebida;
    }

    private static Runnable conectar(String ip, int port) {
        Mestre3.mensagensHoras = new ArrayList<>();
        Runnable rn = () -> {
            try {

                DatagramSocket socket = openSocket(socketPort);
                socketPort++;
                Thread.sleep(2000);
                enviarMensagem(socket, "0", ip, port);
                //Thread.sleep(2000);
                mensagensHoras.add(receberMensagem(socket));
            } catch (Exception e) {
                System.out.println("erro no conectar " + e);
            }
        };
        return rn;
    }

    public static void main(String[] args) {
        enderecos = gerarEnderecos();
        ArrayList<Thread> threads = new ArrayList<>();
        String ip;
        int port;
        try {
            for (int i = 0; i < enderecos.size(); i++) {
                ip = enderecos.get(i)[0];
                port = Integer.parseInt(enderecos.get(i)[1]);
                Thread t1 = new Thread(conectar(ip, port));
                threads.add(t1);

            }

            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).start();
                Thread.sleep(100);
            }

            Thread.sleep(2000);

            System.out.println("asdf" + Mestre3.mensagensHoras.size());

        } catch (Exception e) {
            System.out.println("erro nos threads" + e);
        }

    }

}
