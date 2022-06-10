package ifsp.android.pedrapapeltesoura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ifsp.android.pedrapapeltesoura.controller.ConfiguracaoController
import ifsp.android.pedrapapeltesoura.databinding.ActivityMainBinding
import ifsp.android.pedrapapeltesoura.model.Configuracao
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    companion object Extras {
        const val EXTRA_JOGADOR = "EXTRA_JOGADOR"
    }

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random

    private lateinit var settingsActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var configuracaoController: ConfiguracaoController

    private lateinit var configuracaoGlobal: Configuracao
    private var jogada: String = "pedra"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        configuracaoController = ConfiguracaoController(this)

        configuracaoGlobal = configuracaoController.buscarConfiguracao()

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.pedraBt.setOnClickListener { jogada = "pedra" }
        activityMainBinding.papelBt.setOnClickListener { jogada = "papel" }
        activityMainBinding.tesouraBt.setOnClickListener { jogada = "tesoura" }

        activityMainBinding.jogarBt.setOnClickListener {
            if (configuracaoGlobal.numeroJogadores == 2) {
                mostrarResultado(configuracaoGlobal.numeroJogadores)
            } else if (configuracaoGlobal.numeroJogadores == 0) {
                mostrarResultado(1)
            }
            mostrarResultado(configuracaoGlobal.numeroJogadores)
        }

        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK) {
                if(result.data != null) {
                    val configuracao: Configuracao? = result.data?.getParcelableExtra(EXTRA_JOGADOR)
                    if (configuracao != null) {
                        if (configuracaoGlobal.numeroJogadores == 0) {
                            configuracaoController.inserirConfiguracao(configuracao)
                        } else {
                            configuracaoController.modificarConfiguracao(configuracao.numeroJogadores, configuracaoGlobal.numeroJogadores)
                        }
                        configuracaoGlobal = configuracao
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMi) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            settingsActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }

    private fun mostrarResultado(numJogadores: Int) {
        val jogador1: Int = geradorRandomico.nextInt(1..3)
        val nomeImagem = jogada
        var resultado = ""
        var jogada1 = ""


        if(jogador1 == 1) {
            jogada1 = "pedra"
        }
        if(jogador1 == 2) {
            jogada1 = "papel"
        }
        if(jogador1 == 3) {
            jogada1 = "tesoura"
        }

        "Você escolheu".also { activityMainBinding.mensagemUsuarioTv.text = it }

        activityMainBinding.escolhaUsuarioIv.setImageResource(
            resources.getIdentifier(nomeImagem, "mipmap", packageName)
        )
        activityMainBinding.escolhaJogador1Iv.setImageResource(
            resources.getIdentifier(jogada1, "mipmap", packageName)
        )

        if(numJogadores == 1) {
            activityMainBinding.escolhaJogador2Iv.visibility = View.GONE
            activityMainBinding.mensagemJogador2Tv.visibility = View.GONE
            "O jogador 1 escolheu".also { activityMainBinding.mensagemJogador1Tv.text = it }
            if ((jogador1 == 1 && jogada == "pedra") || (jogador1 == 2 && jogada == "papel") || (jogador1 == 3 && jogada == "tesoura")) {
                resultado = "Empate"
            }
            else if ((jogador1 == 1 && jogada == "tesoura") || (jogador1 == 2 && jogada == "pedra") || (jogador1 == 3 && jogada == "papel")) {
                resultado = "Jogador 1 venceu"
            }
            else if ((jogador1 == 1 && jogada == "papel") || (jogador1 == 2 && jogada == "tesoura") || (jogador1 == 3 && jogada == "pedra")) {
                resultado = "Você venceu"
            }
        } else if(numJogadores == 2) {
            activityMainBinding.escolhaJogador2Iv.visibility = View.VISIBLE
            activityMainBinding.mensagemJogador2Tv.visibility = View.VISIBLE
            val jogador2: Int = geradorRandomico.nextInt(1..3)
            var jogada2 = ""
            if(jogador2 == 1) {
                jogada2 = "pedra"
            }
            if(jogador2 == 2) {
                jogada2 = "papel"
            }
            if(jogador2 == 3) {
                jogada2 = "tesoura"
            }
            activityMainBinding.escolhaJogador2Iv.setImageResource(
                resources.getIdentifier(jogada2, "mipmap", packageName)
            )
            "O jogador 1 escolheu".also { activityMainBinding.mensagemJogador1Tv.text = it }
            "O jogador 2 escolheu".also { activityMainBinding.mensagemJogador2Tv.text = it }

            if ((jogador1 == 1 && jogador2 == 1 && jogada == "pedra") || (jogador1 == 2 && jogador2 == 2 && jogada == "papel") || (jogador1 == 3 && jogador2 == 3 && jogada == "tesoura")) {
                resultado = "Empate"
            }
            else if((jogador1 == 1 && jogador2 == 3 && jogada == "tesoura") || (jogador1 == 2 && jogador2 == 1 && jogada == "pedra") || (jogador1 == 3 && jogador2 == 2 && jogada == "papel")) {
                resultado = "Jogador 1 venceu"
            }
            else if((jogador2 == 1 && jogador1 == 3 && jogada == "tesoura") || (jogador2 == 2 && jogador1 == 1 && jogada == "pedra") || (jogador2 == 3 && jogador1 == 2 && jogada == "papel")) {
                resultado = "Jogador 2 venceu"
            }
            else if((jogador1 == 1 && jogador2 == 1 && jogada == "papel") || (jogador1 == 2 && jogador2 == 2 && jogada == "tesoura") || (jogador1 == 3 && jogador2 == 3 && jogada == "pedra")) {
                resultado = "Você venceu"
            } else {
                resultado = "Ninguem ganhou"
            }
        }
        "O resultado foi: $resultado".also { activityMainBinding.resultadoTv.text = it }
    }
}


