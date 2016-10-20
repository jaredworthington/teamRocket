

//Program for converting daymet csv file to format needed for midterm


import java.lang.*;
import java.util.*;
import java.io.*;

class Converter{

	public static void main(String[] args) throws IOException{

		BufferedReader br;
		BufferedWriter bw;
		try{
			br = new BufferedReader(new FileReader(args[0]));
			bw = new BufferedWriter(new FileWriter("outputWeatherData.csv"));
		

			String line;
			String header = "Year,Month,Day,T_max,T_min,Precip (mm),T_ave,Precip (cm),rh_ave";
			bw.write(header , 0, header.length());
			bw.newLine();
			while ((line=br.readLine())!=null){
				String outLine = "";
				String year = "";
				String month = "";
				String day = "";
				String tMax = "";
				String tMin = "";
				String precipMM = "";
				String tAvg ="";
				String precipCM = "";
				String rhAvg = "";
				String temp = "";

				if(line.charAt(0)!='1'||line.charAt(0)!=2){
					break;
				}
				else{
					int i=0;
					int j=0;
					while(j<line.length()){

						if(line.charAt(j)==','){

							if(i==0){
								year =temp;
							}
							else if(i==1){
								//calculate month and day
								month = "month";
								day = "day";

							}
							else if(i==3){
								//assign mm and calculate cm
								precipMM = temp;
								//parseFloat
								Integer mm = Integer.parseInt(precipMM);
								Integer cm = mm/10;

								precipCM = Integer.toString(cm);
							}
							else if(i==6){
								//assign maxTemp
								tMax = temp;
							}
							else if(i==7){
								//assign minTemp and calculate average temp
								tMin = temp;
								Float t_min = Float.parseFloat(tMin);
								Float t_max = Float.parseFloat(tMax);

								int avgTemp = (int) ((t_min + t_max) / 2);	

								tAvg = Integer.toString(avgTemp);						//parseFloat

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


				}
				String x = year+","+month+","+day+","+tMax+","+tMin+","+precipMM+","+tAvg+","+precipCM+","+rhAvg;
				bw.write(x, 0 , x.length());
				bw.newLine();
			}
		}
		catch(FileNotFoundException e){
			System.out.println("fileNotFound");
		}
	}
}