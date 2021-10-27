package ui;

import java.sql.SQLException;

import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import util.DatabaseManager;

public class MainMenu extends MainWindow {

	private Button btSorvetes;
	
	public MainMenu() throws SQLException {
		
		// Serve para inicializar as tabelas do banco
		DatabaseManager.loadTabelas();
		btSorvetes = new Button("Sorvetes");
	}
	
	@Override
	public void initUI() {
		add(btSorvetes, LEFT + 10, TOP + 10, FILL - 10, PREFERRED);
	}
	
	// Instancia a janela Incluir Sorvetes através do método popup
	@Override
	public void onEvent(Event event) {
		switch(event.type ) {
		case ControlEvent.PRESSED:
			if(event.target == btSorvetes) {
				// Para Incluir
//				IncluirSorvetesWindow incluirSorvetesWindow = new IncluirSorvetesWindow();
//				incluirSorvetesWindow.popup();
				
				// Para Listar
				try {
					ListarSorvetesWindow listarSorvetes = new ListarSorvetesWindow();
					listarSorvetes.popup();
				} catch (SQLException e) {
					Vm.debug(e.getMessage());
				}
			}
			break;
		default:
			break;
		}
		super.onEvent(event);
	}
}
