import java.util.*;
import java.sql.*;

public class MedCareSupplies {
    static Scanner input = new Scanner(System.in);
    static Item item1, item2, item3;
    static int TempSold1, TempSold2, TempSold3;
    static double totalProfit = 0;
    static String invoiceOut;

    public static void main(String[] args) {

        Item[] items=loadItemsFromDB();
        item1=items[0];
        item2=items[1];
        item3=items[2];
        System.out.println("\t\t----------------");
        System.out.println("\t\tWelcome to MedCare Supplies!");
        
        if(item1==null || item2==null || item3==null){
        System.out.println("Error, item failed to load from database");
        return;
        }
       
        displayMenu();
    }
      
    public static void customerMenu() {
        TempSold1 = 0;
        TempSold2 = 0;
        TempSold3 = 0;

        invoiceOut = String.format("%-30s %-10s %-15s %-10s%n", "Name", "Quantity", "PricePerUnit", "Total");

        int choice;
        do {
            System.out.println("\n---Customer Menu---");
            System.out.println("1- View available products.");
            System.out.println("2- Select an item and specify quantity.");
            System.out.println("3- Checkout and generate an invoice.");
            System.out.println("4- Cancel and return to the main menu.");

            choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.printf("%-10s |%-30s |%-10s |%-15s |%-10s%n", "itemId", "name", "quantity", "price", "soldCount");
                    if (item1.getQuantity() > 0) item1.display();
                    if (item2.getQuantity() > 0) item2.display();
                    if (item3.getQuantity() > 0) item3.display();
                    break;

                case 2:
                    System.out.println("Enter ID of Item:");
                    String id = input.next();
                    if (!id.equals(item1.getItemId()) && !id.equals(item2.getItemId()) && !id.equals(item3.getItemId())) {
                        System.out.println("Invalid ID");
                        break;
                    }

                    System.out.print("Enter quantity of item: ");
                    int qun = input.nextInt();

                    if (id.equals(item1.getItemId())) {
                        if (item1.updateStock(qun)) {
                            invoiceOut += String.format("%-30s %-10s %-13.2f %-10.2f%n", item1.getName(), qun, item1.getPrice(), item1.getTotalCost(qun));
                            TempSold1 += qun;
                            System.out.println("Your products have been added to the cart.");
                            updateItemInDB(item1);
                        } else {
                            System.out.println("Number of available quantity isn't enough.");
                        }
                    }

                    if (id.equals(item2.getItemId())) {
                        if (item2.updateStock(qun)) {
                            invoiceOut += String.format("%-30s %-10s %-13.2f %-10.2f%n", item2.getName(), qun, item2.getPrice(), item2.getTotalCost(qun));
                            TempSold2 += qun;
                            System.out.println("Your products have been added to the cart.");
                             updateItemInDB(item2);
                        } else {
                            System.out.println("Number of available quantity isn't enough.");
                        }
                    }

                    if (id.equals(item3.getItemId())) {
                        if (item3.updateStock(qun)) {
                            invoiceOut += String.format("%-30s %-10s %-13.2f %-10.2f%n", item3.getName(), qun, item3.getPrice(), item3.getTotalCost(qun));
                            TempSold3 += qun;
                            System.out.println("Your products have been added to the cart.");
                             updateItemInDB(item3);
                        } else {
                            System.out.println("Number of available quantity isn't enough.");
                        }
                    }

                    break;

                case 3:
                    generateInvoice();
                    break;

                case 4:
                    item1.setQuantity(item1.getQuantity() + TempSold1);
                    item1.setSoldCount(item1.getSoldCount() - TempSold1);

                    item2.setQuantity(item2.getQuantity() + TempSold2);
                    item2.setSoldCount(item2.getSoldCount() - TempSold2);

                    item3.setQuantity(item3.getQuantity() + TempSold3);
                    item3.setSoldCount(item3.getSoldCount() - TempSold3);
                    break;

                default:
                    System.out.println("Invalid input.");
            }
        } while (choice != 3 && choice != 4);
    }

    public static void generateInvoice() {
        System.out.println("---INVOICE---");
        if (TempSold1 == 0 && TempSold2 == 0 && TempSold3 == 0) {
            System.out.println("There are no items in the basket.");
            return;
        }
        System.out.println(invoiceOut);
        double totalCost = item1.getTotalCost(TempSold1) + item2.getTotalCost(TempSold2) + item3.getTotalCost(TempSold3);
        System.out.printf("Total cost: %.2f SAR%n", totalCost);
        System.out.println("--------------\n");

        totalProfit += totalCost;
    }

    public static void findMostPopularItem() {
        if (totalProfit == 0) {
            System.out.println("\n There are no purchases.\n");
            return;
        }

        double max = item1.getSoldCount();
        if (item2.getSoldCount() > max) max = item2.getSoldCount();
        if (item3.getSoldCount() > max) max = item3.getSoldCount();

        System.out.println("--- The Most Popular Item: ---");
        if (max == item1.getSoldCount()) item1.display();
        if (max == item2.getSoldCount()) item2.display();
        if (max == item3.getSoldCount()) item3.display();
    }

    public static void displayMenu() {
        int MainChoice;
        do {
            System.out.println("\n---Main Menu---");
            System.out.println("1. Customer");
            System.out.println("2. Store Owner");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            MainChoice = input.nextInt();

            switch (MainChoice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    storeOwnerMenu();
                    break;
                case 3:
                    System.out.println("--- Goodbye! ---");
                    break;
                default:
                    System.out.println("--- Invalid Input ---");
            }
        } while (MainChoice != 3);
    }

    public static void storeOwnerMenu() {
        int ownerChoice;
        do {
            System.out.println("\n---Owner Menu---");
            System.out.println("1. View total profit.");
            System.out.println("2. View the most popular item.");
            System.out.println("3. Return to Main Menu.");
            ownerChoice = input.nextInt();

            switch (ownerChoice) {
                case 1:
                    System.out.printf("Total profit = %.2f SR.\n", totalProfit);
                    break;
                case 2:
                    findMostPopularItem();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("--Invalid input--");
            }
        } while (ownerChoice != 3);
    }
    
    public static Item[] loadItemsFromDB() {
    Item[] items = new Item[3]; 

    try {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/Invoice_List", "root", "Mohra2006"
        );

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Items");

        int i = 0;
        while (rs.next() && i<3) {
            String id = rs.getString("itemID");
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            double price = rs.getDouble("price");
            int soldCount = rs.getInt("soldCount");

            items[i] = new Item(id, name, quantity, price, soldCount);
            i++;
        }

        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return items;
}

public static void updateItemInDB(Item item) {
    try {
        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/Invoice_List", "root", "Mohra2006" 
        );

        String sql = "UPDATE Items SET quantity = ?, soldCount = ? WHERE itemID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, item.getQuantity());
        pstmt.setInt(2, item.getSoldCount());
        pstmt.setString(3, item.getItemId());
        
            int rowsAffected = pstmt.executeUpdate();  
        System.out.println("Rows affected: " + rowsAffected); 

        pstmt.executeUpdate();
        conn.close();
    } catch (Exception e) {
        System.out.println("Failed to update database.");
        e.printStackTrace();
    }
}
}