package componentes;

import totalcross.ui.Check;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.gfx.Color;

public class ComponenteCheck extends MainWindow {

	private Check checkJava;
	private Check checkPython;
	private Check checkCSharp;
	private Check checkPhp;
	private Check checkLinguagemLider;
	
	public ComponenteCheck() {
		checkJava = new Check("Java");
		checkJava.setChecked(true);
		
		checkPython = new Check("Python");
		
		checkCSharp = new Check("C#");
		checkCSharp.setChecked(true);
		
		checkPhp = new Check("PHP");
		
		checkLinguagemLider = new Check("Uma linguagem para dominar todas as linguagens, sem distinção de ';'"
				+ " ou tipos de dados, onde todas as classes trabalham em conjunto, e não exista null pointer.");
		checkLinguagemLider.split(this.getWidth() - 20);
		
		checkLinguagemLider.textColor = Color.RED;
		checkLinguagemLider.checkColor = Color.BLUE;
		
		System.out.println(
				"Você conhece:\r\n"
					+ (checkJava.isChecked() ? "Java\r\n" : "")
					+ (checkPython.isChecked() ? "Python\r\n" : "")
					+ (checkCSharp.isChecked() ? "C#\r\n" : "")
					+ (checkPhp.isChecked() ? "PHP\r\n" : "")
			);
	}
	
	@Override
	public void initUI() {
		add(new Label("Escolha suas linguagens de programação"), CENTER, TOP + 10);
		add(checkJava, LEFT + 10, AFTER + 10);
		add(checkPython, SAME, AFTER + 10);
		add(checkCSharp, SAME, AFTER + 10);
		add(checkPhp, SAME, AFTER + 10);
		add(checkLinguagemLider, SAME, AFTER + 10);
	}
}
