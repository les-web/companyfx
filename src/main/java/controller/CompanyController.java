package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Category;
import model.Product;

import java.io.*;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CompanyController {
    @FXML
    private TableView<Product> tbl_products;
    @FXML
    private TableColumn<Product, String> tc_name;
    @FXML
    private TableColumn<Product, String> tc_category;
    @FXML
    private TableColumn<Product, Double> tc_price;
    @FXML
    private TableColumn<Product, Integer> tc_quantity;
    @FXML
    private TextField tf_search;
    @FXML
    private CheckBox cb_less5;
    @FXML
    private CheckBox cb_medium;
    @FXML
    private CheckBox cb_more10;
    @FXML
    private ComboBox<Category> combo_category;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
        primaryStage.setTitle("Logowanie");             // tytuł okna
//        primaryStage.setResizable(false);               // brak skalowania
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
        // zamknięcie okna logowania na obiekcie typu Stage
        Stage companyStage = (Stage) btn_delete.getScene().getWindow();
        companyStage.close();
    }

    @FXML
    void closeAction(ActionEvent event) {
        Platform.exit();
    }

    private ObservableList<Product> products = FXCollections.observableArrayList();
    String path = Paths.get("").toAbsolutePath().toString() +
            "\\src\\main\\java\\utility\\products.csv";

    private void getProductsFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.nextLine(); // pominięcie nagłówka w pliku .csv
        while (scanner.hasNextLine()) {
            String line[] = scanner.nextLine().split(";");
            products.add(new Product(
                    Integer.valueOf(line[0]), line[1],
                    Arrays.stream(Category.values())                                        // Category []
                            .filter(category -> category.getCategoryName().equals(line[2])) // filtrowanie po nazwie kategorii
                            .findAny()                                                      // Optional<Category>
                            .get(),                                                          // Category
                    //              Double.valueOf(line[3]), Integer.valueOf(line[4])));
                    Double.valueOf(line[3].replace(",", ".")), Integer.valueOf(line[4])));

        }
        scanner.close();
    }

    private void setProductsIntoTable() {
        // konfiguracja wartości wporwadzanych do tabeli z pól klasy modelu Product
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        tc_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        // format walutowy
        Locale locale = new Locale("pl", "PL");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        tc_price.setCellFactory(tc -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        tc_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        // przekazanie wartości do tabeli z ObservableList
        tbl_products.setItems(products);
    }

    public void initialize() throws FileNotFoundException {
        getProductsFromFile();
        setProductsIntoTable();
        combo_category.setItems(FXCollections.observableArrayList(Category.values()));

    }


    public void saveProductsToFile() {
        try {
            FileWriter pw = new FileWriter(new File(path));
            //        pw.append("id;nazwa;kategoria;cena;lość" + "\n");
            pw.write("id;nazwa;kategoria;cena;lość");

            String format = "";
            for (Product product : products) {
                format = "\n" +
                        String.format(
                                Locale.US,
                                //                         "%d;%s;%s;%.2f;%d",
                                "%d;%s;%s;%s;%d",

                                product.getId(),
                                product.getName(),
                                product.getCategory().getCategoryName(),
                                //                 product.getPrice(),

                                String.format("%.2f", product.getPrice()).replace(".", ","),
                                product.getQuantity()
                        );
                pw.write(format);
//                pw.append(format + "\n");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void addAction(ActionEvent event) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Dodaj produkt");
        dialog.setHeaderText("Dodaj produkt");
        // ustawienie kontrolek
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tf_productName = new TextField();
        tf_productName.setPromptText("nazwa");
        ComboBox<Category> combo_productCategory = new ComboBox<>();
        combo_productCategory.setItems(FXCollections.observableArrayList(Category.values()));
        combo_productCategory.setPromptText("kategoria");
        TextField tf_productPrice = new TextField();
        tf_productPrice.setPromptText("cena");
        TextField tf_productQuantity = new TextField();
        tf_productQuantity.setPromptText("ilość");

        grid.add(tf_productName, 0, 0);
        grid.add(combo_productCategory, 0, 1);
        grid.add(tf_productPrice, 0, 2);
        grid.add(tf_productQuantity, 0, 3);

        dialog.getDialogPane().setContent(grid);
        // przyciski
        ButtonType btn_ok = new ButtonType("Dodaj", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btn_ok);

        Optional<Product> productOpt = dialog.showAndWait();
        if (productOpt.isPresent()) {
            if (!tf_productPrice.getText().matches("[0-9]+\\.{0,1}[0-9]{0,2}") ||
                    !tf_productQuantity.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Błąd danych. Produkt nie został dodany!");
                alert.showAndWait();
            } else {
                products.add(new Product(products.stream().mapToInt(p -> p.getId()).max().getAsInt() + 1,
                        tf_productName.getText(), combo_productCategory.getValue(),
                        Double.valueOf(tf_productPrice.getText()), Integer.valueOf(tf_productQuantity.getText())));
                saveProductsToFile();
                setProductsIntoTable();
            }
        }
    }

    @FXML
    void deleteAction(ActionEvent event) {
        Product product = tbl_products.getSelectionModel().getSelectedItem();
        if (product != null) {
            products.remove(product);
            saveProductsToFile();
            btn_delete.setDisable(true);
            btn_update.setDisable(true);
        }
    }

    @FXML
  //
    void filterAction(ActionEvent event) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList(
                products.stream()
                        .filter(product -> product.getName().toLowerCase().contains(tf_search.getText().toLowerCase()))
                        .collect(Collectors.toList()));
        if (combo_category.getValue() != null){
            filteredProducts = FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getCategory().equals(combo_category.getValue()))
                    .collect(Collectors.toList()));
        }
        // filtrowanie po ilości
        ObservableList<Product> productsToFilter = FXCollections.observableArrayList();
        if (cb_less5.isSelected()){
            productsToFilter.addAll(FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getQuantity() < 5)
                    .collect(Collectors.toList())));
        }
        if (cb_medium.isSelected()){
            productsToFilter.addAll(FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getQuantity() >= 5 && product.getQuantity() <= 10)
                    .collect(Collectors.toList())));
        }
        if (cb_more10.isSelected()){
            productsToFilter.addAll(FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getQuantity() > 10)
                    .collect(Collectors.toList())));
        }
        ObservableList<Product> finalFilter = FXCollections.observableArrayList();
        for (Product p1: productsToFilter) {
            for (Product p2: filteredProducts) {
                if (p1.equals(p2)){
                    finalFilter.add(p1);
                }
            }
        }
        tbl_products.setItems(finalFilter);
        tf_search.clear();
        combo_category.setValue(null);
        cb_less5.setSelected(true);
        cb_medium.setSelected(true);
        cb_more10.setSelected(true);
    }
    @FXML
    void selectAction(MouseEvent event) {
        Product product = tbl_products.getSelectionModel().getSelectedItem();
        if (product != null) {
            btn_delete.setDisable(false);
            btn_update.setDisable(false);
        } else {
            btn_delete.setDisable(true);
            btn_update.setDisable(true);
        }

    }


    @FXML
    void updateAction(ActionEvent event) throws IOException {
        // zaznaczone w tabelce
        Product product = tbl_products.getSelectionModel().getSelectedItem(); // odwołanie do obiektu zaznaczonego w tabeli

        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Edytuj produkt");
        dialog.setHeaderText("Edytuj produkt");
        // ustawienie kontrolek
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tf_productName = new TextField();
        tf_productName.setText(product.getName());
        ComboBox<Category> combo_productCategory = new ComboBox<>();
        combo_productCategory.setItems(FXCollections.observableArrayList(Category.values()));
        combo_productCategory.setValue(product.getCategory());
        TextField tf_productPrice = new TextField();
        tf_productPrice.setText(String.valueOf(product.getPrice()));
        TextField tf_productQuantity = new TextField();
        tf_productQuantity.setText(String.valueOf(product.getQuantity()));

        grid.add(tf_productName, 0, 0);
        grid.add(combo_productCategory, 0, 1);
        grid.add(tf_productPrice, 0, 2);
        grid.add(tf_productQuantity, 0, 3);

        dialog.getDialogPane().setContent(grid);
        // przyciski
        ButtonType btn_ok = new ButtonType("Edytuj", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btn_ok);

        Optional<Product> productOpt = dialog.showAndWait();
        if (productOpt.isPresent()) {
            if (!tf_productPrice.getText().matches("[0-9]+\\.{0,1}[0-9]{0,2}") ||
                    !tf_productQuantity.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Błąd danych. Produkt nie został zmieniony!");
                alert.showAndWait();
            } else {
                product.setName(tf_productName.getText());
                product.setCategory(combo_productCategory.getValue());
                product.setPrice(Double.valueOf(tf_productPrice.getText()));
                product.setQuantity(Integer.valueOf(tf_productQuantity.getText()));
                saveProductsToFile();
                products.clear();
                getProductsFromFile();
                setProductsIntoTable();
            }
        }
    }
}