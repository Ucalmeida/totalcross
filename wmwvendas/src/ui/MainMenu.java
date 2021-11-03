package br.com.wmw.lavenderepda.presentation.ui;

import java.sql.SQLException;

import br.com.wmw.framework.FrameworkMessages;
import br.com.wmw.framework.business.domain.Menu;
import br.com.wmw.framework.business.domain.UsuarioSistema;
import br.com.wmw.framework.business.service.AutorizacaoService;
import br.com.wmw.framework.business.service.CampoTelaService;
import br.com.wmw.framework.business.service.FuncaoPapelService;
import br.com.wmw.framework.business.service.MenuService;
import br.com.wmw.framework.business.service.TelaService;
import br.com.wmw.framework.business.service.UsuarioSistemaService;
import br.com.wmw.framework.presentation.ui.DynamicMenu;
import br.com.wmw.framework.presentation.ui.event.MenuEvent;
import br.com.wmw.framework.presentation.ui.ext.ButtonAction;
import br.com.wmw.framework.presentation.ui.ext.LabelName;
import br.com.wmw.framework.presentation.ui.ext.LoadingBoxWindow;
import br.com.wmw.framework.presentation.ui.ext.SessionContainer;
import br.com.wmw.framework.presentation.ui.ext.UiUtil;
import br.com.wmw.framework.presentation.ui.ext.WmwMessageBox;
import br.com.wmw.framework.util.ColorUtil;
import br.com.wmw.framework.util.DateUtil;
import br.com.wmw.framework.util.ExceptionUtil;
import br.com.wmw.framework.util.MessageUtil;
import br.com.wmw.framework.util.ScannerCameraUtil;
import br.com.wmw.framework.util.StringUtil;
import br.com.wmw.framework.util.TimeUtil;
import br.com.wmw.framework.util.ValueUtil;
import br.com.wmw.framework.util.VmUtil;
import br.com.wmw.lavenderepda.LavendereConfig;
import br.com.wmw.lavenderepda.Messages;
import br.com.wmw.lavenderepda.SessionLavenderePda;
import br.com.wmw.lavenderepda.business.conf.LavenderePdaConfig;
import br.com.wmw.lavenderepda.business.domain.ColetaGps;
import br.com.wmw.lavenderepda.business.domain.ConfigInterno;
import br.com.wmw.lavenderepda.business.domain.Novidade;
import br.com.wmw.lavenderepda.business.domain.NovoCliente;
import br.com.wmw.lavenderepda.business.domain.PlatVendaMeta;
import br.com.wmw.lavenderepda.business.domain.ValorParametro;
import br.com.wmw.lavenderepda.business.service.AutorizacaoLavendereService;
import br.com.wmw.lavenderepda.business.service.CampoTelaLavendereService;
import br.com.wmw.lavenderepda.business.service.CargaPedidoService;
import br.com.wmw.lavenderepda.business.service.ColetaGpsService;
import br.com.wmw.lavenderepda.business.service.ConexaoPdaService;
import br.com.wmw.lavenderepda.business.service.ConfigInternoService;
import br.com.wmw.lavenderepda.business.service.DadosTc2WebService;
import br.com.wmw.lavenderepda.business.service.EmpresaService;
import br.com.wmw.lavenderepda.business.service.ErroEnvioService;
import br.com.wmw.lavenderepda.business.service.FuncaoPapelLavendereService;
import br.com.wmw.lavenderepda.business.service.MenuLavendereService;
import br.com.wmw.lavenderepda.business.service.NovidadeService;
import br.com.wmw.lavenderepda.business.service.NovoClienteService;
import br.com.wmw.lavenderepda.business.service.PedidoService;
import br.com.wmw.lavenderepda.business.service.PlatVendaMetaAtuaService;
import br.com.wmw.lavenderepda.business.service.PlatVendaMetaPeridService;
import br.com.wmw.lavenderepda.business.service.PlatVendaMetaService;
import br.com.wmw.lavenderepda.business.service.PontoGpsService;
import br.com.wmw.lavenderepda.business.service.TelaLavendereService;
import br.com.wmw.lavenderepda.business.service.UsuarioLavendereService;
import br.com.wmw.lavenderepda.business.service.UsuarioPdaRepService;
import br.com.wmw.lavenderepda.business.service.UsuarioSistemaLavendereService;
import br.com.wmw.lavenderepda.presentation.ui.ext.LiberacaoSenhaWindow;
import br.com.wmw.lavenderepda.sync.SyncManager;
import br.com.wmw.lavenderepda.thread.EnviaTabelasBackgroundThread;
import br.com.wmw.lavenderepda.thread.RecebeDadosBackgroundThread;
import br.com.wmw.lavenderepda.util.UiMessagesUtil;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.util.Date;
import totalcross.util.Vector;

public class MainMenu extends DynamicMenu {

