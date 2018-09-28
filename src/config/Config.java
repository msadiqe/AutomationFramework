package com.sephora.ePlanner.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	
	public static String path="src/test/resources/config/";
	private static final Properties props=new Properties();
	
	public void loadProps(String fileName){
		try{
			FileInputStream input= new FileInputStream(path+fileName);
			props.load(input);
		}catch(FileNotFoundException file){
			file.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
public static void changes(){
}
	
	public String getProperty(String key){
		return props.getProperty(key);
	}

}
