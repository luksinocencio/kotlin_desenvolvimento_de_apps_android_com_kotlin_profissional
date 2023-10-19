package com.devmeist3r.taskapp.util

import com.devmeist3r.taskapp.ui.data.model.Status
import com.devmeist3r.taskapp.ui.data.model.Task

public fun getTasks() = listOf(
    Task("0", "Criando Layout Tela de Login", Status.TODO),
    Task("1", "Criando Layout Tela de Splash", Status.TODO),
    Task("2", "Criando Layout Tela de Tarefas", Status.TODO),
    Task("3", "Criando Layout Tela de Cadastro", Status.TODO),
    Task("4", "Criando Layout Tela de Recuperar senha", Status.TODO)
)