package de.hs_mannheim.informatik.bank.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class Bank implements Serializable {
	private String name;
	private HashMap<Integer, Konto> konten = new HashMap<>();
	private int kontozähler;

	public Bank(String name) {
		this.name = name;
		this.kontozähler = -1;
	}

	public int addKonto(String name, int auswahl) {
		Konto k;

		if (auswahl == 1)
			k = new Konto(name, ++kontozähler);
		else
			k = new Girokonto(name, ++kontozähler);

		konten.put(k.getKontonummer(), k);

		return k.getKontonummer();
	}

	public String getName() {
		return name;
	}

	public Collection<Konto> getKontenliste() {
		return konten.values();
	}

	public Konto findeKonto(int kontonummer) {
		return konten.get(kontonummer);
	}

}
