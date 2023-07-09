package machine;

import java.util.Scanner;

public class CoffeeMachine {
    int waterPerCup = 200; // ml
    int milkPerCup = 50; // ml
    int beansPerCup = 15; // g
    int waterSupply;
    int milkSupply;
    int beansSupply;
    int coffeeCupsOrdered;
    int waterNeeded;
    int milkNeeded;
    int beansNeeded;

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        coffeeMachine.checkSupply();
        coffeeMachine.takeOrder();
        coffeeMachine.calculateIngredients();
        coffeeMachine.calculateNumberOfCupsDoable();
    }

    void calculateNumberOfCupsDoable() {
        int coffeeCupsDoable = Math.min(
                Math.min(this.waterSupply / this.waterPerCup,
                this.milkSupply / this.milkPerCup),
                this.beansSupply / this.beansPerCup
        );
        if (coffeeCupsDoable < this.coffeeCupsOrdered) {
            this.coffeeCupsOrdered = coffeeCupsDoable;
            System.out.printf("No, I can make only %d cup(s) of coffee\n", this.coffeeCupsOrdered);
        } else if (coffeeCupsDoable == this.coffeeCupsOrdered) {
            System.out.println("Yes, I can make that amount of coffee\n");
        } else {
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)\n", coffeeCupsDoable - this.coffeeCupsOrdered);
        }
    }

    void takeOrder() {
        System.out.println("Write how many cups of coffee you will need:");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }
        this.coffeeCupsOrdered = scanner.nextInt();
    }

    void calculateIngredients() {
        this.waterNeeded = this.coffeeCupsOrdered * this.waterPerCup;
        this.milkNeeded = this.coffeeCupsOrdered * this.milkPerCup;
        this.beansNeeded = this.coffeeCupsOrdered * this.beansPerCup;
    }

    void checkSupply() {
        this.waterSupply = checkWater();
        this.milkSupply = checkMilk();
        this.beansSupply = checkBeans();
    }

    int checkWater() {
        System.out.println("Write how many ml of water the coffee machine has:");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }

    int checkMilk() {
        System.out.println("Write how many ml of milk the coffee machine has:");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }

    int checkBeans() {
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        Scanner scanner = new Scanner(System.in);

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
        }

        return scanner.nextInt();
    }
}
