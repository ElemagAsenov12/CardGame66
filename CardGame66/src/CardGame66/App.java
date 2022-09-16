package CardGame66;

public class App {
    public static void main(String[] args) {
        UserPlayer player = new UserPlayer();
        ComputerPlayer computer = new ComputerPlayer();

        Game66 game1 = new Game66(player, computer);

        game1.play();
    }
}
