# JavaRFIDConnector
Use Java and OctaneSDK to connect RFID tag information to a PostgreSQL Database

## Hardware
### You will need
1. An Impinj RFID reader hub. We are using the Impinj Speedway Revolution
2. 1-4 RFID Antennas which are compatible with the Revolution

### Setup
1. Plug the Revolution into an AC outlet
2. Plug an ethernet cable into the ethernet port of the Revolution. Put the other end of the ethernet into the computer running the JavaRFIDConnector
  * You should notice an "Unidentified LAN network" under your network connections
  1. Open your network connnection settings 
  2. Right click the unidentified LAN network and click properties
  3. High light Internet Protocol Version 4 (TCP/IPv4) in the Networking tab
  4. Click Properties
  5. Checkmark "Use the following IP address:
    1. In the IP address box type the IP of the Revolution
    2. In the Subnet mask box type - 255.255.255.0
    3. Click Ok
  Click Ok on Local Area Connection Properties
3. Screw the RFID Antenna(s) into the Revolution
4. The Revolution's LEDs should be Green (consult the Impinj Revolution user guide for more information)
  * https://support.impinj.com/hc/en-us/article_attachments/201052867/SpeedwayR_installation_and_operations_guide.pdf

## Software 
1. Download and install Eclipse - https://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr1a
2. Download OctaneSDK for Java - https://support.impinj.com/hc/en-us/articles/202755268-Octane-SDK#java
  * Unzip OctaneSDK into a directory of your choosing
3. Download PostgreSQL JDBC package - https://jdbc.postgresql.org/download/postgresql-9.4-1200.jdbc41.jar
  * Place the .jar file in a directory of your choosing
4. Clone JavaRFIDConnector to your desktop
5. Open up Eclipse
6. Click on File at the top of the screen
7. Click on import... on the dropdown menu
8. Click on Git as your import source  
  1. Double click Projects from Git
  2. Double click Existing local repository
  3. Click Add..
  4. Click on Browse.. and navigate to the directory you cloned JavaRFIDConnector 
  5. Click Ok and you should see something like "..\JavaRFIDConnector\.git"
  6. Make sure the checkbox is checked for the JavaRFIDConnector and hit Finish
9. Now you should see an entry for the JavaRFIDConnector. Double click it
  1. Select Import as general project and click next
  2. The name should be JavaRFIDConnector so click Finish
10. Navigate to the location of the imported JavaRFIDConnector - ..\JavaRFIDConnector
  1. Open up the .project file with a text editor (like Notepad)
  2. You should see something like 
  ```
  <projectDescription>
	<name>JavaRFIDConnector</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
	</buildSpec>
	<natures>
	</natures>
</projectDescription>
```
Change it to look like
  ```
  <projectDescription>
	<name>JavaRFIDConnector</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.jdt.core.javanature</nature>
	</natures>
</projectDescription>
```
And then save the .project file
11. Now in the Eclipse window right click JavaRFIDConnector and click Refresh on the drop down menu
  * Double click the JavaRFIDConnector project folder in the left-hand workspace/project window and you should see 3 packages
12. Eclipse will say that there are errors in the RFID.src.edu.auburn.eng.sks0024.rfid_connector_test package
  * Double click that package to see a list of unit test files
  * In one of the unit test files hover over the @Test annotation and you should get a message which says
  
  ```
  Test cannot be resolved to a type 
  ```
  * In that window click Add JUnit 4 library to the build path
13. Now we need to add a few libraries to the project so right click on JavaRFIDConnector and click properties on the drop down menu
  1. In the Properties window click on Java Build Path
  2. Click on the Libraries tab
    1. Click Add Library... and choose JRE System Library and click Finish
    2. Click Add Library... again and this time choose User Library
    3. Click on User Libraries...
    4. Click New... and give the user library a name like "PostgreSQL JDBC"
    5. Click New.. and give the user library a name like "OctaneSDK"
    6. Highlight PostgreSQL JDBC and click Add External JARS...
    7. Navigate to where you put the PostgreSQL JDBC .jar file and double click the .jar
    8. Highlight OctaneSDK JDBC and click Add External JARS...
    9. Navigate to where you extracted the OctaneSDK
    10. Add the "jar-with-dependencies" .jar file and "javadoc" .jar file 
    11. Finally click Ok
  3. Back in the User Library Window check mark both OctaneSDK and PostgreSQL JDBC and click Finish
  4. Click Ok on the JavaRFIDConnector Properties window
14. You might need to Refactor the names of the packages to 
```
edu.auburn.eng.sks0024.rfid_connector
```
and
```
edu.auburn.eng.sks0024.rfid_connector_test
```
and
```
doc
```

After this the project should be error free.
