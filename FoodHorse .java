import java.sql.*;
import java.util.Scanner;

public class FoodHorse {

    private static final String URL = "jdbc:mysql://localhost:3306/FoodHorse?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    private static final String USERNAME = "Your_Username";
    private static final String PASSWORD = "Your_Password";
    private static Scanner console = new Scanner(System.in);
    private static int OperationNumber;

    private static Connection connection = null;

    public static void establishConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void Display_SelectOperation() {
        System.out.println("------------------------------------------------");
        System.out.println("(1) Customer registration");
        System.out.println("(2) Buying a product");
        System.out.println("(3) List customers");
        System.out.println("(4) List a customer's purchases");
        System.out.println("(5) List a customer's most recent purchases");
        System.out.println("(6) List branches");
        System.out.println("(7) List a branch’s stock");
        System.out.println("(8) Add new branch store");
        System.out.println("(9) Add new product");
        System.out.println("(10) Add product to branch’s stock");
        System.out.println("(11) Search customer by their phone number");
        System.out.println("(12) Remove a user from system");
        System.out.println("(13) Exit");
        System.out.println("------------------------------------------------");
        System.out.println();
        System.out.print("Enter a code number for the operation: ");

        String stringInput = console.nextLine();
        OperationNumber = Integer.valueOf(stringInput);

        while(OperationNumber < 1 || OperationNumber > 13) {
            System.out.print("Please enter a number between 1 to 13: ");
            stringInput = console.nextLine();
            OperationNumber = Integer.valueOf(stringInput);
        }
    }

    public static void SelectOperation() {
        if (OperationNumber==1) {
            try {
                establishConnection();
                Statement statement = connection.createStatement();

                System.out.print("Customer Name: ");
                String customerName = console.nextLine();
                while (customerName.length() > 20) {
                    System.out.print("Please enter valid name: ");
                    customerName = console.nextLine();
                }

                System.out.print("Customer Surname: ");
                String customerSurname = console.nextLine();
                while (customerSurname.length() > 20) {
                    System.out.print("Please enter valid surname: ");
                    customerSurname = console.nextLine();
                }

                System.out.print("Customer Adress: ");
                String customerAdress = console.nextLine();
                while(customerAdress.length() > 40) {
                    System.out.print("Please enter valid adress: ");
                    customerAdress = console.nextLine();
                }

                System.out.print("Customer PhoneNumber: ");
                String customerPhoneNumber = console.nextLine();
                if (customerPhoneNumber.length()<11 || customerPhoneNumber.length()>11) {
                    while (customerPhoneNumber.length() != 11) {
                        System.out.print("Please enter valid phone number: ");
                        customerPhoneNumber = console.nextLine();
                    }
                }

                String insertCustomerQuery = "insert into customer(customerName,customerSurname,customerAdress,customerPhoneNumber)"
                                            +" values ('" + customerName + "', '" + customerSurname + "', '" + customerAdress + ""+"', '"
                                            + customerPhoneNumber + "')";
                statement.executeUpdate(insertCustomerQuery);
                closeConnection();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
	
	else if (OperationNumber==2) {
            try {
                establishConnection();
                Statement statement = connection.createStatement();
                System.out.print("CustomerID: ");
                String CustomerId = console.nextLine();
                int customerId = Integer.valueOf(CustomerId);
                while (customerId <= 0) {
                    System.out.print("Please enter a valid CustomerID: ");
                    CustomerId = console.nextLine();
                    customerId = Integer.valueOf(CustomerId);
                }

                System.out.print("BranchID: ");
                String BranchId = console.nextLine();
                int branchId = Integer.valueOf(BranchId);
                while (branchId <= 0) {
                    System.out.print("Please enter a valid BranchID: ");
                    BranchId = console.nextLine();
                    branchId = Integer.valueOf(BranchId);
                }

                System.out.print("ProductID: ");
                String ProductId = console.nextLine();
                int productId = Integer.valueOf(ProductId);
                while (productId <= 0) {
                    System.out.print("Please enter a valid ProductID: ");
                    ProductId = console.nextLine();
                    productId = Integer.valueOf(ProductId);
                }

                System.out.print("OrderTime: ");
                String OrderTime = console.nextLine();
                while (OrderTime.length()<12 || OrderTime.length()>20) {
                    System.out.print("Please enter valid Date (YYYY-MM-DD HH:MI:SS): ");
                    OrderTime = console.nextLine();
                }

                String insertOrderTimeQuery = "insert into OrderTime(customerID,branchID,productID,Otime) values ('" + customerId
                                            + "', '" + branchId + "', '" + productId + "', '" + OrderTime + "')";
                statement.executeUpdate(insertOrderTimeQuery);

                String selectedStockQuery = "select * from stock";
                ResultSet rst = statement.executeQuery(selectedStockQuery);
                int stockNumber = 0;
                while(rst.next()){
                    if(branchId == rst.getInt(1) && productId == rst.getInt(2)) {
                        stockNumber = rst.getInt(3);
                    }
                }
                stockNumber--;
                String updateStockQuery = "update stock set stockquantity = " + stockNumber + " where (branchID = " + branchId
                                                        + " and " + " productID = " + productId + ")";
                statement.executeUpdate(updateStockQuery);
                closeConnection();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        else if (OperationNumber==13) {
            establishConnection();
            System.exit(0);
            closeConnection();
        }
    }
    public static void main(String[] args) {
        while (OperationNumber != 13) {
            Display_SelectOperation();
            SelectOperation();
        }
    }
}