package game.world.rules;

import game.settlements.Settlement;
import game.world.CoordinateSystemHelper;
import game.world.TileManager;
import game.world.rules.exceptions.*;
import game.tile.*;

public class TileRulesManager {
    private TileManager tileManager;

    public TileRulesManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public boolean ableToPlaceTileAtLocation(Tile tile, Location[] locationsOfTileHexes) throws IllegalTilePlacementException {
        if (!tileManager.getFirstTileHasBeenPlaced()) {
            throw new SpecialFirstTileHasNotBeenPlacedException("Special first game.tile has not been placed!");
        }

        verifyNoHexesExistAtLocations(locationsOfTileHexes);

        int zCoordinate = locationsOfTileHexes[0].getHeight();
        if (zCoordinate > 0) {
            return ableToPlaceTileOnUpperLayer(tile, locationsOfTileHexes);
        }
        else {
            return ableToPlaceTileOnBaseLayer(tile, locationsOfTileHexes);
        }
    }

    private boolean ableToPlaceTileOnUpperLayer(Tile tile, Location[] locationsOfTileHexes) throws IllegalTilePlacementException {
        return noAirBelowTile(locationsOfTileHexes) && topVolcanoCoversOneBelow(locationsOfTileHexes[0]) && tileDoesNotLieCompletelyOnAnother(locationsOfTileHexes) && doesNotCoverTigersOrTotoros(locationsOfTileHexes);
    }



    private boolean doesNotCoverTigersOrTotoros(Location[] locationsOfTileHexes) throws IllegalTilePlacementException {
        for (Location location:locationsOfTileHexes) {
            Hex hex = tileManager.getHexRegardlessOfHeight(location.getxCoordinate(), location.getyCoordinate());

            Settlement settlement = hex.getSettlement();

            if (settlement == null) {
                continue;
            }

            Location locationOfTiger = settlement.getTigerLocation();
            Location locationOfTotoro = settlement.getTotoroLocation();

            if (locationOfTiger == null && locationOfTotoro == null) {
                continue;
            }

            if (locationOfTiger != null) {
                if (location.getxCoordinate() == locationOfTiger.getxCoordinate() && location.getyCoordinate() == locationOfTiger.getyCoordinate()) {
                    throw new TigerWouldBeCrushedException("Tiger would be crushed at " + locationOfTiger.toString());
                }
            }

            if (locationOfTotoro != null) {
                if (location.getxCoordinate() == locationOfTotoro.getxCoordinate() && location.getyCoordinate() == locationOfTotoro.getyCoordinate()) {
                    throw new TotoroWouldBeCrushedException("Totoro would be crushed at "+ locationOfTotoro.toString());
                }
            }

        }

        return true;
    }


    private void verifyNoHexesExistAtLocations(Location[] locationOfHexes) throws HexAlreadyAtLocationException {
        boolean ableToInsertTileIntoWorld = true;
        Location notEmptyLocation = null;

        if (!hexLocationIsEmpty(locationOfHexes[0])) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOfHexes[0];
        }

