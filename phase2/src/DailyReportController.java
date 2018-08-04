import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Set;
import java.text.DateFormat;


public class DailyReportController extends Controller implements Initializable {

    @FXML private Label dateLabel;
    @FXML private Label fareLabel;
    @FXML private Label costLabel;
    @FXML private Label profitLabel;
    @FXML private Label numStationReachedLabel;
    @FXML private Label numStationServicedLabel;
    @FXML private Label reachServiceRatioLabel;
    @FXML private TableView transitStatTable;
    @FXML private TableColumn transitLineColumn;
    @FXML private TableColumn numTripsColumn;
    @FXML private TableColumn ridershipColumn;
    @FXML private TableColumn avgRiderColumn;




    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }


}