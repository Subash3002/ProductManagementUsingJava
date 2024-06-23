
import java.util.HashMap;


public class Customer {
    private String customerName;
    static HashMap<String,Item> purchasedItems =new HashMap<>();

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void buyProduct(String productId,int quantity){
        boolean bought=MongoDBConnection.buyProduct(productId,quantity);
        if(bought){
            Product product=Product.products.get(productId);
            String orderId="ord"+(PostgreSQLConnection.findMaxOrderId()+1);
            if(purchasedItems.containsKey(productId)){
                Item orderItem=purchasedItems.get(productId);
                orderItem.setProductQuantity(orderItem.getProductQuantity()+quantity);
            }else{
                purchasedItems.put(productId,new Item(product,quantity,orderId));
            }
            int newQuantity = product.getProductQuantity()-quantity;
            product.setProductQuantity(newQuantity);

        }
    }


    public void displayPurchaseHistory(){
        if(purchasedItems.isEmpty()){
            System.out.println("Cart is Empty");
            return;
        }
        double total=0;
        for(Item item: purchasedItems.values()){
            total+=item.getProductPrice()*item.getProductQuantity();
            System.out.print(item.getProductName()+"| ");
            System.out.print(item.getProductId()+"| ");
            System.out.print(item.getProductQuantity()+"| ");
            System.out.print(item.getProductPrice()+"| ");
            System.out.println("\n------------------");
        }
        System.out.println("Total = "+total);
    }


    public void addOrder(){
        int totalQuantity=0;
        double totalPrice=0;
        String orderId ="";
        int totalItems=0;

        for(Item item:purchasedItems.values()){
            totalItems++;
            PostgreSQLConnection.insertOrderItem(item.getOrderId(),item.getProductId(),item.getProductQuantity(),item.getProductPrice(),item.getProductName());
            orderId=item.getOrderId();
            totalQuantity+=item.getProductQuantity();
            totalPrice+=(item.getProductQuantity()*item.getProductPrice());
        }

        if(totalItems>0){
            PostgreSQLConnection.insertOrder(orderId,totalQuantity,totalPrice,totalItems);
        }

        purchasedItems=new HashMap<>();
    }

    void displayProducts(){
        MongoDBConnection.displayProductsTableToCustomer();
    }


    void displayProducts(String productId){
        MongoDBConnection.displayProduct(productId);
    }

}
