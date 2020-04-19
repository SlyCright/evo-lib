import org.evocraft.lib.model.*; //<>//

SpecimenBuilder specimenBuilder=new SpecimenBuilder();
Specimen specimen=specimenBuilder.buildSpecimen();
ArrayList<SpecimenComponent> components=specimen.getSpecimenComponents();

int wdth=999;
int hght=666;

int TICK_MAX=1600;
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
    component.act();
  }

  for (SpecimenComponent component : components) {
    if (component instanceof Cell) {
      Cell cell=(Cell)component;
      PVector position=cell.getPosition();

      strokeWeight(3f);
      if (cell instanceof Muscle) {
        Muscle muscle=(Muscle)cell;
        boolean isActive=muscle.isActive();
        if (isActive) {
          stroke(255, 0, 0);
        } else {
          stroke(125, 0, 0);
        }
        strokeWeight(3f); //muscle.getSize()
      } else {
        stroke(0);
      }

      ellipse(position.x, position.y, 2f, 2f);
    }
  }

  for (SpecimenComponent component : components) {
    if (component instanceof Node) {
      Node node=(Node)component;
      PVector position=node.getPosition();

      for (Node nearbyNode : node.getAdjacentNodes()) {

        stroke(0);
        strokeWeight(1f);
        line(node.getPosition().x, node.getPosition().y, nearbyNode.getPosition().x, nearbyNode.getPosition().y);
      } 

      stroke(0);
      strokeWeight(1f);
      ellipse(position.x, position.y, 5f, 5f);
    }
  }

  for (SpecimenComponent component : components) {
    if (component instanceof Connection) {
      Connection connection=(Connection)component;
      PVector initialPosition=connection.getInitialPosition().copy();
      PVector terminalPosition=connection.getTerminalPosition().copy();
      float weight=connection.getWeight();

      stroke(180, 180, 180, 35);
      strokeWeight(5f*weight);
      line(initialPosition.x, initialPosition.y, terminalPosition.x, terminalPosition.y);
    }
  }

  tick--;
  if (tick<0) {
    tick=TICK_MAX; 
    specimen=specimenBuilder.buildSpecimen();
    components=specimen.getSpecimenComponents();
  }
}
