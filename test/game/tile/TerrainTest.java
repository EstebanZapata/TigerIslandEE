package game.tile;

import org.junit.Assert;
import org.junit.Test;

public class TerrainTest {

    @Test
    public void testNonVolcanoTerrainsAreHabitable() {
        Assert.assertEquals(true, Terrain.JUNGLE.isHabitable());
        Assert.assertEquals(true, Terrain.LAKE.isHabitable());
        Assert.assertEquals(true, Terrain.GRASSLANDS.isHabitable());
        Assert.assertEquals(true, Terrain.ROCKY.isHabitable());
        Assert.assertEquals(true, Terrain.PADDY.isHabitable());
    }

    @Test
    public void testVolcanoTerrainIsNotHabitable() {
        Assert.assertEquals(false, Terrain.VOLCANO.isHabitable());
    }


}