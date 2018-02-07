import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SavePrompt {

    enum MenuOptions{
        FILE,
        OPEN,
        CLOSE
    };

    private final String NOT_SAVED = "Save the current file?";

    private BrainFMenuBar menuBar;
    private Stage mainStage;
    private Stage check;

    public SavePrompt(BrainFMenuBar menuBar, Stage mainStage){
        this.mainStage = mainStage;
        this.menuBar = menuBar;
        check = new Stage();
    }

    public void run(MenuOptions type){
        //check.initOwner(mainStage);

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

        Scene dialogScene = new Scene(dialogVbox, 300, 300);
        check.setScene(dialogScene);
        check.show();
    }

    private void yesButton(MenuOptions type){
        menuBar.saveFile(menuBar.getCurrentFile());
        menuBar.runFunctionality(type);
        check.close();
    }

    private void noButton(MenuOptions type){
        menuBar.runFunctionality(type);
        check.close();
    }
}
