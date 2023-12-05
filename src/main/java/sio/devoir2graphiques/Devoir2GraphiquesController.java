package sio.devoir2graphiques;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import sio.devoir2graphiques.Tools.ConnexionBDD;
import sio.devoir2graphiques.Tools.GraphiqueController;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class Devoir2GraphiquesController implements Initializable
{
    ConnexionBDD maCnx;
    @FXML
    private Button btnGraph1;
    @FXML
    private Button btnGraph2;
    @FXML
    private Button btnGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private LineChart graph1;
    @FXML
    private Label lblTitre;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private PieChart graph3;
    @FXML
    private BarChart graph2;
    private GraphiqueController graphiqueController;
    private HashMap<Integer,Double> datasGraphique1;

    private HashMap<String,Integer> datasGraphique2;

    XYChart.Series<String,Number> serieGraph2;
    XYChart.Series<String,Number> serieGraph3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitre.setText("Devoir : Graphique n°1");
        apGraph1.toFront();

        try {
            maCnx = new ConnexionBDD();
            graphiqueController = new GraphiqueController();
            datasGraphique1 = graphiqueController.getDataGraphique1();
            XYChart.Series<String, Double> serieGraph1 = new XYChart.Series<>();
            Set<Integer> ages = datasGraphique1.keySet();

            for (Integer age : ages) {
                serieGraph1.getData().add(new XYChart.Data<>(String.valueOf(age), datasGraphique1.get(age)));
            }
            graph1.getData().add(serieGraph1);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void menuClicked(Event event) throws SQLException {
        if(event.getSource() == btnGraph1)
        {
            lblTitre.setText("Devoir : Graphique n°1");
            apGraph1.toFront();

            try {
                maCnx = new ConnexionBDD();
                graphiqueController = new GraphiqueController();
                datasGraphique1 = graphiqueController.getDataGraphique1();
                XYChart.Series<String, Double> serieGraph1 = new XYChart.Series<>();
                Set<Integer> ages = datasGraphique1.keySet();

                for (Integer age : datasGraphique1.keySet()) {
                    serieGraph1.getData().add(new XYChart.Data<>(String.valueOf(age), datasGraphique1.get(age)));
                }
                graph1.getData().add(serieGraph1);

            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else if(event.getSource() == btnGraph2)
        {
            lblTitre.setText("Devoir : Graphique n°2");
            apGraph2.toFront();

            // A vous de jouer

        }
        else
        {
            lblTitre.setText("Devoir : Graphique n°3");
            apGraph3.toFront();
            graph3.getData().clear();

            graphiqueController = new GraphiqueController();
            datasGraphique2 = graphiqueController.getDataGraphique2();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : datasGraphique2.entrySet()) {
                String sexe = entry.getKey();
                double pourcentage = entry.getValue();

                pieChartData.add(new PieChart.Data(sexe, pourcentage));
            }


            graph3.setData(pieChartData);



        }
    }
}