
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

        MongoDBConnection.createProducts(product);
        boolean isAdded=product.addProduct(product);

    }

    public void removeProduct(String productId){
        if(Product.products.containsKey(productId)){
            Product.products.get(productId).setExist(false);
            MongoDBConnection.deleteProductById(productId);

            System.out.println("Product Removed Successfully");
        }else{
            System.out.println("Item not found");
        }
    }

    public void update(String productName,String productId){
        if(Product.products.containsKey(productId)){
            Product product=Product.products.get(productId);
            product.setProductName(productName);
            MongoDBConnection.updateProductNameById(productId,productName);
        }else{
            System.out.println("Not Found");
        }
    }

    public void update(int productQuantity,String productId){

        if(Product.products.containsKey(productId)){

            Product product=Product.products.get(productId);
            if(productQuantity<=0){
                System.out.println("Product Quantity is Invalid");
            }else{
                product.setProductQuantity(productQuantity);
                MongoDBConnection.updateProductQuantityById(productId,productQuantity);
            }

        }else{
            System.out.println("Not Found");
        }
    }

    public void update(double productPrice,String productId){
        if(Product.products.containsKey(productId)){
            Product product=Product.products.get(productId);
            if(productPrice<=0){
                System.out.println("Price is Invalid");
            }else{
                product.setProductPrice(productPrice);
                MongoDBConnection.updateProductPriceById(productId,productPrice);
            }

        }else{
            System.out.println("Not Found");
        }
    }



    void displayProducts(){
        MongoDBConnection.displayProductsTableToSeller();
    }

}
