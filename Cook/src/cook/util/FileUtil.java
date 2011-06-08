package cook.util;

import cook.Main;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import jline.ANSIBuffer;


public class FileUtil
{

    private static final int TAMANHO_BUFFER = 2048;
    private static final int MAX_BUFFER_SIZE = 1024;

    private static final int DOWNLOADING = 0;

    private static int size;
    private static int downloaded;
    private static int status;



    private static String findJarParentPath(File jarFile)
    {
        while (jarFile.getPath().contains(".jar")) {
            jarFile = jarFile.getParentFile();
        }

        return jarFile.getPath().substring(5);
    }

    public static String getPromptPath()
    {
        return System.getProperty("user.dir");
    }

    public static String getApplicationPath()
    {
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

    public static void saveToPath(String file, String body, boolean append)
    {
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

    public static void saveToPath(String file, String body)
    {
        saveToPath(file, body, false);
    }

    public static boolean fileExist(String path)
    {
        return (new File(path)).exists();
    }

    public static boolean createDir(String dir)
    {
        return (new File(dir)).mkdir();
    }

    public static boolean extractZip(String fileZip, String path) throws ZipException, IOException, Exception
    {
        File zipFile = new File(fileZip);
        File directory = new File(path);
        ZipFile zip = null;
        File arquivo = null;
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer = new byte[TAMANHO_BUFFER];
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (!directory.exists() || !directory.isDirectory()) {
                throw new IOException("Enter a valid directory.");
            }
            zip = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> e = zip.entries();
            PrintUtil.outn("");
            PrintUtil.outn("Starting decompress...");
            while (e.hasMoreElements()) {
                ZipEntry entrada = (ZipEntry) e.nextElement();
                arquivo = new File(directory, entrada.getName());
                if (entrada.isDirectory() && !arquivo.exists()) {
                    arquivo.mkdirs();
                    continue;
                }

                if (!arquivo.getParentFile().exists()) {
                    arquivo.getParentFile().mkdirs();
                }
                try {
                    is = zip.getInputStream(entrada);
                    os = new FileOutputStream(arquivo);
                    int bytesLidos = 0;
                    if (is == null) {
                        throw new ZipException("Error reading zip: " + entrada.getName());
                    }
                    while ((bytesLidos = is.read(buffer)) > 0) {
                        os.write(buffer, 0, bytesLidos);
                    }
                }catch (IOException ex){

                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception ex) {
                            throw new Exception();
                        }
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (Exception ex) {
                            throw new Exception();
                        }
                    }
                }
            }
        }catch (ZipException zp){
            throw new ZipException("Error opening file zip.");
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (Exception e) {
                    throw new Exception();
                }
            }
        }
        return true;
    }

    public static Boolean download(String stringUrl, String pathLocal) throws FileNotFoundException, IOException
    {
        int flag = 0;
        InputStream stream = null;
        try {
            status = 0;
            RandomAccessFile file = null;
            URL url = new URL(stringUrl);
            String arquivo = url.getFile();
            String nomeArquivoLocal = arquivo.substring(arquivo.lastIndexOf('/') + 1);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            size = connection.getContentLength();
            stream = connection.getInputStream();
            file = new RandomAccessFile(new File(pathLocal+ "/" + nomeArquivoLocal), "rw");

            PrintUtil.outn("");
            PrintUtil.outn("Starting download...");
            PrintUtil.outn("");
            PrintUtil.outn("File: "+nomeArquivoLocal);
            PrintUtil.outn("");
            System.out.print("~ [");
            System.out.print(ANSIBuffer.ANSICodes.right(50));
            System.out.println("]");

            System.out.print(ANSIBuffer.ANSICodes.up(1));
            System.out.print(ANSIBuffer.ANSICodes.right(3));

            PrintUtil.disableASCII();

            while (status == DOWNLOADING) {
                byte buffer[];
                if (size - downloaded > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[size - downloaded];
                }
                int read = stream.read(buffer);
                if (read == -1) {
                    break;
                }
                file.write(buffer, 0, read);
                downloaded += read;
                int parcial = downloaded*50/size;

                printPercent(parcial - flag);

                flag = parcial;
            }    
            System.out.print(ANSIBuffer.ANSICodes.right(2));
            System.out.print("100%");
            file.close();
            stream.close();
            PrintUtil.enableASCII();
            System.out.println("");

        }catch (FileNotFoundException ex){
            throw new FileNotFoundException("File not found !");
        }catch (IOException ex){
            throw new IOException();
        }
        return true;
    }

    private static void printPercent(int i)
    {
        for (int j = 0; j < i; j++) {
            System.out.print(">");
        }
    }
}
