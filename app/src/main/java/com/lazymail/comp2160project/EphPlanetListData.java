package com.lazymail.comp2160project;

public class EphPlanetListData {
    private String planetName;
    private int planetSweNum;

    public EphPlanetListData(String planetName, int planetSweNum) {
        this.planetName = planetName;
        this.planetSweNum = planetSweNum;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public int getPlanetSweNum() {
        return planetSweNum;
    }

    public void setPlanetSweNum(int planetSweNum) {
        this.planetSweNum = planetSweNum;
    }
}
