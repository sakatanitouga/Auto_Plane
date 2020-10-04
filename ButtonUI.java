package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Orientation;
import javafx.beans.value.ObservableValue;
import application.TaskList;
import application.TaskListAdd_menu;
import application.Task;
import application.TaskEdit_menu;
import application.TaskAdd_menu;
import application.Setting_menu;
import application.TaskListEdit_menu;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.animation.Timeline;	//割り込み処理用
import javafx.animation.KeyFrame;	//割り込み処理用
import javafx.util.Duration;		//割り込み処理用
import javafx.event.ActionEvent;	//割り込み処理用
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import java.io.FileWriter; 		//データ保存用
import java.io.IOException;		//データ保存用
import java.io.File;			//データ読み取り用
import java.io.FileReader;		//データ読み取り用
import java.io.BufferedReader;	//データ読み取り用
import java.time.LocalDateTime;				//時間取得用
import java.time.format.DateTimeFormatter;	//時間取得用
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;


public class ButtonUI extends Application{
	private HBox hbox;
	private VBox rightbox;
	private VBox leftbox;
	private Rectangle clorline;
	private Stage this_stage ;
	private int tasks_count = 0;
	private int taskslist_count = 0;
	private List<List<String>>Save_Tasks;
	private String this_taskList = "今日の予定";
	private String fdate1;
	private int task_count=0;
	private long  cnt1 = 0;
	private String[] addtime= new String[2];
	private String[] Settime = new String[2];
	private Boolean dable_tasklist = false;
	public Boolean task_Add = false;
	public Boolean task_ListAdd = false;
	public Boolean Change_tasklist = false;
	public Boolean push_task = false;
	public Boolean Autotask = true;
	
	public Button SettingButton;
	public Button AddListButton;
	public Button AddTaskButton;
	
	public Text Taskname;
	public Text blankline;
	
	public List<TaskList> Tasklist;
	public List<Task> Tasks;
	
	public String this_taskname;
	public String Task_Start_H;
	public String Task_Start_M;
	public String Task_End_H;
	public String Task_End_M;
	public String[] Setting_data;
	
