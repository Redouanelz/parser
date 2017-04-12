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
		
		fileParser fp=new fileParser("min170201.txt");		
		
		
		String[] consomation=fp.getConsomation("00:00:00","12:00:00","23:55:00");
		
		final int l=consomation.length;
		
		/* get consomation for each time tranche */
		int v[]=new int[l];
		for (int i = 0; i < v.length; i++)
			v[i]=Integer.parseInt(consomation[i]);
		for (int i = 0; i < v.length; i++) System.out.println(v[i]);
		
		if(!fp.errorExist()) System.out.println("-------------------");
		
		/* get consomation beetween each time tranche */
		int t[]=new int[l];
		for (int i = 0; i < t.length; i++)
			if(i==0) t[i]=v[i];
			else  t[i]=v[i]-v[i-1];
		for (int i = 0; i < t.length; i++) System.out.println(t[i]);	
		
		System.out.println(fp.errorMsg());
			
	}

}
