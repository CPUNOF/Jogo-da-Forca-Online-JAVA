# ☕ Jogo da Forca (Java Swing) 

Este projeto é um Jogo da Forca completo desenvolvido em Java 24 com Swing, Maven e JavaFX.

Desenvolvido como projeto final para a disciplina de LPOO, este aplicativo vai muito além de um simples jogo da forca. Ele implementa uma arquitetura híbrida (online/offline), um ranking global persistido no MongoDB e um player de música totalmente funcional integrado à tela de jogo.

**Status do Projeto:** 🏆 **Concluído!** 🏆

---

## 📸 Screenshots

| Menu Principal (com Fundo Animado) | Tela de Jogo (Híbrida) | Ranking Global (Online) |
| :---: | :---: | :---: |
| [Link para Screenshot do Menu] | [Link para Screenshot do Jogo] | [Link para Screenshot do Ranking] |

---

## ✨ Funcionalidades ("Features")

Este projeto implementa 100% dos requisitos obrigatórios do PDF do trabalho e adiciona diversas funcionalidades  para uma experiência completa:

### Lógica de Jogo
* **Seleção de Jogo Completa:** O jogador pode escolher a **Dificuldade** (Fácil [8 erros], Médio [6 erros], Difícil [4 erros]) e a **Categoria** (Animais, Frutas, etc.).
* **Banco de Palavras Híbrido (Online/Offline):** O jogo primeiro tenta buscar a lista de palavras em um banco de dados **MongoDB Atlas** (Modo Online). Se a conexão falhar (sem internet), ele automaticamente usa o `palavras.txt` local como "backup".
* **Sistema de Pontuação :** O placar é cumulativo (modo "run"). A pontuação da rodada é calculada com base em:
    * `+100` Pontos base.
    * `+1` Ponto por segundo restante no cronômetro.
    * `-15` Pontos por erro.
    * `/ 2` (Pontuação cortada pela metade) se a Dica for usada.
* **Modo "Game Over":** O jogador continua jogando (acumulando pontos) até perder (por erros, tempo ou desistir) ou decidir parar.

### Ranking e Persistência
* **Ranking Global no MongoDB:** As pontuações finais das partidas são salvas na nuvem (MongoDB Atlas).
* **Ranking Completo (7 Colunas):** A tela de Ranking exibe as colunas: Posição (#), Jogador, Pontuação, Palavras Vencidas, Dicas Usadas, Categoria e Dificuldade.
* **Desempate "Polido":** O ranking é classificado (sort) por 3 níveis: 1º Pontuação (maior), 2º Palavras (maior), 3º Dicas (menor).

### Design e UX (Experiência do Usuário)
* **Player de Mídia "Zelda" (JavaFX):** Um player de música (MP3) completo está integrado na tela de jogo. Ele inclui:
    * Play/Pause, Próxima e Anterior.
    * Slider de Volume e barra de Progresso da música.
* **Canvas 2D :** O boneco da forca é 100% desenhado em 2D (`Graphics2D`) e inclui:
    * Mapeamento de 8 etapas para as 3 dificuldades.
    * Rosto que muda (neutro -> morto 'X_X').
    * Animação de "Balanço" na derrota (usando `javax.swing.Timer`).
    * Animação de "Pingos Vermelhos" na derrota.
    * Animação de "Flash" (Verde para acerto, Vermelho para erro).
* **Teclado Virtual Híbrido:** O jogador pode usar o teclado físico (`txtLetra`) ou o teclado virtual A-Z na tela. Os botões são desabilitados após o uso.
* **Fundo Animado:** A tela de menu usa um `paintComponent` customizado para exibir um fundo responsivo (`fundo_menu.jpg`).
* **Layout Responsivo:** A janela (`MainFrame`) se redimensiona automaticamente ao navegar entre telas de tamanhos diferentes (Menu vs. Jogo).

---

## 🛠️ Tecnologias Utilizadas

* **Java (JDK 24)**
* **Java Swing** (para a Interface Gráfica)
* **Maven** (para gerenciamento de dependências)
* **MongoDB Atlas** (Banco de dados NaaS para Ranking e Palavras Online)
* **JavaFX Media** (para o Player de Música MP3)

---

## ⚙️ Como Executar o Projeto

### Pré-Requisitos

1.  **Java (JDK 24 ou mais novo)**
2.  **Apache Maven**
3.  **MongoDB Atlas:**
    * Crie um Cluster gratuito no MongoDB Atlas.
    * Libere seu IP (em `Security` -> `Network Access` -> `ALLOW ACCESS FROM ANYWHERE`).
    * Crie um Database `jogodaforca`.
    * Crie a Coleção `ranking`.
    * Crie a Coleção `palavras` (e insira os documentos JSON do banco de palavras online).
    * Pegue sua "Connection String".


## 🎓 Autores

* **Breno Dantas**
* **Khaue Valério**

Projeto desenvolvido para a disciplina de Linguagem de Programação Orientada a Objetos (LPOO).
