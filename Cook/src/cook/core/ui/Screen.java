package cook.core.ui;

import cook.util.FileUtil;
import cook.util.PrintUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeMap;

public class Screen {

    private static String getVersion() {
        return "0.0.4";
    }

    public static void header() {

        System.out.println("~");
        System.out.println("~ " + PrintUtil.getYellowFont() + "  ____            _    _                   " + PrintUtil.getColorReset());
        System.out.println("~ " + PrintUtil.getYellowFont() + " / ___|___   ___ | | ___)_ __   __ _       " + PrintUtil.getColorReset());
        System.out.println("~ " + PrintUtil.getYellowFont() + "| |   / _ \\ / _ \\| |/ / | '_ \\ / _` |      " + PrintUtil.getColorReset());
        System.out.println("~ " + PrintUtil.getYellowFont() + "| |___ (_) | (_) |   <| | | | | (_| |_ _ _ " + PrintUtil.getColorReset());
        System.out.println("~ " + PrintUtil.getYellowFont() + " \\____\\___/ \\___/|_|\\_\\_|_| |_|\\__, (_)_)_)" + PrintUtil.getColorReset());
        System.out.println("~ " + PrintUtil.getYellowFont() + "                               |___/" + PrintUtil.getColorReset());
        System.out.println("~ Cook " + getVersion() + ", https://github.com/itakenami/cook");
        System.out.println("~ ");
    }

    public static void start() {
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getYellowFont() + "Starting cooking. Wait..." + PrintUtil.getColorReset());
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont() + "### Started cooking ##################################################" + PrintUtil.getColorReset());
        PrintUtil.outn("");
    }

    public static void end() {
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont() + "### Baking completed successfully ####################################" + PrintUtil.getColorReset());
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont() + "Thank you for use Cook!! Bye, Bye" + PrintUtil.getColorReset());
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
        PrintUtil.outn(" cook help                  Show this screen :)");
        PrintUtil.outn(" cook list                  List plugins instaled");
        PrintUtil.outn(" cook list-remote           List plugins available for installation");
        PrintUtil.outn(" cook install [plugin]      Instal plugin from web repository");
        PrintUtil.outn(" cook update [plugin]       Update plugin from web repository");
        PrintUtil.outn(" cook uninstall [plugin]    Uninstall plugin from computer");
        PrintUtil.outn(" cook [plugin] help         Show plugin help");
        PrintUtil.outn(" cook [plugin] version      Show plugin version");
        PrintUtil.outn("");
    }

    public static void list() {
        PrintUtil.outn("Installed plugins:");
        PrintUtil.outn("~~~~~~~~~~~~~~~~~~");
        try {
            File folder = new File(FileUtil.getApplicationPath() + "/plugins");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isDirectory()) {
                    String version = FileUtil.getPropetry(FileUtil.getApplicationPath() + "/plugins/" + listOfFiles[i].getName() + "/config.properties", "version");
                    PrintUtil.outn(" " + listOfFiles[i].getName() + " [v" + version + "]");
                }
            }

        } catch (Exception ex) {
            PrintUtil.outn("ERRO!! The plugin folder not found or corrupt.");
        }
        PrintUtil.outn("");

    }

    public static void listRemote() {
        PrintUtil.outn("Plugins available:");
        PrintUtil.outn("~~~~~~~~~~~~~~~~~~");

        try {
            Collection<String> pp = getPlugins();

            if (pp.size() > 0) {
                Iterator<String> it = pp.iterator();
                while (it.hasNext()) {
                    String[] info = it.next().split(";");
                    
                    if(info.length > 4){
                        PrintUtil.outn(info[0] + " [ v" + info[2] + " => to update ]");
                    }else{
                        PrintUtil.outn(info[0] + " [ v" + info[2] + " => to install ]");
                    }
                    
                    PrintUtil.outn("  " + info[1]);
                    PrintUtil.outn("");
                }
            } else {
                PrintUtil.outn("There is no available plugins.");
            }
        } catch (IOException ioex) {
            PrintUtil.outn("ERRO!! Remole plugin list is not acessible.");
        } catch (Exception ex) {
            PrintUtil.outn("ERRO!! was not possible to list the plugins.");
        }

        PrintUtil.outn("");
    }

    public static void install(String op) {
        
        PrintUtil.outn("Instaling plugin: "+op+"");
        PrintUtil.outn("");
        
        try {
            
            TreeMap<String,String> dados = new TreeMap<String,String>();
            
            Collection<String> pp_dispo = getPlugins();
            for (String info : pp_dispo) {
                String[] lin = info.split(";");
                dados.put(lin[0], info);
            }
            
            if (dados.containsKey(op)) {
                String[] info = dados.get(op).split(";");
                String arq = FileUtil.download(info[3], FileUtil.getApplicationPath() + "/plugins");
                FileUtil.extractZip(FileUtil.getApplicationPath() + "/plugins/" + arq, FileUtil.getApplicationPath() + "/plugins");
                FileUtil.deleteDir(FileUtil.getApplicationPath() + "/plugins/" + arq);
                PrintUtil.outn("");
                PrintUtil.outn("Plugin successfully installed.");

            } else {
                PrintUtil.outn("ERRO!! Plugin is not available.");
            }
            PrintUtil.outn("");
        } catch (IOException ioex) {
            PrintUtil.outn("ERRO!! Remole plugin list is not acessible.");
            PrintUtil.outn("");
        } catch (Exception ex) {
            PrintUtil.outn("ERRO!! Not possible install the plugins.");
            PrintUtil.outn("");
        }
    }

    private static Collection<String> getPlugins() throws IOException {


        //String plugins[] = FileUtil.openURL("http://localhost/cook/plugins.conf").split("\n");
        String plugins[] = FileUtil.openURL("https://github.com/itakenami/cook/raw/master/plugins.conf").split("\n");


        TreeMap<String, String> pp = new TreeMap<String, String>();
        for (int i = 0; i < plugins.length; i++) {
            pp.put((plugins[i].split(";"))[0], plugins[i]);
        }


        File folder = new File(FileUtil.getApplicationPath() + "/plugins");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (pp.containsKey(listOfFiles[i].getName())) {
                
                String info_remote = pp.get(listOfFiles[i].getName());
                String ver_remote = (info_remote.split(";"))[2];
                String ver_local = FileUtil.getPropetry(FileUtil.getApplicationPath() + "/plugins/" + listOfFiles[i].getName() + "/config.properties", "version");
                
                if(ver_local.equals(ver_remote)){
                    pp.remove(listOfFiles[i].getName());
                }else{
                    pp.put(listOfFiles[i].getName(), info_remote+";A");
                }
                
            }
        }


        return pp.values();
    }
    
    public static void uninstall(String plugin){
        
        try{
            if(FileUtil.fileExist(FileUtil.getApplicationPath() + "/plugins/" + plugin)){
                FileUtil.deleteDir(FileUtil.getApplicationPath() + "/plugins/" + plugin);
                PrintUtil.outn("Plugin successfully uninstalled.");
                PrintUtil.outn("");
            }else{
                PrintUtil.outn("ERRO!! Plugin is not available.");
                PrintUtil.outn("");
            }
        }catch(Exception ex){
            PrintUtil.outn("ERRO!! Not possible uninstall the plugins.");
            PrintUtil.outn("");
        }
        
    }
}
