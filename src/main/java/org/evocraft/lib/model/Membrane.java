package org.evocraft.lib.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import processing.core.PVector;

@Setter
@Getter
public class Membrane extends SpecimenComponent {

    final static private float STIFFNESS = 0.025f; //todo backlog: should depend of DNA
    final static public float LENGTH = 50f; //todo backlog: should depend of DNA

    private Node initialNode = new Node();
    private Node terminalNode = new Node();

    private PVector membrane = new PVector(0f, 0f);
    private Map<Integer, Float> appliedLengths = new HashMap<>();

    public void act() {
        this.membrane = calculateMembraneVector();
        float shouldBeLength = calculateShouldBeLength();
        float currentLength = this.membrane.mag();
        float force = - STIFFNESS * (currentLength - shouldBeLength);
        applyForceToAdjacentNode(-force, initialNode);
        applyForceToAdjacentNode(force, terminalNode);
    }

    protected float calculateShouldBeLength() {
        float shouldBeLength = 0f;

        switch (appliedLengths.size()) {
            case 0:
                return LENGTH;
            case 1:
                shouldBeLength += LENGTH;
                shouldBeLength += appliedLengths.values().stream()
                        .findFirst()
                        .get();
                shouldBeLength /= 2;
                return shouldBeLength;
            default:
                for (Float length : appliedLengths.values()) {
                    shouldBeLength += length;
                }
                shouldBeLength /= appliedLengths.size();
                return shouldBeLength;
        }
    }

    protected PVector calculateMembraneVector() {
        PVector initialPosition = initialNode.getPosition().copy();
        PVector terminalPosition = terminalNode.getPosition().copy();
        return PVector.sub(terminalPosition, initialPosition);
    }

    protected void applyForceToAdjacentNode(float forceValue, Node node) {
        PVector membraneCopy = membrane.copy();
        PVector forceDirection = membraneCopy.normalize();
        PVector forceVector = forceDirection.mult(forceValue);
        node.applyForce(forceVector);
    }

    public void applyLength(int cellHash, float length) {
        this.appliedLengths.put(cellHash, length);
    }

    @Override
    public SpecimenComponent copy() { //todo refactor: will never used. See todo in Crossoverer class
        return null;
    }
}
