package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        int coffeeCups = takeOrder();
        while (coffeeCups == 0) {
            System.out.println("Please choose a number greater than 0.");
            coffeeCups = takeOrder();
        }
        int[] ingredients = calculateIngredients(coffeeCups);
        int water = ingredients[0];
        int milk = ingredients[1];
        int beans = ingredients[2];

        System.out.printf("""
                For %d cups of coffee you will need:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                """,
                coffeeCups,
                water,
                milk,
                beans
        );
    }

    static int takeOrder() {
        System.out.println("Write how many cups of coffee you will need:");
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return 0;
        }
        return scanner.nextInt();
    }

    static int[] calculateIngredients(int coffeeCups) {
        int waterPerCup = 200; // ml
        int milkPerCup = 50; // ml
        int beansPerCup = 15; // g

        return new int[] {coffeeCups * waterPerCup, coffeeCups * milkPerCup, coffeeCups * beansPerCup};
    }

}