        if(!hexLocationIsEmpty(locationOfHexes[1])) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOfHexes[1];
        }

        if (!hexLocationIsEmpty(locationOfHexes[2])) {
            ableToInsertTileIntoWorld = false;
            notEmptyLocation = locationOfHexes[2];
        }

        if (ableToInsertTileIntoWorld) {
            return;
        }
        else {

            String errorMessage = "Hex already exists at location " + notEmptyLocation.toString();
            throw new HexAlreadyAtLocationException(errorMessage);
        }
    }

    private boolean ableToPlaceTileOnBaseLayer(Tile tile, Location[] locationsOfTileHexes) throws IllegalTilePlacementException {
        return tileIsAdjacentToAnExistingTile(locationsOfTileHexes);
    }

    public boolean noAirBelowTile(Location[] locationOfTileHexes) throws AirBelowTileException {
        int zCoordinate = locationOfTileHexes[0].getHeight();
        int zLayerToCheck = zCoordinate - 1;

        try {
            tileManager.getHexByCoordinate(locationOfTileHexes[0].getxCoordinate(), locationOfTileHexes[0].getyCoordinate(), zLayerToCheck);
            tileManager.getHexByCoordinate(locationOfTileHexes[1].getxCoordinate(), locationOfTileHexes[1].getyCoordinate(), zLayerToCheck);
            tileManager.getHexByCoordinate(locationOfTileHexes[2].getxCoordinate(), locationOfTileHexes[2].getyCoordinate(), zLayerToCheck);
        }
        catch (NoHexAtLocationException e) {
            throw new AirBelowTileException("Air below game.tile");
        }

        return true;
    }

    public boolean topVolcanoCoversOneBelow(Location locationOfVolcano) throws IllegalTilePlacementException {
        int xCoordinate = locationOfVolcano.getxCoordinate();
        int yCoordinate = locationOfVolcano.getyCoordinate();
        int zCoordinateToCheck = locationOfVolcano.getHeight() - 1;
        if (tileManager.getHexByCoordinate(xCoordinate,yCoordinate,zCoordinateToCheck).getTerrain() != Terrain.VOLCANO) {
            throw new TopVolcanoDoesNotCoverBottomVolcanoException(String.format("Hex at (%d,%d,%d) is not volcano", xCoordinate,yCoordinate,zCoordinateToCheck));
        }
        return true;
    }

    public boolean tileDoesNotLieCompletelyOnAnother(Location[] locationOfTileHexes) throws IllegalTilePlacementException {

        Tile tileOne;
        Tile tileTwo;
        Tile tileThree;

        int zCoordinateToCheck = locationOfTileHexes[0].getHeight() - 1;

        Location locationOne = locationOfTileHexes[0];
        Location locationTwo = locationOfTileHexes[1];
        Location locationThree = locationOfTileHexes[2];

        Location locationOneToCheck = new Location(locationOne.getxCoordinate(), locationOne.getyCoordinate(), zCoordinateToCheck);
        Location locationTwoToCheck = new Location(locationTwo.getxCoordinate(), locationTwo.getyCoordinate(), zCoordinateToCheck);
        Location locationThreeToCheck = new Location(locationThree.getxCoordinate(), locationThree.getyCoordinate(), zCoordinateToCheck);


        tileOne = tileManager.getHexByLocation(locationOneToCheck).getOwner();
        tileTwo = tileManager.getHexByLocation(locationTwoToCheck).getOwner();
        tileThree = tileManager.getHexByLocation(locationThreeToCheck).getOwner();



        if (tileOne == tileTwo && tileOne == tileThree) {
            throw new TileCompletelyOverlapsAnotherException("Tile completely overlaps another");
        }
        else {
            return true;
        }
    }

    public boolean tileIsAdjacentToAnExistingTile(Location[] locationOfHexes) throws TileNotAdjacentToAnotherException {
        Location[] adjecentHexLocations = CoordinateSystemHelper.getAdjacentHexLocationsToTile(locationOfHexes);

        for (int i = 0; i < 12; i++) {
            try {
                if (hexExistsAtLocationOrBaseLevelAtLocation(adjecentHexLocations[i])) {
                    return true;
                }
            }
            catch (NoHexAtLocationException e) {
                continue;
            }
        }
        throw new TileNotAdjacentToAnotherException("Tile being placed is not adjacent to an existing game.tile " + locationOfHexes[0] + locationOfHexes[1] + locationOfHexes[2]);
    }

    private boolean hexLocationIsEmpty(Location location) {
        try {
            Hex hex = tileManager.getHexByLocation(location);
        }
        catch (NoHexAtLocationException e) {
            return true;
        }
        return false;
    }

    public boolean ableToPlaceFirstTile() throws SpecialFirstTileHasAlreadyBeenPlacedExeption {
        if (tileManager.getFirstTileHasBeenPlaced()) {
            throw new SpecialFirstTileHasAlreadyBeenPlacedExeption("Special first game.tile has already been placed!");
        }

        else {
            return true;
        }
    }

    private boolean hexExistsAtLocationOrBaseLevelAtLocation(Location location) throws NoHexAtLocationException {
        Hex hex = tileManager.getHexRegardlessOfHeight(location.getxCoordinate(), location.getyCoordinate());
        return true;
    }
}

