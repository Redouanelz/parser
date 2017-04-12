package parser;


public class test {

	public static void main(String[] args) {
		
		 /* PART 1 (Constuctor)
		 * 1.  get an url string 
		 * 2. Check if there is ':' the geted string is an url else its a file 
		 * 3. call getLineByLine
		 * 
		 * PART 2 (geLineByLine function)
		 * 1. get the buf and fill the stringBuilder with line by line data from the geted file
		 * 
		 * PART 3 (getConsomation function)
		 * 1. pass array of strings as parameters ( tranches of time )
		 * 2. for each passed parameter we call searchInList function
		 * 
		 * PART 4 (searchInList function)
		 * 1. search if exist in the list then get last part of the string after the ';' = consomation for the searched tranche
		 * */
		
		fileParser fp=new fileParser("http://www.bmypro-creation.com/reda/min170201.js");				
		
		String[] consomation = fp.getConsomation("00:00:00","12:00:00","23:55:00");
				
		/* get consomation for each time tranche */
		System.out.println("-- Consomations for each tranche --");
		System.out.println("Tranche 1 [00:00:00] :" + consomation[0]);
		System.out.println("Tranche 2 [12:00:00] :" + consomation[1]);
		System.out.println("Tranche 3 [23:55:00] :" + consomation[2]);
		
		/* get consomation beetween each time tranche */
		System.out.println("-- Consomations between  --");
		System.out.println("between [00:00:00] and  [00:00:00] :"  + consomation[0]);
		System.out.println("between [00:00:00] and  [12:00:00] :"  + ( Integer.parseInt(consomation[1]) - Integer.parseInt(consomation[0]) ));
		System.out.println("between [12:00:00] and  [23:55:00] :" + ( Integer.parseInt(consomation[2]) - Integer.parseInt(consomation[1]) ));
		System.out.println("between [23:55:00] and  [23:55:00] :" + consomation[2]);
				
		
	
		
		System.out.println(fp.errorMsg());
			
	}

}
