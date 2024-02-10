package org.example.stage1;

public class Player {
    private String name;
    private float Xpos;
    private float Ypos;

    public Player() {
        this.name = "Player";
    }

    public String getName() {
        return this.name;
    }

    public float getXpos() {
        return this.Xpos;
    }

    public float getYpos() {
        return this.Ypos;
    }

    public void setName(String _name) {
        this.name = _name;
    }
}
