import com.mycompany.server.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OliverThreadSafteyTest {

    @BeforeAll
    static void setUp(){
        Server server = new Server();
        Thread serverThread = new Thread(server) ;

        serverThread.start();
    }

    @ParameterizedTest
    @CsvSource({
            "'add:2024-04-18,LM051,CS4076,CSG001,13:00'",
            "'add:2024-04-18,LM051,CS4004,CSG002,13:00'",
            "'dis:LM051'"
    })
    void testSameClient(String message){
        while(Server.serverLoaded == false);

        TestClientServerConnection client = new TestClientServerConnection();
        String response = client.send(message);

    }

}
