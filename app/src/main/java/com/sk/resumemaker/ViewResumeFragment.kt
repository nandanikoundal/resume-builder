package com.sk.resumemaker

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.adapters.*
import com.sk.resumemaker.databinding.CustomDialogBinding
import com.sk.resumemaker.databinding.FragmentViewResumeBinding
import com.sk.resumemaker.databinding.QualificationBinding
import com.sk.resumemaker.entities.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewResumeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewResumeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var resumes: DisplayResumes
    lateinit var binding1: FragmentViewResumeBinding
    lateinit var mainActivity: MainActivity
    var qualificationArray = ArrayList<QualificationEntity>()
    lateinit var adapter: ShowQualificationAdapter
    var experienceArray = ArrayList<ExperienceEntity>()
    lateinit var adapter1: ShowExperienceAdapter
    var skillArray = ArrayList<SkillEntity>()
    lateinit var adapter2:ShowSkillAdapter
    var currricularArray = ArrayList<CurricularEntity>()
    lateinit var adapter3:ShowCurricularAdapter
    var referenceArray = ArrayList<ReferenceEntity>()
    lateinit var adapter4:ShowReferenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            resumes = it.getSerializable("resume") as DisplayResumes
            qualificationArray.addAll(resumes.qualificationEntity ?: ArrayList())
            experienceArray.addAll(resumes.experienceEntity ?: ArrayList())
            skillArray.addAll(resumes.skillEntity ?: ArrayList())
            currricularArray.addAll(resumes.curricularEntity ?: ArrayList())
            referenceArray.addAll(resumes.referenceEntity ?: ArrayList())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding1 = FragmentViewResumeBinding.inflate(layoutInflater)
        binding1.etfirstname.setText(resumes.personalInfoEntity?.firstName)
        binding1.etlastname.setText(resumes.personalInfoEntity?.lastName)
        binding1.etemail.setText(resumes.personalInfoEntity?.email)
        binding1.etphone.setText(resumes.personalInfoEntity?.phone)
        binding1.etaddress.setText(resumes.personalInfoEntity?.address)
        binding1.etnationality.setText(resumes.personalInfoEntity?.nationality)
        if (resumes.personalInfoEntity?.gender == "female") {
            binding1.etGender.setText(mainActivity.resources.getString(R.string.female))
        } else {
            binding1.etGender.setText(mainActivity.resources.getString(R.string.male))

        }
        return binding1.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ShowQualificationAdapter(qualificationArray)

        binding1.rvQualification.layoutManager = LinearLayoutManager(requireContext())
        binding1.rvQualification.adapter = adapter

    adapter1=ShowExperienceAdapter(experienceArray)
       binding1.rvexperie.layoutManager=LinearLayoutManager(requireContext())
        binding1.rvexperie.adapter=adapter1

        adapter2=ShowSkillAdapter(skillArray)
        binding1.rvskill.layoutManager=LinearLayoutManager(requireContext())
        binding1.rvskill.adapter=adapter2

        adapter3=ShowCurricularAdapter(currricularArray)
        binding1.rvcurricular.layoutManager=LinearLayoutManager(requireContext())
        binding1.rvcurricular.adapter=adapter3

        adapter4=ShowReferenceAdapter(referenceArray)
        binding1.rvreference.layoutManager=LinearLayoutManager(requireContext())
        binding1.rvreference.adapter=adapter4

}







        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment ViewResumeFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                ViewResumeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }



}