package io;

import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;
import org.junit.Assert;
import org.junit.Test;
import thread.message.GameActionMessage;
import thread.message.PlayerScoreMessage;

public class ClientToServerParserTest {
    @Test
    public void testClientToServerCoordinatesFromOrigin() {
        String serverCoordinates = ClientToServerParser.convertClientCoordinatesToServerCoordinates(new Location(0,0,0));

        Assert.assertEquals("0 0 0", serverCoordinates);
    }

    @Test
    public void testClientToServerCoordinatesFromArbitraryPoint() {
        String serverCoordinates = ClientToServerParser.convertClientCoordinatesToServerCoordinates(new Location(-2,-1,1));

        Assert.assertEquals("-2 1 1", serverCoordinates);
    }

    @Test
    public void testClientToServerCoordinatesFromAnotherArbitrary() {
        String serverCoordinates = ClientToServerParser.convertClientCoordinatesToServerCoordinates(new Location(3,-1,5));

        Assert.assertEquals("3 -4 1", serverCoordinates);
    }

    @Test
    public void testGameActionToServer() {
        GameActionMessage gameActionMessage =
                new GameActionMessage("1", 4, "spag", new Tile(Terrain.GRASSLANDS, Terrain.ROCKY),
                        new Location(1,2,3), TileOrientation.NORTHWEST_WEST, BuildAction.BUILT_TIGER_PLAYGROUND,
                        new Location(-2,1,0), null);

        String serverString = ClientToServerParser.getStringFromGameActionMessage(gameActionMessage);

        Assert.assertEquals("GAME 1 MOVE 4 PLACE ROCK+GRASS AT 1 1 -2 6 BUILD TIGER PLAYGROUND AT -2 3 -1", serverString);
    }

    @Test
    public void testGameActionWithNewTerrainToServerString() {
        GameActionMessage gameActionMessage =
                new GameActionMessage("5", 2, "spagett", new Tile(Terrain.PADDY, Terrain.ROCKY),
                        new Location(2,1,0), TileOrientation.NORTHEAST_NORTHWEST, BuildAction.FOUNDED_SETTLEMENT,
                        new Location(3,4,0), null);

        String serverString = ClientToServerParser.getStringFromGameActionMessage(gameActionMessage);

        Assert.assertEquals("GAME 5 MOVE 2 PLACE ROCK+PADDY AT 2 -1 -1 1 FOUND SETTLEMENT AT 3 1 -4", serverString);
    }

    @Test
    public void testGameActionWithNewTerrainAndExpansionToNewTerrainReturnsServerString() {
        GameActionMessage gameActionMessage =
                new GameActionMessage("5", 2, "spagett", new Tile(Terrain.PADDY, Terrain.ROCKY),
                        new Location(2,1,0), TileOrientation.NORTHEAST_NORTHWEST, BuildAction.EXPANDED_SETTLEMENT,
                        new Location(3,4,0), Terrain.PADDY);

        String serverString = ClientToServerParser.getStringFromGameActionMessage(gameActionMessage);

        Assert.assertEquals("GAME 5 MOVE 2 PLACE ROCK+PADDY AT 2 -1 -1 1 EXPAND SETTLEMENT AT 3 1 -4 PADDY", serverString);


    }

    @Test
    public void testGameActionShangrilaToServerString() {
        GameActionMessage gameActionMessage =
                new GameActionMessage("5", 2, "spagett", new Tile(Terrain.PADDY, Terrain.ROCKY),
                        new Location(2,1,0), TileOrientation.NORTHEAST_NORTHWEST, BuildAction.SHANGRILA,
                        new Location(3,4,0), Terrain.PADDY);

        String serverString = ClientToServerParser.getStringFromGameActionMessage(gameActionMessage);

        Assert.assertEquals("GAME 5 MOVE 2 PLACE ROCK+PADDY AT 2 -1 -1 1 FOUND SHANGRILA AT 3 1 -4", serverString);
    }

    @Test
    public void testPlayerScoreMessageToServerString() {
        PlayerScoreMessage playerScoreMessage = new PlayerScoreMessage("gameOne", "spagett", 100, "playerTwo", 95);

        String serverString = ClientToServerParser.getStringFromPlayerScoreMessage(playerScoreMessage);

        Assert.assertEquals("GAME gameOne OVER PLAYER spagett 100 PLAYER playerTwo 95", serverString);
    }

}
