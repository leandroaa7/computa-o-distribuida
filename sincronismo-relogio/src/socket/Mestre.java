package socket;

import java.util.ArrayList;

class Mestre {

    private static int socketPort = 9500;
    private static ArrayList<String[]> enderecos;
    private static ArrayList<Socket> conexoes = new ArrayList<>();
    private static ArrayList<ThreadSocket> threads = new ArrayList<>();

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

    /*
    private static Socket criarConexao(String ipSlave, int portSlave) {
        Socket clientSocket = new Socket(Mestre.socketPort, ipSlave, portSlave);
        Mestre.socketPort++;
        return clientSocket;
    }*/
    private static ThreadSocket criarThreadConexao(String ipSlave, int portSlave) {
        ThreadSocket thSocket = new ThreadSocket(socketPort, ipSlave, portSlave);
        Mestre.socketPort++;
        return thSocket;
    }

    /*
    private static void criarConexoes() {
        for (int i = 0; i < Mestre.enderecos.size(); i++) {
            String[] enderecoSlave = enderecos.get(i);
            Mestre.conexoes.add(
                    criarConexao(enderecoSlave[0], Integer.parseInt(enderecoSlave[1]))
            );
        }
    }*/
    private static void criarConexoes() {
        String[] enderecoSlave;
        for (int i = 0; i < Mestre.enderecos.size(); i++) {
            enderecoSlave = enderecos.get(i);
            Mestre.threads.add(
                    criarThreadConexao(enderecoSlave[0], Integer.parseInt(enderecoSlave[1]))
            );
        }
    }

    private static ArrayList<String[]> gerarEnderecos() {

        String[] endereco2 = {"localhost", "9502"};
        String[] endereco = {"localhost", "9555"};

        ArrayList<String[]> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        enderecos.add(endereco2);

        return enderecos;
    }

    private static void enviarMensagem(String mensagem) {
        try {
            Runnable r;
            for (int i = 0; i < Mestre.threads.size(); i++) {
                //enviarMensagem(Mestre.conexoes.get(i), "hora");
                r = Mestre.threads.get(i).criarThreadEnviarMensagem(Integer.toString(i));
                new Thread(r).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static ArrayList<String> receberMensagem() {
        ArrayList<String> mensagens = new ArrayList<>();
        String mensagem;
        Runnable r;
        try {
            for (int i = 0; i < Mestre.threads.size(); i++) {
                r = Mestre.threads.get(i).criarThreadReceberMensagem();
                new Thread(r).start();
            }
            Thread.sleep(1000);
            for (int i = 0; i < Mestre.threads.size(); i++) {
                System.out.println(Mestre.threads.get(i).getMensagem()); //temporary
                mensagens.add(
                        Mestre.threads.get(i).getMensagem()
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mensagens;
    }

    private static void getHoras() {
        String mensagem = "hora";

        Mestre.enderecos = gerarEnderecos();//temporary
        criarConexoes();//temporary

        enviarMensagem(mensagem);
        receberMensagem();

    }

    public static void main(String[] args) {
        getHoras();
    }
}
