package game.world;

import game.tile.*;
import game.world.rules.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.orientation.TileOrientation;

public class WorldTest {
    private World world;

    private Tile tileOne;
    private Tile tileTwo;
    private Tile tileThree;
    private Tile tileFour;
    private Tile tileFive;
    private Tile tileSix;
//f
    @Before
    public void setupWorldAndTiles() {
        world = new World();
        tileOne = new Tile(Terrain.GRASSLANDS, Terrain.JUNGLE);
        tileTwo = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        tileThree = new Tile(Terrain.LAKE, Terrain.ROCKY);
        tileFour = new Tile(Terrain.GRASSLANDS, Terrain.ROCKY);
        tileFive = new Tile(Terrain.ROCKY, Terrain.LAKE);
        tileSix = new Tile(Terrain.LAKE, Terrain.JUNGLE);

    }

    private void setupBaseForHigherTiles() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(tileTwo, new Location(1,-1,0), TileOrientation.SOUTHWEST_SOUTHEAST);

    }

    @Test
    public void testWorldContainsSpecialStartingTileUponCreation() throws NoHexAtLocationException {
        Assert.assertTrue(world.getHexByCoordinate(0,0,0).getOwner() instanceof FirstTile);
    }


    @Test(expected = SpecialFirstTileHasAlreadyBeenPlacedExeption.class)
    public void testSpecialFirstTileCannotBePlacedAgain() throws IllegalTilePlacementException {
        world.placeFirstTile();
        world.placeFirstTile();
    }

    @Test
    public void testGetHexByCoordinate() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(1,0,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(2,0,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(2,1,0));
    }

    @Test
    public void testGetHexByLocation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(0,2,0), TileOrientation.NORTHWEST_WEST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByLocation(tileOne.getVolcanoHex().getLocation()));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByLocation(tileOne.getLeftHexRelativeToVolcano().getLocation()));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByLocation(tileOne.getRightHexRelativeToVolcano().getLocation()));
    }

    @Test
    public void testGetHexByNewLocationObject() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(-2,-2,0), TileOrientation.WEST_SOUTHWEST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByLocation(new Location(-2,-2,0)));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByLocation(new Location(-3,-2,0)));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByLocation(new Location(-3,-3,0)));
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testGetEmptyHexLocationByCoordinatesThrowsException() throws IllegalTilePlacementException {
        world.getHexByCoordinate(1,-1,0);
    }

    @Test(expected = NoHexAtLocationException.class)
    public void testGetEmptyHexLocationByLocationThrowsException() throws IllegalTilePlacementException {
        world.getHexByLocation(new Location(1,-2,0));
    }

    @Test
    public void testPlaceTileWithSouthwestSoutheastOrientation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(2,1,0), TileOrientation.SOUTHWEST_SOUTHEAST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(2,1,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(1,0,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(2,0,0));
    }

    @Test
    public void testPlaceTileWithWestSouthwestOrientation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(-2,-2,0), TileOrientation.WEST_SOUTHWEST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(-2,-2,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(-3,-2,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(-3,-3,0));
    }

    @Test
    public void testPlaceTileWithNorthwestWestOrientation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(1,2,0), TileOrientation.NORTHWEST_WEST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(1,2,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(1,3,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(0,2,0));
    }

    @Test
    public void testPlaceTileWithNortheastNorthwestOrientation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(2,0,0), TileOrientation.NORTHEAST_NORTHWEST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(2,0,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(3,1,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(2,1,0));
    }

    @Test
    public void testPlaceTileWithEastNortheastOrientation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(2,2,0), TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(2,2,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(3,2,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(3,3,0));
    }

    @Test
    public void testPlaceTileWithSoutheastEastOrientation() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(-2,1,0), TileOrientation.SOUTHEAST_EAST);

        Assert.assertEquals(tileOne.getVolcanoHex(), world.getHexByCoordinate(-2,1,0));
        Assert.assertEquals(tileOne.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(-2,0,0));
        Assert.assertEquals(tileOne.getRightHexRelativeToVolcano(), world.getHexByCoordinate(-1,1,0));
    }

    @Test
    public void testSuccessfullyPlaceTileOnAHigherLayer() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        world.insertTileIntoTileManager(tileThree, new Location(0,0,1), TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(tileThree.getVolcanoHex(), world.getHexByCoordinate(0,0,1));
        Assert.assertEquals(tileThree.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(1,0,1));
        Assert.assertEquals(tileThree.getRightHexRelativeToVolcano(), world.getHexByCoordinate(1,1,1));
    }

    @Test
    public void testSuccessfullyPlaceTileOnAnEvenHigherLayer() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        world.insertTileIntoTileManager(tileThree, new Location(2,-1,0), TileOrientation.EAST_NORTHEAST);

        world.insertTileIntoTileManager(tileFour, new Location(0,0,1), TileOrientation.SOUTHEAST_EAST);
        world.insertTileIntoTileManager(tileFive, new Location(1,-1,1), TileOrientation.EAST_NORTHEAST);

        world.insertTileIntoTileManager(tileSix, new Location(1,-1,2), TileOrientation.NORTHEAST_NORTHWEST);

        Assert.assertEquals(tileSix.getVolcanoHex(), world.getHexByCoordinate(1,-1,2));
        Assert.assertEquals(tileSix.getLeftHexRelativeToVolcano(), world.getHexByCoordinate(2,0,2));
        Assert.assertEquals(tileSix.getRightHexRelativeToVolcano(), world.getHexByCoordinate(1,0,2));

    }


    @Test
    public void testListOfAllHexesGainsHexesWhenInsertingFirstTile() throws IllegalTilePlacementException {
        Assert.assertEquals(5, world.getAllHexesInWorld().size());
    }

    @Test
    public void testListOfAllHexesGainsHexesWhenInsertingAnotherTile() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(1,2,0), TileOrientation.NORTHEAST_NORTHWEST);

        Assert.assertTrue(world.getAllHexesInWorld().contains(tileOne.getVolcanoHex()));
        Assert.assertTrue(world.getAllHexesInWorld().contains(tileOne.getLeftHexRelativeToVolcano()));
        Assert.assertTrue(world.getAllHexesInWorld().contains(tileOne.getRightHexRelativeToVolcano()));
    }




    @Test(expected = TileNotAdjacentToAnotherException.class)
    public void testFailToPlaceTileOnBaseLevelDueToNotAdjacentToExistingTile() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileOne, new Location(2,0,0) , TileOrientation.EAST_NORTHEAST);

    }

    @Test(expected = AirBelowTileException.class)
    public void testFailToPlaceTileOnHigherLevelDueToAirBelowTile() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        world.insertTileIntoTileManager(tileThree, new Location(1,-1,1), TileOrientation.SOUTHEAST_EAST);
    }

    @Test(expected = HexAlreadyAtLocationException.class)
    public void testFailToPlaceTileOnHigherLevelDueToOverlappingExistingTile() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        world.insertTileIntoTileManager(tileThree, new Location(1,0,1), TileOrientation.WEST_SOUTHWEST);

        world.insertTileIntoTileManager(tileFour, new Location(1,-1,1), TileOrientation.WEST_SOUTHWEST);
    }

    @Test(expected = TileCompletelyOverlapsAnotherException.class)
    public void testFailToPlaceTileOnHigherLevelDueToCompleteCoverageOfTileBelow() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        world.insertTileIntoTileManager(tileThree, new Location(1,0,1), TileOrientation.EAST_NORTHEAST);
    }

    @Test(expected = TopVolcanoDoesNotCoverBottomVolcanoException.class)
    public void testFailToPlaceTileOnHigherLevelDueToNotCoveringLowerVolcano() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        world.insertTileIntoTileManager(tileThree, new Location(2,1,1), TileOrientation.WEST_SOUTHWEST);
    }

    @Test(expected = AirBelowTileException.class)
    public void testFailToPlaceTileOverInlet() throws IllegalTilePlacementException {
        world.insertTileIntoTileManager(tileTwo, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(tileThree, new Location(0,-2,0), TileOrientation.SOUTHEAST_EAST);

        world.insertTileIntoTileManager(tileFour, new Location(1,0,1), TileOrientation.SOUTHWEST_SOUTHEAST);
    }

    @Test
    public void testGetHexRegardlessOfHeightReturnsBaseHexIfNoneAbove() throws IllegalTilePlacementException {
        Hex jungleHex = ((FirstTile) world.getHexByCoordinate(0,0,0).getOwner()).getJungleHex();

        Assert.assertEquals(jungleHex, world.getHexRegardlessOfHeight(0,1));
    }

    @Test
    public void testGetHexRegardlessOfHeightReturnsHighestHex() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();
        world.insertTileIntoTileManager(tileFour, new Location(0,0,1), TileOrientation.SOUTHEAST_EAST);

        Hex upperTileLeftHex = tileFour.getLeftHexRelativeToVolcano();

        Assert.assertEquals(upperTileLeftHex, world.getHexRegardlessOfHeight(0,-1));


    }

    @Test
    public void testGetValidTileOrientationToPlaceTileOnTopOfVolcanoAndAdjacentInSameHex() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        Location locationOfVolcanoToCover = new Location(1,0,0);
        Location locationAdjacentToVolcanoAndOnSameHex = new Location(2,0,0);

        Assert.assertEquals(TileOrientation.SOUTHEAST_EAST, world.calculateTileOrientationToCoverVolcanoLocationAndAdjacentLocation(locationOfVolcanoToCover, locationAdjacentToVolcanoAndOnSameHex));
    }

    @Test
    public void testGetValidTileOrientationToPlaceTileOnTopOfVolcanoAndAdjacentInDifferentHex() throws IllegalTilePlacementException {
        setupBaseForHigherTiles();

        Location locationOfVolcanoToCover = new Location(1,0,0);
        Location locationAdjacentToVolcanoAndOnDifferentHex = new Location(1,-1,0);

        TileOrientation possibleOrientation = world.calculateTileOrientationToCoverVolcanoLocationAndAdjacentLocation(locationOfVolcanoToCover, locationAdjacentToVolcanoAndOnDifferentHex);

        boolean wasExpectedOrientation = false;

        if (possibleOrientation == TileOrientation.SOUTHEAST_EAST || possibleOrientation == TileOrientation.SOUTHWEST_SOUTHEAST) {
            wasExpectedOrientation = true;
        }

        Assert.assertTrue(wasExpectedOrientation);

    }

    @Test(expected = NoValidTileOrientationException.class)
    public void testGetValidTileOrientationThrowsExceptionIfNoValidOrientation() throws IllegalTilePlacementException{
        setupBaseForHigherTiles();

        Location locationOfVolcanoToCover = new Location(1,-1,0);
        Location locationOfAdjacent = new Location(1,-2,0);

        world.calculateTileOrientationToCoverVolcanoLocationAndAdjacentLocation(locationOfVolcanoToCover, locationOfAdjacent);
    }

    @Test
    public void testPlacingNewPaddyTerrain() throws IllegalTilePlacementException {
        Tile paddyTile = new Tile(Terrain.PADDY, Terrain.ROCKY);

        Location locationOfVolcano = new Location(1,2,0);

        world.insertTileIntoTileManager(paddyTile, locationOfVolcano, TileOrientation.NORTHEAST_NORTHWEST);

        Assert.assertEquals(Terrain.PADDY, world.getHexByCoordinate(2,3,0).getTerrain());
        Assert.assertEquals(Terrain.ROCKY, world.getHexByCoordinate(1,3,0).getTerrain());
    }



}