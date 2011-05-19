/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cook.util;

import java.util.Scanner;
import jline.ANSIBuffer;
import org.fusesource.jansi.AnsiConsole;

/**
 *
 * @author itakenami
 */
public class PrintUtil {
    
    
    public static int MSG = -1;
    public static int ERRO = 0;
    public static int INFO = 1;
    
    public static void colorReset(){
        System.out.print(getColorReset());
    }
    
    public static void greenFont(){
        System.out.print(getGreenFont());
    }
    
    public static void redFont(){
        System.out.print(getRedFont());
    }
    
    public static void yellowFont(){
        System.out.print(getYellowFont());
    }
    
    public static String getColorReset(){
        return ANSIBuffer.ANSICodes.attrib(0);
    }
    
    public static String getGreenFont(){
        return ANSIBuffer.ANSICodes.attrib(32);
    }
    
    public static String getRedFont(){
        return ANSIBuffer.ANSICodes.attrib(31);
    }
    
    public static String getYellowFont(){
        return ANSIBuffer.ANSICodes.attrib(33);
    }
    
    
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
    
    public static int inInt(String prompt){
        disableASCII();
        out(prompt);
        int s = (new Scanner(System.in)).nextInt();
        enableASCII();
        return s;
        
    }
    
    public static String inString(String prompt){
        disableASCII();
        out(prompt);
        String s = (new Scanner(System.in)).nextLine();
        enableASCII();
        return s;
        
    }
    
    public static void disableASCII(){
        AnsiConsole.systemUninstall();
    }
    
    public static void enableASCII(){
        AnsiConsole.systemInstall();
    }
    
    
}
