import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class windows extends Application {

    Stage window;
    Scene adminUserScene;

    public static void main(String[] args) {
        launch(args);
    }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Transit System");

    GridPane adminUserSceneGrid = new GridPane();
    adminUserSceneGrid.setPadding(new Insets(15, 10, 10, 10));
    adminUserSceneGrid.setVgap(10);
    adminUserSceneGrid.setHgap(10);


    HBox headerRow = new HBox();
    headerRow.setSpacing(200);
      GridPane.setConstraints(headerRow,1,0);
    // Scene Name label
    Label adminUserHeader = new Label("Admin User");
    adminUserHeader.setFont(Font.font(18));
    //GridPane.setConstraints(adminUserHeader,1,0);

    // Return to Main Button
      Button returnToMain = new Button("Return to Main");
      //GridPane.setConstraints(returnToMain,5,0);
      //returnToMain.setOnAction( e -> {})

      headerRow.getChildren().addAll(
              adminUserHeader,
              returnToMain);


      Label systemManagementHeader = new Label("System Management");
      systemManagementHeader.setFont(Font.font(16));
      GridPane.setConstraints(systemManagementHeader,1,1);

      /**
       * System Power on/off section
       */

      //Layout of System power on/off row
      HBox systemManagementRow1 = new HBox();
      systemManagementRow1.setSpacing(5);
      GridPane.setConstraints(systemManagementRow1,1,2);

      Label systemDateLabel = new Label("Date: ");
      systemDateLabel.setFont(Font.font(14));
      systemDateLabel.setMaxHeight(Double.MAX_VALUE);
      //systemDateLabel.setMaxWidth(60);
      //GridPane.setConstraints(systemDateLabel,1,2);

      TextField powerOnDateField = new TextField();
      powerOnDateField.setPromptText("enter yyyy-mm-dd");
      powerOnDateField.setMaxHeight(Double.MAX_VALUE);
      //GridPane.setConstraints(powerOnDateField,2,2);

      Button powerOnButton = new Button("Power On");
      powerOnButton.setMaxHeight(Double.MAX_VALUE);
      //powerOnButton.setMaxWidth(100);
      //.setOnAction( e -> {})

      Button powerOffButton = new Button("Power Off");
      powerOffButton.setMaxHeight(Double.MAX_VALUE);
      //powerOffButton.setMaxWidth(100);
      //.setOnAction( e -> {})


      systemManagementRow1.getChildren().addAll(
              systemDateLabel,
              powerOnDateField,
              powerOnButton,
              powerOffButton);

      Separator lineUnderSystemManagement = new Separator();
      GridPane.setConstraints(lineUnderSystemManagement,0,3);
      GridPane.setColumnSpan(lineUnderSystemManagement,6);

      /**
       * daily report section
       */

      Label dailyReportHeader = new Label("Daily Report");
    dailyReportHeader.setFont(Font.font(16));
    GridPane.setConstraints(dailyReportHeader,1,4);

    GridPane dailyReportRow = new GridPane();
    dailyReportRow.setPadding(new Insets(5, 5, 5, 0));
    dailyReportRow.setVgap(10);
    dailyReportRow.setHgap(5);
    GridPane.setConstraints(dailyReportRow,1,5);

    Label reportDateLabel = new Label("Date: ");
    reportDateLabel.setFont(Font.font(14));
    reportDateLabel.setMaxHeight(Double.MAX_VALUE);
    GridPane.setConstraints(reportDateLabel,0,0);

    TextField dailyReportDateField = new TextField();
    dailyReportDateField.setPromptText("enter yyyy-mm-dd");
    dailyReportDateField.setMaxHeight(Double.MAX_VALUE);
    GridPane.setConstraints(dailyReportDateField,1,0);

    Button runDailyReportButton = new Button("Run Daily Report");
    runDailyReportButton.setMaxHeight(Double.MAX_VALUE);
    GridPane.setConstraints(runDailyReportButton,3,0);
    //.setOnAction( e -> {})

    dailyReportRow.getChildren().addAll(
            reportDateLabel,
            dailyReportDateField,
            runDailyReportButton);

    Separator lineUnderDailyReport = new Separator();
    GridPane.setConstraints(lineUnderDailyReport,0,6);
    GridPane.setColumnSpan(lineUnderDailyReport,6);

    /**
     * Scheduling Section
     */

    Label transitSchedulingHeader = new Label("Transit Scheduling");
    transitSchedulingHeader.setFont(Font.font(16));
    GridPane.setConstraints(transitSchedulingHeader,1,7);

    HBox transitSchedulingRow1 = new HBox();
    transitSchedulingRow1.setSpacing(5);
    GridPane.setConstraints(transitSchedulingRow1,1,8);

    Label schedulingDateLabel = new Label("Date: ");
    schedulingDateLabel.setFont(Font.font(14));
    schedulingDateLabel.setMaxHeight(Double.MAX_VALUE);

    TextField schedulingDateField = new TextField();
    schedulingDateField.setPromptText("enter yyyy-mm-dd");
    schedulingDateField.setMaxHeight(Double.MAX_VALUE);

    transitSchedulingRow1.getChildren().addAll(
            schedulingDateLabel,
            schedulingDateField);

    HBox transitSchedulingRow2 = new HBox();
    transitSchedulingRow2.setSpacing(5);
    GridPane.setConstraints(transitSchedulingRow2,1,9);

    Label schedulingTransitLabel = new Label("Transit Line: ");
    schedulingTransitLabel.setFont(Font.font(14));
    schedulingTransitLabel.setMaxHeight(Double.MAX_VALUE);

    ComboBox<String> schedulingTransitList = new ComboBox<>();
    schedulingTransitList.setPromptText("Choose a Transit Line");
    schedulingTransitList.setMaxHeight(Double.MAX_VALUE);
    schedulingTransitList.getItems().addAll(
            "placeholder1",
            "placeholder2");

    transitSchedulingRow2.getChildren().addAll(
            schedulingTransitLabel,
            schedulingTransitList);



      adminUserSceneGrid.getChildren().addAll(
              headerRow,
              systemManagementHeader,
              systemManagementRow1,
              lineUnderSystemManagement,
              dailyReportHeader,
              dailyReportRow,
              lineUnderDailyReport,
              transitSchedulingHeader,
              transitSchedulingRow1,
              transitSchedulingRow2);


    adminUserScene = new Scene(adminUserSceneGrid, 500, 600);
    window.setScene(adminUserScene);
    window.show();

    }

}

