from time import sleep
import pifacedigitalio as p

p.init()
while(True):
     p.digital_write(0,1) #turn on      sleep(1)
     p.digital_write(0,0) #turn off
     sleep(1)
     p.digital_write(1,1) #turn on      sleep(1)
     p.digital_write(1,0) #turn off