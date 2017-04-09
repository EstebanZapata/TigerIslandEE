package game;
import game.Game;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Tile;
import game.world.World;
import thread.message.GameActionMessage;

import static game.settlements.BuildAction.FOUNDED_SETTLEMENT;
import static game.tile.Terrain.GRASSLANDS;
import static game.tile.orientation.TileOrientation.NORTHWEST_WEST;

public class Ai {
    private World world;
    public Ai(World world){
        this.world = world;
    }
        public GameActionMessage chooseMove(String gameID, int moveNumber, String playerID, Tile tileToBePlaced) {
            Hex hexToBePlacedNextTo = world.tileManager.getLeftMostHex();

            int newTileXCoordinate = hexToBePlacedNextTo.getLocation().getxCoordinate() - 1;
            int newTileYCoordinate = hexToBePlacedNextTo.getLocation().getyCoordinate();
            Location locationOfNewTile = new Location(newTileXCoordinate, newTileYCoordinate, 0);

            int buildXCoordinate = newTileXCoordinate - 1;
            int buildYCoordinate = newTileYCoordinate;
            Location locationOnWhichToBuild = new Location(buildXCoordinate, buildYCoordinate, 0);

            return new GameActionMessage(gameID, moveNumber, playerID, tileToBePlaced, locationOfNewTile, NORTHWEST_WEST, FOUNDED_SETTLEMENT, locationOnWhichToBuild, GRASSLANDS);

        }

    }
