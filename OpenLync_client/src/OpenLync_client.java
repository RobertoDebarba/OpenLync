//Referencias:
//	
//	http://www.guj.com.br/articles/126

import java.io.BufferedReader;
import java.io.IOException;  
import java.io.InputStreamReader;
import java.io.PrintStream;  
import java.net.Socket;

public class OpenLync_client {

	public static void main(String[] args) {
		//Declaro o socket cliente  
        Socket s = null;  
          
        //Declaro a Stream de saida de dados  
        PrintStream ps = null;  
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        
        String mensagem = "x";
        while (mensagem != "sair") {
        	
        	try{  
        	
            	//Cria o socket com o recurso desejado na porta especificada  
            	s = new Socket("192.168.152.1",7600);  
            
            	//Cria a Stream de saida de dados  
                ps = new PrintStream(s.getOutputStream()); 
            	
	            System.out.print("Digite alguma mensagem('sair' para sair): ");
	            mensagem = in.readLine(); 
	              
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
