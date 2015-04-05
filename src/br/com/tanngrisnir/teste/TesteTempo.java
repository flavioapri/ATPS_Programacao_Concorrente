package br.com.tanngrisnir.teste;

public class TesteTempo {

	public static void main(String[] args) throws InterruptedException {

		long a = System.currentTimeMillis();
		int i = 0;
		while ((System.currentTimeMillis() - a) < 60000) {
			System.out.println("Segundos: " + i);
			
			Thread.sleep(1000);
			
			if ((System.currentTimeMillis() - a) > 60000) {
				System.out.println("Fim!");
			}
			i++;
		}
		
	}

}
