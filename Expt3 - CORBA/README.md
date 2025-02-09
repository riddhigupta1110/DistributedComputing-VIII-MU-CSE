Ensure you have [**JDK 8**](https://www.oracle.com/in/java/technologies/javase/javase8u211-later-archive-downloads.html) or earlier installed, as modern Java versions (JDK 11 and later) have removed CORBA support.
In case of issues, *verify your JDK installation and idlj presence inside the bin folder*.

**NOTE**: If issues persist, follow the steps in a new folder outside of this repo folder.

# Step 1: Create IDL File
Create a file named Hello.idl 
- This defines a CORBA interface named Hello with a method sayHello().  
//File has already been created in this repo

# Step 2: Compile the IDL File
Navigate to the directory containing Hello.idl and run:  
```bash
idlj -fall Hello.idl
```  
- This generates a folder HelloApp/

# Step 3: Implement the Server
### Create file named HelloServer.java  

- This file should be in the same directory as HelloApp/.  
- This file starts a CORBA server, registers the Hello service, saves the Interoperable Object Reference (IOR) in ior.txt and keeps running to serve client requests    
//File has already been created in this repo

# Step 4: Implement the Client
### Create a file named HelloClient.java  

- This file should be in the same directory as HelloServer.java.  
- This file reads the IOR (server reference) from ior.txt, connects to the CORBA server, calls sayHello() and prints the response.    
//File has already been created in this repo  

# Step 5: Compile Java Files
Ensure You Are in the Right Directory. Navigate to the folder containing HelloServer.java and HelloClient.java.

### Compile all java files:
```bash
javac HelloApp/*.java HelloServer.java HelloClient.java
```  

If you get "javac: file not found", ensure:
1. You are in the correct directory.
2. You generated the HelloApp/ folder with idlj.

# Step 6: Start the CORBA Naming Service

### Run:  
+ For windows:
```bash
tnameserv -ORBInitialPort 1050
```  
+ For UNIX:  
```bash
tnameserv -ORBInitialPort 1050 &
```  
- This starts the CORBA Naming Service, which helps clients locate the server.

# Step 7: Run the Server
### Start the server:
```bash
java HelloServer -ORBInitialPort 1050
```

- The IOR is saved in ior.txt.

# Step 8: Run the Client

### Open a new terminal and run:
```bash
java HelloClient -ORBInitialPort 1050
```







