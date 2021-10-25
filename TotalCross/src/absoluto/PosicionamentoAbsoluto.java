package absoluto;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;

public class PosicionamentoAbsoluto extends MainWindow {
	
	private Button btLeftTop;
	private Button btCenterCenter;
	private Button btRightBottom;
	
	public PosicionamentoAbsoluto() {
		btLeftTop = new Button("Aqui temos o primeiro bot�o");
		btCenterCenter = new Button("O segundo bot�o deveria ser posicionado aqui");
		btRightBottom = new Button("O �ltimo bot�o est� em sua posi��o");
	}
	
	@Override
	public void initUI() {
		add(btLeftTop, 0, 0);
		add(btCenterCenter, 0, 23);
		add(btRightBottom, 228, 0);
	}
}
