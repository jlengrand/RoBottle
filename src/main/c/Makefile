JAVA_HOME=/usr/lib/jvm/java-11-openjdk-armhf
INCLUDES = -I. -I../java  -Iadafruit/Raspberry_Pi_2 -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
HEADERS = ../java/driver.Dht11Driver.h adafruit/common_dht_read.h adafruit/Raspberry_Pi_2/pi_2_mmio.h adafruit/Raspberry_Pi_2/pi_2_dht_read.h
FILES = adafruit/common_dht_read.c adafruit/Raspberry_Pi_2/pi_2_mmio.c adafruit/Raspberry_Pi_2/pi_2_dht_read.c driver.Dht11Driver.c
OUT = libdht11

default: program

program: driver.Dht11Driver.c
	gcc -Wall $(INCLUDES) $(HEADERS) $(FILES) -o $(OUT)

library: driver.Dht11Driver.c
	gcc -shared -fPIC -o $(OUT).so $(INCLUDES) $(FILES)

move:
    -cp $(OUT).so

clean:
	-rm -f $(OUT).so
	-rm -f $(OUT)