package cook.core;

import cook.core.ui.Screen;
import cook.util.FileUtil;
import cook.util.PrintUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Properties;

public class Cook {

    //Parametros de cozimento
    String[] args;
    //Obtem o plugin que será utilizado
    String PLUGIN = null;
    //Define o PATH do plugin que será utilizado
    String PATH = null;
    //Plugin que será utilizado
    IFCook cook = null;

    //Construtor da classe
    public Cook(String[] args) {
        this.args = args;
    }

    //Valida os paramestros de entrada
    private boolean valid() {


        if (args.length == 0 || args[0].equals("")) {

            Screen.blank();
            return false;

        } else {

            if ("help".equals(args[0])) {
                Screen.help();
                return false;
            }

            if ("list".equals(args[0])) {
                Screen.list();
                return false;
            }

            if ("teste".equals(args[0])) {
                PrintUtil.outn("SAIDA:" + System.getProperty("os.arch"));
                return false;
            }

            if("list-remote".equals(args[0]))
            {
                Screen.listRemote();
                return false;
            }

            if("install".equals(args[0])){
                if(args.length > 1){
                    Screen.install(args[1]);
                    return false;
                }
                PrintUtil.outn(PrintUtil.ERRO,PrintUtil.getRedFont()+"Plugin is not available"+PrintUtil.getColorReset());
                PrintUtil.outn("");
                return false;
            }

            //Obtem o plugin que será utilizado
            PLUGIN = args[0];

            //Define o PATH do plugin que será utilizado
            PATH = FileUtil.getApplicationPath() + "/plugins/" + PLUGIN;

            if (!(new File(PATH)).exists()) {

                PrintUtil.outn(PrintUtil.getRedFont()+"Plugin does not exist!!! Try a valid plugin."+PrintUtil.getColorReset());
                PrintUtil.outn("");
                Screen.blank();

                return false;

            } else {

                return true;
            }
        }
    }

    public void start() {

        //Imprime o cabecalho
        Screen.header();

        //Valida os parametros de entrada
        if (!valid()) {
            return;
        }

        String MAIN_CLASS = null;
        String TEMPLATE_DIR = null;

        //Carrega o arquivos de propriedades do plugin
        PrintUtil.out(PrintUtil.getYellowFont()+"Loading the property file of the plugin " + PLUGIN + ": "+PrintUtil.getColorReset());
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(PATH + "/config.properties"));

            if (props.getProperty("main") == null) {
                
                PrintUtil.redFont();
                PrintUtil.outn(PrintUtil.ERRO,"\"main\" property not found.");
                PrintUtil.colorReset();
                PrintUtil.outn("");
                return;
            }

            if (props.getProperty("templates") == null) {
                
                PrintUtil.redFont();
                PrintUtil.outn(PrintUtil.ERRO,"\"templates\" property not found.");
                PrintUtil.colorReset();
                PrintUtil.outn("");
                return;
            }

            //Define as propriedades que serão utilizadas
            MAIN_CLASS = props.getProperty("main");
            TEMPLATE_DIR = props.getProperty("templates");

