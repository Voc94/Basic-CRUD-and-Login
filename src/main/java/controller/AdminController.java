package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import model.builder.UserBuilder;
import repository.user.UserRepositoryMySQL;
import view.AdminView;
import view.EmployeeView;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
public class AdminController {

    private final AdminView adminView;
    private List<User> employeeList;
    private final UserRepositoryMySQL userRepository;

    public AdminController(Stage stage, UserRepositoryMySQL userRepository) {
        this.userRepository = userRepository;
        updateUserList(userRepository);
        this.adminView = new AdminView(stage, this.employeeList);
        this.adminView.addCreateButtonListener(new CreateButtonListener());
        this.adminView.addRetrieveButtonListener(new RetrieveButtonListener());
        this.adminView.addUpdateButtonListener(new UpdateButtonListener());
        this.adminView.addDeleteButtonListener(new DeleteButtonListener());
        this.adminView.addGenerateReportButtonListener(new GenerateReportButtonListener());
        stage.setScene(adminView.getScene());
    }

    public void updateUserList(UserRepositoryMySQL userRepository) {
        this.employeeList = userRepository.findAll();
    }

    private class CreateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for creating a user
            System.out.println("Create button clicked");
            // Extract data from input fields
            Long id = Long.parseLong(adminView.getIdField().getText());
            String name = adminView.getNameField().getText();
            String password = adminView.getChefField().getText();

            // Build User object
            User user = new UserBuilder()
                    .setId(id)
                    .setUsername(name)
                    .setRoles(null)
                    .setPassword(password)
                    .build();

            // Save user using the service
            userRepository.save(user);

            // Update the user list and refresh the table
            updateUserList(userRepository);
            showConfirmationDialog("Created User", "User " + user.getUsername());
        }
    }

    private class RetrieveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for retrieving a user
            System.out.println("Retrieve button clicked");
            try {
                Long id = Long.parseLong(adminView.getIdField().getText());
                Optional<User> retrievedUser = Optional.ofNullable(userRepository.findById(id));

                if (retrievedUser.isPresent()) {
                    // Display a confirmation dialog with user details
                    showConfirmationDialog("User Retrieved", "User details:\n" + retrievedUser.get().toString());
                } else {
                    // Display an error dialog if the user is not found
                    showErrorDialog("Error", "No user found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                // Display an error dialog for invalid ID format
                showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
            }
            updateUserList(userRepository);
        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for updating a user
            System.out.println("Update button clicked");
            try {
                // Extract data from input fields
                Long id = Long.parseLong(adminView.getIdField().getText());
                String name = adminView.getNameField().getText();
                String password = adminView.getChefField().getText();
                // Additional fields for the user, modify as needed

                // Check if the user with the given ID exists
                Optional<User> existingUser = Optional.ofNullable(userRepository.findById(id));

                if (existingUser.isPresent()) {
                    // Build a new User object with updated values
                    User updatedUser = new UserBuilder()
                            .setId(id)
                            .setUsername(name)
                            .setPassword(password)
                            .build();

                    // Update the user using the service
                    boolean updated = userRepository.update(updatedUser);

                    if (updated) {
                        System.out.println("User with ID " + id + " has been updated successfully.");
                        showConfirmationDialog("Updated User", "User with ID " + id + " has been updated successfully.");
                        // Update the user list and refresh the table
                        updateUserList(userRepository);
                    } else {
                        System.out.println("Failed to update user with ID: " + id);
                        showErrorDialog("Error", "Failed to update user with ID: " + id);
                    }
                } else {
                    System.out.println("No user found with ID: " + id);
                    showErrorDialog("Error", "No user found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please enter a valid numeric ID.");
                showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
            }
            updateUserList(userRepository);
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for deleting a user
            System.out.println("Delete button clicked");
            try {
                Long id = Long.parseLong(adminView.getIdField().getText());
                boolean removed = userRepository.removeById(id);

                if (!removed) {
                    System.out.println("User with ID " + id + " has been deleted successfully.");
                    showConfirmationDialog("Deleted User", "User with ID " + id + " has been deleted successfully.");
                    // Update the user list and refresh the table
                    updateUserList(userRepository);
                } else {
                    System.out.println("No user found with ID: " + id);
                    showErrorDialog("Error", "No user found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please enter a valid numeric ID.");
                showErrorDialog("Error", "Invalid ID format. Please enter a valid numeric ID.");
            }
            updateUserList(userRepository);
        }
    }

    private class GenerateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Logic for Generating PDF Report for Users
            System.out.println("Generate Report button clicked");
            List<User> users = employeeList;

            try {
                /* Step-2: Initialize PDF documents - logical objects */
                Document my_pdf_report = new Document(PageSize.A4);
                File pdfFile = new File("user_report.pdf");
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream(pdfFile));
                my_pdf_report.open();

                PdfPTable my_report_table = new PdfPTable(3); // Adjust as needed
                //cell object
                PdfPCell table_cell;

                //table headers
                table_cell = new PdfPCell(new Paragraph("ID"));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Paragraph("Name"));
                my_report_table.addCell(table_cell);
                table_cell = new PdfPCell(new Paragraph("Password"));
                my_report_table.addCell(table_cell);
                // Additional headers for the user, modify as needed

                // Adding data from User list
                for (User user : users) {
                    table_cell = new PdfPCell(new Paragraph(String.valueOf(user.getId())));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Paragraph(user.getUsername()));
                    my_report_table.addCell(table_cell);
                    table_cell = new PdfPCell(new Paragraph(user.getPassword()));
                    my_report_table.addCell(table_cell);
                    // Additional data for the user, modify as needed
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