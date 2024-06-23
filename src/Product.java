
import org.bson.Document;

import java.util.HashMap;

public class Product {
    private String productName;
    private String productId;
    private int productQuantity;
    private double productPrice;
    private boolean isExist=true;
    private String categoryId;

    static HashMap<String,Product> products=new HashMap<>();

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Product(String productId,String productName, int productQuantity, double productPrice, String categoryId){
        this.productName = productName;
        this.productId=productId;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.categoryId=categoryId;
    }

    public Product(String productId,String productName, int productQuantity, double productPrice, boolean isExist) {
        this.productName = productName;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.isExist = isExist;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean addProduct(Product product){
        if(!products.containsKey(product.getProductId())){
            products.put(product.productId,product);
            return true;
        }else{
            System.out.println("Product Id already exists!");
            return false;
        }

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public static Product fromDocument(Document doc) {
        String id = doc.getString("productId");
        String name = doc.getString("productName");
        Double price = doc.containsKey("productPrice") ? doc.getDouble("productPrice") : 0.0;
        Integer quantity = doc.containsKey("productQuantity") ? doc.getInteger("productQuantity") : 0;
        return new Product(id, name, quantity,price,true);
    }

}
