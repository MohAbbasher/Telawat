package quran.telawat

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import quran.telawat.ui.theme.TelawatTheme

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelawatTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = "image"
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.image_credit),
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.clickable(onClick = {
                                openWebURL("https://pixabay.com/vectors/arabic-islam-calligraphy-word-6231345/")
                            })
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_play),
                                contentDescription = "image",
                                Modifier
                                    .size(100.dp, 100.dp)
                                    .clickable {
                                        mediaPlayer?.start() ?: startMediaPlayer()

                                    },
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_pause),
                                contentDescription = "image",
                                Modifier
                                    .size(100.dp, 100.dp)
                                    .clickable {
                                        mediaPlayer?.pause()
                                    }
                            )
                        }
                    }

                }

            }
        }

        startMediaPlayer()

    }

    private fun startMediaPlayer() {
        Log.e("media","start function")
        val url = "http://live.mp3quran.net:9702/"
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
        }
        mediaPlayer?.setOnPreparedListener { player ->
            Log.e("media","prepared")
            player.start()
        }
    }

//    override fun onStop() {
//        super.onStop()
//        mediaPlayer?.release()
//        mediaPlayer = null
//
//    }

    private fun openWebURL(inURL: String) {
        val browse = Intent(Intent.ACTION_VIEW, Uri.parse(inURL))
        startActivity(browse)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TelawatTheme {
        Greeting("Android")
    }
}