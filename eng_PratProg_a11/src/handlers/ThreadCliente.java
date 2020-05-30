package handlers;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ThreadCliente extends Thread {

	private Socket usuario;
	private int indice;

	public ThreadCliente(Socket usuario, int indice) {
		this.usuario = usuario;	
		this.indice = indice;
		start();

	}

	@SuppressWarnings("resource")
	@Override
	public void run(){
		Scanner scanner;
		String mensagem;
		ServidorSocket ss = ServidorSocket.getInstance();
		try {
			scanner = new Scanner(usuario.getInputStream());
		} catch (IOException e) {
			return;
		}

		while (true) {

			if(scanner.hasNextLine()) {
				mensagem = scanner.nextLine();
				try {					
					ss.enviarMensagem(indice, mensagem);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

		}
	}

	public void enviaMensagem(String mensagem) {
			try {
				PrintStream saida = new PrintStream(usuario.getOutputStream());
				saida.println(mensagem);
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}

}
