#!/bin/bash

COMPILER_PROF="~/unizar/procesadores-lenguajes/alike/alike.jar"
COMPILER_USER="./dist/alike_4.jar"
PTOOLS="./pcode_tools/linux"


if [ "$1" == "-p" ]
then
	shift
	rm "$1".pcode "$1".x
	if ! java -jar "$COMPILER_PROF" "$1" 
	then
		exit 1
	fi
elif [ "$1" == "-u" ]
then
	if ! ant ; then
		echo "error: ant failed"
		exit 1
	fi
	shift
	rm "$1".pcode "$1".x
	if ! java -jar "$COMPILER_USER" "$1" 
	then
		exit 1
	fi
else 
	echo "USO: $0 [ -p | -u ] <fichero-sin-extension>"
	echo "       -p: compila con el compilador de los profesores"
	echo "       -u: compila con el compilador de los alumnos"
	exit 3
fi


if ! "$PTOOLS"/ensamblador "$1" 
then
	exit 2
fi


"$PTOOLS"/maquinap "$1"
