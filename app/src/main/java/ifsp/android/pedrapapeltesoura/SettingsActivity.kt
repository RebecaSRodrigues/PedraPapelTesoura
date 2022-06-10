package ifsp.android.pedrapapeltesoura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ifsp.android.pedrapapeltesoura.MainActivity.Extras.EXTRA_JOGADOR
import ifsp.android.pedrapapeltesoura.model.Configuracao
import ifsp.android.pedrapapeltesoura.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var activitySettingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        /*val config: Configuracao? = intent.getParcelableExtra(EXTRA_JOGADOR)
        if (config != null) {
            activitySettingsBinding.numeroJogadoresSp.selectedView.isEnabled = true
        }*/

        activitySettingsBinding.salvarBt.setOnClickListener{
            val numeroJogadores: Int = (activitySettingsBinding.numeroJogadoresSp.selectedView as TextView).text.toString().toInt()
            val configuracao = Configuracao(numeroJogadores)
            val retornoIntent = Intent()
            retornoIntent.putExtra(EXTRA_JOGADOR, configuracao)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}