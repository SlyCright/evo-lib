import org.evocraft.lib.model.*; //<>//

World world=new World();
ArrayList<Specimen> species=world.getSpecies();
ArrayList<SpecimenComponent> components;

int wdth=999;
int hght=666;

void settings() {
  size(wdth, hght);
}

void setup() {
  // frameRate(10);
  thread("simulation");
  int sleepTime=2;
  Specimen firstBest;

  do {
    firstBest=world.getBestSpecimen();
    sleepTime*=2; 
    println("wait for first Best:"+sleepTime);

    try {
      Thread.currentThread().sleep(sleepTime);
    }
    catch(InterruptedException e) {
    }
  } while (firstBest==null);
}

int tickCounter=0;

void mousePressed() {
  tickCounter=1001;
}

void draw() {    
  background(75);

  tickCounter++;
  if (tickCounter>1000) {
    tickCounter=0;
    species.clear();
    species.add(world.getBestSpecimen());
    new World().moveSpeciesToInitialPosition(species, new PVector(0f, 0f));
  }

  for (Specimen specimen : species) {

    specimen.act();

    components=specimen.getComponents();

    for (SpecimenComponent component : components) {

      if (component instanceof Cell) {
        Cell cell=(Cell)component;
        PVector position=cell.getPosition();

        if (cell instanceof Oscillator) {
          Oscillator oscillator=(Oscillator)cell;
          boolean isActive=oscillator.isActive();
          if (isActive) {
            stroke(0, 255, 0);
          } else {
            stroke(0, 125, 0);
          }
          strokeWeight(10f);
        } 

        if (cell instanceof Neuron) {
          Neuron neuron=(Neuron)cell;
          boolean isActive=neuron.isActive();
          if (isActive) {
            stroke(255, 255, 0);
          } else {
            stroke(125, 125, 0);
          }
          strokeWeight(10f);
        } 

        if (cell instanceof Muscle) {
          Muscle muscle=(Muscle)cell;
          boolean isActive=muscle.isActive();
          if (isActive) {
            stroke(255, 0, 0);
          } else {
            stroke(125, 0, 0);
          }
          strokeWeight(muscle.getCurrentSize()); //muscle.getSize()
        } 

        ellipse(position.x, position.y, 2f, 2f);
      }
    }

    for (SpecimenComponent component : components) {

      if (component instanceof Node) {
        Node node=(Node)component;
        PVector position=node.getPosition();
        stroke(0);
        strokeWeight(1f);
        ellipse(position.x, position.y, 5f, 5f);
      }

      if (component instanceof Membrane) {
        Membrane membrane=(Membrane)component;
        Node node=membrane.getInitialNode();
        Node nearbyNode=membrane.getTerminalNode();
        stroke(0);
        strokeWeight(1f);
        line(node.getPosition().x, node.getPosition().y, nearbyNode.getPosition().x, nearbyNode.getPosition().y);
      }
    }

    for (SpecimenComponent component : components) {
      if (component instanceof Connection) {
        Connection connection=(Connection)component;
        PVector initialPosition=connection.getInitialPosition().copy();
        PVector terminalPosition=connection.getTerminalPosition().copy();
        float weight=connection.getWeight();

        if (connection.isActive()) {
          stroke(180, 180, 180);
        } else {
          stroke(180, 180, 180, 35);
        }

        strokeWeight(5f*weight);
        line(initialPosition.x, initialPosition.y, terminalPosition.x, terminalPosition.y);
      }
    }
  }
}

void simulation() {
  while (true) {
    world.act();
  }
}
