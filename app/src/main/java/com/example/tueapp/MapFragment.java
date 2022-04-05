package com.example.tueapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {

    //Location Permission code and checking if the permission was granted or not in the main activity
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted = MainActivity.getMyData();

    //Coordinates Array
    double coordinates[] = new double[2];

    //Creating an arrayAdapter and the List
    ArrayAdapter<String> arrayAdapter;
    List<String> myList = new ArrayList<>();

    FusedLocationProviderClient fusedLocationProviderClient;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Initialize view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        //MapAsync
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                //If location permission granted show zoom to me button and user on map
                if (locationPermissionGranted) {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                }

                // Creating a map bound of google maps to just show the university area
                LatLng one = new LatLng(51.452505317648395, 5.483781791020252);
                LatLng two = new LatLng(51.4465266596796, 5.4991904032922445);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                builder.include(one);
                builder.include(two);

                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;

                int padding = (int) (width * 0.01);

                googleMap.setLatLngBoundsForCameraTarget(bounds);

                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

                googleMap.setMinZoomPreference(googleMap.getCameraPosition().zoom);

                //Get Data
                myList.addAll(Data.getMyListData());

                //Creating a SearchView
                SearchView searchView = (SearchView) view.findViewById(R.id.search);

                //Creating a ListView and adding elements to it
                ListView listView = (ListView) view.findViewById(R.id.my_list);

                //Setting up the adapter and the listView
                arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, myList);
                listView.setAdapter(arrayAdapter);

                //In the beginning the list view need to be invisible
                listView.setVisibility(View.INVISIBLE);
                // Search bar default text
                searchView.setQueryHint("Search Here");

                //Setting the listView invisible or visible when the user presses on search icon or close icon
                searchView.setOnSearchClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listView.setVisibility(View.VISIBLE);
                        supportMapFragment.getView().setVisibility(View.INVISIBLE);
                    }
                });
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        listView.setVisibility(View.INVISIBLE);
                        supportMapFragment.getView().setVisibility(View.VISIBLE);
                        return false;
                    }
                });

                //Adding a Listener for the text and a filter in order to be easier for the user to search things
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if(myList.toString().toLowerCase().contains(s.toLowerCase())){
                            arrayAdapter.getFilter().filter(s);
                        }else{
                            Toast.makeText(getActivity(), "No Match found",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if(myList.toString().toLowerCase().contains(s.toLowerCase())){
                            arrayAdapter.getFilter().filter(s);
                        }else{
                            Toast.makeText(getActivity(), "No Match found",Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });

                //If the user selects something from the list, a marker will be created on the map
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Clear all the old markers and close the search view
                        searchView.setIconified(true);
                        googleMap.clear();

                        //Creating a marker with the data from Data.java
                        LatLng marker = new LatLng(Data.getLat()[i], Data.getLong()[i]);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                        googleMap.addMarker(new MarkerOptions().position(marker).title(myList.get(i)));
                        supportMapFragment.getView().setVisibility(View.VISIBLE);

                        // Getting the coordinates
                        if(locationPermissionGranted) {
                            if(coordinates[0] == 0.0 || coordinates[1] == 0.0){
                                Toast.makeText(getActivity(), "1 ", Toast.LENGTH_LONG).show();
                                getLocation();
                            }else{
                                if(coordinates[0] == 0.0 || coordinates[1] == 0.0){
                                    Toast.makeText(getActivity(), " 2", Toast.LENGTH_LONG).show();
                                    getLocation();
                                }else {
                                    if(coordinates[0] == 0.0 || coordinates[1] == 0.0){
                                        Toast.makeText(getActivity(), " 3", Toast.LENGTH_LONG).show();
                                        getLocation();
                                    }
                                }

                            }
                            Toast.makeText(getActivity(), " " + coordinates[0], Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(getActivity(), "Allow location permission", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            //Getting the current location coordinates
            private void  getLocation() {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location != null) {
                            coordinates[0] = location.getLatitude();
                            coordinates[1] = location.getLongitude();
                        }
                    }
                });
            }
        });
        return view;
    }
}