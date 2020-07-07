package org.xiao.shm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Pretty much stolen from the Oracle website
 * https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
 * 
 * @author Andy
 *
 */

public class UserInterface extends Application {

	private TableView<Oscillator> table = new TableView<Oscillator>();
	private final ObservableList<Oscillator> data = FXCollections.observableArrayList(
			new Oscillator("0.1", "0.05", "1", "0.05")
			//, new Oscillator("0.1", "0.05", "1", "0.5")
			);
	final HBox hb = new HBox();
	final HBox h2 = new HBox();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Simple Harmonic Motion");
        stage.setWidth(440);
        stage.setHeight(550);
 
        final Label label = new Label("Simple Harmonic Motion");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn<Oscillator, String> position = new TableColumn<>("Position");
        position.setMinWidth(100);
        position.setCellValueFactory(
            new PropertyValueFactory<Oscillator, String>("Position"));
        position.setCellFactory(TextFieldTableCell.forTableColumn());
        position.setOnEditCommit(
            new EventHandler<CellEditEvent<Oscillator, String>>() {
                @Override
                public void handle(CellEditEvent<Oscillator, String> t) {
                    ((Oscillator) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setPosition(t.getNewValue());
                }
            }
        );
 
 
        TableColumn<Oscillator, String> velocity = new TableColumn<>("Velocity");
        velocity.setMinWidth(100);
        velocity.setCellValueFactory(
            new PropertyValueFactory<Oscillator, String>("Velocity"));
        velocity.setCellFactory(TextFieldTableCell.forTableColumn());
        velocity.setOnEditCommit(
            new EventHandler<CellEditEvent<Oscillator, String>>() {
                @Override
                public void handle(CellEditEvent<Oscillator, String> t) {
                    ((Oscillator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setVelocity(t.getNewValue());
                }
            }
        );
 
        TableColumn<Oscillator, String> w0 = new TableColumn<>("w0");
        w0.setMinWidth(100);
        w0.setCellValueFactory(
            new PropertyValueFactory<Oscillator, String>("w0"));
        w0.setCellFactory(TextFieldTableCell.forTableColumn());
        w0.setOnEditCommit(
            new EventHandler<CellEditEvent<Oscillator, String>>() {
                @Override
                public void handle(CellEditEvent<Oscillator, String> t) {
                    ((Oscillator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setW0(t.getNewValue());
                }
            }
        );
        
        TableColumn<Oscillator,String> zeta = new TableColumn<>("zeta");
        zeta.setMinWidth(100);
        zeta.setCellValueFactory(
            new PropertyValueFactory<Oscillator, String>("zeta"));
        zeta.setCellFactory(TextFieldTableCell.forTableColumn());
        zeta.setOnEditCommit(
            new EventHandler<CellEditEvent<Oscillator, String>>() {
                @Override
                public void handle(CellEditEvent<Oscillator, String> t) {
                    ((Oscillator) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setZeta(t.getNewValue());
                }
            }
        );
 
        table.setItems(data);
        table.getColumns().addAll(position, velocity, w0, zeta);
 
        final TextField addPos = new TextField();
        addPos.setPromptText("position");
        addPos.setMaxWidth(position.getPrefWidth());
        
        final TextField addVel= new TextField();
        addVel.setMaxWidth(velocity.getPrefWidth());
        addVel.setPromptText("velocity");
        
        final TextField addW0 = new TextField();
        addW0.setMaxWidth(w0.getPrefWidth());
        addW0.setPromptText("w0");
        
        final TextField addZeta = new TextField();
        addZeta.setMaxWidth(zeta.getPrefWidth());
        addZeta.setPromptText("zeta");
 
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	try {
            		// all this JavaFX table stuff doesn't seem to like numbers. So use Strings -
            		// basically try force the exception by parsing now, then pass String values
            		// to constructor, which should parse without issues.
            		double posVal = Double.parseDouble(addPos.getText());
            		double velVal = Double.parseDouble(addVel.getText());
            		double w0Val = Double.parseDouble(addW0.getText());
            		double zetaVal = Double.parseDouble(addZeta.getText());
            		
                    data.add(new Oscillator(
                    		addPos.getText(),
                            addVel.getText(),
                            addW0.getText(),
                    		addZeta.getText()));
            		
            	}catch(NumberFormatException ex) {
            		Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText("Number format error");
            		alert.setContentText("Invalid entry - please ensure only numbers are entered as run parameters.");

            		alert.showAndWait();
            	}
            	

        addPos.clear();
        addVel.clear();
        addW0.clear();
        addZeta.clear();
            }
        });
        
        final Button runButton = new Button("Run");
        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Running...");
                
                try {
                Simulation sim = new Simulation();
                sim.run(data.get(0));
                }
                catch(Exception ex) {
                	
                	ex.printStackTrace();
                	
                	Alert alert = new Alert(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText("Unknown error");
            		alert.setContentText("An unknown error has occured. Please check your run parameters.");

            		alert.showAndWait();
                }
            }
        });
 
        hb.getChildren().addAll(addPos, addVel, addW0, addZeta, addButton);
        hb.setSpacing(3);
        h2.getChildren().addAll(runButton);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        vbox.getChildren().addAll(h2);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
}