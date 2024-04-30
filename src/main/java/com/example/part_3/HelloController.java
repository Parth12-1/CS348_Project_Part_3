package com.example.part_3;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class HelloController {

    public static Connection connection;

    public static Stage stage;

    public static boolean customerOrEmployee = false; //customer = true, employee = false

    public static int id = 0;
    public static String name = "";
    public static String email = "";

    public static String Position = "";
    public static double Commission_Percent = 0;
    public static String Hourly_Rate = "";


    public void logoutHandler(ActionEvent actionEvent) {
        moveToLogIn();
    }

    public void moveToLogIn() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("log-in.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 700, 700);

            Stage stage = (Stage) loginResponseTxt.getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    //////////////////////////////////////////////////////// LOGIN PAGE


    public Text loginResponseTxt;
    public TextField cIdTxt, cPassTxt;
    public TextField eIdTxt, ePassTxt;
    public Button cLoginBtn, eLoginBtn;


    public void cLoginHandler(ActionEvent actionEvent) {
        if (!cIdTxt.getText().isEmpty() && !cPassTxt.getText().isEmpty()) {
            String cid = cIdTxt.getText();
            String cpassword = cPassTxt.getText();

            //TODO: Query SQL with it
            boolean found = false;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

                String query = "SELECT ID, Name, Email FROM Customer WHERE ID = ? AND Password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, cid);
                preparedStatement.setString(2, cpassword);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt("ID");
                    name = resultSet.getString("Name");
                    email = resultSet.getString("Email");
                    found = true;
                    customerOrEmployee = true;
                    System.out.println("ID" + id);

                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            if (!found) {
                loginResponseTxt.setText("User not found! Check ID and Password and try again.");
            }
            else {
                //TODO: Save all values and move to next screen!
                moveToCustomerPage();
            }
        }
        else {
            loginResponseTxt.setText("ID or Password is empty, complete and try again.");
        }
    }


    public void eLoginHandler(ActionEvent actionEvent) {
        if (!eIdTxt.getText().isEmpty() && !ePassTxt.getText().isEmpty()) {
            String eid = eIdTxt.getText();
            String epassword = ePassTxt.getText();

            //TODO: Query SQL with it
            boolean found = false;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

                String query = "SELECT ID, Name, Email, Position, Commission_Percent, Hourly_Rate FROM Employee WHERE" +
                        " ID = ? AND Password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, eid);
                preparedStatement.setString(2, epassword);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt("ID");
                    name = resultSet.getString("Name");
                    email = resultSet.getString("Email");
                    Position = resultSet.getString("Position");
                    Commission_Percent = resultSet.getDouble("Commission_Percent");
                    Hourly_Rate = resultSet.getString("Hourly_Rate");
                    found = true;
                    customerOrEmployee = false;

                    System.out.println("ID" + id);
                    // Process the retrieved data
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            if (!found) {
                loginResponseTxt.setText("User not found! Check ID and Password and try again.");
            }
            else {

                moveToEmployeePage();
            }
        }
        else {
            loginResponseTxt.setText("ID or Password is empty, complete and try again.");
        }
    }







    public void moveToEmployeePage() { //false
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("employee-Home.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 700, 700);

            Stage stage = (Stage) loginResponseTxt.getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void moveToCustomerPage() { //true
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customer-Home.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 700, 700);

            Stage stage = (Stage) loginResponseTxt.getScene().getWindow();
            stage.setScene(newScene);

            stage.setOnShown(event -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    //////////////////////////////////////////////////////// END LOGIN PAGE












    //////////////////////////////////////////////////////// EMPLOYEE PAGE


    public Text eIdHoursTxt, eNameHoursTxt;
    public Button logOutBtn;
    public TableView<Hours_Worked> hoursCrudTable;
    public TextField eDateAddTxt, eHoursAddTxt, eDateUpdateTxt, eHoursUpdateTxt,  eDateDeleteTxt;
    public Button eCreateHoursBtn, eUpdateHoursBtn, eDeleteHoursBtn, eHoursReportBtn, eSalesReportBtn;



    public ObservableList<Hours_Worked> hours_WorkedList = FXCollections.observableArrayList();


    public void etestHandler(ActionEvent actionEvent) {
        eIdHoursTxt.setText("ID:" + id);
        eNameHoursTxt.setText(name);

        updateHoursTable();
    }

    public void handleHoursCrudClick(MouseEvent mouseEvent) {
        Hours_Worked selectedHours = hoursCrudTable.getSelectionModel().getSelectedItem();
        if (selectedHours != null) {
            eDateUpdateTxt.setText(String.valueOf(selectedHours.getDate()));
            eHoursUpdateTxt.setText(Double.toString(selectedHours.getHours()));
            eDateDeleteTxt.setText(String.valueOf(selectedHours.getDate()));
        }
    }

    public void eCreateHoursHandle(ActionEvent actionEvent) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "INSERT INTO Hours_Worked (Employee_ID, Date, Hours) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, eDateAddTxt.getText());
            statement.setDouble(3, Double.parseDouble(eHoursAddTxt.getText()));
            statement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        updateHoursTable();
    }

    public void eUpdateHoursHandle(ActionEvent actionEvent) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "UPDATE Hours_Worked SET Hours = ? WHERE Employee_ID = ? AND Date = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, Double.parseDouble(eHoursUpdateTxt.getText()));
            statement.setInt(2, id);
            statement.setString(3, eDateUpdateTxt.getText());
            statement.executeUpdate();


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        updateHoursTable();
    }

    public void eDeleteHoursHandle(ActionEvent actionEvent) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "DELETE FROM Hours_Worked WHERE Employee_ID = ? AND Date = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, eDateDeleteTxt.getText());
            statement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        updateHoursTable();
    }

    public void eHoursReportHandle(ActionEvent actionEvent) {

    }

    public void eSalesReportHandle(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sales_Report.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 900, 900);

            Stage stage = (Stage) eIdHoursTxt.getScene().getWindow();
            stage.setScene(newScene);

            stage.setOnShown(event -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHoursTable() {
        System.out.println("INN");
        hours_WorkedList.clear();
        hoursCrudTable.getColumns().clear();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            Statement statement = connection.createStatement(); //TODO SET TO PS
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Hours_Worked WHERE Employee_ID = " + id);

            while (resultSet.next()) {
                hours_WorkedList.add(new Hours_Worked(resultSet.getInt("Employee_ID"), resultSet.getString("Date"),
                        resultSet.getDouble("Hours")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        TableColumn<Hours_Worked, Integer> Employee_ID = new TableColumn<>("Employee_ID");
        Employee_ID.setCellValueFactory(new PropertyValueFactory<Hours_Worked, Integer>("Employee_ID"));

        TableColumn<Hours_Worked, String> Date = new TableColumn<>("Date");
        Date.setCellValueFactory(new PropertyValueFactory<Hours_Worked, String>("Date"));

        TableColumn<Hours_Worked, Double> Hours = new TableColumn<>("Hours");
        Hours.setCellValueFactory(new PropertyValueFactory<Hours_Worked, Double>("Hours"));


        hoursCrudTable.getColumns().add(Employee_ID);
        hoursCrudTable.getColumns().add(Date);
        hoursCrudTable.getColumns().add(Hours);


        hoursCrudTable.setItems(hours_WorkedList);
    }


    public void eLogoutHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("log-in.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 700, 700);

            Stage stage = (Stage) eDateUpdateTxt.getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////////////////// END EMPLOYEE PAGE












    //////////////////////////////////////////////////////// EMPLOYEE SALES REPORT

    public TableView salesReportTable;
    public Text srIdHoursTxt;
    public Text srNameHoursTxt;
    public Label srTtlLabel;
    public TextField srNameTxt;
    public TextField srItemNameTxt;
    public TextField srDateTxt;
    public TextField srPricetxt;
    public ChoiceBox srPriceFilterBtn;
    public ChoiceBox srDateFilterBtn;
    public Button srFilterBtn;

    public ObservableList<SalesData> salesDataList = FXCollections.observableArrayList();



    public CheckBox SRcIDCheck;
    public CheckBox SRDateCheck;
    public CheckBox SRcNameCheck;
    public CheckBox SRcEmailCheck;
    public CheckBox SRiIDCheck;
    public CheckBox SRiNameCheck;
    public CheckBox SRiPriceCheck;
    public CheckBox SRQuantityCheck;
    public CheckBox SRtPriceCheck;
    public CheckBox SRtCommCheck;
    public CheckBox SRtHoursCheck;
    public CheckBox SRaveHoursCheck;




    public void handleSalesReportClick(MouseEvent mouseEvent) {
    }

    public void eSalesTestHandler(ActionEvent actionEvent) {
        srIdHoursTxt.setText("ID:" + id);
        srNameHoursTxt.setText(name);
        srPriceFilterBtn.getItems().clear();
        srDateFilterBtn.getItems().clear();
        srPriceFilterBtn.getItems().add(">");
        srPriceFilterBtn.getItems().add("<");
        srPriceFilterBtn.getItems().add("=");
        srDateFilterBtn.getItems().add(">");
        srDateFilterBtn.getItems().add("<");
        srDateFilterBtn.getItems().add("=");
        srPriceFilterBtn.setValue("=");
        srDateFilterBtn.setValue("=");
        updateSalesReportTable(id);




        double total_Commisiion = 0;
        int total_hours = 0;
        for (SalesData salesData : salesDataList) {
            total_Commisiion += salesData.getTotalCommission();
        }

        ArrayList<String> dates = new ArrayList<>();
        for (SalesData salesData : salesDataList) {
            if (!dates.contains(salesData.getDateTime().split(" ")[0])) {
                dates.add(salesData.getDateTime().split(" ")[0]);
                total_hours += salesData.getTotalHoursWorked();
            }
        }

        //get the hourly rate that this employee make from sql
        double hourly_rate = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "SELECT Hourly_Rate FROM Employee WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hourly_rate = resultSet.getDouble("Hourly_Rate");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        double total_Made = total_Commisiion + (total_hours * hourly_rate);
        srTtlLabel.setText("Total Commission: $" + total_Commisiion + ", Total Hours Worked: " + total_hours + ", Total Made: $" + total_Made);

    }

    public void updateSalesReportTable(int employeeId) {




        salesDataList.clear();
        salesReportTable.getColumns().clear();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            CallableStatement statement = connection.prepareCall("{CALL GetSalesData(?)}");
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                salesDataList.add(new SalesData(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("Employee_ID"),
                        resultSet.getString("Date_Time"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Customer_Email"),
                        resultSet.getInt("Item_ID"),
                        resultSet.getString("Item_Name"),
                        resultSet.getDouble("Item_Price"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("Total_Price"),
                        resultSet.getDouble("Total_Commission"),
                        resultSet.getDouble("Total_Hours_Worked"),
                        resultSet.getDouble("Ave_Hours_Per_Customer")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<SalesData, Integer> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        TableColumn<SalesData, Integer> employeeIDCol = new TableColumn<>("Employee ID");
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<SalesData, String> dateTimeCol = new TableColumn<>("Date Time");
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        TableColumn<SalesData, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<SalesData, String> customerEmailCol = new TableColumn<>("Customer Email");
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        TableColumn<SalesData, Integer> itemIDCol = new TableColumn<>("Item ID");
        itemIDCol.setCellValueFactory(new PropertyValueFactory<>("itemID"));

        TableColumn<SalesData, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<SalesData, Double> itemPriceCol = new TableColumn<>("Item Price");
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<SalesData, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<SalesData, Double> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<SalesData, Double> totalCommissionCol = new TableColumn<>("Total Commission");
        totalCommissionCol.setCellValueFactory(new PropertyValueFactory<>("totalCommission"));

        TableColumn<SalesData, Double> totalHoursWorkedCol = new TableColumn<>("Total Hours Worked");
        totalHoursWorkedCol.setCellValueFactory(new PropertyValueFactory<>("totalHoursWorked"));

        TableColumn<SalesData, Double> aveHoursPerCustomerCol = new TableColumn<>("Ave Hours Per Customer");
        aveHoursPerCustomerCol.setCellValueFactory(new PropertyValueFactory<>("aveHoursPerCustomer"));

        salesReportTable.getColumns().addAll(
                customerIDCol,
                employeeIDCol,
                dateTimeCol,
                customerNameCol,
                customerEmailCol,
                itemIDCol,
                itemNameCol,
                itemPriceCol,
                quantityCol,
                totalPriceCol,
                totalCommissionCol,
                totalHoursWorkedCol,
                aveHoursPerCustomerCol
        );
        employeeIDCol.setVisible(false);

        //set the columns to visible or not based on the checkboxes
        customerIDCol.setVisible(SRcIDCheck.isSelected());
        dateTimeCol.setVisible(SRDateCheck.isSelected());
        customerNameCol.setVisible(SRcNameCheck.isSelected());
        customerEmailCol.setVisible(SRcEmailCheck.isSelected());
        itemIDCol.setVisible(SRiIDCheck.isSelected());
        itemNameCol.setVisible(SRiNameCheck.isSelected());
        itemPriceCol.setVisible(SRiPriceCheck.isSelected());
        quantityCol.setVisible(SRQuantityCheck.isSelected());
        totalPriceCol.setVisible(SRtPriceCheck.isSelected());
        totalCommissionCol.setVisible(SRtCommCheck.isSelected());
        totalHoursWorkedCol.setVisible(SRtHoursCheck.isSelected());
        aveHoursPerCustomerCol.setVisible(SRaveHoursCheck.isSelected());





        salesReportTable.setItems(salesDataList);
        srFilter();
}


    public void srFilterHandler(ActionEvent actionEvent) {
        srFilter();
    }




    public void srFilter() {
        String name = srNameTxt.getText();
        System.out.println("Name: " + name + ";");
        String itemName = srItemNameTxt.getText();
        String date = srDateTxt.getText();
        String price = srPricetxt.getText();
        String priceFilter = srPriceFilterBtn.getValue().toString(); //greater than, less than, equal to
        String dateFilter = srDateFilterBtn.getValue().toString(); //greater than, less than, equal to

        ObservableList<SalesData> filteredList = FXCollections.observableArrayList();

        for (SalesData salesData : salesDataList) {
            filteredList.add(salesData);
        }

        salesReportTable.setItems(filteredList);

        if (!name.isEmpty() && !name.equals(" ")) {
            for (int i = 0; i < filteredList.size(); i++) {
                if (!filteredList.get(i).getCustomerName().contains(name)) {
                    filteredList.remove(i);
                    i--;
                }
            }
        }




        if (!itemName.isEmpty()) {
            for (int i = 0; i < filteredList.size(); i++) {
                if (!filteredList.get(i).getItemName().contains(itemName)) {
                    filteredList.remove(i);
                    i--;
                }
            }
        }

        if (!date.isEmpty()) {
            for (int i = 0; i < filteredList.size(); i++) {
                String[] dateParts = filteredList.get(i).getDateTime().split("-");
                String[] inputDateParts = date.split("-");
                Date date1 = new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]));
                Date date2 = new Date(Integer.parseInt(inputDateParts[2]), Integer.parseInt(inputDateParts[0]), Integer.parseInt(inputDateParts[1]));
                switch (dateFilter) {
                    case ">" -> {
                        if (date1.before(date2)) {
                            filteredList.remove(i);
                            i--;
                        }
                    }
                    case "<" -> {
                        if (date1.after(date2)) {
                            filteredList.remove(i);
                            i--;
                        }
                    }
                    case "=" -> {
                        if (!date1.equals(date2)) {
                            filteredList.remove(i);
                            i--;
                        }
                    }
                }
            }
        }




        if (!price.isEmpty()) {
            for (int i = 0; i < filteredList.size(); i++) {
                switch (priceFilter) {
                    case ">" -> {
                        if (filteredList.get(i).getItemPrice() <= Double.parseDouble(price)) {
                            filteredList.remove(i);
                            i--;
                        }
                    }
                    case "<" -> {
                        if (filteredList.get(i).getItemPrice() >= Double.parseDouble(price)) {
                            filteredList.remove(i);
                            i--;
                        }
                    }
                    case "=" -> {
                        if (filteredList.get(i).getItemPrice() != Double.parseDouble(price)) {
                            filteredList.remove(i);
                            i--;
                        }
                    }
                }
            }
        }


    }

    public void SRlogoutHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("log-in.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 700, 700);

            Stage stage = (Stage) salesReportTable.getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void SRcheckboxHandler(ActionEvent actionEvent) {
        updateSalesReportTable(id);
    }



















    //////////////////////////////////////////////////////// END EMPLOYEE SALES REPORT













    //////////////////////////////////////////////////////// CUSTOMER PAGE
    public Text cIdHoursTxt;
    public Text cNameHoursTxt;
    public ListView cItems_Lstview;
    public TextField cQuantityTxt;
    public Button cAddCartBtn;
    public ListView cCart_Lstview;
    public TextField cEmployeeIDTxt;
    public TextField cPaymentCardNumTxt;
    public TextField cDateTxt;
    public Button cNewPurchaseBtn;
    public Button cRemoveFromCartBtn;

    public int indexItemClicked = 0;
    public int indexCartClicked = 0;

    public void ctestHandler(ActionEvent actionEvent) {
        cIdHoursTxt.setText("ID: " + id);
        cNameHoursTxt.setText(name);
        upDateItems();
        updateCartListview();
    }

    public void cItemsClickHandler(MouseEvent mouseEvent) {
        indexItemClicked = cItems_Lstview.getSelectionModel().getSelectedIndex() + 1;
    }

    public void cAddToCartHandler(ActionEvent actionEvent) {
        String quantity = cQuantityTxt.getText();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");



            String query2 = "{CALL AddToCart(?, ?, ?)}";
            CallableStatement statement2 = connection.prepareCall(query2);
            statement2.setInt(1, id);
            statement2.setInt(2, indexItemClicked);
            statement2.setInt(3, Integer.parseInt(quantity));
            statement2.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        updateCartListview();
        getPriceOfCart();
    }

    public void updateCartListview() {

        cCart_Lstview.getItems().clear();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "SELECT * FROM Cart WHERE Customer_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String query2 = "SELECT * FROM Item WHERE ID = ?";
                PreparedStatement statement2 = connection.prepareStatement(query2);
                statement2.setInt(1, resultSet.getInt("Item_ID"));
                ResultSet resultSet2 = statement2.executeQuery();

                while (resultSet2.next()) {
                    cCart_Lstview.getItems().add("Name: " + resultSet2.getString("Name") + ", Price: $" + resultSet2.getDouble("Price") + ", Quantity: " + resultSet.getInt("Quantity") + ", SKU: " + resultSet.getInt("Item_ID"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void cCartClickedHandler(MouseEvent mouseEvent) {
        indexCartClicked = cCart_Lstview.getSelectionModel().getSelectedIndex();
    }

    public void getPriceOfCart(){
        double total = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "SELECT * FROM Cart WHERE Customer_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String query2 = "SELECT * FROM Item WHERE ID = ?";
                PreparedStatement statement2 = connection.prepareStatement(query2);
                statement2.setInt(1, resultSet.getInt("Item_ID"));
                ResultSet resultSet2 = statement2.executeQuery();

                while (resultSet2.next()) {
                    total += resultSet2.getDouble("Price") * resultSet.getInt("Quantity");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total: " + total);

    }



    public void cNewPurchaseHandle(ActionEvent actionEvent) {
        String employee_id = cEmployeeIDTxt.getText();
        String cardNum = cPaymentCardNumTxt.getText();
        String date = cDateTxt.getText();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "{CALL NewPurchase(?, ?, ?, ?)}";
            CallableStatement statement = connection.prepareCall(query);
            statement.setInt(1, id);
            statement.setInt(2, Integer.parseInt(employee_id));
            statement.setString(3, cardNum);
            statement.setString(4, date);

            statement.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cRemoveFromCartHandler(ActionEvent actionEvent) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "DELETE FROM Cart WHERE Customer_ID = ? AND Item_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            String[] parts = cCart_Lstview.getItems().get(indexCartClicked).toString().split("SKU: ");
            statement.setInt(2, Integer.parseInt(parts[1]));
            statement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        updateCartListview();
    }

    public void upDateItems() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Part_3?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "Parths12");

            String query = "SELECT * FROM Item";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                cItems_Lstview.getItems().add("Name: " + resultSet.getString("Name") + ", Price: $" + resultSet.getDouble("Price"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        updateCartListview();
    }

    public void cLogoutHandler(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("log-in.fxml"));
            Scene newScene = new Scene(fxmlLoader.load(), 700, 700);

            Stage stage = (Stage) cItems_Lstview.getScene().getWindow();

            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////////////////// END CUSTOMER PAGE


}