	public static int CDTELA_CLIENTES = 1;
	public static int CDTELA_PEDIDOS = 2;
	public static int CDTELA_PRODUTOS = 3;
	public static int CDTELA_VISITA = 5;
	public static int CDTELA_AGENDA = 6;
	public static int CDTELA_RECADO = 7;
	public static int CDTELA_SYNC = 8;
	public static int CDTELA_NOVOCLIENTE = 9;
	public static int CDTELA_NOVIDADES = 10;
	public static int CDTELA_INFOENTREGA = 11;
	public static int CDTELA_ANIVERSARIANTES = 12;
	public static int CDTELA_INADIMPREP = 13;
	public static int CDTELA_INADIMPCLI = 14;
	public static int CDTELA_CLIENTESNAOATENDIDOS = 15;
	public static int CDTELA_METASVENDA = 16;
	public static int CDTELA_METASVENDAUNIF = 17;
	public static int CDTELA_METAACOMP = 18;
	public static int CDTELA_INDICADORESPROD = 19;
	public static int CDTELA_PRODUTIVIDADE = 20;
	public static int CDTELA_METASPRODUTO = 21;
	public static int CDTELA_METASFORNECEDOR = 22;
	public static int CDTELA_METASCLIENTE = 23;
	public static int CDTELA_CESTAPOSITIV = 24;
	public static int CDTELA_ACOMPVISITAS = 25;
	public static int CDTELA_METASGRUPOPROD = 26;
	public static int CDTELA_RESUMODIA = 27;
	public static int CDTELA_NOVOPEDIDO = 28;
	public static int CDTELA_METAVENDACLIENTE = 29;
	public static int CDTELA_CONFIGPARAMETRO = 31;
	public static int CDTELA_TEMAS = 32;
	public static int CDTELA_QRCODE = 33;
	public static int CDTELA_SOBRE = 34;
	public static int CDTELA_IMPRESSAO = 35;
	public static int CDTELA_CARGAPEDIDO = 36;
	public static int CDTELA_PRODUCAOPRODREP = 37;
	public static int CDTELA_DOCNAOIMPRESSO = 38;
	public static int CDTELA_VENCIMENTO_PEDIDO_CONSIGNACAO = 39;
	public static int CDTELA_ANALISE_CLIENTE = 40;
	public static int CDTELA_REMESSA_ESTOQUE = 43;
	public static int CDTELA_VALORIZACAO_PRODUTO = 44;
	public static int CDTELA_FECHAMENTO_DIARIO = 45;
	public static int CDTELA_CARGA_PRODUTO = 46;
	public static int CDTELA_FECHAMENTO_VENDAS = 47;
	public static int CDTELA_CESTA_POSITIVACAO = 48;
	public static int CDTELA_INDICADOR_PROD_INTERNO = 49;
	public static int CDTELA_EXTRATO_PONTUACAO_REP = 50;
	public static int CDTELA_PROSPECT = 51;
	public static int CDTELA_REQUISICAO_SERV = 52;
	public static int CDTELA_PLANEJAR_METAS = 53;
	public static int CDTELA_SOLICITACAO_AUTORIZACAO = 54;
	public static int CDTELA_TREINAMENTO = 2001; // MEU

	public static final int CDMENU_PONTUACAO = 50;
	public static final int CDMENU_PONTUACAO_EXTRATO = 51;
	

	
	public static int CDTELA_CLIENTE_CHURN = 55;
	
	private ButtonAction btSair;
	private ButtonAction btTrocarEmpresaRep;
	private ButtonAction btIniciarPararColetaGps;
	private SessionContainer barButtomMenu;
	
	public MainMenu(String newTitle) throws SQLException {
		super(newTitle);
		int typeMenuSaved = ValueUtil.getIntegerValue(ConfigInternoService.getInstance().getVlConfigInterno(ConfigInterno.tipoMenuSistema, newTitle));
		typeMenu = typeMenuSaved == 0 ? 1 : typeMenuSaved;
		btSair = new ButtonAction(FrameworkMessages.BOTAO_SAIR, "images/sair.png");
		barButtomMenu = new SessionContainer();
    	barButtomMenu.setBackColor(ColorUtil.formsBackColor);
   		btIniciarPararColetaGps = new ButtonAction(Messages.COLETAGPS_MSG_COLETA_GPS_INATIVA, UiUtil.getIconButtonAction("images/gps.png"), RIGHT, 0);
   		btIniciarPararColetaGps.setFont(UiUtil.defaultFontSmall);
	}
	
	@Override
	protected boolean isMenuAutorizado(Vector funcaoPapelList, Menu menu) throws SQLException {
		if (!LavenderePdaConfig.mostraExtratoPontuacao && (ValueUtil.valueEquals(CDMENU_PONTUACAO, menu.cdMenu) || ValueUtil.valueEquals(CDMENU_PONTUACAO_EXTRATO, menu.cdMenu))) return false;
		return super.isMenuAutorizado(funcaoPapelList, menu);
	}
	
	protected int getCdSistema() {
		return LavendereConfig.CDSISTEMALAVENDEREPDA;
	}

	protected String getCdUsuario() {
		return SessionLavenderePda.usuarioPdaRep.cdUsuario;
	}

	@Override
	protected String getCdFuncaoUsuario() throws SQLException {
		return UsuarioLavendereService.getInstance().findColumnByRowKey(SessionLavenderePda.usuarioPdaRep.usuario.getRowKey(), "cdFuncao");
	}

	protected MenuService getMenuServiceInstance() {
		return MenuLavendereService.getInstance();
	}

	protected AutorizacaoService getAutorizacaoServiceInstance() {
		return AutorizacaoLavendereService.getInstance();
	}

	protected FuncaoPapelService getFuncaoPapelServiceInstance() {
		return FuncaoPapelLavendereService.getInstance();
	}

	protected UsuarioSistemaService getUsuarioSistemaServiceInstance() {
		return UsuarioSistemaLavendereService.getInstance();
	}

