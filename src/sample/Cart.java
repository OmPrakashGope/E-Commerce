package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Cart {
    int cart_id;
    int customer_id;
    int product_id;
    int quantity;

    public Cart(int cart_id, int customer_id, int product_id, int quantity) {
        this.cart_id = cart_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public Cart(int cart_id, int quantity) {
        this.cart_id = cart_id;
        this.quantity = quantity;
    }

    public Cart(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public int getQuantity() {
        return quantity;
    }


    public static boolean addCart(Customer customer, Product product) {
        try {
            String query = "select * from cart where customer_id ="+ customer.getId()+" and product_id = " + product.getId()+";";
            DatabaseConnection dbConn = new DatabaseConnection();
            System.out.println(query);
            ResultSet rs  = dbConn.getQueryTable(query);
            if(rs.next())
            {
                System.out.println("hello");
                Cart cart = getquantity(customer.getId(),product.getId());
                int value = cart.getQuantity() + 1;
                String add1 = "UPDATE cart SET quantity = "+value+" WHERE cart_id = "+cart.cart_id+";";
                System.out.println(add1);
                dbConn.insertUpdate(add1);
            }
            else {
                System.out.println("hell01");
                String add = "insert into cart(customer_id,product_id) values(" + customer.getId() + "," + product.getId() + "); ";
                return dbConn.insertUpdate(add);
            }
//


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Cart getCart(int customer_id, int product_id) {
        String query = "Select cart_id from cart where customer_id = " + customer_id + " and product_id = " + product_id + ";";
        System.out.println(query);
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = null;
        try {
            rs = dbConn.getQueryTable(query);
            System.out.println(rs);
            if (rs != null && rs.next()) {
                return new Cart(rs.getInt("cart_id"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(rs.getInt("cid"));
        return null;
    }
    public static Cart getquantity(int customer_id, int product_id) {
        String query = "select * from cart where customer_id = "+ customer_id+" and product_id = " + product_id+";";
//        System.out.println(query);
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = null;
        try {
            rs = dbConn.getQueryTable(query);
//            System.out.println(rs);
            if (rs != null && rs.next()) {
                return new Cart(rs.getInt("cart_id"),rs.getInt("customer_id"),rs.getInt("product_id"),rs.getInt("quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(rs.getInt("cid"));
        return null;
    }
}
