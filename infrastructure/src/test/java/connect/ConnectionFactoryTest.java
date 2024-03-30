package connect;

import com.nexus.java.api.infrastructure.connect.ConnectProperties;
import com.nexus.java.api.infrastructure.connect.ConnectionFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConnectionFactoryTest {

    ConnectProperties dbProperties = new ConnectProperties();
    private static final ThreadLocal<Connection> context = ThreadLocal.withInitial(() -> null);

    @Test
    @SneakyThrows(SQLException.class)
    void testGetConnection() {
        dbProperties.setUrl("jdbc:h2:mem:tecnico;DB_CLOSE_DELAY=-1");
        dbProperties.setPassword("");
        dbProperties.setUsername("sa");
        ConnectionFactory connectionFactory = new ConnectionFactory(dbProperties);
        Connection connection = connectionFactory.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
        connection.close();
    }

    @Test
    @SneakyThrows(SQLException.class)
    void testGetConnectionTwiceShouldReturnSameConnection() {
        dbProperties.setUrl("jdbc:h2:mem:tecnico;DB_CLOSE_DELAY=-1");
        dbProperties.setPassword("");
        dbProperties.setUsername("sa");

        ConnectionFactory connectionFactory = new ConnectionFactory(dbProperties);
        Connection connection1 = connectionFactory.getConnection();
        Connection connection2 = connectionFactory.getConnection();

        assertNotNull(connection1);
        assertNotNull(connection2);
        assertSame(connection1, connection2);
        connection1.close();
    }

    @Test
    @SneakyThrows(SQLException.class)
    void testClean() {
        dbProperties.setUrl("jdbc:h2:mem:tecnico;DB_CLOSE_DELAY=-1");
        dbProperties.setPassword("");
        dbProperties.setUsername("sa");

        ConnectionFactory connectionFactory = new ConnectionFactory(dbProperties);
        Connection connection = connectionFactory.getConnection();

        assertFalse(connection.isClosed());
        connectionFactory.clean();
        assertTrue(connection.isClosed());
    }
}
