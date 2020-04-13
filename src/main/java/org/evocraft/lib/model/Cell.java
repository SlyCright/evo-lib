package org.evocraft.lib.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import processing.core.PVector;

@Data
public class Cell {

    private Node upLeftNode = null;
    private Node rightUpNode = null;
    private Node downRightNode = null;
    private Node leftDownNode = null;

    private List<Node> nodes = new ArrayList<>(4);
    private PVector position = new PVector(0, 0);

}
