# file-sorting
The test task for CFT school of Java (http://start.cft.ru/.) is done by mail@valery.tech.

Class to run is Conveyor which uses Options class as a place 
to keep all current settings. Options.process does all neccessary steps 
for sorting input data. 

There is jar (file-sorting-1.0.jar) builded for GNU/Debian in build/libs. 

Start program example: 
java -jar build/libs/file-sorting-1.0.jar in3.txt out.txt -i -a

If file with income data (option in.file) contained values which cannot be converted
into right values with accordance with current type 
then Conveyor writes to log a number of such values. 
