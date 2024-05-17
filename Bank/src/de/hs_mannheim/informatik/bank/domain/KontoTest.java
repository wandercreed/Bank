package de.hs_mannheim.informatik.bank.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KontoTest {

	@Test
	void testKontoBasics() {
		Konto k = new Konto("Müller", 0);
		assertEquals("Müller", k.getInhaber());
		assertEquals(1000, k.getKontonummer());
		assertEquals(0, k.getKontostand());
	}

	@Test
	void testKontoEinUndAuszahlungUndSaldo() {
		Konto k = new Konto("Müller", 0);
		Konto k2 = new Konto("Mayer", 1);
		testKontoEinUndAuszahlungUndSaldo(k, k2);

		k = new Girokonto("Müller", 0);
		k2 = new Girokonto("Mayer", 1);
		testKontoEinUndAuszahlungUndSaldo(k, k2);
	}

	private void testKontoEinUndAuszahlungUndSaldo(Konto k, Konto k2) {
		assertEquals("Mayer", k2.getInhaber());
		assertNotEquals(k.getKontonummer(), k2.getKontonummer());

		k2.einzahlen(100, "Test", "Einzahlung", "JUnit");
		assertEquals(100, k2.getKontostand());

		assertTrue(k2.auszahlen(50, "Test", "Auszahlung", "JUnit"));
		assertEquals(50, k2.getKontostand());

		assertTrue(k2.auszahlen(50, "Test", "Auszahlung", "JUnit"));
		assertEquals(0, k2.getKontostand());

		k2.einzahlen(100, "Test", "Einzahlung", "JUnit");
		k2.einzahlen(100, "Test", "Einzahlung", "JUnit");
		k2.einzahlen(1, "Test", "Einzahlung", "JUnit");

		assertEquals(100, k2.berechneSaldo(1));
		assertEquals(100, k2.berechneSaldo(4));
		assertEquals(k2.getKontostand(), k2.berechneSaldo(6));
	}

	@Test
	void testKeineÜberziehungFürSparkonten() {
		Konto k = new Konto("Müller", 0);
		k.einzahlen(100, "Test", "Einzahlung", "JUnit");
		assertFalse(k.auszahlen(500, "Test", "Auszahlung", "JUnit"));

		k.auszahlen(50, "Test", "Auszahlung", "JUnit");
		assertFalse(k.auszahlen(100, "Test", "Auszahlung", "JUnit"));
	}

}
