package com.maven.my.compress;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang3.time.DateFormatUtils;

import net.coobird.thumbnailator.Thumbnails;
/**
 * Hello world!
 *
 */
public class App {
	
	public static String outputPath = "\\img_compress" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") ;
	public static float size = 0.5f;//图片大小（长宽）压缩比例 从0-1，1表示原图
	public static float quality = 0.5f;//图片质量压缩比例 从0-1，越接近1质量越好
	public static int count = 0;
	public static int i = 1;
	
    public static void main( String[] args )
    {
        System.out.println( "start" );

        String inputPath="C:\\Users\\Administrator\\Desktop\\照片压缩版";
        
        File file = new File(inputPath);
        folderMethod(inputPath, file.getParent(), size, quality, true);
        folderMethod(inputPath, file.getParent(), size, quality, false);
    }
    
    public static void folderMethod(String inputPath, String basePath, float size, float quality, boolean isCount) {
        File infile = new File(inputPath);
        if (infile.exists()) {
            File[] files = infile.listFiles();
            if (null != files) {
                for (File itemFile : files) {
                	if (isCount) {
                		count++;
					}else {
				    	System.out.println("-------------------");
	                    System.out.println("[" + i + "/" + count + "]");
						i++;
					}
                    
                    String inPath = itemFile.getParent() +"\\"+ itemFile.getName();
                    String outPath = basePath + outputPath + itemFile.getAbsolutePath().replace(basePath,"");
                    if (itemFile.isDirectory()) {
                    	if (!isCount) {
                            System.out.println("dir: " + itemFile.getAbsolutePath());
                    		File outFile = new File(outPath);
                    		outFile.mkdirs();
						}
                        folderMethod(itemFile.getAbsolutePath(), basePath, size, quality, isCount);
                    } else {
                    	if (!isCount) {
                            System.out.println("file: " + itemFile.getAbsolutePath());

	                        try {
	                  			compressPic(inPath, outPath, size, quality);
	                  		} catch (Exception e) {
	                  			e.printStackTrace();
	                  		}
                    	}
                    }
                }
            }
        } else {
            System.out.println("file not exists: "+infile.getAbsolutePath());
            
        }
    }
    
    public static void compressPic(String inputPath, String outputPath, float size, float quality) throws Exception {
	   	 System.out.println("inputPath: " + inputPath);
	   	 System.out.println("outputPath: " + outputPath);
    	 System.out.println("size: " + size);
    	 System.out.println("quality: " + quality);
	   	 
	   	 if(size < 0) {
	   		 size = 0;
	   	 }else if(size > 1) {
	   		size = 1;
	   	 }
	   	 if(quality < 0) {
	   		quality = 0;
	   	 }else if(quality > 1) {
	   		quality = 1;
	   	 }
    	 
	   	 Thumbnails.of(new File(inputPath))
            .scale(size) 
            .outputQuality(quality) 
            .toOutputStream(new FileOutputStream(outputPath));
      }
}