package org.example;

import java.util.Scanner;

public class UserInteraction {

    public static int askUserForStationId() {
        Scanner console = new Scanner(System.in);

        if (console.hasNext("stop")) {
            System.out.println("***Terminated***");
            System.exit(0);
        }
        while (!console.hasNextInt()) {
            System.out.println("ID może składać się tylko z cyfr! ");
            console.next();
        }
        int providedId = console.nextInt();
        while (providedId <= 0) {
            System.out.println("ID powinno być większe od 0! ");
            providedId = console.nextInt();
        }
        return providedId;
    }
    public static boolean askUserForDecision() {
        System.out.println("\nJeśli chcesz zapisać dane do pliku PDF, wciśnij 'y'.");

        Scanner console = new Scanner(System.in);

        String userInput = console.nextLine().toString();

        if (userInput.equals("y")){
            return true;
        }
        else{
            return false;
        }
    }
    public static String askUserForFileName() {
        System.out.println("Podaj nazwę pliku PDF, do którego chcesz zapisać dane.");
        Scanner console = new Scanner(System.in);

        String userInput = console.nextLine().toString();

        return userInput;
    }
}
