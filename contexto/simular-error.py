import urllib, random, time
cepurl = 'http://localhost:8085'

"""
Critico

  Temp         - 60 
  Humidade     - 30
  Luminosidade - 50

Atencao

  Temp         - 40
  Humidade     - 50
  Luminosidade - 50

Alerta

  Temp         - 30
  Humidade     - 50
  Luminosidade - 50

"""

data = {
   'normal': {
     'temperature': 60,
     'humidity': 30,
     'luminosity': 50
   },
   'critico': {
     'temperature': 60,
     'humidity': 30,
     'luminosity': 50
   },
   'atencao': {
     'temperature': 60,
     'humidity': 30,
     'luminosity': 50
   },
   'alerta': {
     'temperature': 60,
     'humidity': 30,
     'luminosity': 50
   },
   'vapor': {
     'temperature': 60,
     'humidity': 30,
     'luminosity': 50
   }, 
}


while True:
    param = urllib.urlencode({'stream':'TemperatureEvent', 'temperature':str(random.randrange(10,120))})
    urllib.urlopen(cepurl + "/sendevent?" + param)
    time.sleep(1)
