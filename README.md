# Arrowhead_OPC-UA
This project is based on the [Eclipse Milo (tm)](https://github.com/eclipse/milo) OPC-UA stack. It provides the possibility
to integrate an existing OPC-UA Server with Consumers in an [Arrowhead Framework](http://github.com/arrowhead-f) local cloud. In essence, 
the system is a Provider that allows Consumers to read and write OPC-UA variables over a REST api. 
The Provider works in the following way:
 1. The Provider will connect to an OPC-UA server and register all OPC-UA Variables beneath a chosen Node to the Service Registry.
 2. The Provider then exposes a REST API for reading and writing (integer values only) these variables. In order to read a variable, just use the service URI in the Service Registry (leave the "value" query parameter empty). In order to write a variable, add your desired value to the "value" query parameter.
 
 NOTE! All variables below the chosen Node are assumed to be read/write, but the code should work even if they are read-only provided that no writes are attempted.

## How to use

### Requirements
* Java JRE/JDK 8+
* Maven 3.5+
* Arrowhead core services running, for more info goto [core-java](http://github.com/arrowhead-f)
* Client-java built, for more information goto [client-java](http://github.com/arrowhead-f/client-java)

This project uses Maven. In order to use it, first download or clone this repository. Then in the root folder of run:
```mvn install```

### Setup and run
1. Download or clone the repository.
2. Edit the /provider/conf/default.conf file to match your Arrowhead Core Services.
3. Select a Node in your OPC-UA server that will serve as a root. All found Variable Nodes below this root
will be added to the Arrowhead Service Registry and exposed via a REST api. 
4. Input your OPC-UA server address and your selected root Node in FullProviderMain.java. (/provider/src/main/java/eu/arrowhead/client/provider/FullProvider.java)
These are member variables named (found almost at the top): ```private String opcServerAddress, = "Niklass-MacBook-Pro.local:53530/OPCUA/SimulationServer"; private int rootNodeNamespaceIndex = 5; private String rootNodeIdentifier = "85/0:Simulation";```
5. Goto the root folder and run: ```mvn clean install```

To run your Provider (same procedure for Consumer), go to provider/target folder and run: ```java -jar provider-4.0.jar``` (add -auth if you want to automatically add the Consumer to the intra_cloud authorization)

