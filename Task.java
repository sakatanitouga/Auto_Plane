package application;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import application.ButtonUI;

public class Task {
	public VBox grid;
	public String taskname = "ñ≥ñºÇÃÉ^ÉXÉN";
	public String Shortcut;
	public String tasklistname;
	public int StartH;
	public int StartM;
	public int EndH;
	public int EndM;
	public Boolean Delete = false;
	
	public Button Task ;
	
	public void Set_Task(int x,int y,String thislistname ,ButtonUI person,String ButtonColor) {
		tasklistname = thislistname;
		Task = new Button(taskname);
		Task.setFont(new Font(15) );		
		Task.setTextFill(Color.web("WHITE"));
		Task.setTextAlignment(TextAlignment.RIGHT);		
		Task.setPrefSize(900, 90);
		Task.setStyle("-fx-background-color:"+ButtonColor+";");
		EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.Push_Task( event,person);
		Task.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );	
		//grid.add(Task, x, y);
		grid.getChildren().add(Task);
	}
	
	public void Push_Task(MouseEvent e,ButtonUI person) {
		person.push_task = true;
		person.this_taskname = taskname;
	}
}