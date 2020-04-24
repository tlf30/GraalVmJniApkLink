package io.tlf.outside.bridge;

import com.jayfella.jme.jfx.JavaFxUI;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import javafx.scene.control.Button;

public class JmeApp extends SimpleApplication {

    private Geometry boxGeometry;
    private boolean direction = true;

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        boxGeometry = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        boxGeometry.setMaterial(mat);
        boxGeometry.setLocalTranslation(-2, 0, 0);
        rootNode.attachChild(boxGeometry);

        //Init Jfx
        JavaFxUI.initialize(this);

        // add a javafx control to the GUI scene
        Button button = new Button("Click Me");
        JavaFxUI.getInstance().attachChild(button);
    }

    @Override
    public void simpleUpdate(float tpf) {
        boxGeometry.rotate(0, tpf * 2f, 0);
    }
}
