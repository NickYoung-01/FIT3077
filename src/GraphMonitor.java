import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class GraphMonitor extends Observer {

	private TimeSeries series;
	
	private Stock stock;
	private JFrame frame;
	
	public GraphMonitor(Stock stock) {
		this.stock = stock;
		this.stock.registerObserver(this);
		
		frame = new JFrame(stock.getSymbol() + " Monitor");

		this.series = new TimeSeries("Price", Second.class);
		final TimeSeriesCollection dataSet = new TimeSeriesCollection(this.series);
		final JFreeChart chart = createChart(dataSet);
		
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
		
        final JPanel panel = new JPanel(new BorderLayout());

        //Our chart needs a chartpanel
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        //chart panel needs to be added to the frame's panel
        panel.add(chartPanel);

        //Set the size of our chart's panel
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        
        //add panel to jframe
        frame.add(panel);

		frame.setVisible(true);
	}
	
	private JFreeChart createChart(TimeSeriesCollection dataSet) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
	            stock.getSymbol() + " TimeSeries Chart",
	            "Time",
	            "Price",
	            dataSet,
	            true,
	            false,
	            false
	        );
	        final XYPlot plot = result.getXYPlot();

	        plot.setBackgroundPaint(Color.white);
	        plot.setDomainGridlinesVisible(true);
	        plot.setDomainGridlinePaint(Color.lightGray);
	        plot.setRangeGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.lightGray);

	        ValueAxis xaxis = plot.getDomainAxis();
	        xaxis.setAutoRange(true);

	        //the x axis will show the time in a timeLimit increment
	        //show 20 of these increments as our x axis
	        xaxis.setFixedAutoRange((1000.0 * stock.getTimeLimit())*20);
	        xaxis.setVerticalTickLabels(true);

	        ValueAxis yaxis = plot.getRangeAxis();
	        yaxis.setRange(0.0, Double.parseDouble(stock.getLastTrade()) + 50);

	        return result;
	}

	@Override
	public void update() {
        this.series.addOrUpdate(new Second(), Double.parseDouble(stock.getLastTrade()));
	}
	
	//Remove this observer from Stock and close JFrame
	public void closeWindow() {
		stock.removeObserver(this);
		//closes the JFrame
		frame.dispose();
	}

}
