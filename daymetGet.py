"""
Downloads all single point data Daymet CSVs for a given area defined by
the areas most North-Western (upper left or ul) and it's most South-
Eastern (bottom right or br) coordinates. Makes a request lat/lonStep times
between each value respectively and saves the CSV in a file with the title:
	[Latitude]-[Longitude]-[StartYear]-[EndYear]
	*These values may not be accurate if the years entered are not in series.

Parameters:
	ul - The North-Western point of the area
		type: tuple
		format: (latitute,longitude)
		ex:     (25.123, 10.222)
	br - The South-Eastern point of the area
		type: tuple
		format: (latitute,longitude)
		ex:     (25.123, 10.222)
	years - The years for which to gather data.
		type: string
		format: "year1,year2,.."
		ex:     "1980,1981,1982"

	
Coordinate info for Flagstaff:
		     lat	lon
	Upper Left: 35.24374, -111.7041
	Bottom Right: 35.11915, -111.5063
	

Added: run each downloaded file through converter and then mols
"""
import requests
import os
import MoLS
import matlab

#coordinates for your city, one upper left, one bottom right(set to flagstaff)

ul = (35.24374,-111.7041)
br = (35.11915,-111.5063)
years =  "2015"#"1980,1981,1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015"
#the number of steps in the lat/lon difference, we rounded up to make sure 
# we would not step over any square kilometers.
latStep = 9
lonStep = 12

#the change in lat and lon values, for incrementing
deltaLat = (ul[0]-br[0])/latStep
deltaLon = (ul[1]-br[1])/lonStep

# Setup the request url and parameters
url = 'https://daymet.ornl.gov/data/send/saveData'
# these values are passed along as parameters in the 
# http request 
payload = {
		'lat':br[0],
		'lon':br[1],
		'year':years,
	}
# create a directory for the downloaded data
dataDir = "daymetData";
if not os.path.exists('daymetData'):
	os.makedirs('daymetData')
"""
Loop through the grid of lat,lon to get all of the 1km^2 daymet csvs
"""
#compile the converter program
os.system("javac Converter.java")

#initialize mols
for latitude in range(0,latStep):
	for longitude in range(0,lonStep):
		# Get the first and last year and build a string in formay yyyy-yyyy for creating a filename
		#This doesn't work as intended for series with gaps, but still helps organize a bit
		# prepend lat-lon to the filename 
		filenameYears = payload['year'].split(',')
		filename = str(payload['lat'])+'_'+str(payload['lon'])+'_'+filenameYears[0]+'-'+filenameYears[-1]+'.csv'
		if os.path.isfile(dataDir+'/'+filename):
			print("File "+dataDir+'/'+filename+" already exists.")
		else:
			csv = requests.get(url, params=payload)
			"""
				UNCOMMENT/comment to toggle print of filenames as written
			"""
			print("Writing to "+dataDir+'/'+filename)
			#create the new csv
		
			f = open(dataDir+'/'+filename,'w')
			#write the csv
			print(csv.text,file = f)

		payload['lon'] += deltaLon #increment longitude
	payload['lon'] = br[1] #reset latitude
	payload['lat'] += deltaLat #increment latitude

"""
   Convert all the daymet CSVs into usable data for MoLS
   
"""
for f in os.listdir(dataDir):
	os.system("java Converter "+dataDir+'/'+f)

"""
   Iterate through the converted csvs and run them through
   MoLS.
"""
modeler = MoLS.initialize();
for f in os.listdir(dataDir):
	#modeler.MoLS('./Weather','Test_Data',nargout=0)
	filename = f[:-4]
	modeler.MoLS('./'+dataDir,filename,nargout=0)
modeler.terminate()

