#!/bin/bash
cd POO/bin
export CLASSPATH=`find ../../lib -name "*.jar" | tr '\n' ':'`
java --module-path ../../lib --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web -cp ${CLASSPATH}:.:../../RenduIHM $@ ProgrammeTerminal
cd ../..

