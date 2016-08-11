# ticketservice

for gradlew command use './gradlew' for linux/mac and 'gradlew.bat' for windows

To execute the code and run the tests download the files into a local directory
and run for

Tests:

>gradlew test

Executable jar:

>gradlew assemble

Then a build directory should be generated. In 'build/distributions' there is a
zipped directory named 'ticketservice-1.0-SNAPSHOT.zip'. Unzip it and in the
bin directory there should be two executables with the name ticketservice.
If you are on windows run "ticketservice.bat", on mac and linux
use the other. This should run a command line program which is the ticket service.