	protected TelaService getTelaServiceInstance() {
		return TelaLavendereService.getInstance();
	}

	protected CampoTelaService getCampoTelaServiceInstance() {
		return CampoTelaLavendereService.getInstance();
	}

	protected void onFormStart() throws SQLException {
		super.onFormStart();
		if (isMenuPrincipal()) {
			//--
			Vector usuarioPdaErpList = UsuarioPdaRepService.getInstance().findAll();
			Vector empresaList = EmpresaService.getInstance().findAllByRepresentante(SessionLavenderePda.usuarioPdaRep.cdRepresentante);
			boolean useBtTrocarEmpresaRep = empresaList.size() > 1 || usuarioPdaErpList.size() > 1;
			String empRep = getDsEmpresaRepresentante();
			if (useBtTrocarEmpresaRep) {
				btTrocarEmpresaRep = new ButtonAction(UiUtil.getColorfulImage("images/trocaEmpRep.png", (barBottomContainer.getHeight() * 2) / 3, (barBottomContainer.getHeight() * 2) / 3));
			}
			UiUtil.add(barBottomContainer, new LabelName(empRep), LEFT + (useBtTrocarEmpresaRep ? btTrocarEmpresaRep.getPreferredWidth() + WIDTH_GAP : WIDTH_GAP), CENTER, FILL - btSair.getPreferredWidth() - WIDTH_GAP_BIG, PREFERRED);
			if (empresaList.size() > 1 || usuarioPdaErpList.size() > 1) {
				UiUtil.add(barBottomContainer, btTrocarEmpresaRep, LEFT, CENTER, barBottomContainer.getHeight() - HEIGHT_GAP, barBottomContainer.getHeight() - HEIGHT_GAP);
			}
			//--
			UiUtil.add(barBottomContainer, btSair, RIGHT - WIDTH_GAP_BIG, CENTER + 2, PREFERRED, PREFERRED);
			Novidade novidade = NovidadeService.getInstance().findNovidadePendenteLeitura(getCdSistema());
			if (ValueUtil.isNotEmpty(novidade.cdNovidade)) {
				NovidadeWindow novidadeWindow = new NovidadeWindow(novidade);
				novidadeWindow.popup();
			}
			if (isUsaColetaGps()) {
				ColetaGpsService.getInstance().encerraColetaGpsSeNecessario();
				UiUtil.add(barButtomMenu, btIniciarPararColetaGps, LEFT, CENTER, PREFERRED, UiUtil.getControlPreferredHeight());
				atualizaCorImagemBtIniciarPararColetaGps();
			}
			LiberacaoSenhaWindow.verificaGpsDesligado(false, true);
		}
	}
	
	private void verificaAvisaVencimentoCargaPedido() throws SQLException {
		if (LavenderePdaConfig.nuDiasRestantesAvisoVencimentoCarga > 0 && CargaPedidoService.getInstance().avisaValidadeCarga()) {
			String[] botoes = new String[] { FrameworkMessages.BOTAO_FECHAR, FrameworkMessages.BOTAO_VISUALIZAR };
			int acao = UiUtil.showMessage(FrameworkMessages.TITULO_MSG_ATENCAO, Messages.CARGAPEDIDO_AVISO_VENCIMENTO, WmwMessageBox.TYPE_MESSAGE_WARN, botoes);
			if (acao != 0) {
				new ListCargaPedidoVencidasWindow(Messages.CARGAPEDIDO_TITULO_LISTA_PROXIMO_VENCIMENTO).popup();
			}
		}
	}

	protected void addFundoMenu() {
		if (isMenuPrincipal()) {
			int height = isUsaColetaGps() ? barBottomContainer.getHeight() + UiUtil.getControlPreferredHeight() : barBottomContainer.getHeight();
			UiUtil.add(this, sessionFundoMenu, LEFT, getTop(), FILL, FILL - height);
			if (isUsaColetaGps()) {
				UiUtil.add(this, barButtomMenu, LEFT, AFTER, FILL, UiUtil.getControlPreferredHeight());
			}
		} else {
			super.addFundoMenu();
		}
	}

