package com.sk.resumemaker

import android.app.AlertDialog
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.resumemaker.adapters.ExperienceAdapter
import com.sk.resumemaker.databinding.CustomDialogBinding
import com.sk.resumemaker.databinding.CustomdialogexperienceBinding
import com.sk.resumemaker.databinding.ExperiencefragmentBinding
import com.sk.resumemaker.entities.CurricularEntity
import com.sk.resumemaker.entities.ExperienceEntity
import com.sk.resumemaker.entities.QualificationEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExperienceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ExperienceFragment : Fragment(),ExperienceClicked{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var BindingExperience: ExperiencefragmentBinding
    lateinit var dialogExperiencecustom: CustomdialogexperienceBinding
    lateinit var dialogExp: Dialog
    lateinit var btncustomexp: Button
    lateinit var btnsaveexper: Button
    lateinit var etJobTitle2: EditText
    lateinit var etcompanyname2: EditText
    lateinit var etcountryname2: EditText
    lateinit var etdetails2: EditText
    lateinit var mainActivity: MainActivity
    lateinit var adapter: ExperienceAdapter
    var experienceEntityArray: ArrayList<ExperienceEntity> = ArrayList()
    var pid = 0
    var experienceEntity= ExperienceEntity()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            pid = it.getInt("id")

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        BindingExperience = ExperiencefragmentBinding.inflate(layoutInflater)
        return (BindingExperience.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ExperienceAdapter(experienceEntityArray, this)
        BindingExperience.rvexperie.layoutManager = LinearLayoutManager(requireContext())
        BindingExperience.rvexperie.adapter = adapter
        BindingExperience.btnaddexperience.setOnClickListener {
            var dialog = Dialog(requireContext())
            dialogExperiencecustom = CustomdialogexperienceBinding.inflate(layoutInflater)
            dialog.setContentView(dialogExperiencecustom.root)
            dialog.show()
            dialogExperiencecustom.btncustomexp.setOnClickListener {
                if (dialogExperiencecustom.etJobTitle2.text.isNullOrEmpty()) {
                    dialogExperiencecustom.etJobTitle2.error = ""
                } else if (dialogExperiencecustom.etcompanyname2.text.isNullOrEmpty()) {
                    dialogExperiencecustom.etcompanyname2.error = ""
                } else if (dialogExperiencecustom.etcountryname2.text.isNullOrEmpty()) {
                    dialogExperiencecustom.etcountryname2.error = ""
                } else if (dialogExperiencecustom.etdetails2.text.isNullOrEmpty()) {
                    dialogExperiencecustom.etdetails2.error = ""
                } else {
                    var expi = ExperienceEntity()
                    expi.jobtitle = dialogExperiencecustom.etJobTitle2.text.toString()
                    expi.companyname = dialogExperiencecustom.etcompanyname2.text.toString()
                    expi.countryname = dialogExperiencecustom.etcountryname2.text.toString()
                    expi.details = dialogExperiencecustom.etdetails2.text.toString()
                    expi.pid = pid
                    experienceEntityArray.add(expi)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }

            }
        }



        BindingExperience.btnsaveexper.setOnClickListener {
            if (experienceEntityArray.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Experience", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveExperience()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment4.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExperienceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

     override fun OnEditExperienceClicked(experienceEntity: ExperienceEntity, position: Int) {
    var dialog = Dialog(requireContext())
    dialogExperiencecustom = CustomdialogexperienceBinding.inflate(layoutInflater)
    dialog.setContentView(dialogExperiencecustom.root)
    dialog.show()
    dialogExperiencecustom.etJobTitle2.setText(experienceEntity.jobtitle)
    dialogExperiencecustom.etcompanyname2.setText(experienceEntity.companyname)
    dialogExperiencecustom.etcountryname2.setText(experienceEntity.countryname)
    dialogExperiencecustom.etdetails2.setText(experienceEntity.details)
    dialogExperiencecustom.btncustomexp.setOnClickListener {
        if (dialogExperiencecustom.etJobTitle2.text.isNullOrEmpty()) {
            dialogExperiencecustom.etJobTitle2.error = ""
        } else if (dialogExperiencecustom.etcompanyname2.text.isNullOrEmpty()) {
            dialogExperiencecustom.etcompanyname2.error = ""
        } else if (dialogExperiencecustom.etcountryname2.text.isNullOrEmpty()) {
            dialogExperiencecustom.etcountryname2.error = ""
        } else if (dialogExperiencecustom.etdetails2.text.isNullOrEmpty()) {
            dialogExperiencecustom.etdetails2.error = ""
        } else {
            var expi = ExperienceEntity()
            expi.jobtitle = dialogExperiencecustom.etJobTitle2.text.toString()
            expi.companyname = dialogExperiencecustom.etcompanyname2.text.toString()
            expi.countryname = dialogExperiencecustom.etcountryname2.text.toString()
            expi.details = dialogExperiencecustom.etdetails2.text.toString()
            expi.pid = pid
            experienceEntityArray.set(position,expi)
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
    }
}

override fun OnDeleteExperienceClicked(experienceEntity: ExperienceEntity, position: Int) {

    AlertDialog.Builder(mainActivity).apply {
        setTitle(mainActivity.resources.getString(R.string.delete_experience))
        setMessage(mainActivity.resources.getString(R.string.delete_experience_msg))
        setPositiveButton(mainActivity.resources.getString(R.string.yes)){_,_->
            experienceEntityArray.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        setNegativeButton(mainActivity.resources.getString(R.string.no)){_,_->}
    }.show()

}

private fun saveExperience() {
    class saveData: AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            var response = mainActivity.resumeDatabase.resumeDao().insertAllExperience(experienceEntityArray)
            System.out.println("response of saving personal info $response")
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            var bundle = Bundle()
            bundle.putInt("id",pid)
            findNavController().navigate(
                R.id.action_experienceFragment_to_CurricularActivity,
                bundle
            )
        }


    }
    saveData().execute()
}


}