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
import com.sk.resumemaker.adapters.CurricularAdapter
import com.sk.resumemaker.databinding.*
import com.sk.resumemaker.entities.CurricularEntity
import com.sk.resumemaker.entities.ExperienceEntity
import java.text.ParsePosition

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurricularFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurricularFragment : Fragment(), CurricularClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var customDialogBinding: CurricularCustomBinding
    lateinit var binding: CurricularActivitiesBinding
    lateinit var dialog: Dialog
    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    lateinit var btnAdd1: Button
    lateinit var btnAddcurricular: Button
    lateinit var adapter: CurricularAdapter
    lateinit var mainActivity: MainActivity
    var curricularEntityArray: ArrayList<CurricularEntity> = ArrayList()
    var curricularEntity=CurricularEntity()
    var pid = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            pid = it.getInt("id")
            System.out.println("id $pid")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = CurricularActivitiesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CurricularAdapter(curricularEntityArray,this)
        binding.rvcurricular.layoutManager = LinearLayoutManager(requireContext())
        binding.rvcurricular.adapter = adapter
        binding.btnAddcurricular.setOnClickListener {
            var dialog = Dialog(requireContext())
            customDialogBinding = CurricularCustomBinding.inflate(layoutInflater)
            dialog.setContentView(customDialogBinding.root)
            dialog.show()
            customDialogBinding.btnAdd1.setOnClickListener {
                if (customDialogBinding.etTitle.text.isNullOrEmpty()) {
                    customDialogBinding.etTitle.error = ""
                } else if (customDialogBinding.etContent.text.isNullOrEmpty()) {
                    customDialogBinding.etContent.error = ""
                } else {
                    var curri = CurricularEntity()
                    curri.title = customDialogBinding.etTitle.text.toString()
                    curri.content = customDialogBinding.etContent.text.toString()
                    curri.pid = pid
                    curricularEntityArray.add(curri)
                    adapter.notifyDataSetChanged()

                }
                dialog.dismiss()
            }
        }
        binding.btnSave1.setOnClickListener {
            if (curricularEntityArray.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Curricular Activity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                saveCurricular()
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
         * @return A new instance of fragment Fragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurricularFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnEditCurricularClicked(
        curricularEntity: CurricularEntity,
        position: Int
    ) {
        var dialog = Dialog(requireContext())
        customDialogBinding = CurricularCustomBinding.inflate(layoutInflater)
        dialog.setContentView(customDialogBinding.root)
        dialog.show()
        customDialogBinding.etTitle.setText(curricularEntity.title)
        customDialogBinding.etContent.setText(curricularEntity.content)
        customDialogBinding.btnAdd1.setOnClickListener {
            if (customDialogBinding.etTitle.text.isNullOrEmpty()) {
                customDialogBinding.etTitle.error = ""
            } else if (customDialogBinding.etContent.text.isNullOrEmpty()) {
                customDialogBinding.etContent.error = ""
            } else {
                var curri = CurricularEntity()
                curri.title = customDialogBinding.etTitle.text.toString()
                curri.content = customDialogBinding.etContent.text.toString()
                curri.pid = pid
                curricularEntityArray.set(position, curri)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }

    }

    override fun OnDeleteCurricularClicked(curricularEntity: CurricularEntity,position: Int) {

        AlertDialog.Builder(mainActivity).apply {
            setTitle(mainActivity.resources.getString(R.string.delete_curricular))
            setMessage(mainActivity.resources.getString(R.string.delete_curricular_msg))
            setPositiveButton(mainActivity.resources.getString(R.string.yes)) { _, _ ->
                curricularEntityArray.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            setNegativeButton(mainActivity.resources.getString(R.string.no)) { _, _ -> }
        }.show()

    }
    private fun saveCurricular() {
        class saveData: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                var response = mainActivity.resumeDatabase.resumeDao().insertAllCurricular(curricularEntityArray)
                System.out.println("response of saving personal info $response")
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                var bundle = Bundle()
                bundle.putInt("id",pid)
                findNavController().navigate(
                    R.id.action_CurricularActivity_to_skills,
                    bundle
                )
            }


        }
        saveData().execute()
    }


}