package cook.util;

import cook.Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class FileUtil {

    private static String findJarParentPath(File jarFile) {
        while (jarFile.getPath().contains(".jar")) {
            jarFile = jarFile.getParentFile();
        }

        return jarFile.getPath().substring(5);
    }
    
    public static String getPromptPath(){
        return System.getProperty("user.dir");
    }

    public static String getApplicationPath() {
        String url = Main.class.getResource(Main.class.getSimpleName() + ".class").getPath();
        File dir = new File(url).getParentFile();
        String path = null;

        if (dir.getPath().contains(".jar")) {
            path = findJarParentPath(dir);
        } else {
            path = dir.getPath();
        }

        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return path.replace("%20", " ");
        }
    }

    public static void saveToPath(String file, String body, boolean append) {
        FileWriter x = null;
        try {
            x = new FileWriter(file, append);
            x.write(body);
        } catch (Exception ex) {
        } finally {
            try {
                x.close();
            } catch (IOException ex) {
            }

        }
    }
    
    public static void saveToPath(String file, String body){
        saveToPath(file, body, false);
    }
    
    public static boolean fileExist(String path){
        return (new File(path)).exists();
    }
    
    public static boolean createDir(String dir){
        return (new File(dir)).mkdir();
    }
}
