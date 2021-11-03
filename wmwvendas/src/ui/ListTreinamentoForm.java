package br.com.wmw.lavenderepda.presentation.ui;

import java.sql.SQLException;

import br.com.wmw.framework.business.domain.BaseDomain;
import br.com.wmw.framework.business.service.CrudService;
import br.com.wmw.framework.presentation.ui.ext.GridListContainer;
import br.com.wmw.framework.presentation.ui.ext.UiUtil;
import br.com.wmw.lavenderepda.Messages;
import br.com.wmw.lavenderepda.business.conf.ListContainerConfig;
import br.com.wmw.lavenderepda.business.domain.Licao;
import br.com.wmw.lavenderepda.business.service.LicaoService;
import br.com.wmw.lavenderepda.presentation.ui.ext.LavendereCrudListForm;
import totalcross.ui.event.Event;
import totalcross.util.Vector;

public class ListTreinamentoForm extends LavendereCrudListForm {

	public ListTreinamentoForm() throws SQLException {
		super("Informações do treinamento");
		constructorListContainer();
		setBaseCrudCadForm(new CadTreinamentoForm());
		singleClickOn = true;
	}
	
	private void constructorListContainer() {
		listContainer = new GridListContainer();
		//ListContainerConfig.getDefautSortColumn(getConfigClassName());
		configListContainer("TITULO");
		listContainer.setColsSort(new String[][] {{Messages.LABEL_LICAO_TITULO, "TITULO"}});
	}

	@Override
	protected BaseDomain getDomainFilter() throws SQLException {
		Licao licao = new Licao();
		licao.titulo = edFiltro.getValue();
		return licao;
	}

	@Override
	protected CrudService getCrudService() throws SQLException {
		return LicaoService.getInstance();
	}

	@Override
	protected void onFormStart() throws SQLException {
		UiUtil.add(this,  edFiltro, LEFT, getTop());
		UiUtil.add(this,  btFiltrar, RIGHT, SAME);
		UiUtil.add(this, listContainer, LEFT, AFTER, FILL, FILL - barBottomContainer.getHeight());
		UiUtil.add(barBottomContainer, btNovo, 5);
	}
	
	@Override
	protected void filtrarClick() throws SQLException {
		list();
	}
	
	@Override
	protected void onFormEvent(Event event) throws SQLException {

	}

	@Override
	protected String[] getItem(Object domain) throws SQLException {
		Licao licao = (Licao) domain;
		Vector item = new Vector();
		item.addElement(licao.titulo);
		return (String[]) item.toObjectArray();
	}
	
	@Override
	protected Vector getDomainList() throws SQLException {
		return getCrudService().findAllByExample(new Licao());
	}
	
	@Override
	public void onFormClose() throws SQLException {
	}
}