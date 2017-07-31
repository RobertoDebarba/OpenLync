package openlync.principal;

import java.io.IOException;

import openlync.forms.FormMain;

public class OpenLync {

	public static FormMain frmMain = new FormMain();
	
	public static void main(String[] args) throws IOException {
		new OpenLync();
	}

	public OpenLync() {
		frmMain.setVisible(true);
		Configuracao.lerCfg();
	}
}