	public Setting_menu setting_menu;
	public TaskListAdd_menu taskadd_menu;
	public TaskListEdit_menu tasklistEd;
	public TaskEdit_menu taskedit_menu;
	public TaskAdd_menu this_task_menu;
	public static final String MAIN_COLOR = "#141414";
	public static final String SUB_COLOR = "#367145";
	public static boolean push_SettingButton = false;
	public static boolean push_AddListButton = false;
	public static boolean push_AddTaskButton = false;
	public ScrollBar sp1 = new ScrollBar();

	
	@Override
	/***
	 * UIの表示
	 */
	public void start(Stage stage) throws Exception{
		int tasklist_count = 0;
		
		//グリッドの初期設定
		this_stage= stage;
		hbox = new HBox(50);
		rightbox = new VBox(10);
		leftbox = new VBox();
		hbox.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		Setting_data = new String[20];
		//タスクリストの初期化
		Tasklist = new ArrayList<TaskList>();
		Tasks = new ArrayList<Task>();
		Save_Tasks = new ArrayList<List<String>>();

		//タイマー割り込み
		Timeline timer = new Timeline(new KeyFrame(new Duration(10), new EventHandler<ActionEvent>(){
			 @Override
	         public void handle(ActionEvent event) {
				 cnt1++;
				 if(cnt1>4000) {
					 importSettingCsv();
					 time_visible();
					 AutoOpen();
					 cnt1=0;
				 }
				 if(push_task) {
					 try {
						 CleanWindow();
					 }finally {
						 int j;
						 for( j=0;j<Save_Tasks.size();j++) {
								if(Save_Tasks.get(j).get(1).equals(this_taskname)) {
									break;
								}
						}
						 taskedit_menu = new TaskEdit_menu(this_stage,Tasks,tasks_count,rightbox,this_taskname,Tasks.get(0),Save_Tasks.get(j).get(2),Save_Tasks.get(j).get(3),Save_Tasks.get(j).get(4),Save_Tasks.get(j).get(5),Save_Tasks.get(j).get(6)); 
						 push_task=false;
					 }
					
				 }
				 
				 //タスク追加ボタンが押されたか
				 if(task_Add) {
					 if(this_taskList.equals("今日の予定")) {
						 task_count++;
					 }
					 	 
					 ReSet_Task();
					 exportCsv(Save_Tasks);
					 task_Add=false;
				 }
				 //タスクリストに変更あり
				 if(task_ListAdd) {
					 clear_task(this_taskList);
					 this_taskList = Tasklist.get(Tasklist.size()-1).taskname;
					 task_ListAdd = false;
					 Taskname.setText(this_taskList);
				 }
				 push_Tasklist();
				 if(Change_tasklist) {					 
					 Change_tasklist=false;
					 clear_task(this_taskList);
					 this_taskList = Last_pust_TaskList();
					 Recleate_task(this_taskList);
					 Taskname.setText(this_taskList);
					 if(dable_tasklist) {
						 tasklistEd = new TaskListEdit_menu(this_stage,Tasklist,leftbox,this_taskList);
						 dable_tasklist=false;
					 }
				 }
				 if(tasklistEd.this_Delete) {
					 RemoveTaskList(tasklistEd);
					 tasklistEd.this_Delete= false;
				 }
				 
				 if(taskedit_menu.this_Delete) {
					 RemoveTask(taskedit_menu);
					 taskedit_menu.this_Delete=false;
					 if(this_taskList.equals("今日の予定")) {
						 task_count--;	 
					 }
				 }
				 if(taskedit_menu.this_Cancel) {
					 int j;
					 for( j=0;j<Save_Tasks.size();j++) {
							if(Save_Tasks.get(j).get(1).equals(this_taskname)) {
								break;
							}
					}
					 Tasks.get(j).Task.setText(taskedit_menu.Taskname.getText());
					 Save_Tasks.get(j).set(1, taskedit_menu.Taskname.getText()) ;
					 Save_Tasks.get(j).set(2, taskedit_menu.Start_TaskListnameH.getText()) ;
					 Save_Tasks.get(j).set(3, taskedit_menu.Start_TaskListnameM.getText()) ;
					 Save_Tasks.get(j).set(4, taskedit_menu.End_TaskListnameH.getText()) ;
					 Save_Tasks.get(j).set(5, taskedit_menu.End_TaskListnameM.getText()) ;
					 if(taskedit_menu.Auto_Start)
						 Save_Tasks.get(j).set(6,"1");
					 else
						 Save_Tasks.get(j).set(6,"0");
					 exportCsv(Save_Tasks);
					 taskedit_menu.this_Cancel=false;
				 }			
			}
		}));
		timer.setCycleCount(Timeline.INDEFINITE);
	    timer.play();
		   
	    //左側
		Set_SettingButton();		
		Set_AddListButton();
		Set_ColorLine(0,4);
		for(;tasklist_count<2;tasklist_count++) {
			 TaskList this_tasklist = new TaskList();
			 this_tasklist.grid = leftbox;
			 
			 if(tasklist_count==0) 
				 this_tasklist.taskname = "今日の予定";
			 else if(tasklist_count==1)
				 this_tasklist.taskname = "やりたいこと";
			 
			 this_tasklist.Set_TaskList(0, tasklist_count+1,this);
			 Tasklist.add(this_tasklist);
		 }
		//右側
		Set_Taskname();
		Set_BlankLine("2020/2/28");
		Set_AddTaskButton();
		

		importCsv();
		open_csv();
		importSettingCsv();	

		leftbox.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		rightbox.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		 
		
		
		ScrollPane sp = new ScrollPane();
		sp.setContent(leftbox);
		sp.setStyle("-fx-control-inner-background: "+MAIN_COLOR+";"
				+"-fx-background: "+MAIN_COLOR+";"
				+"-fx-background-insets: 0;");
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		//sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		ScrollPane sp1 = new ScrollPane();
		sp1.setContent(rightbox);
		sp1.setStyle("-fx-control-inner-background: "+MAIN_COLOR+";"
				+"-fx-background: "+MAIN_COLOR+";"
				+"-fx-background-insets: 0;");
		sp1.setHbarPolicy(ScrollBarPolicy.NEVER);
		//sp1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		hbox.getChildren().add(sp);
		hbox.getChildren().add(sp1);		
		
		Scene scene = new Scene(hbox, 1320, 240);
		stage.setScene(scene);
		stage.show();
	}
	/***
	 * タスクデータを追加する関数
	 */
	private void ReSet_Task() {
	 	List<String>this_Save_task = new ArrayList<String>();
	 	this_Save_task.add(this_taskList);
	 	this_Save_task.add(this_taskname);
	 	this_Save_task.add(Task_Start_H);
	 	this_Save_task.add(Task_Start_M);
	 	this_Save_task.add(Task_End_H);
	 	this_Save_task.add(Task_End_M);
	 	if(Autotask)
	 		this_Save_task.add("1");
	 	else
	 		this_Save_task.add("0");
		task_Add=false;
		Save_Tasks.add(this_Save_task);
	}
	public void Set_set_leftvar(VBox vb) {
		ScrollBar sp = new ScrollBar();
		sp.setStyle("-fx-background-color:"+MAIN_COLOR+";");
	    
		sp.setMin(300);
	    sp.setOrientation(Orientation.VERTICAL);
	    sp.setPrefHeight(180);
	    sp.setMax(360);
	    sp.valueProperty().addListener(( ObservableValue<? extends Number>observ,Number old_val, Number new_val) -> {
	                  vb.setLayoutY(-new_val.doubleValue()*100);
	    });
	    hbox.getChildren().add(sp);
	}
	/***
	 * 設定ボタンの初期化
	 */	
	public void Set_SettingButton() {
		 SettingButton = new Button("・・・");
		 SettingButton.setFont( new Font(20));
		 SettingButton.setTextFill(Color.WHITE);
		 SettingButton.setPrefSize(70, 30);
		 SettingButton.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		 EventHandler<MouseEvent> Set_SettingButtonClick = ( event ) -> this.SettingButtonClick( event );
		 SettingButton.addEventHandler( MouseEvent.MOUSE_CLICKED , Set_SettingButtonClick );	 
		 leftbox.getChildren().add(SettingButton);
	}
	/***
	 * リスト追加ボタンの初期化
	 */
	public void Set_AddListButton() {
		 AddListButton = new Button("リストの作成　+");
		 AddListButton.setFont( new Font(18) );
		 AddListButton.setTextFill(Color.WHITE);
		 AddListButton.setPrefSize(300, 50);
		 AddListButton.setStyle("-fx-background-color:"+MAIN_COLOR+";");
		 EventHandler<MouseEvent> AddListButtonClick = ( event ) -> this.AddListButtonClick( event );
		 AddListButton.addEventHandler( MouseEvent.MOUSE_CLICKED , AddListButtonClick );
		 leftbox.getChildren().add(AddListButton);
	}
	/***
	 * タスク追加ボタンの初期化
	 */
	public void Set_AddTaskButton() {
		 AddTaskButton = new Button("タスクの作成　+");
		 AddTaskButton.setFont( new Font(18) );
		 AddTaskButton.setTextFill(Color.WHITE);
		 AddTaskButton.setPrefSize(900, 50);
		 AddTaskButton.setStyle("-fx-background-color:"+SUB_COLOR+";");
		 EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.AddTaskButtonClick( event );
		 AddTaskButton.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );
		 rightbox.getChildren().add(AddTaskButton);
	}
	/***
	 * タスクリストの名前の初期化
	 * @param task
	 */
	public void Set_Taskname() {
		Taskname = new Text(50, 100, this_taskList);
		Taskname.setFont(new Font(30));
		Taskname.setFill(Color.WHITE);
		rightbox.getChildren().add(Taskname);
	}

	public void Set_ColorLine(int x,int y) {
		clorline = new Rectangle(300,3);
		clorline.setFill(Color.web("367145"));
		leftbox.getChildren().add(clorline);
	}
	public void Set_BlankLine(String date) {
		blankline = new Text(300,50,date);
		blankline.setFont(new Font(20));
		blankline.setFill(Color.WHITE);
		time_visible();
		rightbox.getChildren().add(blankline);
	}
	public void SettingButtonClick(MouseEvent e ) {
		try {
			CleanWindow();
		}finally {
			setting_menu = new Setting_menu(this_stage,Setting_data[15],Setting_data[16],Setting_data[17],Setting_data[18],Setting_data); 
		}
		
	}
	public void AddListButtonClick(MouseEvent e ) {
		try {
			CleanWindow();
		}finally {
			taskadd_menu = new TaskListAdd_menu(this_stage ,Tasklist ,taskslist_count, leftbox, this);
			importCsv();
		}

	}
	public void AddTaskButtonClick(MouseEvent e ) {
		try {
			CleanWindow();
		}finally {
			Settime[0] = Setting_data[15];
			Settime[1] = Setting_data[16];
			for(int i=0;i<task_count;i++) {
				MathTime(Settime,60);	
				Settime = addtime;
			}
			MathTime(Settime,60);	
			
			this_task_menu = new TaskAdd_menu(this_stage,Tasks,tasks_count,rightbox,this,this_taskList,Settime[0],Settime[1],addtime[0],addtime[1]);
		}
	}
	/***
	 * CSVに保存
	 * @param idList
	 * @param nameList
	 * @param All_Tasks
	 */
	public void exportCsv(List<List<String>> All_Tasks){
			FileWriter f= null;
            try {
             f = new FileWriter("person.csv");
              //リストの内容を順に処理
              for(int i = 0; i < All_Tasks.size(); i++){
                  for(int j = 0;j < All_Tasks.get(i).size();j++) {
                  	f.append(All_Tasks.get(i).get(j));
                      if(j<All_Tasks.get(i).size()-1)
                      	f.append(",");
                  }
                  f.append("\r\n");	
              }
              System.out.println("CSVファイル出力完了");

            } catch (Exception e) {
              e.printStackTrace();
            } finally {

              try {
                f.flush();
                f.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
    }	
	public void importCsv(){
		try {
			  Save_Tasks = new ArrayList<List<String>>();
			 
		      File f = new File("person.csv");
		      BufferedReader br = new BufferedReader(new FileReader(f));
		 
		      String line;
		      // 1行ずつCSVファイルを読み込む
		      while ((line = br.readLine()) != null) {
		        String[] data = line.split(",", 0); // 行をカンマ区切りで配列に変換
		        List<String> this_list = new ArrayList<String>();
		 
		        for (String elem : data) {
		        	this_list.add(elem);
		        	System.out.println(elem);
		        }
		        Save_Tasks.add(this_list);
		      }
		      br.close();
		    } catch (IOException e) {
		      System.out.println(e);
		    }
	}

	public void importSettingCsv(){
		try {
			 
		      File f = new File("Shortcuts.csv");
		      BufferedReader br = new BufferedReader(new FileReader(f));
		 
		      String line;
		      // 1行ずつCSVファイルを読み込む
		      while ((line = br.readLine()) != null) {
		        Setting_data = line.split(",", 0); // 行をカンマ区切りで配列に変換
		      }
		      br.close();
		    } catch (IOException e) {
		      System.out.println(e);
		      Setting_data[15]="10";
		      Setting_data[16]="30";
		      Setting_data[17]="17";
		      Setting_data[18]="0";
		      Setting_data[19]="4";
		    }
	}

	public void open_csv() {
		for(int i = 0;i<Save_Tasks.size();i++) {
			Task this_task = new Task();
			this_task.grid = rightbox;
			tasks_count++;
			String this_tasklist_name = Save_Tasks.get(i).get(0);
			
			if(!this_tasklist_name.equals("今日の予定") && !this_tasklist_name.equals("やりたいこと") && !Last_tasklist(this_tasklist_name)) {
				 TaskList this_tasklist = new TaskList();
				 this_tasklist.grid = leftbox;
				 this_tasklist.taskname = Save_Tasks.get(i).get(0);
				 this_tasklist.Set_TaskList(0, taskslist_count+1,this);
				 Tasklist.add(this_tasklist);
			}
			this_task.taskname = Save_Tasks.get(i).get(1);
			this_task.Set_Task(1, tasks_count+2,this_tasklist_name,this,"#313131");
			Tasks.add(this_task);
			if(!this_tasklist_name.equals("今日の予定")) {
				Tasks.get(i).Task.setVisible(false);
				Tasks.get(i).Task.setManaged(false);
			}else {
				task_count++;
			}
		}
	}
	public void clear_task(String listname) {
		int i = 0;
		for(;i<Tasks.size();i++) {		
			if(Tasks.get(i).tasklistname.equals(listname)) {
				Tasks.get(i).Task.setVisible(false);
				Tasks.get(i).Task.setManaged(false);
			}
		}
	}

	public void Recleate_task(String listname) {
		int i = 0;
		for(;i<Tasks.size();i++) {
			if(Tasks.get(i).tasklistname.equals(listname) && !Tasks.get(i).Delete) {
				Tasks.get(i).Set_Task(1, tasks_count+2, listname, this,"#313131");
			}
		}
	}

	public String Last_pust_TaskList() {
		String ret="";
		for(int i = 0;i<Tasklist.size();i++) {
			if(Tasklist.get(i).push_tasklist) {
				ret = Tasklist.get(i).taskname;
				Tasklist.get(i).push_tasklist=false;
			}
		}
		return ret;
	}
	private void push_Tasklist() {
		for(int i = 0;i<Tasklist.size();i++) {
			if(Tasklist.get(i).push_tasklist) {
				Change_tasklist=true;
			}
			if(Tasklist.get(i).dable_click) {
				dable_tasklist=true;
			}
		}
	}
	private void time_visible() {
		LocalDateTime date1 = LocalDateTime.now();
			DateTimeFormatter dtformat1 = 
			DateTimeFormatter.ofPattern("yyyy年MM月dd日 E HH:mm");
			fdate1 = dtformat1.format(date1);
			blankline.setText(fdate1);
	}
	private Boolean Last_tasklist(String list_name) {
		Boolean ret = false;
		for(int i=0;i<Tasklist.size();i++) {
			if(list_name.equals(Tasklist.get(i).taskname)) {
				ret = true;
			}
		}
		return ret;
	}
	private void RemoveTask(TaskEdit_menu this_menu) {
		for(int i=0;i<Tasks.size();i++) {
			if(Tasks.get(i).taskname.equals(this_menu.thisTaskname)) {
				Tasks.get(i).Task.setVisible(false);
				Tasks.get(i).Task.setManaged(false);
				Tasks.get(i).Delete=true;
			}
		}
		for(int j=0;j<Save_Tasks.size();j++) {
			if(Save_Tasks.get(j).get(1).equals(this_menu.thisTaskname)) {
				Save_Tasks.remove(j);
			}
		}
		exportCsv(Save_Tasks);
	}
	private void RemoveTaskList(TaskListEdit_menu this_menu) {
		for(int i=0;i<Tasklist.size();i++) {
			if(Tasklist.get(i).taskname.equals(this_menu.this_tasklist)) {
				Tasklist.get(i).this_patient.setVisible(false);
				Tasklist.get(i).this_patient.setManaged(false);
				Tasklist.get(i).Delete=true;
			}
		}
		for(int j=0;j<Save_Tasks.size();j++) {
			if(Save_Tasks.get(j).get(0).equals(this_menu.this_tasklist)) {
				Save_Tasks.remove(j);
			}
		}
		exportCsv(Save_Tasks);
		
	}
	private void AutoOpen() {
		String fdateH = fdate1.substring(14, 16);
		String fdateM = fdate1.substring(17, 19);
		for(int j=0;j<Save_Tasks.size();j++) {
			if(Save_Tasks.get(j).get(6).equals("1")) {
				if(Save_Tasks.get(j).get(1).indexOf(Setting_data[1])!=-1||Save_Tasks.get(j).get(1).indexOf(Setting_data[2])!=-1) {
					//時間の設定
					if(TimeWatcher(Save_Tasks.get(j).get(2),fdateH)&&TimeWatcher(Save_Tasks.get(j).get(3),fdateM)) {
						if(!Setting_data[0].equals("null"))
							open_app(Setting_data[0]);
						else
							open_new("https://docs.google.com/spreadsheets/u/0/");
						}
				}
				if(Save_Tasks.get(j).get(1).indexOf(Setting_data[4])!=-1||Save_Tasks.get(j).get(1).indexOf(Setting_data[5])!=-1) {
					//時間の設定
					if(TimeWatcher(Save_Tasks.get(j).get(2),fdateH)&&TimeWatcher(Save_Tasks.get(j).get(3),fdateM)) {
						if(!Setting_data[3].equals("null"))
							open_app(Setting_data[3]);
						else
							open_new("https://docs.google.com/document/u/0/");
					}
				}
				
				if(Save_Tasks.get(j).get(1).indexOf(Setting_data[7])!=-1||Save_Tasks.get(j).get(1).indexOf(Setting_data[8])!=-1) {
					//時間の設定
					if(TimeWatcher(Save_Tasks.get(j).get(2),fdateH)&&TimeWatcher(Save_Tasks.get(j).get(3),fdateM)) {
						if(!Setting_data[6].equals("null"))
							open_app(Setting_data[6]);
						else
							open_new("https://docs.google.com/presentation/u/0/");
			
					}
				}
				
				if(Save_Tasks.get(j).get(1).indexOf(Setting_data[10])!=-1||Save_Tasks.get(j).get(1).indexOf(Setting_data[11])!=-1) {
					//時間の設定
					if(TimeWatcher(Save_Tasks.get(j).get(2),fdateH)&&TimeWatcher(Save_Tasks.get(j).get(3),fdateM)) {
						if(!Setting_data[9].equals("null"))
							open_app(Setting_data[9]);
						else
							open_new("https://mail.google.com/mail/u/0/#inbox");
					}
				}
				if(Save_Tasks.get(j).get(1).indexOf(Setting_data[13])!=-1||Save_Tasks.get(j).get(1).indexOf(Setting_data[14])!=-1) {
					if(TimeWatcher(Save_Tasks.get(j).get(2),fdateH)&&TimeWatcher(Save_Tasks.get(j).get(3),fdateM)) {
						if(!Setting_data[9].equals("null"))
							open_app(Setting_data[12]);
					}
				}	
			}
		}
	}
	private void open_new(String url) {
		Desktop desktop = Desktop.getDesktop();

		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void open_app(String link) {
		try {
		      Runtime rt = Runtime.getRuntime();
		      rt.exec(link);
		    } catch (IOException ex) {
		      ex.printStackTrace();
		    }
	}
	private Boolean TimeWatcher(String SaveTime, String Time ) {
		Boolean ret=false;
		if(SaveTime.length()==1) 
			SaveTime="0"+SaveTime;
		ret=SaveTime.equals(Time);
		return ret;
	}
	private void MathTime(String[]Start_time,int work_M) {
		int add_hour = work_M / 60;
		int add_minits = work_M % 60;
		addtime=new String[2];
		addtime[0]= String.valueOf(Integer.parseInt(Start_time[0]) + add_hour);
		addtime[1]= String.valueOf(Integer.parseInt(Start_time[1]) + add_minits);
	}
	private void CleanWindow() {
		 taskedit_menu.getScene().getWindow().hide();
		 setting_menu.getScene().getWindow().hide();
		 taskadd_menu.getScene().getWindow().hide();
		 this_task_menu.getScene().getWindow().hide();
	}
}