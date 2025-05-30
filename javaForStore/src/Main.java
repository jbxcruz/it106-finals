import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Sari-Sari Store Inventory ---");
            System.out.println("1. View all products");
            System.out.println("2. Add a new product");
            System.out.println("3. Update a product");
            System.out.println("4. Delete a product");
            System.out.println("5. Exit");

            int choice = readInt(scanner, "Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        List<Product> products = apiClient.getProducts();
                        products.forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter product description: ");
                        String desc = scanner.nextLine();

                        double price = readDouble(scanner, "Enter product price: ");

                        Product newProduct = new Product(0, name, desc, price);
                        apiClient.addProduct(newProduct);
                        break;
                    case 3:
                        int updateId = readInt(scanner, "Enter ID of product to update: ");

                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();

                        System.out.print("Enter new description: ");
                        String newDesc = scanner.nextLine();

                        double newPrice = readDouble(scanner, "Enter new price: ");

                        Product updatedProduct = new Product(updateId, newName, newDesc, newPrice);
                        apiClient.updateProduct(updatedProduct);
                        break;
                    case 4:
                        int deleteId = readInt(scanner, "Enter ID of product to delete: ");
                        apiClient.deleteProduct(deleteId);
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number 1–5.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Reads an integer from the user, re-prompting on invalid input
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    // Reads a double from the user, re-prompting on invalid input
    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
}
