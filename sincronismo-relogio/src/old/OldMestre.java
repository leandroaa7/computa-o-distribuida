package old;

import java.util.ArrayList;
import socket.Socket;

class Mestre {

    private static int socketPort = 9500;
    private static ArrayList<String[]> enderecos;
    private static ArrayList<Socket> conexoes = new ArrayList<>();

    private static Socket enviarMensagemHoraParaSlave(String ipSlave, int portSlave) {
        Socket clientSocket = new Socket(socketPort, ipSlave, portSlave);
        Mestre.socketPort++;
        try {
            clientSocket.enviarMensagem("hora");
            // return clientSocket.receberMensagem();
        } catch (Exception e) {
            System.out.println(e);
        }
        return clientSocket;
    }

    private static Socket criarConexao(String ipSlave, int portSlave) {
        Socket clientSocket = new Socket(Mestre.socketPort, ipSlave, portSlave);
        Mestre.socketPort++;
        return clientSocket;
    }

    private static void criarConexoes() {
        for (int i = 0; i < Mestre.enderecos.size(); i++) {
            String[] enderecoSlave = enderecos.get(i);
            Mestre.conexoes.add(
                    criarConexao(enderecoSlave[0], Integer.parseInt(enderecoSlave[1]))
            );
        }
    }

    private static void enviarMensagem(Socket clientSocket, String mensagem) {
        try {
            // mensagem = "hora"
            clientSocket.enviarMensagem(mensagem);
            // return clientSocket.receberMensagem();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String receberMensagem(Socket clientSocket) {
        try {
            return clientSocket.receberMensagem();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static ArrayList<String[]> gerarEnderecos() {
        String[] endereco = {"localhost", "9500"};
        ArrayList<String[]> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        return enderecos;
    }

    private static void getHoras() {
        Mestre.enderecos = gerarEnderecos();//temporary
        criarConexoes();//temporary

        for (int i = 0; i < Mestre.conexoes.size(); i++) {
            enviarMensagem(Mestre.conexoes.get(i), "hora");
        }

        for (int i = 0; i < Mestre.enderecos.size(); i++) {
            String[] endereco = enderecos.get(i);
            System.out.println(endereco[0]);
            System.out.println(endereco[1]);
            //enviarMensagem(endereco[0], Integer.parseInt(endereco[1]), "hora");
        }
    }

    public static void main(String[] args) {
        getHoras();
    }
}
