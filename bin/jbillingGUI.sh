#!/bin/bash

cd ..
export CLASSPATH=dist/JBillingGUI.jar:conf:dist/lib/
java -classpath $CLASSPATH mainForm.JBGUIMainForm
