package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario;
	private List<RichWord> paroleControllate;
	
	final static boolean dicotomica = false;
	
	public Dictionary() {
		dizionario = new ArrayList<String>();
		paroleControllate = new ArrayList<RichWord>();
	}

	public void loadDictionary(String language) {
		try {
			FileReader fr = new FileReader("rsc/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				// Aggiungere parola alla struttura dati
				dizionario.add(word.toLowerCase());
			}
			br.close();
		} catch (IOException e){
			System.out.println("Errore nella lettura del file");
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){

		for(String temp : inputTextList) {
			RichWord r = new RichWord(temp.toLowerCase());
			paroleControllate.add(r);
		
			if (dicotomica) {
				if (binarySearch(temp)) 
					r.setCorretta(true);
				else 
					r.setCorretta(false);
			}
			else {
				if(dizionario.contains(temp))
					r.setCorretta(true);
				else
					r.setCorretta(false);
			}
		}
		return paroleControllate;
	}
	
	private boolean binarySearch(String text) {
	
		int inizio = 0;
	    int fine = dizionario.size();

	    while (inizio!=fine){
	    	int medio = inizio + (fine - inizio)/2;
	    	
	    	if (text.compareToIgnoreCase(dizionario.get(medio))==0)
	    		return true;
	    	else if (text.compareToIgnoreCase(dizionario.get(medio))>0)
	    		inizio=medio +1;
	    	else
	    		fine=medio;
	    }
	    
	    return false;
	}
}
