import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SavePrompt {

    // enum for the MenuOption tab types
    enum MenuOptions{
        FILE,
        OPEN,
        CLOSE
    };

    //Fields
    private final String NOT_SAVED = "Save the current file?"; // text for the pop up box prompt

    private BrainFMenuBar menuBar; // the BrainFMenuBar for executing methods
    private Stage mainStage; // the main Stage in which the IDE is based around
    private Stage check; // the pop up box stage

    // Constructor
    public SavePrompt(BrainFMenuBar menuBar, Stage mainStage){
        this.mainStage = mainStage;
        this.menuBar = menuBar;
        check = new Stage();
    }

    // Methods

    /**
     * The method for starting up the save prompt. Initializes the buttons and their
     * OnAction functionality.
     * @param type
     */
    public void run(MenuOptions type){
        //check.initOwner(mainStage);

        // creates the buttons and text
        HBox options = new HBox(20);
        Button yes = new Button("Yes");
        yes.setOnAction(event -> yesButton(type));
        Button no = new Button("No");
        no.setOnAction(event -> noButton(type));
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> check.close());

        options.getChildren().addAll(yes, no, cancel);

        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().addAll(new Text(NOT_SAVED), options);

        Scene dialogScene = new Scene(dialogVbox, 300, 100);
        check.setScene(dialogScene);
        check.show();
    }

    /**
     * Saves the file and runs the proper functionality afterwards based off of the
     * enum type.
     * @param type
     */
    private void yesButton(MenuOptions type){
        menuBar.saveFile(menuBar.getCurrentFile());
        menuBar.runFunctionality(type);
        check.close();
    }

    /**
     * Runs the functionality based off of the enum type without saving.
     * @param type
     */
    private void noButton(MenuOptions type){
        menuBar.runFunctionality(type);
        check.close();
    }
}
