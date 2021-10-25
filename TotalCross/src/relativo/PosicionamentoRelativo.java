package relativo;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;

public class PosicionamentoRelativo extends MainWindow {
	
	private Button btLeftTop;
	private Button btCenterCenter;
	private Button btRightBottom;
	private Button btTeste;
	
	public PosicionamentoRelativo() {
		btLeftTop = new Button("Aqui temos o primeiro botão");
		btCenterCenter = new Button("O segundo botão deveria ser posicionado aqui");
		btRightBottom = new Button("O último botão está em sua posição");
		btTeste = new Button("Aqui vai ser meu botão de teste"); 
	}
	
	@Override
	public void initUI() {
		add(btLeftTop, LEFT + 5, TOP + 3);
		add(btCenterCenter, SAME, AFTER + 3);
		add(btRightBottom, RIGHT - 5, BEFORE - 3);
		add(btTeste, CENTER, BOTTOM- 10);
	}
}
