/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import File.GerenciadorArquivo;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import time.CalcTime;

public class Mestre {

    private static ArrayList<String[]> enderecos;
    private static int socketPort = 9000;
    private static ArrayList<String> mensagensHoras;
    private static String hora = "03:05";
    private static String myIp;
    private static String caminhoLog;
    private static GerenciadorArquivo arquivo = new GerenciadorArquivo();

    private static ArrayList<String[]> gerarEnderecos() {
        ArrayList<String[]> enderecos = new ArrayList<>();
        //String[] endereco2 = {"localhost", "9500"};
        //String[] endereco = {"localhost", "9501"};
        //enderecos.add(endereco);
        //enderecos.add(endereco2);
        GerenciadorArquivo ga = new GerenciadorArquivo();
        enderecos = ga.getIps();
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

            IPAddress = InetAddress.getByName(ip);
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

    private static Runnable conectar(String ip, int port, String mensagem) {
        Mestre.mensagensHoras = new ArrayList<>();
        Runnable rn = () -> {
            try {

                DatagramSocket socket = openSocket(socketPort);
                socketPort++;
                Thread.sleep(2000);
                enviarMensagem(socket, mensagem, ip, port);
                //Thread.sleep(2000);
                mensagensHoras.add(receberMensagem(socket));
                socket.close();
            } catch (Exception e) {
                System.out.println("erro no conectar " + e);
            }
        };
        return rn;
    }

    public static String calcMedia() {
        System.out.println("Quantidade de mensagens" + Mestre.mensagensHoras.size());

        CalcTime ct = new CalcTime();
        ArrayList<Integer> minutes = new ArrayList<>();
        minutes.add(0); // valor da diferença do master

        int minutesMaster = 0, minutesSlave = 0;
        for (int i = 0; i < Mestre.mensagensHoras.size(); i++) {
            minutesSlave = ct.calcTimeToMin(Mestre.mensagensHoras.get(i));
            minutesMaster = ct.calcTimeToMin(Mestre.hora);
            minutes.add(ct.diff(minutesSlave, minutesMaster));
        }

        float media = ct.media(minutes);
        //System.out.println(media);
        return ct.calcMinToTime(minutesSlave);

        //return media;
    }

    public static ArrayList<Thread> buscarHorariosSlaves() {
        //criação de threads para buscar horas dos 
        enderecos = gerarEnderecos();
        ArrayList<Thread> threads = new ArrayList<>();
        String ip;
        int port;
        try {

            for (int i = 0; i < enderecos.size(); i++) {
                ip = enderecos.get(i)[0];
                port = Integer.parseInt(enderecos.get(i)[1]);
                Thread t1 = new Thread(conectar(ip, port, "-1"));
                threads.add(t1);
            }

            //execução de threads
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).start();
                Thread.sleep(100);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return threads;
    }

    public static void setarNovaMedia(String media) {

        enderecos = gerarEnderecos();
        ArrayList<Thread> threads = new ArrayList<>();
        String ip;
        int port;
        try {

            for (int i = 0; i < enderecos.size(); i++) {
                ip = enderecos.get(i)[0];
                port = Integer.parseInt(enderecos.get(i)[1]);
                Thread t1 = new Thread(conectar(ip, port, media));
                threads.add(t1);
            }

            //execução de threads
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).start();
                Thread.sleep(100);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void run() {
        enderecos = gerarEnderecos();
        ArrayList<Thread> threads = new ArrayList<>();
        String ip;
        int port;
        String media;
        while (true) {
            try {
                //arquivo.gravarLog("foi mano", caminhoLog);
                //criação de threads para buscar horas dos 
                threads = buscarHorariosSlaves();
                //esperar 2 segundos
                Thread.sleep(2000);
                //calcular média dos horarios            
                media = calcMedia();
                //enviar nova média
                setarNovaMedia(media);
                //esperar 2 segundos
                Thread.sleep(2000);

            } catch (Exception e) {
                System.out.println("erro nos threads " + e);
            }
        }
    }

    public static void main(String[] args) {
        run();

    }

    public Mestre(String ip, int socketPort, String Hora, String caminhoLog) {
        Mestre.hora = hora;
        Mestre.myIp = ip;
        Mestre.hora = hora;
        //Mestre.caminhoLog = caminhoLog;
        Mestre.caminhoLog = "/home/mackleaps/Desktop/dev/computacao-distribuida/sincronismo-relogio/src/File/logs.txt";
    }

}
