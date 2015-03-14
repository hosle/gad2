package com.game.pintu;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
 
/**
 * @author 码农小江
 * H20121012.java
 * 2012-10-12下午11:40:21
 */
public class predict 
{
    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static void readTxtFile(String filePath,String str[])
    {
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists())
                { //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    int i = 0;
                    while((lineTxt = bufferedReader.readLine()) != null)
                    {
                       str[i] = lineTxt;
                       i++;
                    }
                    read.close();
        }else
        {
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     
    }
    
    public static void WriteDate(String filePath,String str[]) //数组
    {  
        
        try{  
	            File file = new File(filePath);  
	            if (file.exists()) 
	            {  
	                  
	                file.delete();  
	            }  
	              
	            file.createNewFile();  
	              
	            BufferedWriter output = new BufferedWriter(new FileWriter(file));   
	              
	            ArrayList ResolveList = new ArrayList();  
	              
	            for (int i = 0; i < 1; i++)
	            {  
	                  
	                ResolveList.add(str[i]);  
	                  
	            }  
	              
	            for (int i=0 ;i<1/*ResolveList.size()/2*/; i++) 
	            {  
	                output.write(String.valueOf(ResolveList.get(i)) + "\r\n"); 
	            }  
	            output.close();  
	        } 
	        catch (Exception ex) 
	        {  
	            System.out.println(ex);  
	        }          
    }  
    public static void FeatureWriteDate(String filePath,String str[]) //数组
    {  
        
        try{  
	            File file = new File(filePath);  
	            if (file.exists()) 
	            {  
	                  
	                file.delete();  
	            }  
	              
	            file.createNewFile();  
	              
	            BufferedWriter output = new BufferedWriter(new FileWriter(file));   
	              
	            ArrayList ResolveList = new ArrayList();  
	              
	            for (int i = 0; i < 21; i++)
	            {  
	                  
	                ResolveList.add(str[i]);  
	                  
	            }  
	              
	            for (int i=0 ;i<ResolveList.size(); i++) 
	            {  
	                output.write(String.valueOf(ResolveList.get(i)) + "  \r\n"); 
	            }  
	            output.close();  
	        } 
	        catch (Exception ex) 
	        {  
	            System.out.println(ex);  
	        }          
    }  
}
