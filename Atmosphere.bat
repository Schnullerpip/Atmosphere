rmdir tmp /s /q
mkdir tmp
start /B java -J-Xmx8144M -J-d64 Atmosphere.jar
start "" http://localhost:50777