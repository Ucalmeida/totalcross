package br.com.wmw.lavenderepda.business.service;

import java.sql.SQLException;

import br.com.wmw.framework.business.domain.BaseDomain;
import br.com.wmw.framework.business.service.CrudService;
import br.com.wmw.framework.integration.dao.CrudDao;
import br.com.wmw.lavenderepda.business.domain.Licao;
import br.com.wmw.lavenderepda.integration.dao.pdbx.LicaoPdbxDao;

public class LicaoService extends CrudService {

	private static LicaoService instance;
	
	public LicaoService() {
	}
	
	public static LicaoService getInstance() {
		if(instance == null) {
			instance = new LicaoService();
		}
		return instance;
	}

	@Override
	public void validate(BaseDomain domain) throws SQLException {
	}

	@Override
	protected CrudDao getCrudDao() {
		return new LicaoPdbxDao();
	}

	@Override
	public void insert(BaseDomain domain) throws SQLException {
		Licao licao = (Licao) domain;
		licao.cdLicao = generateIdGlobal();
		super.insert(licao);
	}
}
