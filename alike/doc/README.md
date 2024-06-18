# PRACTICA 4 - PROCESADORES DE LANGUAJES - 2023/2024

## Lenguaje ALIKE

#### Authors: Diego Roldán (841723) && Abel Romeo (846088)




Se debe cambiar la linea 66 de build.xml para ajustar javacchome.
Se deben modificar las rutas a los compiladores y a las ptools en el script ```execute.sh```.

Ejecutar para compilar y ejecutar con el compilador que se elija (alumnos o profesores):
```
chmod u+x execute.sh
./execute.sh [-u|-p] <ruta-al-fichero-sin-extension>
```	 
-    -p para compilar con el compilador de los profesores
-    -u para compilar con el compilador de los alumnos


<br>  Para compilar y ejecutar todos los archivos con extension ```.al``` del directorio de tests: 

```
chmod u+x testall.sh
./testall.sh
```
*cambiar ruta en el script si el directorio de tests no es ```../test```*

<br>  Para compilar manualmente:

```
ant
java -jar ./dist/alike_4.jar <ruta-al-fichero-sin-extension>
```

<br>  Para ejecutar manualmente:

```
pcode_tools/<OS>/ensamblador <ruta-a-fichero-sin-extension>
pcode_tools/<OS>/maquinap <ruta-a-fichero-sin-extension>
```

> Cambiar ```<OS>``` por ```linux``` o ```sunos``` o ```macos``` según el sistema operativo.

<br> Se ha realizado el nivel 4 de compilador. El lenguaje permite el uso de 
parámetros escalares y de vectores, tanto por valor como por referencia 
en procedimientos y funciones.

<br> 

###### Extras: 

- Se ha implementado el reconocimiento de caracteres especiales (e.g. á, é, ñ, etc.).
