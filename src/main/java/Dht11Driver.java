public class Dht11Driver {

    static {
        System.loadLibrary("dht11");
    }

    public static void main(String[] args) throws InterruptedException {
        Dht11Driver theDriver = new Dht11Driver();
        theDriver.sayHello();

        for (int i = 0; i < 10; i++) {
            float [] res = theDriver.getTemperatureAndHumidity();
            System.out.println(res[0] + ", " + res[1]);
            Thread.sleep(1000);
        }
    }

    private native void sayHello();

    private native float[] getTemperatureAndHumidity();
}