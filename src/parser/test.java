package parser;


import java.sql.*;

public class test {


	public static void main(String[] args) {
		
		/* set start and end day file name */
		int startday = 170201;
		int endday = 170228;
		
		Connection cnx =  db.cnx();		
		Statement st;
		
		/* Uncomment this bloc of code if you don't need to delete all consomtions data from database 
		 
			Statement st_;		
			 try {
				st_ = cnx.createStatement();
				 st_.executeUpdate("DELETE FROM consomations;");
				 System.out.println("Donnée supprimer");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
		 */

		 
		for(int compteur = startday; compteur <= endday ; compteur++ ){
			
			int day = compteur;
			fileParser fp = new fileParser("http://www.bmypro-creation.com/reda/parser/min"+compteur+".js");							
	
			try {
				st = cnx.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM tranches");			
				int[] consomation_par_tranche = new int[3];  int j=0;
				while(rs.next()){				
					//float priceperunit = rs.getFloat("price-per-unit");
					String[] consomation = fp.getConsomation(rs.getString("from"),rs.getString("to"));				
					consomation_par_tranche[j]= Integer.parseInt(consomation[1]) - Integer.parseInt(consomation[0]);
					j++;														
				}
					 int total = (consomation_par_tranche[0] + consomation_par_tranche[1] + consomation_par_tranche[2]);
					 st.executeUpdate("INSERT INTO consomations SET day = '" + day + "' , t1 = " + consomation_par_tranche[0] + " , t2 = " + consomation_par_tranche[1] + ", t3 = "  + consomation_par_tranche[0] + " , total  = " + total + ";");
					 System.out.println( day + " Enregistrer");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			System.out.println(fp.errorMsg());		
			
		}
		
		
	}

}
