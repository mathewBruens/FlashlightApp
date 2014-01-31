/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spotlightppm;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Mathew
 */
public class BarChart {

  
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private final ArrayList<Timestamp> mouseData;
    private final ArrayList<Timestamp> keyData;
    Timestamp start;
    Timestamp end;

    public BarChart(ArrayList<Timestamp> mouseData, ArrayList<Timestamp> keyData, Timestamp start, Timestamp end) {
        this.mouseData = mouseData;
        this.keyData = keyData;
        this.start = start;
        this.end = end;



    }
    
    public void createBarChart(){ 
        
        dataset.setValue(mouseData.size(), "Number of Presses", "Mouse");
        dataset.setValue(keyData.size(),"Number of Presses" , "Key");
        String title = "Number of Mouse and Key Presses between "+ start.toString() + " - " + end.toString();
                JFreeChart chart = ChartFactory.createBarChart(title,
                "Mouse/Key", "Number of Presses", dataset, PlotOrientation.VERTICAL,
                false, true, false);
        try {
            ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
        } catch (IOException ex) {
            Logger.getLogger(BarChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

}
