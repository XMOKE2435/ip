import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {
    private xmoke.Xmoke xmoke = new xmoke.Xmoke();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("XMOKE");
            stage.setMinHeight(300.0);
            stage.setMinWidth(400.0);

            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setXmoke(xmoke);
            mainWindow.setUserImage(loadImage("/images/DaUser.jpg"));
            mainWindow.setDukeImage(loadImage("/images/DaDuke.jpg"));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image loadImage(String path) {
        try (var stream = getClass().getResourceAsStream(path)) {
            if (stream != null) {
                Image img = new Image(stream);
                if (img.getWidth() > 1 && img.getHeight() > 1) {
                    return img;
                }
            }
        } catch (Exception ignored) {
        }
        WritableImage fallback = new WritableImage(100, 100);
        var pw = fallback.getPixelWriter();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                pw.setColor(x, y, Color.LIGHTGRAY);
            }
        }
        return fallback;
    }
}
