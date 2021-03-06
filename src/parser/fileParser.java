package parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ListIterator;

public class fileParser {
	
	/* -- */
	ArrayList<StringBuilder> Tsb=new ArrayList<>();
	private String errorMsg="";
	private boolean errorExist=true;
	
	
	/* -- */
	private void getLineByLine(BufferedInputStream buf,ArrayList<StringBuilder> Tsb) throws IOException {
			StringBuilder sb = null;
			boolean b=true;
			while(true){
				if(b) {sb = new StringBuilder(); b=false;}
				int data = buf.read();
				if(data==-1) break;
				else if((char)data!='\n')
						sb.append((char)data);
				else{ b=true; Tsb.add(sb);  }
			}
	}
	
		
	
    boolean plusMinus = false;
    int plusMinusval = 0;
	private StringBuilder searchInList(ArrayList<StringBuilder> Tsb,String sv){
    	boolean equaled = false;
    	
		ListIterator<StringBuilder> li=Tsb.listIterator();
		StringBuilder s1=null;
		
		while (li.hasNext()) {
			s1=li.next();

			if(s1.indexOf(sv) >= 0)	
			{
		    	equaled = true;
				break;	
			}													
		}
		
		if(equaled == false && !sv.equals("NOTHING")  ){
			  	DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
			    LocalTime lt = LocalTime.parse(sv);
			    String new_sv ;
			    plusMinusval= plusMinusval + 5;

			    if(plusMinus==false)
				 {	new_sv = df.format(lt.plusMinutes(plusMinusval)); plusMinus= true; }
			    else
			     {  new_sv = df.format(lt.minusMinutes(plusMinusval)); plusMinus = false;  }
			    System.out.println(" New sv  : " + new_sv);
			    return searchInList(Tsb, new_sv);			   
		}
		return s1;
	}
	
	
	
	private String getLast(StringBuilder s){
		StringBuilder r=new StringBuilder("");
		for(int i=s.length()-3;s.charAt(i)!=';';i--)
			r.append(s.charAt(i));
		return r.reverse().toString();
	}
	
	String[] getConsomationArray(String[] sv){
		if(!errorExist()) {
			String c[]=new String[sv.length];
			for (int j = 0; j < sv.length; j++)
			{
				plusMinusval = 0;  plusMinus = false; /* initialize for each call */
				c[j]=getLast(searchInList(Tsb, sv[j]));
				System.out.println(" " + sv[j] + " - " + c[j]);
			}
			return c;			
		} else return new String[0];
	}
	
	String[] getConsomation(String...sv){
		return getConsomationArray(sv);
	}
	
	
	/* -- */
	fileParser (String urlChaine){
		boolean be=false;
		try {
			InputStream in; 
			/* 1. first case : if url contains ':'  InputStram = new URL
			 * 2. second case: else InputStream = new FileInputStream ..
			 */
			if(
					urlChaine.indexOf(':')>=0) in= new URL(urlChaine).openStream();
			else in=new FileInputStream(new File(urlChaine));
			BufferedInputStream buf=new BufferedInputStream(in);
			getLineByLine(buf, Tsb);
			setError(false,"");
		} catch (MalformedURLException e) {
			setError(true,e.getMessage());
			be=true;
		} catch (IOException e) {
			setError(true,(be)?errorMsg+" ":""+e.getMessage());
		}
	}
	
	private void setError(boolean errorExist, String errorMsg){
		this.errorExist=errorExist;
		this.errorMsg=errorMsg;
	}

	boolean errorExist(){
		return errorExist;
	}
	
	String errorMsg(){
		return errorMsg;
	}
	
}
