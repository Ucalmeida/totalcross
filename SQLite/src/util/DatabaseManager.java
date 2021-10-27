package util;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import totalcross.sys.Settings;
import totalcross.sys.Vm;

public class DatabaseManager {

	// � uma classe utilit�ria que facilita a comunica��o com o Banco de Dados SQLite
	public static SQLiteUtil sqliteUtil;
	
	// Em seguida criar um bloco est�tico que ir� inicializar a vari�vel passando como 
	// par�metros o caminho da aplica��o, dispon�vel a partir da vari�vel est�tica appPath 
	// da classe Settings, e o nome do arquivo SQLite, devendo sempre ser inclu�da a extens�o 
	// do arquivo, �.db�. O corpo do bloco dever� estar dentro de um bloco try-catch, pois existe
	// a possibilidade de ocorrerem erros ao tentar inicializar o SQLiteUtil, e a exce��o que 
	// dever� ser tratada vem diretamente do Java, java.sql.SQLException
	static {
		try {
			sqliteUtil = new SQLiteUtil(Settings.appPath, "app.db");
		} catch (SQLException e) {
			Vm.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Em seguida vamos criar um m�todo est�tico que ir� nos retornar uma conex�o a partir do sqliteUtil. 
	// O m�todo con() ir� retornar apenas uma conex�o por inst�ncia do SQLiteUtil, ent�o, se a conex�o j� existir, 
	// a mesma ser� retornada, se ela n�o existir uma nova ser� criada, e ser� usada nas pr�ximas intera��es
	public static  Connection getConnection() throws SQLException {
		return sqliteUtil.con();
	}
	
	// Para criar uma tabela, utilizamos a classe Statement do TotalCross
	// Criamos um Statement com o m�todo createStatement() -> Este m�todo ir� preparar 
	// todo o necess�rio para que possamos executar nossas consultas e persist�ncias
	// Atrav�s do m�todo execute() da classe Statement iremos executar a persist�ncia 
	// de uma nova tabela, Sorvetes por exemplo
	// Como par�metro enviamos o SQL para a cria��o da tabela
	public static void loadTabelas() throws SQLException {
		Statement st = getConnection().createStatement();
		st.execute("CREATE TABLE IF NOT EXISTS SORVETES(SABOR VARCHAR, VALOR NUMERIC, ESTOQUE NUMERIC)");
		st.close();
	}
}
