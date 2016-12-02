/**
 * Created by aqueelmiqdad on 11/30/16.
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Scenes extends Application {



    @Override
    public void start(Stage stage) throws InterruptedException {

        int sol = 1;

        if(!getParameters().getRaw().isEmpty())
            sol = Integer.parseInt(getParameters().getRaw().get(0));


        double minx = Double.MAX_VALUE, miny = Double.MAX_VALUE, maxx = Double.MIN_VALUE, maxy = Double.MIN_VALUE;
        List<Point> points;
        if(sol < 10)
             points = RW.read("input/instance0" + sol + ".txt");
        else
             points = RW.read("input/instance" + sol + ".txt");
        for(Point p : points) {
            if(minx > p.x)
                minx = p.x;
            if(maxx < p.x)
                maxx = p.x;
            if(miny > p.y)
                miny = p.y;
            if(maxy < p.y)
                maxy = p.y;
        }

        Group root = new Group();

        stage.setTitle("Plotting Solution");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis(--minx, ++maxx, 1);
        final NumberAxis yAxis = new NumberAxis(--miny, ++maxy, 1);
        xAxis.setLabel("");
        //creating the chart

        final ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Points");
        //populating the series with data

        for(Point p : points) {
            series.getData().add(new XYChart.Data(p.x, p.y));
        }

        Scene scene  = new Scene(root, scatterChart.getMaxWidth(), scatterChart.getMaxHeight());
        root.getChildren().add(scatterChart);
        scatterChart.getData().add(series);

        List<Lines> lines;
        if(sol < 10)
            lines = RW.read_lines("output/greedy_solution0" + sol + ".txt");
        else
            lines = RW.read_lines("output/greedy_solution" + sol + ".txt");

        final double Xoffset = 40.5*11/(maxx-minx);
        final double Yoffset = 29.1*11/(maxy-miny);


        for(Lines line: lines) {
            Line liness;
            if(line.dir == 'v')
                liness = new Line(40 + line.loc*Xoffset, 15,   40 + line.loc*Xoffset,   335);
            else
                liness = new Line(40, 335-line.loc*Yoffset,   485,   335-line.loc*Yoffset);
            root.getChildren().add(liness);

        }

        TextField userTextField = new TextField();
        userTextField.setText("Instance Number");
        userTextField.setLayoutX(100);
        userTextField.setLayoutY(420);
        Button submit = new Button("Chart Graph");
        submit.setLayoutX(300);
        submit.setLayoutY(420);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Node> rm = new ArrayList<Node>();
                for(Node n: root.getChildren()) {
                    rm.add(n);
                }
                for(Node n: rm) {
                    root.getChildren().remove(n);
                }
                int sol = Integer.parseInt(userTextField.getText());
                double minx = Double.MAX_VALUE, miny = Double.MAX_VALUE, maxx = Double.MIN_VALUE, maxy = Double.MIN_VALUE;
                List<Point> points;
                if(sol < 10)
                    points = RW.read("input/instance0" + sol + ".txt");
                else
                    points = RW.read("input/instance" + sol + ".txt");
                for(Point p : points) {
                    if(minx > p.x)
                        minx = p.x;
                    if(maxx < p.x)
                        maxx = p.x;
                    if(miny > p.y)
                        miny = p.y;
                    if(maxy < p.y)
                        maxy = p.y;
                }

                stage.setTitle("Plotting Solution");
                //defining the axes
                final NumberAxis xAxis = new NumberAxis(--minx, ++maxx, 1);
                final NumberAxis yAxis = new NumberAxis(--miny, ++maxy, 1);
                xAxis.setLabel("");
                //creating the chart

                final ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);

                //defining a series
                XYChart.Series series = new XYChart.Series();
                series.setName("Points");
                //populating the series with data

                for(Point p : points) {
                    series.getData().add(new XYChart.Data(p.x, p.y));
                }

                root.getChildren().add(scatterChart);
                scatterChart.getData().add(series);

                List<Lines> lines;
                if(sol < 10)
                    lines = RW.read_lines("output/greedy_solution0" + sol + ".txt");
                else
                    lines = RW.read_lines("output/greedy_solution" + sol + ".txt");

                final double Xoffset = 40.5*11/(maxx-minx);
                final double Yoffset = 29.1*11/(maxy-miny);


                for(Lines line: lines) {
                    Line liness;
                    if(line.dir == 'v')
                        liness = new Line(40 + line.loc*Xoffset, 15,   40 + line.loc*Xoffset,   335);
                    else
                        liness = new Line(40, 335-line.loc*Yoffset,   485,   335-line.loc*Yoffset);
                    root.getChildren().add(liness);

                }
                //root.getChildren().add(scatterChart);
                root.getChildren().add(submit);
                root.getChildren().add(userTextField);
                stage.setScene(scene);
                stage.show();

            }
        });
        root.getChildren().add(submit);
        root.getChildren().add(userTextField);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}