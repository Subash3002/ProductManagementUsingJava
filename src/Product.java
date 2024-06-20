
import java.util.HashMap;

public class Product {
    private String productName;
    private int productId;
    private int productQuantity;
    private float productPrice;
    private boolean isExist=true;

    static HashMap<Integer,Product> products=new HashMap<>();
    public Product(String productName, int productQuantity, float productPrice) {
        this.productName = productName;
        this.productId=products.size()+1;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public Product(int productId,String productName, int productQuantity, float productPrice, boolean isExist) {
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

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