            PrintUtil.greenFont();
            PrintUtil.outn(PrintUtil.MSG,"[OK]");
            PrintUtil.colorReset();
            
        } catch (FileNotFoundException e1) {
            
            PrintUtil.redFont();
            PrintUtil.outn(PrintUtil.ERRO,"Property file not found.");
            PrintUtil.colorReset();
            PrintUtil.outn("");
            return;
        } catch (Exception e2) {
            
            PrintUtil.redFont();
            PrintUtil.outn(PrintUtil.ERRO,e2.getMessage());
            PrintUtil.colorReset();
            PrintUtil.outn("");
            return;

        }

        PrintUtil.out(PrintUtil.getYellowFont()+"Loading the plugin libraries: "+PrintUtil.getColorReset());
        try {
            //Define o PATH das lib's do plugin que será utilizado
            final String LIB_PATH = PATH + "/lib";

            //Carrega as libs do plugin
            Classpath.loadJars(LIB_PATH);

            PrintUtil.greenFont();
            PrintUtil.out(PrintUtil.MSG,"[OK]");
            PrintUtil.colorReset();
            
        }catch (NullPointerException e1){
            
            PrintUtil.redFont();
            PrintUtil.outn(PrintUtil.ERRO,"lib folder not found.");
            PrintUtil.colorReset();
            PrintUtil.outn("");
            return;
        } catch (Exception e2) {
            
            PrintUtil.redFont();
            PrintUtil.outn(PrintUtil.ERRO,e2.getMessage());
            PrintUtil.colorReset();
            PrintUtil.outn("");
            return;
        }



        //Cria o objetos do plugin que será utilizado
        PrintUtil.out(PrintUtil.getYellowFont()+"Instantiating plugin: "+PrintUtil.getColorReset());
        try {
            cook = (IFCook) Class.forName(MAIN_CLASS).newInstance();
            
            PrintUtil.greenFont();
            PrintUtil.out(PrintUtil.MSG,"[OK]");
            PrintUtil.colorReset();
            
        } catch (ClassNotFoundException e1) {
            
            PrintUtil.redFont();
            PrintUtil.outn(PrintUtil.ERRO,"Class of plugin not found.");
            PrintUtil.colorReset();
            PrintUtil.outn("");
            return;
        } catch (Exception e2) {
            
            PrintUtil.redFont();
            PrintUtil.outn(PrintUtil.ERRO,e2.getMessage());
            PrintUtil.colorReset();
            PrintUtil.outn("");
            return;
        }

        if (args.length > 1) {
            
            
            if (args[1].equals("help")) {
                Screen.start();
                cook.printHeader();
                PrintUtil.outn("");
                cook.printHelp();
                Screen.end();
                return;
            }

            if (args[1].equals("version")) {
                Screen.start();
                cook.printHeader();
                PrintUtil.outn("");
                PrintUtil.outn("Version of plugin: " + cook.getVersion());
                Screen.end();
                return;
            }
            

        }


        //Inicia o ciclo de vida
        Screen.start();

        cook.printHeader();

        PrintUtil.outn("");

        if (!cook.start(args)) {
            PrintUtil.outn("");
            PrintUtil.outn(PrintUtil.getRedFont()+"--- ERRO ----------------------------------------------------------"+PrintUtil.getColorReset());
            PrintUtil.outn("The plugin is not started correctly.");
            PrintUtil.outn(PrintUtil.getRedFont()+"-------------------------------------------------------------------"+PrintUtil.getColorReset());
            PrintUtil.outn("");
            PrintUtil.outn(PrintUtil.getRedFont()+"Ho no!!! The plugin did not run correctly. Try again."+PrintUtil.getColorReset());
            PrintUtil.outn("");
            return;
        }

        //Valida se o script está na pasta certa para ser utilizado
        if (cook.validDirectory()) {

            //Cria um Warapper do Freemarker com o caminhos dos templates
            //FreemarkerWrapper maker = new FreemarkerWrapper(PATH + "/" + TEMPLATE_DIR + "/");
            FreemarkerWrapper.newInstance(PATH + "/" + TEMPLATE_DIR + "/");

            //Executa o procedimento
            ResultProcess out = cook.cook();

            if (!out.isERROR()) {

                PrintUtil.outn("");
                PrintUtil.outn(out.getMESSAGE());
                cook.end();

            } else {

                PrintUtil.outn("");
                PrintUtil.outn(PrintUtil.getRedFont()+"--- ERRO ----------------------------------------------------------"+PrintUtil.getColorReset());
                PrintUtil.outn("" + out.getMESSAGE());
                PrintUtil.outn(PrintUtil.getRedFont()+"-------------------------------------------------------------------"+PrintUtil.getColorReset());
                PrintUtil.outn("");
                PrintUtil.outn(PrintUtil.getRedFont()+"Ho no!!! The plugin did not run correctly. Try again."+PrintUtil.getColorReset());
                PrintUtil.outn("");
                return;
            }

        } else {

            PrintUtil.outn("");
            PrintUtil.outn(PrintUtil.getRedFont()+"--- ERRO ----------------------------------------------------------"+PrintUtil.getColorReset());
            PrintUtil.outn("You're in bad directory to run this plugin.");
            PrintUtil.outn(PrintUtil.getRedFont()+"-------------------------------------------------------------------"+PrintUtil.getColorReset());
            PrintUtil.outn("");
            PrintUtil.outn(PrintUtil.getRedFont()+"Ho no!!! The plugin did not run correctly. Try again."+PrintUtil.getColorReset());
            PrintUtil.outn("");
            return;
        }

        Screen.end();
    }
}
