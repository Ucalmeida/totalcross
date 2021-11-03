package br.com.wmw.lavenderepda.integration.dao.pdbx;

import java.sql.SQLException;

import br.com.wmw.framework.business.domain.BaseDomain;
import br.com.wmw.framework.integration.dao.dbx.Sql;
import br.com.wmw.framework.integration.dao.dbx.SqlWhereClause;
import br.com.wmw.framework.util.DateUtil;
import br.com.wmw.framework.util.TimeUtil;
import br.com.wmw.lavenderepda.business.domain.Licao;
import totalcross.sql.ResultSet;

public class LicaoPdbxDao extends LavendereCrudPersonDbxDao {
	
	private static LicaoPdbxDao instance;
	
	public LicaoPdbxDao() {
		super(Licao.TABLE_NAME);
	}
	
	public static LicaoPdbxDao getInstance() {
		if (instance == null) {
			instance = new LicaoPdbxDao();
		}
		return instance;
	}

	@Override
	protected BaseDomain populate(BaseDomain domainFilter, ResultSet rs) throws SQLException {
		// Licao licao = (Licao) domainFilter; --> POde ser assim
		Licao licao = new Licao();
		licao.rowKey = rs.getString(1);
		licao.cdLicao = rs.getString(2);
		licao.titulo = rs.getString(3);
		licao.cdLicaoDependente = rs.getString(4);
		licao.conteudo = rs.getString(5);
		return licao;
	}

	@Override
	public void addSelectColumns(BaseDomain domainFilter, StringBuffer sql) throws SQLException {
		sql.append(" ROWKEY");
		sql.append(", CDLICAO");
		sql.append(", TITULO");
		sql.append(", CDLICAODEPENDENTE");
		sql.append(", CONTEUDO");
	}

	@Override
	protected void addUpdateValues(BaseDomain domain, StringBuffer sql) throws SQLException {
		Licao licao = (Licao) domain;
		sql.append(" TITULO = ").append(Sql.getValue(licao.titulo));
		sql.append(", CDLICAODEPENDENTE = ").append(Sql.getValue(licao.cdLicaoDependente));
		sql.append(", CONTEUDO = ").append(Sql.getValue(licao.conteudo));
	}

	@Override
	protected void addWhereByExample(BaseDomain domain, StringBuffer sql) throws SQLException {
		Licao licao = (Licao) domain;
		SqlWhereClause sqlWhereClause = new SqlWhereClause();
		sqlWhereClause.addAndLikeCondition("CDLICAO", licao.cdLicao);
		sqlWhereClause.addOrLikeCondition("TITULO", licao.titulo);
		sqlWhereClause.addAndConditionEquals("CDLICAODEPENDENTE", licao.cdLicaoDependente);
	}

	@Override
	protected BaseDomain getBaseDomainDefault() {
		return null;
	}
	
	@Override
	protected String getInsertValue(String columnName, int columnType, int columnSize, BaseDomain domain) {
		Licao licao = (Licao) domain;
		if ("ROWKEY".equalsIgnoreCase(columnName)) {
			return Sql.getValue(licao.getRowKey());
		}
		if ("CDLICAO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(licao.cdLicao);
		}
		if ("TITULO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(licao.titulo);
		}
		if ("CDLICAODEPENDENTE".equalsIgnoreCase(columnName)) {
			return Sql.getValue(licao.cdLicaoDependente);
		}
		if ("CONTEUDO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(licao.conteudo);
		}
		if ("FLTIPOALTERACAO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(BaseDomain.FLTIPOALTERACAO_INSERIDO);
		}
		if ("CDUSUARIO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(licao.cdUsuario);
		}
		if ("DTALTERACAO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(DateUtil.getCurrentDate());
		}
		if ("HRALTERACAO".equalsIgnoreCase(columnName)) {
			return Sql.getValue(TimeUtil.getCurrentTimeHHMMSS());
		}
		return super.getInsertValue(columnName, columnType, columnSize, domain);
	}
}