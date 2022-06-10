package ifsp.android.pedrapapeltesoura.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Configuracao(
    val numeroJogadores: Int
) : Parcelable
