package ibdaa.bloodbank.view.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.utils.GPSTracker;

public class MapsActivity extends AppCompatActivity {

    GPSTracker gpsTracker;
    @BindView(R.id.btn_get_location)
    Button btnGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        gpsTracker = new GPSTracker(getApplicationContext(), getParent());

    }

    @OnClick(R.id.btn_get_location)
    public void onViewClicked() {
        //gpsTracker.getLocation();
        //gpsTracker.getAddressLine(getApplicationContext());
        gpsTracker.getLatitude();
        gpsTracker.getLongitude();
    }
}
