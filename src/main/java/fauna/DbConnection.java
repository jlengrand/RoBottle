package fauna;

import com.faunadb.client.FaunaClient;
import com.faunadb.client.query.Language;
import com.faunadb.client.types.Value;

import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;

public class DbConnection {

    private static final String API_KEY_NAME = "ROBOTTLE_KEY";
    private final static String DB_NAME = "robottle";
        private final static String COLLECTION_NAME = "sensors";
        private final static String UPDATE_DEVICE_NAME = "robottle_00";
        private final static String ADD_DEVICE_NAME = "robottle_01";
    private final static String INDEX_NAME = "sensor_data";

    public DbConnection(){}

    private static String getKey() {
        return System.getenv(API_KEY_NAME);
    }

    private static String getDbKey(FaunaClient adminClient) throws InterruptedException, ExecutionException {
        Value keyResults = adminClient.query(
                CreateKey(Obj("database", Database(Language.Value(DB_NAME)), "role", Language.Value("server")))
        ).get();

        return keyResults.at("secret").to(String.class).get();
    }

    public FaunaClient createConnection() throws ExecutionException, InterruptedException {
        System.out.println(getKey()!= null? "API key found" : "No API key found");

        FaunaClient adminClient = FaunaClient.builder()
                .withSecret(getKey())
                .build();

        return adminClient.newSessionClient(getDbKey(adminClient));
    }

    public void add(float[] res, FaunaClient client) throws ExecutionException, InterruptedException {
        Value addDataResult = client.query(
                Create(
                        Collection(Value(COLLECTION_NAME)),
                        Obj("data",
                                Obj( "temperature", Value(res[0]), "humidity", Value(res[1]) )
                        )
                )
        ).get();
        System.out.println("Added sensor data to collection " + COLLECTION_NAME + ":\n " + addDataResult + "\n");
    }

    public void update(float[] res, FaunaClient client) throws ExecutionException, InterruptedException {
        System.out.println(
            client.query(
                Update(
                    Select(Value("ref"), Get(Match(Index(INDEX_NAME), Value(UPDATE_DEVICE_NAME)))),
                    Obj("data",
                            Obj(
//                                    "name", Value(DEVICE_NAME),
                                    "temperature", Value(res[0]),
                                    "humidity", Value(res[1])
                            )
                    )
                )
            ).get()
        );
    }
}
