/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author mackleaps
 */
public class Main {

    /**
     * Verificar se está faltando parâmetros
     */
    private static void verificarUser(String[] args) throws Exception {
        if (args[0].equals("-m")) {
            System.out.println("master");
        } else if (args[1].equals("-l")) {
            System.out.println("slave");
        } else {
            throw new Exception("parâmetros faltando");
        }
    }

    private static void verificarParametros(String[] args) throws Exception {
        try {

            if (args.length == 0 || args.length < 4) {
                throw new Exception("parâmetros faltando");
            }
            
            verificarUser(args);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        try {
            verificarParametros(args);
        } catch (Exception e) {
        }

    }

}
