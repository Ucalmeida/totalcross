package ui;

import java.sql.SQLException;
import java.util.List;

import dao.SorveteDAO;
import domain.Sorvete;
import totalcross.sys.Settings;
import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.PenEvent;

public class ListarSorvetesWindow extends Window {

	// Agora vamos criar uma nova Window para listar os sorvetes. 
	// A partir do MainMenu, o nosso botão “Sorvetes” irá agora 
	// chamar a tela de listagem primeiro, e dentro da tela de 
	// listagem teremos um botão para incluir um sorvete. 
	// Vamos utilizar o ScrollContainer para montar a lista
	private ScrollContainer listaSorvetes;
	
	private Button btIncluir;
	private Button btVoltar;

	// Declarar sorveteList como uma variável global
	List<Sorvete> sorveteList;
	
	private SorveteDAO sorveteDAO;
	
	public ListarSorvetesWindow() throws SQLException {
		listaSorvetes = new ScrollContainer();
		btIncluir = new Button("Incluir Sorvete");
		btVoltar = new Button("Voltar");

		// 3 - Depois vamos instanciar o SorveteDAO
		sorveteDAO = new SorveteDAO();
		
		// Realizar a pesquisa no Construtor da Classe
		sorveteList = sorveteDAO.findAllSorvetes();
	}
	
	// 1 - Primeiro vamos criar um método para buscar 
	private void loadList() throws SQLException {
		
		// 4 - e chamar o método findAllSorvetes() - Primeira forma
		// List<Sorvete> sorveteList = sorveteDAO.findAllSorvetes();
		
		// Criar esse inteiro para usa-lo para definir a propriedade appId do Container
		// Essa propriedade irá nos ajudar a obter os dados do sorvete que o usuário irá selecionar
		int index = 0;
		
		// 2 - e montar a lista de sorvetes
		for (Sorvete sorvete : sorveteList) {
			// Podemos então montar cada Container de sorvete, conforme vimos anteriormente
			String[] dados = sorveteToArray(sorvete);
			Container sorveteContainer = new Container();
			sorveteContainer.appId = index++;
			sorveteContainer.setBorderStyle(BORDER_SIMPLE);
			listaSorvetes.add(sorveteContainer, LEFT + 10, AFTER + 3, listaSorvetes.getWidth() - 30, 50);
			for (int dadosIndex = 0; dadosIndex < 3; dadosIndex++) {
				int horizontalPosition = dadosIndex % 2 == 0 ? LEFT + 10 : RIGHT - 10;
				int verticalPosition = dadosIndex % 2 == 0 ? AFTER : SAME;
				sorveteContainer.add(new Label(dados[dadosIndex]), horizontalPosition, verticalPosition);
			}
		}
	}

	// criar um método para transformar nosso objeto sorvete em um array de Strings. 
	// Note que os valores double precisam ser convertidos para String. No momento 
	// vamos apenas utilizar o método valueOf() da classe String
	private String[] sorveteToArray(Sorvete sorvete) {
		
		String[] dadosArray = new String[3];
		
		dadosArray[0] = sorvete.sabor;
		dadosArray[1] = String.valueOf(sorvete.valor);
		dadosArray[2] = String.valueOf(sorvete.estoque);
		
		return dadosArray;
	}
	
	// Podemos refatorar também o método initUI() para reaproveitar melhor o fonte. 
	// Extraímos o conteúdo do initUI() para um método, montaTela()
	@Override
	public void initUI() {
		montaTela();
	}
	
	// É necessário remontar a lista após a adição de um sorvete
	// É criado então um método reloadList(). Nele, removemos todos os
	// elementos do ScrollContainer() com o removeAll(). Depois chama o loadList() - Primeira forma - Depois mudou
	// Depois, em onEvent(), ao invés de chamar loadList(), chamamos o reloadList() e 
	// o reposition() da classe Window, que recalcula as posições e adiciona corretamente à tela
	private void reloadList() throws SQLException {
		// Como não fazemos mais a pesquisa da lista de sorvetes no método loadList(), 
		// precisamos refazê-la no método reloadList()
		sorveteList = sorveteDAO.findAllSorvetes();
		listaSorvetes = new ScrollContainer();
		// No método reloadList() refazemos a pesquisa dos sorvetes, e instanciamos 
		// um novo scrollContainer, para que ele seja refeito corretamente. Removemos 
		// da tela todos os componentes com o método removeAll(), e chamamos o método 
		// montaTela(). Em seguida basta chamar o método reposition()
		removeAll();
		montaTela();
		reposition();
	}
	
	private void montaTela() {

		add(listaSorvetes, LEFT, TOP, FILL, getScrollContainerSize());

		// 5 - Vamos chamar o método reloadList() no initUI(), após adicionar o ScrollContainer, 
		// dentro de um bloco try-catch, para tratar possíveis erros do método de busca. 
		try {
			loadList();
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
		add(btIncluir, CENTER -40, BOTTOM -10);
		add(btVoltar, CENTER + 40, BOTTOM - 10);
	}
	
	// Podemos extrair o cálculo do tamanho do scrollContainer para um método, 
	// e definir o tamanho máximo, para o tamanho da tela, para que a barra de 
	// rolagem funcione corretamente. Para isso verificamos se o valor do cálculo 
	// não é maior que a tela
	private int getScrollContainerSize() {
		int size = (sorveteList.size() * 50) + (sorveteList.size() * 3) + 10;
		size = size > Settings.screenHeight ? Settings.screenHeight - 10 : size;
		return size;
	}
	
	@Override
	public void popup() {
		setRect(0, 0, Settings.screenWidth, Settings.screenHeight);
		super.popup();
	}
	
	public void onEvent(Event event) {
		switch(event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btIncluir) {
				IncluirSorvetesWindow sorvetesWindow = new IncluirSorvetesWindow();
				sorvetesWindow.popup();
				try {
					reloadList();
				} catch (SQLException e) {
					Vm.debug(e.getMessage());
				}
			} else if (event.target == btVoltar) {
				this.unpop();
			}
			break;
		case PenEvent.PEN_DOWN:
			// Verifica se o local clicado é um container e não é uma Window
			if(event.target instanceof Container && !(event.target instanceof Window)) {
				Container c = (Container) event.target;
				// Utilizamos agora a propriedade appId para recuperar o sorvete, na lista de sorvetes
				Sorvete sorvete = sorveteList.get(c.appId);
				// Caso sorvete seja nulo, retornamos sem continuar o evento
				if (sorvete == null) return;
				// Caso encontre o sorvete, utilizamos o novo construtor para enviar para a classe IncluirSorvetesWindow
				IncluirSorvetesWindow sorvetesWindow = new IncluirSorvetesWindow(sorvete);
				sorvetesWindow.popup();
				try {
					reloadList();
				} catch (SQLException e) {
					Vm.debug(e.getMessage());
				}
			}
		default:
			break;
		}
		super.onEvent(event);
	}
}
