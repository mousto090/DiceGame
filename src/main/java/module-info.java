module org.game.DiceGame {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	requires jedis;
    
    opens org.game.DiceGame to javafx.fxml;
    exports org.game.DiceGame;
    opens org.game.DiceGame.controllers to javafx.fxml;
    exports org.game.DiceGame.controllers;
    exports org.game.DiceGame.models;
}