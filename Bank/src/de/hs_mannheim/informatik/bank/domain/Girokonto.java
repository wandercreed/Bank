package de.hs_mannheim.informatik.bank.domain;

import java.io.Serializable;

public class Girokonto extends Konto implements Serializable {
	private long dispo = 100000;

	public Girokonto(String inhaber, int kontozähler) {
		super(inhaber, kontozähler);
	}

	public boolean überweise(Girokonto ziel, long betrag, String zweck) {
		if (super.getKontostand() - betrag >= dispo * (-1)) {
			this.auszahlen(betrag, zweck, "Überweisungsausgang", super.getInhaber());
			ziel.einzahlen(betrag, zweck, "Überweisungseingang", super.getInhaber());

			return true;
		}

		return false;
	}

	@Override
	public boolean auszahlen(long betrag, String zweck, String art, String auftraggeber) {
		if (stand - betrag >= dispo * (-1)) {
			stand -= betrag;

			kontobewegungen.add(new Kontobewegung(betrag * -1, zweck, art, auftraggeber));

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Giro-" + super.toString();
	}

}
