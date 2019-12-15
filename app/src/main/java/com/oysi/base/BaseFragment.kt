package com.oysi.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.oysi.R
import com.wang.avi.AVLoadingIndicatorView
import dmax.dialog.SpotsDialog

open class BaseFragment : Fragment() {
    protected var mContext: Context? = null
    private lateinit var mActivity: Activity
    private lateinit var progressDialog: AlertDialog

    lateinit var tinyDB: TinyDB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
            mContext=context
            tinyDB= TinyDB(context)
        }
    }

    override fun onDetach() {
        super.onDetach()
        mContext=null
    }

    fun progressDialog(context: Context?, message: String?): AlertDialog {
        return SpotsDialog.Builder().setContext(context)
            .setMessage(message)
            .build()
    }

    @SuppressLint("InflateParams")
    fun progressLoadingDialog(context: Context): Dialog {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val viewDialog = inflater.inflate(R.layout.dialog_load_progress, null)
        dialogBuilder.setView(viewDialog)
        val dialog = dialogBuilder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val avi = viewDialog.findViewById<AVLoadingIndicatorView>(R.id.avi)
        dialog.setCanceledOnTouchOutside(false)
        avi.show()
        return dialog
    }

}