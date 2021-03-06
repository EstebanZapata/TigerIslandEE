package game.tile;

public class Tile {
    protected Hex[] hexes;
    public static int MAX_HEXES_PER_TILE = 3;

    private static final int VOLCANO_HEX = 0;
    private static final int LEFT_HEX = 1;
    private static final int RIGHT_HEX = 2;

    protected Tile() {}

    public Tile(Terrain terrainOne, Terrain terrainTwo) {
        hexes = new Hex[MAX_HEXES_PER_TILE];
        hexes[VOLCANO_HEX] = new Hex(this, Terrain.VOLCANO);
        hexes[LEFT_HEX] = new Hex(this, terrainOne);
        hexes[RIGHT_HEX] = new Hex(this, terrainTwo);
    }

    public Hex getVolcanoHex() {
        return hexes[VOLCANO_HEX];
    }

    public Hex getLeftHexRelativeToVolcano() {
        return hexes[LEFT_HEX];
    }

    public Terrain getLeftHexTerrain() { return hexes[LEFT_HEX].getTerrain(); }

    public Hex getRightHexRelativeToVolcano() {
        return hexes[RIGHT_HEX];
    }

    public Terrain getRightHexTerrain() { return hexes[RIGHT_HEX].getTerrain(); }

    @Override
    public String toString() {
        return hexes[0].getLocation() + " " + hexes[1].getLocation() + " " + hexes[2].getLocation();
    }
}
