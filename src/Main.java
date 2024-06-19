
import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        String fileName="inventory.txt";
        loadInventoryFromFile(fileName);



        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("Press 1 for Seller | Press 2 for Customer | Press 3 to Exit");
            int choose=sc.nextInt();

            if(choose==1){
                System.out.println("Enter Seller Name: ");
                String sellerName=sc.next();
                Seller seller=new Seller(sellerName);
                while (true){
                    System.out.println("1.Add Product \n2.Remove Product \n3.update Quantity \n4.update Price \n5.update ProductName \n6.archieve product \n7.display products");
                    int ch=sc.nextInt();
                    int productId;
                    switch (ch) {
                        case 1:
                            System.out.println("Product Name: ");
                            String productName = sc.next();
//                            System.out.println("Product Id:");
//                            productId=sc.nextInt();
                            System.out.println("Product Quantity: ");
                            int quantity = sc.nextInt();
                            System.out.println("Product Price: ");
                            float price = sc.nextFloat();
                            Product product = new Product(productName, quantity, price);
                            seller.addProduct(product);
                            break;

                        case 2:
                            System.out.println("ProductId you want to Delete");
                            productId = sc.nextInt();
                            seller.removeProduct(productId);
                            break;

                        case 3:
                            System.out.println("ProductId you want to update");
                            productId = sc.nextInt();
                            System.out.println("Product Quantity");
                            int q = sc.nextInt();
                            seller.update(q, productId);
                            break;

                        case 4:
                            System.out.println("ProductId you want to update");
                            int pId = sc.nextInt();
                            System.out.println("Product Price");
                            float prce = sc.nextFloat();
                            seller.update(prce, pId);
                            break;

                        case 5:
                            System.out.println("ProductId you want to update");
                            int pid = sc.nextInt();
                            System.out.println("Product Name");
                            String name = sc.next();
                            seller.update(name, pid);
                            break;

                        case 6:
                            System.out.println("ProductId you want to archive");
                            productId = sc.nextInt();
                            seller.archive(productId);
                            break;

                        case 7:
                            seller.displayProducts();
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }
                    System.out.println("Do you want to continue:(y/n)");
                    char f=sc.next().charAt(0);
                    if(f=='n'){
                        break;
                    }
                }
            }else if(choose==2){

                System.out.println("Enter Customer Name: ");
                String customerName=sc.next();
                Customer customer=new Customer(customerName);
                while (true) {

                    System.out.println("1.Buy Product \n2.View Products \n3.view specific Product \n4.view cart ");
                    int ch = sc.nextInt();

                    switch (ch) {
                        case 1:
                            System.out.println("Enter ProductId: ");
                            int pId = sc.nextInt();
                            System.out.println("Enter Quantity: ");
                            int quantity = sc.nextInt();
                            customer.buyProduct(pId, quantity);
                            break;

                        case 2:
                            customer.displayProducts();
                            break;

                        case 3:
                            System.out.println("Enter ProductId");
                            int productId = sc.nextInt();
                            customer.displayProduct(productId);
                            break;

                        case 4:
                            customer.displayCart();
                            break;


                        default:
                            System.out.println("Invalid choice");

                    }
                    System.out.println("Do you want to continue:(y/n)");
                    char f=sc.next().charAt(0);
                    if(f=='n'){
                        break;
                    }
                }
            }else{
                System.out.println("ThankYou ");
                break;
            }

        }


    }
    public static void loadInventoryFromFile(String fileName){


        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int productId = Integer.parseInt(parts[0]);
                String productName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                float price = Float.parseFloat(parts[3]);
                boolean exists = Boolean.parseBoolean(parts[4].trim());

                // Create a Product object and add to HashMap
                Product product = new Product(productId, productName, quantity, price, exists);
                Product.products.put(productId, product);
            }
            System.out.println("Inventory loaded from " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}