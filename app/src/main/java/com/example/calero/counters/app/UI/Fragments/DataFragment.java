package com.example.calero.counters.app.UI.Fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.calero.counters.app.Data.CountersAdapter;
import com.example.calero.counters.app.Data.CountersContract;
import com.example.calero.counters.app.Repositories.RepositoryCounter;
import com.example.calero.counters.app.ModelData;
import com.example.calero.counters.app.R;

import java.util.ArrayList;

public class DataFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private int listViewPosition;
    private static final String SELECTED_KEY = "selected_position";

    private CountersAdapter countersAdapter;
    private ListView listViewData;

    public interface Callback {
        public void onItemSelected(Uri dateUri);
    }

    public DataFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        countersAdapter = new CountersAdapter(getActivity(), null, 0);

        View view = inflater.inflate(R.layout.fragmentdata, container, false);

        listViewData = (ListView) view.findViewById(R.id.listView);
        listViewData.setAdapter(countersAdapter);

        listViewData.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                }
                listViewPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            listViewPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (listViewPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, listViewPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = CountersContract.CountersEntry.COLUMN_STAR + " ASC";

        String locationSetting = Utility.getPreferredLocation(getActivity());
        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                locationSetting, System.currentTimeMillis());

        return new CursorLoader(getActivity(),
                weatherForLocationUri,
                FORECAST_COLUMNS,
                null,
                null,
                sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
