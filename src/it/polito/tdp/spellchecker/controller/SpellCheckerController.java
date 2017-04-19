/**
 * Sample Skeleton for 'SpellChecker.fxml' Controller Class
 */

package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {

	private Dictionary dizionario;
	
	List<String> listaDaCorreggere = new ArrayList<String>();
	List<RichWord> listaCorretta = new ArrayList<RichWord>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbLingua"
    private ComboBox<String> cmbLingua; // Value injected by FXMLLoader

    @FXML // fx:id="txtDaCorreggere"
    private TextArea txtDaCorreggere; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpellCheck"
    private Button btnSpellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorretto"
    private TextArea txtCorretto; // Value injected by FXMLLoader

    @FXML // fx:id="lblErrori"
    private Label lblErrori; // Value injected by FXMLLoader

    @FXML // fx:id="btnClearText"
    private Button btnClearText; // Value injected by FXMLLoader

    @FXML // fx:id="lblTempo"
    private Label lblTempo; // Value injected by FXMLLoader

    @FXML
    void doClearText(ActionEvent event) {
    	
    	listaDaCorreggere.clear();
    	listaCorretta.clear();
    	
    	txtDaCorreggere.clear();
  
    	txtCorretto.clear();
    	lblErrori.setText("");
    	lblTempo.setText("");

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	listaDaCorreggere.clear();
    	listaCorretta.clear();
  
    	lblErrori.setVisible(true);
		lblTempo.setVisible(true);
		
		dizionario.loadDictionary(cmbLingua.getValue());
		
		String testoDaCorreggere = txtDaCorreggere.getText();
		if (testoDaCorreggere.isEmpty()) {
			txtCorretto.setText("Inserire testo da correggere");
			return;
		}

		StringTokenizer st = new StringTokenizer(testoDaCorreggere, " ");		
		while (st.hasMoreTokens()) {
			listaDaCorreggere.add(st.nextToken().replaceAll("[ \\p{Punct}]", "").trim().toLowerCase());
		
		}
		
		long l1 = System.nanoTime();
		listaCorretta = dizionario.spellCheckText(listaDaCorreggere);
		long l2 = System.nanoTime();
		
		int errori = 0;
		String paroleSbagliate = "";
		
		for(RichWord temp : listaCorretta){
			if(!temp.isCorretta()){
				errori++;
				paroleSbagliate += temp.getParola()+"\n";
			}
		}
		
		txtCorretto.setText(paroleSbagliate);
		lblErrori.setText("The text contains " + errori + " errors");
		lblTempo.setText("Spell check completed in " + (l2 - l1) / 1e9 + " seconds");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbLingua != null : "fx:id=\"cmbLingua\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblTempo != null : "fx:id=\"lblTempo\" was not injected: check your FXML file 'SpellChecker.fxml'.";

        cmbLingua.getItems().addAll("English" , "Italian");
        cmbLingua.setValue(cmbLingua.getItems().get(0));
        
    }

	public void setModel(Dictionary dictionary) {
		dizionario = dictionary;
	}
}
