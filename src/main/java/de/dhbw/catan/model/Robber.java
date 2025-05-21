package de.dhbw.catan.model;

import lombok.Data;


@Data
public class Robber {
    private int position;
    private boolean isActive;

    public Robber() {
        // this.position = ;
        this.isActive = false;
    }

    public void move(int newPosition) {
        this.position = newPosition;
    }

    public void activate() {
        this.isActive = true;
    }


}
