package componentes;

import totalcross.io.IOException;
import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.KeyEvent;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;

public class ComponenteButton extends MainWindow {

	private Button btTexto;
	private Button btImagem;
	private Button btTextoBorda;
	private Button btImagemBorda;
	private Button btCompleto;
	
	public ComponenteButton() throws ImageException, IOException {
		btTexto = new Button("Enviar");
		
		btImagem = new Button(new Image("/resources/camera.png"));
		btTextoBorda = new Button("Enviar", Button.BORDER_3D_HORIZONTAL_GRADIENT);
		btTextoBorda.setBackColor(Color.RED);
		
		btImagemBorda = new Button(new Image("/resources/camera.png"));
		btImagemBorda.setBackColor(Color.BLUE);
		btImagemBorda.borderColor3DG = Color.RED;
		btImagemBorda.setBorder(Button.BORDER_GRAY_IMAGE);
		
		btCompleto = new Button("Enviar", new Image("/resources/camera.png"), BOTTOM, 0);		
	}
	
	@Override
	public void initUI() {
		add(btTexto, LEFT, TOP);
		add(btImagem, RIGHT, BOTTOM);
		add(btTextoBorda, RIGHT, TOP);
		add(btImagemBorda, LEFT, BOTTOM, 100, 100);
		add(btCompleto, CENTER, CENTER);
	}
	
	@Override
	public void onEvent(Event event) {
		switch(event.type) {
		case ControlEvent.PRESSED:
			if(event.target == btTexto) {
				System.out.println("Mensagem enviada!");
				break;
			}
		case KeyEvent.SPECIAL_KEY_PRESS:
			if(event.target == btCompleto) {
				btCompleto.simulatePress();
				System.out.println("Foto capturada!");
				break;
			}
		}
	}
}