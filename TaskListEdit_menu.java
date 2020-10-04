package application;

import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import application.ButtonUI;
import javafx.scene.input.KeyEvent;
/**
 * 子Stage
 * @author tomo
 */
public class TaskListEdit_menu extends Stage {
	private GridPane pane;
	private static final String MAIN_COLOR = "#141414";
	private List<TaskList> TaskLists;
	private int tasklist_count;
	private VBox this_Box;
	private TextField TaskListname;
	private ButtonUI this_buttonUI;
	public String this_tasklist;
	public Boolean this_Delete;
	/***
	 * タスクリスト追加メニュの表示
	 * @param oya
	 */
	public TaskListEdit_menu( Stage oya ,List<TaskList> this_Tasks,VBox leftbox,String tasklist){
		super();
		pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		
		TaskLists = this_Tasks;
		this_Box = leftbox;
		this_tasklist = tasklist;
		this_Delete = false;
		
		Set_CancelButton();
		Set_TaskListname();
		Set_ColorLine();
		Set_AddList();
		Scene scene = new Scene(pane, 300, 150);
		scene.setOnKeyPressed(this::keyPressed);
		this.setX( oya.getX() + 30);
		this.setY( oya.getY() + 30);
		this.setScene(scene);
		this.show();
	}
	/***
	 * キャンセルボタンの初期設定
	 */
	private void Set_CancelButton() {
		Button CancelButton = new Button("キャンセル");
		CancelButton.setFont( new Font(15) );
		CancelButton.setTextFill(Color.WHITE);
		CancelButton.setPrefSize(100, 30);
		CancelButton.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.CancelButtonClick( event );
		CancelButton.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );
		pane.add(CancelButton, 0, 0);
	}
	private void Set_TaskListname() {
		TaskListname = new TextField ();
		TaskListname.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		TaskListname.setText(this_tasklist);
		TaskListname.setFont( new Font(20) );
		TaskListname.setPrefSize(300, 30);
		pane.add(TaskListname,0,1);
	}
	private void Set_ColorLine() {
		Rectangle clorline = new Rectangle(300,3);
		clorline.setFill(Color.web("367145"));
		pane.add(clorline, 0, 2);
	}
	private void Set_AddList() {
		Button AddList = new Button("タスクの削除");
		AddList.setFont( new Font(20) );
		AddList.setTextFill(Color.WHITE);
		AddList.setPrefSize(300, 30);
		AddList.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.AddTaskList( event );
		AddList.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );
		pane.add(AddList, 0, 3);
	}
	public void CancelButtonClick(MouseEvent e ) {
		this.getScene().getWindow().hide();
	}
	public void AddTaskList(MouseEvent e ) {
		this_Delete=true; 
		 this.getScene().getWindow().hide();
	}
	private void keyPressed(KeyEvent e) {
		switch(e.getCode()) {
		case ENTER:
			this_buttonUI.task_ListAdd = true;
			TaskList this_tasklist = new TaskList();
			 this_tasklist.grid = this_Box;
			 this_tasklist.taskname = TaskListname.getText();
			 this_tasklist.Set_TaskList(0, tasklist_count+1,this_buttonUI);
			 TaskLists.add(this_tasklist);
			 this.getScene().getWindow().hide();
			break;
		case SPACE:
			System.out.println("スペースを押下しました。");
			break;
		default:
			break;
		}
	}
}