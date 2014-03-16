import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TrataCliente implements Runnable {
	
	private Socket Scliente;
	private int portaSaida;
	 
	public TrataCliente(Socket Scliente, int portaSaida) {
		this.Scliente = Scliente;
		this.portaSaida = portaSaida;
	}
	 
	public void run() {
	   
		Scanner s = null;
		try {
			s = new Scanner(this.Scliente.getInputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar Scanner! Socket.getInputStream()");
		}
		
		while (s.hasNextLine()) {

			// Salva ip do cliente que enviou a mensagem em var
			String remetente = Scliente.getInetAddress().getHostAddress(); 
			 
			// Varre linha para capturar ip de destino
			// ---
			boolean pParte = true;
			String ipDestino = "";
			String mensagem = "";
			 
			String sLinha = s.nextLine();  // "nextLine()" só pode ser usado uma vez
			 
			for(int i=0;i<(sLinha).length();i++){  
				char c = (sLinha).charAt(i);
			   
				if (c == '|') {
					pParte = false;
				}
				else if (pParte) {
					ipDestino = ipDestino + c;
				}
				else if (!pParte) {
					mensagem = mensagem + c;
				}
			} 
			// ---
			String msg = remetente + "|" + mensagem;
			
			MandaMsg(ipDestino, msg);
		 }
	}
	
	private void MandaMsg(String ipDestino, String msg) {
		
		Socket Sdestino = null;
		PrintStream PSdestino = null;
		 
		try {
			Sdestino = new Socket(ipDestino, this.portaSaida); 
			PSdestino = new PrintStream(Sdestino.getOutputStream());
			PSdestino.println(msg); //Envia a Mensagem com o ip do remetente
		} catch (IOException e) {
			System.out.println("Não foi possivel estabelecer conexão com destinatario "+ipDestino);
			System.out.println("Porta de servidor de saida "+this.portaSaida);
		}

		System.out.println(msg); //FIXME mensagem enviada
	}
}