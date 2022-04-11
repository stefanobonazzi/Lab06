package it.polito.tdp.meteo.model;

import java.util.*;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	MeteoDAO dao = new MeteoDAO();
	
	public Model() {

	}

	public Map<String, Double> getUmiditaMedia(int mese) {
		List<String> cities = dao.getAllCities();
		Map<String, Double> avgUmidity = new TreeMap<>();
		
		for(String s: cities) 
			avgUmidity.put(s, dao.getAvgRilevamentiLocalitaMese(mese, s));
		
		return avgUmidity;
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		return "TODO!";
	}
	
}
