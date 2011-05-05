/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cook.core;

import java.io.IOException;
import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.lang.reflect.Method;

public class Classpath {
    

    private static final Class[] parameters = new Class[]{URL.class};
    
    
    public static void loadJars(String path) throws IOException{
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                addFile(path + "/" + listOfFiles[i].getName());

            }
        }
    }

    public static void addFile(String s) throws IOException {
        File f = new File(s);
        addFile(f);
    }//end method

    public static void addFile(File f) throws IOException {      
        addURL(f.toURI().toURL());
    }//end method

    public static void addURL(URL u) throws IOException {

        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod("addURL", parameters);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[]{u});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }//end try catch

    }//end method
}//end class
