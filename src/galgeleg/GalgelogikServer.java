package galgeleg;

import java.rmi.Naming;

public class GalgelogikServer {

    public static void main(String[] arg) throws Exception
    {
        // Enten: Kør programmet 'rmiregistry' fra mappen med .class-filerne, eller:
        java.rmi.registry.LocateRegistry.createRegistry(1099); // start i server-JVM

        IGalgelogik spil = new Galgelogik();
        Naming.rebind("rmi://localhost/galgelogik", spil);
        System.out.println("Galgelogik registreret.");
    }
}
