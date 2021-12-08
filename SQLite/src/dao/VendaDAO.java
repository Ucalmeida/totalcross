package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Venda;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;
import util.DatabaseManager;

public class VendaDAO {
	
	public boolean insertVenda(Venda venda) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO VENDAS VALUES (?, ?, ?)");
		ps.setInt(1, venda.cdvenda);
		ps.setDouble(2, venda.valorTotal);
		ps.setDouble(3, venda.qtdVendida);
		
		int inserted = ps.executeUpdate();
		
		ps.close();
		
		return inserted > 0;
	}
	
	// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement
	public boolean atualizarVenda(Venda venda) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE VENDAS SET VALORTOTAL = ?, QTOTALVENDIDA = ? WHERE CDVENDA = ?");
		ps.setDouble(1, venda.valorTotal);
		ps.setDouble(2, venda.qtdVendida);
		ps.setInt(3, venda.cdvenda);
		
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
	}
	
	// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement
	public boolean excluirVenda(Venda venda) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("DELETE FROM VENDAS WHERE CDVENDA = ?");
		ps.setInt(1, venda.cdvenda);
		
		int excluded = ps.executeUpdate();
		ps.close();
		
		return excluded > 0;
	}
	
	public static int ultimoCodigoVenda() throws SQLException {
		int cdvenda;
		
		Statement st = DatabaseManager.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT MAX(CDVENDA) AS CDVENDA FROM VENDAS");
		
		cdvenda = rs.getInt("CDVENDA");
		st.close();
		rs.close();
		return cdvenda;
	}
	
	public List<Venda> findAllVendas() throws SQLException {
		List<Venda> vendaList = new ArrayList<>();
		try {
			Statement st = DatabaseManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT CDVENDA, VALORTOTAL, QTOTALVENDIDA FROM VENDAS");
			
			while(rs.next()) {
				Venda venda = new Venda();
				venda.cdvenda = rs.getInt(1);
				venda.valorTotal = rs.getDouble(2);
				venda.qtdVendida = rs.getDouble(3);
				vendaList.add(venda);
			}
			
			st.close();
			rs.close();
			return vendaList;
			
		} catch (Exception e) {
			Vm.debug(e.getMessage());
			throw e;
		}
	}
}
