# JavaRFIDConnector Installation Guide
## Hardware
### You will need
1. An Impinj RFID reader hub. We are using the Impinj Speedway Revolution
2. 1-4 RFID Antennas which are compatible with the Revolution
3. A Windows machine is preferred to launch executable

### Setup 
1. Plug the Revolution into an AC outlet
2. Plug an ethernet cable into the ethernet port of the Revolution. Put the other end of the ethernet into the computer running the JavaRFIDConnector
  * You should notice an "Unidentified LAN network" under your network connections
  1. Open your network connnection settings 
  2. Right click the unidentified LAN network and click properties
  3. High light Internet Protocol Version 4 (TCP/IPv4) in the Networking tab
  4. Click Properties
  5. Checkmark "Use the following IP address":
    1. In the IP address box type the IP of the Revolution +1 on the far right number (e.g. Impinj IP 192.168.225.50 you would type 192.168.225.51 as the IP)
    2. In the Subnet mask box type - 255.255.255.0
    3. Leave the rest of the fields blank
    3. Click Ok
  Click Ok on Local Area Connection Properties
3. Screw the RFID Antenna(s) into the Revolution
4. The Revolution's LEDs should be Green (consult the Impinj Revolution user guide for more information)
  * https://support.impinj.com/hc/en-us/article_attachments/201052867/SpeedwayR_installation_and_operations_guide.pdf
  
# Software
### You will need
1. Java version 1.7 or greater. Follow the link for the latest version of Java  - http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
2. The RFIDConfigurationManager.exe file provided in in the JavaRFIDConnector Github project.

### Setup
1. Choose the platform version of Java suitable for your operating system
2. Install Java
3. You will now be able to run the RFIDConfigurationManager.exe

### How to use
1. Launch the RFIDConfigurationManager.exe by double clicking it
  * A warning may appear about running unverified software. Click continue.
2. Enter the IP of your Impinj Reader in the Reader Hostname/IP text field
  * e.g. 192.168.225.50
3. Enter the owner/user name associated with your database
4. Enter the password needed to access your database
5. Enter the URL to access the database
  * Note: This can be the fully qualified URL
6. Click on the 'Enter Store Layout' button on the bottom of the RFIDConfigurationManager window
7. A new window will appear titled 'Create Store Layout'
8. Here you can enter up to 8 unique locations to define your store's layout.
  * Capitalization and spacing matter. AREA is not the same as area 
9. After entering in all the locations desired, press Save Layout.
  * Pressing Cancel closes the Window and clears the store locations of the GUI
10. Doing this populates the store location drop down lists with the store locations entered.
11. Check the enable boxes for each RFID antenna which is physically connected to the Impinj Reader which you would like to have activated during execution
12. Now check the 'Entry point?' box for each RFID antenna you would like to be able to insert new tags into the database.
13. Now you can utilize the drop down lists on the same row as each enabled button to define where an RFID antenna will be located.
  1. The drop down list just to the right of "Between" denotes one of the areas an RFID antenna sits between
  2. The drop down list just to the right of "And" denotes the other area which an RFID antenna sits between
    * e.g. Between "Area one" And "Area two"
14. After assigning store locations for an RFID antenna you will be able to assign where tags should be inserted if the antenna is an "entry point" antenna
  1. To do this locate the drop down list to the right of the 'Add Items To' label.
  2. If the 'Entry point?' checkbox for this row is checked, then you will be able to click the drop down list to display potential locations for a tag to be inserted by the antenna
  3. The possible locations for insertion are generated based off where the RFID antenna is located
    * e.g. If the RFID antenna is located Between "Area one" And "Area two" then the choices that will appear when you click the 'Add Items To' drop down list are "Area one" and "Area two"
15. Repeat steps 13 and 14 for each Antenna which was enabled.
16. Afterwards, you may click the 'Save Configuration' button in order to save a new *.rmconf file containing all the configuration information entered into the GUI.
17. Give the file a name and save it to any location on your computer
18. Later, when you wish to load one of your configurations
  1. Click the 'Load Configuration' button
  2. A file directory navigation window will appear
  3. Navigate to the .rmconf file you wish to load
  4. Double click it
  5. The GUI will now populate with the configuration information contained within the file.
19. After establishing the configuration you wish, press the 'Execute' button to begin executing the JavaRFIDConnector
  * At any time during execution, you may press the 'Quit' button to close the entire application.

### How it will work after 'Execute' button pressed
1. Pass RFID tags past the connected and enabled RFID antennas and they will scan the tag
2. A message will be displayed in the 'Console Output' displaying that a tag was read
3. After some time has passed (approximately 10 seconds) the tags which have been scanned in the past 10 seconds will be inserted/updated in the database
4. The console will display that they are being updated along with the tag being updated, when it was scanned, where the tag used to be located, and where the tag is now located after the scan.
5. For the senior design demo and a short while after, the results of scans can be viewed at - http://aurfid.herokuapp.com/

### Chapman demo instructions
1. Set up 2 antennas (currently Antenna 1 and 3 are plugged in)
  1. You'll want to spread them out as much as possible and have them face opposite directions
    * Make sure that there are no tags in the direction the scanners are facing
  2. Turn on the Thinkpad on the table with the reserved items
    * password is rfid
  2. Plug the ethernet cord from the Speedway Revoultion into the Thinkpad
  3. Launch the Terminal
  4. Navigate to Desktop 
    * cd Desktop
  5. Type ls and you should see RFIDConfigurationManager.jar
  6. Type java -jar RFIDConfigurationManager.jar
    * Runnable jar was the best I could do for Linux
  7. This will launch the application
    * The GUI issues are due to platform differences. The linux SWT library isn't exactly the same as the windows one.
2. Click Load Configuration (looks like 'd Configuration' because SWT issues)
3. Navigate to the Desktop folder
4. Should see LabDemo.rmconf. Double click this file.
5. Afterwards Antenna 1 will be Enabled. 
  * It'll be setup as such - Enabled, Between "Area one" And "backroom" Entry point, backoom
6. Antenna 2 will be Enabled.
  * It'll be setup as such - Enabled, Between "backroom" And "in_store"
7. Click the 'Execute' button on the bottom.
8. Wait a few moments for JavaRFIDConnector to initialize.
  * Should print out log4j:WARN No appenders could be found for logger (org.apache.mina.filter.executor.ExecutorFilter).
                     log4j:WARN Please initialize the log4j system properly.
    This is fine.
9. Walk the items on the table briskly past Antenna 1
10. View the website links (or refresh them) - http://aurfid.herokuapp.com/upc_descriptions/2 (jeans) and http://aurfid.herokuapp.com/upc_descriptions/31 (shirts) to view changes
11. Then walk those same items briskly past Antenna 3
12. View the website links once again (refresh) to view the change




