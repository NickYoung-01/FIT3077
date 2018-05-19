package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import Model.Observer;
import Model.Stock;

public class GraphMonitor extends Observer {

	private TimeSeriesCollection stockDataSet;
	
	//holds a reference to the stock
	private Stock stock;
	private JFrame frame;
	
	public GraphMonitor(Stock stock) {
		this.stock = stock;
		this.stock.registerObserver(this);
		
		frame = new JFrame(stock.getSymbol() + " Monitor");
		
		XYDataset stockDataset = createStockDataset();

		final JFreeChart chart = createChart(stockDataset);
		
		//We want to perform our own closing action
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//Add a listener to the close button
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeWindow();
			}
		});
		
		frame.setSize(900, 600);

		
		//Need to access os in order to get screen size, toolkit gives us that access
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//get the dimenions of our screen size
		Dimension dim = toolkit.getScreenSize();
		//the below coordinates will center the screen
		int xCord = ((dim.width / 2) - (frame.getWidth() / 2));
		int yCor = ((dim.height / 2) + (frame.getHeight() / 2));
		frame.setLocation(xCord, yCor);

        //Our chart needs a chartPanel
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        final XYPlot plot = chart.getXYPlot();
        //format xaxis to have a date format
        DateAxis xaxis = (DateAxis) plot.getDomainAxis();
        //format the xaxis's label to our datetime
        xaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        xaxis.setVerticalTickLabels(true);
        
        //set colour of monitor
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);

        ValueAxis yaxis = plot.getRangeAxis();
        //yaxis also automatically grows
        yaxis.setAutoRange(true);

        //Set the size of our chart's panel
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        
        //add panel to jframe
        frame.add(chartPanel);

		frame.setVisible(true);
	}
	
	//create the dataset that will store the stock data over time
	private XYDataset createStockDataset() {
		stockDataSet = new TimeSeriesCollection();
		TimeSeries series = new TimeSeries("Price");
		//the x value will be our datetime, and y will be the price
		series.add(new Second(stock.getDateTime()), Double.parseDouble(stock.getLastTrade()));
		stockDataSet.addSeries(series);
		return stockDataSet;
	}
	
	private JFreeChart createChart(XYDataset stockDataset) {
		return ChartFactory.createTimeSeriesChart(
	            stock.getSymbol() + " TimeSeries Chart",
	            "Time",//x-axis
	            "Price (AUD)",//y-axis
	            stockDataset, //dataset
	            true,
	            false,
	            false
	        );
	}

	@Override
	public void update() {
		RegularTimePeriod timePeriod = new Second(stock.getDateTime());
		if (stockDataSet.getSeriesCount() >= 1) {
			//add the new data to our DataSet
			stockDataSet.getSeries(0).addOrUpdate(timePeriod, Double.parseDouble(stock.getLastTrade()));
		}
	}
	
	//Remove this observer from Stock and close JFrame
	public void closeWindow() {
		stock.removeObserver(this);
		//closes the JFrame
		frame.dispose();
	}

}
