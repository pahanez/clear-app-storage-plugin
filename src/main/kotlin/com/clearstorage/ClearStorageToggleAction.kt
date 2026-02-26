package com.clearstorage

import com.android.tools.idea.run.AndroidRunConfiguration
import com.intellij.execution.RunManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction

class ClearStorageToggleAction : ToggleAction(
    "Clear App Storage Before Deploy",
    "Toggle clearing app storage before each deployment",
    AllIcons.Actions.GC
) {

    private fun getAndroidRunConfig(e: AnActionEvent): AndroidRunConfiguration? {
        val project = e.project ?: return null
        val selectedConfig = RunManager.getInstance(project).selectedConfiguration?.configuration
        return selectedConfig as? AndroidRunConfiguration
    }

    override fun isSelected(e: AnActionEvent): Boolean {
        val config = getAndroidRunConfig(e) ?: return false
        return config.CLEAR_APP_STORAGE
    }

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        val config = getAndroidRunConfig(e) ?: return
        config.CLEAR_APP_STORAGE = state
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val config = getAndroidRunConfig(e)
        if (config == null) {
            e.presentation.isEnabled = false
            return
        }
        e.presentation.icon = if (config.CLEAR_APP_STORAGE) AllIcons.Actions.GC else AllIcons.Actions.Edit
    }
}
