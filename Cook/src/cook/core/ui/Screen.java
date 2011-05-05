
package cook.core.ui;

import cook.util.FileUtil;
import cook.util.PrintUtil;
import java.io.File;


public class Screen {

    public static void header() {
        System.out.println("");
        System.out.println("");
        System.out.println("__        __   _ _                             ____            _    ");
        System.out.println("\\ \\      / /__| | | ___ ___  _ __ ___   ___   / ___|___   ___ | | __");
        System.out.println(" \\ \\ /\\ / / _ \\ | |/ __/ _ \\| '_ ` _ \\ / _ \\ | |   / _ \\ / _ \\| |/ /");
        System.out.println("  \\ V  V /  __/ | | (__ (_) | | | | | |  __/ | |___ (_) | (_) |   < ");
        System.out.println("   \\_/\\_/ \\___|_|_|\\___\\___/|_| |_| |_|\\___|  \\____\\___/ \\___/|_|\\_\\");
        System.out.println("");
        System.out.println("   Cook 0.1, https://github.com/itakenami/cook");
        System.out.println("");
    }

    public static void start() {
        PrintUtil.out("");
        PrintUtil.out("Starting cooking. Wait...");
        PrintUtil.out("");
        PrintUtil.out("### Started cooking ##################################################");
        PrintUtil.out("");
    }

    public static void end() {
        PrintUtil.out("");
        PrintUtil.out("### Baking completed successfully ####################################");
        PrintUtil.out("");
        PrintUtil.out("Thank you for use Cook!! Bye, Bye");
        PrintUtil.out("");
    }

    public static void blank() {
        PrintUtil.out("Use: cook [plugin] [params]");
        PrintUtil.out("OR: cook help");
        PrintUtil.out("");
    }

    public static void help() {
        PrintUtil.out("Use: cook [plugin] [param]");
        PrintUtil.out("");
        PrintUtil.out("Try commands:");
        PrintUtil.out("    cook help");
        PrintUtil.out("    cook list");
        PrintUtil.out("    cook [plugin] help");
        PrintUtil.out("    cook [plugin] version");
        PrintUtil.out("");
    }

    public static void list() {
        PrintUtil.out("Installed plugins:");
        File folder = new File(FileUtil.getApplicationPath() + "/plugins");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                PrintUtil.out("    " + listOfFiles[i].getName());
            }
        }

        PrintUtil.out("");
    }
}
