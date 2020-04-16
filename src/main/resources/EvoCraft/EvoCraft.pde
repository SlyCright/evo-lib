import org.evocraft.lib.model.*; //<>//

SpecimenBuilder specimenBuilder=new SpecimenBuilder();
Specimen specimen=specimenBuilder.buildSpecimen();
ArrayList<SpecimenComponent> components=specimen.getSpecimenComponents();

int wdth=999;
int hght=666;

int TICK_MAX=300;
int tick=TICK_MAX;

void settings() {
  size(wdth, hght);
}

void setup() {
  //frameRate();
}

void draw() {    
  background(75);

  for (SpecimenComponent component : components) {
    if (component instanceof Cell) {
      component.act();
      Cell cell=(Cell)component;
      PVector position=cell.getPosition();
      ellipse(position.x, position.y, 2f, 2f);
    }
  }

  for (SpecimenComponent component : components) {
    if (component instanceof Node) {
      component.act();
      Node node=(Node)component;

      for (Node nearbyNode : node.getAdjacentNodes()) {
      line(node.getPosition().x, node.getPosition().y, nearbyNode.getPosition().x, nearbyNode.getPosition().y);
      } 

      PVector position=node.getPosition();
      ellipse(position.x, position.y, 5f, 5f);
    }
  }

  tick--;
  if (tick<0) {
    tick=TICK_MAX; 
    specimen=specimenBuilder.buildSpecimen();
    components=specimen.getSpecimenComponents();
  }
}
