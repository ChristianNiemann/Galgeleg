package galgeleg;

import java.rmi.Naming;
import java.util.Scanner;

public class GalgelogikKlient {

  private static IGalgelogik spil;
  private static Scanner scanner;

  public static void main(String[] args) throws Exception {

    init();

    while(true){

      StringBuilder message = new StringBuilder();
      message.append("Ordet du skal gætte er: ").append(spil.getSynligtOrd());
      message.append("\n").append("Bogstaver du allerede har gættet på: ").append(spil.getBrugteBogstaver());
      message.append("\n").append("Du har ").append(spil.getAntalForkerteBogstaver()).append(" forkerte gæt.");
      System.out.println(message.toString());

      String input = scanner.nextLine();
      if(input.toLowerCase().equals("exit")) {
        break;
      } else if(input.length() != 1 || !Character.isLetter(input.charAt(0))){
        System.out.println("Ugyldigt input - prøv igen.");
      } else {
        play(input);
      }
    }

    exit();
  }

  private static void init() throws Exception {
    spil = (IGalgelogik) Naming.lookup("rmi://localhost/galgelogik");
    scanner = new Scanner(System.in);

    spil.nulstil();
    try {
      spil.hentOrdFraDr();
    } catch (Exception e) {
      e.printStackTrace();
    }
    spil.logStatus();

    System.out.println("Velkommen til Galgeleg!\n" +
            "Spillet går ud på at du skal gætte et ord, man må kun gætte på et bogstav ad gangen.\n" +
            "Hvis man gætter forkert 7 gange, har man tabt spillet.\n" +
            "Hvis du vil afslutte spillet kan du skrive \"exit\".\n" +
            "------------------------------------------------------------------------------------------");
  }

  private static void play(String input) throws Exception {

    spil.gætBogstav(input);

    if(spil.erSpilletSlut()) {
      StringBuilder message = new StringBuilder();
      message.append("------------------------------------------------------------------------------------------");
      if (spil.erSpilletVundet()) {
        message.append("\n").append("Tillykke du har vundet!");
      } else if (spil.erSpilletTabt()) {
        message.append("\n").append("Du har desværre tabt!");
      }
      message.append("\n").append("Ordet var: ").append(spil.getOrdet());
      message.append("\n").append("Tryk på en \"enter\" for at starte et nyt spil.");
      System.out.println(message.toString());
      spil.nulstil();
      scanner.nextLine();
      System.out.println("------------------------------------------------------------------------------------------");
      return;
    }

    spil.logStatus();
  }

  private static void exit() throws Exception {
    System.out.println("Farvel..");
    scanner.close();
  }
}
