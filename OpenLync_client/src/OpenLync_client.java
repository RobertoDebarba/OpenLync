//Referencias:
//	
//	http://www.guj.com.br/articles/126

public class OpenLync_client {
	
	public OpenLync_client() {
		
		FormMain frmMain = new FormMain();
		frmMain.abrirTela();
		
		FormMain.abrirFrmLogin();
		
		new Configuracoes();
	}

	public static void main(String[] args) {
		
		new OpenLync_client();
	}
	
	public static void iniciarEntrada() {
		
		// Instancia objeto que cuidará da entrada de dados e manda para uma thread
		EntradaDados ed = new EntradaDados(Configuracoes.getPortaEntrada());
		new Thread(ed).start();
	}
}
