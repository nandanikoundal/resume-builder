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
import com.sk.resumemaker.adapters.ReferenceAdapter
import com.sk.resumemaker.databinding.FragmentReferenceBinding
import com.sk.resumemaker.databinding.ReferenceCustomBinding
import com.sk.resumemaker.entities.ReferenceEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReferenceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReferenceFragment : Fragment(), ReferenceClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var referenceCustomBinding: ReferenceCustomBinding
    lateinit var binding: FragmentReferenceBinding
    lateinit var dialog: Dialog
    lateinit var etRef: EditText
    lateinit var etJob: EditText
    lateinit var etCompName: EditText
    lateinit var etEmail: EditText
    lateinit var btnAdd4: Button
    lateinit var btnAddReference: Button
    lateinit var adapter: ReferenceAdapter
    lateinit var mainActivity: MainActivity
    var referenceEntityArray: ArrayList<ReferenceEntity> = ArrayList()
    var referenceEntity = ReferenceEntity()
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
        binding = FragmentReferenceBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ReferenceAdapter(referenceEntityArray, this)
        binding.rvreference.layoutManager = LinearLayoutManager(requireContext())
        binding.rvreference.adapter = adapter
        binding.btnAddReference.setOnClickListener {
            var dialog = Dialog(requireContext())
            referenceCustomBinding = ReferenceCustomBinding.inflate(layoutInflater)
            dialog.setContentView(referenceCustomBinding.root)
            dialog.show()
            referenceCustomBinding.btnAdd4.setOnClickListener {
                if (referenceCustomBinding.etRef.text.isNullOrEmpty()) {
                    referenceCustomBinding.etRef.error = ""
                } else if (referenceCustomBinding.etJob.text.isNullOrEmpty()) {
                    referenceCustomBinding.etJob.error = ""
                } else if (referenceCustomBinding.etCompName.text.isNullOrEmpty()) {
                    referenceCustomBinding.etCompName.error = ""
                } else if (referenceCustomBinding.etEmail.text.isNullOrEmpty()) {
                    referenceCustomBinding.etEmail.error = ""
                } else {
                    var ref = ReferenceEntity()
                    ref.referee = referenceCustomBinding.etRef.text.toString()
                    ref.job = referenceCustomBinding.etJob.text.toString()
                    ref.company = referenceCustomBinding.etCompName.text.toString()
                    ref.email = referenceCustomBinding.etEmail.text.toString()
                    ref.pid = pid
                    referenceEntityArray.add(ref)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
        binding.btnSaveRef.setOnClickListener {
            if (referenceEntityArray.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Reference", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                saveReference()
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
         * @return A new instance of fragment ReferenceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReferenceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun OnEditReferenceClicked(
        referenceEntity: ReferenceEntity,
        position: Int
    ){
        var dialog = Dialog(requireContext())
        referenceCustomBinding = ReferenceCustomBinding.inflate(layoutInflater)
        dialog.setContentView(referenceCustomBinding.root)
        dialog.show()
        referenceCustomBinding.etRef.setText(referenceEntity.referee)
        referenceCustomBinding.etJob.setText(referenceEntity.job)
        referenceCustomBinding.etCompName.setText(referenceEntity.company)
        referenceCustomBinding.etEmail.setText(referenceEntity.email)
        referenceCustomBinding.btnAdd4.setOnClickListener {
            if (referenceCustomBinding.etRef.text.isNullOrEmpty()) {
                referenceCustomBinding.etRef.error = ""
            } else if (referenceCustomBinding.etJob.text.isNullOrEmpty()) {
                referenceCustomBinding.etJob.error = ""
            } else if (referenceCustomBinding.etCompName.text.isNullOrEmpty()) {
                referenceCustomBinding.etCompName.error = ""
            } else if (referenceCustomBinding.etEmail.text.isNullOrEmpty()) {
                referenceCustomBinding.etEmail.error = ""
            } else {
                var ref = ReferenceEntity()
                ref.referee = referenceCustomBinding.etRef.text.toString()
                ref.job = referenceCustomBinding.etJob.text.toString()
                ref.company = referenceCustomBinding.etCompName.text.toString()
                ref.email = referenceCustomBinding.etEmail.text.toString()
                ref.pid = pid
                referenceEntityArray.set(position, ref)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
}
    override fun OnDeleteReferenceClicked(referenceEntity: ReferenceEntity, position: Int){

        AlertDialog.Builder(mainActivity).apply {
            setTitle(mainActivity.resources.getString(R.string.delete_reference))
            setMessage(mainActivity.resources.getString(R.string.delete_reference_msg))
            setPositiveButton(mainActivity.resources.getString(R.string.yes)){_,_->
                referenceEntityArray.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            setNegativeButton(mainActivity.resources.getString(R.string.no)){_,_->}
        }.show()
        }

    private fun saveReference(){
        class saveData: AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                var response = mainActivity.resumeDatabase.resumeDao().insertAllReferences(referenceEntityArray)
                System.out.println("response of saving personal info $response")
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                var bundle = Bundle()
                bundle.putInt("id",pid)
                mainActivity.navController.navigate(R.id.createviewfragment)

            }

        }
        saveData().execute()

    }
}