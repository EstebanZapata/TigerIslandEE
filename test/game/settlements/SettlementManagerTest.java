package game.settlements;

import game.Settings;
import game.settlements.exceptions.*;
import game.world.rules.exceptions.IllegalTilePlacementException;
import game.world.rules.exceptions.NoHexAtLocationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.*;
import game.tile.orientation.*;
import game.world.*;

import java.util.ArrayList;

public class SettlementManagerTest {
    private World world;
    private SettlementManager settlementManager;

    @Before
    public void setUp() throws IllegalTilePlacementException {
        this.world = new World();
        this.settlementManager = new SettlementManager(this.world);
    }

    @Test
    public void testFoundSettlement() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(this.settlementManager.settlements.size(), 0);
        this.settlementManager.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        Assert.assertEquals(this.settlementManager.settlements.size(), 1);
    }

    @Test (expected = SettlementAlreadyExistsOnHexException.class)
    public void testFoundSettlementThrowsSettlementAlreadyExistsOnHexException() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        this.settlementManager.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        this.settlementManager.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
    }

    @Test
    public void testGetSettlementFromHex() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoSettlementOnHexException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        Settlement newSettlement = this.settlementManager.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        Settlement returnedSettlement = this.settlementManager.getSettlementFromHex(foundingTile.getLeftHexRelativeToVolcano());
        Assert.assertEquals(newSettlement, returnedSettlement);
    }

    @Test
    public void testBuildTotoroSanctuary() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);

        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);
        this.settlementManager.expandSettlement(newSettlement, Terrain.GRASSLANDS);

        FirstTile sanctuaryTile = (FirstTile) this.world.getHexByCoordinate(0,0,0).getOwner();
        Hex sanctuaryHex = sanctuaryTile.getLakeHex();
        this.settlementManager.buildTotoroSanctuary(sanctuaryHex);
    }

    @Test (expected = BuildConditionsNotMetException.class)
    public void testBuildTotoroSanctuaryThrowsExceptionWhenTryingToBuildOnExistingTotoroSanctuary() throws
            IllegalTilePlacementException,
            NoHexesToExpandToException,
            BuildConditionsNotMetException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);

        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);
        this.settlementManager.expandSettlement(newSettlement, Terrain.GRASSLANDS);

        FirstTile sanctuaryTile = (FirstTile) this.world.getHexByCoordinate(0,0,0).getOwner();
        Hex sanctuaryHex = sanctuaryTile.getLakeHex();
        this.settlementManager.buildTotoroSanctuary(sanctuaryHex);
        this.settlementManager.buildTotoroSanctuary(sanctuaryHex);
    }

    @Test (expected = BuildConditionsNotMetException.class)
    public void testBuildTotoroSanctuaryThrowsExceptionWhenSizeIsntEnough() throws
            IllegalTilePlacementException,
            NoHexesToExpandToException,
            BuildConditionsNotMetException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        Settlement newSettlement = this.settlementManager.foundSettlement(foundingTile.getRightHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);

        FirstTile sanctuaryTile = (FirstTile) this.world.getHexByCoordinate(0,0,0).getOwner();
        Hex sanctuaryHex = sanctuaryTile.getLakeHex();
        this.settlementManager.buildTotoroSanctuary(sanctuaryHex);
    }

    @Test
    public void testBuildTigerPlayground() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        // 1st level
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);

        // 2nd level
        Location volcanoLocation6 = new Location(0, 2, 1);
        Location volcanoLocation7 = new Location(0, 0, 1);
        Location volcanoLocation8 = new Location(2, 1, 1);

        // 3rd level
        Location volcanoLocation9 = new Location(0, 0, 2);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);

        this.world.insertTileIntoTileManager(expansionTile6, volcanoLocation6, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(expansionTile7, volcanoLocation7, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);

        this.world.insertTileIntoTileManager(expansionTile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);

        this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
    }

    @Test (expected = BuildConditionsNotMetException.class)
    public void testBuildTigerPlaygroundThrowsExceptionWhenTryingToBuildOnExistingTigerPlayground() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        // 1st level
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);

        // 2nd level
        Location volcanoLocation6 = new Location(0, 2, 1);
        Location volcanoLocation7 = new Location(0, 0, 1);
        Location volcanoLocation8 = new Location(2, 1, 1);

        // 3rd level
        Location volcanoLocation9 = new Location(0, 0, 2);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);

        this.world.insertTileIntoTileManager(expansionTile6, volcanoLocation6, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(expansionTile7, volcanoLocation7, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);

        this.world.insertTileIntoTileManager(expansionTile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);

        this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
    }

    @Test (expected = BuildConditionsNotMetException.class)
    public void testBuildTigerPlaygroundThrowsNoSettlementOnAdjacentHexesException() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        // 1st level
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);

        // 2nd level
        Location volcanoLocation6 = new Location(0, 2, 1);
        Location volcanoLocation7 = new Location(0, 0, 1);
        Location volcanoLocation8 = new Location(2, 1, 1);

        // 3rd level
        Location volcanoLocation9 = new Location(0, 0, 2);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);

        this.world.insertTileIntoTileManager(expansionTile6, volcanoLocation6, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(expansionTile7, volcanoLocation7, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);

        this.world.insertTileIntoTileManager(expansionTile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);

        this.settlementManager.foundSettlement(expansionTile1.getVolcanoHex());
        this.settlementManager.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
    }

    @Test
    public void testExpandSettlement() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 3, 0);
        Location volcanoLocation4 = new Location(-2, 1, 0);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);

        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile2.getLeftHexRelativeToVolcano());
        Assert.assertEquals(newSettlement.getSettlementSize(), 1);
        newSettlement.addHexToSettlement(expansionTile3.getLeftHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);

        ArrayList<Hex> test = newSettlement.getHexesFromSettlement();
        for (Hex testing : test ) {
            System.out.println(testing.getLocation());
        }

        Assert.assertEquals(newSettlement.getSettlementSize(), 5);
    }

    @Test
    public void testAdvanceExpansion() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);

        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);

        Hex jungleHex1 = world.getHexByCoordinate(-1,0,0);
        Hex jungleHex2 = world.getHexByCoordinate(0,1,0);
        Hex jungleHex3 = world.getHexByCoordinate(1,2,0);

        Settlement jungleHex1SettlementReturned = jungleHex1.getSettlement();
        Settlement jungleHex2SettlementReturned = jungleHex2.getSettlement();
        Settlement jungleHex3SettlementReturned = jungleHex3.getSettlement();

        Assert.assertEquals(newSettlement, jungleHex1SettlementReturned);
        Assert.assertEquals(newSettlement, jungleHex2SettlementReturned);
        Assert.assertEquals(newSettlement, jungleHex3SettlementReturned);
    }

    @Test
    public void testMultipleLevelExpansion() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 3, 0);
        Location volcanoLocation4 = new Location(-2, 1, 0);
        Location volcanoLocation5 = new Location(-2, 1, 1);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(expansionTile5, volcanoLocation5, TileOrientation.EAST_NORTHEAST);

        Settlement newSettlement = this.settlementManager.foundSettlement(expansionTile3.getRightHexRelativeToVolcano());
        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);

        ArrayList<Hex> test = newSettlement.getHexesFromSettlement();
        for (Hex testing : test ) {
            System.out.println(testing.getLocation());
        }

        Assert.assertEquals(newSettlement.getSettlementSize(), 6);
    }

    @Test
    public void testExpansionOntoMultipleTerrains() throws
            IllegalTilePlacementException,
            SettlementAlreadyExistsOnHexException,
            SettlementCannotBeBuiltOnVolcanoException,
            NoHexesToExpandToException
    {
        Tile tile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile5 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile6 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile7 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile8 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile9 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile10 = new Tile(Terrain.ROCKY, Terrain.ROCKY);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(0, 2, 1);
        Location volcanoLocation8 = new Location(0, 0, 1);
        Location volcanoLocation9 = new Location(0, 0, 2);
        Location volcanoLocation10 = new Location(1, -3, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.NORTHWEST_WEST);

        Settlement newSettlement = this.settlementManager.foundSettlement(tile1.getRightHexRelativeToVolcano());
        int settlementSize = 1;
        Assert.assertEquals(settlementSize, newSettlement.getSettlementSize());

        this.settlementManager.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        settlementSize += 4;
        Assert.assertEquals(settlementSize, newSettlement.getSettlementSize());

        this.settlementManager.expandSettlement(newSettlement, Terrain.JUNGLE);
        settlementSize += 5;
        Assert.assertEquals(settlementSize, newSettlement.getSettlementSize());

        this.settlementManager.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        settlementSize += 2;
        Assert.assertEquals(settlementSize, newSettlement.getSettlementSize());

        this.settlementManager.expandSettlement(newSettlement, Terrain.ROCKY);
        settlementSize += 3;
        Assert.assertEquals(settlementSize, newSettlement.getSettlementSize());
    }

    @Test
    public void testMergingFoundingSettlements() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile3 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile4 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        Location volcanoLocation3 = new Location(0, 3, 0);
        Location volcanoLocation4 = new Location(-2, 1, 0);

        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(expansionTile3, volcanoLocation3, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(expansionTile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);

        Settlement newSettlement2 = this.settlementManager.foundSettlement(expansionTile2.getLeftHexRelativeToVolcano());
        Settlement newSettlement3 = this.settlementManager.foundSettlement(expansionTile3.getLeftHexRelativeToVolcano());
        Settlement newSettlement4 = this.settlementManager.foundSettlement(expansionTile4.getLeftHexRelativeToVolcano());

        Assert.assertEquals(newSettlement2, newSettlement3);
        Assert.assertEquals(newSettlement2, newSettlement4);
        Assert.assertEquals(newSettlement3, newSettlement4);
    }

    @Test
    public void ableToBuildTotoroOnPaddyTerrain() throws Exception {
        Tile paddyTile = new Tile(Terrain.PADDY, Terrain.ROCKY);

        world.insertTileIntoTileManager(paddyTile, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);

        Hex paddyHex = paddyTile.getLeftHexRelativeToVolcano();
        Assert.assertEquals(Terrain.PADDY, paddyHex.getTerrain());

        boolean checkSanctuaryConditionsDoesNotThrowException = false;
        paddyHex.checkSanctuaryConditions();
        checkSanctuaryConditionsDoesNotThrowException = true;

        Assert.assertTrue(checkSanctuaryConditionsDoesNotThrowException);

    }

    @Test
    public void ableToBuildTigerOnPaddyTerrain() throws Exception {
        Tile paddyTile = new Tile(Terrain.PADDY, Terrain.ROCKY);

        world.insertTileIntoTileManager(paddyTile, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);

        Hex paddyHex = paddyTile.getLeftHexRelativeToVolcano();
        paddyHex.setLocation(new Location(0,0,2));
        Assert.assertEquals(Terrain.PADDY, paddyHex.getTerrain());

        boolean checkSanctuaryConditionsDoesNotThrowException = false;
        paddyHex.checkPlaygroundConditions();
        checkSanctuaryConditionsDoesNotThrowException = true;

        Assert.assertTrue(checkSanctuaryConditionsDoesNotThrowException);

    }

}