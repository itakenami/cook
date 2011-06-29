package cook.core.ui;

import cook.util.FileUtil;
import cook.util.PrintUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Screen
{

    private static String getVersion()
    {
        return "0.0.3";
    }

    public static void header()
    {

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

    public static void start()
    {
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getYellowFont() + "Starting cooking. Wait..." + PrintUtil.getColorReset());
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont() + "### Started cooking ##################################################" + PrintUtil.getColorReset());
        PrintUtil.outn("");
    }

    public static void end()
    {
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont() + "### Baking completed successfully ####################################" + PrintUtil.getColorReset());
        PrintUtil.outn("");
        PrintUtil.outn(PrintUtil.getGreenFont() + "Thank you for use Cook!! Bye, Bye" + PrintUtil.getColorReset());
        PrintUtil.outn("");
    }

    public static void blank()
    {
        PrintUtil.outn("Use: cook [plugin] [params]");
        PrintUtil.outn("OR: cook help");
        PrintUtil.outn("");
    }

    public static void help()
    {
        PrintUtil.outn("Use: cook [plugin] [param]");
        PrintUtil.outn("");
        PrintUtil.outn("Try commands:");
        PrintUtil.outn("~~~~~~~~~~~~~");
        PrintUtil.outn(" cook help");
        PrintUtil.outn(" cook list");
        PrintUtil.outn(" cook remote-list");
        PrintUtil.outn(" cook install [plugin]");
        PrintUtil.outn(" cook [plugin] help");
        PrintUtil.outn(" cook [plugin] version");
        PrintUtil.outn("");
    }

    public static void list()
    {
        PrintUtil.outn("Installed plugins:");
        PrintUtil.outn("~~~~~~~~~~~~~~~~~~");
        File folder = new File(FileUtil.getApplicationPath() + "/plugins");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                PrintUtil.outn(" " + listOfFiles[i].getName());
            }
        }

        PrintUtil.outn("");
    }

    public static void remoteList()
    {
        PrintUtil.outn("Plugins available:");
        PrintUtil.outn("~~~~~~~~~~~~~~~~~~");
        ArrayList<String> pp = new ArrayList<String>();
        try {
            pp = getPlugins();
        } catch (IOException ex) {
            PrintUtil.outn(PrintUtil.ERRO, ex.getMessage());
        }
        if(pp.size() > 0){
            for (int i = 0; i < pp.size(); i++) {
                PrintUtil.outn(pp.get(i));
            }
        }else{
            PrintUtil.outn("There is no available plugins.");
        }

        PrintUtil.outn("");
    }

    public static void install(String op)
    {
        String url = null;
        ArrayList<String> pp_dispo = new ArrayList<String>();
        try {
            pp_dispo = getPlugins();
            if (pp_dispo.contains(op)) {
                BufferedReader f = new BufferedReader(new FileReader(FileUtil.getApplicationPath() + "/plugins.conf"));
                String linha;
                String texto = "";
                while ((linha = f.readLine()) != null) {
                    texto += linha + "\n";
                }
                String plugins[] = texto.split("\n");
                String nomePlugin[];
                for (int i = 0; i < plugins.length; i++) {
                    nomePlugin = plugins[i].split(";");
                    if (nomePlugin[0].equals(op)) {
                        url = nomePlugin[2];
                    }
                }
                try {
                    String arq = FileUtil.download(url, FileUtil.getApplicationPath() + "/plugins");
                    FileUtil.extractZip(FileUtil.getApplicationPath() + "/plugins/"+arq, FileUtil.getApplicationPath() + "/plugins");
                    PrintUtil.outn("");
                    PrintUtil.outn(PrintUtil.getGreenFont() + "Plugin successfully installed." + PrintUtil.getColorReset());
                } catch (FileNotFoundException ex) {
                    PrintUtil.outn(PrintUtil.ERRO, ex.getMessage());
                } catch (IOException ex) {
                    PrintUtil.outn(PrintUtil.ERRO, ex.getMessage());
                } catch (Exception ex) {
                    PrintUtil.outn(PrintUtil.ERRO, ex.getMessage());
                }
            } else {
                PrintUtil.outn(PrintUtil.ERRO, PrintUtil.getRedFont()+"Plugin is not available."+PrintUtil.getColorReset());
            }
        } catch (IOException ex) {
            PrintUtil.outn(PrintUtil.ERRO, ex.getMessage());
        }
    }

    private static ArrayList<String> getPlugins() throws IOException
    {
        ArrayList<String> pp_disponiveis = new ArrayList<String>();
        ArrayList<String> pp_add = new ArrayList<String>();
        try {
            BufferedReader f = new BufferedReader(new FileReader(FileUtil.getApplicationPath() + "/plugins.conf"));
            String linha;
            String texto = "";
            while ((linha = f.readLine()) != null) {
                texto += linha + "\n";
            }
            String plugins[] = texto.split("\n");
            String nomePlugin[];
            ArrayList<String> pp = new ArrayList<String>();
            for (int i = 0; i < plugins.length; i++) {
                nomePlugin = plugins[i].split(";");
                pp.add(nomePlugin[0]);
            }
            File folder = new File(FileUtil.getApplicationPath() + "/plugins");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < pp.size(); i++) {
                for (int j = 0; j < listOfFiles.length; j++) {
                    if (listOfFiles[j].getName().equals(pp.get(i))) {
                        pp_add.add(listOfFiles[j].getName());
                    }
                }
            }

            for (int i = 0; i < pp.size(); i++) {
                if (!pp_add.contains(pp.get(i))) {
                    pp_disponiveis.add(pp.get(i));
                }
            }
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
        return pp_disponiveis;
    }
}
