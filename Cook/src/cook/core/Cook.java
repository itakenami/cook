package cook.core;

import cook.core.ui.Screen;
import cook.util.FileUtil;
import cook.util.PrintUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                PrintUtil.out("SAIDA:" + FileUtil.getApplicationPath());
                return false;
            }


            //Obtem o plugin que será utilizado
            PLUGIN = args[0];

            //Define o PATH do plugin que será utilizado
            PATH = FileUtil.getApplicationPath() + "/plugins/" + PLUGIN;

            if (!(new File(PATH)).exists()) {

                PrintUtil.out("Plugin does not exist!!! Try a valid plugin.");
                PrintUtil.out("");
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
        PrintUtil.outn("Loading the property file of the plugin " + PLUGIN + ": ");
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(PATH + "/config.properties"));

            if (props.getProperty("main") == null) {
                PrintUtil.oute("\"main\" property not found.");
                return;
            }

            if (props.getProperty("templates") == null) {
                PrintUtil.oute("\"templates\" property not found.");
                return;
            }

            //Define as propriedades que serão utilizadas
            MAIN_CLASS = props.getProperty("main");
            TEMPLATE_DIR = props.getProperty("templates");

            PrintUtil.outo("OK");
        } catch (FileNotFoundException e1) {
            PrintUtil.oute("Property file not found.");
            return;
        } catch (Exception e2) {
            PrintUtil.oute(e2.getMessage());
            return;

        }

        PrintUtil.outn("Loading the plugin libraries: ");
        try {
            //Define o PATH das lib's do plugin que será utilizado
            final String LIB_PATH = PATH + "/lib";

            //Carrega as libs do plugin
            Classpath.loadJars(LIB_PATH);

            PrintUtil.outo("OK");
        }catch (NullPointerException e1){
            PrintUtil.oute("lib folder not found.");
            return;
        } catch (Exception e2) {
            PrintUtil.oute(e2.getMessage());
            return;
        }



        //Cria o objetos do plugin que será utilizado
        PrintUtil.outn("Instantiating plugin: ");
        try {
            cook = (IFCook) Class.forName(MAIN_CLASS).newInstance();
            PrintUtil.outo("OK");
        } catch (ClassNotFoundException e1) {
            PrintUtil.oute("Class of plugin not found.");
            return;
        } catch (Exception e2) {
            PrintUtil.oute(e2.getMessage());
            return;
        }

        if (args.length > 1) {
            
            
            if (args[1].equals("help")) {
                Screen.start();
                cook.printHeader();
                PrintUtil.out("");
                cook.printHelp();
                Screen.end();
                return;
            }

            if (args[1].equals("version")) {
                Screen.start();
                cook.printHeader();
                PrintUtil.out("");
                PrintUtil.out("Version of plugin: " + cook.getVersion());
                Screen.end();
                return;
            }
            

        }


        //Inicia o ciclo de vida
        Screen.start();

        cook.printHeader();

        PrintUtil.out("");

        if (!cook.start(args)) {
            PrintUtil.out("");
            PrintUtil.out("--- ERRO ----------------------------------------------------------");
            PrintUtil.out("The plugin is not started correctly.");
            PrintUtil.out("-------------------------------------------------------------------");
            PrintUtil.out("");
            PrintUtil.out("Ho no!!! The plugin did not run correctly. Try again.");
            PrintUtil.out("");
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

                PrintUtil.out("");
                PrintUtil.out(out.getMESSAGE());
                cook.end();

            } else {

                PrintUtil.out("");
                PrintUtil.out("--- ERRO ----------------------------------------------------------");
                PrintUtil.out("" + out.getMESSAGE());
                PrintUtil.out("-------------------------------------------------------------------");
                PrintUtil.out("");
                PrintUtil.out("Ho no!!! The plugin did not run correctly. Try again.");
                PrintUtil.out("");
                return;
            }

        } else {

            PrintUtil.out("");
            PrintUtil.out("--- ERRO ----------------------------------------------------------");
            PrintUtil.out("You're in bad directory to run this plugin.");
            PrintUtil.out("-------------------------------------------------------------------");
            PrintUtil.out("");
            PrintUtil.out("Ho no!!! The plugin did not run correctly. Try again.");
            PrintUtil.out("");
            return;
        }

        Screen.end();
    }
}
