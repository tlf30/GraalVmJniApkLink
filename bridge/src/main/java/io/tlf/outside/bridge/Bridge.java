/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.tlf.outside.bridge;

import com.jme3.app.LegacyApplication;

public class Bridge {
    public static LegacyApplication getJme(String classpath) {
        return new JmeApp();
    }

    public static void main(String[] args) {
        System.out.println("Outside GraalVM Bridge");
    }
}
