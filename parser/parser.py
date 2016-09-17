#!/usr/bin/env python
import json
import os

sensorData = []
sensorDataFile = []

def parse(jsonFile, fileName):
	sensorDataFile.append(fileName)
	with open(jsonFile) as f:
		for line in f:
			sensorData.append(json.loads(line))

def printToResultFile():
	print len(sensorData), len(sensorDataFile)
	resultFilePath = os.getcwd() + "/result"
	fileResult = open(resultFilePath, 'w')

	columnsNames = sensorData[0].keys()
	# print columnsNames
	fileResult.write('fileName' + ' ')
	for col in columnsNames:
		fileResult.write(col + ' ')
	fileResult.write('\n')
	for it in range(0, len(sensorData)):
		fileResult.write(sensorDataFile[it] + ' ')
		for key in sensorData[it]:
			fileResult.write(sensorData[it][key] + ' ')
		fileResult.write('\n')

	fileResult.close()
	# print(sensorData)


if __name__ == "__main__":
	# TODO: Need to set correctly rootDir
	rootDir = os.getcwd() + "/Dataset/1920/"
	for dirName, subdirList, fileList in os.walk(rootDir):
		for fName in fileList:
			if fName.endswith(".txt"):
				parse(dirName+"/" + fName, fName[:-4])


	printToResultFile()


