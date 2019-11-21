import com.faunadb.client.FaunaClient;
import driver.Dht11Driver;
import fauna.Connection;
import fauna.FaunaApi;

import java.util.concurrent.ExecutionException;

public class Robottle {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Dht11Driver theDriver = new Dht11Driver();
        theDriver.sayHello();

        FaunaApi api = new FaunaApi();
        Thread.sleep(1000);

//        Let's see if it runs until tomorrow!
        while(true){
            float [] res = theDriver.getTemperatureAndHumidity();
            if(res[0] != 0 && res[1] != 0){
                System.out.println("Sending " + res[0] + " " + res[1]);
                api.add(res);
                api.update(res);
            }
            else{
                System.out.println("Incorrect data!");
            }
            Thread.sleep(10000);
        }
    }

}
