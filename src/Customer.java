import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Customer {
    private String customerName;

    List<Item> cart=new ArrayList<>();

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void buyProduct(int productId,int quantity){
        if(Product.products.containsKey(productId) && Product.products.get(productId).isExist()){
            Product product=Product.products.get(productId);
            if(quantity>0 && product.getProductQuantity()>=quantity){
                product.setProductQuantity(product.getProductQuantity()-quantity);

                Item item=new Item(product,quantity);
                cart.add(item);
                if(product.getProductQuantity()<=0)product.setExist(false);
                new Thread(Seller::saveInventoryToFile).start();
                new Thread(this::saveCartToFile).start();
//                Seller.saveInventoryToFile();
//                saveCartToFile();
            }else{
                if(quantity>product.getProductQuantity())System.out.println("Quantity is higher than the stock");
                else System.out.println("Enter valid quantity");
            }
        }else{
            System.out.println("Product Not Found to Buy");
        }
    }

    public void displayCart(){
        if(cart.isEmpty()){
            System.out.println("Cart is Empty");
            return;
        }
        float total=0;
        for(Item item:cart){
            total+=item.getProductPrice()*item.getProductQuantity();
            System.out.print(item.getProductName()+"| ");
            System.out.print(item.getProductId()+"| ");
            System.out.print(item.getProductQuantity()+"| ");
            System.out.print(item.getProductPrice()+"| ");
            System.out.println("\n------------------");
        }
        System.out.println("Total = "+total);
    }

    public void displayProduct(int productId){
        if(Product.products.containsKey(productId) && Product.products.get(productId).isExist()){
            Product product=Product.products.get(productId);
            System.out.print("Product Name: "+product.getProductName()+"| ");
            System.out.print("Product Id: "+product.getProductId()+"| ");
            System.out.print("Product Quantity: "+product.getProductQuantity()+"| ");
            System.out.print("Product Price: "+product.getProductPrice()+"| ");
            System.out.println("\n------------------");
        }else{
            System.out.println("Product Not Found");
        }
    }

    public void saveCartToFile() {
        String fileName = "cart.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Customer: " + customerName);
            writer.newLine();
            writer.write("ProductID,ProductName,Quantity,Price");
            writer.newLine();
            for (Item item : cart) {
                writer.write(String.format("%d,%s,%d,%.2f%n", item.getProductId(), item.getProductName(),
                        item.getProductQuantity(), item.getProductPrice()));
            }
            System.out.println("Cart saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void displayProducts() {


        for(Product product:Product.products.values()){
            if(product.isExist()){
                System.out.print("Product Name: "+product.getProductName()+"| ");
                System.out.print("Product Id: "+product.getProductId()+"| ");
                System.out.print("Product Quantity: "+product.getProductQuantity()+"| ");
                System.out.print("Product Price: "+product.getProductPrice()+"| ");
                System.out.println("\n------------------");
            }
        }


    }









}
