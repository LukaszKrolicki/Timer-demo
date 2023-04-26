package eu.pl.snk.senseibunny.counterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import eu.pl.snk.senseibunny.counterdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding ?=null

    private var countDownTimer: CountDownTimer?=null

    private var timerDuration: Long = 60000 // Timer duration in miliseconds

    private var pauseOffset: Long = 0 // pauseOffset = timerDuration - time left

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.timer?.text="${timerDuration/1000}"

        binding?.start?.setOnClickListener{
            startTimer(pauseOffset)
        }

        binding?.stop?.setOnClickListener{
            stopTimer()
        }

        binding?.reset?.setOnClickListener{
            resetTimer()
        }
    }

    private fun startTimer(pauseOffset: Long){
        countDownTimer=object : CountDownTimer(timerDuration-pauseOffset, 1000){
            override fun onTick(p0: Long) { //p0 is mili-seconds until end, on tick is called every countDown Interval
                this@MainActivity.pauseOffset = timerDuration - p0 //setting up the pause offset

                binding?.timer?.text="${p0/1000}"
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Finished", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    //canceling Timer, after every deletion new Countdown timer is created, thats why we need pauseOffset
    private fun stopTimer(){
        if(countDownTimer!=null){
            countDownTimer!!.cancel()
        }
    }

    private fun resetTimer(){
        if(countDownTimer!=null){
            countDownTimer!!.cancel()
            binding?.timer?.text="${timerDuration/1000}"
            countDownTimer=null
            pauseOffset=0
        }
    }
}