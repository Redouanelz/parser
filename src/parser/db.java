package parser;
import java.sql.*;

	public abstract class db {
			
		private static Connection cnx ;	
		public static Connection cnx() {
			try{
				Class.forName("com.mysql.jdbc.Driver");
				 cnx = DriverManager.getConnection("jdbc:mysql://localhost/parser","root","");
				System.out.println("Connexion bien établie");						
			}
			catch(ClassNotFoundException e)
			{
				System.err.print("Erreur driver"  + e);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				System.err.print("Erreur database");
			}
			return cnx;
		}
			
}

