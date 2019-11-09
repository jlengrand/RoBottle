#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <unistd.h>

#include "Raspberry_Pi_2/pi_2_dht_read.h"

int main( void )
{
	printf( "Raspberry Pi wiringPi DHT11 Temperature test program\n" );

    int i;
    int res;
    float humidity = 0, temperature = 0;
    int sensor = 11;
    int pin = 4;
    for (i = 1; i < 15; ++i){
        res = pi_2_dht_read(sensor, pin, &humidity, &temperature);
//        printf("%d ", res);
        printf("Temperature : %f\n", temperature);
        printf("Humidity : %f\n", humidity);
        sleep(1);
    }

	return 0;
}