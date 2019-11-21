import com.faunadb.client.FaunaClient;
import fauna.DbConnection;

import java.util.concurrent.ExecutionException;

public class Dht11Driver {

    static {
//        System.loadLibrary("dht11");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Dht11Driver theDriver = new Dht11Driver();
//        theDriver.sayHello();

        DbConnection connection = new DbConnection();
        FaunaClient client = connection.createConnection();
        Thread.sleep(1000);

//        Let's see if it runs until tomorrow!
        while(true){
//            float [] res = theDriver.getTemperatureAndHumidity();
            float[] res = {26, 56};
            if(res[0] != 0 && res[1] != 0){
                System.out.println("Sending " + res[0] + " " + res[1]);
                connection.add(res, client);
                connection.update(res, client);
            }
            else{
                System.out.println("Incorrect data!");
            }
            Thread.sleep(10000);
        }
    }

    private native void sayHello();

    private native float[] getTemperatureAndHumidity();
}