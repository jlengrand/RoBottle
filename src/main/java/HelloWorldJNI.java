public class HelloWorldJNI {

    static {
        System.loadLibrary("dht11");
    }

    public static void main(String[] args) {
        HelloWorldJNI theJni = new HelloWorldJNI();
        theJni.sayHello();

        float [] res = theJni.getTemperatureAndHumidity();
        System.out.println(res[0] + ", " + res[1]);
    }

    private native void sayHello();

    private native float[] getTemperatureAndHumidity();
}