package thread;

import game.settlements.BuildAction;
import game.tile.Location;
import game.tile.orientation.TileOrientation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import game.tile.Terrain;
import game.tile.Tile;
import thread.message.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameThreadTest {
    private String gameId;
    private String myPlayerId;
    private String opponentPlayerId;

    private int moveNumber;

    private BlockingQueue<Message> gameMessageQueue;
    private BlockingQueue<Message> gameResponseQueue;

    private GameThread gameThread;

    private GameCommandMessage message;

    private double moveTimeInSeconds;

    private Tile tileToPlace;

    private GameCommandMessage gameCommandMessage;
    private GameActionMessage gameOpponentActionMessage;
    private GameActionMessage gameOpponentExpansionMessage;

    @Before
    public void setupGameThread() {
        gameId = "Spaghetti";
        myPlayerId = "Spagett";
        opponentPlayerId = "yoyo";

        moveNumber = 1;

        gameMessageQueue = new LinkedBlockingQueue<>();
        gameResponseQueue = new LinkedBlockingQueue<>();

        GameThreadCommunication gameThreadCommunication = new GameThreadCommunication(gameMessageQueue, gameResponseQueue);

        gameThread = new GameThread(gameThreadCommunication, myPlayerId, opponentPlayerId, gameId);

        moveTimeInSeconds = 0.10;

        tileToPlace = new Tile(Terrain.GRASSLANDS, Terrain.LAKE);

        gameCommandMessage = new GameCommandMessage(gameId, moveTimeInSeconds, moveNumber, tileToPlace);
        gameOpponentActionMessage = new GameActionMessage(gameId, moveNumber, opponentPlayerId, new Tile(Terrain.JUNGLE, Terrain.LAKE),
                new Location(1,0,0), TileOrientation.EAST_NORTHEAST, BuildAction.FOUNDED_SETTLEMENT, new Location(0,1,0), null);

        gameOpponentExpansionMessage = new GameActionMessage(gameId, moveNumber, opponentPlayerId, new Tile(Terrain.JUNGLE, Terrain.GRASSLANDS),
                new Location(3,0,0), TileOrientation.EAST_NORTHEAST, BuildAction.EXPANDED_SETTLEMENT, new Location(0,1,0), Terrain.LAKE);


    }

    @Test
    public void testGameThreadObtainsMessage() throws InterruptedException{
        Assert.assertTrue(gameMessageQueue.isEmpty());

        gameMessageQueue.add(gameCommandMessage);

        gameThread.start();

        Thread.sleep(50);

        Assert.assertTrue(gameMessageQueue.isEmpty());

    }

    @Test
    public void testGameThreadReturnsResponseWithinSafetyTimeframe() throws InterruptedException {
        Assert.assertTrue(gameMessageQueue.isEmpty());
        Assert.assertTrue(gameResponseQueue.isEmpty());

        gameMessageQueue.add(gameCommandMessage);

        double moveTimeInMilliseconds = moveTimeInSeconds * GameThread.MILLISECONDS_PER_SECOND;
        double safetyMarginWithTestOverhead = GameThread.TIME_TO_TAKE_ACTION_SAFETY_BUFFER * 1.2;
        double moveTimeWithSafetyAndTestOverhead = moveTimeInMilliseconds * safetyMarginWithTestOverhead;

        gameThread.start();

        Thread.sleep((int) Math.floor(moveTimeWithSafetyAndTestOverhead));

        Assert.assertFalse(gameResponseQueue.isEmpty());
    }


    @Test
    public void testSameGameIdPassedIsReturned() {
        gameThread.start();
        gameMessageQueue.add(gameCommandMessage);

        Message gameResponse = waitForMessage();

        GameActionMessage gameActionMessage = (GameActionMessage)  gameResponse;
        Assert.assertTrue(gameId.equals(gameActionMessage.getGameId()));
    }

    @Test
    public void testSameMoveNumberPassedIsReturned() {
        gameThread.start();
        gameMessageQueue.add(gameCommandMessage);

        GameActionMessage gameResponse = (GameActionMessage) waitForMessage();

        Assert.assertTrue(moveNumber == gameResponse.getMoveNumber());
    }

    @Test
    public void testGameOverMessageReturnsPlayerScoreMessage() {
        gameThread.start();

        gameMessageQueue.add(gameCommandMessage);
        waitForMessage();

        gameMessageQueue.add(gameOpponentActionMessage);

        gameMessageQueue.add(gameOpponentExpansionMessage);

        gameMessageQueue.add(new GameOverMessage(gameId));
        PlayerScoreMessage message = (PlayerScoreMessage) waitForMessage();

        Assert.assertEquals("Spaghetti", message.getGameId());
        Assert.assertEquals("Spagett", message.getPlayerOne());
        Assert.assertEquals(1, message.getPlayerOneScore());

        Assert.assertEquals("yoyo", message.getPlayerTwo());
        Assert.assertEquals(3, message.getPlayerTwoScore());

    }

    private Message waitForMessage() {
        Message gameResponse = null;
        while(true) {
            try {
                gameResponse = gameResponseQueue.take();
            }
            catch (Exception e) {

            }
            if (gameResponse != null) {
                break;
            }
        }

        return gameResponse;
    }
}
