package io.blackjesus.calendario.managers;

import io.blackjesus.calendario.HelloApplication;
import io.blackjesus.calendario.controllers.DayController;
import io.blackjesus.calendario.controllers.MainViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;

public class PageManager {
    private static MainViewController mainViewController;

    public static void setMainViewController(MainViewController controller) {
        mainViewController = controller;
    }

    public static void switchPage(String pageName) {
        if(mainViewController != null) {
            mainViewController.switchContent(pages.get(pageName));
        }
    }

    public static void switchPage(Node node) {
        if(mainViewController != null) {
            mainViewController.switchContent(node);
        }
    }



    /**
     * Itt tárolódnak a betöltött oldalak/nézetek
     * Kulcs az oldal neve, érték betöltött fxml Parent típusként
     * Pl: "daily" - napi nézet
     */
    public static HashMap<String, Parent> pages = new HashMap<>();

    /**
     * Betölti a nézeteket
     */
    public static void loadPages() {
        pages.put("daily", loadFxml("daily-view"));
        pages.put("weekly", loadFxml("weekly-view"));
        pages.put("monthly", loadFxml("monthly-view"));
        pages.put("yearly", loadFxml("year-view"));
    }


    /**
     * Betölti a paraméterben megadott FXML fájlt a view mappából
     * @param viewFileName
     * @return Parent típusként tér vissza
     */
    @SafeVarargs
    public static Parent loadFxml(String viewFileName, Callback<Class<?>, Object>... controllerFactory) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/" + viewFileName + ".fxml"));
        if(controllerFactory.length > 0) {
            fxmlLoader.setControllerFactory(controllerFactory[0]);
        }
        try {
            return (Parent) fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Nem sikerült betölteni: " + viewFileName);
            throw new RuntimeException(e);
        }
    }
}
