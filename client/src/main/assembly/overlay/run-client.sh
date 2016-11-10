#!/bin/bash

java -cp 'lib/jars/*'  -Dname='52055-52108'  -Dpass=52055-52108  -Daddresses='127.0.0.1'   "mbaracus.client.Client" $*

