package de.dhbw.catan.model;

import lombok.Data;

@Data
public class Robber {
    private int position;
    private boolean isActive;

    public Robber() {
        this.isActive = false;
        this.position = -1;
    }

    public void move(int newPosition) {
        this.position = newPosition;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
