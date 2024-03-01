import android.icu.text.SimpleDateFormat
import java.util.Calendar

fun retornaDataDiasAtras(dia: Int) : Long {
    return Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, - dia)
    }.timeInMillis
}

fun formataDataDiaMesAno(date : Long): String {
    return SimpleDateFormat("dd/MM/yyyy").format(date)
}

fun Long.formatarDataCustom(): String {
    var formatador = SimpleDateFormat("dd/MM/yyyy")
    val dataRecebida = formatador.format(this)

    var eHoje = false
    var eOntem = false
    var contador = 0
    while (contador < 7) {
        if (dataRecebida == formataDataDiaMesAno( retornaDataDiasAtras( contador ))) {
            formatador = when(contador) {
                0   -> {
                    eHoje = true
                    SimpleDateFormat("HH:mm a")
                }
                1 -> {
                    eOntem = true
                    SimpleDateFormat("HH:mm a")
                }
                in 2..6   -> SimpleDateFormat("E HH:mm a")
                else -> SimpleDateFormat("dd/MM/yyyy")
            }
        }
        contador ++
    }
    return if(eHoje) {
         "hoje " + formatador.format(this)
    } else if (eOntem) {
        "ontem " + formatador.format(this)
    }
    else formatador.format(this)
}