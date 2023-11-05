package com.devmeist3r.taskapp.util

import com.devmeist3r.taskapp.ui.adapter.TaskAdapter
import com.devmeist3r.taskapp.ui.adapter.TaskTopAdapter
import com.devmeist3r.taskapp.ui.data.model.Status
import com.devmeist3r.taskapp.ui.data.model.Task

fun getTasks(taskAdapter: TaskAdapter, taskTopAdapter: TaskTopAdapter? = null) {
    val taskTopList = listOf(
        Task("0", "Top da minha lista", Status.DOING)
    )

    val taskList = listOf(
        Task("0", "Criando Layout Tela de Login", Status.DOING),
        Task("1", "Criando Layout Tela de Splash", Status.DONE),
        Task("2", "Criando Layout Tela de Tarefas", Status.DONE),
        Task("3", "Criando Layout Tela de Cadastro", Status.TODO),
        Task("4", "Criando Layout Tela de Recuperar senha", Status.TODO),
        Task("5", "Criando Layout Tela de Recuperar senha", Status.DONE),
        Task("6", "Criando Layout Tela de Recuperar senha", Status.TODO),
        Task("7", "Criando Layout Tela de Recuperar senha", Status.DOING),
        Task("8", "Criando Layout Tela de Recuperar senha", Status.TODO),
        Task("9", "Criando Layout Tela de Recuperar senha", Status.TODO),
        Task("10", "Criando Layout Tela de Recuperar senha", Status.DOING),
    )

    taskTopAdapter?.submitList(taskTopList)
    taskAdapter.submitList(taskList)
}