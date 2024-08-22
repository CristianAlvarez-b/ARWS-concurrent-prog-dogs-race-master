package edu.eci.arsw.primefinder;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		AtomicBoolean flag = new AtomicBoolean(false);
		PrimeFinderThread thread1=new PrimeFinderThread(0, 999999999, flag);
		PrimeFinderThread thread2 = new PrimeFinderThread(1000000000, 1999999999, flag);
		PrimeFinderThread thread3 = new PrimeFinderThread(2000000000, new Long("3000000000"), flag);
		thread1.start();
		thread2.start();
		thread3.start();

		while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive()) {
			// Pausa todos los hilos
			Thread.sleep(5000);
			flag.set(true);
			scanner.nextLine();

			// Reanuda todos los hilos
			synchronized (flag) {
				flag.set(false);
				flag.notifyAll();
			}
		}

		scanner.close();
	}
}


