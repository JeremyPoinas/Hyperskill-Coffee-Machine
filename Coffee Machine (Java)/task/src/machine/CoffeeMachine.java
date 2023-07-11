package machine;

import java.util.Scanner;

public class CoffeeMachine {
    int waterSupply;
    int milkSupply;
    int beansSupply;
    int disposableCups;
    int cash;
    Recipe espressoRecipe = new Recipe(250, 0, 16, 4);
    Recipe lateRecipe = new Recipe(350, 75, 20, 7);
    Recipe cappuccinoRecipe = new Recipe(200, 100, 12, 6);

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);

        String action;
        do {
            action = coffeeMachine.chooseAction();
            switch (action) {
                case "fill" -> coffeeMachine.addSupply();
                case "take" -> coffeeMachine.emptyCash();
                case "buy" -> {
                    Recipe choice = coffeeMachine.takeOrder();
                    if (choice == null) continue;
                    if (coffeeMachine.hasEnoughResources(choice)) {
                        coffeeMachine.makeCoffee(choice);
                    }
                }
                case "remaining" -> coffeeMachine.getState();
            }
        } while (!action.equals("exit"));
    }

    CoffeeMachine(int waterSupply, int milkSupply, int beansSupply, int disposableCups, int cash) {
        this.waterSupply = waterSupply;
        this.milkSupply = milkSupply;
        this.beansSupply = beansSupply;
        this.disposableCups = disposableCups;
        this.cash = cash;
    }

    static class Recipe {
        int waterPerCup; // ml
        int milkPerCup; // ml
        int beansPerCup; // g
        int price; // dollars

        public Recipe(int waterPerCup, int milkPerCup, int beansPerCup, int price) {
            this.waterPerCup = waterPerCup;
            this.milkPerCup = milkPerCup;
            this.beansPerCup = beansPerCup;
            this.price = price;
        }
    }

    void makeCoffee(Recipe recipe) {
        this.waterSupply -= recipe.waterPerCup;
        this.milkSupply -= recipe.milkPerCup;
        this.beansSupply -= recipe.beansPerCup;
        this.disposableCups--;
        this.cash += recipe.price;
    }

    void emptyCash() {
        System.out.printf("I gave  you $%d \n", this.cash);
        this.cash = 0;
    }

    void getState() {
        System.out.printf("""
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                
                """,
                this.waterSupply,
                this.milkSupply,
                this.beansSupply,
                this.disposableCups,
                this.cash
        );
    }

    String chooseAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextLine()) {
            System.out.println("Please enter an action!");
        }
        return scanner.nextLine();
    }

    boolean hasEnoughResources(Recipe recipe) {
        if (this.waterSupply >= recipe.waterPerCup) {
            if (this.milkSupply >= recipe.milkPerCup) {
                if (this.beansSupply >= recipe.beansPerCup) {
                    if (this.disposableCups > 0) {
                        System.out.println("I have enough resources, making you a coffee!");
                        return true;
                    }
                    else {
                        System.out.println("Sorry, not enough cups!");
                    }
                }
                else {
                    System.out.println("Sorry, not enough beans!");
                }
            }
            else {
                System.out.println("Sorry, not enough milk!");
            }
        } else {
            System.out.println("Sorry, not enough water!");
        }
        return false;
    }

    Recipe takeOrder() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt() && scanner.next().equals("back")) {
            return null;
        }
        int input = scanner.nextInt();
        return input == 1 ? this.espressoRecipe : input == 2 ? this.lateRecipe : this.cappuccinoRecipe;
    }

    void addSupply() {
        this.waterSupply += addWater();
        this.milkSupply += addMilk();
        this.beansSupply += addBeans();
        this.disposableCups += addCups();
    }

    int addWater() {
        System.out.println("Write how many ml of water you want to add:");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }

    int addMilk() {
        System.out.println("Write how many ml of milk you want to add:");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }

    int addBeans() {
        System.out.println("Write how many grams of coffee beans you want to add: ");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }

    int addCups() {
        System.out.println("Write how many disposable cups you want to add:");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }
}
