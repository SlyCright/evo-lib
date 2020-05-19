import org.evocraft.lib.model.*; //<>//

World world=new World();
ArrayList<Specimen> species=new ArrayList<Specimen>();
ArrayList<SpecimenComponent> components;

int wdth=999;
int hght=666;

void settings() {
  size(wdth, hght);
}

void setup() {
  // frameRate(10);
  HashMap<Integer, Cell> cells=new HashMap<Integer, Cell>();

  Cell muscle=new Muscle(75, false);
  muscle.setTileIndex(new TileIndex(0, -6));
  cells.put(muscle.hashCode(), muscle);

  Cell oscillatorOne=new Oscillator(35, 0f, false);
  oscillatorOne.setTileIndex(new TileIndex(0, -8));
  cells.put(oscillatorOne.hashCode(), oscillatorOne);

  Cell oscillatorTwo=new Oscillator(35, 1f, false);
  oscillatorTwo.setTileIndex(new TileIndex(0, -4));
  cells.put(oscillatorTwo.hashCode(), oscillatorTwo);

  Cell fixerOne=new Fixer(false);
  fixerOne.setTileIndex(new TileIndex(-2, -6));
  cells.put(fixerOne.hashCode(), fixerOne);

  Cell fixerTwo=new Fixer(false);
  fixerTwo.setTileIndex(new TileIndex(2, -6));
  cells.put(fixerTwo.hashCode(), fixerTwo);

  HashMap<Integer, Connection> connections=new HashMap<Integer, Connection>();

  Connection connectionOne=new Connection(1f, false);
  connectionOne.setInputTileIndex(new TileIndex(0, -8));
  connectionOne.setOutputTileIndex(new TileIndex(0, -6));
  connections.put(connectionOne.hashCode(), connectionOne);

  Connection connectionTwo=new Connection(1f, false);
  connectionTwo.setInputTileIndex(new TileIndex(0, -8));
  connectionTwo.setOutputTileIndex(new TileIndex(-2, -6));
  connections.put(connectionTwo.hashCode(), connectionTwo);
  
  Connection connectionThree=new Connection(1f, false);
  connectionThree.setInputTileIndex(new TileIndex(0, -4));
  connectionThree.setOutputTileIndex(new TileIndex(2, -6));
  connections.put(connectionThree.hashCode(), connectionThree);

  Specimen specimen=SpecimenBuilder.createSpecimenOfCellsAndConnections(cells, connections);

  species.add(specimen);

  world.moveSpeciesToInitialPosition(species, new PVector(width/2f, height/2f));
}

void draw() {    
  background(75);

  for (Specimen specimen : species) {

    specimen.act();
    Environment.interactWith(species);

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
        
        if (cell instanceof Fixer) {
          Fixer fixer=(Fixer)cell;
          boolean isActive=fixer.isActive();
          if (isActive) {
            stroke(125, 125, 125);
          } else {
            stroke(25, 25, 25);
          }
          strokeWeight(10f); //muscle.getSize()
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
