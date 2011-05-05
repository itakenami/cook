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
    
    public static void out(String out) {
        System.out.println("   "+out);
    }

    public static void outn(String out) {
        System.out.print("   "+out);
    }
    
    public static void outo(String out) {
        System.out.println(out);
    }
    
    public static void oute(String out){
        outo("ERRO => "+out);
        out("");
    }
    
}
