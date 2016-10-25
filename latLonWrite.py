"""
Appends latitude and longitude onto the result CSVs from Joceline's code
"""

import os
import csv


DAYMET_PATH='daymetData'

for f in os.listdir(DAYMET_PATH):
	fn = f.split('_')
	lat = fn[0]
	lon = fn[1]
	with open("{}/{}".format(DAYMET_PATH, f),mode='r')as csvfile:
		reader = csv.reader(csvfile)
		for row in reader:
			
		