	protected void onFormEvent(Event event) throws SQLException {
		super.onFormEvent(event);
		switch (event.type) {
			case MenuEvent.MENU_CLICKED: {
				Menu menu = (Menu)event.target;
				if (isMenuPrincipal()) {
					SessionLavenderePda.clearSessionCliente();
					ColetaGpsService.getInstance().encerraColetaGpsSeNecessario();
					if (menu.cdMenu != CDTELA_SYNC && !LiberacaoSenhaWindow.verificaGpsDesligado(true, true)) {
						return;
					}
				}
				//--
				if (menu.cdMenu == CDTELA_PEDIDOS) {
					if (LavenderePdaConfig.usaEnvioPedidoBackground) {
						PedidoService.getInstance().updatePedidosTransmitidosFromCacheOnLine(true);
					}
				}
				if (menu.cdTela == 0) {
					if (LavenderePdaConfig.apresentaPopUpErroEnvioPedidoAcessoTelaPorMenu && ErroEnvioService.getInstance().hasPedidosErroEnvioServidor()) {
						ListErroEnvioWindow listErroEnvioWindow = new ListErroEnvioWindow(this);
						listErroEnvioWindow.popup();
						if (!listErroEnvioWindow.closedByBtFechar) {
							return;
						}
					}
					MainMenu mainSubMenu = new MainMenu(menu.nmMenu);
					mainSubMenu.cdMenuPai = menu.cdMenu;
					show(mainSubMenu);
				} else {
					showTelaFixa(menu);
				}
				break;
			}
			case ControlEvent.PRESSED: {
				if (event.target == btSair) {
					if (LavenderePdaConfig.isAvisaPedidoAbertoFechadoEntrarSairSistema()) {
						UiMessagesUtil.mostraMensagemPedidosAbertos();
					}
					if (LavenderePdaConfig.apresentaPopUpErroEnvioPedidoAoSairSistema && ErroEnvioService.getInstance().hasPedidosErroEnvioServidor()) {
						ListErroEnvioWindow listErroEnvioWindow = new ListErroEnvioWindow(this);
						listErroEnvioWindow.popup();
						if (!listErroEnvioWindow.closedByBtFechar) {
							return;
						}
					}
					MainLavenderePda.getInstance().exit();
				} else if (event.target == btTrocarEmpresaRep) {
					btTrocarEmpresaClick();
				} else if (event.target == btIniciarPararColetaGps) {
					if (LavenderePdaConfig.usaMotivoParadaColetaGps && SessionLavenderePda.isColetaGpsEmAndamento()) {
						CadMotivoParadaColetaWindow cadMotivoParadaColetaWindow = new CadMotivoParadaColetaWindow();
						cadMotivoParadaColetaWindow.popup();
						if (cadMotivoParadaColetaWindow.cadastrouMotivo) {
							startStopColetaGps();
						}
					} else {
						String msg = SessionLavenderePda.isColetaGpsEmAndamento() ? Messages.COLETAGPS_MSG_PARAR_COLETA_GPS : Messages.COLETAGPS_MSG_INICIAR_COLETA_GPS;
						if (UiUtil.showConfirmYesNoMessage(Messages.COLETAGPS_LABEL_TITULO, msg)) {
							startStopColetaGps();
						}
					}
				}
				break;
			}
		}
	}

	private void startStopColetaGps() throws SQLException {
		LoadingBoxWindow msgLoading = UiUtil.createProcessingMessage();
		try {
			msgLoading.popupNonBlocking();
			iniciarPararColetaGps();
			atualizaCorImagemBtIniciarPararColetaGps();
		} finally {
			msgLoading.unpop();
		}
	}


	private void iniciarPararColetaGps() throws SQLException {
		if (SessionLavenderePda.isColetaGpsEmAndamento()) {
			PontoGpsService.getInstance().stopColetaGpsSistema();
			ColetaGps coletaGpsEmAndamento = ColetaGpsService.getInstance().getLastColetaGpsEmAndamento();
			if (DateUtil.getCurrentDate().isAfter(coletaGpsEmAndamento.dtColetaGps)) {
				ColetaGpsService.getInstance().finalizaColetaGpsFimDia(coletaGpsEmAndamento);
				ColetaGpsService.getInstance().iniciaColetaGps("00:00:00", TimeUtil.getCurrentTimeHHMMSS());
			}
			ColetaGpsService.getInstance().paraColetaGps(coletaGpsEmAndamento);
			SessionLavenderePda.setColetaGpsEmAndamento(false);
			if (LavenderePdaConfig.isUsaEnvioPontoGpsBackground()) {
				ColetaGpsService.getInstance().montaDadosEnvioColetaGpsBackground(coletaGpsEmAndamento);
			}
		} else {
			if (PontoGpsService.getInstance().startColetaGpsSistema(true)) {
				ColetaGpsService.getInstance().iniciaColetaGps();
				SessionLavenderePda.setColetaGpsEmAndamento(true);
			}
		}
	}
	
	private void atualizaCorImagemBtIniciarPararColetaGps() {
		int color;
		String text;
		if (SessionLavenderePda.isColetaGpsEmAndamento()) {
			color = ColorUtil.baseForeColorSystem;
			text = Messages.COLETAGPS_MSG_COLETA_GPS_ATIVA;
		} else {
			color = Color.BRIGHT;
			text = Messages.COLETAGPS_MSG_COLETA_GPS_INATIVA;
		}
		if (btIniciarPararColetaGps != null) {
			btIniciarPararColetaGps.setText(text);
			btIniciarPararColetaGps.setForeColor(color);
			btIniciarPararColetaGps.setImage(UiUtil.getIconButtonAction("images/gps.png", color));
		}
	}
	
	private boolean isUsaColetaGps() {
		return LavenderePdaConfig.isUsaColetaGpsManual();
	}
	
	protected void afterChangeTypeMenu(int newType) {
		super.afterChangeTypeMenu(newType);
		try {
			ConfigInternoService.getInstance().addValue(ConfigInterno.tipoMenuSistema, getTitle(), StringUtil.getStringValue(newType));
		} catch (SQLException e) {
			ExceptionUtil.handle(e);
		}
	}
	
	private void btTrocarEmpresaClick() throws SQLException {
		//Tem mais de uma empresa, portanto apresenta tela para selecionar a empresa desejada.
		AdmTrocaEmpRepWindow empresaForm = new AdmTrocaEmpRepWindow();
		empresaForm.popup();
		if (empresaForm.result != AdmLoginForm.LOGIN_RESULT_CANCEL) {
			MainLavenderePda.getInstance().removeBaseFormsButNotMenu();
			SyncManager.limpeCaches();
			MainLavenderePda.getInstance().loadSessao(SessionLavenderePda.usuarioPdaRep);
			MainLavenderePda.getInstance().initSystem();
			//--
			MainMenu menu = new MainMenu(title);
			menu.setRect(0, 0, FILL, FILL);
			MainLavenderePda.getInstance().setNewMainForm(menu);
			CadItemPedidoForm.invalidateInstance();
			ListItemPedidoForm.invalidateInstance();
			if (MainLavenderePda.getInstance().permiteAcessoNormalSistema()) {
				MainLavenderePda.getInstance().afterShowMenu();
			}
		} else {
			repaint();
		}
	}

