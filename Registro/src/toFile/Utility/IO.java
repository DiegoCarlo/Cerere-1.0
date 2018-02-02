/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 *
 * @author DiegoCG
 */
public class IO
{
    /**
     * create the directory
     * @param directory 
     * @return false if it already exist
     */
    public static boolean creaPath(String directory)
    {
        Path path = Paths.get(directory);
        if (!Files.exists(path))
        {
            File dir = new File(directory);
            return dir.mkdir();
        }
        return true;
    }
    public static void log(String string)
    {
        registro.Registro.log(string);
    }
    public static String readStringFile(String pathFile)
    {
        log("IO.readStringFile " + pathFile);
        String contents = null;
        try
        {
            File f = new File(pathFile);
            if(f.exists() && !f.isDirectory())
            {
                //"UTF-8 UTF-16BE / UTF-16LE"
                contents = Files.lines(Paths.get(pathFile), Charset.forName("Cp1252")).collect(Collectors.joining("\n"));
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        return contents;
    }
    public static void writeStringFile(String pathFile, String text)
    {
        try
        {
            log("IO.writeStringFile " + pathFile);
            File file = new File (pathFile);
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "Cp1252"))
            {
                writer.write(text);
            }
        }
        catch(IOException x)
        {
            x.printStackTrace();
        }
    }
}
