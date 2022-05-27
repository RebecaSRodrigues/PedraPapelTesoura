package ifsp.android.pedrapapeltesoura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ifsp.Configuracao
import ifsp.android.pedrapapeltesoura.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var activitySettingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        activitySettingsBinding.salvarBt.setOnClickListener{
            val numeroJogadores: Int = (activitySettingsBinding.numeroJogadoresSp.selectedView as TextView).text.toString().toInt()
            val configuracao = Configuracao(numeroJogadores)
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, configuracao)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}