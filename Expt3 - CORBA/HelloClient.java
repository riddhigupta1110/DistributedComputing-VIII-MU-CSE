import HelloApp.*;
import org.omg.CORBA.*;

import java.io.BufferedReader;
import java.io.FileReader;

public class HelloClient {
    public static void main(String[] args) {
        try {
            // Initialize ORB
            ORB orb = ORB.init(args, null);

            // Read IOR from file
            BufferedReader br = new BufferedReader(new FileReader("ior.txt"));
            String ior = br.readLine();
            br.close();

            // Convert IOR to CORBA object
            org.omg.CORBA.Object objRef = orb.string_to_object(ior);
            Hello helloRef = HelloHelper.narrow(objRef);

            // Call remote method
            String message = helloRef.sayHello();
            System.out.println("Response from server: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
