/**
 * Created by aqueelmiqdad on 11/30/16.
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.List;

public class Scenes extends Application {



    @Override
    public void start(Stage stage) {

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

        Scene scene  = new Scene(root, scatterChart.getMaxWidth(), scatterChart.getMaxHeight(), Color.BLANCHEDALMOND);
        root.getChildren().add(scatterChart);
        scatterChart.getData().add(series);



        List<Lines> lines;
        if(sol < 10)
            lines = RW.read_lines("output/greedy_solution0" + sol + ".txt");
        else
            lines = RW.read_lines("output/greedy_solution" + sol + ".txt");

        final double Xoffset = 40.5*11/(maxx-minx);
        final double Yoffset = 29.1*11/(maxy-miny);

        System.out.print(maxx);

        for(Lines line: lines) {
            Line liness;
            if(line.dir == 'v')
                liness = new Line(40 + line.loc*Xoffset, 15,   40 + line.loc*Xoffset,   335);
            else
                liness = new Line(40, 335-line.loc*Yoffset,   485,   335-line.loc*Yoffset);
            root.getChildren().add(liness);

        }

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}