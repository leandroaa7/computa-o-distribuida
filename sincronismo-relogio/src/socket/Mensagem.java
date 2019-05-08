/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Fabrica de Software
 */
public class Mensagem implements Runnable {

    private boolean exit;
    private String name;
    Thread t;
    private DatagramSocket clientSocket;

    Mensagem(DatagramSocket clientSocket) {
        this.name = "mano do ceu";
        this.clientSocket = clientSocket;
        t = new Thread(this);
        exit = false;
        t.start();
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

    public void run() {
        int i = 0;
        while (!exit) {
            try {
                String mensagem = receberMensagemMaster(this.clientSocket);
                System.out.println(mensagem);

                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Chega de ouvir mensagens");
    }

    public void stop() {
        exit = true;
    }

}
