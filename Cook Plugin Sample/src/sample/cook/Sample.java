package sample.cook;

import cook.core.FreemarkerWrapper;
import cook.core.IFCook;
import cook.core.ResultProcess;
import cook.util.FileUtil;
import cook.util.PrintUtil;

public class Sample implements IFCook {

    //Path out file
    private String PATH_OUT;
    //In param
    private String[] param;

    //Return the version of plugin
    @Override
    public String getVersion() {
        return "0.1";
    }

    //Print header message of plugin start
    @Override
    public void printHeader() {
        PrintUtil.out("Sample Plugin. Version "+getVersion());
    }

    //Print help invoke
    @Override
    public void printHelp() {
        PrintUtil.out("Use: cook sample [name]");
    }

    //Start cook plugin. Use thi method for valid in param
    @Override
    public boolean start(String[] param) {
        
        //Valid in param
        if(param.length==1 || param[1].equals("")){
            PrintUtil.out("Please enter your name");
            printHelp(); //show help
            PrintUtil.out("");
            return false;
        }
        
        this.param = param;
        
        return true;
        
    }

    //Valid directory for execute the plugin
    @Override
    public boolean validDirectory() {

        boolean saida;
        
        //get the path of user execute script
        String pwd = FileUtil.getPromptPath();

        //Teste if out dir is created
        if (FileUtil.fileExist(pwd + "/out")) {
            PATH_OUT = pwd + "/out";
            saida = true;
            PrintUtil.out("Generate in " + PATH_OUT);
        } else {
            //If out dir not created, try create dir
            if (FileUtil.createDir(pwd + "/out")){
                PATH_OUT = pwd + "/out";
                saida = true;
                PrintUtil.out("Generate in " + PATH_OUT);
            }else{
                saida = false;
                PrintUtil.out("Don't create a folder for generate.");
            }

        }

        return saida;
    }

    //Execute plugin
    @Override
    public ResultProcess cook() {
        
        ResultProcess out = new ResultProcess();
        
        try {
            //Add variable for param define in template
            FreemarkerWrapper.getInstance().addVar("name", param[1]);    
            
            //Call parse for template
            String arq = FreemarkerWrapper.getInstance().parseTemplate("test.ftl");
            
            //Save template out
            PrintUtil.out("Save file "+PATH_OUT + "/" + param[1] + ".txt");            
            FileUtil.saveToPath(PATH_OUT + "/" + param[1] + ".txt", arq);
            
            //Define out of process
            out.setResultProcess(ResultProcess.SUCESS, "Successfully generated");
            
        } catch (Exception ex) {
            
            PrintUtil.out(""); 
            PrintUtil.out("Erro generated!!"); 
            //Define out of process exception
            out.setResultProcess(ResultProcess.ERROR, ex.getMessage());
            
        } finally {
            
            return out;
            
        }
    }

    //End of file cicle
    @Override
    public void end() {
        PrintUtil.out("");
        PrintUtil.out("Open file to see the result.");
    }

    
}
