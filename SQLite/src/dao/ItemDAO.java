package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Item;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;
import util.DatabaseManager;

public class ItemDAO {
	
	public boolean insertItem(Item item) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO ITENS VALUES (?, ?, ?, ?, ?)");
		ps.setInt(1, item.cditem);
		ps.setInt(2, item.cdvenda);
		ps.setString(3, item.sabor);
		ps.setDouble(4, item.totalVendido);
		ps.setDouble(5, item.qtdTotalVendida);
		
		int inserted = ps.executeUpdate();
		
		ps.close();
		
		return inserted > 0;
	}
	
	// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement
	public boolean atualizarItem(Item item) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE ITENS SET SABOR = ?, VALORTOTAL = ?, QTOTALVENDIDA = ? WHERE CDITEM = ?");
		ps.setString(1, item.sabor);
		ps.setDouble(2, item.totalVendido);
		ps.setDouble(3, item.qtdTotalVendida);
		ps.setInt(4, item.cditem);
		
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
	}
	
	// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement
	public boolean excluirItem(Item item) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("DELETE FROM ITENS WHERE CDITEM = ?");
		ps.setInt(1, item.cditem);
		
		int excluded = ps.executeUpdate();
		ps.close();
		
		return excluded > 0;
	}
	
	public static Integer ultimoCodigoItem() throws Exception {
		Integer cditem;
		try {
			Statement st = DatabaseManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(CDITEM) AS CDITEM FROM ITENS");
			
			cditem = rs.getInt("CDITEM");
			st.close();
			rs.close();
			return cditem;
		} catch(Exception e) {
			Vm.debug(e.getMessage());
			throw e;
		}
	}
	
	public List<Item> findAllItens() throws SQLException {
		List<Item> itemList = new ArrayList<>();
		 try {
			 Statement st = DatabaseManager.getConnection().createStatement();
			 ResultSet rs = st.executeQuery("SELECT CDITEM, CDVENDA, SABOR, VALORTOTAL, QTOTALVENDIDA FROM ITENS");
			 
			 while(rs.next()) {
				 Item item = new Item();
				 
				 item.cditem = rs.getInt(1);
				 item.cdvenda = rs.getInt(2);
				 item.sabor = rs.getString(3);
				 item.qtdTotalVendida = rs.getDouble(4);
				 item.totalVendido = rs.getDouble(5);
				 itemList.add(item);
			 }
			 st.close();
			 rs.close();
			 return itemList;
		 } catch(Exception e) {
			 Vm.debug(e.getMessage());
			 throw e;
		 }
	}

	public List<Item> findItensByVenda(int cdvenda) throws SQLException {
		List<Item> itemList = new ArrayList<>();
		 try {
			 Statement st = DatabaseManager.getConnection().createStatement();
			 ResultSet rs  = st.executeQuery("SELECT CDITEM, CDVENDA, SABOR, VALORTOTAL, QTOTALVENDIDA FROM ITENS WHERE CDVENDA = " + cdvenda);
			 
			 while(rs.next()) {
				 Item item = new Item();
				 item.cditem = rs.getInt(1);
				 item.cdvenda = rs.getInt(2);
				 item.sabor = rs.getString(3);
				 item.totalVendido = rs.getDouble(4);
				 item.qtdTotalVendida = rs.getDouble(5);
				 itemList.add(item);
			 }

			 st.close();
			 rs.close();
			 return itemList;
		 } catch(Exception e) {
			 Vm.debug(e.getMessage());
			 throw e;
		 }
	}
}
