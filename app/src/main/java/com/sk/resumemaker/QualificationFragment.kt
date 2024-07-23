package com.sk.resumemaker

import android.app.AlertDialog
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.resumemaker.adapters.QualificationAdapter
import com.sk.resumemaker.databinding.CustomDialogBinding
import com.sk.resumemaker.databinding.QualificationBinding
import com.sk.resumemaker.entities.PersonalInfoEntity
import com.sk.resumemaker.entities.QualificationEntity
import com.sk.resumemaker.room.ResumeDatabase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QualificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QualificationFragment : Fragment(), QualificationClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var customDialogBinding: CustomDialogBinding
    lateinit var binding:QualificationBinding
    lateinit var dialog: Dialog
    lateinit var etCD:EditText
    lateinit var etSU:EditText
    lateinit var etGS:EditText
    lateinit var etyr:EditText
    lateinit var btnAdd3:Button
    lateinit var btnAddqyalification:Button
    lateinit var adapter: QualificationAdapter
    lateinit var mainActivity: MainActivity
    var qualificationEntityArray: ArrayList<QualificationEntity> = ArrayList()
    var qualificationEntity= QualificationEntity()
    var pid = 0


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
       binding = QualificationBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = QualificationAdapter(qualificationEntityArray, this)
        binding.rvqualification.layoutManager = LinearLayoutManager(requireContext())
        binding.rvqualification.adapter = adapter
        binding.btnAddqualification.setOnClickListener {
            var dialog = Dialog(requireContext())
            customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
            dialog.setContentView(customDialogBinding.root)
            dialog.show()
            customDialogBinding.btnAdd3.setOnClickListener {
                if (customDialogBinding.etCD.text.isNullOrEmpty()) {
                    customDialogBinding.etCD.error = ""
                } else if (customDialogBinding.etSU.text.isNullOrEmpty()) {
                    customDialogBinding.etSU.error = ""
                } else if (customDialogBinding.etGS.text.isNullOrEmpty()) {
                    customDialogBinding.etGS.error = ""
                } else if (customDialogBinding.etyr.text.isNullOrEmpty()) {
                    customDialogBinding.etyr.error = ""
                } else {
                    var qual = QualificationEntity()
                    qual.course = customDialogBinding.etCD.text.toString()
                    qual.school = customDialogBinding.etSU.text.toString()
                    qual.grade = customDialogBinding.etGS.text.toString()
                    qual.year = customDialogBinding.etyr.text.toString()
                    qual.pid = pid
                    qualificationEntityArray.add(qual)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }


        binding.btnSavequali.setOnClickListener {
            if (qualificationEntityArray.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Qualification", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                saveQualification()
            }

        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QualificationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnEditQualificationClicked(
        qualificationEntity: QualificationEntity,
        position: Int
    ) {
        var dialog = Dialog(requireContext())
        customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
        dialog.setContentView(customDialogBinding.root)
        dialog.show()
        customDialogBinding.etCD.setText(qualificationEntity.course)
        customDialogBinding.etSU.setText(qualificationEntity.school)
        customDialogBinding.etGS.setText(qualificationEntity.grade)
        customDialogBinding.etyr.setText(qualificationEntity.year)
        customDialogBinding.btnAdd3.setOnClickListener {
            if (customDialogBinding.etCD.text.isNullOrEmpty()) {
                customDialogBinding.etCD.error = ""
            } else if (customDialogBinding.etSU.text.isNullOrEmpty()) {
                customDialogBinding.etSU.error = ""
            } else if (customDialogBinding.etGS.text.isNullOrEmpty()) {
                customDialogBinding.etGS.error = ""
            } else if (customDialogBinding.etyr.text.isNullOrEmpty()) {
                customDialogBinding.etyr.error = ""
            } else {
                var qual = QualificationEntity()
                qual.course = customDialogBinding.etCD.text.toString()
                qual.school = customDialogBinding.etSU.text.toString()
                qual.grade = customDialogBinding.etGS.text.toString()
                qual.year = customDialogBinding.etyr.text.toString()
                qual.pid = pid
                qualificationEntityArray.set(position,qual)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    }

    override fun OnDeleteQualificationClicked(qualificationEntity: QualificationEntity, position: Int) {

        AlertDialog.Builder(mainActivity).apply {
            setTitle(mainActivity.resources.getString(R.string.delete_qualification))
            setMessage(mainActivity.resources.getString(R.string.delete_qualification_msg))
            setPositiveButton(mainActivity.resources.getString(R.string.yes)){_,_->
                qualificationEntityArray.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            setNegativeButton(mainActivity.resources.getString(R.string.no)){_,_->}
        }.show()

    }

    private fun saveQualification() {
        class saveData: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                var response = mainActivity.resumeDatabase.resumeDao().insertAllQualifications(qualificationEntityArray)
                System.out.println("response of saving personal info $response")
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                var bundle = Bundle()
                bundle.putInt("id",pid)
                findNavController().navigate(
                    R.id.action_qualification_to_experienceFragment,
                    bundle
                )
            }


        }
        saveData().execute()
    }


}