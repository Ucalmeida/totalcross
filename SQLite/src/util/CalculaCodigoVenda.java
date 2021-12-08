package util;

import java.sql.SQLException;

import dao.VendaDAO;

public class CalculaCodigoVenda {
	
	VendaDAO vendaDAO = new VendaDAO();
	
	public int calculaCodigoVenda() throws SQLException {
		int cdvenda;
		if(!vendaDAO.findAllVendas().isEmpty()) {
			cdvenda = VendaDAO.ultimoCodigoVenda() + 1;
		} else {
			cdvenda = 1;
		}
		return cdvenda;
	}
}
