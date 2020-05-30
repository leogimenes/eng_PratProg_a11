
package handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class ServidorSocket {

	private ServerSocket servidor;
	private ArrayList<ThreadCliente> usuario = new ArrayList<>();
	private JTextArea mensagens;
	private static ServidorSocket servidorSocket = null;
	
	private ServidorSocket() {
		
	}
	
	public synchronized static ServidorSocket getInstance() {
		if(servidorSocket == null) {
			servidorSocket = new ServidorSocket();
			return servidorSocket;
		}else {
			return servidorSocket;
		}
		
	}
	
	public void iniciar(String porta, JTextArea mensagens) throws IOException {
		this.mensagens = mensagens;
		servidor = new ServerSocket(Integer.parseInt(porta));
		String msg;


		while (true) {
			usuario.add(new ThreadCliente(servidor.accept(), usuario.size() + 1));
			msg = usuario.size() + " Entrou no chat" ;
			mensagens.append(msg + "\n");
			for(ThreadCliente cliente : usuario) {
				cliente.enviaMensagem(msg);
			}
			
		}

	}

	public void enviarMensagem(int remetente ,String mensagem) throws IOException {
		mensagem = remetente + ": " + mensagem;
		mensagens.append(mensagem + "\n");
		for(ThreadCliente cliente : usuario) {
			cliente.enviaMensagem(mensagem);
		}
	}

}
