package openlync.principal;
import openlync.forms.FormMain;

//Referencias:
//	
//	http://www.guj.com.br/articles/126

public class OpenLync_client {

	public OpenLync_client() {

		FormMain frmMain = new FormMain();
		frmMain.setVisible(true);

		FormMain.abrirFrmLogin();

		new Configuracao();
	}

	public static void main(String[] args) {

		new OpenLync_client();
	}
}
