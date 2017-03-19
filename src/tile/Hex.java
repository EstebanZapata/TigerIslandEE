package tile;

public class Hex {
    private Terrain terrain;
    private Tile owner;
    private Location locationOfHex;

    public Hex(Tile owner, Terrain terrain) {
        this.owner = owner;
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public Tile getOwner() {
        return this.owner;
    }

    public Location getLocationOfHex() {
        return locationOfHex;
    }

    public void setLocationOfHex(Location locationOfHex) {
        this.locationOfHex = locationOfHex;
    }
}
