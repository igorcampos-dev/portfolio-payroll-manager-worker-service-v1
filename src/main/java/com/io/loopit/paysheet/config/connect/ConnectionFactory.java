package com.io.loopit.paysheet.config.connect;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
@Component
@RequiredArgsConstructor
public class ConnectionFactory {

	private final ConnectProperties dbProperties;

	private static final ThreadLocal<Connection> context = ThreadLocal.withInitial(() -> null);

	public Connection getConnection() throws SQLException {
		Connection conn = context.get();
		if (conn == null || conn.isClosed()) {
			conn = createConnection();
			context.set(conn);
		}
		return conn;
	}

	public void clean() {
		Connection conn = context.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("Failed to close connection: {}", e.getMessage());
			}
			context.remove();
		}
	}

	private synchronized Connection createConnection() throws SQLException {
		return DriverManager.getConnection(dbProperties.getUrl(),
				                           dbProperties.getUsername(),
				                           dbProperties.getPassword());
	}

}