	public void onFormShow() throws SQLException {
		super.onFormShow();
		if (isMenuPrincipal()) {
			showTelaPadraoUsuario();
			//--
			if (PedidoService.getInstance().isCountPedidosAbertosMaiorPermitido()) {
				if (LavenderePdaConfig.usaVerbaUnificada) {
					UiUtil.showWarnMessage(MessageUtil.getMessage(Messages.PEDIDO_MSG_PEDIDOS_EM_ABERTO_SISTEMA_TODAS_EMPRESA, LavenderePdaConfig.getQtdPedidosPermitidosManterAbertos()));
				} else {
					UiUtil.showWarnMessage(MessageUtil.getMessage(Messages.PEDIDO_MSG_PEDIDOS_EM_ABERTO_SISTEMA, LavenderePdaConfig.getQtdPedidosPermitidosManterAbertos()));
				}
				ListPedidoForm listPedidoForm = new ListPedidoForm();
				listPedidoForm.inRelPedidosAbertos = true;
				show(listPedidoForm);
			}
			if ("2".equals(LavenderePdaConfig.getUsaCadastroFotoNovoCliente()) || (LavenderePdaConfig.isUsaCadastroFotoNovoCliente() && LavenderePdaConfig.qtMinimaFotosCadastroNovoCliente > 0)) {
				NovoCliente novoCliente = NovoClienteService.getInstance().getClienteSemFoto();
				if (novoCliente != null) {
					UiUtil.showWarnMessage(Messages.NOVOCLIENTE_MSG_OBRIGATORIEDADE_FOTO_LOGAR);
					CadNovoClienteForm cadNovoClienteForm = new CadNovoClienteForm();
					novoCliente = (NovoCliente) NovoClienteService.getInstance().findByRowKeyDyn(novoCliente.getRowKey());
					cadNovoClienteForm.edit(novoCliente);
					show(cadNovoClienteForm);
				}
			}
			if (LavenderePdaConfig.isAvisaPedidoAbertoFechadoEntrarSairSistema()) {
				UiMessagesUtil.mostraMensagemPedidosAbertos();
			}
			if (LavenderePdaConfig.usaConfigCadastroMetasPlataformaVenda()) {
				validaAvisaBloqueioPlanejamento();
			}
			verificaAvisaVencimentoCargaPedido();
			if (LavenderePdaConfig.nuIntervaloRecebimento > 0) {
				RecebeDadosBackgroundThread.getInstance().start();
			}
			if (LavenderePdaConfig.usaEnvioInformacoesBackground) {
				EnviaTabelasBackgroundThread.getInstance().start();
			}
		}
	}

	private void validaAvisaBloqueioPlanejamento() throws SQLException {
		int qtDiasAlerta = SessionLavenderePda.isUsuarioSupervisor() ? LavenderePdaConfig.getDiasAlertaBloqueioMetaSupCadastroMetasPlataformaVenda() : LavenderePdaConfig.getDiasAlertaBloqueioMetaRepCadastroMetasPlataformaVenda();
		if (qtDiasAlerta <= 0) return;

		int qtDiasBloqueio = SessionLavenderePda.isUsuarioSupervisor() ? LavenderePdaConfig.getDiasBloqueioMetaSupCadastroMetasPlataformaVenda() : LavenderePdaConfig.getDiasBloqueioMetaRepCadastroMetasPlataformaVenda();
		PlatVendaMeta platVendaMeta = PlatVendaMetaService.getInstance().getPlatVendaMeta(qtDiasAlerta, qtDiasBloqueio);
		if (platVendaMeta == null) return;

		PlatVendaMetaAtuaService.getInstance().setPropertiesByAtuaIfExists(platVendaMeta);
		if (ValueUtil.valueEquals(platVendaMeta.flPlanejado, ValueUtil.VALOR_SIM)) return;

		Date dtMeta = platVendaMeta.dtMeta;
		Date dtBloqueio = DateUtil.getDateValue(dtMeta);
		DateUtil.decDay(dtBloqueio, qtDiasBloqueio);
		Date dtAlerta = DateUtil.getDateValue(dtBloqueio);
		DateUtil.decDay(dtAlerta, qtDiasAlerta);
		if (DateUtil.isAfterOrEquals(DateUtil.getCurrentDate(), dtAlerta) && DateUtil.isBefore(DateUtil.getCurrentDate(), dtBloqueio)) {
			String dsPeriodo = PlatVendaMetaPeridService.getInstance().getDsPeriodoBtData(dtMeta, platVendaMeta.cdRepresentante);
			UiUtil.showWarnMessage(MessageUtil.getMessage(Messages.PLANEJAMENTOMETAVENDA_ALERTA_BLOQUEIO, new String[] { 
					Date.getMonthName(dtMeta.getMonth()),
					dsPeriodo,
					StringUtil.getStringValue(dtBloqueio)
			}));

		}
	}
	
