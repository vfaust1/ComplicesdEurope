#!/bin/bash
export SOURCES=POO/src
export CLASSES=POO/bin
export CLASSPATH=`find lib -name "*.jar" | tr '\n' ':'`

javac -cp ${CLASSPATH} -sourcepath ${SOURCES} -d ${CLASSES} $@ `find POO/src -name "*.java"`
