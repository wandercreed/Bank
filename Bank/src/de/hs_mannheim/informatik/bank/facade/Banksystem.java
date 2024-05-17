package de.hs_mannheim.informatik.bank.facade;

import java.util.Collection;

import de.hs_mannheim.informatik.bank.domain.Bank;
import de.hs_mannheim.informatik.bank.domain.Girokonto;
import de.hs_mannheim.informatik.bank.domain.Konto;
import de.hs_mannheim.informatik.bank.infrastructure.Persistenz;

public class Banksystem {
	private Bank bank;

	public Banksystem(String bankname) throws Exception {
		if (Persistenz.sindDatenGespeichert(bankname))
			this.bank = (Bank) Persistenz.ladeBankDaten(bankname);
		else
			this.bank = new Bank(bankname);
	}

	public int kontoAnlegen(String name, int auswahl) throws Exception {
		int kontonummer = bank.addKonto(name, auswahl);

		Persistenz.speichereBankDaten(this.bank, bank.getName());

		return kontonummer;
	}

	public String[] getKontenliste() {
		Collection<Konto> konten = bank.getKontenliste();
		String[] liste = new String[konten.size()];

		int i = 0;
		for (Konto k : konten) {
			liste[i++] = k.toString();
		}

		return liste;
	}

	public String getBankname() {
		return bank.getName();
	}

	public long geldEinzahlen(int kontonummer, long betrag) throws Exception {
		Konto konto = bank.findeKonto(kontonummer);
		konto.einzahlen(betrag, "Einzahlung am Schalter", "Einzahlung", konto.getInhaber());

		Persistenz.speichereBankDaten(this.bank, bank.getName());

		return konto.getKontostand();
	}

	public boolean geldAuszahlen(int kontonummer, long betrag) throws Exception {
		Konto konto = bank.findeKonto(kontonummer);

		boolean erg = konto.auszahlen(betrag, "Auszahlung am Schalter", "Auszahlung", konto.getInhaber());

		Persistenz.speichereBankDaten(this.bank, bank.getName());

		return erg;
	}

	public String[] erstelleKontoauszug(int kontonummer) {
		Konto konto = bank.findeKonto(kontonummer);

		return konto.getKontobewegungen();
	}

	public boolean überweisungBeauftragen(int startkonto, int zielkonto, long betrag, String verwendungszweck)
			throws Exception {
		Konto start = bank.findeKonto(startkonto);
		Konto ziel = bank.findeKonto(zielkonto);

		if (start instanceof Girokonto && ziel instanceof Girokonto) {
			boolean erfolg = ((Girokonto) start).überweise((Girokonto) ziel, betrag, verwendungszweck);
			Persistenz.speichereBankDaten(this.bank, bank.getName());

			return erfolg;
		}

		return false;
	}

	public long getKontostand(int kontonummer) {
		Konto konto = bank.findeKonto(kontonummer);

		return konto.getKontostand();
	}

	public long saldoBestimmen(int kontonummer, int anzahl) {
		Konto konto = bank.findeKonto(kontonummer);

		return konto.berechneSaldo(anzahl);
	}

}
