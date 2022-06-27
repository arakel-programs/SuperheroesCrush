package com.arakel.superherocrush


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.arakel.superherocrush.uiltel.OnSwipeListener
import com.arakel.superherocrush.view.PlayActivity
import com.google.android.gms.ads.*
import java.util.Arrays.asList

class MainActivity : AppCompatActivity() {


    /**adding a icons/candies/logos*/
    var candies = intArrayOf(
        R.drawable.superman,
        R.drawable.batman,
        R.drawable.flash,
        R.drawable.spider,
        R.drawable.capitan,
        R.drawable.avengers
    )

    var widthOfBlock :Int = 0
    var noOfBlock :Int = 8
    var widthOfScreen :Int = 0
    lateinit var candy :ArrayList<ImageView>
    var candyToBeDragged :Int = 0
    var candyToBeReplaced :Int = 0
    var notCandy :Int = R.drawable.transparent

    lateinit var mHandler :Handler
    private lateinit var scoreResult: TextView
    var score = 0
    var interval= 100L

    private lateinit var backBtn : Button


    /**AD*/
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        backBtn = findViewById(R.id.backBtn)
        backBtn.setOnClickListener{
            startActivity(Intent
                (this@MainActivity,PlayActivity::class.java))
        }

        scoreResult = findViewById(R.id.score)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        widthOfScreen = displayMetrics.widthPixels

        var heightOfScreen = displayMetrics.heightPixels

        widthOfBlock = widthOfScreen / noOfBlock

        candy = ArrayList()
        createBoard()

        for (imageView in candy){

            imageView.setOnTouchListener(
                object :OnSwipeListener(this){
                    override fun onSwipeRight() {
                        super.onSwipeRight()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged + 1
                        candyInterChacge()
                    }

                    override fun onSwipeLift() {
                        super.onSwipeLift()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged - 1
                        candyInterChacge()
                    }

                    override fun onSwipeTop() {
                        super.onSwipeTop()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged - noOfBlock
                        candyInterChacge()
                    }

                    override fun onSwipeBottom() {
                        super.onSwipeBottom()
                        candyToBeDragged = imageView.id
                        candyToBeReplaced = candyToBeDragged + noOfBlock
                        candyInterChacge()
                    }
            })
        }

        mHandler = Handler()
        startRepeat()

        /**AD*/
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

    }

    private fun candyInterChacge() {
        var background :Int = candy.get(candyToBeReplaced).tag as Int
        var background1 :Int = candy.get(candyToBeDragged).tag as Int

        candy.get(candyToBeDragged).setImageResource(background)
        candy.get(candyToBeReplaced).setImageResource(background1)

        candy.get(candyToBeDragged).setTag(background)
        candy.get(candyToBeReplaced).setTag(background1)
    }
    private fun checkRowForThree(){
        for ( i in 0..61){
            var chosedCandy = candy.get(i).tag
            var isBlank :Boolean = candy.get(i).tag  == notCandy
            val notValid = arrayOf(6,7,14,15,22,23,30,31,38,39,46,47,54,55)
            val list = asList(*notValid)
            if (!list.contains(i)){
                var x = i

                if (candy.get(x++).tag as Int == chosedCandy
                    && !isBlank
                    && candy.get(x++).tag as Int == chosedCandy
                    && candy.get(x).tag as Int == chosedCandy
                ){
                    score = score + 3
                    scoreResult.text = "$score"
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                    x--
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                    x--
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                }
            }
        }
        moveDownCandies()
    }

    private fun checkColumnForThree(){
        for ( i in 0..47){
            var chosedCandy = candy.get(i).tag
            var isBlank :Boolean = candy.get(i).tag  == notCandy
            var x = i

            if (candy.get(x).tag as Int == chosedCandy
                    && !isBlank
                    && candy.get(x+noOfBlock).tag as Int == chosedCandy
                    && candy.get(x+2*noOfBlock).tag as Int == chosedCandy
                ){
                    score = score + 3
                    scoreResult.text = "$score"
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                    x = x + noOfBlock
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                    x = x + noOfBlock
                    candy.get(x).setImageResource(notCandy)
                    candy.get(x).setTag(notCandy)
                }
            }
        moveDownCandies()
    }

    private fun moveDownCandies() {

        val firstRow = arrayOf(1,2,3,4,5,6,7)
        val list = asList(*firstRow)
        for (i in 55 downTo 0){
            if (candy.get(i+noOfBlock).tag as Int == notCandy){

                candy.get(i+noOfBlock).setImageResource(candy.get(i).tag as Int)
                candy.get(i+noOfBlock).setTag(candy.get(i).tag as Int)

                candy.get(i).setImageResource(notCandy)
                candy.get(i).setTag(notCandy)
                if (list.contains(i) && candy.get(i).tag == notCandy){
                    var randomColor :Int = Math.abs(Math.random() * candies.size).toInt()
                    candy.get(i).setImageResource(candies[randomColor])
                    candy.get(i).setTag(candies[randomColor])
                }
            }
        }
        for (i in 0..7){
            if (candy.get(i).tag as Int == notCandy){

                var randomColor :Int = Math.abs(Math.random() * candies.size).toInt()
                candy.get(i).setImageResource(candies[randomColor])
                candy.get(i).setTag(candies[randomColor])
            }
        }

    }
    var repeatChecker :Runnable = object :Runnable{
        override fun run() {
            try {
                checkRowForThree()
                checkColumnForThree()
                moveDownCandies()
            }
            finally {
                mHandler.postDelayed(this, interval)
            }
        }
    }
    private fun startRepeat() {
        repeatChecker.run()
    }

    private fun createBoard() {
        val gridLayout = findViewById<GridLayout>(R.id.board)
        gridLayout.rowCount = noOfBlock
        gridLayout.columnCount = noOfBlock
        gridLayout.layoutParams.width = widthOfScreen
        gridLayout.layoutParams.height = widthOfScreen

        for (i in 0 until noOfBlock * noOfBlock){
            val imageView = ImageView(this)
            imageView.id = i
            imageView.layoutParams = android.
            view.ViewGroup
                .LayoutParams(widthOfBlock, widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock

            var random :Int = Math.floor(Math.random() * candies.size).toInt()

            // randomIndex from candies array
            imageView.setImageResource(candies[random])
            imageView.setTag(candies[random])

            candy.add(imageView)
            gridLayout.addView(imageView)

        }
    }
}

