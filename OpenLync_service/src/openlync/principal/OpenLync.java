package openlync.principal;

import openlync.sockets.Servidor;
import openlync.utilidades.Configuracao;

public class OpenLync {

	public static void main(String[] args) {
		Configuracao.lerCfg();
		
		Servidor server = new Servidor();
		new Thread(server).start();
		System.out.println("Servidor iniciado com sucesso na porta "+Configuracao.getPortaSocket());
	}
}