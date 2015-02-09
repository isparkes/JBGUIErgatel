@echo off
cd ..
set CLASSPATH=dist/JBillingGUI.jar;conf;dist/lib/
java -classpath %CLASSPATH% mainForm.JBGUIMainForm
