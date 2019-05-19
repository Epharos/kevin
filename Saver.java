package fr.epharos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Saver 
{
	private File file;
	private ArrayList<String> toSave;
	private ArrayList<KeyValue> loaded;
	
	public Saver(String name)
	{
		file = new File(name);
		toSave = new ArrayList<String>();
		loaded = new ArrayList<KeyValue>();
	}
	
	public void addToSaver(String key, Object value)
	{
		toSave.add(key + ":" + String.valueOf(value));
	}
	
	public String getLoaded(String key)
	{
		for(KeyValue kv : loaded)
		{
			if(key.equals(kv.getKey()))
			{
				return kv.getValue();
			}
		}
		
		System.err.println("Couldn't load the value for key " + key);
		return null;
	}
	
	public void clear()
	{
		toSave.clear();
		loaded.clear();
	}
	
	public void save()
	{
		System.out.println("Saving ...");
		
		String toWrite = "";
		
		for(String s : toSave)
			toWrite += s + "\n";
		
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) 
		{
		    bufferedWriter.write(toWrite);
		} 
		catch (IOException e) 
		{
		    
		}
		
		System.out.println("Saving done !");
	}
	
	public void load()
	{
		System.out.println("Loading ...");
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) 
		{  
		    String line = bufferedReader.readLine();
		    
		    while(line != null)
		    {
		        loaded.add(new KeyValue(line.split(":")[0], line.split(":")[1]));
		        line = bufferedReader.readLine();
		    }
		} 
		catch (FileNotFoundException e) 
		{
			
		} 
		catch (IOException e) 
		{
			
		}
		
		System.out.println("Loading done !");
	}
	
	private class KeyValue
	{
		private String key, value;
		
		public KeyValue(String s, String t)
		{
			key = s;
			value = t;
		}
		
		public String getKey()
		{
			return key;
		}
		
		public String getValue()
		{
			return value;
		}
	}
}
