import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ConnectFour extends Application {
    public static void main(String[] args){
        launch(args);
    }

    Pane pane = new Pane();
    int[][] board  = new int[7][6];
    int player = 1;

    public void update(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Circle circle = new Circle((i * 100) + 50, (j * 100) + 50, 50, Color.WHITE);
                if (board[i][j] == 1) circle.setFill(Color.RED);
                if (board[i][j] == 2) circle.setFill(Color.YELLOW);
                pane.getChildren().add(circle);
            }
        }
    }

    public void updateWinner(int y1, int x1, int y2, int x2, int y3, int x3, int y4, int x4){
        Circle circle1 = new Circle((x1 * 100) + 50, (y1 * 100) + 50, 50, Color.WHITE);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), circle1);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        Circle circle2 = new Circle((x2 * 100) + 50, (y2 * 100) + 50, 50, Color.WHITE);
        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(0.1), circle2);
        fadeTransition2.setFromValue(1.0);
        fadeTransition2.setToValue(0.0);
        fadeTransition2.setCycleCount(Animation.INDEFINITE);
        fadeTransition2.play();
        Circle circle3 = new Circle((x3 * 100) + 50, (y3 * 100) + 50, 50, Color.WHITE);
        FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(0.1), circle3);
        fadeTransition3.setFromValue(1.0);
        fadeTransition3.setToValue(0.0);
        fadeTransition3.setCycleCount(Animation.INDEFINITE);
        fadeTransition3.play();
        Circle circle4 = new Circle((x4 * 100) + 50, (y4 * 100) + 50, 50, Color.WHITE);
        FadeTransition fadeTransition4 = new FadeTransition(Duration.seconds(0.1), circle4);
        fadeTransition4.setFromValue(1.0);
        fadeTransition4.setToValue(0.0);
        fadeTransition4.setCycleCount(Animation.INDEFINITE);
        fadeTransition4.play();

        pane.getChildren().addAll(circle1, circle2, circle3, circle4);
    }

    public int bottom(int x){
        //finds next available spot on x axis
        for (int y = 5; y >= 0; y-- ) if (board[x][y] == 0) return y;
        return -1;
    }

    public int looper(int y, int x){
        //loops back the positions that are off the board on either side of last clicked.
        if (y < 0 || x < 0 || y >= 6 || x >= 7) return 0;
        return board[x][y];
    }

    public int winner(){
        //row checker
        for(int x = 0; x < 7; x ++)for (int y = 0; y < 8; y++){
            if (looper(y, x) != 0 &&
                looper(y,x) == looper(y,x+1)&&
                looper(y,x) == looper(y,x+2)&&
                looper(y,x) == looper(y,x+3)){
                updateWinner(y,x+1, y,x+2, y,x+3, y, x);
                return looper(y,x);
        }}
        //column checker
        for(int x = 0; x < 7; x ++)for (int y = 0; y < 8; y++) {
            if (looper(y, x) != 0 &&
                    looper(y, x) == looper(y + 1, x) &&
                    looper(y, x) == looper(y + 2, x) &&
                    looper(y, x) == looper(y + 3, x)) {
                updateWinner(y + 1, x, y + 3, x, y + 2, x, y, x);
                return looper(y, x);
            }
        }
        //diagonal checker
        for(int x = 0; x < 7; x ++)for (int y = 0; y < 8; y++)for (int d = -1; d <= 1; d +=2){
            if (looper(y, x) != 0 &&
                    looper(y, x) == looper(y + 1 * d, x + 1) &&
                    looper(y, x) == looper(y + 2 * d, x + 2) &&
                    looper(y, x) == looper(y + 3 * d, x + 3)) {
                updateWinner( y + 1 * d, x + 1 , y + 2 * d, x + 2, y + 3 * d, x + 3, y, x);
                return looper(y, x);
            }
        }
        for (int x = 0; x <7; x++)for (int y = 0;y < 6;y++) if (looper(y,x) == 0) return 0;
        //tie checker
        return 3;
    }

    @Override
    public void start(Stage PrimaryStage){
        Rectangle rectangle = new Rectangle(0,0,700,600);
        rectangle.setFill(Color.GREY);
        pane.getChildren().add(rectangle);
        Text winner = new Text();
        winner.setX(120);
        winner.setY(300);

        update();

        pane.setOnMouseClicked(e ->{
            if (e.getButton() == MouseButton.PRIMARY) {
                int x = (int) (e.getX() / 100);
                int y = bottom(x);
                if(y >= 0){
                    board[x][y] = player;
                    if (player == 1) player = 2;
                    else player = 1;
                }
                update();
                if (winner() != 0 ) {
                    if (winner() == 3){
                        winner.setText("Its a tie both you lose!!!");
                    }else{
                        winner.setText("Player " + winner() + " is the Winner!");
                    }
                    winner.setFont(Font.font(50));
                    pane.getChildren().add(winner);
                }
            }
        });



        Scene scene = new Scene(pane);
        PrimaryStage.setScene(scene);
        PrimaryStage.show();

    }

}