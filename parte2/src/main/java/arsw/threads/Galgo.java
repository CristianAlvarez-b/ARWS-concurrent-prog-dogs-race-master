package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	static RegistroLlegada regl;
	private static boolean paused = false;

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl = reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
			Thread.sleep(100);

			synchronized (regl) {
				while (paused) {
					regl.wait();
				}
			}

			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			if (paso == carril.size()) {
				carril.finish();

				// Región crítica
				synchronized (regl) {
					int ubicacion = regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion + 1);
					System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
					if (ubicacion == 1) {
						regl.setGanador(this.getName());
					}
				}
			}
		}
	}

	public static void pauseRace() {
		synchronized (regl) {
			paused = true;
		}
	}

	public static void resumeRace() {
		synchronized (regl) {
			paused = false;
			regl.notifyAll();
		}
	}

	@Override
	public void run() {

		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}