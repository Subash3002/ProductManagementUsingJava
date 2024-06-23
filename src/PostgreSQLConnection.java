import java.sql.*;

public class PostgreSQLConnection {
    public static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "1234";
    public static void main(String[] args) {
        // Connection URL


        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create a statement object
            stmt = conn.createStatement();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void createOrderTable() {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create a statement object
            stmt = conn.createStatement();

            // SQL statement to create the table
            String sql = "CREATE TABLE orders(" +
                    "orderId VARCHAR(200) PRIMARY KEY, " +

                    "totalQuantity INT NOT NULL, " +
                    "totalPrice DOUBLE PRECISION NOT NULL, " ;


            // Execute the statement
            stmt.executeUpdate(sql);
            System.out.println("Table 'orders' created successfully!");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void insertOrder(String orderId, int totalQuantity, double totalPrice,int totalItems) {


        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO orders (\"orderId\", \"totalQuantity\", \"totalPrice\",\"totalProducts\") VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, orderId);
                preparedStatement.setInt(2, totalQuantity);
                preparedStatement.setDouble(3, totalPrice);
                preparedStatement.setInt(4,totalItems);
                preparedStatement.executeUpdate();
                System.out.println("Order placed successfully,Total price = "+totalPrice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllOrders() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String orderQuery = "SELECT \"orderId\", \"totalQuantity\", \"totalPrice\", \"totalProducts\" FROM orders";
            ResultSet orderResultSet = statement.executeQuery(orderQuery);

            int totalOrders = 0;

            while (orderResultSet.next()) {
                String orderId = orderResultSet.getString("orderId");
                int totalQuantity = orderResultSet.getInt("totalQuantity");
                double totalPrice = orderResultSet.getDouble("totalPrice");
                int totalItems = orderResultSet.getInt("totalProducts");
                totalOrders++;

                System.out.println("Order ID: " + orderId + ", Total Quantity: " + totalQuantity + ", Total Price: " + totalPrice + ", Total Items: " + totalItems);

                // Query to get the order items for the current orderId
                String orderItemsQuery = "SELECT \"productId\", \"productName\", \"productQuantity\", \"productPrice\" FROM \"orderItems\" WHERE \"orderId\" = ?";
                try (PreparedStatement orderItemsStatement = connection.prepareStatement(orderItemsQuery)) {
                    orderItemsStatement.setString(1, orderId);
                    try (ResultSet orderItemsResultSet = orderItemsStatement.executeQuery()) {
                        int itemCounter = 0;
                        while (orderItemsResultSet.next()) {
                            itemCounter++;
                            String productId = orderItemsResultSet.getString("productId");
                            String productName = orderItemsResultSet.getString("productName");
                            int productQuantity = orderItemsResultSet.getInt("productQuantity");
                            double productPrice = orderItemsResultSet.getDouble("productPrice");

                            System.out.printf("  %d. Product ID: %s, Product Name: %s, Product Quantity: %d, Product Price: %.2f%n", itemCounter, productId, productName, productQuantity, productPrice);
                        }
                    }
                }
            }

            if (totalOrders == 0) {
                System.out.println("There are no orders placed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int findMaxOrderId() {
        int maxOrderId = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT COUNT(*) AS maxOrderId FROM orders";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                maxOrderId = resultSet.getInt("maxOrderId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxOrderId;
    }

    public static void insertOrderItem(String orderId, String productId,  int productQuantity, double productPrice,String productName) {
        String insertSQL = "INSERT INTO \"orderItems\" (\"orderId\", \"productId\",  \"productQuantity\", \"productPrice\",\"productName\") VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, orderId);
            preparedStatement.setString(2, productId);
            preparedStatement.setInt(3, productQuantity);
            preparedStatement.setDouble(4, productPrice);
            preparedStatement.setString(5, productName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order item inserted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
