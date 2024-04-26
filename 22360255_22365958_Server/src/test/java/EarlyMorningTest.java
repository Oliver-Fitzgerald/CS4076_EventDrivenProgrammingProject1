import com.mycompany.server.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EarlyMorningTest {
    @BeforeAll
    static void setUp(){
        Server server = new Server();
        Thread serverThread = new Thread(server) ;

        serverThread.start();
    }

    @ParameterizedTest
    @CsvSource({
            "'add:2024-04-18,LM051,CS4076,CSG001,13:00'",
            "'add:2024-04-18,LM051,CS4004,CSG001,15:00'",
            "'add:2024-04-18,LM051,CS4002,CSG001,09:00'",
            "'add:2024-04-18,LM051,CS4007,CSG002,10:00'",
            "'add:2024-04-18,LM051,CS4001,CSG002,17:00'",

            "'add:2024-04-18,LM071,MA001,A001,12:00'",
            "'add:2024-04-18,LM054,BA102,BG02,17:00'",
            "'add:2024-04-18,LM011,NA112,FB042,09:00'",


            "'ear:'"
    })
    void earlyMornTest(String message){
        while(Server.serverLoaded == false);

        TestClientServerConnection client = new TestClientServerConnection();
        String response = client.send(message);

    }
}
