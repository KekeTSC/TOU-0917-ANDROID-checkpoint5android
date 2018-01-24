package fr.wildcodeschool.kelian.wcstravel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


class FlightController {

    private final String TAG = String.format("WCSTravel %s", getClass().getSimpleName());
    private static volatile FlightController sInstance = null;
    private ArrayList<TravelModel> mTravelList = new ArrayList<>();

    static FlightController getInstance(){
        // Check for the first time
        if(sInstance == null) {
            // Check for the second time
            synchronized (FlightController.class) {
                // If no Instance available create new One
                if(sInstance == null) {
                    sInstance = new FlightController();
                }
            }
        }
        return sInstance;
    }


    void searchFly(Context context, String departureAirport, String destinationAirport, String departureDate, String returnDate) {
        String affiliateUrl = "https://api.sandbox.amadeus.com/v1.2/flights/affiliate-search?apikey=dUuPpr69hzi2tS43KOnbo8d6koUv3xxg&origin=%s&destination=%s&departure_date=%s&return_date=%s&currency=USD";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET
                , String.format(affiliateUrl, departureAirport, destinationAirport, departureDate, returnDate),
                (String response) -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray results= jsonObject.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject json = results.getJSONObject(i);
                            TravelModel travelModel = new
                                    TravelModel(departureAirport
                                    , destinationAirport
                                    , json.getJSONObject("fare").getDouble("total_price")
                                    , json.getString("airline"));
                            Log.d(TAG, String.format("VOL DE %s, vers %S, coute %s, par %s"
                                    , travelModel.getDeparture()
                                    , travelModel.getDestination()
                                    , travelModel.getPrice()
                                    , travelModel.getCompany()));
                            mTravelList.add(travelModel);
                            context.startActivity(new Intent(context, ResultActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Désolé aucun itineraire a été trouvé", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Log.d(TAG, "error");
        });
        queue.add(stringRequest);
    }

    ArrayList<TravelModel> getTravelList() {
        return mTravelList;
    }
}
