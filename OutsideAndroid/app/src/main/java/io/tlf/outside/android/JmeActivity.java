package io.tlf.outside.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jme3.system.JmeSystem;

public class JmeActivity extends AppCompatActivity {
    JmeJfxFragment fragment;

    /**
     * Static String to pass the key for the selected test app to the
     * TestsHarness class to start the application. Also used to store the
     * current selection to the savedInstanceState Bundle.
     */
    public static final String SELECTED_APP_CLASS = "Selected_App_Class";

    /**
     * Static String to pass the key for the selected list position to the
     * savedInstanceState Bundle so the list position can be restored after
     * exiting the test application.
     */
    public static final String SELECTED_LIST_POSITION = "Selected_List_Position";

    /**
     * Static String to pass the key for the setting for enabling mouse events to the
     * savedInstanceState Bundle.
     */
    public static final String ENABLE_MOUSE_EVENTS = "Enable_Mouse_Events";

    /**
     * Static String to pass the key for the setting for enabling joystick events to the
     * savedInstanceState Bundle.
     */
    public static final String ENABLE_JOYSTICK_EVENTS = "Enable_Joystick_Events";

    /**
     * Static String to pass the key for the setting for enabling key events to the
     * savedInstanceState Bundle.
     */
    public static final String ENABLE_KEY_EVENTS = "Enable_Key_Events";

    /**
     * Static String to pass the key for the setting for verbose logging to the
     * savedInstanceState Bundle.
     */
    public static final String VERBOSE_LOGGING = "Verbose_Logging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jme);

        fragment = new JmeJfxFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();

        Bundle bundle = savedInstanceState;
        if (bundle == null) {
            bundle = getIntent().getExtras();
        }

        String appClass = bundle.getString(SELECTED_APP_CLASS);
        args.putString(SELECTED_APP_CLASS, appClass);
//        Log.d(TestActivity.class.getSimpleName(), "AppClass="+appClass);

        boolean mouseEnabled = bundle.getBoolean(ENABLE_MOUSE_EVENTS, true);
        args.putBoolean(ENABLE_MOUSE_EVENTS, mouseEnabled);
//        Log.d(TestActivity.class.getSimpleName(), "MouseEnabled="+mouseEnabled);

        boolean joystickEnabled = bundle.getBoolean(ENABLE_JOYSTICK_EVENTS, true);
        args.putBoolean(ENABLE_JOYSTICK_EVENTS, joystickEnabled);
//        Log.d(TestActivity.class.getSimpleName(), "JoystickEnabled="+joystickEnabled);

        boolean keyEnabled = bundle.getBoolean(ENABLE_KEY_EVENTS, true);
        args.putBoolean(ENABLE_KEY_EVENTS, keyEnabled);
//        Log.d(TestActivity.class.getSimpleName(), "KeyEnabled="+keyEnabled);

        boolean verboseLogging = bundle.getBoolean(VERBOSE_LOGGING, true);
        args.putBoolean(VERBOSE_LOGGING, verboseLogging);
//        Log.d(TestActivity.class.getSimpleName(), "VerboseLogging="+verboseLogging);

        fragment.setArguments(args);


        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.add(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }


    private void toggleKeyboard(final boolean show) {
        fragment.getView().getHandler().post(new Runnable() {

            @Override
            public void run() {
                JmeSystem.showSoftKeyboard(show);
            }
        });
    }
}