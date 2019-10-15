package com.edynamix.learning.android.carsapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.edynamix.learning.android.carsapp.Car;
import com.edynamix.learning.android.carsapp.CarDetailsView;
import com.edynamix.learning.android.carsapp.CarsStorage;
import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.utils.Constants;

import java.util.List;

public class ListViewActivity extends Activity {

    private Button buttonBack;
    private Button buttonShowYearClose;

    private TextView textViewEmailInAppBar;

    private ListView listViewCars;

    private Dialog dialogShowYearOfManufacture;

    private String YEAR_LABEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_list_view);

        initStrings();
        initViews();
    }

    private void initStrings() {
        YEAR_LABEL = getResources().getString(R.string.year) + " ";
    }

    private void initViews() {
        initAppToolbar();
        initListView();
        initDialogWithYearOfManufacture();
    }

    private void initAppToolbar() {
        textViewEmailInAppBar = (TextView) findViewById(R.id.textViewLoggedInEmail);
        setEmailFromSharedPrefsInAppBar(textViewEmailInAppBar);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEmailFromSharedPrefsInAppBar(TextView textViewForEmail) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedPrefsEmail = sharedPreferences.getString(Constants.SHARED_PREFERENCES_EMAIL, Constants.EMPTY_VALUE);
        textViewForEmail.setText(sharedPrefsEmail);
    }

    private void initListView() {
        listViewCars = (ListView) findViewById(R.id.listViewCars);
        listViewCars.setAdapter(new CarAdapter(CarsStorage.carsList));

        listViewCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setTag(position);
                showDialogWithYearOfManufacture(view);
            }
        });
    }

    private void initDialogWithYearOfManufacture() {
        final Dialog dialogShowYearOfManufacture = new Dialog(ListViewActivity.this);
        dialogShowYearOfManufacture.setContentView(R.layout.display_year_of_manufacture_dialog);
        this.dialogShowYearOfManufacture = dialogShowYearOfManufacture;
        this.dialogShowYearOfManufacture.setTitle(R.string.year_of_manufacture);

        initButtonShowYearInDialog();
    }

    private void initButtonShowYearInDialog() {
        buttonShowYearClose = (Button) dialogShowYearOfManufacture.findViewById(R.id.buttonShowYearDialogClose);
        buttonShowYearClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowYearOfManufacture.dismiss();
            }
        });
    }

    private void showDialogWithYearOfManufacture(View view) {
        int carIndex = (int) view.getTag();

        TextView textViewYearOfManufacture = (TextView) dialogShowYearOfManufacture.findViewById(R.id.textViewDialogDisplayYearOfManufacture);
        textViewYearOfManufacture.setText(YEAR_LABEL + CarsStorage.carsList.get(carIndex).getYearOfManufacture());

        dialogShowYearOfManufacture.show();
    }

    private class CarAdapter extends BaseAdapter {

        List<Car> carsList;

        CarAdapter(List<Car> carsList) {
            this.carsList = carsList;
        }

        @Override
        public int getCount() {
            return carsList.size();
        }

        @Override
        public Object getItem(int position) {
            return carsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) ListViewActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Car car = carsList.get(position);
            CarDetailsView carDetails = new CarDetailsView(ListViewActivity.this, car);

            return carDetails;
        }
    }
}
