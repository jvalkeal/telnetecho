# Telnet/Echo Server

Simple example of creating a socket server where clients can connect using raw tcp connection.

## Check Out and Build from Source

1. Clone the repository from GitHub:

		$ git clone git://github.com/jvalkeal/telnetecho.git

2. Navigate into the cloned repository directory:

		$ cd telnetecho

3. The project uses Maven to build:

		$ mvn clean package -DskipTests=true

## Eclipse

To generate Eclipse metadata (.classpath and .project files), use the following Maven task:

	$ mvn eclipse:eclipse

Once complete, you may then import the projects into Eclipse as usual:

	File -> Import -> Existing projects into workspace

## Run Server

After successful Maven build there should be a file structure shown below:

    $ ls target
    classes/  lib/  maven-archiver/  surefire-reports/  telnetecho-1.0-SNAPSHOT.jar  test-classes/
    
Maven build modifies jar manifest and sets Main-Class as com.badtuned.telnetecho.CommandServer. Also files under lib are added to manifest's Class-Path.

Server can be run by simply using it as an executable jar:

    $ java -jar target/telnetecho-1.0-SNAPSHOT.jar
    20:06:54,259  INFO main telnetecho.AbstractServer - Starting server: serverPort=9999
    20:07:32,369  INFO Thread-0 telnetecho.AbstractCommandServer - got connection from 127.0.0.1
    20:07:41,877 DEBUG Thread-0 telnetecho.AbstractCommandServer - Got data:exit

You can connect multiple client to server i.e. using telnet or nc commands:

    $ nc localhost 9999
    Hello. Type 'help' to see supported commands.
    C:\Users\janne#
    exit
    
Server can be bind to different port by giving it as an first argument:

    $ java -jar target/telnetecho-1.0-SNAPSHOT.jar 8888

## Tests

Running build or package without instructions to skip tests will execute all tests.

        $ mvn clean package
