package com.example.demo.datasource;

import net.sf.jasperreports.engine.JRDataSource;

public interface ReportBuilder extends JRDataSource, AutoCloseable {

    String getReportCode();
}
