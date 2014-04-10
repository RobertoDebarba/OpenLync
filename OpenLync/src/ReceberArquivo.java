import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class ReceberArquivo implements Runnable {

	private Socket Scliente;
	
	public ReceberArquivo(Socket cliente) {
		this.Scliente = cliente;
	}
	
	@Override
	public void run() {
		
		System.out.println("Recebendo arquivo de "+ this.Scliente.getInetAddress().getHostAddress());
		
		try {    
            InputStream in = this.Scliente.getInputStream();  
            InputStreamReader isr = new InputStreamReader(in);  
            BufferedReader reader = new BufferedReader(isr);  
            String fName = reader.readLine();  
            System.out.println("Nome do arquivo: "+ fName);  
            File f1 = new File("/home/roberto/joao2.jpg");  
            @SuppressWarnings("resource")
			FileOutputStream out = new FileOutputStream(f1);  
  
                int tamanho = 4096; // buffer de 4KB    
                byte[] buffer = new byte[tamanho];    
                int lidos = -1;    
                while ((lidos = in.read(buffer, 0, tamanho)) != -1) {     
                    out.write(buffer, 0, lidos);    
                }    
                out.flush();    
        } catch (IOException e) {  
        }
        
		return;
	}
}
