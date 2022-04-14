package it.polito.tdp.meteo.model;

import java.util.*;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	//Da implementare in un modo più efficiente i controlli nella ricorsione
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private int totalCost;
	private List<Citta> cities;
	private List<Rilevamento> best;
	private MeteoDAO dao = new MeteoDAO();
	
	public Model() {
		this.cities = dao.getAllCities();
		this.totalCost = 1000000000;
		this.best = new ArrayList<Rilevamento>();
	}

	public Map<String, Double> getUmiditaMedia(int mese) {
		Map<String, Double> avgUmidity = new TreeMap<>();
		
		for(Citta c: cities) 
			avgUmidity.put(c.getNome(), dao.getAvgRilevamentiLocalitaMese(mese, c.getNome()));
				
		return avgUmidity;
	}
	
	public List<Rilevamento> trovaSequenza(int mese) {
		List<Rilevamento> parziale = new ArrayList<Rilevamento>();
		
		for(Citta c: cities)
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
			
		this.trovaSequenzaRicorsiva(parziale, 0);
		
		return best;
	}
	
	public void trovaSequenzaRicorsiva(List<Rilevamento> parziale, int data) {
		//Caso finale
		if(data == Model.NUMERO_GIORNI_TOTALI) {
			//Controllo giorni minimi consecutivi per città
			int g = 0;
			for(int i=0; i<parziale.size(); i++) {
				for(int j=0; j<=Model.NUMERO_GIORNI_CITTA_MAX; j++) {
					if(i+j >= parziale.size()) {
						if(g<Model.NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN)
							return;
						
						i = i+j;
						break;
					}
					
					if(parziale.get(i).getLocalita().equals(parziale.get(i+j).getLocalita())) 
						g++;
					
					if(!parziale.get(i).getLocalita().equals(parziale.get(i+j).getLocalita()) && 
						g < Model.NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) 
						return;
					
					if(!parziale.get(i).getLocalita().equals(parziale.get(i+j).getLocalita())) {
						i = i+j-1;
						g = 0;
						break;
					}
				}
			}
			
			//Controllo che siano presenti tutte le città
			for(Citta c: cities) {
				for(Rilevamento r: parziale)
					if(r.getLocalita().equals(c))
						c.increaseCounter();
				
				if(c.getCounter() == 0) {
					return;
				}
				
				c.setCounter(0);
			}
			
			//Controllo del costo minore della soluzione
			int costo = 0;
			for(int i=0; i<parziale.size(); i++) {
				costo = costo + parziale.get(i).getUmidita();
				
				if(i != 0 && !parziale.get(i).getLocalita().equals((parziale.get(i-1).getLocalita())))
					costo = costo + Model.COST;
			}
			
			//Salvo la soluzione se rispetta il costo
			if(costo < this.totalCost) {
				best = new ArrayList<>(parziale);
				this.totalCost = costo;
			}
		
			return;
		} 
		
		//Ricorsione se non si è al caso finale
		for(Citta c: cities) {
			parziale.add(c.getRilevamenti().get(data));
	
			//Controllo che non vada oltre il numero massimo di giorni in ogni città
			int gg = 0;
			for(Rilevamento r: parziale)
				if(r.getLocalita().equals(c))
					gg++;
			
			//Se rispetta il controllo faccio la ricorsione
			if(gg <= Model.NUMERO_GIORNI_CITTA_MAX) {
				this.trovaSequenzaRicorsiva(parziale, data+1);
			}

			//Backtracking
			parziale.remove(c.getRilevamenti().get(data));
		}
		
		return;
	}
	
}
