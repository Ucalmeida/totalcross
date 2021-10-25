package relativo;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;

public class PosicionamentoRelativo extends MainWindow {
	
	private Button btLeftTop;
	private Button btCenterCenter;
	private Button btRightBottom;
	private Button btTeste;
	
	public PosicionamentoRelativo() {
		btLeftTop = new Button("Aqui temos o primeiro bot�o");
		btCenterCenter = new Button("O segundo bot�o deveria ser posicionado aqui");
		btRightBottom = new Button("O �ltimo bot�o est� em sua posi��o");
		btTeste = new Button("Aqui vai ser meu bot�o de teste"); 
	}
	
	@Override
	public void initUI() {
		add(btLeftTop, LEFT + 5, TOP + 3);
		add(btCenterCenter, SAME, AFTER + 3);
		add(btRightBottom, RIGHT - 5, BEFORE - 3);
		add(btTeste, CENTER, BOTTOM- 10);
	}
}
