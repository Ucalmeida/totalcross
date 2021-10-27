package util;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import totalcross.sys.Settings;
import totalcross.sys.Vm;

public class DatabaseManager {

	// É uma classe utilitária que facilita a comunicação com o Banco de Dados SQLite
	public static SQLiteUtil sqliteUtil;
	
	// Em seguida criar um bloco estático que irá inicializar a variável passando como 
	// parâmetros o caminho da aplicação, disponível a partir da variável estática appPath 
	// da classe Settings, e o nome do arquivo SQLite, devendo sempre ser incluída a extensão 
	// do arquivo, “.db”. O corpo do bloco deverá estar dentro de um bloco try-catch, pois existe
	// a possibilidade de ocorrerem erros ao tentar inicializar o SQLiteUtil, e a exceção que 
	// deverá ser tratada vem diretamente do Java, java.sql.SQLException
	static {
		try {
			sqliteUtil = new SQLiteUtil(Settings.appPath, "app.db");
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Em seguida vamos criar um método estático que irá nos retornar uma conexão a partir do sqliteUtil. 
	// O método con() irá retornar apenas uma conexão por instância do SQLiteUtil, então, se a conexão já existir, 
	// a mesma será retornada, se ela não existir uma nova será criada, e será usada nas próximas interações
	public static  Connection getConnection() throws SQLException {
		return sqliteUtil.con();
	}
	
	// Para criar uma tabela, utilizamos a classe Statement do TotalCross
	// Criamos um Statement com o método createStatement() -> Este método irá preparar 
	// todo o necessário para que possamos executar nossas consultas e persistências
	// Através do método execute() da classe Statement iremos executar a persistência 
	// de uma nova tabela, Sorvetes por exemplo
	// Como parâmetro enviamos o SQL para a criação da tabela
	public static void loadTabelas() throws SQLException {
		Statement st = getConnection().createStatement();
		st.execute("CREATE TABLE IF NOT EXISTS SORVETES(SABOR VARCHAR, VALOR NUMERIC, ESTOQUE NUMERIC)");
		st.close();
	}
}
