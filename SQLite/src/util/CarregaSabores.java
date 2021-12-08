package util;

import java.sql.SQLException;
import java.util.List;

import dao.SorveteDAO;
import domain.Sorvete;
import totalcross.sys.Vm;

public class CarregaSabores {
	
	private String[] sorvetesArray;
	List<Sorvete> sorveteList;
	
	public void loadSabores() {
		
		SorveteDAO sorveteDAO = new SorveteDAO();
		
		try {
			sorveteList = sorveteDAO.findAllSaborSorvetes();
			sorvetesArray = new String[sorveteList.size()];
			int dadosIndex = 0;
			for(Sorvete sorvete : sorveteList) {
				String[] dados = sorveteToArray(sorvete);
				sorvetesArray[dadosIndex] = dados[0];
				dadosIndex++;
			}
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
		}
		
	}
	
	private String[] sorveteToArray(Sorvete sorvete) {
		
		String[] dadosArray = new String[1];
		
		dadosArray[0] = sorvete.sabor;
		
		return dadosArray;
	}
	
	public String[] getSorvetesArray() {
		return sorvetesArray;
	}
}
