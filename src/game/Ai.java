/**
 * Created by thomasbaldwin on 4/11/17.
 */

package game;

import game.player.Player;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Tile;
import game.world.World;
import game.world.rules.exceptions.IllegalTilePlacementException;
import thread.message.GameActionMessage;

import static game.settlements.BuildAction.FOUNDED_SETTLEMENT;
import static game.tile.Terrain.GRASSLANDS;
import static game.tile.orientation.TileOrientation.NORTHWEST_WEST;

public class Ai {
    public World world;
    private Player theAI;
    public Ai(World world, Player theAI){
        this.world = world;
        this.theAI = theAI;
    }

    public GameActionMessage chooseMove(String gameID, int moveNumber, String playerID, Tile tileToBePlaced) throws IllegalTilePlacementException {
        Hex hexToBePlacedNextTo = world.getLeftMostHex();
        int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 1;
        int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate();
        Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

        int buildXCoordinate = newTileXCoordinate - 1;
        int buildYCoordinate = newTileYCoordinate;
        Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);
        world.insertTileIntoTileManager(tileToBePlaced,locationOfNewTile,NORTHWEST_WEST);
        try {
            theAI.foundSettlement(world.getHexByLocation(locationOnWhichToBuild));
        }
        catch (Exception e) {

        }
        return new GameActionMessage(gameID, moveNumber, playerID, tileToBePlaced, locationOfNewTile, NORTHWEST_WEST, FOUNDED_SETTLEMENT, locationOnWhichToBuild, GRASSLANDS);

    }
}
