
import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
//        String fileName="inventory.txt";
//        loadInventoryFromFile(fileName);
//        PostgreSQLConnection.createOrderTable();
        MongoDBConnection.connectMongo();
        MongoDBConnection.readProductsFromDatabase();
        Scanner sc=new Scanner(System.in);

        while(true){
            System.out.println("Press 1 for Seller | Press 2 for Customer | Press 3 to Exit");
            int choose=sc.nextInt();

            if(choose==1){
                System.out.println("Enter Seller Name: ");
                String sellerName=sc.next();
                Seller seller=new Seller(sellerName);
                while (true){
                    System.out.println("1.Add Product \n2.Remove Product \n3.update Quantity \n4.update Price \n5.update ProductName \n6.display products");
                    int ch=sc.nextInt();
                    String productId;
                    switch (ch) {
                        case 1:
                            System.out.println("Product Name: ");
                            String productName = sc.next();
                            if(productName.isEmpty()){
                                System.out.println("Product Name is Empty");
                                break;
                            }
                            System.out.println("Product Quantity: ");
                            int quantity = sc.nextInt();
                            if(quantity<=0){
                                System.out.println("Enter valid quantity");
                                break;
                            }
                            System.out.println("Enter Product Price: ");
                            double price = sc.nextDouble();
                            System.out.println("Enter category name");
                            String catId=sc.next();

                            if(price<=0){
                                System.out.println("Enter valid price");
                                break;
                            }
                            MongoDBConnection.addCategory(catId);
                            int findId=MongoDBConnection.findMaxProductId();
                            Product product = new Product("prod"+(findId+1),productName, quantity, price,catId);
                            seller.addProduct(product);
                            break;

                        case 2:
                            System.out.println("ProductId you want to Delete");
                            productId = sc.next();

                            seller.removeProduct(productId);
                            break;

                        case 3:
                            System.out.println("ProductId you want to update");
                            productId = sc.next();
                            System.out.println("Product Quantity");
                            int q = sc.nextInt();
                            seller.update(q, productId);
                            break;

                        case 4:
                            System.out.println("ProductId you want to update");
                            productId= sc.next();
                            System.out.println("Product Price");
                            double prce = sc.nextDouble();
                            seller.update(prce, productId);
                            break;

                        case 5:
                            System.out.println("ProductId you want to update");
                            productId= sc.next();
                            System.out.println("Product Name");
                            String name = sc.next();
                            seller.update(name, productId);
                            break;


                        case 6:
                            seller.displayProducts();
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }
                    System.out.println("Do you want to continue as seller:(y/n)");
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

                    System.out.println("1.Buy Product \n2.View Products \n3.view specific Product \n4.view Purchase History ");
                    int ch = sc.nextInt();
                    String productId;
                    switch (ch) {
                        case 1:
                            while(true){
                                System.out.println("Enter ProductId: ");
                                productId= sc.next();
                                if(!(Product.products.containsKey(productId) && Product.products.get(productId).isExist())){
                                    System.out.println("ProductId Not Found");
                                    break;
                                }
                                System.out.println("Enter Quantity: ");
                                int quantity = sc.nextInt();
                                if(quantity<=0){
                                    System.out.println("Invalid Quantity");
                                    continue;
                                }
                                customer.buyProduct(productId, quantity);
                                System.out.println("Buy more(y/n) :");
                                String in=sc.next();
                                if(in.equals("n")){
                                    customer.addOrder();
                                    break;
                                }
                            }

                            break;

                        case 2:
                            customer.displayProducts();
                            break;

                        case 3:
                            System.out.println("Enter ProductId");
                            productId = sc.next();
                            customer.displayProducts(productId);
                            break;

                        case 4:
                            PostgreSQLConnection.printAllOrders();
                            break;


                        default:
                            System.out.println("Invalid choice");

                    }
                    System.out.println("Do you want to continue as customer:(y/n)");
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

}