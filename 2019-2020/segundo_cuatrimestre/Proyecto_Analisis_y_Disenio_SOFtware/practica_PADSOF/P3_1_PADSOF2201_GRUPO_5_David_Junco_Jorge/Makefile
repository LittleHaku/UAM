generate_documentation:
	@rm -fR doc/
	javadoc -d doc -author  -version -noqualifier java.lang  -classpath  /usr/share/java/junit4.jar:grantsGateway-1.0-SNAPSHOT.jar src/*/*.java

compile:
	@rm -fR bin/
	@mkdir bin
	javac -d bin -classpath  /usr/share/java/junit4.jar:grantsGateway-1.0-SNAPSHOT.jar src/*/*.java

run:
	@cp -a es bin/
	@cd bin;java demostracionMain.Main 


clean:
	rm -rf src/*/*.class
