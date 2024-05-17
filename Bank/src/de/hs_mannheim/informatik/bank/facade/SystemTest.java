package de.hs_mannheim.informatik.bank.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class SystemTest {
	private static Banksystem bs;

	@BeforeAll
	static void initBanksystem() throws Exception {
		bs = new Banksystem("Testsystem");
	}

	@Test
	@Order(1)
	void smokeTest() {
		assertNotNull(bs);
		assertEquals(0, bs.getKontenliste().length);
		assertEquals("Testsystem", bs.getBankname());
	}

	@Test
	@Order(2)
	void einzahlenTest() throws Exception {
		int knr = bs.kontoAnlegen("Test1", 1);

		assertEquals(1000, bs.geldEinzahlen(knr, 1000));

		bs.geldEinzahlen(knr, 1);
		assertEquals(1001, bs.getKontostand(knr));

		assertEquals(1001, bs.geldEinzahlen(knr, 0));
	}

	@Test
	@Order(3)
	void persistenzTest() throws Exception {
		int knr = bs.kontoAnlegen("Test2", 2);
		int knr2 = bs.kontoAnlegen("Test3", 2);

		bs.geldEinzahlen(knr, 1000);
		bs.geldAuszahlen(knr, 500);
		assertTrue(bs.überweisungBeauftragen(knr, knr2, 100, "Überweisungstest."));

		assertEquals(400, bs.getKontostand(knr));

		bs = null;

		Banksystem bs2 = new Banksystem("Testsystem");
		assertEquals(400, bs2.getKontostand(knr));
	}

	@AfterAll
	static void cleanup() {
		File file = new File("/Users/oliver/git/Bank-System/Bank-Beispiel/Testsystem-bank-data.ser");
		file.delete();
	}

}
