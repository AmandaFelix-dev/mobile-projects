<h1 align="center">📱 Dev Tasks - Projeto de Mobile</h1>

<p align="center">
  </p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-2.0-purple?style=for-the-badge&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?style=for-the-badge&logo=android" alt="Compose">
  <img src="https://img.shields.io/badge/Room-Database-green?style=for-the-badge&logo=sqlite" alt="Room">
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange?style=for-the-badge" alt="MVVM">
</p>

<h3 align="center">
  Projeto desenvolvido para a disciplina de Mobile / Sistemas de Informação
</h3>

---

# 📝 Sobre o Projeto

Este aplicativo é um **Gerenciador de Tarefas (To-Do List)** desenvolvido nativamente para Android.
O objetivo foi aplicar conceitos avançados de Desenvolvimento Mobile Moderno (**MAD**), focando em arquitetura limpa, reatividade e persistência de dados local.

### ✅ Funcionalidades
- [x] **Listagem Reativa:** As tarefas atualizam automaticamente na tela.
- [x] **Adicionar Tarefas:** Input flutuante com validação.
- [x] **Editar Tarefas:** Dialog para alteração de texto.
- [x] **Concluir Tarefas:** Checkbox com animações visuais e reordenação.
- [x] **Excluir Tarefas:** Remoção definitiva do banco de dados.
- [x] **Persistência Local:** Os dados não somem ao fechar o app (uso do Room/SQLite).
- [x] **Dark Mode:** Interface moderna e profissional.

---

# 🛠 Tecnologias & Arquitetura

O projeto segue a arquitetura **MVVM (Model-View-ViewModel)** com fluxo de dados unidirecional (UDF).

### Bibliotecas Utilizadas:
* **Kotlin DSL**: Para scripts de build modernos.
* **Jetpack Compose**: UI Toolkit declarativo (sem arquivos XML).
* **Room Database**: Abstração do SQLite para persistência segura.
* **Coroutines & Flow**: Para operações assíncronas e reatividade em tempo real.
* **ViewModel**: Gerenciamento de estado que sobrevive a rotações de tela.
