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
}
