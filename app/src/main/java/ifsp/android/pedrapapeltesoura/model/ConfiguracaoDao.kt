package ifsp.android.pedrapapeltesoura.model

interface ConfiguracaoDao {
    fun criarConfiguracao(config: Configuracao): Long
    fun recuperarConfiguracao(): Configuracao
    fun atualizarConfiguracao(numeroJogadores: Int, numeroAntigo: Int): Int
}
