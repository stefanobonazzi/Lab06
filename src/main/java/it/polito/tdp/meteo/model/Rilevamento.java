package it.polito.tdp.meteo.model;

import java.util.Date;

public class Rilevamento {
	
	private Citta citta;
	private Date data;
	private int umidita;

	public Rilevamento(Citta citta, Date data, int umidita) {
		this.citta = citta;
		this.data = data;
		this.umidita = umidita;
	}

	public Citta getLocalita() {
		return citta;
	}

	public void setLocalita(Citta localita) {
		this.citta = localita;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getUmidita() {
		return umidita;
	}

	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}

	@Override
	public String toString() {
	return citta + "\t" + data + "\t" + umidita;
	}

}
