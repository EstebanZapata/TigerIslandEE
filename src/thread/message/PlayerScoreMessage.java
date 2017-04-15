package thread.message;

public class PlayerScoreMessage extends Message {
    private String gameId;

    private String playerOne;
    private int playerOneScore;

    private String playerTwo;
    private int playerTwoScore;

    public PlayerScoreMessage(String gameID, String playerOne, int playerOneScore, String playerTwo, int playerTwoScore) {
        this.gameId = gameID;
        this.playerOne = playerOne;
        this.playerOneScore = playerOneScore;
        this.playerTwo = playerTwo;
        this.playerTwoScore = playerTwoScore;
    }

    public String getGameId() {
        return this.gameId;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }
}
