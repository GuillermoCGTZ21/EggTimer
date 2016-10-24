package com.example.el_me.eggtimer;


        import android.media.MediaPlayer;
        import android.os.CountDownTimer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.SeekBar;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // We create a SeekBar object to handle the seek bar in our user interface.  This
    // seek bar will control our timer.  We define this instance as a global object because
    // we want to access it in several functions within our class.
    SeekBar barritareloj;

    // We create a TextView object to handle the TextView displaying our timer.
    TextView tiemporeloj;

    // This variable will let us know if we have already started our counter.
    Boolean counterIsActive = false;

    // We create a Button object to control our button
    Button reloj;

    // We create a CountDownTimer
    CountDownTimer countDownTimer;

    public void resetTimer() {
        tiemporeloj.setText("00:30");
        barritareloj.setProgress(30);
        countDownTimer.cancel();
        reloj.setText("Go!");
        barritareloj.setEnabled(true);
        counterIsActive = false;
    }

    // This method will update our TextView in our user interface, reflecting the time remaining
    // before our timer gets off
    public void updateTimer(int secondsLeft) {
        // We get the number of minuts and seconds
        int minutes = (int) secondsLeft / 60;
        int seconds = (int) secondsLeft % 60;

        // If the number of seconds are less than 10, we add a preceding 0 to the
        // seconds string.
        String secondsString = Integer.toString(seconds);
        if (secondsString.length() < 2) {
            secondsString = "0" + secondsString;
        }

        // If the number of minutes are less than 10, we add a preceding 0 to the
        // minutes string
        String minutesString = Integer.toString(minutes);
        if (minutesString.length() < 2) {
            minutesString = "0" + minutesString;
        }

        // We change the text displayed on our user interface, to reflect the time
        // remaining in our timer.
        tiemporeloj.setText(minutesString + ":" + secondsString);
    }

    public void controlTimer(View view) {

        // If we have not activated our count down timer:
        if (!counterIsActive) {

            // We activate our count down timer, disable our seek bar and change the text of
            // our button
            counterIsActive = true;
            barritareloj.setEnabled(false);
            reloj.setText("Stop");

            // We set the timer to the number of seconds specified in the seek bar.  We add a tenth
            // of a second (100 milliseconds) to the initial value.
            countDownTimer = new CountDownTimer(barritareloj.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // We call our method update Timer sending the number of seconds remaining in
                    // our timer
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    // We set the text of our timer to "00:00" and play our horn sound
                    tiemporeloj.setText("00:00");
                    resetTimer();
                    MediaPlayer egg = MediaPlayer.create(getApplicationContext(), R.raw.alarma5);
                }
            }.start();

        } else {
            // If we clicked on our button while our counter was active, then we reset our timer
            // and cancel our count down
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We initialize our objects' instances
        barritareloj = (SeekBar)findViewById(R.id.barritareloj);
        tiemporeloj = (TextView)findViewById(R.id. tiemporeloj);
        reloj = (Button)findViewById(R.id.reloj);

        // We set the maximum number of minutes for our timer (10 minutes = 600 seconds).
        // We also set our current position at 30 seconds, just as we are displaying in our
        // user interface.
        barritareloj.setMax(600);
        barritareloj.setProgress(30);

        // We disable the TextView so the user can't write in it
        tiemporeloj.setEnabled(false);

        // We define our listener to respond to changes in the seek bar
        barritareloj.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // We call our method updateTimer, sending the number of seconds remaining in
                // our timer
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
