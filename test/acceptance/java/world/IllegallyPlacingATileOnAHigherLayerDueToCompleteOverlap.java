package acceptance.java.world;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.rules.exceptions.NoHexAtLocationException;
import game.world.rules.exceptions.TileCompletelyOverlapsAnotherException;
import game.world.World;
import org.junit.Assert;
import game.tile.Hex;
import game.tile.Location;
import game.tile.Terrain;
import game.tile.Tile;
import game.tile.orientation.TileOrientation;

public class IllegallyPlacingATileOnAHigherLayerDueToCompleteOverlap {
    private World world;
    private Tile tileOne;
    private Tile tileTwo;
    private Tile tileThree;

    @Given("^a board with at least two tiles on it$")
    public void a_board_with_at_least_two_tiles_on_it() throws Throwable {
        world = new World();
        tileOne = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        tileTwo = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);

        world.insertTileIntoTileManager(tileTwo, new Location(1,0,0), TileOrientation.SOUTHEAST_EAST);
    }

    @When("^I attempt to place the tile on the layer higher than those$")
    public void i_attempt_to_place_the_tile_on_the_layer_higher_than_those() throws Throwable {
        tileThree = new Tile(Terrain.LAKE, Terrain.ROCKY);
        try {
            world.insertTileIntoTileManager(tileThree, new Location(1,0,1), TileOrientation.SOUTHEAST_EAST);
        }
        catch (Exception e) {

        }
    }

    @When("^it completely overlaps a tile$")
    public void it_completely_overlaps_a_tile() throws Throwable {
        boolean completelyOverlapsATile = false;
        try {
            world.insertTileIntoTileManager(tileThree, new Location(1,0,1), TileOrientation.SOUTHEAST_EAST);
        }
        catch (TileCompletelyOverlapsAnotherException e) {
            completelyOverlapsATile = true;
        }
        Assert.assertTrue(completelyOverlapsATile);
    }

    @Then("^the tile should not be placed on the board$")
    public void the_tile_should_not_be_placed_on_the_board() throws Throwable {
        boolean tileWasNotPlaced = false;
        try {
            Hex hex = world.getHexByCoordinate(1,0,1);
        }
        catch (NoHexAtLocationException e) {
            tileWasNotPlaced = true;
        }

        Assert.assertTrue(tileWasNotPlaced);
    }
}
