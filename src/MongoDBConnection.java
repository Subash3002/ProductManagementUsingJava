import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MongoDBConnection {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "db1";
    private static MongoCollection<Document> collection;

    public static void connectMongo() {
        String connectionString = "mongodb://localhost:27017";

        // Create a MongoClient
        MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);

        // Access the database
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection("products");
        // Print the database name to verify connection
        System.out.println("Connected to database: " + database.getName());

    }


    public static void createProducts(Product p) {
        Document product = new Document("productId",p.getProductId())
                .append("productName", p.getProductName())
                .append("productQuantity", p.getProductQuantity())
                .append("productPrice", p.getProductPrice())
                .append("categoryName", p.getCategoryId())
                .append("productExist",true);
        // Create the collection and insert the document

        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Create the collection (if it doesn't exist) and insert the document
            MongoCollection<Document> collection = database.getCollection("products");
            collection.insertOne(product);

            System.out.println("Product document inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductById(String productId) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Create a filter to match the productId
            Document filter = new Document("productId", productId);

            // Create an update operation to set the new productQuantity value
            Document update = new Document("$set", new Document("productExist", false));

            // Perform the update operation
            collection.updateOne(filter, update);

//            System.out.println("Product Quantity updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void archiveProductById(String productId) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Create a filter to match the productId
            Document filter = new Document("productId", productId);

            // Create an update operation to set the new productQuantity value
            Document update = new Document("$set", new Document("productExist", true));

            // Perform the update operation
            collection.updateOne(filter, update);

//            System.out.println("Product Quantity updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductQuantityById(String productId, int newQuantity) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Create a filter to match the productId
            Document filter = new Document("productId", productId);

            // Create an update operation to set the new productQuantity value
            Document update = new Document("$set", new Document("productQuantity", newQuantity));

            // Perform the update operation
            collection.updateOne(filter, update);

            System.out.println("Product Quantity updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductPriceById(String productId, double newPrice) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Create a filter to match the productId
            Document filter = new Document("productId", productId);

            // Create an update operation to set the new productPrice value
            Document update = new Document("$set", new Document("productPrice", newPrice));

            // Perform the update operation
            collection.updateOne(filter, update);

            System.out.println("Product Price updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateProductNameById(String productId, String newName) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Create a filter to match the productId
            Document filter = new Document("productId", productId);

            // Create an update operation to set the new productName value
            Document update = new Document("$set", new Document("productName", newName));

            // Perform the update operation
            collection.updateOne(filter, update);

            System.out.println("Product Name updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void displayProductsTableToSeller() {
        int productCount=0;
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Find all documents in the collection
            FindIterable<Document> documents = collection.find();

            // Print each document's fields

            for (Document document : documents) {

                String productId = document.getString("productId");
                String productName = document.getString("productName");
                int productQuantity = document.getInteger("productQuantity", 0);
                double productPrice = document.getDouble("productPrice");
                String categoryName = document.getString("categoryName");
                boolean productExist = document.getBoolean("productExist", true);
                productCount++;
                if(productCount==1){
                    System.out.println("Products Table:");
                }
                System.out.printf("ProductId: %s, ProductName: %s, ProductQuantity: %d, ProductPrice: %.2f, CategoryName: %s, ProductExist: %b%n",
                        productId, productName, productQuantity, productPrice, categoryName, productExist);
            }
            if(productCount==0){
                System.out.println("No Products in store,Add First");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayProductsTableToCustomer() {
        int productCount=0;
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Find all documents in the collection
            FindIterable<Document> documents = collection.find();

            // Print each document's fields

            for (Document document : documents) {
                String productId = document.getString("productId");
                String productName = document.getString("productName");
                int productQuantity = document.getInteger("productQuantity", 0);
                double productPrice = document.getDouble("productPrice");
                String categoryName = document.getString("categoryName");
                boolean productExist = document.getBoolean("productExist",false);

                if(productExist){
                    productCount++;
                    if(productCount==1)System.out.println("Products Table:");
                    System.out.printf("ProductId: %s, ProductName: %s, ProductQuantity: %d, ProductPrice: %.2f, CategoryName: %s%n",
                            productId, productName, productQuantity, productPrice, categoryName);

                }
            }
            if(productCount==0){
                System.out.println("There is no products to show!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayProduct(String searchProductId) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Query for the specific product by productId
            Document query = new Document("productId", searchProductId);
            Document document = collection.find(query).first();

            if (document != null) {
                boolean productExist = document.getBoolean("productExist", false);
                if (productExist) {
                    // Extract and print the product's fields
                    String productId = document.getString("productId");
                    String productName = document.getString("productName");
                    int productQuantity = document.getInteger("productQuantity", 0);
                    double productPrice = document.getDouble("productPrice");
                    String categoryName = document.getString("categoryName");

                    System.out.printf("ProductId: %s, ProductName: %s, ProductQuantity: %d, ProductPrice: %.2f, CategoryName: %s%n",
                            productId, productName, productQuantity, productPrice, categoryName);
                } else {
                    // Print a message if the product exists but productExist is false
                    System.out.println("Product not found.");
                }
            } else {
                // Print a message if the product is not found
                System.out.println("Product not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static MongoCollection<Document> getCollection() {
        return collection;
    }

    public static void readProductsFromDatabase() {
        FindIterable<Document> documents = collection.find();
        for (Document doc : documents) {
            Product product = null;
            try {
                product = Product.fromDocument(doc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Product.products.put(product.getProductId(), product);
        }
    }

    public static int findMaxProductId() {

        int maxNumber = -1;

        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);


            FindIterable<Document> documents = collection.find();
            Pattern pattern = Pattern.compile("prod(\\d+)");

            for (Document document : documents) {
                String productId = document.getString("productId");
                Matcher matcher = pattern.matcher(productId);
                if (matcher.matches()) {
                    int number = Integer.parseInt(matcher.group(1));
                    if (number > maxNumber) {
                        maxNumber = number;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxNumber;
    }

    public static void addCategory(String categoryName) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("db1");
        MongoCollection<Document> categories = database.getCollection("category");

        try {

            Document existingCategory = categories.find(Filters.eq("categoryName", categoryName)).first();

            if (existingCategory == null) {
                // Category does not exist, so add it
                Document categoryDocument = new Document();
                long categoryCount = categories.countDocuments();
                int categoryId = generateCategoryId((int) categoryCount);
                categoryDocument.append("categoryName", categoryName);
                categoryDocument.append("categoryId", "Cat" + categoryId);
                categories.insertOne(categoryDocument);

                System.out.println("Category '" + categoryName + "' added.");
            }
        } catch (Exception e) {
            System.err.println("Error adding category: " + e.getMessage());
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }
    private static int generateCategoryId(int existingCount) {
        return existingCount + 1;
    }

    public static boolean buyProduct(String productId, int quantityToBuy) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // Get the products collection
            MongoCollection<Document> collection = database.getCollection("products");

            // Query for the specific product by productId
            Document query = new Document("productId", productId);
            Document document = collection.find(query).first();

            if (document != null) {
                boolean productExist = document.getBoolean("productExist", false);
                if (productExist) {
                    int productQuantity = document.getInteger("productQuantity", 0);

                    if (productQuantity >= quantityToBuy) {
                        // Update the product quantity

                        int newQuantity = productQuantity - quantityToBuy;
                        Document update = new Document("$set", new Document("productQuantity", newQuantity));
                        collection.updateOne(query, update);

                        System.out.printf("Bought %d units of productId: %s.%n", quantityToBuy, productId);
                        return true;
                    } else {
                        System.out.println("Insufficient quantity available.");
                    }
                } else {
                    System.out.println("Product not found.");
                }
            } else {
                System.out.println("Product not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}
