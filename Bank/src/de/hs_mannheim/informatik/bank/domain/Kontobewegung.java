package de.hs_mannheim.informatik.bank.domain;

import java.io.Serializable;
import java.util.Date;

public class Kontobewegung implements Serializable {
	private long betrag;
	private Date datum;
	private String betreff;
	private String art;
	private String auftraggeber;

	public Kontobewegung(long betrag, String betreff, String art, String auftraggeber) {
		this.betrag = betrag;
		this.betreff = betreff;
		this.art = art;
		this.auftraggeber = auftraggeber;

		this.datum = new Date();
	}

	public long getBetrag() {
		return betrag;
	}

	@Override
	public String toString() {
		return "Kontobewegung [betrag=" + betrag + ", datum=" + datum + ", betreff=" + betreff + ", art=" + art
				+ ", auftraggeber=" + auftraggeber + "]";
	}

}
