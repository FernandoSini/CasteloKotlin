//constantes
const val WELCOME = "Bem Vindo ao Castelo de Kotlin"
const val OBJETIVO = "Seu objetivo será encotrar a coroa sagrada um dos aposentos do castelo."
const val SAIR = "Até a proxima!"
const val LARGURA = 4 //Largura do tabuleiro
const val ALTURA = 4  // Altura do tabuleiro
var x: Int = 2 //posição inicial do jogador X
var y: Int = 3 //posição inicial do jogador y
var tabuleiro: Tabuleiro = Tabuleiro() //objeto tabuleiro
var listaCarga: MutableList<Objeto> = mutableListOf()
var goblin = true
var fim = true

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

    if (palavras.contains("NORTE") && tabuleiro.getPosicao(x, y).norte) {
        if (y > 0) {
            if (x == 3 && y == 1 && goblin) {
                print("Goblin: Você não irá sair!!!")
                return
            }
            y -= 1
            println("Posição do tabuleiro: $x, $y")

        } else {
            println("Movimento não Permitido!")
        }
    } else if (palavras.contains("SUL") && tabuleiro.getPosicao(x, y).sul) {
        if (y < ALTURA - 1) {
            y += 1
            println("Posição do tabuleiro: $x, $y")
        } else {
            println("Movimento não permitido!")
        }
    } else if (palavras.contains("LESTE") && tabuleiro.getPosicao(x, y).leste) {
        if (x < LARGURA - 1) {
            x += 1
            println("Posição do tabuleiro: $x, $y")
        } else {
            println("Movimento não permitido!")
        }
    } else if (palavras.contains("OESTE") && tabuleiro.getPosicao(x, y).oeste) {
        if (x > 0) {
            x -= 1
            println("Posição do tabuleiro: $x, $y")

        } else {
            println("Movimento não permitido!")
        }

    } else {
        println("Erro !")
    }

    println(tabuleiro.getPosicao(x, y).descricao)


    if (x == 0 && y == 2) {
        var temVela = false
        for (obj in listaCarga) {

            temVela = (obj is Vela)
            if (temVela) {
                break
            }
        }
        if (temVela) {
            println("Você possui uma vela para iluminar o quarto escuro! Aproveite para explorá-lo. ")
        } else {
            println("Você entrou em um quarto escuro sem a vela e quebrou a perna! ")
            print("GameOver")
            return


        }
    }

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
        if (listaCarga.size == 0) {
            println("Não há objetos para serem carregados")
        } else {
            carga()

        }
    } else if (palavras.contains("PEGAR")) {
        pegar()

    } else if (palavras.contains("SOLTAR")) {
        soltar()
    } else if (palavras.contains("ABRIR")) {
        abrir()
    } else if (palavras.contains("ATACAR")) {
        atacar()
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

        if (listaCarga.size > 3 || obj.equals(3)) {
            println("Você já está carregando 3 objetos remova 1 deles")

        } else if (listaCarga.size <= 3 && !listaCarga.contains(Bau()) || obj.equals(
                tabuleiro.getPosicao(
                    x,
                    y
                ).listaObjeto
            )
        ) {
            println(obj.desc)
            if (x == 3 && y == 3 && obj is Bau) {
                listaCarga.remove(obj)
                println("${obj.desc} removido")
            } else {
                listaCarga.add(obj)
                println("${obj.desc} sendo carregado")
            }

        } else {
            println("não exite objeto aqui")
        }
        if (obj is Coroa) {
            fimDeJogo(fim)
        }


    }

}


/*função para soltar os objetos*/
fun soltar() {

    for (obj in tabuleiro.getPosicao(x, y).listaObjeto)
        if (listaCarga.contains(obj)) {
            listaCarga.remove(obj)
            println("${obj.desc} solto")
        } else {
            println("Você não possui esse objeto")
        }

}


