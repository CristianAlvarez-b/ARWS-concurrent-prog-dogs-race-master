package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeFinderThread extends Thread{

	
	long a,b;
	AtomicBoolean flag;
	private List<Long> primes=new LinkedList<Long>();
	
	public PrimeFinderThread(long a, long b, AtomicBoolean flag) {
		super();
		this.a = a;
		this.b = b;
		this.flag = flag;

	}

	public void run(){
		try{
			finder();
		}catch (InterruptedException ex){
			ex.printStackTrace();
		}

	}
	private void finder() throws InterruptedException{
		for (long i=a;i<=b;i++){
			synchronized (flag){
				while (flag.get()){
					flag.wait();
				}
			}
			if (isPrime(i)){
				primes.add(i);
				System.out.println(i);
			}
		}
	}
	boolean isPrime(long n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Long> getPrimes() {
		return primes;
	}
	
	
	
	
}
