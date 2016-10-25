import java.io.*;
import java.util.*;
import java.lang.*;

class Converter{

	public static void main(String[] args) throws IOException{

		BufferedReader br;
		BufferedWriter bw;
		String outputLine = "";
		String header = "Year,Month,Day,T_max,T_min,Precip (mm),T_ave,Precip (cm),rh_ave";
		try{
			br = new BufferedReader(new FileReader(args[0]));
		

			String line;
			
			
			System.out.println(header);
		
			line = br.readLine();
			while (line!=null){
				String year = "";
				String month = "";
				String day = "";
				String tMax = "";
				String tMin = "";
				String precipMM = "";
				String tAvg ="";
				String precipCM = "";
				String rhAvg = "rhAvg";
				String temp = "";
				String vp = "";

				if(line.length()==0){
					//do nothing
				}
				else if(line.charAt(0)!='1'&& line.charAt(0)!='2'){
					//do nothing
				}
				else{
					int i=0;
					int j=0;
					while(j<=line.length()){
						
						if(j== line.length()){
							vp = temp;
						
							Double vpD = Double.parseDouble(vp);

   			 			//---------------------------------------------------------------
						//-----Calculate relative humidity for the day-------------------
							Double t_min = Double.parseDouble(tMin);
							Double t_max = Double.parseDouble(tMax);
							Double avgTemp =  ((t_min + t_max) / 2);
							
							Double up = (7.5*avgTemp)/(237.3+avgTemp);
							Double u = Math.pow(10.0, up);
							Double rh = 100*(vpD/u);

							long f = rh.longValue();
				 			if(rh== f){
								rhAvg = String.format("%d",f);
							}
							else{
				 				rhAvg = String.format("%s", rh);						//parseFloat
		       		 		}
	       		 		//--------------------------------------------------------------


						}
					
						else if(line.charAt(j)==','){

							if(i==0){
								year =temp;
							}
							else if(i==1){
								//calculate month and day
								String doy = temp;
								Calendar cal = Calendar.getInstance();
								cal.set(Calendar.YEAR, Integer.parseInt(year));
								cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(doy));

								int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
								int m = cal.get(Calendar.MONTH);
								month = Integer.toString(m+1);
								day = Integer.toString(dayOfMonth);

							}
							else if(i==3){
								//assign mm and calculate cm
								precipMM = temp;
								//parseFloat
								Double mm = Double.parseDouble(precipMM);
								Double cm = (mm/10);

								//format the doubles
								long u = cm.longValue();
								if(cm == u){
        							precipCM = String.format("%d", u);
        						}
   								else{
        							precipCM =
        							  String.format("%s", cm);
								}	

								u = mm.longValue();
       			 				if(mm == u){
       								precipMM = String.format("%d",u);
								}
    							else{
       			 					precipMM = String.format("%s", mm);						//parseFloat
       			 				}


							}
							else if(i==6){
								//assign maxTemp
								tMax = temp;
							}
							else if(i==7){
								//assign minTemp and calculate average temp
								tMin = temp;
								Double t_min = Double.parseDouble(tMin);
								Double t_max = Double.parseDouble(tMax);

								Double avgTemp =  ((t_min + t_max) / 2);	

								long u = avgTemp.longValue();
								if(avgTemp == u){
       								tAvg = String.format("%d",u);
								}
    							else{
       			 					tAvg = String.format("%s", avgTemp);						//parseFloat
       			 				}

       			 				 u = t_min.longValue();
       			 				if(t_min == u){
       								tMin = String.format("%d",u);
								}
    							else{
       			 					tMin = String.format("%s", t_min);						//parseFloat
       			 				}

       			 				 u = t_max.longValue();
       			 				if(t_max == u){
       								tMax = String.format("%d",u);
								}
    							else{
       			 					tMax = String.format("%s", t_max);						//parseFloat
       			 				}

							}
						
							temp = "";
							i++;
						}
						
						else{
							String c = Character.toString(line.charAt(j));
							temp = temp + c;
						}
						j++;
					}

					String x = year+","+month+","+day+","+tMax+","+tMin+","+precipMM+","+tAvg+","+precipCM+","+rhAvg;
					outputLine = outputLine + x + '\n';
				
				}
				
				

				line = br.readLine();
			}
		}
		catch(FileNotFoundException e){
			System.out.println("fileNotFound");
		}

		bw = new BufferedWriter(new FileWriter(args[0]));
		bw.write(header , 0, header.length());
		bw.newLine();
		bw.write(outputLine,0,outputLine.length());
	
	}
}
