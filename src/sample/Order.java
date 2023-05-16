package sample;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    int oid;
    int quantity;

    public Order(int oid, int quantity) {
        this.oid = oid;gi
        this.quantity = quantity;
    }

    public Order(int oid) {
        this.oid = oid;
    }

    public int getOid() {
        return oid;
    }

    public int getQuantity() {
        return quantity;
    }
    public static boolean placeOrderCart(Customer customer, Product product)
    {
        try
        {
            String query = "select * from orders where customer_id = " +customer.getId()+" and product_id = " +product.getId();
            ResultSet rs = DatabaseConnection.getQueryTable(query);
            Cart cart = Cart.getquantity(customer.getId(),product.getId());
            int cartQuantity = cart.getQuantity();
            System.out.println(query);
            if(rs.next())
            {
                Order order = getQuantity(customer.getId(),product.getId());
                int quantity = order.getQuantity() + cartQuantity;
                int orderNumber = order.getOid();
                System.out.println(orderNumber);
                String Query = "update orders set quantity = "+quantity+" where oid = "+orderNumber+";";
                System.out.println(Query);
                return DatabaseConnection.insertUpdate(Query);
            }
            else {
                String placeOrder = "insert into orders(customer_id,product_id,status,quantity) values(" + customer.getId() + "," + product.getId() + ",'Ordered',"+cartQuantity+"); ";
                System.out.println(customer.getId() + " " + product.getId());
                DatabaseConnection dbConn = new DatabaseConnection();
                return dbConn.insertUpdate(placeOrder);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean placeOrder(Customer customer, Product product)
    {
        try
        {
            String query = "select * from orders where customer_id = " +customer.getId()+" and product_id = " +product.getId();
            ResultSet rs = DatabaseConnection.getQueryTable(query);
            System.out.println(query);
            if(rs.next())
            {
                Order order = getQuantity(customer.getId(),product.getId());
                int quantity = order.getQuantity() + 1;
                int orderNumber = order.getOid();
                System.out.println(orderNumber);
                String Query = "update orders set quantity = "+quantity+" where oid = "+orderNumber+";";
                System.out.println(Query);
                return DatabaseConnection.insertUpdate(Query);
            }
            else {
                String placeOrder = "insert into orders(customer_id,product_id,status) values(" + customer.getId() + "," + product.getId() + ",'Ordered'); ";
                System.out.println(customer.getId() + " " + product.getId());
                DatabaseConnection dbConn = new DatabaseConnection();
                return dbConn.insertUpdate(placeOrder);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static int placeOrderMultipleProducts(ObservableList<Product> productObservableList,Customer customer)
    {
        int count = 0;
        for(Product product : productObservableList){
            System.out.println(product);
            if(placeOrderCart(customer,product)) {
                Cart cart = Cart.getCart(Controller.loggedInCustomer.getId(),product.getId());
                String query = "delete from cart where cart_id = " + cart.getCart_id()+"";
                System.out.println(query);
                DatabaseConnection.insertUpdate(query);
                count++;
            }
        }
        return count;
    }
    public static Order getOrder(int customer_id, int product_id) {
        String query = "Select oid from orders where customer_id = " + customer_id + " and product_id = " + product_id + ";";
        System.out.println(query);
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = null;
        try {
            rs = dbConn.getQueryTable(query);
            System.out.println(rs);
            if (rs != null && rs.next()) {
                return new Order(rs.getInt("oid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(rs.getInt("cid"));
        return null;
    }
    public static Order getQuantity(int customer_id, int product_id) {
        String query = "Select oid,quantity from orders where customer_id = " + customer_id + " and product_id = " + product_id + ";";
        System.out.println(query);
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = null;
        try {
            rs = dbConn.getQueryTable(query);
            if (rs != null && rs.next()) {
                return new Order(rs.getInt("oid"),rs.getInt("quantity"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
