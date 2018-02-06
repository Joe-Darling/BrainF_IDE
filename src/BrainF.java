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

    // Methods
    public void createBorderPane(){
        borderPane = new BorderPane();
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
        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        // On key press setTextModified to True
        editor = codeArea;
        textEditor = new VirtualizedScrollPane<>(codeArea);
        textEditor.setMinWidth(600);
        textEditor.setMaxWidth(600);
    }

    public void createMenuBar(Stage primaryStage){
        menuBar = new BrainFMenuBar(primaryStage, editor, terminal);
    }

    public void createTerminal(){
        terminal = new Terminal();
        terminal.setMinWidth(300);
        terminal.setMaxWidth(300);
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
        //createToolbar();
        createTextEditor();
        createTerminal();
        createMenuBar(primaryStage);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar.createMenuBar());

        borderPane.setTop(vbox);
        borderPane.setLeft(textEditor);
        borderPane.setRight(terminal);

        Scene scene = new Scene(borderPane);

        // Define the Stage
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("[INSERT FILE NAME] - BrainF IDE");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
