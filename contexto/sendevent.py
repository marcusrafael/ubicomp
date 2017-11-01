import urllib, random, time
#cepurl = 'http://13.82.180.189:8085'
cepurl = 'http://localhost:8085'

while True:
    param = urllib.urlencode({'stream':'TemperatureEvent', 'temperature':str(random.randrange(10,120))})
    urllib.urlopen(cepurl + "/sendevent?" + param)
    param = urllib.urlencode({'stream':'HumidityEvent', 'humidity':str(random.randrange(10,120))})
    urllib.urlopen(cepurl + "/sendevent?" + param)
    param = urllib.urlencode({'stream':'LuminosityEvent', 'luminosity':str(random.randrange(10,120))})
    urllib.urlopen(cepurl + "/sendevent?" + param)
    time.sleep(1)
