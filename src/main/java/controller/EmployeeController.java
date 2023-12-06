package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Pizza;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.SalesRecord;
import model.builder.PizzaBuilder;

import model.builder.SalesRecordBuilder;
import org.w3c.dom.events.Event;
import service.pizza.PizzaService;
import service.pizza.PizzaServiceImpl;
import service.salesrecord.SalesRecordService;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.EmployeeView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final PizzaServiceImpl pizzaService;
    private final Long employee_id;
    private List<Pizza> pizzaList;
    private final SalesRecordService salesRecordService;
    public EmployeeController(Stage stage, PizzaServiceImpl pizzaService, SalesRecordService salesRecordService,Long employee_id) {
        this.employee_id = employee_id;
        this.salesRecordService = salesRecordService;
        this.pizzaService = pizzaService;
        updatePizzaList(pizzaService);
        this.employeeView = new EmployeeView(stage,this.pizzaList);
        this.employeeView.addCreateButtonListener(new CreateButtonListener());
        this.employeeView.addRetrieveButtonListener(new RetrieveButtonListener());
        this.employeeView.addUpdateButtonListener(new UpdateButtonListener());
        this.employeeView.addDeleteButtonListener(new DeleteButtonListener());
        this.employeeView.addGenerateReportButtonListener(new GenerateReportButtonListener());
        this.employeeView.addSellButtonListener(new SellButtonListener());
        stage.setScene(employeeView.getScene());
    }
    public void updatePizzaList(PizzaService pizzaService){
        this.pizzaList = pizzaService.findAll();
    }
    public EmployeeView getEmployeeView() {
        return employeeView;
    }
    private class CreateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for creating a pizza
            System.out.println("Create button clicked");
            // Extract data from input fields
            Long id = Long.parseLong(employeeView.getIdField().getText());
            String name = employeeView.getNameField().getText();
            String chef = employeeView.getChefField().getText();
            LocalDateTime deliveryDateTime = LocalDateTime.parse(employeeView.getDeliveryDateTimeField().getText());

            // Build Pizza object
            Pizza pizza = new PizzaBuilder()
                    .setId(id)
                    .setName(name)
                    .setChef(chef)
                    .setDeliveryDateTime(deliveryDateTime)
                    .build();

            // Save pizza using the service
            pizzaService.save(pizza);

            // Update the pizza list and refresh the table
            updatePizzaList(pizzaService);
            showConfirmationDialog("Created Pizza","Pizza" + pizza.getName());
        }
    }
    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for selling a pizza
            System.out.println("Sell button clicked");

            // Extract data from input fields
            try {
                Long inputtedId = Long.parseLong(employeeView.getIdField().getText());

                // Get the selected Pizza from the TableView
                Pizza selectedPizza = employeeView.getSelectedPizza();

                if (selectedPizza != null && inputtedId != null) {
                    Long selectedPizzaId = selectedPizza.getId();
                    System.out.println("User ID: " + employee_id);
                    System.out.println("Inputted ID: " + inputtedId);
                    System.out.println("Selected Pizza ID: " + selectedPizzaId);
                    SalesRecord salesRecord = new SalesRecordBuilder()
                            .setBuyer(inputtedId)
                            .setEmployeeId(employee_id)
                            .setPizzaId(selectedPizzaId)
                            .build();
                    salesRecordService.save(salesRecord);
                } else {
                    System.out.println("No pizza selected or Customer");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please enter a valid numeric ID.");
                showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
            }
        }
    }

    private class RetrieveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for retrieving a pizza
            System.out.println("Retrieve button clicked");
            try {
                Long id = Long.parseLong(employeeView.getIdField().getText());
                Optional<Pizza> retrievedPizza = Optional.ofNullable(pizzaService.findById(id));

                if (retrievedPizza.isPresent()) {
                    // Display a confirmation dialog with pizza details
                    showConfirmationDialog("Pizza Retrieved", "Pizza details:\n" + retrievedPizza.get().toString());
                } else {
                    // Display an error dialog if the pizza is not found
                    showErrorDialog("Error", "No pizza found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                // Display an error dialog for invalid ID format
                showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
            }
            updatePizzaList(pizzaService);

        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for updating a pizza
            System.out.println("Update button clicked");
                try {
                    // Extract data from input fields
                    Long id = Long.parseLong(employeeView.getIdField().getText());
                    String name = employeeView.getNameField().getText();
                    String chef = employeeView.getChefField().getText();
                    LocalDateTime deliveryDateTime = LocalDateTime.parse(employeeView.getDeliveryDateTimeField().getText());

                    // Check if the pizza with the given ID exists
                    Optional<Pizza> existingPizza = Optional.ofNullable(pizzaService.findById(id));

                    if (existingPizza.isPresent()) {
                        // Build a new Pizza object with updated values
                        Pizza updatedPizza = new PizzaBuilder()
                                .setId(id)
                                .setName(name)
                                .setChef(chef)
                                .setDeliveryDateTime(deliveryDateTime)
                                .build();

                        // Update the pizza using the service
                        boolean updated = pizzaService.update(updatedPizza);

                        if (updated) {
                            System.out.println("Pizza with ID " + id + " has been updated successfully.");
                            showConfirmationDialog("Updated Pizza", "Pizza with ID " + id + " has been updated successfully.");
                            // Update the pizza list and refresh the table
                            updatePizzaList(pizzaService);
                        } else {
                            System.out.println("Failed to update pizza with ID: " + id);
                            showErrorDialog("Error", "Failed to update pizza with ID: " + id);
                        }
                    } else {
                        System.out.println("No pizza found with ID: " + id);
                        showErrorDialog("Error", "No pizza found with ID: " + id);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID format. Please enter a valid numeric ID.");
                    showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter a valid date and time.");
                    showErrorDialog("Error", "Invalid date format. Please enter a valid date and time.");
                }
            updatePizzaList(pizzaService);
        }
        }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for deleting a pizza
            System.out.println("Delete button clicked");
            try {
                Long id = Long.parseLong(employeeView.getIdField().getText());
                boolean removed = pizzaService.removeById(id);

                if (!removed) {
                    System.out.println("Pizza with ID " + id + " has been deleted successfully.");
                    showConfirmationDialog("Deleted Pizza", "Pizza with ID " + id + " has been deleted successfully.");
                    // Update the pizza list and refresh the table
                    updatePizzaList(pizzaService);
                } else {
                    System.out.println("No pizza found with ID: " + id);
                    showErrorDialog("Error", "No pizza found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please enter a valid numeric ID.");
                showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
            }
            updatePizzaList(pizzaService);
        }
    }
    private class GenerateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for Generating PDF Report for SalesRecord
            System.out.println("Generate Report button clicked");
            List<SalesRecord> salesRecords = salesRecordService.findAll();

            try {
                /* Step-2: Initialize PDF documents - logical objects */
                Document my_pdf_report = new Document(PageSize.A4);
                File pdfFile = new File("sales_record_report.pdf");
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream(pdfFile));
                my_pdf_report.open();

                PdfPTable my_report_table = new PdfPTable(4);
                //cell object
                PdfPCell table_cell;

                //table headers
                table_cell = new PdfPCell(new Paragraph("ID"));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Paragraph("Employee ID"));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Paragraph("Buyer ID"));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Paragraph("Pizza ID"));
                my_report_table.addCell(table_cell);

                // Adding data from SalesRecord list
                for (SalesRecord salesRecord : salesRecords) {
                    table_cell = new PdfPCell(new Paragraph(String.valueOf(salesRecord.getEmployeeId())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Paragraph(String.valueOf(salesRecord.getBuyerId())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Paragraph(String.valueOf(salesRecord.getPizza())));
                    my_report_table.addCell(table_cell);
                }

                my_pdf_report.add(my_report_table);
                my_pdf_report.close();

                Desktop.getDesktop().open(pdfFile);

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showConfirmationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void showErrorDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
