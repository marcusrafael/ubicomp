import urllib, random, time
cepurl = 'http://localhost:8085'

while True:
    param = urllib.urlencode({'stream':'TemperatureEvent', 'temperature':str(random.randrange(10,120))})
    urllib.urlopen(cepurl + "/sendevent?" + param)
    time.sleep(1)
