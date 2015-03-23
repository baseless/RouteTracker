package njp.nu.routetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class ResultActivity extends ActionBarActivity {

    RouteApplication app;

    @Override
    protected  void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_result);
        app = (RouteApplication)getApplicationContext();
    }

    public void onRestartClick(View v) {
        Intent switchToRoute = new Intent(ResultActivity.this, RouteActivity.class);
        app.startRoute();
        startActivity(switchToRoute);
    }
}
