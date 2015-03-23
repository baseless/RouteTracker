package njp.nu.routetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class StartActivity extends ActionBarActivity {

    RouteApplication app;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        app = (RouteApplication)getApplicationContext();
        setContentView(R.layout.activity_start);
    }

    public void onStartClick(View v) {
        app.startRoute();
        Intent switchToRoute = new Intent(StartActivity.this, RouteActivity.class);
        startActivity(switchToRoute);
    }
}
