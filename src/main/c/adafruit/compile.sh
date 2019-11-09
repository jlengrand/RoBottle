#!/bin/sh

rm testAdafruit;
gcc -Wall -I./ -I./Raspberry_Pi_2 common_dht_read.c Raspberry_Pi_2/pi_2_mmio.c Raspberry_Pi_2/pi_2_dht_read.c testAdafruit.c -o testAdafruit