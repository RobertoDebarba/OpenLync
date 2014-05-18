
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/*
 * Esta thread está ligada à Thread TrataCliente no servidor.
 * Cada vez que esta é criada, uma nova trata cliente surge.
 * 
 * Quando esta for finalizada, a trata cliente também deve ser.
 */

public class SaidaDados implements Runnable {

	private Socket socketSaida = null;
	private PrintStream PSsaida = null;
	
	private String ipServidor;
	private int portaSaida; 
	private String ipDestino;
	
	public SaidaDados(String ipDestino) {
		this.ipServidor = Configuracoes.getIpServidor();
		this.portaSaida = Configuracoes.getPortaSaida();
		this.ipDestino = ipDestino;
	}
	
	/**
	 * Encerra Thread de saida
	 */
	public void encerrarThread() {
		
		PSsaida.println("SYSTEM|KILL CLIENT");
		Thread.currentThread().interrupt();
	}
	
	public void run() {
		// Conecta ao socket
		try {
    	socketSaida = new Socket(this.ipServidor, this.portaSaida);  
    	System.out.println("Criada conexão com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		} catch(IOException e) {
			System.out.println("Erro ao conectar com o servidor "+this.ipServidor+" pela porta "+this.portaSaida);
		}

		// Cria printStream
		PSsaida = null;  
    	try {
			PSsaida = new PrintStream(this.socketSaida.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar PrintStream de saída");
		} 
	}
	
	/**
	 * Criptografa e manda mensagem
	 */
	public void enviarMensagem(String msg) {
		String mensagem = this.ipDestino + "|" + msg; 
		
		Criptografia cript = new Criptografia();
        
		//Criptografa e manda mensagem
        PSsaida.println(cript.criptografarMensagem(mensagem)); 
	}
	
	//TODO arquivo
//	@SuppressWarnings("resource")
//	public void enviarArquivo() {
//		try {	
//			File arquivo = escolherArquivo();
//			if (arquivo == null) {
//				return;		//Aborta execução do metodo
//			}
//			
//			PSsaida.println("FILE|ipqualquer");
//						
//			FileInputStream in;
//			in = new FileInputStream(arquivo.getAbsolutePath());
//			
//			OutputStream out = this.socketSaida.getOutputStream();
//			
//			OutputStreamWriter osw;
//			osw = new OutputStreamWriter(out);		
//			 
//			BufferedWriter writer = new BufferedWriter(osw); 
//			
//			writer.write(arquivo.getName() + "\n");
//		    writer.flush(); 
//		    
//		    Thread.sleep(1000); //QG gambiara master - Sem esse sleep o arquivo é enviado com 0b
//			 
//		    int tamanho = 4096; // buffer de 4KB 
//		    byte[] buffer = new byte[tamanho];    
//	        
//		    int lidos = -1;
//		    while ((lidos = in.read(buffer, 0, tamanho)) != -1) {    
//	            out.write(buffer, 0, lidos);    
//		    }  
//		} catch (IOException | InterruptedException  e) {
//			System.out.println("Erro ao enviar arquivo ao servidor!");
//			e.printStackTrace();
//		}	 
//	}
//	
//    public File escolherArquivo(){
//        File arquivo  = null;  
//        JFileChooser fc = new JFileChooser();
//        fc.setDialogTitle("Escolha o arquivo...");  
//        fc.setDialogType(JFileChooser.OPEN_DIALOG);  
//        fc.setApproveButtonText("OK");  
//        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
//        fc.setMultiSelectionEnabled(false);  
//        int resultado = fc.showOpenDialog(fc);  
//        if (resultado == JFileChooser.CANCEL_OPTION){  
//        	return null;
//        }  
//        
//        arquivo = fc.getSelectedFile();
//
//        return arquivo;  
//    } 
}
