package de.hs_mannheim.informatik.bank.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistenz {
	private static final String BANK_DATEI = "-bank-data.ser";

	public static boolean sindDatenGespeichert(String name) {
		return new File(name + BANK_DATEI).exists();
	}

	public static void speichereBankDaten(Object bank, String name) throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name + BANK_DATEI));
		oos.writeObject(bank);
		oos.close();
	}

	public static Object ladeBankDaten(String name) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name + BANK_DATEI));
		Object bank = ois.readObject();
		ois.close();

		return bank;
	}

}