fun carga() {
    for (obj in listaCarga) {

        println("Objetos carregados: ${obj.desc}")
    }

}

fun abrir() {
    if (x == 3 && y == 3) {
        var temChave = false
        var temBau = false
        for (obj in listaCarga) {
            temChave = obj is Chave
            if (temChave) {
                break
            }
            temBau = obj is Bau
            if (temBau) {
                break
            }
        }
        if (temChave) {
            println("Abrindo o baú!")
            println("Existe um pacote de Açucar dentro do baú")
            tabuleiro.getPosicao(3, 3).addObjeto(Acucar())

        } else if (x == 3 && y == 3) {
            println("Você não tem as chaves para abrir o baú!")
        }
    } else {
        println("Não existe nada para abrir aqui!")
    }
}

fun atacar() {
    if (x == 3 && y == 1) {
        var temEspada = false
        var temCafe = false
        var temAcucar = false

        for (obj in listaCarga) {
            temEspada = obj is Espada
            if (temEspada) {
                break

            }
        }
        if (temEspada) {
            println("Goblins são muito bons com espadas! Após uma longa batalha você levou a pior...")
        } else {
            println("Atacar um Goblin sem	uma	espada é sentença de morte... Você morreu!")
        }

        for (obj in listaCarga) {
            temCafe = obj is Cafe
            if (temCafe) {
                break
            }
        }
        if (temCafe) {
            println("Você deu café para o Goblin mas estava amargo! Ele gosta de café doce! Ficou furioso e você levou a pior…")
        } else {
            println("Você não possui um café")

        }

        for (obj in listaCarga) {
            temAcucar = obj is Acucar
            if (temAcucar) {
                break
            }
        }
        if (temEspada && temCafe && temAcucar) {
            goblin = false
            println("O Golbin se distraiu tomando o café doce! Você aproveitou e acabou com ele! Caminho livre!")
        } else {
            println("Falta algum item")
        }

    } else {
        print("Não existe nada para atacar aqui!")
    }
}

fun fimDeJogo(fim: Boolean) {
    if (fim) {
        print("Fim de jogo! Você conseguiu pegar a coroa!")
    } else {
        print("Pegue a coroa para finalizar o jogo!")
    }
}
//representa o tabuleiro 4 x4

class Tabuleiro {
    var tabuleiro = arrayOf<Array<Posicao>>(
        arrayOf<Posicao>(
            Posicao("Você está na cozinha 1", _sul = true),
            Posicao("Você está na cozinha 2", _sul = true, _leste = true),
            Posicao("Você está no corredor interno 1", _oeste = true, _sul = true),
            Posicao("Você está na sala do trono", _sul = true)
        ),
        arrayOf<Posicao>(
            Posicao("Você está na cozinha 3", _leste = true, _norte = true),
            Posicao("Você está na cozinha 4", _oeste = true, _norte = true),
            Posicao("Você está no corredor interno 2", _norte = true, _sul = true),
            Posicao(
                "Você está na sala do trono. Tem um Goblin que gosta de café doce aqui...",
                _norte = true,
                _sul = true
            )
        ),
        arrayOf<Posicao>(
            Posicao("Você está no quarto escuro", _sul = true, _leste = true),
            Posicao("Você está no corredor interno 3", _leste = true, _oeste = true, _sul = true),
            Posicao("Você está no corredor interno 4", _norte = true, _sul = true, _leste = true, _oeste = true),
            Posicao("Você está no corredor interno 5", _oeste = true, _sul = true, _norte = true)
        ),
        arrayOf<Posicao>(
            Posicao("Você está no jardim", _norte = true),
            Posicao("Você está no corredor interno", _norte = true),
            Posicao("Você está na entrada principal", _norte = true),
            Posicao("Você está no corredor interno", _norte = true)
        )
    )

    fun getPosicao(x: Int, y: Int): Posicao {
        return tabuleiro[y][x]
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
    var oeste = _oeste
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

