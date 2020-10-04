package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import application.ButtonUI;

public class Main extends Application {
	public ButtonUI afdsa = new ButtonUI();
	//private Allert allert;
	@Override
	public void start(Stage primaryStage)throws Exception {
		//allert = new Allert("実行開始");
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,0,0);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			afdsa.start(primaryStage);
		}catch(Exception e) {
			e.printStackTrace();
			//allert = new Allert("初期のパネルの作成に失敗");
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
