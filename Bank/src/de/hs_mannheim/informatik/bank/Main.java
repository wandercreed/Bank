package de.hs_mannheim.informatik.bank;

import de.hs_mannheim.informatik.bank.facade.Banksystem;
import de.hs_mannheim.informatik.bank.ui.UI;

public class Main {

	public static void main(String[] args) throws Exception {
		Banksystem bs = new Banksystem("Spaßkasse Mannheim");
		UI ui = new UI(bs);
	}

}