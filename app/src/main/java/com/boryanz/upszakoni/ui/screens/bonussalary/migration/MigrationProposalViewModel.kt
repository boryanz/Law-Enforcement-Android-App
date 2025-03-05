package com.boryanz.upszakoni.ui.screens.bonussalary.migration

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boryanz.upszakoni.data.local.sharedprefs.SharedPrefsDao
import com.boryanz.upszakoni.ui.screens.bonussalary.migration.BonusSalaryGraphUiAction.MigrationAccepted
import kotlinx.coroutines.launch


class MigrationProposalViewModel : ViewModel() {

    /**
     * New users are automatically accepting new overtime tracking feature.
     * Save decision to shared preferences,delete the whole DB and add new daily entries!
     */
    @SuppressLint("NewApi")
    fun onMigrationAccepted(migrationAccepted: MigrationAccepted) =
        viewModelScope.launch {
            SharedPrefsDao.acceptOvertimeTrackingMigration()
            migrationAccepted.navigateNext()
        }
}