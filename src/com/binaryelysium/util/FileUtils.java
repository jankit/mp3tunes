package com.binaryelysium.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtils
{
    public static void copyFile(String src, String dest) throws IOException
    {
        copyFile(new File(src), new File(dest));
    }
    
    public static void copyFile(File sourceFile, File destFile) throws IOException 
    {
        if(!destFile.exists()) {
         destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
         source = new FileInputStream(sourceFile).getChannel();
         destination = new FileOutputStream(destFile).getChannel();
         destination.transferFrom(source, 0, source.size());
        }
        finally {
         if(source != null) {
          source.close();
         }
         if(destination != null) {
          destination.close();
         }
       }
    }
}
