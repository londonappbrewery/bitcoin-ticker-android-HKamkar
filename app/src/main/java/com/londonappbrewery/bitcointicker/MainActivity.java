package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

  // Constants:
  // TODO: Create the base URL
  private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCUSD";

  // Member Variables:
  TextView mPriceTextView;
  private String symbol;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPriceTextView = (TextView) findViewById(R.id.priceLabel);
    Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

    // Create an ArrayAdapter using the String array and a spinner layout
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
      R.array.currency_array, R.layout.spinner_item);

    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

    // Apply the adapter to the spinner
    spinner.setAdapter(adapter);

    // TODO: Set an OnItemSelected listener on the spinner
    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        symbol = "BTC" + parent.getItemAtPosition(position).toString();
        RequestParams params = new RequestParams();
        params.put("market", "global");
        params.put("symbol", symbol);
        Log.d("Bitcoin", symbol + " is selected");
        letsDoSomeNetworking(params);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        Log.d("Bitcoin", "Nothing Selected");
      }
    });
  }

  // TODO: complete the letsDoSomeNetworking() method
  private void letsDoSomeNetworking(RequestParams params) {
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(BASE_URL, params, new JsonHttpResponseHandler() {

      @Override
      public void onSuccess(int statusCode, cz.
        msebera.android.httpclient.Header[] headers, JSONObject response) {
        // called when response HTTP status is "200 OK"
        Log.d("Bitcoin", "JSON: " + response.toString());
        try {
          mPriceTextView.setText(response.getString("last"));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(int statusCode, cz.
        msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
      }
    });


  }


}
