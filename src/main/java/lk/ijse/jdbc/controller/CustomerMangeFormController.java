package lk.ijse.jdbc.controller;

/*
    @author DanujaV
    @created 3/8/23 - 4:47 PM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.Properties;

public class CustomerMangeFormController {
    private final static String URL = "jdbc:mysql://localhost:3306/ThogaKade";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        try (Connection con = DriverManager.getConnection(URL, props)) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO Customer(id, name, address, salary) " +
                    "VALUES(?, ?, ?, ?)");
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setDouble(4, salary);

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        try (Connection con = DriverManager.getConnection(URL, props)) {
            PreparedStatement pstm = con.prepareStatement("UPDATE Customer SET " + " name=?,address=?, salary=?  WHERE id = ?");

            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setDouble(3, salary);
            pstm.setString(4, id);


            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
            }
        }
    }

    public void btnDltOnAction(ActionEvent actionEvent) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, props)) {
            PreparedStatement pstm = con.prepareStatement("DELETE FROM Customer WHERE id=?");
            pstm.setString(1, txtId.getText());


            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
            }
        }

    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {
       // String id = txtId.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM Customer WHERE id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, txtId.getText());

            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {

                txtName.setText(resultSet.getString(2));
                txtAddress.setText(resultSet.getString(3));
                txtSalary.setText(String.valueOf(resultSet.getDouble(4)));
            }
        }

    }


}
