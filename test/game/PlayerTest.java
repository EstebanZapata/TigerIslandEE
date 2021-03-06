package game;

import game.player.exceptions.CannotBuildShamanOnHigherLevel;
import game.player.exceptions.NotEnoughPiecesException;
import game.player.exceptions.OutOfShamansException;
import game.settlements.Settlement;
import game.settlements.exceptions.BuildConditionsNotMetException;
import game.settlements.exceptions.NoHexesToExpandToException;
import game.settlements.exceptions.SettlementAlreadyExistsOnHexException;
import game.tile.*;
import game.tile.orientation.TileOrientation;
import game.world.*;
import game.player.Player;
import game.world.rules.exceptions.IllegalTilePlacementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private World world;
    private Player player;

    @Before
    public void setupPlayer() {
        this.world = new World();
        this.player = new Player(this.world);


    }

    @Test
    public void testPreConditions() {
        Assert.assertEquals(Settings.STARTING_SCORE_COUNT, player.getScore());
        Assert.assertEquals(Settings.STARTING_VILLAGER_COUNT, player.getVillagerCount());
        Assert.assertEquals(Settings.STARTING_TOTORO_COUNT, player.getTotoroCount());
        Assert.assertEquals(Settings.STARTING_TIGER_COUNT, player.getTigerCount());
    }

    @Test
    public void testFoundSettlement() throws
            IllegalTilePlacementException,
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        Assert.assertEquals(Settings.STARTING_VILLAGER_COUNT, player.getVillagerCount());
        this.player.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        int numberOfVillagersUsed = 1;
        int updatedVillagerCount = Settings.STARTING_VILLAGER_COUNT - numberOfVillagersUsed;
        Assert.assertEquals(updatedVillagerCount, player.getVillagerCount());
    }

    @Test (expected = SettlementAlreadyExistsOnHexException.class)
    public void testFoundSettlementThrowsSettlementAlreadyExistsOnHexException() throws
            IllegalTilePlacementException,
            NotEnoughPiecesException,
            BuildConditionsNotMetException
    {
        Tile foundingTile = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location foundingLocation = new Location(-2, 0, 0);
        this.world.insertTileIntoTileManager(foundingTile, foundingLocation, TileOrientation.EAST_NORTHEAST);

        this.player.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
        this.player.foundSettlement(foundingTile.getLeftHexRelativeToVolcano());
    }

    public void testFoundSettlementCanUseAllPlayers() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException,
            NotEnoughPiecesException
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
        Tile tile10 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(2, -1, 0);
        Location volcanoLocation8 = new Location(3, 0, 0);
        Location volcanoLocation9 = new Location(4, 0, 0);
        Location volcanoLocation10 = new Location(1, -3, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.NORTHWEST_WEST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());
    }

    @Test (expected = NotEnoughPiecesException.class)
    public void testFoundSettlementThrowsNotEnoughPiecesException() throws
            IllegalTilePlacementException,
            BuildConditionsNotMetException,
            NoHexesToExpandToException,
            NotEnoughPiecesException
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
        Tile tile10 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile tile11 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);

        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(-2, -1, 0);
        Location volcanoLocation3 = new Location(0, 2, 0);
        Location volcanoLocation4 = new Location(2, 1, 0);
        Location volcanoLocation5 = new Location(1, 0, 0);
        Location volcanoLocation6 = new Location(-1, -2, 0);
        Location volcanoLocation7 = new Location(2, -1, 0);
        Location volcanoLocation8 = new Location(3, 0, 0);
        Location volcanoLocation9 = new Location(4, 0, 0);
        Location volcanoLocation10 = new Location(1, -3, 0);
        Location volcanoLocation11 = new Location(6, 0, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.NORTHWEST_WEST);
        this.world.insertTileIntoTileManager(tile11, volcanoLocation11, TileOrientation.SOUTHEAST_EAST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile2.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile3.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile4.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile5.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile6.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile7.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile8.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile9.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getLeftHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile10.getRightHexRelativeToVolcano());
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, this.player.getVillagerCount());

        this.player.foundSettlement(tile11.getLeftHexRelativeToVolcano());
    }

    @Test
    public void testExpandSettlement() throws Exception
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
        Location volcanoLocation10 = new Location(1, -2, 0);

        this.world.insertTileIntoTileManager(tile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile3, volcanoLocation3, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile4, volcanoLocation4, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile5, volcanoLocation5, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile6, volcanoLocation6, TileOrientation.SOUTHWEST_SOUTHEAST);
        this.world.insertTileIntoTileManager(tile7, volcanoLocation7, TileOrientation.SOUTHEAST_EAST);
        this.world.insertTileIntoTileManager(tile8, volcanoLocation8, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(tile9, volcanoLocation9, TileOrientation.NORTHEAST_NORTHWEST);
        this.world.insertTileIntoTileManager(tile10, volcanoLocation10, TileOrientation.SOUTHWEST_SOUTHEAST);

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        Settlement newSettlement = this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount = Settings.STARTING_VILLAGER_COUNT - 1;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 7;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.JUNGLE);
        pieceCount -= 8;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 2;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.ROCKY);
        pieceCount -= 1;
        Assert.assertEquals(pieceCount, player.getVillagerCount());
    }

    @Test (expected = NotEnoughPiecesException.class)
    public void testExpandSettlementThrowsNotEnoughPiecesException() throws Exception
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

        int pieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        Settlement newSettlement = this.player.foundSettlement(tile1.getRightHexRelativeToVolcano());
        pieceCount = Settings.STARTING_VILLAGER_COUNT - 1;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 7;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.JUNGLE);
        pieceCount -= 8;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        pieceCount -= 2;
        Assert.assertEquals(pieceCount, player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.ROCKY);
    }

    @Test
    public void testBuildTotoroSanctuary() throws Exception
    {
        Tile expansionTile1 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Tile expansionTile2 = new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS);
        Location volcanoLocation1 = new Location(-2, 0, 0);
        Location volcanoLocation2 = new Location(2, 3, 0);
        this.world.insertTileIntoTileManager(expansionTile1, volcanoLocation1, TileOrientation.EAST_NORTHEAST);
        this.world.insertTileIntoTileManager(expansionTile2, volcanoLocation2, TileOrientation.SOUTHWEST_SOUTHEAST);

        int villagerPieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(villagerPieceCount, this.player.getVillagerCount());

        int totoroPieceCount = Settings.STARTING_TOTORO_COUNT;
        Assert.assertEquals(totoroPieceCount, this.player.getTotoroCount());

        Settlement newSettlement = this.player.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        villagerPieceCount -= 1;
        Assert.assertEquals(villagerPieceCount, this.player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.JUNGLE);
        villagerPieceCount -= 3;
        Assert.assertEquals(villagerPieceCount, this.player.getVillagerCount());

        this.player.expandSettlement(newSettlement, Terrain.GRASSLANDS);
        villagerPieceCount -= 1;
        Assert.assertEquals(villagerPieceCount, this.player.getVillagerCount());

        FirstTile sanctuaryTile = (FirstTile) this.world.getHexByCoordinate(0,0,0).getOwner();
        Hex sanctuaryHex = sanctuaryTile.getLakeHex();
        this.player.buildTotoroSanctuary(sanctuaryHex);
        totoroPieceCount -= 1;
        Assert.assertEquals(totoroPieceCount, this.player.getTotoroCount());
    }

    @Test
    public void testBuildTigerPlayground() throws
            IllegalTilePlacementException,
            NotEnoughPiecesException,
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

        int villagerPieceCount = Settings.STARTING_VILLAGER_COUNT;
        Assert.assertEquals(villagerPieceCount, this.player.getVillagerCount());

        int tigerPieceCount = Settings.STARTING_TIGER_COUNT;
        Assert.assertEquals(tigerPieceCount, this.player.getTigerCount());

        this.player.foundSettlement(expansionTile1.getRightHexRelativeToVolcano());
        villagerPieceCount -= 1;
        Assert.assertEquals(villagerPieceCount, this.player.getVillagerCount());

        this.player.buildTigerPlayground(expansionTile9.getRightHexRelativeToVolcano());
        tigerPieceCount -= 1;
        Assert.assertEquals(tigerPieceCount, this.player.getTigerCount());
    }

    @Test
    public void testAbleToFoundSettlementOnPaddy() throws Exception {
        Tile paddyTile = new Tile(Terrain.PADDY, Terrain.GRASSLANDS);

        world.insertTileIntoTileManager(paddyTile, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);

        Settlement foundedSettlement = player.foundSettlement(paddyTile.getLeftHexRelativeToVolcano());

        Assert.assertEquals(foundedSettlement, paddyTile.getLeftHexRelativeToVolcano().getSettlement());

    }

    @Test
    public void testAbleToExpandOntoPaddy() throws Exception {
        Tile paddyTile = new Tile(Terrain.PADDY, Terrain.GRASSLANDS);
        Tile paddyTile2 = new Tile(Terrain.PADDY, Terrain.PADDY);

        world.insertTileIntoTileManager(paddyTile, new Location(1,0,0), TileOrientation.EAST_NORTHEAST);
        world.insertTileIntoTileManager(paddyTile2, new Location(2,-1,0), TileOrientation.WEST_SOUTHWEST);

        Settlement settlement = player.foundSettlement(world.getHexByCoordinate(2,1,0));
        player.expandSettlement(settlement, Terrain.PADDY);

        Assert.assertEquals(4, settlement.getSettlementSize());

    }

    @Test
    public void testAbleToCreateSettlementUsingShaman() throws Exception {
        Hex rockHex = world.getHexByCoordinate(-1,-1,0);
        Assert.assertEquals(null, rockHex.getSettlement());

        Settlement shamanSettlement = player.foundSettlementUsingShaman(rockHex);
        Assert.assertEquals(shamanSettlement, rockHex.getSettlement());
        Assert.assertTrue(shamanSettlement.getHasShaman());
    }

    @Test
    public void testCombiningTwoSettlementsWhereOneHasAShaman() throws Exception {
        Hex rockHex = world.getHexByCoordinate(-1,-1,0);
        Hex grassHex = world.getHexByCoordinate(0,-1,0);

        Settlement normalSettlement = player.foundSettlement(rockHex);
        Assert.assertFalse(normalSettlement.getHasShaman());

        Settlement shamanSettlement = player.foundSettlementUsingShaman(grassHex);

        Assert.assertTrue(normalSettlement.getHasShaman());

    }

    @Test
    public void testExpandingASettlementWithNoShamanPoints() throws Exception {
        Tile tileOne = new Tile(Terrain.ROCKY, Terrain.GRASSLANDS);
        Tile tileTwo = new Tile(Terrain.LAKE, Terrain.JUNGLE);
        Tile tileThree = new Tile(Terrain.GRASSLANDS,Terrain.GRASSLANDS);

        world.insertTileIntoTileManager(tileOne, new Location(2,-1,0), TileOrientation.WEST_SOUTHWEST);
        world.insertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.NORTHWEST_WEST);
        world.insertTileIntoTileManager(tileThree, new Location(2,0,1), TileOrientation.WEST_SOUTHWEST);

        Assert.assertEquals(0, player.getScore());

        Settlement settlementWithoutShaman = player.foundSettlement(world.getHexByCoordinate(0,-1,0));
        Assert.assertEquals(1, player.getScore());

        player.expandSettlement(settlementWithoutShaman, Terrain.GRASSLANDS);
        Assert.assertEquals(1+9, player.getScore());
    }

    @Test
    public void testExpandingASettlementWithShamanPoints() throws Exception {
        Tile tileOne = new Tile(Terrain.ROCKY, Terrain.GRASSLANDS);
        Tile tileTwo = new Tile(Terrain.LAKE, Terrain.JUNGLE);
        Tile tileThree = new Tile(Terrain.GRASSLANDS,Terrain.GRASSLANDS);

        world.insertTileIntoTileManager(tileOne, new Location(2,-1,0), TileOrientation.WEST_SOUTHWEST);
        world.insertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.NORTHWEST_WEST);
        world.insertTileIntoTileManager(tileThree, new Location(2,0,1), TileOrientation.WEST_SOUTHWEST);

        Assert.assertEquals(0, player.getScore());

        Settlement settlementWithShaman = player.foundSettlementUsingShaman(world.getHexByCoordinate(0,-1,0));
        Assert.assertEquals(1, player.getScore());

        player.expandSettlement(settlementWithShaman, Terrain.GRASSLANDS);
        Assert.assertEquals(1+9*2, player.getScore());
    }

    @Test(expected = OutOfShamansException.class)
    public void testCannotBuildMoreThanOneShaman() throws Exception {
        player.foundSettlementUsingShaman(world.getHexByCoordinate(1,1,0));
        player.foundSettlementUsingShaman(world.getHexByCoordinate(0,-1,0));
    }

    @Test(expected = CannotBuildShamanOnHigherLevel.class)
    public void testCannotBuildShamanOnHigherLevel() throws Exception {
        Tile tileOne = new Tile(Terrain.ROCKY, Terrain.GRASSLANDS);
        Tile tileTwo = new Tile(Terrain.LAKE, Terrain.JUNGLE);
        Tile tileThree = new Tile(Terrain.GRASSLANDS,Terrain.GRASSLANDS);

        world.insertTileIntoTileManager(tileOne, new Location(2,-1,0), TileOrientation.WEST_SOUTHWEST);
        world.insertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.NORTHWEST_WEST);
        world.insertTileIntoTileManager(tileThree, new Location(2,0,1), TileOrientation.WEST_SOUTHWEST);

        player.foundSettlementUsingShaman(tileThree.getLeftHexRelativeToVolcano());
    }

    @Test
    public void testNukingShamanStopsPointDoubling() throws Exception {
        Tile tileOne = new Tile(Terrain.ROCKY, Terrain.GRASSLANDS);
        Tile tileTwo = new Tile(Terrain.LAKE, Terrain.JUNGLE);
        Tile grassTile = new Tile(Terrain.GRASSLANDS, Terrain.GRASSLANDS);

        world.insertTileIntoTileManager(tileOne, new Location(2,-1,0), TileOrientation.WEST_SOUTHWEST);
        world.insertTileIntoTileManager(tileTwo, new Location(2,0,0), TileOrientation.NORTHWEST_WEST);
        world.insertTileIntoTileManager(grassTile, new Location(2,0,1), TileOrientation.WEST_SOUTHWEST);


        Settlement settlement = player.foundSettlementUsingShaman(world.getHexByCoordinate(0,1,0));
        Assert.assertTrue(settlement.getHasShaman());
        Assert.assertEquals(1, player.getScore());

        player.expandSettlement(settlement, Terrain.LAKE);
        Assert.assertEquals(1 + 2*2, player.getScore());

        Tile tileThree = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        world.insertTileIntoTileManager(tileThree, new Location(-2,0,0), TileOrientation.EAST_NORTHEAST);

        Tile tileNuke = new Tile(Terrain.JUNGLE, Terrain.JUNGLE);
        world.insertTileIntoTileManager(tileNuke, new Location(0,0,1), TileOrientation.NORTHWEST_WEST);

        Assert.assertFalse(settlement.getHasShaman());

        player.expandSettlement(settlement, Terrain.GRASSLANDS);
        Assert.assertEquals(5 + 10, player.getScore());

    }
}
