package com.ncr.bet.visualapexdoc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;

// New Stuff
import javafx.stage.DirectoryChooser;
import java.io.File;
import org.salesforce.apexdoc.ApexDoc;
import javafx.application.Platform;


public class VisualApexDoc extends Application {

	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Generate Apex Documentation");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		Text scenetitle = new Text("Settings");
		scenetitle.setId("setings-header");
		grid.add(scenetitle, 0, 0, 2, 1);


		/* Begin Source Directory */

		Label srcDirLabel = new Label("Source Directory:");
		grid.add(srcDirLabel, 0, 1);

		TextField srcDirTextField = new TextField();
		grid.add(srcDirTextField, 1, 1);

		Button srcDirButton = new Button("Choose Directory");
		grid.add(srcDirButton, 2, 1);


		srcDirButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	DirectoryChooser sourceDirChooser = new DirectoryChooser();

		    	File selectedDirectory = sourceDirChooser.showDialog(primaryStage);

		        srcDirTextField.setText(selectedDirectory.toString());
		    }

		});

		/* End Source Directory */
		

		/* Begin Target Directory */

		Label tgtDirLabel = new Label("Target Directory:");
		grid.add(tgtDirLabel, 0, 2);

		TextField tgtDirTextField = new TextField();
		grid.add(tgtDirTextField, 1, 2);

		Button tgtDirButton = new Button("Choose Directory");
		grid.add(tgtDirButton, 2, 2);


		tgtDirButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	DirectoryChooser tgtDirChooser = new DirectoryChooser();

		    	File selectedDirectory = tgtDirChooser.showDialog(primaryStage);

		        tgtDirTextField.setText(selectedDirectory.toString());
		    }

		});

		/* End Target Directory */
		

		Button generateDocButton = new Button("Generate");
		grid.add(generateDocButton, 0, 3);

		generateDocButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	try{

		    		String[] argArray = new String[4];

		    		argArray[0] = "-s";
		    		argArray[1] = srcDirTextField.getText();
		    		argArray[2] = "-t";
		    		argArray[3] = tgtDirTextField.getText();

		    		ApexDoc.RunApexDoc(argArray, null);

		    		Platform.exit();	
		    	}
		    	catch(Exception ex)
		    	{
		    		System.err.println("Caught IOException: " + ex.getMessage());
		    	}
		    }

		});

		
		Scene scene = new Scene(grid, 500, 275);
		primaryStage.setScene(scene);

		primaryStage.show();

    }    

    
}
