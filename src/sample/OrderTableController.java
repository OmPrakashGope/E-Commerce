package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrderTableController implements Initializable {

    ObservableList<Product> productList;
    @FXML
    private TableView<Product> orderTable;
    @FXML
    private Button backToButton = new Button();
    @FXML
    private Button removeButton = new Button();

    public void setRemoveButton() throws IOException {
        Product product = getSelectedProduct();
        Order order = Order.getOrder(Controller.loggedInCustomer.getId(),product.getId());
        String Query = "delete from orders where oid = " + order.getOid()+";" ;
        boolean updated = DatabaseConnection.insertUpdate(Query);
        System.out.println(updated + " " + Query);
        ProductSearchController productSearchController = new ProductSearchController();
//        productSearchController.setOrderButton();
        String query = "select products.pid,products.name,products.price,orders.ts,orders.quantity from orders inner join products on orders.product_id = products.pid where customer_id = "+Controller.loggedInCustomer.getId()+";";
        productList = getProducts(query);
        orderTable.setItems(productList);
    }

    public void setBackToButton() throws IOException {
        Stage stage = (Stage) backToButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Table .fxml"));
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "select products.pid,products.name,products.price,orders.ts,orders.quantity from orders inner join products on orders.product_id = products.pid where customer_id = "+Controller.loggedInCustomer.getId()+";";
//        TableColumn id = new TableColumn("Ida");
//        id.setCellValueFactory(new PropertyValueFactory("pid"));
        TableColumn name = new TableColumn("Product");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory("price"));
        TableColumn time = new TableColumn("Time");
        time.setCellValueFactory(new PropertyValueFactory("time"));
        TableColumn quantity = new TableColumn("Quantity");
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        productList = getProducts(query);
        orderTable.setItems(productList);
        orderTable.getColumns().addAll(name, price,time,quantity);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    public Product getSelectedProduct()
    {
        return orderTable.getSelectionModel().getSelectedItem();
    }

    public static ObservableList<Product> getProducts(String query) {
        DatabaseConnection dbConn = new DatabaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Product> result = FXCollections.observableArrayList();
        try {
            if (rs != null) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("pid"), rs.getString("name"), rs.getDouble("price"),rs.getTimestamp("ts"),rs.getInt("quantity")));
                    System.out.println(rs.getDouble("price"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
