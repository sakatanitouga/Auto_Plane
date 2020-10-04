package application;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import application.ButtonUI;
//import java.util.Date;

public class TaskList {
	public VBox grid;
	public VBox this_patient;
	public Color font_color = Color.WHITE;
	public String taskname="�����̃^�X�N���X�g";
	private String MainColor = "#141414";
	
	private int fontsize=15;
	private int size_x=300;
	private int size_y=80;
	public Boolean dable_click=false;
	
	public boolean push_tasklist = false;
	public Boolean Delete = false;
	private Button Tasklist;
	private Rectangle clorline = new Rectangle(size_x,3);
	/***
	 * �^�X�N���X�g�{�^���̕\���̕ύX
	 * @param x�@:x���ł̃{�^���̈ʒuGrid��
	 * @param y�@:y���ł̃{�^���̈ʒuGrid��
	 */
	public void Set_TaskList(int x,int y,ButtonUI person) {
		
		this_patient = new VBox();
		clorline.setFill(Color.web("367145"));
		Tasklist = new Button(taskname);
		Tasklist.setFont( new Font(fontsize) ); 
		Tasklist.setTextFill(font_color);
		Tasklist.setTextAlignment(TextAlignment.RIGHT);
		
		Tasklist.setPrefSize(size_x, size_y);
		Tasklist.setStyle("-fx-background-color:"+MainColor+";");
		EventHandler<MouseEvent> AddTaskButtonClick = ( event ) -> this.AddTaskButtonClick( event ,person);
		Tasklist.addEventHandler( MouseEvent.MOUSE_CLICKED , AddTaskButtonClick );
		
		this_patient.getChildren().add(Tasklist);
		this_patient.getChildren().add(clorline);
		
		grid.getChildren().add(this_patient);
		//grid.add(this_patient, x, y); 
	}
	
	/***
	 * �^�X�N���X�g�{�^���������ꂽ���̔���
	 * @param e�@�F�}�E�X�C�x���g
	 */
	public void AddTaskButtonClick(MouseEvent e,ButtonUI person) {
		push_tasklist = true;
		person.Change_tasklist = true;
		if (e.getClickCount() >= 2){
			dable_click=true;// �_�u���N���b�N
		}
	}
	
	
}