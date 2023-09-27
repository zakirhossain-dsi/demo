package com.example.demo.datasource;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Component
public class SampleReportBuilder implements ReportBuilder {

  private static final int BATCH_SIZE = 5;
  private final PreparedStatement preparedStatement;
  private final Connection databaseConnection;
  private ResultSet resultSet;
  private int currentPage = 0;

  public SampleReportBuilder(JdbcTemplate jdbcTemplate) throws SQLException {
    String sqlQuery = "SELECT * FROM student LIMIT ? OFFSET ?";
    jdbcTemplate.setFetchSize(BATCH_SIZE);
    databaseConnection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
    preparedStatement = databaseConnection.prepareStatement(sqlQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    preparedStatement.setInt(1, BATCH_SIZE);
    preparedStatement.setInt(2, currentPage * BATCH_SIZE);
    preparedStatement.setFetchSize(BATCH_SIZE);
    resultSet = preparedStatement.executeQuery();
  }

  @Override
  public boolean next() throws JRException {
    try {
      if (!resultSet.next()) {
        currentPage++;
        resultSet.close();
        preparedStatement.setInt(2, currentPage * BATCH_SIZE);
        log.info(String.format("Querying the records from %s", currentPage * BATCH_SIZE));
        resultSet = preparedStatement.executeQuery();
        return resultSet.next();
      }
      return true;
    } catch (SQLException e) {
      throw new JRException(e);
    }
  }

  @Override
  public Object getFieldValue(JRField jrField) throws JRException {
    try {
      return resultSet.getObject(jrField.getName());
    } catch (SQLException e) {
      throw new JRException(e);
    }
  }

  @Override
  public void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
    } catch (SQLException e) {
      log.error("Error while closing result set.", e);
    }

    try {
      if (preparedStatement != null) {
        preparedStatement.close();
      }
    } catch (SQLException e) {
      log.error("Error while closing prepared statement.", e);
    }

    try {
      if (databaseConnection != null) {
        databaseConnection.close();
      }
    } catch (SQLException e) {
      log.error("Error while closing database connection.", e);
    }
  }

  @Override
  public String getReportCode() {
    return "MLFF_DAILY_VIOLATION";
  }
}
