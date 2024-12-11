import processing.serial.*; // importă biblioteca pentru comunicație serială
import java.awt.event.KeyEvent; // importă biblioteca pentru citirea datelor de la portul serial
import java.io.IOException;

Serial myPort; // definește obiectul Serial
String angle = "";
String distance = "";
String data = "";
String noObject;
float pixsDistance;
int iAngle, iDistance;
int index1 = 0;
int index2 = 0;
PFont orcFont;
PGraphics radarBuffer;

void setup() {
  size(1200, 700); // **SCHIMBĂ ACESTA CU REZOLUȚIA ECRANULUI TĂU**
  smooth();
  myPort = new Serial(this, "COM8", 9600); // pornește comunicația serială
  myPort.bufferUntil('.'); // citește datele de la portul serial până la caracterul '.'. Deci de fapt citește asta: unghi, distanță.
  radarBuffer = createGraphics(width, height);
  drawRadar();
}

void draw() {
  fill(98, 245, 31);
  // simularea blur-ului de mișcare și estomparea lentă a liniei în mișcare
  noStroke();
  fill(0, 4);
  rect(0, 0, width, height - height * 0.065);

  fill(98, 245, 31); // culoarea verde
  // apelează funcțiile pentru desenarea radarului
  image(radarBuffer, 0, 0);
  drawLine();
  drawObject();
  drawText();
}

void serialEvent(Serial myPort) { // începe citirea datelor de la portul serial
  // citește datele de la portul serial până la caracterul '.' și le pune în variabila String "data".
  data = myPort.readStringUntil('.');
  data = data.substring(0, data.length() - 1);

  index1 = data.indexOf(","); // găsește caracterul ',' și îl pune în variabila "index1"
  angle = data.substring(0, index1); // citește datele de la poziția "0" până la poziția variabilei index1, aceasta fiind valoarea unghiului pe care placa Arduino l-a trimis către portul serial
  distance = data.substring(index1 + 1, data.length()); // citește datele de la poziția "index1" până la sfârșitul datelor, aceasta fiind valoarea distanței

  // convertește variabilele String în Integer
  iAngle = int(angle);
  iDistance = int(distance);
}

void drawRadar() {
  radarBuffer.beginDraw();
  radarBuffer.translate(width / 2, height - height * 0.074); // mută coordonatele de start la noua locație
  radarBuffer.noFill();
  radarBuffer.strokeWeight(2);
  radarBuffer.stroke(98, 245, 31);
  // desenează liniile arc
  radarBuffer.arc(0, 0, (width - width * 0.0625), (width - width * 0.0625), PI, TWO_PI);
  radarBuffer.arc(0, 0, (width - width * 0.27), (width - width * 0.27), PI, TWO_PI);
  radarBuffer.arc(0, 0, (width - width * 0.479), (width - width * 0.479), PI, TWO_PI);
  radarBuffer.arc(0, 0, (width - width * 0.687), (width - width * 0.687), PI, TWO_PI);
  // desenează liniile unghiului
  radarBuffer.line(-width / 2, 0, width / 2, 0);
  radarBuffer.line(0, 0, (-width / 2) * cos(radians(30)), (-width / 2) * sin(radians(30)));
  radarBuffer.line(0, 0, (-width / 2) * cos(radians(60)), (-width / 2) * sin(radians(60)));
  radarBuffer.line(0, 0, (-width / 2) * cos(radians(90)), (-width / 2) * sin(radians(90)));
  radarBuffer.line(0, 0, (-width / 2) * cos(radians(120)), (-width / 2) * sin(radians(120)));
  radarBuffer.line(0, 0, (-width / 2) * cos(radians(150)), (-width / 2) * sin(radians(150)));
  radarBuffer.line((-width / 2) * cos(radians(30)), 0, width / 2, 0);
  radarBuffer.endDraw();
}

void drawObject() {
  pushMatrix();
  translate(width / 2, height - height * 0.074); // mută coordonatele de start la noua locație
  strokeWeight(9);
  stroke(255, 10, 10); // culoarea roșie
  pixsDistance = iDistance * ((height - height * 0.1666) * 0.025); // convertește distanța de la senzor din cm în pixeli
  // limitează distanța la 40 cm
  if (iDistance < 40) {
    // desenează obiectul în funcție de unghi și distanță
    line(pixsDistance * cos(radians(iAngle)), -pixsDistance * sin(radians(iAngle)), (width - width * 0.505) * cos(radians(iAngle)), -(width - width * 0.505) * sin(radians(iAngle)));
  }
  popMatrix();
}

void drawLine() {
  pushMatrix();
  strokeWeight(9);
  stroke(30, 250, 60);
  translate(width / 2, height - height * 0.074); // mută coordonatele de start la noua locație
  line(0, 0, (height - height * 0.12) * cos(radians(iAngle)), -(height - height * 0.12) * sin(radians(iAngle))); // desenează linia în funcție de unghi
  popMatrix();
}

void drawText() { // desenează textele pe ecran
  pushMatrix();
  if (iDistance > 40) {
    noObject = "În afara razei";
  } else {
    noObject = "În raza de acțiune";
  }
  fill(0, 0, 0);
  noStroke();
  rect(0, height - height * 0.0648, width, height);
  fill(98, 245, 31);
  textSize(25);

  text("10cm", width - width * 0.3854, height - height * 0.0833);
  text("20cm", width - width * 0.281, height - height * 0.0833);
  text("30cm", width - width * 0.177, height - height * 0.0833);
  text("40cm", width - width * 0.0729, height - height * 0.0833);
  textSize(40);
  text("N_Tech ", width - width * 0.875, height - height * 0.0277);
  text("Unghi: " + iAngle + " ", width - width * 0.48, height - height * 0.0277);
  text("Distanță: ", width - width * 0.26, height - height * 0.0277);
  if (iDistance < 40) {
    text("        " + iDistance + " cm", width - width * 0.225, height - height * 0.0277);
  }
  textSize(25);
  fill(98, 245, 60);
  translate((width - width * 0.4994) + width / 2 * cos(radians(30)), (height - height * 0.0907) - width / 2 * sin(radians(30)));
  rotate(-radians(-60));
  text("30", 0, 0);
  resetMatrix();
  translate((width - width * 0.503) + width / 2 * cos(radians(60)), (height - height * 0.0888) - width / 2 * sin(radians(60)));
  rotate(-radians(-30));
  text("60", 0, 0);
  resetMatrix();
  translate((width - width * 0.507) + width / 2 * cos(radians(90)), (height - height * 0.0833) - width / 2 * sin(radians(90)));
  rotate(radians(0));
  text("90", 0, 0);
  resetMatrix();
  translate(width - width * 0.513 + width / 2 * cos(radians(120)), (height - height * 0.07129) - width / 2 * sin(radians(120)));
  rotate(radians(-30));
  text("120", 0, 0);
  resetMatrix();
  translate((width - width * 0.5104) + width / 2 * cos(radians(150)), (height - height * 0.0574) - width / 2 * sin(radians(150)));
  rotate(radians(-60));
  text("150", 0, 0);
  popMatrix();
}
