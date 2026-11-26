package com.clearstorage

import com.android.tools.idea.run.AndroidRunConfiguration
import com.intellij.execution.RunManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction

// Text, description and default icon are declared in plugin.xml (action id "ClearStorage.Toggle").
class ClearStorageToggleAction : ToggleAction() {

    // update()/isSelected() only read the selected run configuration, which is safe off the EDT.
    // The implicit OLD_EDT default made every toolbar refresh grab the UI thread (freeze reports).
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun isSelected(e: AnActionEvent): Boolean =
        e.androidRunConfig()?.CLEAR_APP_STORAGE ?: false

    override fun setSelected(e: AnActionEvent, state: Boolean) {
        e.androidRunConfig()?.CLEAR_APP_STORAGE = state
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        val config = e.androidRunConfig()
        e.presentation.isEnabled = config != null
        if (config != null) {
            e.presentation.icon = if (config.CLEAR_APP_STORAGE) AllIcons.Actions.GC else AllIcons.Actions.Edit
        }
    }

    private fun AnActionEvent.androidRunConfig(): AndroidRunConfiguration? {
        val project = project ?: return null
        return RunManager.getInstance(project).selectedConfiguration?.configuration as? AndroidRunConfiguration
    }
}
