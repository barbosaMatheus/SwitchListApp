package com.example.matheus.mw03_barbosa_matheus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //array to hold all the available cars
    public Car[] cars = { new Car( "DRGW", 18347, "Marble City" ),
            new Car( "KCS", 29900, "Cargill" ),
            new Car( "SP", 400089, "Lime Loader" ),
            new Car( "SP", 401290, "Lime Loader" ),
            new Car( "GATX", 73127, "Sallisaw" ),
            new Car( "SLSF", 78465, "Lime Loader" ),
            new Car( "BN", 441716, "Lime Loader" ),
            new Car( "GATX", 91381, "Feed Mill" ),
            new Car( "KCS", 753412, "Warehouse" ),
            new Car( "CNW", 490032, "Hampton Feed" ),
            new Car( "GATX", 73127, "Hampton Feed" ) };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //this method is called when we return from
    //an activity if it was sent from this one
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if( requestCode == 1 && resultCode == RESULT_OK ) { //if we are returning from PickUps
            boolean[] array = data.getBooleanArrayExtra( "array" ); //get the array from the Intent instance
            for( int i = 0; i < array.length; ++i ) { //loop through the array and update the
                cars[i].selected = array[i];          //cars array with the cars that were selected in PickUps
            }
        }
        else if( requestCode == 2 && resultCode == RESULT_OK ) { //same as above but for DropOffs
            boolean[] array = data.getBooleanArrayExtra( "array" );
            for( int i = 0; i < array.length; ++i ) {
                cars[i+5].selected = array[i];
            }
        }
    }

    //this method is called when either button is pressed
    //and based on the button it chooses which activity
    //to start
    public void segue( View view ) {
        if( view == findViewById( R.id.pickup ) ) { //if the pickup button is pressed
            Intent intent = new Intent( this, PickUps.class ); //make an intent instance for the PickUps activity
            //send serialized objects
            intent.putExtra( "car_1", this.cars[0] );
            intent.putExtra( "car_2", this.cars[1] );
            intent.putExtra( "car_3", this.cars[2] );
            intent.putExtra( "car_4", this.cars[3] );
            intent.putExtra( "car_5", this.cars[4] );
            startActivityForResult( intent, 1 ); //start the activity and give it a unique result
                                                 //code so we know where we're returning from later
        }
        else if( view == findViewById( R.id.dropoff ) ) { //same as above for DropOffs activity
            Intent intent = new Intent( this, DropOffs.class );
            //send serialized objects
            intent.putExtra( "car_1", this.cars[5] );
            intent.putExtra( "car_2", this.cars[6] );
            intent.putExtra( "car_3", this.cars[7] );
            intent.putExtra( "car_4", this.cars[8] );
            intent.putExtra( "car_5", this.cars[9] );
            intent.putExtra( "car_6", this.cars[10] );
            startActivityForResult( intent, 2 );
        }
    }
}
