package setup.fauna;

import com.faunadb.client.FaunaClient;
import com.faunadb.client.types.Value;

import java.util.concurrent.ExecutionException;

import static com.faunadb.client.query.Language.*;

// Used to create the database and indexes. Should be run only once!
public class FaunaDbSetup {

    private static final String API_KEY_NAME = "ROBOTTLE_KEY";

    private final static String DB_NAME = "robottle";
    private final static String COLLECTION_NAME = "sensors";
    private final static String INDEX_NAME = "sensor_data";
    private final static String INDEX_ALL_NAME = "all_sensors";
    private final static String DEVICE_NAME = "robottle_00";


    private static String getKey() {
        return System.getenv(API_KEY_NAME);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(getKey()!= null? "API key found" : "No API key found");

        FaunaClient adminClient = FaunaClient.builder()
                .withSecret(getKey())
                .build();
        System.out.println("Connected to Fauna database " + DB_NAME + " with server role\n");


        createDatabase(adminClient);
        Thread.sleep(1000);
        FaunaClient dbClient = adminClient.newSessionClient(getDbKey(adminClient));

        createCollection(dbClient);
        Thread.sleep(1000);

        createIndex(dbClient);
        Thread.sleep(1000);

        createAllIndex(dbClient);
        Thread.sleep(1000);

        createInitialValue(dbClient);

        adminClient.close();
        System.out.println("Client closed");
    }

    private static String getDbKey(FaunaClient adminClient) throws InterruptedException, ExecutionException {
        Value keyResults = adminClient.query(
                CreateKey(Obj("database", Database(Value(DB_NAME)), "role", Value("server")))
        ).get();

        return keyResults.at("secret").to(String.class).get();
    }

    private static void createIndex(FaunaClient dbClient) throws InterruptedException, ExecutionException {
        Value indexResults = dbClient.query(
                CreateIndex(
                        Obj("name", Value(INDEX_NAME),
                            "source", Collection(Value(COLLECTION_NAME)),
                            "unique", Value(true),
                            "terms", Arr(Obj("field", Arr(Value("data"), Value("name"))))
                            )
                )
        ).get();
        System.out.println("Create name index for " + DB_NAME + ":\n " + indexResults + "\n");
    }

    private static void createAllIndex(FaunaClient dbClient) throws InterruptedException, ExecutionException {
        Value indexResults = dbClient.query(
                CreateIndex(
                        Obj("name", Value(INDEX_ALL_NAME),
                                "source", Collection(Value(COLLECTION_NAME))
                        )
                )
        ).get();
        System.out.println("Create all index for " + DB_NAME + ":\n " + indexResults + "\n");
    }


    private static void createCollection(FaunaClient dbClient) throws InterruptedException, ExecutionException {
        Value collectionResults = dbClient.query(
                CreateCollection(
                        Obj("name", Value(COLLECTION_NAME), "history_days", null)
                )
        ).get();
        System.out.println("Create Collection for " + DB_NAME + ":\n " + collectionResults + "\n");
    }

    private static void createDatabase(FaunaClient adminClient) {
        adminClient.query(
                CreateDatabase(
                        Obj("name", Value(DB_NAME))
                )
        );

        System.out.println("Db created");
    }

    public static void createInitialValue(FaunaClient client) throws ExecutionException, InterruptedException {
        Value init = client.query(
                Create(
                        Collection(Value(COLLECTION_NAME)),
                        Obj("data",
                                Obj("name", Value(DEVICE_NAME))
                        )
                )
        ).get();
        System.out.println("Added initial item to collection " + COLLECTION_NAME + ":\n " + init + "\n");
    }
}
