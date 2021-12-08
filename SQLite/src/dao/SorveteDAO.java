package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Sorvete;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.sys.Vm;
import util.DatabaseManager;

public class SorveteDAO {

	// Para fazer a ponte entre a interface e o Banco de Dados,
	// temos essa classe DAO, manipulando corretamente os dados que precisam
	// ser inseridos/consultados/atualizados
	
	// Nela vamos criar um método para a inserção de um registro de sorvete, 
	// recebendo como parâmetro o sorvete desejado. Dentro do método iremos 
	// criar um PreparedStatement() com a query e valores que desejamos inserir. 
	// Para isso utilizamos o método estático getConnection() da nossa classe DatabaseManager. 
	// Ao escrever a query do PreparedStatement() escrevemos sinais de interrogação, onde 
	// normalmente iriam os valores de um insert. Em seguida setamos os valores de cada 
	// posição com os métodos corretos, que fazem o tratamento dos tipos de dados para o banco. 
	// Para Strings utilizamos preparedStatement.setString(posicao, valor), para Doubles, 
	// preparedStatement.setDouble(posicao, valor), e assim de acordo com cada tipo de dado
	public boolean insertSorvete(Sorvete sorvete) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("INSERT INTO SORVETES VALUES (?, ?, ?)");
		ps.setString(1, sorvete.sabor);
		ps.setDouble(2, sorvete.valor);
		ps.setDouble(3, sorvete.estoque);
		
		// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement que criamos
		// Este método retorna um inteiro com a quantidade de inserções realizadas, de acordo com a query
		int inserted = ps.executeUpdate();
		
		// Não esquecer de fechar o PreparedStatement
		ps.close();
		
		// Podemos assim retornar um booleano
		return inserted > 0;
	}
	
	// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement
	public boolean atualizarSorvete(Sorvete sorvete) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("UPDATE SORVETES SET SABOR = ?, VALOR = ?, ESTOQUE = ? WHERE SABOR = ?");
		ps.setString(1, sorvete.sabor);
		ps.setDouble(2, sorvete.valor);
		ps.setDouble(3, sorvete.estoque);
		ps.setString(4, sorvete.sabor);
		
		int updated = ps.executeUpdate();
		ps.close();
		
		return updated > 0;
	}
	
	// Para executar a inserção utilizamos o método executeUpdate() do objeto PreparedStatement
	public boolean excluirSorvete(Sorvete sorvete) throws SQLException {
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement("DELETE FROM SORVETES WHERE SABOR = ?");
		ps.setString(1, sorvete.sabor);
		
		int excluded = ps.executeUpdate();
		ps.close();
		
		return excluded > 0;
	}
	
	// Para fazer a busca dos registros dos sorvetes inseridos vamos criar um método que fará uma busca e trará todos 
	// os sorvetes que estiverem no banco
	public List<Sorvete> findAllSorvetes() throws SQLException {
		// Vamos criar uma lista para armazenar objetos do tipo Sorvete
		List<Sorvete> sorveteList = new ArrayList<>();
		 try {
			 Statement st = DatabaseManager.getConnection().createStatement();
			 // Utilizamos o método executeQuery() da classe Statement, enviando a query desejada
			 // O retorno desse método é um ResultSet que DEVE ser percorrido para tratar corretamente
			 // os registros da busca
			 ResultSet rs = st.executeQuery("SELECT SABOR, VALOR, ESTOQUE FROM SORVETES");
			 
			 // Com a lista de objetos do tipo Sorvete, vamos montar cada objeto
			 // no laço de repetição do ResultSet, utilizando o método next() do mesmo para realizar o loop
			 while(rs.next()) {
				 Sorvete sorvete = new Sorvete();
				 // O ResultSet possui métodos para obter cada tipo de dado
				 // e devemos ter o cuidado de utilizar o método correto para cada coluna do banco
				 // Os métodos recebem como parâmetro, o nome da coluna desejada, ou o seu índice na busca
				 // Pelo nome é mais prático, mas pelo índice é mais performático
				 sorvete.sabor = rs.getString(1);
				 sorvete.valor = rs.getDouble(2);
				 sorvete.estoque = rs.getDouble(3);
				 sorveteList.add(sorvete);
				 // Instanciamos um objeto Sorvete dentro do loop, e atribuímos à cada variável, 
				 // a coluna correspondente. Em seguida adicionamos o sorvete à nossa lista
			 }
			 // Por fim devemos fechar o Statement e o ResultSet com seus respectivos métodos close() 
			 // para liberar os recursos alocados, e também retornar a lista de sorvetes
			 st.close();
			 rs.close();
			 return sorveteList;
		 } catch(Exception e) {
			 Vm.debug(e.getMessage());
			 throw e;
		 }
	}
	
	public List<Sorvete> findAllSaborSorvetes() throws SQLException {
		List<Sorvete> sorveteList = new ArrayList<>();
		 try {
			 Statement st = DatabaseManager.getConnection().createStatement();
			 ResultSet rs = st.executeQuery("SELECT SABOR FROM SORVETES");
			 
			 while(rs.next()) {
				 Sorvete sorvete = new Sorvete();
				 sorvete.sabor = rs.getString(1);
				 sorveteList.add(sorvete);
			 }
			 st.close();
			 rs.close();
			 return sorveteList;
		 } catch(Exception e) {
			 Vm.debug(e.getMessage());
			 throw e;
		 }
	}
}