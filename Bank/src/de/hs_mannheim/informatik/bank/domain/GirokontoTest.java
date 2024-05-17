package de.hs_mannheim.informatik.bank.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GirokontoTest {

	@Test
	void testÜberziehung() {
		Konto k = new Girokonto("Müller", 0);
		k.einzahlen(10000, "Test", "Einzahlung", "JUnit");

		assertTrue(k.auszahlen(20000, "Test", "Einzahlung", "JUnit"));
		assertEquals(-10000, k.getKontostand());

		assertTrue(k.auszahlen(40000, "Test", "Einzahlung", "JUnit"));
		assertTrue(k.auszahlen(50000, "Test", "Einzahlung", "JUnit"));
		assertEquals(-100000, k.getKontostand());

		assertFalse(k.auszahlen(40000, "Test", "Einzahlung", "JUnit"));
		assertFalse(k.auszahlen(1, "Test", "Einzahlung", "JUnit"));
	}

}
