package ifsp.android.pedrapapeltesoura.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import ifsp.android.pedrapapeltesoura.R
import java.sql.SQLException

class ConfiguracaoSqlite(contexto: Context): ConfiguracaoDao {
    companion object {
        private val BD_CONFIG = "configuracoes"
        private val TABELA_CONFIG = "configuracao"
        private val COLUNA_NUM_JOGADORES = "numero_jogadores"

        val CRIAR_TABELA_CONFIG_STMT = "CREATE TABLE IF NOT EXISTS ${TABELA_CONFIG} (" + "${COLUNA_NUM_JOGADORES} INTEGER NOT NULL );"
    }

    private val configBd: SQLiteDatabase
    init{
        configBd = contexto.openOrCreateDatabase(BD_CONFIG, MODE_PRIVATE, null)
        try{
            configBd.execSQL(CRIAR_TABELA_CONFIG_STMT)
        }
        catch (se: SQLException) {
            Log.e(contexto.getString(R.string.app_name), se.toString())
        }
    }

    override fun criarConfiguracao(config: Configuracao): Long {
        val configCv = ContentValues()
        configCv.put(COLUNA_NUM_JOGADORES, config.numeroJogadores)

        return configBd.insert(TABELA_CONFIG, null, configCv)
    }

    override fun recuperarConfiguracao(): Configuracao {
        val consultaQuery = "SELECT ${COLUNA_NUM_JOGADORES} FROM ${TABELA_CONFIG};"
        val configCursor = configBd.rawQuery(consultaQuery, null)

        return if (configCursor.moveToFirst()) {
            with (configCursor) {
                Configuracao (
                    getInt(getColumnIndexOrThrow(COLUNA_NUM_JOGADORES))
                )
            }
        }
        else {
            Configuracao(0)
        }
    }

    override fun atualizarConfiguracao(numeroJogadores: Int, numeroAntigo: Int): Int {
        val configCv = ContentValues()
        configCv.put(COLUNA_NUM_JOGADORES, numeroJogadores)

        return configBd.update(
            TABELA_CONFIG,
            configCv,
            "${COLUNA_NUM_JOGADORES} = ?",
            arrayOf(numeroAntigo.toString())
        )
    }
}