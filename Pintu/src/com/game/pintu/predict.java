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
 * @author ��ũС��
 * H20121012.java
 * 2012-10-12����11:40:21
 */
public class predict 
{
    /**
     * ���ܣ�Java��ȡtxt�ļ�������
     * ���裺1���Ȼ���ļ����
     * 2������ļ��������������һ���ֽ���������Ҫ��������������ж�ȡ
     * 3����ȡ������������Ҫ��ȡ�����ֽ���
     * 4��һ��һ�е������readline()��
     * ��ע����Ҫ���ǵ����쳣���
     * @param filePath
     */
    public static void readTxtFile(String filePath,String str[])
    {
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists())
                { //�ж��ļ��Ƿ����
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//���ǵ������ʽ
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
            System.out.println("�Ҳ���ָ�����ļ�");
        }
        } catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
        }
     
    }
    
    public static void WriteDate(String filePath,String str[]) //����
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
    public static void FeatureWriteDate(String filePath,String str[]) //����
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
