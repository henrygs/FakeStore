# Projeto de Lista de Produtos

Este projeto foi criado a partir da API [FakeStoreAPI](https://fakestoreapi.com/). Ele possui funcionalidades como exibição de produtos, filtragem por categoria, detalhes do produto, favoritação e armazenamento no banco de dados local (Room).

## Funcionalidades

- **Home**: Exibe uma lista de produtos.
- **Filtragem por Categoria**: A lista de categorias pode ser clicada para filtrar os produtos com base na categoria selecionada.
- **Detalhes do Produto**: Ao clicar na imagem de um produto, o usuário é direcionado para a tela de detalhes do produto.
- **Favoritar Produto**: Ao clicar no botão de favoritar, o produto é salvo no banco de dados local (Room).
- **Tela de Favoritos**: Exibe a lista de produtos favoritados. Ao clicar em um produto, o usuário pode ver os detalhes, e ao clicar no botão **Deletar**, o produto é removido do banco de dados.

## Tecnologias Utilizadas

- **Kotlin**: A linguagem de programação utilizada para o desenvolvimento.
- **Koin**: Framework para injeção de dependência.
- **Navigation**: Para navegação entre as telas do aplicativo.
- **LiveData**: Para observar mudanças nas chamadas e atualizar a interface de forma reativa.
- **Room**: Banco de dados local para armazenar os produtos favoritos.

## Arquitetura

O projeto foi desenvolvido seguindo o padrão **MVVM** (Model-View-ViewModel), onde a lógica de negócios e a interação com a UI são separadas para promover a manutenção e escalabilidade.

## Instalação

### Pré-requisitos

- Android Studio instalado.
- SDK do Android configurado.

### Passos para Instalação

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
    ```

2. Abra o projeto no Android Studio.

3. Faça o build do projeto:
    - No Android Studio, clique em **Build > Make Project**.

4. Execute o aplicativo em um dispositivo Android ou emulador.

## Como Contribuir

1. Faça um **fork** deste repositório.
2. Crie uma branch para a sua modificação (`git checkout -b minha-modificacao`).
3. Faça o commit das suas alterações (`git commit -am 'Adicionando nova funcionalidade'`).
4. Envie as modificações para o repositório remoto (`git push origin minha-modificacao`).
5. Abra um **Pull Request** explicando as mudanças feitas.