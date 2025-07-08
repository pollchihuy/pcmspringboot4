package com.juaracoding.pcmspringboot4.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ReadTextFileSB {
    private String[] exceptionString = new String[2];
    private String contentFile;
    private byte[] contentOfFile;
    private byte[] bdata;
    private StringBuilder sBuild = new StringBuilder();
    public ReadTextFileSB(String pathFiles) throws Exception {
        setContentFile(pathFiles);
    }
    public void setContentFile(String fileName) throws Exception {
        InputStream is = null;
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        /** directory template-email di hardcode, jadi jika menggunakan class ini maka seluruh template email yang mau di load ditaruh di directory resources->template-email */
        Resource[] resources = scanner.getResources(new StringBuilder().
                append("template-email").append("/").
                append("*").toString());
        try {
            if(resources==null || resources.length==0)
            {
                throw new Exception("Problem in server F-X-001 : Data resources file not found");
            }
            for(Resource r : resources)
            {
                if(r.getFilename().equals(fileName))
                {
                    is = r.getInputStream();
                    break;
                }
            }
            this.contentOfFile = is.readAllBytes();
            this.contentFile = new String(contentOfFile, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            try {
                is.close();
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }
    public String getContentFile()
    {
        return contentFile;
    }

    public byte[] getByteOfFile()
    {
        return contentOfFile;
    }

    public static void main(String[] args) throws Exception {
        StringBuilder sBuild = new StringBuilder();
	}
}