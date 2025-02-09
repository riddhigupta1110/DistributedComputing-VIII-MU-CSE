import HelloApp.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

import java.io.FileWriter;

class HelloImpl extends HelloPOA {
    private ORB orb;

    public void setORB(ORB orb) {
        this.orb = orb;
    }

    @Override
    public String sayHello() {
        return "Hello, CORBA World!";
    }
}

public class HelloServer {
    public static void main(String[] args) {
        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("RootPOA");
            POA rootpoa = POAHelper.narrow(objRef);
            rootpoa.the_POAManager().activate();

            // Create servant object
            HelloImpl helloImpl = new HelloImpl();
            helloImpl.setORB(orb);

            // Convert servant to CORBA reference
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
            Hello helloRef = HelloHelper.narrow(ref);

            // Generate IOR and save to file
            String ior = orb.object_to_string(helloRef);
            System.out.println("Server ready and waiting...");
            System.out.println("IOR: " + ior);
            
            FileWriter file = new FileWriter("ior.txt");
            file.write(ior);
            file.close();

            // Keep server running
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
