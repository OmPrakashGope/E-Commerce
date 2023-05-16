package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ProductSearchController implements Initializable {

    TableColumn<Product,String> id = new TableColumn<Product,String>("Oda");
    TableColumn quantity = new TableColumn("Quantity");
    ObservableList<Product> cartItemList = FXCollections.observableArrayList();
    @FXML
    private TextField searchField = new TextField();
    @FXML
    private TableView<Product> tableView;
    @FXML
    private Button buyNowButton = new Button();
    @FXML
    private Button addToCartButton = new Button();
    @FXML
    private Button cartButton = new Button();
    @FXML
    private Button cartBuyButton = new Button();
    @FXML
    private Button removeButton = new Button();
    @FXML
    private Button orderButton = new Button();
    @FXML
    private Button loginPageButton = new Button();
    @FXML
    private Button backToSearchButton = new Button();
    @FXML
    private Button searchButton = new Button();
    @FXML
    private Label tittleLabel = new Label();

    public void setSearchButton()
    {
        String query = "Select * from products where name like '"+searchField.getText()+"%';";
        cartItemList = getProducts(query);
        tableView.setItems(cartItemList);
    }
    public void setBackToSearchButton()
    {
        tittleLabel.setText("Search Products");
        String query =  "Select * from products";
        searchField.setVisible(true);
        searchButton.setVisible(true);
        quantity.setVisible(false);
        loginPageButton.setVisible(true);
        backToSearchButton.setVisible(false);
        cartBuyButton.setVisible(false);
        removeButton.setVisible(false);
        id.setVisible(true);
        buyNowButton.setVisible(true);
        addToCartButton.setVisible(true);
        ObservableList<Product> productList  = getProducts(query);
        tableView.setItems(productList);
    }

    public void setLoginPageButton() throws IOException {
        Stage stage = (Stage) loginPageButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        stage.setScene(new Scene(root));
    }

    public void setOrderButton() throws IOException {
       Stage stage = (Stage) orderButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("OrderTable.fxml"));
        stage.setScene(new Scene(root));
    }

    public void setCartBuyButton()
    {
        int orderCount = 0;
        Controller control = new Controller();
        if( !cartItemList.isEmpty() && Controller.loggedInCustomer != null)
        {
            System.out.println(cartItemList);
            orderCount = Order.placeOrderMultipleProducts(cartItemList,Controller.loggedInCustomer);
        }
        if(orderCount > 0)
        {
            showDialog(orderCount + " products ordered");
            String query = "select products.pid,products.name,products.price,cart.quantity from cart inner join products on cart.product_id = products.pid where customer_id = "+ Controller.loggedInCustomer.getId()+";";
            cartItemList = getCartTable(query);
            tableView.setItems(cartItemList);
        }
        else
        {
            showDialog("Please Log In first");
        }
    }
    public void setRemoveButton()
    {
        Product product = getSelectedProduct();
        Cart cart = Cart.getCart(Controller.loggedInCustomer.getId(),product.getId());
        String Query = "delete from cart where cart_id = " + cart.getCart_id()+"";
        boolean updated = DatabaseConnection.insertUpdate(Query);
        System.out.println(updated + " " + Query);
        setCartButton();
    }
    public void setCartButton()
    {
        String query = "select products.pid,products.name,products.price,cart.quantity from cart inner join products on cart.product_id = products.pid where customer_id = "+ Controller.loggedInCustomer.getId()+";";
        System.out.println(query);
        tittleLabel.setText("Cart");
        cartItemList = getCartTable(query);
        searchField.setVisible(false);
        searchButton.setVisible(false);
        loginPageButton.setVisible(false);
        buyNowButton.setVisible(false);
        addToCartButton.setVisible(false);
        cartBuyButton.setVisible(true);
        removeButton.setVisible(true);
        quantity.setVisible(true);
        backToSearchButton.setVisible(true);
        id.setVisible(false);
        tableView.setItems(cartItemList);
    }
    public void setAddToCartButton()
    {
        Product product = getSelectedProduct();
        addItemsToCart(product);
        System.out.println("Products in cart ");
    }
    private void addItemsToCart(Product product)
    {
        Cart.addCart(Controller.loggedInCustomer,product);
    }
    public void setBuyNowButton()
    {
        Product product = getSelectedProduct();
        boolean orderStatus = false;
        Controller control = new Controller();
        if(product != null && Controller.loggedInCustomer != null)
        {
            orderStatus = Order.placeOrder(Controller.loggedInCustomer,product);
        }
        if(orderStatus == true)
        {
            showDialog("Order Successful");
        }
        else
        {
            showDialog("Please Log In first");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query =  "Select * from products";
        backToSearchButton.setVisible(false);
        cartBuyButton.setVisible(false);
        removeButton.setVisible(false);
        quantity.setVisible(false);
        id.setCellValueFactory(new PropertyValueFactory<Product,String>("id"));
        TableColumn<Product,String> name = new TableColumn<Product,String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        TableColumn<Product,String> price = new TableColumn<Product,String>("Price");
        price.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        ObservableList<Product> productList  = getProducts(query);
        tableView.setItems(productList);
        tableView.getColumns().addAll(id,name,price,quantity);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public static ObservableList<Product> getProducts(String query)
    {
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Product> result = FXCollections.observableArrayList();
        try{
            if(rs != null) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("pid"), rs.getString("name"), rs.getDouble("price"),rs.getInt("quantity")));
                    System.out.println(rs.getDouble("price"));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public  Product getSelectedProduct()
    {
        return tableView.getSelectionModel().getSelectedItem();
    }
     public void showDialog(String message) {
         Dialog<String> dialog = new Dialog<String>();
         dialog.setTitle("Order Status");
         ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
         //Setting the content of the dialog
         dialog.setContentText(message);
         //Adding buttons to the dialog pane
         dialog.getDialogPane().getButtonTypes().add(type);
//         dialog.showAndWait();
         dialog.show();
     }
    public static ObservableList<Product> getCartTable(String query)
    {
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Product> result = FXCollections.observableArrayList();
        try{
            if(rs != null) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("pid"), rs.getString("name"), rs.getDouble("price"),rs.getInt("quantity")));
//                    System.out.println(rs.getDouble("price"));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}


