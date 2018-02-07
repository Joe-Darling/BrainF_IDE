import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;


public class BrainF extends Application {

    // Fields
    private BorderPane borderPane;
    private BrainFMenuBar menuBar;
    private ToolBar toolbar;
    private CodeArea editor;
    private VirtualizedScrollPane textEditor;
    private Terminal terminal;

    final private KeyCombination newShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    final private KeyCombination openShortcut = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
    final private KeyCombination saveShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

    // Methods
    public void createBorderPane(){
        borderPane = new BorderPane();
    }

    /**
     * Creates the Toolbar for the IDE
     *
     * NOTE: Not included in MVP. Used for testing of functionality.
     */
    public void createToolbar(){
        toolbar = new ToolBar();

        Button save = new Button();
        Image saveImage = new Image(getClass().getResourceAsStream("media/stolen_save_icon.png"));
        save.setGraphic(new ImageView(saveImage));

        //save.setMaxSize(26, 27);

        toolbar.getItems().addAll(save);
    }

    /**
     * Creates the code area for the IDE. Responsible for setting the codeModified boolean
     * to true when the user Removes or Adds text.
     */
    public void createTextEditor(){
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            menuBar.setCodeModified(true);
        });
        editor = codeArea;
        textEditor = new VirtualizedScrollPane<>(codeArea);
        textEditor.setMinWidth(600);
        textEditor.setMaxWidth(600);
    }

    /**
     * Creates the menu bar through creating the BrainFMenuBar class object.
     * @param primaryStage
     */
    public void createMenuBar(Stage primaryStage){
        menuBar = new BrainFMenuBar(primaryStage, editor, terminal);
    }

    /**
     * Creates the Terminal for the interpreter to output data to.
     */
    public void createTerminal(){
        terminal = new Terminal();
        terminal.setMinWidth(300);
        terminal.setMaxWidth(300);
        terminal.setText(">>>\n");
        terminal.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER && terminal.getCurrMode() == Terminal.Mode.EDIT){
                menuBar.getInterpreter().getInput();
            }
        });
    }

    // Main
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates the Scene and sets the Stage for the BrainF IDE. Initializes all the main components
     * by calling the initialization methods.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize
        createBorderPane();
        //createToolbar();
        createTextEditor();
        createTerminal();
        createMenuBar(primaryStage);

        // Creates the Menu Bar
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar.createMenuBar());

        // sets border pane values
        borderPane.setTop(vbox);
        borderPane.setLeft(textEditor);
        borderPane.setRight(terminal);

        // sets the OnKeyPressed events for the scene
        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(event -> {
            if(newShortcut.match(event)){
                menuBar.newFile();
            }
            else if(openShortcut.match(event)){
                menuBar.openFile();
            }
            else if(saveShortcut.match(event)){
                menuBar.saveFile(menuBar.getCurrentFile());
            }

            else if(event.getCode() == KeyCode.F5){
                menuBar.runFile();
            }
        });

        // Define the Stage
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(900);
        primaryStage.setScene(scene);

        menuBar.setMainSceneTitle();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
