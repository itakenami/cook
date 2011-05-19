
package cook.core.ui;

import cook.util.FileUtil;
import cook.util.PrintUtil;
import java.io.File;


public class Screen {
    
    
    private static String getVersion(){
        return "0.0.3";
    }
    

    public static void header() {
        
        System.out.println("~");
        System.out.println("~ "+PrintUtil.getYellowFont()+"  ____            _    _                   "+PrintUtil.getColorReset());
        System.out.println("~ "+PrintUtil.getYellowFont()+" / ___|___   ___ | | ___)_ __   __ _       "+PrintUtil.getColorReset());
        System.out.println("~ "+PrintUtil.getYellowFont()+"| |   / _ \\ / _ \\| |/ / | '_ \\ / _` |      "+PrintUtil.getColorReset());
        System.out.println("~ "+PrintUtil.getYellowFont()+"| |___ (_) | (_) |   <| | | | | (_| |_ _ _ "+PrintUtil.getColorReset());
        System.out.println("~ "+PrintUtil.getYellowFont()+" \\____\\___/ \\___/|_|\\_\\_|_| |_|\\__, (_)_)_)"+PrintUtil.getColorReset());
        System.out.println("~ "+PrintUtil.getYellowFont()+"                               |___/"+PrintUtil.getColorReset());
        System.out.println("~ Cook "+getVersion()+", https://github.com/itakenami/cook");
        System.out.println("~ ");
    }

    public static void start() {
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getYellowFont()+"Starting cooking. Wait..."+PrintUtil.getColorReset());
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont()+"### Started cooking ##################################################"+PrintUtil.getColorReset());
        PrintUtil.outn("");
    }

    public static void end() {
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont()+"### Baking completed successfully ####################################"+PrintUtil.getColorReset());
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont()+"Thank you for use Cook!! Bye, Bye"+PrintUtil.getColorReset());
        PrintUtil.outn("");
    }

    public static void blank() {
        PrintUtil.outn("Use: cook [plugin] [params]");
        PrintUtil.outn("OR: cook help");
        PrintUtil.outn("");
    }

    public static void help() {
        PrintUtil.outn("Use: cook [plugin] [param]");
        PrintUtil.outn("");
        PrintUtil.outn("Try commands:");
        PrintUtil.outn("~~~~~~~~~~~~~");
        PrintUtil.outn(" cook help");
        PrintUtil.outn(" cook list");
        PrintUtil.outn(" cook [plugin] help");
        PrintUtil.outn(" cook [plugin] version");
        PrintUtil.outn("");
    }

    public static void list() {
        PrintUtil.outn("Installed plugins:");
        PrintUtil.outn("~~~~~~~~~~~~~~~~~~");
        File folder = new File(FileUtil.getApplicationPath() + "/plugins");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                PrintUtil.outn(" "+listOfFiles[i].getName());
            }
        }

        PrintUtil.outn("");
    }
}