	@Override
	public void onFormExibition() throws SQLException {
		if (isUsaColetaGps()) {
			atualizaCorImagemBtIniciarPararColetaGps();
		}
		super.onFormExibition();
	}

	public void showTelaPadraoUsuario() throws SQLException {
		UsuarioSistema usuarioSistema = SessionLavenderePda.usuarioPdaRep.usuario.getUsuarioSistema();
		if (usuarioSistema != null && usuarioSistema.cdTelaPadrao != 0) {
			Menu menu = new Menu();
			menu.cdTela = usuarioSistema.cdTelaPadrao;
			menu.cdSistema = usuarioSistema.cdSistema;
			Vector funcaoPapelList = getFuncaoPapelServiceInstance().getPapelByFuncao(getCdSistema(), getCdFuncaoUsuario());
			if (getAutorizacaoServiceInstance().isTelaAutorizada(funcaoPapelList, menu)) {
				openMenu(menu);
			}
		}
	}
	
	private void showTelaFixa(Menu menu) throws SQLException {
		if (!(menu.cdTela == CDTELA_SYNC) && LavenderePdaConfig.apresentaPopUpErroEnvioPedidoAcessoTelaPorMenu && ErroEnvioService.getInstance().hasPedidosErroEnvioServidor()) {
			ListErroEnvioWindow listErroEnvioWindow = new ListErroEnvioWindow(this);
			listErroEnvioWindow.popup();
			if (!listErroEnvioWindow.closedByBtFechar) {
				return;
			}
		}
		if (menu.cdTela == CDTELA_CLIENTES) {
			if (controlaConexaoObrigatoria()) {
				if (LavenderePdaConfig.isUsaColetaGpsManual() && !SessionLavenderePda.isColetaGpsEmAndamento() && (LavenderePdaConfig.avisaColetaGpsParada == 1 || LavenderePdaConfig.avisaColetaGpsParada == 3)) {
					showAvisoColetaGpsParada();
				}
				show(new ListClienteForm(false, null, null));
			}
		} else if (menu.cdTela == CDTELA_PEDIDOS) {
			if (controlaConexaoObrigatoria()) {
				show(new ListPedidoForm());
			}
		} else if (menu.cdTela == CDTELA_PRODUTOS) {
			if (controlaConexaoObrigatoria()) {
				show(new ListProdutoForm());
			}
		} else if (menu.cdTela == CDTELA_VISITA) {
			if (MainLavenderePda.getInstance().isSistemaBloqueadoPendenteAtualizacao()) {
				if (LavenderePdaConfig.nuIntervaloRecebimento > 0) {
					if (UiUtil.showConfirmYesNoMessage(MessageUtil.getMessage(Messages.SISTEMA_MSG_CONFIRM_ATUALIZAR, new Object[]{LavendereConfig.getInstance().version, ConfigInternoService.getInstance().getVlConfigInternoGeral(ConfigInterno.nuVersaoAtual)}))) {
						SyncManager.atualizaApp();
					}
				}
				return;
			}
			show(new ListVisitaSupervisorForm());
		} else if (menu.cdTela == CDTELA_AGENDA) {
			if (MainLavenderePda.getInstance().isSistemaBloqueadoPendenteAtualizacao()) {
				if (LavenderePdaConfig.nuIntervaloRecebimento > 0) {
					if (UiUtil.showConfirmYesNoMessage(MessageUtil.getMessage(Messages.SISTEMA_MSG_CONFIRM_ATUALIZAR, new Object[]{LavendereConfig.getInstance().version, ConfigInternoService.getInstance().getVlConfigInternoGeral(ConfigInterno.nuVersaoAtual)}))) {
						SyncManager.atualizaApp();
					}
				}
				return;
			}
			if (controlaConexaoObrigatoria()) {
				if (LavenderePdaConfig.isUsaColetaGpsManual() && !SessionLavenderePda.isColetaGpsEmAndamento() && (LavenderePdaConfig.avisaColetaGpsParada == 1 || LavenderePdaConfig.avisaColetaGpsParada == 2)) {
					showAvisoColetaGpsParada();
				}
				show(new ListAgendaVisitaForm());
			}
		} else if (menu.cdTela == CDTELA_RECADO) {
			show(new ListRecadoForm());
		} else if (menu.cdTela == CDTELA_NOVOCLIENTE) {
			if (MainLavenderePda.getInstance().isSistemaBloqueadoPendenteAtualizacao()) {
				if (LavenderePdaConfig.nuIntervaloRecebimento > 0) {
					if (UiUtil.showConfirmYesNoMessage(MessageUtil.getMessage(Messages.SISTEMA_MSG_CONFIRM_ATUALIZAR, new Object[]{LavendereConfig.getInstance().version, ConfigInternoService.getInstance().getVlConfigInternoGeral(ConfigInterno.nuVersaoAtual)}))) {
						SyncManager.atualizaApp();
					}
				}
				return;
			}
			show(new ListNovoClienteForm());
		} else if (menu.cdTela == CDTELA_NOVIDADES) {
			show(new ListRelNovidadeForm());
		} else if (menu.cdTela == CDTELA_ANIVERSARIANTES) {
			show(new ListAniversariantesForm());
		} else if (menu.cdTela == CDTELA_INFOENTREGA) {
			show(new RelInfoEntregaPedidoForm());
		} else if (menu.cdTela == CDTELA_SYNC) {
			btSyncClick();
		} else if (menu.cdTela == CDTELA_INADIMPREP) {
			show(new ListInadimplenciaRepForm());
		} else if (menu.cdTela == CDTELA_INADIMPCLI) {
			show(new ListInadimplenciaClienteForm());
		} else if (menu.cdTela == CDTELA_INDICADORESPROD) {
			show(new ListValorIndicadorForm(!SessionLavenderePda.isUsuarioSupervisor()));
		} else if (menu.cdTela == CDTELA_PRODUTIVIDADE) {
			show(new ListValorIndicadorForm(true));
		} else if (menu.cdTela == CDTELA_METASPRODUTO) {
			show(new ListMetasPorProdutoForm());
		} else if (menu.cdTela == CDTELA_METASFORNECEDOR) {
			show(new ListMetasPorFornecedorForm());
		} else if (menu.cdTela == CDTELA_METASCLIENTE) {
			show(new ListMetasPorClienteForm());
		} else if (menu.cdTela == CDTELA_METAACOMP) {
			show(new ListMetaAcompanhamentoForm());
		} else if (menu.cdTela == CDTELA_CESTAPOSITIV) {
			show(new ListCestaPositProdutoForm());
		} else if (menu.cdTela == CDTELA_ACOMPVISITAS) {
			show(new ListVisitaAcompForm());
		} else if (menu.cdTela == CDTELA_METASGRUPOPROD) {
			show(new ListMetasPorGrupoProduto1Form());
		} else if (menu.cdTela == CDTELA_METASVENDAUNIF) {
			show(new ListMetaVendaTipoForm());
		} else if (menu.cdTela == CDTELA_CLIENTESNAOATENDIDOS) {
			show(new RelClientesNaoAtendidosForm());
		} else if (menu.cdTela == CDTELA_METASVENDA) {
			if (SessionLavenderePda.isUsuarioSupervisor()) {
				show(new ListMetasVendaForm());
			} else {
				show(new ListMetasVendaRepForm(SessionLavenderePda.getRepresentante().cdRepresentante));
			}
		} else if (menu.cdTela == CDTELA_RESUMODIA) {
			show(new RelResumoDiaForm());
		} else if (menu.cdTela == CDTELA_NOVOPEDIDO) {
			if (MainLavenderePda.getInstance().isSistemaBloqueadoPendenteAtualizacao()) {
				if (LavenderePdaConfig.nuIntervaloRecebimento > 0) {
					if (UiUtil.showConfirmYesNoMessage(MessageUtil.getMessage(Messages.SISTEMA_MSG_CONFIRM_ATUALIZAR, new Object[]{LavendereConfig.getInstance().version, ConfigInternoService.getInstance().getVlConfigInternoGeral(ConfigInterno.nuVersaoAtual)}))) {
						SyncManager.atualizaApp();
					}
				}
				return;
			}
			new ListClienteWindow(true, false, false).popup();
		} else if (menu.cdTela == CDTELA_METAVENDACLIENTE) {
			show(new ListMetaVendaCliForm());
		} else if (menu.cdTela == CDTELA_CONFIGPARAMETRO) {
			show(new ListValorParametroForm());
		} else if (menu.cdTela == CDTELA_TEMAS) {
			show(new ListLavendereTemaSistemaForm());
		} else if (menu.cdTela == CDTELA_QRCODE) {
			if (!VmUtil.isAndroid()) {
				UiUtil.showWarnMessage(FrameworkMessages.MENU_QRCODE_APENAS_ANDROID);
			} else {
				String result = ScannerCameraUtil.realizaLeitura(ScannerCameraUtil.MODO_SOMENTE_QRCODE, "");
				if (result != null) {
					UiUtil.showInfoMessage(Messages.MENU_QRCODE, result);
				}
			}
		} else if (menu.cdTela == CDTELA_SOBRE) {
			new AdmSobreLavendereWindow().popup();
		} else if (menu.cdTela == CDTELA_PRODUCAOPRODREP) {
			show(new ListProducaoProdRepForm(SessionLavenderePda.isUsuarioSupervisor()));
		} else if (menu.cdTela == CDTELA_CARGAPEDIDO) {
			show(new ListCargaPedidoForm());
		} else if (menu.cdTela == CDTELA_IMPRESSAO) {
			show(new ImpressaoTesteForm());
		} else if (menu.cdTela == CDTELA_DOCNAOIMPRESSO) {
			show(new ListDocNaoImpressoForm());
		} else if (menu.cdTela == CDTELA_ANALISE_CLIENTE) {
			show(new ListNovoClienteAnaForm());
		} else if (menu.cdTela == CDTELA_CARGA_PRODUTO) {
			show(new ListCargaProdutoForm());
		} else if (menu.cdTela == CDTELA_REMESSA_ESTOQUE) {
			show(new ListRemessaEstoqueForm());
		} else if (menu.cdTela == CDTELA_VENCIMENTO_PEDIDO_CONSIGNACAO) {
			show(new ListVencimentoPedidoConsignacaoForm());
		} else if (menu.cdTela == CDTELA_VALORIZACAO_PRODUTO) {
			show(new ListValorizacaoProdForm());
		} else if (menu.cdTela == CDTELA_FECHAMENTO_DIARIO) {
			show(new RelFechamentoDiarioForm());
		} else if (menu.cdTela == CDTELA_FECHAMENTO_VENDAS) {
			show(new CadFechamentoVendasForm());
		} else if (menu.cdTela == CDTELA_CESTA_POSITIVACAO) {
			show(new ListCestaPositivacaoForm());
		} else if (menu.cdTela == CDTELA_INDICADOR_PROD_INTERNO) {
			show(new ListIndicadorProdutividadeInternoForm());
		} else if (menu.cdTela == CDTELA_REQUISICAO_SERV) {
			show(new ListRequisicaoServForm());
		} else if (menu.cdTela == CDTELA_PROSPECT) {
			show(new ListProspectForm());
		} else if (menu.cdTela == CDTELA_EXTRATO_PONTUACAO_REP) {
			show(new RelPontuacaoExtratoRepresentanteForm());
		} else if (menu.cdTela == CDTELA_PLANEJAR_METAS) {
			if (LavenderePdaConfig.usaConfigCadastroMetasPlataformaVenda()) {
				show(new ListPlanejarVendaMetaForm());
			} else {
				UiUtil.showErrorMessage(MessageUtil.getMessage(Messages.PLANEJAMENTOMETAVENDA_MSG_PARAMETRO_NAO_CONFIGURADO, new String[] {
						StringUtil.getStringValueToInterface(ValorParametro.CONFIGCADASTROMETASPLATAFORMAVENDA),
						Messages.PLANEJAMENTOMETAVENDA_DSPARAMETRO
				}));
			}
		} else if (menu.cdTela == CDTELA_CLIENTE_CHURN) { 
			acessaTelaRiscoChurn();
		} else if (menu.cdTela == CDTELA_SOLICITACAO_AUTORIZACAO) {
			show(new ListSolAutorizacaoForm(null, null, true));
		} else if (menu.cdTela == CDTELA_TREINAMENTO) {
			show(new ListTreinamentoForm());
		} // MEU
	}

