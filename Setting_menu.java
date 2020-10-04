package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;

public class Setting_menu extends Stage{
	private GridPane pane;
	private HBox hsbox;
	private HBox hebox;
	private HBox h_titlebox;
	private GridPane g_spreadsheets;
	private GridPane g_documents;
	private GridPane g_slide;
	private GridPane g_mail;
	private GridPane g_work;
	private static final String MAIN_COLOR = "#141414";
	private static final String Field_COLOR = "#101010";
	public Label SettingLabel;	
	
	public TextField Start_TaskListnameH;
	public TextField Start_TaskListnameM;
	public TextField End_TaskListnameH;
	public TextField End_TaskListnameM;
	
	
	private TextField spreadsheet_link;
	private TextField spreadsheet_shortcut1;
	private TextField spreadsheet_shortcut2;
	
	private TextField documents_link;
	private TextField documents_shortcut1;
	private TextField documents_shortcut2;
	
	private TextField slide_link;
	private TextField slide_shortcut1;
	private TextField slide_shortcut2;
	
	private TextField mail_link;
	private TextField mail_shortcut1;
	private TextField mail_shortcut2;
	
	private TextField work_link;
	private TextField work_shortcut1;
	private TextField work_shortcut2;
	
	public String[] Shortcuts;

	public Boolean this_Delete=false;
	public Boolean this_Cancel=false;
	private String StartH;
	private String StartM;
	private String EndH;
	private String EndM;
	
