# pythonprogramminglanguage.com
import sys
import urllib, random, time

import urllib.parse
import urllib.request

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import (QApplication, QCheckBox, QGridLayout, QGroupBox,
                             QMenu, QPushButton, QRadioButton, QHBoxLayout, QVBoxLayout, QWidget, QSlider,
QLabel
)
 
from PyQt5.QtWidgets import QApplication, QWidget, QInputDialog, QLineEdit


cepurl = 'http://13.82.180.189:8085'

 
class Window(QWidget):
    def __init__(self, parent=None):
        super(Window, self).__init__(parent)
        self.t = QLineEdit()
        self.t.setText(cepurl)
        grid = QGridLayout()
        l = QVBoxLayout()


        y = QHBoxLayout()
        y.addLayout(l)

        grid.addWidget(self.createExampleGroup("Temperature", self.valueChangeTemp, 25), 0, 0)
        grid.addWidget(self.createExampleGroup("Humidity", self.valueChangeHumidity, 50), 1, 0)
        grid.addWidget(self.createExampleGroup("Luminosity", self.valueChangeLuminosity, 50), 0, 1)
        grid.addWidget(self.t, 1, 1)

        l.addWidget(QLabel("<b>Ubicomp</b>"))
        l.addWidget(self.t)
        l.addLayout(grid)
        #grid1 = QHBoxLayout()
        #grid1.addWidget(t, 0, 0)
        #grid1.addWidget(grid, 0, 1)

        self.setLayout(y)
 

        z = QHBoxLayout()
        z.addWidget(QPushButton('Alerta', self))
        z.addWidget(QPushButton('Critico', self))
        z.addWidget(QPushButton('Info', self))

        l.addLayout(z)

        self.setWindowTitle("Ubicomp")
        self.resize(400, 300)
 
    def callEvent(self, name, value):
        name = str(name)
        value = str(value)
        p = {
            'stream': name + "Event",
            name.lower(): value
        }
        param = urllib.parse.urlencode(p)
        urllib.request.urlopen(self.t.text() + "/sendevent?" + param)
 
    def valueChangeTemp(self, t):
        self.callEvent("Temperature", t)

    def valueChangeHumidity(self, t):
        self.callEvent("Humidity", t)

    def valueChangeLuminosity(self, t):
        self.callEvent("Luminosity", t)

    def createExampleGroup(self, name, cb, defaultValue=25):
        groupBox = QGroupBox(str(name))
 
        slider = QSlider(Qt.Horizontal)
        slider.setFocusPolicy(Qt.StrongFocus)
        slider.setTickPosition(QSlider.TicksBothSides)
        slider.setTickInterval(10)
        slider.setSingleStep(1)
        slider.setValue(defaultValue)
        slider.valueChanged.connect(cb)

        vbox = QVBoxLayout()
        vbox.addWidget(slider)
        vbox.addStretch(1)
        groupBox.setLayout(vbox)
 
        return groupBox
 
 
if __name__ == '__main__':
    app = QApplication(sys.argv)
    clock = Window()
    clock.show()
    sys.exit(app.exec_())

