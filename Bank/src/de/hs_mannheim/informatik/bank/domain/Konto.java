package de.hs_mannheim.informatik.bank.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Konto implements Serializable {
	private int nummer;
	protected long stand = 0;
	private String inhaber;

	protected ArrayList<Kontobewegung> kontobewegungen;

	public Konto(String inhaber, int kontozähler) {
		nummer = 1000 + kontozähler;
		this.inhaber = inhaber;

		this.kontobewegungen = new ArrayList<>();
	}

	public int getKontonummer() {
		return nummer;
	}

	@Override
	public String toString() {
		return "Konto [nummer=" + nummer + ", inhaber=" + inhaber + "]";
	}

	public String getInhaber() {
		return inhaber;
	}

	public long getKontostand() {
		return stand;
	}

	public void einzahlen(long betrag, String zweck, String art, String auftraggeber) {
		stand += betrag;

		kontobewegungen.add(new Kontobewegung(betrag, zweck, art, auftraggeber));
	}

	public boolean auszahlen(long betrag, String zweck, String art, String auftraggeber) {
		if (stand - betrag >= 0) {
			stand -= betrag;

			kontobewegungen.add(new Kontobewegung(betrag * -1, zweck, art, auftraggeber));

			return true;
		}

		return false;
	}

	public String[] getKontobewegungen() {
		String[] auflistung = new String[kontobewegungen.size()];

		int i = 0;
		for (Kontobewegung kb : kontobewegungen) {
			auflistung[i++] = kb.toString();
		}

		return auflistung;
	}

	public long berechneSaldo(int anzahl) {
		long saldo = 0;

		for (int i = 0; i < anzahl; i++) {
			saldo += kontobewegungen.get(i).getBetrag();
		}

		return saldo;
	}

}
