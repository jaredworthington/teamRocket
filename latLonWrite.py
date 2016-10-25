"""
Appends latitude and longitude onto the result CSVs from Joceline's code
"""

import os
import csv

if not os.path.exists('Output'):
    os.makedirs('Output')

 
DAYMET_PATH='daymetData'

for f in os.listdir(DAYMET_PATH):
    fn = f.split('_')
    lat = fn[0]
    lon = fn[1]
    with open("{}/{}".format(DAYMET_PATH, f),mode='r')as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            dir_name = ('Output/' + row[0])
            #dir_name = ('Output\\' + row[0])
            if not os.path.exists(dir_name):
                os.makedirs(dir_name)
                
            day_file = open(os.path.join(dir_name, row[0]+"_"+row[1]+"_"+row[2]+".csv"), "a")
            day_data = [fn[0], fn[1], row[3], row[4], row[5], row[6], row[7], row[8], row[9]]
            day_file.write(','.join(day_data))
            day_file.write('\n')

           # day_file.write((",".join(day_data)))
            day_file.close()


			
		
