package de.hs_mannheim.informatik.bank.ui;

import java.util.Scanner;

import de.hs_mannheim.informatik.bank.facade.Banksystem;

public class UI {
	private Banksystem bs;
	Scanner sc = new Scanner(System.in);

	public UI(Banksystem bs) {
		this.bs = bs;
		hauptmenü();
	}

	private void hauptmenü() {
		System.out.println("Willkommen bei der " + bs.getBankname() + "!");

		mainloop: while (true) {
			System.out.println();
			System.out.println("--------");
			System.out.println("Hauptmenü");
			System.out.println("1 -> Konten anzeigen");
			System.out.println("2 -> Konto anlegen");
			System.out.println("3 -> Geld einzahlen");
			System.out.println("4 -> Geld auszahlen");
			System.out.println("5 -> Kontoauszug drucken");
			System.out.println("6 -> Überweisung beauftragen");
			System.out.println("7 -> Saldo abfragen");

			System.out.println("9 -> Beenden");
			System.out.println();

			System.out.print("> ");
			int input = Integer.parseInt(sc.nextLine());
			System.out.println();

			try {
				switch (input) {
				case 1:
					kontenAnzeigen();
					break;
				case 2:
					kontoAnlegen();
					break;
				case 3:
					geldEinzahlen();
					break;
				case 4:
					geldAuszahlen();
					break;
				case 5:
					kontoauszugDrucken();
					break;
				case 6:
					überweisungBeauftragen();
					break;
				case 7:
					saldoAbfragen();
					break;
				case 9:
					break mainloop;
				}

			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
			System.out.println();
		}

		System.out.println("Auf Wiedersehen!");

	} // hauptmenü

	private void kontenAnzeigen() {
		String[] konten = bs.getKontenliste();
		if (konten.length > 0) {
			System.out.println("Folgende Konten sind aktuell verfügbar:");
			for (String s : konten) {
				System.out.println(s);
			}
		} else {
			System.out.println("Bisher keine Konten angelegt.");
		}
	}

	private void kontoAnlegen() throws Exception {
		System.out.println("Bitte den Namen des Kontoinhabers angeben: ");
		String name = sc.nextLine();

		System.out.println("Möchten Sie ein Sparkonto (1) oder ein Girokonto (2) anlegen?");
		int auswahl = Integer.parseInt(sc.nextLine());

		int kontonummer = bs.kontoAnlegen(name, auswahl);
		System.out.println("Konto mit der Nummer " + kontonummer + " neu angelegt.");
	}

	private void geldEinzahlen() throws Exception {
		System.out.println("Geld einzahlen");
		System.out.print("Bitte die gewünschte Kontonummer eingeben: ");
		int kontonummer = Integer.parseInt(sc.nextLine());

		// optional prüfen, ob Konto existiert

		System.out.print("Bitte den gewünschten Betrag eingeben: ");
		double betrag = Double.parseDouble(sc.nextLine());

		long neuerKontostand = bs.geldEinzahlen(kontonummer, (long) betrag * 100);

		System.out.printf("Einzahlung erfolgreich, neuer Kontostand = %.2f Euro", (neuerKontostand / 100.0));
	}

	private void geldAuszahlen() throws Exception {
		System.out.println("Geld auszahlen");
		System.out.print("Bitte die gewünschte Kontonummer eingeben: ");
		int kontonummer = Integer.parseInt(sc.nextLine());

		System.out.print("Bitte den gewünschten Betrag eingeben: ");
		double betrag = Double.parseDouble(sc.nextLine());

		boolean erfolgreich = bs.geldAuszahlen(kontonummer, (long) betrag * 100);

		System.out.printf("Auszahlung" + ((!erfolgreich) ? " nicht" : "") + " erfolgreich. ");
		System.out.printf("Neuer Kontostand = %.2f Euro.", (bs.getKontostand(kontonummer) / 100.0));
	}

	private void kontoauszugDrucken() {
		System.out.print("Bitte die gewünschte Kontonummer für den Auszug eingeben: ");
		int kontonummer = Integer.parseInt(sc.nextLine());

		// in echt auf einem Drucker
		System.out.println("Auszug für Konto " + kontonummer);
		String[] kontobewegungen = bs.erstelleKontoauszug(kontonummer);

		if (kontobewegungen.length > 0)
			for (String kb : kontobewegungen) {
				System.out.println(kb);
			}
		else
			System.out.println("Noch keine Kontobewegungen.");
	}

	private void überweisungBeauftragen() throws Exception {
		System.out.print("Bitte die Kontonummer des Ausgangskontos der Überweisung eingeben: ");
		int startkonto = Integer.parseInt(sc.nextLine());

		System.out.print("Bitte die Kontonummmer für das Zielkonto der Überweisung eingeben: ");
		int zielkonto = Integer.parseInt(sc.nextLine());

		System.out.print("Bitte den gewünschten Überweisungsbetrag eingeben: ");
		double betrag = Double.parseDouble(sc.nextLine());

		System.out.print("Bitte den Verwendungszweck eingeben: ");
		String verwendungszweck = sc.nextLine();

		boolean erfolgreich = bs.überweisungBeauftragen(startkonto, zielkonto, (long) (betrag * 100),
				verwendungszweck);

		System.out.println("Überweisung" + ((!erfolgreich) ? " nicht" : "") + " erfolgreich ausgeführt.");
	}

	private void saldoAbfragen() {
		System.out.print("Bitte die Kontonummer des gewünschten Kontos eingeben: ");
		int konto = Integer.parseInt(sc.nextLine());

		System.out.print("Bitte die Anzahl der Kontobewegungen für den Saldo eingeben: ");
		int anzahl = Integer.parseInt(sc.nextLine());

		long saldo = bs.saldoBestimmen(konto, anzahl);
		System.out.printf("Der Saldo nach %d Kontobewegungen beträgt %.2f Euro.%n", anzahl, (saldo / 100d));
	}

}
