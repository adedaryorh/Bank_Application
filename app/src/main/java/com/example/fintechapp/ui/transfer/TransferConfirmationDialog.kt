package com.example.fintechapp.ui.transfer

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import com.example.fintechapp.R

class TransferConfirmationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Confirm Transfer")
            .setMessage("Are you sure you want to proceed?")
            .setPositiveButton("Confirm") { _, _ ->
                // Handle confirmation action
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Handle cancellation action
            }
            .create()
    }
}
