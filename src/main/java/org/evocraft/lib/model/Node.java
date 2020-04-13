package org.evocraft.lib.model;

import lombok.Data;
import processing.core.PVector;

@Data
public class Node {

    private Cell upLeftCell = null;
    private Cell rightUpCell = null;
    private Cell downRightCell = null;
    private Cell leftDownCell = null;

    protected PVector position = new PVector(0.0f, 0.0f);
    protected PVector velocity = new PVector(0.0f, 0.0f);
    protected PVector acceleration = new PVector(0.0f, 0.0f);

}
