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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {

    //variables for location permission
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    //Search View
    ArrayAdapter<String> arrayAdapter;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        SearchView searchView = (SearchView) view.findViewById(R.id.search);

        ListView listView = (ListView) view.findViewById(R.id.my_list);
        List<String> myList = new ArrayList<>();
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");
        myList.add("Luna");
        myList.add("yes");
        myList.add("no");
        myList.add("maybe");

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(arrayAdapter);
        listView.setVisibility(View.INVISIBLE);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.VISIBLE);
                supportMapFragment.getView().setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "OMG CLICK",Toast.LENGTH_LONG).show();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView.setVisibility(View.INVISIBLE);
                supportMapFragment.getView().setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Close",Toast.LENGTH_LONG).show();
                return false;
            }
        });

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
    }

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
                //get location permission
                getLocationPermission();

                //if location permission granted show zoom to me button and user on map
                if (locationPermissionGranted) {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                        @Override
                        public void onMyLocationClick(@NonNull Location location) {
                            Toast.makeText(getActivity(), location.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            Toast.makeText(getActivity(), "location!", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                }

                LatLng one = new LatLng(51.452505317648395, 5.483781791020252);
                LatLng two = new LatLng(51.4465266596796, 5.4991904032922445);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                builder.include(one);
                builder.include(two);

                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;

                int padding = (int) (width * 0.01);

                //googleMap.setLatLngBoundsForCameraTarget(bounds);

                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

                googleMap.setMinZoomPreference(googleMap.getCameraPosition().zoom);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        googleMap.addMarker((markerOptions));

                    }
                });
            }
        });
        return view;
    }


    //Code snippet from https://developer.android.com/training/permissions/requesting
    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    fineLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                }
                Boolean coarseLocationGranted = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    coarseLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_COARSE_LOCATION,false);
                }
                if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                            locationPermissionGranted = true;
                            FragmentTransaction tr = getFragmentManager().beginTransaction();
                            tr.replace(R.id.map, MapFragment.class, null);
                            tr.commit();
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            // Only approximate location access granted.
                            locationPermissionGranted = true;
                            FragmentTransaction tr = getFragmentManager().beginTransaction();
                            tr.replace(R.id.map, MapFragment.class, null);
                            tr.commit();
                            Toast.makeText(getActivity(), "Granted", Toast.LENGTH_SHORT).show();
                        } else {
                            // No location access granted.
                        }
                    }
            );



}