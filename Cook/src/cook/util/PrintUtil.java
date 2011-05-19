/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cook.util;

/**
 *
 * @author itakenami
 */
public class PrintUtil {
    
    
    public static int MSG = -1;
    public static int ERRO = 0;
    public static int INFO = 1;
    
    
    public static void outn(int info, String out) {
        switch(info){
            case 0:
                System.out.println("ERRO => "+out);
                break;
            case 1:
                System.out.println("INFO => "+out);
                break;
            default:
                System.out.println(out);
                break;
        }
    }
    
    public static void out(int info, String out) {
        switch(info){
            case 0:
                System.out.println("ERRO => "+out);
                break;
            case 1:
                System.out.println("INFO => "+out);
                break;
            default:
                System.out.println(out);
                break;
        }
    }
    
    public static void outn(String out) {
        System.out.println("~ " + out);
    }

    public static void out(String out) {
        System.out.print("~ " + out);
    }
    
    
}
