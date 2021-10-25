package service;

import java.util.ArrayList;
import java.util.List;

public class CarroService {

	public List<String[]> instantiateCarros() {
		List<String[]> carrosList = new ArrayList<>();
		carrosList.add(new String[] {"Ford", "K�", "Branco", "", "", "@foto1"});
		carrosList.add(new String[] {"Ford", "Fiesta", "Vermelho", "", "", ""});
		carrosList.add(new String[] {"Ford", "Focus", "Preto", "", "", ""});
		carrosList.add(new String[] {"Ford", "Fusion", "Prata", "", "", ""});
		carrosList.add(new String[] {"Ford", "Ranger", "Azul", "", "", ""});
		carrosList.add(new String[] {"Chevrolet", "Onix", "Prata", "", "", ""});
		carrosList.add(new String[] {"Chevrolet", "Prisma", "Branco", "", "", ""});
		carrosList.add(new String[] {"Chevrolet", "Cruze", "Preto", "", "", ""});
		return carrosList;
	}
	
	public List<String[]> instantiateCarrosSummary() {
		List<String[]> carrosList = new ArrayList<>();
		carrosList.add(new String[] {"Ford", "K�", "Branco"});
		carrosList.add(new String[] {"Ford", "Fiesta", "Vermelho"});
		carrosList.add(new String[] {"Ford", "Focus", "Preto"});
		carrosList.add(new String[] {"Ford", "Fusion", "Prata"});
		carrosList.add(new String[] {"Ford", "Ranger", "Azul"});
		carrosList.add(new String[] {"Chevrolet", "Onix", "Prata"});
		carrosList.add(new String[] {"Chevrolet", "Prisma", "Branco"});
		carrosList.add(new String[] {"Chevrolet", "Cruze", "Preto"});
		return carrosList;
	}
	
	public List<String[]> instantiateHorarios() {
		List<String[]> horariosList = new ArrayList<>();
		horariosList.add(new String[] {"Dia: 12/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 12/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 12/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 12/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 13/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 13/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 13/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		horariosList.add(new String[] {"Dia: 14/06/2019", "Hor�rio: 08:00", "Previs�o: 09:00"});
		return horariosList;
	}
	
	public String[] getAgendaVazia() {
		return new String[] {"Dia: � confirmar", "Hor�rio: � confirmar", "Previs�o: sem previs�o"};
	}
}
