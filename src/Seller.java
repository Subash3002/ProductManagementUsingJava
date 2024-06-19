
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Seller {
    private String sellerName;

    public Seller(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
    public void addProduct(Product product){
        boolean isAdded=product.addProduct(product);
        if(isAdded)saveInventoryToFile();
    }

    public void removeProduct(int productId){
        if(Product.products.containsKey(productId)){
            Product.products.get(productId).setExist(false);
            saveInventoryToFile();
            System.out.println("Product Removed Successfully");
        }else{
            System.out.println("Item not found");
        }
    }

    public void update(String productName,int productId){
        if(Product.products.containsKey(productId)){
            Product product=Product.products.get(productId);
            product.setProductName(productName);
            saveInventoryToFile();
        }else{
            System.out.println("Not Found");
        }
    }

    public void update(int productQuantity,int productId){

        if(Product.products.containsKey(productId)){
            Product product=Product.products.get(productId);
            if(productQuantity<=0){
                System.out.println("Product Quantity is Invalid");
            }else{
                product.setProductQuantity(productQuantity);
                saveInventoryToFile();
            }

        }else{
            System.out.println("Not Found");
        }
    }

    public void update(float productPrice,int productId){
        if(Product.products.containsKey(productId)){
            Product product=Product.products.get(productId);
            if(productPrice<=0){
                System.out.println("Price is Invalid");
            }else{
                product.setProductPrice(productPrice);
                saveInventoryToFile();
            }

        }else{
            System.out.println("Not Found");
        }
    }

    public static void saveInventoryToFile() {
        String fileName = "inventory.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("ProductID,ProductName,Quantity,Price,Product Exist");
            for (Product product : Product.products.values()) {
                writer.printf("%d,%s,%d,%.2f,%b%n", product.getProductId(), product.getProductName(),
                        product.getProductQuantity(), product.getProductPrice(),product.isExist());
            }
            System.out.println("Inventory saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    void archive(int productId){
        if(Product.products.containsKey(productId)){
            Product.products.get(productId).setExist(true);
            saveInventoryToFile();
            System.out.println("Product Archived successfully");
        }else{
            System.out.println("Product Not Found");
        }
    }

    void displayProducts(){
        for(Product product:Product.products.values()){

            System.out.print("Product Name: "+product.getProductName()+"| ");
            System.out.print("Product Id: "+product.getProductId()+"| ");
            System.out.print("Product Quantity: "+product.getProductQuantity()+"| ");
            System.out.print("Product Price: "+product.getProductPrice()+"| ");
            System.out.print("Product Exist Status: "+product.isExist()+"| ");
            System.out.println("\n------------------");
        }
    }

}
