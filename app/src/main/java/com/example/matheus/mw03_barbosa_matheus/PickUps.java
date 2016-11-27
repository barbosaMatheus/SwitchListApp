package com.example.matheus.mw03_barbosa_matheus;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickUps extends ListActivity {
    public ArrayList<Car> current_cars = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current_cars.clear( );                                                  //clear the cars ArrayList just in case
        setContentView(R.layout.activity_pick_ups);
        List<Map<String, String>> cars = new ArrayList<>( );                    //array list to hold info for ListView
        ListView cars_list = (ListView) this.findViewById( android.R.id.list ); //ListView instance

        //Idk what this is but cell color changing doesn't work without it
        cars_list.setDescendantFocusability( ViewGroup.FOCUS_BLOCK_DESCENDANTS );

        //this view is always started by Main, and Car objects get
        //sent so this loop parses them
        for( int i = 0; i < 5; ++i ) {
            String name = "car_" + Integer.toString( i+1 );                       //the key for the ith object is 'car_i'
            Car car = (Car)getIntent( ).getSerializableExtra( name );             //save the serialized car object
            current_cars.add( car );                                              //add this new object to the current cars in the view
            Map<String, String> car_data = new HashMap<>(2);                      //this map will hold one item to be displayed on the list
            String title = car.road_name + " " + Long.toString( car.car_number ); //build the title (per app specs)
            String subtitle = "Location: " + car.location;                        //build subtitle (per app specs)
            car_data.put( "title", title );                                       //put these in the map
            car_data.put( "subtitle", subtitle );
            cars.add( car_data );                                                 //add map to the item (map) to the list
        }

        //using simple adapter because it allows subtitles
        //the getView method is overridden so the list items
        //can be properly painted upon creation
        SimpleAdapter adapter = new SimpleAdapter( this, cars, android.R.layout.simple_list_item_2,
                                                   new String[] { "title", "subtitle" },
                                                   new int[] { android.R.id.text1, android.R.id.text2 } ) {
            @Override
            public View getView( int pos, View convertView, ViewGroup parent ) {
                View view = super.getView( pos, convertView, parent );
                if( current_cars.get( pos ).selected ) {                       //if this item was selected before, paint it green
                    view.setBackgroundResource( R.color.selected_row );
                }
                else {                                                         //else just leave it transparent
                    view.setBackgroundResource( android.R.color.transparent );
                }
                return view;                                                   //return a new list item (view)
            }
        };
        cars_list.setAdapter( adapter );                                       //set this simple adapter as the data source for the list

        //overrides the onItemClick method and sets it as
        //the new click listener for list items
        cars_list.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
            //this method toggles the color and selection status of
            //each item on the list view and each car in the array
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                if( !( current_cars.get( i ).selected ) ) {
                    view.setBackgroundResource( R.color.selected_row );
                    current_cars.get( i ).selected = true;
                }
                else {
                    view.setBackgroundResource( android.R.color.transparent );
                    current_cars.get( i ).selected = false;
                }
            }
        } );
    }

    //this method gets called when the back button is pressed
    //it is being overridden so we can send back data about which
    //cars have been selected so we can remember when this activity
    //is called again
    @Override
    public void onBackPressed( ) {
        Intent intent = new Intent( this, MainActivity.class );
        boolean[] array = { false, false, false, false, false };
        //send back a boolean array indicating which cars have been selected
        for( int i = 0; i < 5; ++i ) {
            array[i] = current_cars.get( i ).selected;
        }
        intent.putExtra( "array", array );
        setResult( RESULT_OK, intent );
        finish( );
    }
}
