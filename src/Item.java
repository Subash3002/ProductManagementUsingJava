public class Item {
    private String productId;
    private String productName;
    private String orderId;
    private String categoryId;
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private int productQuantity;
    private double productPrice;

    public Item(Product product, int quantity,String orderId){
        this.productName=product.getProductName();
        this.productId= product.getProductId();
        this.orderId=orderId;
        this.productPrice=product.getProductPrice();
        this.productQuantity=quantity;
        this.categoryId=product.getCategoryId();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
