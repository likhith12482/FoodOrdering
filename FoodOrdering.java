import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

class MenuItem {
    private String itemName;
    private String description;
    private double price;
    private String category;

    public MenuItem(String itemName, String description, double price, String category) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return itemName + " - " + description + " ($" + price + ")";
    }
}

class Order {
    private User user;
    private List<MenuItem> items;

    public Order(User user) {
        this.user = user;
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void displayOrder() {
        System.out.println("Order for: " + user.getUsername());
        for (MenuItem item : items) {
            System.out.println(item);
        }
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }
}

public class FoodOrdering {
    private static List<MenuItem> menuItems = new ArrayList<>();

    public static void main(String[] args) {
        // Sample Menu Items
        menuItems.add(new MenuItem("Burger", "Delicious beef burger", 5.99, "Non-Vegetarian"));
        menuItems.add(new MenuItem("Veg Pizza", "Cheesy vegetable pizza", 8.99, "Vegetarian"));
        menuItems.add(new MenuItem("Chicken Salad", "Fresh salad with grilled chicken", 7.99, "Non-Vegetarian"));
        menuItems.add(new MenuItem("Fruit Salad", "Mixed fresh fruits", 4.99, "Vegetarian"));
        menuItems.add(new MenuItem("Chocolate Cake", "Rich chocolate dessert", 3.99, "Dessert"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Food Ordering System!");

        User user = new User("Customer", "customer");

        // Display Categories
        System.out.println("Please select a category:");
        System.out.println("1. Vegetarian");
        System.out.println("2. Non-Vegetarian");
        System.out.println("3. Desserts");

        int categoryChoice = scanner.nextInt();

        // Check for valid category selection
        if (categoryChoice < 1 || categoryChoice > 3) {
            System.out.println("Invalid number. Please restart the application and select a valid category.");
            scanner.close();
            return;
        }

        String selectedCategory = "";
        switch (categoryChoice) {
            case 1:
                selectedCategory = "Vegetarian";
                break;
            case 2:
                selectedCategory = "Non-Vegetarian";
                break;
            case 3:
                selectedCategory = "Dessert";
                break;
        }

        // Display Items in Selected Category
        System.out.println("\n" + selectedCategory + " Items:");
        List<MenuItem> filteredItems = new ArrayList<>(); // Create a list to store filtered items
        int serialNo = 1;
        for (MenuItem item : menuItems) {
            if (item.getCategory().equals(selectedCategory)) {
                filteredItems.add(item);
                System.out.println(serialNo + ". " + item);
                serialNo++;
            }
        }

        // Order Items
        Order order = new Order(user);
        System.out.print("Select an item number to add to your order (0 to finish): ");
        while (true) {
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            } else if (choice > 0 && choice < serialNo) { // Check against the number of items displayed
                order.addItem(filteredItems.get(choice - 1)); // Use the filtered list
                System.out.println("Item added to your order.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.print("Select another item number (0 to finish): ");
        }

        // Display Order
        order.displayOrder();
        System.out.println("Total Amount: $" + order.calculateTotal());

        scanner.close();
    }
}
