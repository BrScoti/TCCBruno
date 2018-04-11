package com.example.bruno.tccbruno;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aluno on 05/04/2018.
 */

public class GetNearbyPlacesData  extends AsyncTask<Object,String,String> {
    String googlePlacesData;
    GoogleMap nMap;
    String url;
    @Override
    protected String doInBackground(Object... objects) {
        nMap= (GoogleMap)objects[0];
        url=(String)objects[1];
        DownloadUrl downloadUrl= new DownloadUrl();
        try {
            googlePlacesData= downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList= null;
        DataParser parser= new DataParser();
        nearbyPlaceList= parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }


    private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList){
        for (int i=0; i<nearbyPlaceList.size();i++){
            MarkerOptions markerOptions= new MarkerOptions();
            HashMap<String,String> googlePlace= nearbyPlaceList.get(i);
            String placeName= googlePlace.get("place_name");
            String vicinity= googlePlace.get("vicinity");
            String placeId= googlePlace.get("place_id");
            double lat= Double.parseDouble(googlePlace.get("lat"));
            double lng= Double.parseDouble(googlePlace.get("lng"));
            LatLng latLng= new LatLng(lat,lng);
           markerOptions.position(latLng);
            markerOptions.title(placeName+": "+placeId);
            markerOptions.snippet(vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            nMap.addMarker(markerOptions);
           // nMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
           // nMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
}
