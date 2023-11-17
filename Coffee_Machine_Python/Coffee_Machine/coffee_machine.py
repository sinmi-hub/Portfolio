class CoffeeMachine:
    # Class variables shared by all instances
    resources = {
        "water": 400,
        "milk": 540,
        "beans": 120,
        "cups": 9,
        "money": 550
    }

    low_resource_prompts = {
        "water": "Sorry, not enough water!",
        "milk": "Sorry, not enough milk!",
        "beans": "Sorry, not enough beans!",
        "cups": "Sorry, not enough cups!"
    }

    coffee_types = {
        "1": {"name": "espresso", "water": 250, "beans": 16, "cost": 4},
        "2": {"name": "latte", "water": 350, "beans": 20, "milk": 75, "cost": 7},
        "3": {"name": "cappuccino", "water": 200, "beans": 12, "milk": 100, "cost": 6}
    }

    buy_prompt = "1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: \n"

    def __init__(self, name):
        self.name = name

    # Method to display the current resource status of the coffee machine
    def remaining(self):
        print(
            f"The coffee machine has:\n"
            f"{self.resources['water']} ml of water\n"
            f"{self.resources['milk']} ml of milk\n"
            f"{self.resources['beans']} g of coffee beans\n"
            f"{self.resources['cups']} disposable cups\n"
            f"${self.resources['money']} of money\n"
        )

    # Method to update the resources based on the coffee choice
    def update_resources(self, choice):
        coffee = self.coffee_types[choice]
        self.resources['water'] -= coffee.get('water', 0)
        self.resources['beans'] -= coffee.get('beans', 0)
        self.resources['milk'] -= coffee.get('milk', 0)
        self.resources['money'] += coffee.get('cost', 0)
        self.resources['cups'] -= 1

    # Method to check if sufficient resources are available to make the selected coffee
    def check_resources(self, choice):
        coffee = self.coffee_types[choice]
        if self.resources['water'] < coffee.get('water', 0):
            print(self.low_resource_prompts['water'])
            return False
        if self.resources['beans'] < coffee.get('beans', 0):
            print(self.low_resource_prompts['beans'])
            return False
        if 'milk' in coffee and self.resources['milk'] < coffee.get('milk', 0):
            print(self.low_resource_prompts['milk'])
            return False
        if self.resources['cups'] < 1:
            print(self.low_resource_prompts['cups'])
            return False
        return True

    # Method for handling the coffee purchasing process
    def buy(self):
        choice = input(self.buy_prompt)
        if choice in self.coffee_types:
            if self.check_resources(choice):
                self.update_resources(choice)
                print("I have enough resources, making you a coffee!\n")
        elif choice == "back":
            return  # Return to the main menu

    # Method for adding resources to the coffee machine
    def fill(self):
        self.resources['water'] += int(input("How much water to add?\n>"))
        self.resources['milk'] += int(input("How much milk to add?\n>"))
        self.resources['beans'] += int(input("How much coffee beans to add?\n>"))
        self.resources['cups'] += int(input("How many disposable cups to add?\n>"))

    # Method for handling the action of taking the money from the coffee machine
    def take(self):
        print(f"I gave you ${self.resources['money']}\n")
        self.resources['money'] = 0

    # Main method to run the coffee machine program
    def runner(self):
        print(f"Hello, I am {self.name}! I will be making your coffee today\n")

        running = True
        while running:
            action = input("Write action (buy, fill, take, remaining, exit):\n>")
            if action == "remaining":
                self.remaining()
            elif action == "buy":
                self.buy()
            elif action == "fill":
                self.fill()
            elif action == "take":
                self.take()
            elif action == "exit":
                running = False

