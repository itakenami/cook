/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cook.core;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author itakenami
 */
public class FreemarkerWrapper {

    private Configuration cfg;
    private final String TEMPLATES_FOLDER;
    private Map map;
    
    public static FreemarkerWrapper instance;
    
    public static FreemarkerWrapper getInstance(){
        return instance;
    }
    
    protected static void newInstance(String dir){
        instance = new FreemarkerWrapper(dir);
    }

    private FreemarkerWrapper(String dir) {
        cfg = new Configuration();
        TEMPLATES_FOLDER = dir;
        map = new HashMap();
    }

    public void addVar(Object nome, Object value) {
        map.put(nome, value);
    }

    public String parseTemplate(String templateName) throws Exception {
        
        try{

            freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATES_FOLDER));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            Template t = cfg.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            t.process(map, writer);
            writer.flush();
            writer.close();
            return writer.toString();
        }catch(IOException e1){
            throw new Exception(e1.getMessage());
        }catch (TemplateException e2){
            throw new Exception(e2.getMessage());
        }catch (Exception e3){
            throw e3;
        }
        

    }
}
