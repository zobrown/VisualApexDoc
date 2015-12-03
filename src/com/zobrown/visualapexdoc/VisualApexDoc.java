package com.zobrown.visualapexdoc;

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
import javafx.stage.FileChooser;
import java.io.File;
import org.salesforce.apexdoc.ApexDoc;
import javafx.application.Platform;

// Just for checkbox	
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.control.cell.CheckBoxListCell;


public class VisualApexDoc extends Application {
	private GridPane grid;
	private TextField srcDirTextField;
	private TextField tgtDirTextField;
	private TextField srcURLTextField;
	private TextField homePageTextField;
	private TextField bannerPageTextField;

	final ObservableList<JavaScope> scopeData = FXCollections.observableArrayList();

	public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

    	setUpGrid(primaryStage);

		setUpSourceDirWidget(primaryStage);

		setUpTargetDirWidget(primaryStage);
		
		setUpSourceURLWidget(primaryStage);

		setUpHomePageWidget(primaryStage);

		setUpBannerPageWidget(primaryStage);

		setUpScopeCheckboxes(primaryStage);

		setUpCancelButton(primaryStage);
		
		setUpGenerateButton(primaryStage);

		
		Scene scene = new Scene(grid, 600, 400);
		primaryStage.setScene(scene);

		primaryStage.show();

    }    


	private void setUpGrid(Stage primaryStage) {
        primaryStage.setTitle("Generate Apex Documentation");
		
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		Text scenetitle = new Text("Settings");
		scenetitle.setId("setings-header");
		grid.add(scenetitle, 0, 0, 2, 1);

	}

	private void setUpSourceDirWidget(Stage primaryStage) {
		/* Begin Source Directory */

		Label srcDirLabel = new Label("Source Directory:");
		grid.add(srcDirLabel, 0, 1);

		srcDirTextField = new TextField();
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

	}

	private void setUpTargetDirWidget(Stage primaryStage) {
		/* Begin Target Directory */

		Label tgtDirLabel = new Label("Target Directory:");
		grid.add(tgtDirLabel, 0, 2);

		tgtDirTextField = new TextField();
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
	
	}


	private void setUpSourceURLWidget(Stage primaryStage) {
		/* Begin Target Directory */

		Label srcURLLabel = new Label("Source URL:");
		grid.add(srcURLLabel, 0, 3);

		srcURLTextField = new TextField();
		grid.add(srcURLTextField, 1, 3);


		/* End Target Directory */
	
	}

	private void setUpHomePageWidget(Stage primaryStage) {
		/* Begin Target Directory */

		Label homePageLabel = new Label("Home Page:");
		grid.add(homePageLabel, 0, 4);

		homePageTextField = new TextField();
		grid.add(homePageTextField, 1, 4);

		Button homePageButton = new Button("Choose File");
		grid.add(homePageButton, 2, 4);


		homePageButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	FileChooser tgtFileChooser = new FileChooser();

		    	File selectedFile = tgtFileChooser.showOpenDialog(primaryStage);

		        homePageTextField.setText(selectedFile.toString());
		    }

		});

		/* End Target Directory */
	
	}

	private void setUpBannerPageWidget(Stage primaryStage) {
		/* Begin Target Directory */

		Label bannerPageLabel = new Label("Banner Page:");
		grid.add(bannerPageLabel, 0, 5);

		bannerPageTextField = new TextField();
		grid.add(bannerPageTextField, 1, 5);

		Button bannerPageButton = new Button("Choose File");
		grid.add(bannerPageButton, 2, 5);


		bannerPageButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	FileChooser tgtFileChooser = new FileChooser();

		    	File selectedFile = tgtFileChooser.showOpenDialog(primaryStage);

		        bannerPageTextField.setText(selectedFile.toString());
		    }

		});

		/* End Target Directory */
	
	}


	private void setUpScopeCheckboxes(Stage primaryStage) {
		Label scopeLabel = new Label("Scope:");
		grid.add(scopeLabel, 0, 6);

		
		// Add scopes
		// global;public;private;testmethod;webService
		scopeData.add(new JavaScope(false, "global"));
		scopeData.add(new JavaScope(false, "public"));
		scopeData.add(new JavaScope(false, "private"));
		scopeData.add(new JavaScope(false, "testmethod"));
		scopeData.add(new JavaScope(false, "webService"));

        final ListView<JavaScope> listView = new ListView<JavaScope>();
        // listView.setPrefSize(200, 250);
        listView.setEditable(true);
        listView.setItems(scopeData);

        Callback<JavaScope, ObservableValue<Boolean>> getProperty = new Callback<JavaScope, ObservableValue<Boolean>>() {
                @Override
                public BooleanProperty call(JavaScope layer) {

                        return layer.selectedProperty();

                }
        };
        Callback<ListView<JavaScope>, ListCell<JavaScope>> forListView = CheckBoxListCell.forListView(getProperty);
        listView.setCellFactory(forListView);

        grid.add(listView, 1, 6, 1, 3);

	}

	private void setUpCancelButton(Stage primaryStage) {
		Button cancelButton = new Button("Cancel");
		grid.add(cancelButton, 2, 7);

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	try{

		    		Platform.exit();	
		    	}
		    	catch(Exception ex)
		    	{
		    		System.err.println("Caught IOException: " + ex.getMessage());
		    	}
		    }

		});

	}

	private void setUpGenerateButton(Stage primaryStage) {
		Button generateDocButton = new Button("Generate");
		grid.add(generateDocButton, 3, 7);

		generateDocButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	try{

		    		String[] argArray = new String[12];

		    		argArray[0] = "-s";
		    		argArray[1] = srcDirTextField.getText();
		    		argArray[2] = "-t";
		    		argArray[3] = tgtDirTextField.getText();
		    		argArray[4] = "-g";
		    		argArray[5] = srcURLTextField.getText();
		    		argArray[6] = "-h";
		    		argArray[7] = homePageTextField.getText();
		    		argArray[8] = "-a";
		    		argArray[9] = bannerPageTextField.getText();

		    		StringBuilder scopelist = new StringBuilder();

		    		for (JavaScope thisData : scopeData) {
		    			if(thisData.getSelected())
		    			{
							scopelist.append(thisData.getName());
							scopelist.append(";");

		    			}
                    }

		    		argArray[10] = "-p";
		    		argArray[11] = scopelist.toString();
                    
                    // System.out.println(scopelist.toString());


		    		ApexDoc.RunApexDoc(argArray, null);

		    		Platform.exit();	
		    	}
		    	catch(Exception ex)
		    	{
		    		System.err.println("Caught IOException: " + ex.getMessage());
		    	}
		    }

		});

	}


	class JavaScope {
			private final SimpleBooleanProperty selected;
			private final SimpleStringProperty name;

			public JavaScope(boolean id, String name) {
				this.selected = new SimpleBooleanProperty(id);
				this.name = new SimpleStringProperty(name);
			}

			public boolean getSelected() {
				return selected.get();
			}

			public void setSelected(boolean selected) {
				this.selected.set(selected);
			}

			public String getName() {
				return name.get();
			}

			public void setName(String fName) {
				name.set(fName);
			}

			public SimpleBooleanProperty selectedProperty() {
				return selected;
			}

			@Override
			public String toString() {
				return getName();
			}
		}
    
}
