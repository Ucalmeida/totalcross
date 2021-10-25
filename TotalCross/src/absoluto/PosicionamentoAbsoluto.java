package absoluto;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;

public class PosicionamentoAbsoluto extends MainWindow {
	
	private Button btLeftTop;
	private Button btCenterCenter;
	private Button btRightBottom;
	
	public PosicionamentoAbsoluto() {
		btLeftTop = new Button("Aqui temos o primeiro botão");
		btCenterCenter = new Button("O segundo botão deveria ser posicionado aqui");
		btRightBottom = new Button("O último botão está em sua posição");
	}
	
	@Override
	public void initUI() {
		add(btLeftTop, 0, 0);
		add(btCenterCenter, 0, 23);
		add(btRightBottom, 228, 0);
	}
}
