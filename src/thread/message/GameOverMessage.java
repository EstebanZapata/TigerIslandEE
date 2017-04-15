package thread.message;

public class GameOverMessage extends Message {
    private String gameId;

    public GameOverMessage(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return this.gameId;
    }
}
