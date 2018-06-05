# Tweets-Explorer
Sample project for educational purpose. Shows (minimally) how to search and display tweets content on Android.

*Description is in portuguese because it's intended for my brazilian students.*

Em aplicações profissionais, recomenda-se utilizar a RecyclerView ao invés da ListView. Contudo, por questões didáticas e também para que tenham uma noção caso um dia precisem, utilizaremos a ListView neste projeto. Importante reparar:

1. Como o uso da ListView se assemelha ao do Spinner (relação com Adapter como intermediário)
2. Como passamos nosso próprio layout ao Adapter, mesmo para uma view simples, em que há apenas texto.
3. Como adicionar uma dependência no arquivo de configurações do Gradle
4. Como funciona o sistema de callback para acessar a API do Twitter (e o porquê de ser assim).
