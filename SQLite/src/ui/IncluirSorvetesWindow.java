package ui;

import java.sql.SQLException;

import dao.SorveteDAO;
import domain.Sorvete;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import util.ScreenToDomain;

public class IncluirSorvetesWindow extends Window {

	private Edit editSabor;
	private Edit editValor;
	private Edit editEstoque;
	
	private Button btInserir;
	private Button btVoltar;
	private Button btAtualizar;
	private Button btExcluir;
	
	private ScreenToDomain screen;
	
	// Declara o DAO
	private SorveteDAO sorveteDAO;
	
	private boolean atualizando;
	
	public IncluirSorvetesWindow() {
		
		screen = new ScreenToDomain();
		
		this.atualizando = false;
		
		editSabor = new Edit();
		
		editValor = new Edit("9999999,99");
		editValor.setMode(Edit.CURRENCY, true);
		editValor.setValidChars("0123456789");
		
		editEstoque = new Edit("9999999,99");
		editEstoque.setMode(Edit.CURRENCY, true);
		editEstoque.setValidChars("0123456789");
		
		btInserir = new Button("Inserir");
		btVoltar = new Button("Voltar");
		btAtualizar = new Button("Atualizar");
		btExcluir = new Button("Excluir");
		
		// Instancia o DAO
		sorveteDAO = new SorveteDAO();
	}
	
	// Novo construtor setando o valor nos campos edit
	public IncluirSorvetesWindow(Sorvete sorvete) {
		// Primeiro chamamos o construtor vazio aqui
		this();
		
		this.atualizando = true;
		
		// Aqui setamos os valores do sorvete escolhido anteriormente
		editSabor.setText(sorvete.sabor);
		editValor.setText(String.valueOf(sorvete.valor));
		editEstoque.setText(String.valueOf(sorvete.estoque));
	}
	
	@Override
	public void initUI() {
		add(new Label("Sabor"), LEFT + 10, TOP + 10);
		add(editSabor, LEFT + 10, AFTER + 5, FILL -10, PREFERRED);
		
		add(new Label("Valor"), LEFT + 10, AFTER + 10);
		add(editValor, LEFT + 10, AFTER + 5);
		
		add(new Label("Estoque"), LEFT + 10, AFTER + 10);
		add(editEstoque, LEFT + 10, AFTER + 5);
		
		// Condição para escolher entre atualizar ou inserir
		if(atualizando) {
			add(btAtualizar, CENTER -20, AFTER + 20);
			add(btExcluir, AFTER + 20, SAME);
		} else {
			add(btInserir, CENTER, AFTER + 20);
		}
		add(btVoltar, RIGHT -20, BOTTOM - 20);
	}
	
	// Como estendemos da classe Window, e não MainWindow, devemos fazer a chamada do método 
	// setRect() manualmente, para que a tela seja desenhada efetivamente
	@Override
	public void popup() {
		setRect(0, 0, Settings.screenWidth, Settings.screenHeight);
		super.popup();
	}
	
	// Criar um evento para o botão voltar - unpop()
	@Override
	public void onEvent(Event event) {
		switch(event.type) {
		case ControlEvent.PRESSED:
			if(event.target == btVoltar) {
				this.unpop();
			} else if(event.target == btInserir) {
				insertSorvete();
				unpop();
			} else if(event.target == btAtualizar) {
				atualizarSorvete();
				unpop();
			} else if(event.target == btExcluir) {
				excluirSorvete();
				unpop();
			}
			break;
		default:
			break;
		}
		super.onEvent(event);
	}
	
	public void insertSorvete() {
		try {
			Sorvete sorvete = screen.sorveteScreenToDomain(editSabor.getText(), editValor.getText(), editEstoque.getText());
			if(sorvete == null) return;
			// Faz uso do DAO
			if(sorveteDAO.insertSorvete(sorvete)) {
				new MessageBox("Info", "Sorvete Inserido").popup();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}
	
	public void atualizarSorvete() {
		try {
			Sorvete sorvete = screen.sorveteScreenToDomain(editSabor.getText(), editValor.getText(), editEstoque.getText());
			if(sorvete == null) return;
			// Faz uso do DAO
			if(sorveteDAO.atualizarSorvete(sorvete)) {
				new MessageBox("Info", "Sorvete Atualizado!").popup();
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}
	
	public void excluirSorvete() {
		try {
			Sorvete sorvete = screen.sorveteScreenToDomain(editSabor.getText(), editValor.getText(), editEstoque.getText());
			if(sorvete == null) return;
			// Faz uso do DAO
			if(sorveteDAO.excluirSorvete(sorvete)) {
				new MessageBox("Info", "Sorvete Excluído!").popup();
			}
		} catch (Exception e) {
			Vm.debug(e.getMessage());
		}
	}
}