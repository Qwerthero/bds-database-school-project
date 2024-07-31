package org.but.flight.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private DataSourceConfig() {
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static synchronized void initializeDataSource(String appProperties) {
        if (appProperties == null) {
            try (InputStream resourceStream = DataSourceConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
                initializeDataSource(resourceStream);
            } catch (IOException | NullPointerException | IllegalArgumentException e) {
                logger.error("Configuration of the datasource was not successful.", e);
            } catch (Exception e) {
                logger.error("Could not connect to the database.", e);
            }
        } else {
            try (InputStream resourceStream = Files.newInputStream(Paths.get(appProperties))) {
                initializeDataSource(resourceStream);
            } catch (IOException | NullPointerException | IllegalArgumentException e) {
                logger.error("Configuration of the datasource was not successful.", e);
            } catch (Exception e) {
                logger.error("Could not connect to the database.", e);
            }
        }
    }

    private static void initializeDataSource(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        config.setJdbcUrl(properties.getProperty("datasource.url"));
        config.setUsername(properties.getProperty("datasource.username"));
        config.setPassword(properties.getProperty("datasource.password"));
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
