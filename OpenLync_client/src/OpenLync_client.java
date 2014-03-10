//Referencias:
//	
//	http://www.guj.com.br/articles/126

import java.io.BufferedReader;
import java.io.IOException;  
import java.io.InputStreamReader;
import java.io.PrintStream;  
import java.net.ServerSocket;
import java.net.Socket;

public class OpenLync_client {

	public static void main(String[] args) throws IOException {
		
		//Aqui começa as pira --------------------------

		// Abre conexão de entrada com o servidor
		ServerSocket Sentrada = new ServerSocket(7601);
	    System.out.println("Porta 7601 aberta!");
	    //Conecta ao servidor
	    Socket servidor = Sentrada.accept();
	    System.out.println("Conectou ao servidor!");
		// Envia objeto para Thread
	    TrataServidor ts = new TrataServidor(servidor);
	    new Thread(ts).start();
	    
		//----------------------------------------------
		
		//Declaro o socket cliente  
        Socket s = null;  
          
        //Declaro a Stream de saida de dados  
        PrintStream ps = null;  
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        
        while (true) {
        	
        	try{  
        	
            	//Cria o socket com o recurso desejado na porta especificada  
            	s = new Socket("192.168.152.1",7600);  
            
            	//Cria a Stream de saida de dados  
                ps = new PrintStream(s.getOutputStream()); 
            	
	            System.out.print("Digite alguma mensagem: ");
	            String mensagem = in.readLine(); 
	              
	            //Imprime uma linha para a stream de saída de dados  
	            ps.println(mensagem);  
	            
	        //Trata possíveis exceções  
	        }catch(IOException e){  
	              
	            System.out.println("Algum problema ocorreu ao criar ou enviar dados pelo socket.");  
	          
	        }finally{  
	              
	            try{  
	                  
	                //Encerra o socket cliente  
	                s.close();  
	                  
	            }catch(IOException e){}  
	          
	        } 
        }
	}

}
