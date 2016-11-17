#!/bin/bash

java -cp 'lib/jars/*' "mbaracus.Client" -Dgroup="52055-52108"  -Dpass="52055-52108"  -Daddresses="127.0.0.1" -DinPath="csv/dataset-1000.csv" -DoutPath="out.txt" -Dquery=1 $*

