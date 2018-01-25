import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class BrainF extends Application {

    // Fields
    private BorderPane borderPane;
    private MenuBar menuBar;
    private ToolBar toolbar;
    private HBox textEditor;
    private TextArea terminal;

    // Methods
    public void createBorderPane(){
        borderPane = new BorderPane();
    }

    public void createMenuBar(){
        menuBar = new MenuBar();

        // Create MenuBar Content
        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        file.getItems().addAll(newFile, open, save);

        Menu edit = new Menu("Edit");
        // Edit Menu Items

        Menu run = new Menu("Run");
        MenuItem runProgram = new MenuItem("Run");
        MenuItem debug = new MenuItem("Debug");
        run.getItems().addAll(runProgram, debug);

        // Add MenuBar Content to the MenuBar
        menuBar.getMenus().addAll(file, edit, run);
    }

    public void createToolbar(){
        toolbar = new ToolBar();

        Button save = new Button();
        Image saveImage = new Image(getClass().getResourceAsStream("media/stolen_save_icon.png"));
        save.setGraphic(new ImageView(saveImage));

        //save.setMaxSize(26, 27);

        toolbar.getItems().addAll(save);
    }

    public void createTextEditor(){
        textEditor = new HBox();

        TextArea textArea = new TextArea();
        textArea.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));

        VBox lineNumbers = new VBox();
        for(int i = 1; i < 21; i++){
            TextField number = new TextField(String.valueOf(i));
            number.setDisable(true);
            number.setMaxWidth(50);
            number.setAlignment(Pos.CENTER_RIGHT);
            lineNumbers.getChildren().add(number);
        }

        textEditor.getChildren().addAll(lineNumbers, textArea);
    }

    public void createTerminal(){
        terminal = new Terminal();
        terminal.setText(">>>\n");
    }

    // Main
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize
        createBorderPane();
        createMenuBar();
        createToolbar();
        createTextEditor();
        createTerminal();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, toolbar);

        borderPane.setTop(vbox);
        borderPane.setLeft(textEditor);
        borderPane.setRight(terminal);

        Scene scene = new Scene(borderPane);

        // Define the Stage
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("[INSERT FILE NAME] - Brain F IDE");
        primaryStage.show();
    }
}