	private void acessaTelaRiscoChurn() throws SQLException {
		if (LavenderePdaConfig.usaConfigModuloRiscoChurn()) {
			show(new ListClienteChurnForm());
			return;
		} 
		UiUtil.showErrorMessage(MessageUtil.getMessage(Messages.CLIENTECHURN_MSG_PARAMETRO_NAO_CONFIGURADO, new String[] {
				StringUtil.getStringValueToInterface(ValorParametro.CONFIGMODULORISCOCHURN),
				Messages.CLIENTECHURN_DSPARAMETRO
		}));
	}

	protected void showRelDinamico(Menu menu) throws SQLException {
		show(new ListRelDinamicoLavendereForm(menu.getTela()));
	}

	private boolean controlaConexaoObrigatoria() throws SQLException {
		if (!SessionLavenderePda.isUsuarioSupervisor()) {
			if (!SessionLavenderePda.autorizadoPorSenhaNovoPedidoSemEnvioDados) {
				int resp = ConexaoPdaService.getInstance().isEnvioPedidosNecessario();
				if (resp != 0) {
					if (resp != 2 || ConexaoPdaService.getInstance().isNecessarioSolicitarEnviosPedidos()) {
						MainLavenderePda.getInstance().showEnvioDadosObrigatorio(resp);
					}
				}
			}
			int resp = ConexaoPdaService.getInstance().isRecebimentoDadosNecessario();
			if (resp != 0) {
				if (!MainLavenderePda.getInstance().showReceberDadosObrigatorio(resp)) {
					return false;
				}
			}
			return true;
		}
		return true;
	}

