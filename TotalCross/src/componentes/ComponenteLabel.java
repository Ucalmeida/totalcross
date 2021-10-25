package componentes;

import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.gfx.Color;

public class ComponenteLabel extends MainWindow {

	private Label lbTitulo;
	private Label lbLetreiro;
	
	public ComponenteLabel() {
		lbTitulo = new Label("Lista de Labels");
		lbTitulo.backgroundType = Label.SOLID_BACKGROUND;
		lbTitulo.setBackColor(Color.BLUE);
		lbTitulo.setForeColor(Color.WHITE);
		
		lbLetreiro = new Label("Nesta tela você ecnotrará informações sobre algumas propriedades de labels");
		lbLetreiro.setMarqueeText(lbLetreiro.getText(), 200, -1, -10);
	}
	
	@Override
	public void initUI() {
		add(lbTitulo, CENTER, TOP + 10);
		add(lbLetreiro, CENTER, AFTER + 10, 200, PREFERRED);
	}
}
