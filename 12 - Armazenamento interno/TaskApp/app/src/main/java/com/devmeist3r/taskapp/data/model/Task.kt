package com.devmeist3r.taskapp.data.model

import android.os.Parcelable
import com.devmeist3r.taskapp.ui.model.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: Long = 0,
    var description: String = "",
    var status: Status = Status.TODO
) : Parcelable
