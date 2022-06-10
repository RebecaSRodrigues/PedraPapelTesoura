package ifsp.android.pedrapapeltesoura.controller

import ifsp.android.pedrapapeltesoura.model.Configuracao
import ifsp.android.pedrapapeltesoura.model.ConfiguracaoDao
import ifsp.android.pedrapapeltesoura.model.ConfiguracaoSqlite
import ifsp.android.pedrapapeltesoura.MainActivity

class ConfiguracaoController(mainActivity: MainActivity) {
    private val configuracaoDao: ConfiguracaoDao = ConfiguracaoSqlite(mainActivity)

    fun inserirConfiguracao(config: Configuracao) = configuracaoDao.criarConfiguracao(config)
    fun buscarConfiguracao() = configuracaoDao.recuperarConfiguracao()
    fun modificarConfiguracao(numeroJogadores: Int, numeroAntigo: Int) = configuracaoDao.atualizarConfiguracao(numeroJogadores, numeroAntigo)
}