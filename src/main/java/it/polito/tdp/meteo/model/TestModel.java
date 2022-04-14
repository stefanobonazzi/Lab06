package it.polito.tdp.meteo.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		List<Rilevamento> l = m.trovaSequenza(4);
		
		for(Rilevamento r: l)
			System.out.println(r);
	}

}
