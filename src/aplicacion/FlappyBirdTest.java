package aplicacion;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;

public class FlappyBirdTest implements Serializable {

	public FlappyBirdTest() {
	}

	@Test
	public void deberiaColicionarSuelo() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		FlappyBird app = new FlappyBird(elementos);
		for (int i = 0; i < 130; i++) {
			try {
				app.next();
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertFalse(app.getJugadores().get(0).isVivo());
	}

	@Test
	public void deberiaMaquinaAstuto() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		elementos[1] = true;
		FlappyBird app = new FlappyBird(elementos, "Astuto");
		for (int i = 0; i < 2000; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertTrue(app.getJugadores().get(1).isVivo());
	}

	@Test
	public void deberiaMaquinaEquilibrado() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		elementos[1] = true;
		FlappyBird app = new FlappyBird(elementos, "Equilibrado");
		for (int i = 0; i < 200; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertTrue(app.getJugadores().get(1).getX() < 500);
	}

	@Test
	public void deberiaMaquinaMimo() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		elementos[1] = true;
		FlappyBird app = new FlappyBird(elementos, "Mimo");
		app.getJugadores().get(0).setPosicionX(app.getJugadores().get(1).getX());
		int dx = 60;
		for (int i = 0; i < 400; i++) {
			try {
				app.next();
				if (i % 20 == 0)
					app.subir(0);
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
				assertEquals(app.getJugadores().get(0).getX(), app.getJugadores().get(1).getX(), dx);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
	}

	@Test
	public void deberiaMaquinaTimido() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		elementos[1] = true;
		FlappyBird app = new FlappyBird(elementos, "Timido");
		for (int i = 0; i < 250; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertEquals(90, app.getJugadores().get(1).getVida());
	}

	@Test
	public void deberiaSubir() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		FlappyBird app = new FlappyBird(elementos);
		for (int i = 0; i < 100; i++) {
			try {
				app.next();
				if (i % 20 == 0)
					app.subir(0);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertEquals(49, app.getJugadores().get(0).getX(), 5);
	}

	@Test
	public void deberiaTuboRojo() {
		boolean[] elementos = new boolean[12];
		elementos[3] = true;
		FlappyBird app = new FlappyBird(elementos);
		try {
			app.next();
		} catch (FlappyException e1) {
			fail("Excepcion");
		}
		int aperturaInicial = app.getElementos().get(0).getEspacio();
		boolean ans = false;
		int dx = 3;
		for (int i = 0; i < 300; i++) {
			try {
				app.next();
				if (i % 20 == 0)
					app.subir(0);
				if (Math.abs(aperturaInicial - app.getElementos().get(0).getEspacio()) < dx)
					ans = true;
			} catch (FlappyException e) {
				fail("Excepcion");
			}
			assertTrue(ans);
		}

	}

	@Test
	public void deberiaTuboAzul() {
		boolean[] elementos = new boolean[12];
		elementos[4] = true;
		FlappyBird app = new FlappyBird(elementos);
		try {
			app.next();
		} catch (FlappyException e1) {
			fail("Excepcion");
		}
		int alturaInicial = app.getElementos().get(0).getAltura();
		for (int i = 0; i < 100; i++) {
			try {
				app.next();
				if (i % 20 == 0)
					app.subir(0);
				assertTrue(alturaInicial < app.getElementos().get(0).getAltura());
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
	}

	@Test
	public void deberiaTuboMagenta() {
		boolean[] elementos = new boolean[12];
		elementos[5] = true;
		FlappyBird app = new FlappyBird(elementos);
		try {
			app.next();
		} catch (FlappyException e1) {
			fail("Excepcion");
		}
		int alturaInicial = app.getElementos().get(0).getAltura();
		for (int i = 0; i < 100; i++) {
			try {
				app.next();
				if (i % 20 == 0)
					app.subir(0);
				assertTrue(alturaInicial > app.getElementos().get(0).getAltura());
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
	}

	@Test
	public void deberiaEstadoOro() {
		boolean[] elementos = new boolean[12];
		elementos[1] = true;
		elementos[2] = true;
		FlappyBird app = new FlappyBird(elementos, "Timido");
		app.getJugadores().get(1).setEstado(new EstadoOro(app.getJugadores().get(1), 1));
		for (int i = 0; i < 250; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertEquals(100, app.getJugadores().get(1).getVida());
		assertEquals("EstadoJugador", app.getJugadores().get(1).getEstado().getClass().getSimpleName());
	}

	@Test
	public void deberiaEstadoPlata() {
		boolean[] elementos = new boolean[12];
		elementos[1] = true;
		elementos[10] = true;
		FlappyBird app = new FlappyBird(elementos, "Timido");
		app.getJugadores().get(1).setEstado(new EstadoPlata(app.getJugadores().get(1)));
		DragonVerde d = new DragonVerde();
		d.setAltura(800);
		app.adicioneElemento(d);
		assertEquals("EstadoPlata", app.getJugadores().get(1).getEstado().getClass().getSimpleName());
		for (int i = 0; i < 250; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertEquals(100, app.getJugadores().get(1).getVida());
		assertEquals("EstadoJugador", app.getJugadores().get(1).getEstado().getClass().getSimpleName());
	}
	
	@Test
	public void deberiaMonedaAlgodon() {
		boolean[] elementos = new boolean[12];
		elementos[2] = true;
		elementos[1] = true;
		FlappyBird app = new FlappyBird(elementos, "Timido");
		app.getJugadores().get(1).setVida(60);
		app.getJugadores().get(1).setEstado(new EstadoPlata(app.getJugadores().get(1)));
		MonedaAlgodon m = new MonedaAlgodon();
		m.setAltura(800);
		m.setPosicionY(FlappyBird.ANCHO-500);
		app.adicioneElemento(m);
		assertEquals("EstadoPlata", app.getJugadores().get(1).getEstado().getClass().getSimpleName());
		for (int i = 0; i < 150; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertEquals(100, app.getJugadores().get(1).getVida());
		assertEquals("EstadoJugador", app.getJugadores().get(1).getEstado().getClass().getSimpleName());
	}
	
	@Test
	public void deberiaDragonVerde() {
		boolean[] elementos = new boolean[12];
		elementos[1] = true;
		elementos[10] = true;
		FlappyBird app = new FlappyBird(elementos, "Timido");
		DragonVerde d = new DragonVerde();
		d.setAltura(800);
		app.adicioneElemento(d);
		for (int i = 0; i < 250; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertFalse(app.getJugadores().get(1).isVivo());
	}
	
	@Test
	public void deberiaDragonRojo() {
		boolean[] elementos = new boolean[12];
		elementos[1] = true;
		elementos[9] = true;
		elementos[2] = true;
		FlappyBird app = new FlappyBird(elementos, "Timido");
		DragonRojo d = new DragonRojo();
		d.setApp(app);
		app.adicioneElemento(d);
		for (int i = 0; i < 250; i++) {
			try {
				app.next();
				if (((Maquina) app.getJugadores().get(1)).isSaltarMaquina())
					app.subir(1);
			} catch (FlappyException e) {
				fail("Excepcion");
			}
		}
		assertFalse(app.getJugadores().get(1).isVivo());
	}
}
