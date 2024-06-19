public class Item {
    private String productName;
    private int productId;
    private int productQuantity;
    private float productPrice;

    public Item(Product product, int quantity){
        this.productName=product.getProductName();
        this.productId=product.getProductId();
        this.productPrice=product.getProductPrice();
        this.productQuantity=quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
}
