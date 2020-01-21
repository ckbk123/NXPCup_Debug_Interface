package application.view;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Camera_Graph {
	private XYChart.Series<Number, Number> series;
	
	public XYChart.Series<Number, Number> getSeries() {
		return series;
	}
	
	public void setSeries(XYChart.Series<Number, Number> series) {
		this.series = series;
	}
	
	public AreaChart<Number, Number> generateGraph (String graph_name, int posX, int posY, int sizeX, int sizeY) {
		// area charts configuration to display camera debug data
		NumberAxis xAxis = new NumberAxis(0, 127, 32);
		NumberAxis yAxis = new NumberAxis(0, 255, 32);
		
		xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickMarkVisible(true);
        xAxis.setMinorTickVisible(false);
        yAxis.setAutoRanging(false);
        yAxis.setTickLabelsVisible(true);
        yAxis.setTickMarkVisible(true);
        yAxis.setMinorTickVisible(false);
        
		AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
		
		areaChart.setTitle(graph_name);

		series = new XYChart.Series<Number, Number>();
		
		// fill in 0 at init as default
		for (int j = 0; j < 128; ++j) series.getData().add(new XYChart.Data<Number, Number>(j, 0));

		areaChart.getData().add(series);
		
		areaChart.setPrefSize(sizeX, sizeY);
		areaChart.setLayoutX(posX);
		areaChart.setLayoutY(posY);

		areaChart.setAnimated(false);
		
		return areaChart;
	}
}
