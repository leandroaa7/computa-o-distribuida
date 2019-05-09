package old;

import java.util.ArrayList;

class Mestre0 {

    private static int socketPort = 9505;
    private static ArrayList<String[]> enderecos;
    private static ArrayList<Socket> conexoes = new ArrayList<>();
    private static ArrayList<ThreadSocket> threads = new ArrayList<>();

    private static Socket enviarMensagemHoraParaSlave(String ipSlave, int portSlave) {
        Socket clientSocket = new Socket(socketPort, ipSlave, portSlave);
        Mestre0.socketPort++;
        try {
            clientSocket.enviarMensagem("hora");
            // return clientSocket.receberMensagem();
        } catch (Exception e) {
            System.out.println(e);
        }
        return clientSocket;
    }

    private static Socket criarConexao(String ipSlave, int portSlave) {
        Socket clientSocket = new Socket(Mestre0.socketPort, ipSlave, portSlave);
        Mestre0.socketPort++;
        return clientSocket;
    }

    private static void criarConexoes() {

        ArrayList<Socket> cn = new ArrayList<>();
        for (int i = 0; i < Mestre0.enderecos.size(); i++) {
            String[] enderecoSlave = enderecos.get(i);

            
            Mestre0.socketPort++;
            cn.add(new Socket(Mestre0.socketPort, enderecoSlave[0], Integer.parseInt(enderecoSlave[1])));
        }
        Mestre0.conexoes = cn;
    }

    public static Runnable criarThreadEnviarMensagem(Socket socket, String mensagem) {
        Runnable t1 = new Runnable() {
            public void run() {
                try {
                    socket.enviarMensagem(mensagem);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        return t1;
    }

    public static Runnable criarThreadReceberMensagem(Socket socket) {
        Runnable t1 = () -> {
            try {
                socket.receberMensagem();
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        return t1;
    }

    /*
    private static ThreadSocket criarThreadConexao(String ipSlave, int portSlave) {
        ThreadSocket thSocket = new ThreadSocket(socketPort, ipSlave, portSlave);
        Mestre.socketPort++;
        return thSocket;
    }

    private static void criarConexoes() {
        String[] enderecoSlave;
        for (int i = 0; i < Mestre.enderecos.size(); i++) {
            enderecoSlave = Mestre.enderecos.get(i);
            Mestre.threads.add(
                    criarThreadConexao(enderecoSlave[0], Integer.parseInt(enderecoSlave[1]))
            );
        }
    }
     */
    private static ArrayList<String[]> gerarEnderecos() {

        String[] endereco2 = {"localhost", "9502"};
        String[] endereco = {"localhost", "9555"};

        ArrayList<String[]> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        enderecos.add(endereco2);

        return enderecos;
    }

    private static void enviarMensagem(String mensagem) {
        for (int i = 0; i < Mestre0.conexoes.size(); i++) {
            try {
                Runnable r, r2;
                //enviarMensagem(Mestre.conexoes.get(i), "hora");
                r = criarThreadEnviarMensagem(Mestre0.conexoes.get(0), Integer.toString(i));
                new Thread(r).start();
                Thread.sleep(500);
                r2 = criarThreadEnviarMensagem(Mestre0.conexoes.get(1), Integer.toString(i));
                new Thread(r2).start();
                Thread.sleep(500);
                {

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static ArrayList<String> receberMensagem() {
        ArrayList<String> mensagens = new ArrayList<>();
        String mensagem;
        Runnable r;
        Runnable r2;
        Socket sk = Mestre0.conexoes.get(0);
        r = criarThreadReceberMensagem(sk);
        new Thread(r).start();

        r2 = criarThreadReceberMensagem(Mestre0.conexoes.get(1));
        new Thread(r2).start();

        for (int i = 0; i < Mestre0.conexoes.size(); i++) {
            try {

            } catch (Exception e) {
                System.out.println(e);
            }

        }

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        try {
            for (int i = 0; i < Mestre0.conexoes.size(); i++) {
                System.out.println(Mestre0.conexoes.get(i).getMensagem()); //temporary
                mensagens.add(
                        Mestre0.threads.get(i).getMensagem()
                );
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return mensagens;
    }

    private static void getHoras() {
        String mensagem = "hora";

        Mestre0.enderecos = gerarEnderecos();//temporary
        criarConexoes();//temporary

        enviarMensagem(mensagem);
        receberMensagem();

    }

    public static void main(String[] args) {
        getHoras();
    }
}
