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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.resumemaker.adapters.SkillAdapter
import com.sk.resumemaker.databinding.*
import com.sk.resumemaker.entities.ExperienceEntity
import com.sk.resumemaker.entities.QualificationEntity
import com.sk.resumemaker.entities.SkillEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Skills.newInstance] factory method to
 * create an instance of this fragment.
 */
class Skills : Fragment(),SkillClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btnaddskill: Button
    lateinit var skillsBinding: FragmentSkillsBinding
    lateinit var customDialogBindingskill: DialogskillBinding
    lateinit var adapter: SkillAdapter
    lateinit var dialog: Dialog
    var skillEntityArray: ArrayList<SkillEntity> = ArrayList()
    lateinit var mainActivity: MainActivity
    var pid = 0
    var skillEntity = SkillEntity()


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
        skillsBinding = FragmentSkillsBinding.inflate(layoutInflater)
        return (skillsBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SkillAdapter(skillEntityArray, this)
        skillsBinding.rvskill.layoutManager = LinearLayoutManager(requireContext())
        skillsBinding.rvskill.adapter = adapter
        skillsBinding.btnaddskill.setOnClickListener {
            var dialog = Dialog(requireContext())
            customDialogBindingskill = DialogskillBinding.inflate(layoutInflater)
            dialog.setContentView(customDialogBindingskill.root)
            dialog.show()
            customDialogBindingskill.btnaddskill1.setOnClickListener {
                if (customDialogBindingskill.etskillname.text.isNullOrEmpty()) {
                    customDialogBindingskill.etskillname.error = ""
                } else if (customDialogBindingskill.etskilldescr.text.isNullOrEmpty()) {
                    customDialogBindingskill.etskilldescr.error = ""
                } else {
                    var skill = SkillEntity()
                    skill.skillname = customDialogBindingskill.etskillname.text.toString()
                    skill.description = customDialogBindingskill.etskilldescr.text.toString()
                    //primary key was not set added by Amanpreet
                    skill.pid =pid
                    skillEntityArray.add(skill)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }


        }
        skillsBinding.btnSaveskill.setOnClickListener {
            if (skillEntityArray.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Skill", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
          saveSkill()

        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Skills.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Skills().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnEditSkillClicked(skillEntity: SkillEntity, position: Int) {
        var dialog = Dialog(requireContext())
        customDialogBindingskill = DialogskillBinding.inflate(layoutInflater)
        dialog.setContentView(customDialogBindingskill.root)
        dialog.show()
        customDialogBindingskill.etskillname.setText(skillEntity.skillname)
        customDialogBindingskill.etskilldescr.setText(skillEntity.description)
        customDialogBindingskill.btnaddskill1.setOnClickListener {
            if (customDialogBindingskill.etskillname.text.isNullOrEmpty()) {
                customDialogBindingskill.etskillname.error = ""
            } else if (customDialogBindingskill.etskilldescr.text.isNullOrEmpty()) {
                customDialogBindingskill.etskilldescr.error = ""
            } else {
                var skill = SkillEntity()
                skill.skillname = customDialogBindingskill.etskillname.text.toString()
                skill.description = customDialogBindingskill.etskilldescr.text.toString()
                skill.pid = pid
                skillEntityArray.set(position, skill)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    }

    override fun OnDeleteSkillClicked(skillEntity: SkillEntity, position: Int) {

        AlertDialog.Builder(mainActivity).apply {
            setTitle(mainActivity.resources.getString(R.string.delete_skill))
            setMessage(mainActivity.resources.getString(R.string.delete_skill_msg))
            setPositiveButton(mainActivity.resources.getString(R.string.yes)) { _, _ ->
                skillEntityArray.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            setNegativeButton(mainActivity.resources.getString(R.string.no)) { _, _ -> }
        }.show()

    }

    private fun saveSkill() {
        class saveData : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                var response =
                    mainActivity.resumeDatabase.resumeDao().insertAllSkills(skillEntityArray)
                System.out.println("response of saving personal info $response")
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                var bundle = Bundle()
                bundle.putInt("id", pid)
                findNavController().navigate(
                    R.id.action_skills_to_referenceFragment,
                    bundle
                )
            }


        }
        saveData().execute()
    }
}

