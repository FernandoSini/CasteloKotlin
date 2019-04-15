//constantes
const val WELCOME = "Bem Vindo ao Castelo de Kotlin"
const val OBJETIVO = "Seu objetivo será encotrar a coroa sagrada um dos aposentos do castelo."
const val SAIR = "Até a proxima!"
const val LARGURA = 4 //Largura do tabuleiro
const val ALTURA = 4  // Altura do tabuleiro
var x: Int = 2 //posição inicial do jogador X
var y: Int = 3 //posição inicial do jogador y
var tabuleiro: Tabuleiro = Tabuleiro() //objeto tabuleiro
var listaCarga: MutableList<Objeto> = ArrayList<Objeto>()

fun main(args: Array<String>) {
    println(WELCOME)
    println(OBJETIVO)
    println(">> ")
    var comando = readLine()

    println("Comando: $comando")
    while (comando!!.toUpperCase() != "SAIR") {
        print(">> ")
        println(converterMaiusculo(getPalavras(comando)))
        interpretar(converterMaiusculo(getPalavras(comando)))
        comando = readLine()


    }
    println(SAIR)

}


//função que irá receber a lista de comando e atualizar as coordenadas do player no tabuleiro
fun ir(palavras: List<String>) {

    if (palavras.contains("NORTE")) {
        if (y > 0) {
            y -= 1
            println("Posição do tabuleiro: $x, $y")
        } else {
            println("Movimento não Permitido!")
        }
    } else if (palavras.contains("SUL")) {
        if (y < ALTURA - 1) {
            y += 1
            println("Posição do tabuleiro: $x, $y")
        } else {
            println("Movimento não permitido!")
        }
    } else if (palavras.contains("LESTE")) {
        if (x < LARGURA - 1) {
            x += 1
            println("Posição do tabuleiro: $x, $y")
        } else {
            println("Movimento não permitido!")
        }
    } else if (palavras.contains("OESTE")) {
        if (x > 0) {
            x -= 1
            println("Posição do tabuleiro: $x, $y")
        } else {
            println("Movimento não permitido!")
        }
    } else {
        println("Erro!")
    }
    println(tabuleiro.getPosicao(x, y).descricao)

/*println("Indo...")*/
}


//Função get palavras para receber um parâmetro string e retornar uma lista
fun getPalavras(comando: String?): List<String> {
    return comando!!.split(" ")
}

//Função para converterMaiusculo que recebe List<String> retorna List<String>
//com todas as palavras em maiusculo. Pode fazer direto no GETPALAVRAS!!
fun converterMaiusculo(comando: List<String>): List<String> {
    val ret: MutableList<String> = mutableListOf<String>()

    for (p in comando) {
        ret.add(p.toUpperCase())
    }

    return ret


}

fun interpretar(palavras: List<String>) {
    if (palavras[0] == "IR") {
        ir(palavras)
        ver()
    } else if (palavras.contains("CARGA")) {
        carga()

    } else if (palavras.contains("PEGAR")) {
        pegar()
    } else {
        println("Comando Invalido")
    }

}

//função ver para verificar os objetos na posicao em que ele se encontra
fun ver() {

    for (obj in tabuleiro.getPosicao(x, y).listaObjeto) {
        println("Objeto: " + obj.desc)
    }

}

//função para pegar os objetos
fun pegar() {

    for (obj in tabuleiro.getPosicao(x, y).listaObjeto) {
        if (listaCarga.size <= 3) {
            listaCarga.add(obj)

        } else if (listaCarga.size > 3) {
            println("Você já está carregando 3 objetos remova 1 deles")

        } else {
            println("Não existe objeto aqui!!")
        }
    }

}

fun carga() {
    println("Objetos carregados: $listaCarga")
}

//representa o tabuleiro 4 x4

class Tabuleiro {
    var tabuleiro = arrayOf<Array<Posicao>>(
        arrayOf<Posicao>(
            Posicao("Você está na cozinha", _sul = true),
            Posicao("Você está na cozinha", _sul = true, _leste = true),
            Posicao("Você está no corredor interno", _oeste = true, _sul = true),
            Posicao("Você está na sala do trono", _sul = true)
        ),
        arrayOf<Posicao>(
            Posicao("Você está na cozinha", _leste = true),
            Posicao("Você está na cozinha", _oeste = true, _norte = true),
            Posicao("Você está no corredor interno", _norte = true, _sul = true),
            Posicao(
                "Você está na sala do trono. Tem um Goblin que gosta de café doce aqui...",
                _norte = true,
                _sul = true
            )
        ),
        arrayOf<Posicao>(
            Posicao("Você está no quarto escuro", _sul = true),
            Posicao("Você está no corredor interno", _leste = true, _oeste = true, _sul = true),
            Posicao("Você está no corredor interno", _norte = true, _sul = true, _leste = true, _oeste = true),
            Posicao("Você está no corredor interno", _oeste = true, _sul = true)
        ),
        arrayOf<Posicao>(
            Posicao("Você está no jardim", _norte = true),
            Posicao("Você está no corredor interno", _norte = true),
            Posicao("Você está na entrada principal", _norte = true),
            Posicao("Você está no corredor interno", _norte = true)
        )
    )

    fun getPosicao(x: Int, y: Int): Posicao {
        return tabuleiro[x][y]
    }

    init {
        getPosicao(0, 0).addObjeto(Cafe())
        getPosicao(0, 1).addObjeto(Espada())
        getPosicao(0, 3).addObjeto(Chave())
        getPosicao(1, 3).addObjeto(Vela())
        getPosicao(3, 0).addObjeto(Coroa())
        getPosicao(3, 3).addObjeto(Bau())

    }


}


//Classe para armazenar 16 posições com um atributo descrição e um metodo get

class Posicao(
    descricao_: String,
    _norte: Boolean = false,
    _sul: Boolean = false,
    _leste: Boolean = false,
    _oeste: Boolean = false


) {
    var descricao = descricao_
    var norte = _norte
    var sul = _sul
    var leste = _leste
    var oeste_ = _oeste
    var listaObjeto = mutableListOf<Objeto>()


    //Função para adicionar um objeto em uma lista de objetos
    fun addObjeto(objeto: Objeto) {

        listaObjeto.add(objeto)


    }

    //Função para remover um objeto de uma lista
    fun removeObjeto(objeto: Objeto) {
        listaObjeto.remove(objeto)

    }

}


abstract class Objeto constructor(desc: String) {
    var desc: String? = desc


}

class Vela() : Objeto(desc = "Vela") {
}


class Chave() : Objeto(desc = "Chave") {
}

class Bau() : Objeto(desc = "Baú") {
}


class Espada() : Objeto(desc = "Espada") {
}

class Cafe() : Objeto(desc = "Café") {
}

class Acucar() : Objeto(desc = "Acucar") {
}

class Coroa() : Objeto(desc = "Coroa") {
}

