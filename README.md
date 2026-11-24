# Bolonha e Milano — versão personalizada

Projeto em Java de estratégia e administração de cidades italianas no século XV. O jogador administra recursos, agricultura, mercado, impostos, construções e prestígio para conquistar títulos da nobreza.

## Requisitos
- JDK 17 ou superior
- Terminal ou VS Code

## Como compilar e executar
Na pasta principal do projeto, execute:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

Para executar os testes automatizados:

```bash
java -cp bin bolonhaemilano.Testes
```

O resultado esperado é uma lista de testes aprovados e, ao final, `Reprovados: 0`.

## Como funciona
- De 1 a 4 participantes podem jogar na mesma partida.
- Cada participante administra uma cidade e realiza ações durante seu turno.
- O mercado permite comprar e vender grãos e terras.
- A agricultura permite realizar a colheita e alimentar os servos.
- O governo controla impostos e nível de justiça.
- Construções permitem erguer edifícios e recrutar soldados.
- O prestígio considera construções, soldados, servos e terras; ele permite subir de título.
- A partida termina quando alguém alcança o título de Rei/Rainha ou ao final do ano de 1410.

## Organização
- `src/bolonhaemilano/`: classes Java do jogo.
- `uml/`: diagramas de classes e sequência em PlantUML.
- `casos-de-uso/`: diagrama de casos de uso.

Os arquivos `.puml` podem ser visualizados com a extensão PlantUML do VS Code ou em um renderizador PlantUML.

## Classes principais
- `Main`: inicia o programa.
- `Menu`: apresenta as opções e recebe os comandos.
- `Jogador` e `Cidade`: representam os participantes e as cidades.
- `Mercado`, `Agricultura`, `Governo` e `Construcoes`: controlam as ações administrativas.
- `Nobreza`: calcula o prestígio e as promoções.
- `Partida`: controla os turnos, os anos e o vencedor.
- `Testes`: verifica regras importantes do programa.

## Observação sobre o histórico do Git
Este pacote contém a versão consolidada do projeto e não simula commits antigos. Para a avaliação, o histórico e as tags devem corresponder ao trabalho realmente realizado no seu repositório. Não apresente commits retroativos como se tivessem sido feitos durante as etapas.
