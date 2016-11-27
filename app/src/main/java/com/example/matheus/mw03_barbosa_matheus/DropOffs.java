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

//this code is essentially the same
//as PickUps, it just uses information
//from different cars. Now that I think
//of it I could have just made one list
//activity but no sense in changing now :)

public class DropOffs extends ListActivity {
    public ArrayList<Car> current_cars = new ArrayList<>(6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_offs);
        List<Map<String, String>> cars = new ArrayList<>( );
        ListView cars_list = (ListView) this.findViewById( android.R.id.list );

        for( int i = 0; i < 6; ++i ) {
            String name = "car_" + Integer.toString( i+1 );
            Car car = (Car)getIntent( ).getSerializableExtra( name );
            current_cars.add( car );
            Map<String, String> car_data = new HashMap<>(2);
            String title = car.road_name + " " + Long.toString( car.car_number );
            String subtitle = "Location: " + car.location;
            car_data.put( "title", title );
            car_data.put( "subtitle", subtitle );
            cars.add( car_data );
        }

        SimpleAdapter adapter = new SimpleAdapter( this, cars, android.R.layout.simple_list_item_2,
                new String[] { "title", "subtitle" },
                new int[] { android.R.id.text1, android.R.id.text2 } ) {
            @Override
            public View getView( int pos, View convertView, ViewGroup parent ) {
                View view = super.getView( pos, convertView, parent );
                if( current_cars.get( pos ).selected ) {
                    view.setBackgroundResource( R.color.selected_row );
                }
                else {
                    view.setBackgroundResource( android.R.color.transparent );
                }
                return view;
            }
        };
        cars_list.setAdapter( adapter );

        cars_list.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l ) {
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

    @Override
    public void onBackPressed( ) {
        Intent intent = new Intent( this, MainActivity.class );
        boolean[] array = { false, false, false, false, false, false };
        for( int i = 0; i < 6; ++i ) {
            array[i] = current_cars.get( i ).selected;
        }
        intent.putExtra( "array", array );
        setResult( RESULT_OK, intent );
        finish( );
    }
}