	public Setting_menu(Stage oya,String thisStartH,String thisStartM,String thisEndH,String thisEndM ,String[] SomeShortCut ){
		super();
		Shortcuts=SomeShortCut;
		
		StartH = thisStartH;
		StartM = thisStartM;
		EndH = thisEndH;
		EndM = thisEndM;
		pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		hsbox = new HBox(0);
		hsbox.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		hebox = new HBox(0);
		hebox.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		h_titlebox = new HBox(0);
		h_titlebox.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		
		g_spreadsheets = new GridPane();
		g_spreadsheets.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		g_spreadsheets.setHgap(10);
		g_spreadsheets.setVgap(5);
		
		g_documents = new GridPane();
		g_documents.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		g_documents.setHgap(10);
		g_documents.setVgap(5);
	
		g_slide = new GridPane();
		g_slide.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		g_slide.setHgap(10);
		g_slide.setVgap(5);
	
		
		g_mail = new GridPane();
		g_mail.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		g_mail.setHgap(10);
		g_mail.setVgap(5);
		
		g_work = new GridPane();
		g_work.setBackground(new Background(new BackgroundFill( Color.web("141414") , CornerRadii.EMPTY , Insets.EMPTY )));
		g_work.setHgap(10);
		g_work.setVgap(5);		
		
		Setting_Label();
		Set_CancelButton();
		Set_Start_timeLabel();
		Set_End_timeLabel();
		Set_Start_Combobox();
		Set_End_Combobox();
		Set_Shortcut_Label();
		Set_Spred();
		Set_document();
		Set_slide();
		Set_mail();
		Set_work();
		
		pane.add(h_titlebox, 0, 0);
		pane.add(hsbox, 0, 2);
		pane.add(hebox, 0, 4);
		
		pane.add(g_spreadsheets, 0, 6);
		pane.add(g_documents, 0, 7);
		pane.add(g_slide, 0, 8);
		
		pane.add(g_mail, 0, 9);
		pane.add(g_work, 0, 10);
		ScrollPane sp = new ScrollPane();
		sp.setContent(pane);
		sp.setStyle("-fx-control-inner-background: "+MAIN_COLOR+";"
				+"-fx-background: "+MAIN_COLOR+";"
				+"-fx-background-insets: 0;");
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		include_AllShortCut();
		Scene scene = new Scene(sp, 350, 500);
		this.setX( oya.getX() + 100);
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
		h_titlebox.getChildren().add(CancelButton);
	}
	private void Setting_Label() {
		SettingLabel = new Label ();
		SettingLabel.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		SettingLabel.setText("設定");
		SettingLabel.setFont( new Font(30) );
		SettingLabel.setPrefSize(220, 30);
		
		h_titlebox.getChildren().add(SettingLabel);
	}
	private void Set_Start_timeLabel() {
		Text Taskname = new Text(50, 100, "作業開始時刻");
		Taskname.setFont(new Font(15));
		Taskname.setFill(Color.WHITE);
		pane.add(Taskname, 0, 1);		
	}
	private void Set_End_timeLabel() {
		Text Taskname = new Text(50, 100, "作業終了時刻");
		Taskname.setFont(new Font(15));
		Taskname.setFill(Color.WHITE);
		pane.add(Taskname, 0, 3);		
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
	private void Set_Shortcut_Label() {
		Label Shortcut_Label = new Label ();
		Shortcut_Label.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		Shortcut_Label.setText("ショートカット");
		Shortcut_Label.setFont( new Font(30) );
		Shortcut_Label.setPrefSize(300, 30);
		
		pane.add(Shortcut_Label,0,5);
	}
	private void Set_Spred() {
		Label Spred_Label = new Label ();
		Spred_Label.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: LIGHTGREEN");
		Spred_Label.setText(" スプレッドシート");
		Spred_Label.setFont( new Font(20) );
		Spred_Label.setPrefSize(150, 30);
		g_spreadsheets.add(Spred_Label,0,0);
		
		spreadsheet_link = new TextField ();
		spreadsheet_link.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		spreadsheet_link.setPromptText("ソフトウェアのリンク");
		spreadsheet_link.setFont( new Font(15) );
		spreadsheet_link.setPrefSize(150, 20);
		g_spreadsheets.add(spreadsheet_link,1,0);
		
		Rectangle clorline1 = new Rectangle(150,3);
		clorline1.setFill(Color.web("367145"));
		g_spreadsheets.add(clorline1, 0, 2);

		Rectangle clorline2 = new Rectangle(150,3);
		clorline2.setFill(Color.web("367145"));
		g_spreadsheets.add(clorline2, 1, 2);

		spreadsheet_shortcut1 = new TextField ();
		spreadsheet_shortcut1.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		spreadsheet_shortcut1.setPromptText("キーワード");
		spreadsheet_shortcut1.setFont( new Font(20) );
		spreadsheet_shortcut1.setPrefSize(100, 25);
		g_spreadsheets.add(spreadsheet_shortcut1,0,1);

		spreadsheet_shortcut2 = new TextField ();
		spreadsheet_shortcut2.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		spreadsheet_shortcut2.setPromptText("キーワード");
		spreadsheet_shortcut2.setFont( new Font(20) );
		spreadsheet_shortcut2.setPrefSize(100, 25);
		g_spreadsheets.add(spreadsheet_shortcut2,1,1);
	}
	private void Set_document() {
		Label document_Label = new Label ();
		document_Label.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: BLUE");
		document_Label.setText(" ドキュメント");
		document_Label.setFont( new Font(20) );
		document_Label.setPrefSize(150, 30);
		g_documents.add(document_Label,0,0);
		
		documents_link = new TextField ();
		documents_link.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		documents_link.setPromptText("ソフトウェアのリンク");
		documents_link.setFont( new Font(15) );
		documents_link.setPrefSize(150, 20);
		g_documents.add(documents_link,1,0);
		
		Rectangle clorline1 = new Rectangle(150,3);
		clorline1.setFill(Color.web("6977ff"));
		g_documents.add(clorline1, 0, 2);

		Rectangle clorline2 = new Rectangle(150,3);
		clorline2.setFill(Color.web("6977ff"));
		g_documents.add(clorline2, 1, 2);

		documents_shortcut1 = new TextField ();
		documents_shortcut1.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		documents_shortcut1.setPromptText("キーワード");
		documents_shortcut1.setFont( new Font(20) );
		documents_shortcut1.setPrefSize(100, 25);
		g_documents.add(documents_shortcut1,0,1);

		documents_shortcut2 = new TextField ();
		documents_shortcut2.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		documents_shortcut2.setPromptText("キーワード");
		documents_shortcut2.setFont( new Font(20) );
		documents_shortcut2.setPrefSize(100, 25);
		g_documents.add(documents_shortcut2,1,1);
	}
	private void Set_slide() {
		Label slide_Label = new Label ();
		slide_Label.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: YELLOW");
		slide_Label.setText(" スライド");
		slide_Label.setFont( new Font(20) );
		slide_Label.setPrefSize(150, 30);
		g_slide.add(slide_Label,0,0);
		
		slide_link = new TextField ();
		slide_link.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		slide_link.setPromptText("ソフトウェアのリンク");
		slide_link.setFont( new Font(15) );
		slide_link.setPrefSize(150, 20);
		g_slide.add(slide_link,1,0);
		
		Rectangle clorline1 = new Rectangle(150,3);
		clorline1.setFill(Color.web("ffff45"));
		g_slide.add(clorline1, 0, 2);

		Rectangle clorline2 = new Rectangle(150,3);
		clorline2.setFill(Color.web("ffff45"));
		g_slide.add(clorline2, 1, 2);

		slide_shortcut1 = new TextField ();
		slide_shortcut1.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		slide_shortcut1.setPromptText("キーワード");
		slide_shortcut1.setFont( new Font(20) );
		slide_shortcut1.setPrefSize(100, 25);
		g_slide.add(slide_shortcut1,0,1);

		slide_shortcut2 = new TextField ();
		slide_shortcut2.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		slide_shortcut2.setPromptText("キーワード");
		slide_shortcut2.setFont( new Font(20) );
		slide_shortcut2.setPrefSize(100, 25);
		g_slide.add(slide_shortcut2,1,1);
	}
	private void Set_mail() {
		Label mail_Label = new Label ();
		mail_Label.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: RED");
		mail_Label.setText(" メール");
		mail_Label.setFont( new Font(20) );
		mail_Label.setPrefSize(150, 30);
		g_mail.add(mail_Label,0,0);
		
		mail_link = new TextField ();
		mail_link.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		mail_link.setPromptText("ソフトウェアのリンク");
		mail_link.setFont( new Font(15) );
		mail_link.setPrefSize(150, 20);
		g_mail.add(mail_link,1,0);
		
		Rectangle clorline1 = new Rectangle(150,3);
		clorline1.setFill(Color.web("ff6253"));
		g_mail.add(clorline1, 0, 2);

		Rectangle clorline2 = new Rectangle(150,3);
		clorline2.setFill(Color.web("ff6253"));
		g_mail.add(clorline2, 1, 2);

		mail_shortcut1 = new TextField ();
		mail_shortcut1.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		mail_shortcut1.setPromptText("キーワード");
		mail_shortcut1.setFont( new Font(20) );
		mail_shortcut1.setPrefSize(100, 25);
		g_mail.add(mail_shortcut1,0,1);

		mail_shortcut2 = new TextField ();
		mail_shortcut2.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		mail_shortcut2.setPromptText("キーワード");
		mail_shortcut2.setFont( new Font(20) );
		mail_shortcut2.setPrefSize(100, 25);
		g_mail.add(mail_shortcut2,1,1);
	}
	private void Set_work() {
		Label work_Label = new Label ();
		work_Label.setStyle("-fx-background-color:"+MAIN_COLOR+";"
				+ "-fx-text-fill: WHITE");
		work_Label.setText("仕事");
		work_Label.setFont( new Font(20) );
		work_Label.setPrefSize(150, 30);
		g_work.add(work_Label,0,0);
		
		work_link = new TextField ();
		work_link.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		work_link.setPromptText("ソフトウェアのリンク");
		work_link.setFont( new Font(15) );
		work_link.setPrefSize(150, 20);
		g_work.add(work_link,1,0);
		
		Rectangle clorline1 = new Rectangle(150,3);
		clorline1.setFill(Color.web("cfcfcf"));
		g_work.add(clorline1, 0, 2);

		Rectangle clorline2 = new Rectangle(150,3);
		clorline2.setFill(Color.web("cfcfcf"));
		g_work.add(clorline2, 1, 2);

		work_shortcut1 = new TextField ();
		work_shortcut1.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		work_shortcut1.setPromptText("キーワード");
		work_shortcut1.setFont( new Font(20) );
		work_shortcut1.setPrefSize(100, 25);
		g_work.add(work_shortcut1,0,1);

		work_shortcut2 = new TextField ();
		work_shortcut2.setStyle("-fx-background-color:"+Field_COLOR+";"
				+ "-fx-text-fill: WHITE");
		work_shortcut2.setPromptText("キーワード");
		work_shortcut2.setFont( new Font(20) );
		work_shortcut2.setPrefSize(100, 25);
		g_work.add(work_shortcut2,1,1);
	}
	private void Set_AllShortCut() {
		Shortcuts[0] = spreadsheet_link.getText();
		Shortcuts[1] = spreadsheet_shortcut1.getText();
		Shortcuts[2] = spreadsheet_shortcut2.getText();
		Shortcuts[3] = documents_link.getText();
		Shortcuts[4] = documents_shortcut1.getText();
		Shortcuts[5] = documents_shortcut2.getText();
		Shortcuts[6] = slide_link.getText();
		Shortcuts[7] = slide_shortcut1.getText();
		Shortcuts[8] = slide_shortcut2.getText();
		Shortcuts[9] = mail_link.getText();
		Shortcuts[10] = mail_shortcut1.getText();
		Shortcuts[11] = mail_shortcut2.getText();
		Shortcuts[12] = work_link.getText();
		Shortcuts[13] = work_shortcut1.getText();
		Shortcuts[14] = work_shortcut2.getText();
		Shortcuts[15] = Start_TaskListnameH.getText();
		Shortcuts[16] = Start_TaskListnameM.getText();
		Shortcuts[17] = End_TaskListnameH.getText();
		Shortcuts[18] = End_TaskListnameM.getText();
	}
	private void include_AllShortCut() {
		for(int shortcnt = 0;shortcnt<15;shortcnt++) {
			if(!Shortcuts[shortcnt].equals("null")) {
				switch(shortcnt) {
					case 0:	
						spreadsheet_link.setText(Shortcuts[0]);
						break;
					case 1:
						spreadsheet_shortcut1.setText(Shortcuts[1]);
						break;
					case 2:
						spreadsheet_shortcut2.setText(Shortcuts[2]);
						break;
					case 3:
						documents_link.setText(Shortcuts[3]);
						break;
					case 4:
						documents_shortcut1.setText(Shortcuts[4]);
						break;
					case 5:
						documents_shortcut2.setText(Shortcuts[5]);
						break;
					case 6:
						slide_link.setText(Shortcuts[6]);
						break;
					case 7:
						slide_shortcut1.setText(Shortcuts[7]);
						break;
					case 8:
						slide_shortcut2.setText(Shortcuts[8]);
						break;
					case 9:
						mail_link.setText(Shortcuts[9]);
						break;
					case 10:
						mail_shortcut1.setText(Shortcuts[10]);
						break;
					case 11:
						mail_shortcut2.setText(Shortcuts[11]);
						break;
					case 12:
						work_link.setText(Shortcuts[12]);
						break;
					case 13:
						work_shortcut1.setText(Shortcuts[13]);
						break;
					case 15:
						work_shortcut2.setText(Shortcuts[14]);
						break;
				}	
			}	
		}
	}
	public void export_csv() {
		FileWriter f= null;
        try {
         f = new FileWriter("Shortcuts.csv");

          //リストの内容を順に処理
          
              for(int j = 0;j < 19;j++) {
              	f.append(Shortcuts[j]);
                f.append(",");
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
	public void CancelButtonClick(MouseEvent e) {
		this_Cancel = true;
		Set_AllShortCut();
		export_csv();
		this.getScene().getWindow().hide();
	}
	
}