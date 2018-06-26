package fr.wcs.programmationnonbloquante;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private boolean isLiftMoving = false;
    private int currentFloor = 0;
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.bt_zero);
        button1 = findViewById(R.id.bt_one);
        button2 = findViewById(R.id.bt_two);
        button3 = findViewById(R.id.bt_three);
        button4 = findViewById(R.id.bt_four);
        button5 = findViewById(R.id.bt_five);
        button6 = findViewById(R.id.bt_six);
        button7 = findViewById(R.id.bt_seven);
        button8 = findViewById(R.id.bt_eight);
        button9 = findViewById(R.id.bt_nine);

        onClick(button0, 0);
        onClick(button1, 1);
        onClick(button2, 2);
        onClick(button3, 3);
        onClick(button4, 4);
        onClick(button5, 5);
        onClick(button6, 6);
        onClick(button7, 7);
        onClick(button8, 8);
        onClick(button9, 9);
    }

    private void goToFloor(int floor) {
        if (!isLiftMoving && floor != currentFloor) {
            MoveLift lift = new MoveLift();
            lift.execute(floor);
        }
    }

    private void onClick(Button button, final int floor) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFloor(floor);
            }
        });
    }

    private void moveNextFloor(int floor) {
        if (floor != currentFloor) {
            isLiftMoving = true;
            MoveLift moveLift = new MoveLift();
            moveLift.execute(floor);
        }
    }

    class MoveLift extends AsyncTask<Integer, Integer, Integer> {

        private static final String Tag = "MainActivity";

        @Override
        protected void onPreExecute() {
            //Setup precondition to execute some task
            Log.d(Tag, getString(R.string.preExecute));
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            Log.d(Tag, getString(R.string.inBackground));

            int floor = params[0];

            if (floor != currentFloor) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                currentFloor = (floor > currentFloor) ? currentFloor + 1 : currentFloor - 1;

                publishProgress(currentFloor);
            }

            return floor;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Update the progress of current task
            TextView floorCount = findViewById(R.id.floor_count);
            floorCount.setText(String.valueOf(currentFloor));
        }

        @Override
        protected void onPostExecute(Integer result) {
            //Show the result obtained from doInBackground
            Log.d(Tag, getString(R.string.postExecute));

            isLiftMoving = false;

            moveNextFloor(result);
        }
    }
}