	public void btSyncClick() throws SQLException {
		PedidoService.getInstance().validateBateria();
		if (LavenderePdaConfig.usaEstoqueOnline || LavenderePdaConfig.enviaInformacoesVisitaOnline || LavenderePdaConfig.usaEnvioPedidoBackground) {
			LoadingBoxWindow pb = UiUtil.createProcessingMessage();
			pb.popupNonBlocking();
			try {
				if (LavenderePdaConfig.usaEnvioPedidoBackground) {
					PedidoService.getInstance().updatePedidosTransmitidosFromCacheOnLine(true);
				}
				if (LavenderePdaConfig.usaControleOnlineUsuariosInativos) {
					ConfigInternoService.getInstance().addValue(ConfigInterno.CONFIG_USUARIO_ATIVO, StringUtil.getStringValue(SessionLavenderePda.isUsuarioAtivo));
				}
				//--
				DadosTc2WebService.getInstance().updateDadosTc2WebEnviadosServidor();
			} finally {
				pb.unpop();
			}
		}
		MainLavenderePda.getInstance().showSincronizacaoForm();
	}

	private String getDsEmpresaRepresentante() {
		try {
			String nmEmpresa = EmpresaService.getInstance().getEmpresaName(SessionLavenderePda.cdEmpresa);
			String nmRepresentante = SessionLavenderePda.getRepresentante().toString();
			//--
			StringBuffer str = new StringBuffer();
			boolean empresaNotEmpty = ValueUtil.isNotEmpty(nmEmpresa);
			if (empresaNotEmpty) {
				str.append(nmEmpresa);
			}
			if (ValueUtil.isNotEmpty(nmRepresentante)) {
				if (empresaNotEmpty) {
					str.append(" / ");
				}
				str.append(nmRepresentante);
			}
			return str.toString();

		} catch (Exception e) {
			return "";
		}
	}
	
	private void showAvisoColetaGpsParada() throws SQLException {
		if (UiUtil.showConfirmYesNoMessage(Messages.COLETAGPS_MSG_AVISO_COLETA_GPS_PARADA)) {
			startStopColetaGps();
		}
	}

}