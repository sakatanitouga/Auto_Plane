package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class TaskEdit_menu extends Stage{
	private GridPane pane;
	private HBox hsbox;
	private HBox hebox;
	private static final String MAIN_COLOR = "#141414";
	private List<Task> Tasks;
	private int task_count;
	private VBox this_Box;
	public TextField Taskname;	
	
	public TextField Start_TaskListnameH;
	public TextField Start_TaskListnameM;
	public TextField End_TaskListnameH;
	public TextField End_TaskListnameM;

	public String thisTaskname;
	public Boolean this_Delete=false;
	public Boolean this_Cancel=false;
	public Boolean Auto_Start=false;
	private String StartH;
	private String StartM;
	private String EndH;
	private String EndM;
	private Button Bool_Auto;
	private int click_Auto_count=0;
	
	
	public TaskEdit_menu(Stage oya,List<Task> this_Tasks,int tasks_count,VBox rightbox ,String this_taskname,Task this_task,String thisStartH,String thisStartM,String thisEndH,String thisEndM,String auto_setup){
		super();
		StartH = thisStartH;
		StartM = thisStartM;
		EndH = thisEndH;
		EndM = thisEndM;
		thisTaskname = this_taskname;
		pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		hsbox = new HBox();
		hsbox.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		hebox = new HBox();
		hebox.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		Tasks = this_Tasks;
		task_count = tasks_count;
		this_Box = rightbox;
		//this_buttonUI = eventHandler;
		if(auto_setup.equals("1"))
			click_Auto_count=1;
		Set_CancelButton();
		Set_Taskname();
		Set_ColorLine();
		Set_AddAuto();
		Set_AddTask();
		Set_Start_timeLabel();
		Set_End_timeLabel();
		Set_Start_Combobox();
		Set_End_Combobox();
		
		pane.add(hsbox, 0, 4);
		pane.add(hebox, 0, 6);
		Scene scene = new Scene(pane, 300, 500);
		scene.setOnKeyPressed(this::keyPressed);
		this.setOnCloseRequest(event -> saveStatus());
		this.setX( oya.getX() + 1000);
		this.setY( oya.getY() + 100);
		this.setScene(scene);
		this.show();
		
	}
	
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
	private void Set_Taskname() {
		Taskname = new TextField ();
		Taskname.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		Taskname.setText(thisTaskname);
		Taskname.setFont( new Font(30) );
		Taskname.setPrefSize(300, 30);
		
		pane.add(Taskname,0,1);
	}
	private void Set_ColorLine() {
		Rectangle clorline = new Rectangle(300,3);
		clorline.setFill(Color.web("367145"));
		pane.add(clorline, 0, 2);
	}
	private void Set_AddTask() {
		Button AddList = new Button("タスクの削除");
		AddList.setFont( new Font(20) );
		AddList.setTextFill(Color.WHITE);
		AddList.setPrefSize(300, 30);
		AddList.setStyle("-fx-background-color:#dc143c;");
		EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.AddTask( event ,Tasks,this_Box,task_count);
		AddList.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );
		pane.add(AddList, 0, 8);
	}
	private void Set_AddAuto() {
		if(click_Auto_count==1) {
			Bool_Auto = new Button("自動起動OFF");			
		}else {
			Bool_Auto = new Button("自動起動ON");
		}
		Bool_Auto.setFont( new Font(20) );
		Bool_Auto.setTextFill(Color.WHITE);
		Bool_Auto.setPrefSize(300, 30);
		Bool_Auto.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.ChangeAuto(event);
		Bool_Auto.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );
		pane.add(Bool_Auto, 0, 7);
	}
	private void Set_Start_timeLabel() {
		Text Taskname = new Text(50, 100, "作業開始時刻");
		Taskname.setFont(new Font(15));
		Taskname.setFill(Color.WHITE);
		pane.add(Taskname, 0, 3);		
	}
	private void Set_End_timeLabel() {
		Text Taskname = new Text(50, 100, "作業終了時刻");
		Taskname.setFont(new Font(15));
		Taskname.setFill(Color.WHITE);
		pane.add(Taskname, 0, 5);		
	}
	private void Set_Start_Combobox() {
		//テキストフィールド（時）
		Start_TaskListnameH = new TextField ();
		Start_TaskListnameH.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		Start_TaskListnameH.setText(StartH);
		Start_TaskListnameH.setFont( new Font(25) );
		Start_TaskListnameH.setPrefSize(60, 30);
		hsbox.getChildren().add(Start_TaskListnameH);
		
		//コンボボックス(時間)
		ComboBox<String> Hourbox = new ComboBox<String>();
		Hourbox.setStyle("-fx-background-color:"+MAIN_COLOR+";"
							+" -fx-font: 15pt'Meiryo UI';"
							+" -fx-text-fill: white;");
		Hourbox.setPrefSize(5, 30);
        int hour = 0;
		for(;hour<24;hour++) {
			Hourbox.getItems().add(new String(""+hour));
		}
		Hourbox.setOnAction((ActionEvent)->{
			Start_TaskListnameH.setText(Hourbox.getValue());
        });
		hsbox.getChildren().add(Hourbox);
		
		//作業時間のセミコロンの表示
		Text Colon = new Text(10, 30, ":");
		Colon.setFont(new Font(30));
		Colon.setFill(Color.WHITE);
		hsbox.getChildren().add(Colon);
		
		//テキストフィールド（分）
		Start_TaskListnameM = new TextField ();
		Start_TaskListnameM.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		Start_TaskListnameM.setText(StartM);
		Start_TaskListnameM.setFont( new Font(25) );
		Start_TaskListnameM.setPrefSize(60, 30);
		
		hsbox.getChildren().add(Start_TaskListnameM);
		
		//コンボボックス（分）
		ComboBox<String> Mbox = new ComboBox<String>();
		Mbox.setStyle("-fx-background-color:"+MAIN_COLOR+";"
							+" -fx-font: 15pt'Meiryo UI';");
		Mbox.setPrefSize(5, 30);
        int minits = 0;
		for(;minits<59;minits++) {
			Mbox.getItems().add(new String(""+minits));
		}
		Mbox.setOnAction((ActionEvent)->{
			Start_TaskListnameM.setText(Mbox.getValue());
        });
		hsbox.getChildren().add(Mbox);
	}
	private void Set_End_Combobox() {
		//テキストフィールド（時）
		End_TaskListnameH = new TextField ();
		End_TaskListnameH.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		End_TaskListnameH.setText(EndH);
		End_TaskListnameH.setFont( new Font(25) );
		End_TaskListnameH.setPrefSize(60, 30);
		hebox.getChildren().add(End_TaskListnameH);
		
		//コンボボックス(時間)
		ComboBox<String> Hourbox = new ComboBox<String>();
		Hourbox.setStyle("-fx-background-color:"+MAIN_COLOR+";"
							+" -fx-font: 15pt'Meiryo UI';"
							+" -fx-text-fill: white;");
		Hourbox.setPrefSize(5, 30);
        int hour = 0;
		for(;hour<24;hour++) {
			Hourbox.getItems().add(new String(""+hour));
		}
		Hourbox.setOnAction((ActionEvent)->{
			End_TaskListnameH.setText(Hourbox.getValue());
        });
		hebox.getChildren().add(Hourbox);
		
		//作業時間のセミコロンの表示
		Text Colon = new Text(10, 30, ":");
		Colon.setFont(new Font(30));
		Colon.setFill(Color.WHITE);
		hebox.getChildren().add(Colon);
		
		//テキストフィールド（分）
		End_TaskListnameM = new TextField ();
		End_TaskListnameM.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		End_TaskListnameM.setText(EndM);
		End_TaskListnameM.setFont( new Font(25) );
		End_TaskListnameM.setPrefSize(60, 30);
		
		
		hebox.getChildren().add(End_TaskListnameM);
		
		//コンボボックス（分）
		ComboBox<String> Mbox = new ComboBox<String>();
		Mbox.setStyle("-fx-background-color:"+MAIN_COLOR+";"
							+" -fx-font: 15pt'Meiryo UI';");
		Mbox.setPrefSize(5, 30);
        int minits = 0;
		for(;minits<59;minits++) {
			Mbox.getItems().add(new String(""+minits));
		}
		Mbox.setOnAction((ActionEvent)->{
			End_TaskListnameM.setText(Mbox.getValue());
        });
		hebox.getChildren().add(Mbox);
	}
	public void ChangeAuto(MouseEvent e) {
		click_Auto_count++;
		if(click_Auto_count%2==0) {
			Bool_Auto.setText("自動起動OFF");	
			Auto_Start=true;
		}else {
			Bool_Auto.setText("自動起動ON");
			Auto_Start=false;
		}
	}
	private void saveStatus() {
		this_Cancel = true;
	}
	public void CancelButtonClick(MouseEvent e) {
		saveStatus();
		this.getScene().getWindow().hide();
	}
	public void AddTask(MouseEvent e ,List<Task>Tasks,VBox rightbox,int tasks_count) {
		this_Delete=true; 
		 this.getScene().getWindow().hide();
	}
	private void keyPressed(KeyEvent e) { 
		switch(e.getCode()) {
		case ENTER:
			saveStatus();
			this.getScene().getWindow().hide();
			break;
		case DELETE:
			this_Delete=true; 
			 this.getScene().getWindow().hide();		
			 break;
		case BACK_SPACE:
			this_Delete=true; 
			 this.getScene().getWindow().hide();
			break;
		default:
			break;
		}
	}
	
}