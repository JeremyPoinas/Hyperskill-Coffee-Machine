package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public enum MachineState {
        OFF, MAIN, BUYING, FILLING_WATER, FILLING_MILK, FILLING_COFFEE, FILLING_CUPS
    }
    private MachineState state;
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
        Scanner scanner = new Scanner(System.in);

        while (coffeeMachine.isWorking()) {
            coffeeMachine.execute(scanner.nextLine());
        }
    }

    CoffeeMachine(int waterSupply, int milkSupply, int beansSupply, int disposableCups, int cash) {
        this.waterSupply = waterSupply;
        this.milkSupply = milkSupply;
        this.beansSupply = beansSupply;
        this.disposableCups = disposableCups;
        this.cash = cash;
        this.setMainState();
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

    boolean isWorking() {
        return state != MachineState.OFF;
    }

    void execute(String input) {
        switch (state) {
            case MAIN -> this.setState(input);
            case FILLING_WATER -> {
                this.waterSupply += Integer.parseInt(input);
                System.out.print("Write how many ml of milk do you want to add:\n> ");
                state = MachineState.FILLING_MILK;
            }
            case FILLING_MILK -> {
                this.milkSupply += Integer.parseInt(input);
                System.out.print("Write how many grams of coffee beans do you want to add:\n> ");
                state = MachineState.FILLING_COFFEE;
            }
            case FILLING_COFFEE -> {
                this.beansSupply += Integer.parseInt(input);
                System.out.print("Write how many disposable cups of coffee do you want to add:\n> ");
                state = MachineState.FILLING_CUPS;
            }
            case FILLING_CUPS -> {
                this.disposableCups += Integer.parseInt(input);
                setMainState();
            }
            case BUYING -> {
                if (input.equals("back")) {
                    this.setMainState();
                    break;
                }
                Recipe choice = input.equals("1") ? this.espressoRecipe : input.equals("2") ? this.lateRecipe : this.cappuccinoRecipe;
                if (hasEnoughResources(choice)) {
                    makeCoffee(choice);
                }
                setMainState();
            }
            default -> {
            }
        }
    }

    public void setState(String command) {
        switch (command) {
            case "remaining" -> {
                getState();
                setMainState();
            }
            case "buy" -> {
                System.out.print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:\n> ");
                state = MachineState.BUYING;
            }
            case "fill" -> {
                System.out.print("Write how many ml of water do you want to add:\n> ");
                state = MachineState.FILLING_WATER;
            }
            case "take" -> {
                emptyCash();
                setMainState();
            }
            case "exit" -> state = MachineState.OFF;
            default -> {
                System.out.println("Unexpected action.");
                setMainState();
            }
        }
    }

    void setMainState() {
        state = MachineState.MAIN;
        System.out.print("\nWrite action (buy, fill, take, remaining, exit):\n> ");
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
}
