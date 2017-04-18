package parser;


import java.sql.*;

public class test {


	public static void main(String[] args) {
		
		/* set start and end day file name */
		int startday = 170201;
		int endday = 170228;
		
		Connection cnx =  db.cnx();		
		Statement st;
		
		/* Uncomment this bloc of code if you don't need to delete all consomtions data from database */
		 
			Statement st_;		
			 try {
				st_ = cnx.createStatement();
				 st_.executeUpdate("DELETE FROM consomations;");
				 st_.executeUpdate("DELETE FROM consomations_tranches;");
				 System.out.println("Donnée supprimer");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
		 

		 
		for(int compteur = startday; compteur <= endday ; compteur++ ){
			
			int day = compteur;
			fileParser fp = new fileParser("http://www.bmypro-creation.com/reda/parser/min"+compteur+".js");							
	
			try {
				st = cnx.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM tranches");			
				int[] consomation_par_tranche = new int[4]; 
				int[] consomations = new int[4];
				int j=0;
				while(rs.next()){				
					//float priceperunit = rs.getFloat("price-per-unit");
					if(rs.getString("to").equals("NOTHING")){
						String[] consomation = fp.getConsomation(rs.getString("from"),rs.getString("to"));				
						consomation_par_tranche[j]=  Integer.parseInt(consomation[0]);
						consomations[j] = Integer.parseInt(consomation[0]);
						j++;	
						 System.out.println("NOTHING");

					}
					else{
						String[] consomation = fp.getConsomation(rs.getString("from"),rs.getString("to"));				
						consomation_par_tranche[j]= Integer.parseInt(consomation[1]) - Integer.parseInt(consomation[0]);	
						consomations[j] = Integer.parseInt(consomation[0]);
						j++;	
					}					
																	
				}
					 int total = (consomation_par_tranche[0] + consomation_par_tranche[1] + consomation_par_tranche[2] + consomation_par_tranche[3]);
					 st.executeUpdate("INSERT INTO consomations SET id = " + day +" , day = '" + day + "' , t1 = " + consomations[0] + " , t2 = " + consomations[1] + ", t3 = "  + consomations[2] + " , t4 = " + consomations[3] + ", total  = " + total + ";");
					 st.executeUpdate("INSERT INTO consomations_tranches SET id = " + day +" , day = '" + day + "' , t1 = " + consomation_par_tranche[0] + " , t2 = " + consomation_par_tranche[1] + ", t3 = "  + consomation_par_tranche[2] + " , t4 = " + consomation_par_tranche[3] + ", total  = " + total + ";");
					 System.out.println( day + " Enregistrer");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			System.out.println(fp.errorMsg());		
			
		}
		
		
	}

}
