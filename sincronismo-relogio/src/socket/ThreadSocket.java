/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

/**
 *
 * @author 31616720
 */
public class ThreadSocket extends Socket {

    private String mensagem;

    public ThreadSocket(int socketPort, String ipSlave, int portSlave) {

        super(socketPort, ipSlave, portSlave);
        System.out.println(" a porta do slave é " + portSlave);
    }

    public Runnable criarThreadEnviarMensagem(String mensagem) {
        Runnable t1 = new Runnable() {
            public void run() {
                try {
                    ThreadSocket.super.enviarMensagem(mensagem);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        return t1;
    }

    public Runnable criarThreadReceberMensagem() {
        Runnable t1 = () -> {
            try {
                ThreadSocket.this.mensagem = ThreadSocket.super.receberMensagem();
            } catch (Exception e) {
                System.out.println(e);
            }
        };
        return t1;
    }

}
