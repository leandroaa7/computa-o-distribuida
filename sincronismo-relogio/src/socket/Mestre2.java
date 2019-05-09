/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.util.ArrayList;

/**
 *
 * @author mackleaps
 */
public class Mestre2 {

    private static int socketPort = 9300;
    private static ArrayList<String> horas = new ArrayList<>();
    private static ArrayList<Socket> sockets = new ArrayList<>();

    private static ArrayList<String[]> gerarEnderecos() {

        ArrayList<String[]> enderecos = new ArrayList<>();
        String[] endereco2 = {"localhost", "9502"};
        String[] endereco = {"localhost", "9555"};

        enderecos.add(endereco);
        enderecos.add(endereco2);

        return enderecos;
    }

    private static ArrayList<Socket> criarConexoes(ArrayList<String[]> enderecos) {

        ArrayList<Socket> sockets = new ArrayList<>();
        Socket socket;
        String ipSlave;
        int portSlave;

        for (int i = 0; i < enderecos.size(); i++) {
            String[] enderecoSlave = enderecos.get(i);
            ipSlave = enderecoSlave[0];
            portSlave = Integer.parseInt(enderecoSlave[1]);
            socketPort++;
            try {
                socket = new Socket(socketPort, ipSlave, portSlave);
                sockets.add(socket);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return sockets;
    }

    public static Runnable criarThreadEnviarMensagem(Socket socket, String mensagem) {
        Runnable rn = () -> {
            try {
                socket.enviarMensagem(mensagem);
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        return rn;
    }

    public static Runnable criarThreadReceberMensagem(Socket socket) {

        Runnable t1 = () -> {
            try {
                String mensagem = null;
                while (mensagem != null) {
                    mensagem = socket.receberMensagem();
                }
                horas.add(mensagem);
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        return t1;
    }

    private static void enviarMensagem(ArrayList<String[]> enderecos, String mensagem) {
        ArrayList<Object> sockets = new ArrayList<>();
        Socket socket;
        String ipSlave;
        int portSlave;

        for (int i = 0; i < enderecos.size(); i++) {
            String[] enderecoSlave = enderecos.get(i);
            ipSlave = enderecoSlave[0];
            portSlave = Integer.parseInt(enderecoSlave[1]);
            socketPort++;
            try {
                socket = new Socket(socketPort, ipSlave, portSlave);
                sockets.add(socket);
                new Thread(criarThreadEnviarMensagem(socket, mensagem)).start();

                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static void receberMensagens(ArrayList<String[]> enderecos) {
        Socket socket;
        for (int i = 0; i < sockets.size(); i++) {
            try {
                socket = sockets.get(i);
                new Thread(criarThreadReceberMensagem(socket)).start();
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static void getHoras() {
        String mensagem = "nao sei ";
        ArrayList<String[]> enderecos = gerarEnderecos();
        enviarMensagem(enderecos, mensagem);
        receberMensagens(enderecos);
        System.out.println("qtd" + horas.size());
    }

    public static void main(String[] args) {
        getHoras();
    }
}
