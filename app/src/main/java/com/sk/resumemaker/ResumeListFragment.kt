package com.sk.resumemaker

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.sk.resumemaker.adapters.AddedResumeAdapter
import com.sk.resumemaker.databinding.FragmentResumeListBinding
import com.sk.resumemaker.entities.DisplayResumes
import java.io.FileOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResumeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResumeListFragment : Fragment(), ResumeListClicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentResumeListBinding
    lateinit var mainActivity: MainActivity
    var resumeList : ArrayList<DisplayResumes> = ArrayList()
    var selectedResume : DisplayResumes = DisplayResumes()
    lateinit var  adapter : AddedResumeAdapter

    var getPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
            isGranted ->
        if (isGranted) {
            printResume(selectedResume)

        } else {
            Toast.makeText(mainActivity, "Not Granted", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResumeListBinding.inflate(layoutInflater)
        adapter = AddedResumeAdapter(resumeList, this)
        binding.rvResume.adapter = adapter
        binding.rvResume.layoutManager = LinearLayoutManager(mainActivity)
        getList()

        binding.fabAdd.setOnClickListener {
            mainActivity.navController.navigate(R.id.personalnfoFragment)
        }
        return binding.root
    }

    private fun getList() {
        resumeList.clear()
        class getResumes: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                resumeList.addAll(mainActivity.resumeDatabase.resumeDao().getResumeList())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter.notifyDataSetChanged()
            }
        }
        getResumes().execute()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResumeListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResumeListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnViewClicked(resumes: DisplayResumes, position: Int) {
        //create a fragment to view the complete entered resume and pass resume as an argument
        var bundle = Bundle()
        bundle.putSerializable("resume", resumes)
        mainActivity.navController.navigate(R.id.viewResumeFragment, bundle)
    }

    override fun OnPrintClicked(resumes: DisplayResumes, position: Int) {
        selectedResume = resumes
        if (ContextCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            printResume(resumes)
        } else
            getPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }

    fun printResume(resumes: DisplayResumes){
        val mDoc = Document()
        //pdf file path
        val mFilePath =
            Environment.getExternalStorageDirectory()
                .toString() + "/" + "${resumes?.personalInfoEntity?.firstName.toString()}" + ".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            val table = PdfPTable(1)

            //open the document for writing
            mDoc.open()
            val font = Font()
            font.isBold
            font.color = BaseColor.WHITE
            font.size = 48f

            val cell = PdfPCell(Phrase("Resume", font))
            cell.backgroundColor = BaseColor.RED
            //get text from EditText i.e. textEt
            table.addCell(cell)

            val cellSpace1 = PdfPCell(Phrase(" "))
            cellSpace1.borderColor = BaseColor.WHITE
            table.addCell(cellSpace1)


            val cell1 = PdfPCell(Phrase("PERSONAL DETAILS"))
            cell1.backgroundColor = BaseColor.RED
            table.addCell(cell1)
            table.getDefaultCell().setBorderWidth(0f)
            table.addCell("Name: " + resumes?.personalInfoEntity?.firstName+resumes?.personalInfoEntity?.lastName)
            table.getDefaultCell().setBorderWidth(0f)
            table.addCell("Email: " + resumes?.personalInfoEntity?.email)
            table.getDefaultCell().setBorderWidth(0f)
            table.addCell("Mobile: " + resumes?.personalInfoEntity?.phone)
            table.getDefaultCell().setBorderWidth(0f)
            table.addCell("Nation: " + resumes?.personalInfoEntity?.nationality)
            table.getDefaultCell().setBorderWidth(0f)
            table.addCell("Address: " + resumes?.personalInfoEntity?.address)
            table.getDefaultCell().setBorderWidth(0f)
            table.addCell("Gender: " + resumes?.personalInfoEntity?.gender)


            val cellSpace2 = PdfPCell(Phrase(" "))
            cellSpace2.borderColor = BaseColor.WHITE
            table.addCell(cellSpace2)


            val cell2 = PdfPCell(Phrase("QUALIFICATION"))
            cell2.backgroundColor = BaseColor.RED
            table.addCell(cell2)

           for(items in resumes?.qualificationEntity!!){
               table.getDefaultCell().setBorderWidth(0f)
               table.addCell("Course/Degree: " + items.course)
               table.getDefaultCell().setBorderWidth(0f)
               table.addCell("School/University: " + items.school)
               table.getDefaultCell().setBorderWidth(0f)
               table.addCell("Grade/Score: " + items.grade)
               table.getDefaultCell().setBorderWidth(0f)
               table.addCell("Year: " + items.year)
           }

            val cellSpace3 = PdfPCell(Phrase(" "))
            cellSpace3.borderColor = BaseColor.WHITE
            table.addCell(cellSpace3)


            val cell3 = PdfPCell(Phrase("EXPERIENCE"))
            cell3.backgroundColor = BaseColor.RED
            table.addCell(cell3)

            for(items in resumes?.experienceEntity!!){
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Job Title: " + items.jobtitle)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Company Name: " + items.companyname)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Country Name: " + items.countryname)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Details: " + items.details)
            }

            val cellSpace4 = PdfPCell(Phrase(" "))
            cellSpace4.borderColor = BaseColor.WHITE
            table.addCell(cellSpace4)


            val cell4 = PdfPCell(Phrase("CURRICULAR ACTIVITY"))
            cell4.backgroundColor = BaseColor.RED
            table.addCell(cell4)

            for(items in resumes?.curricularEntity!!){
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Title: " + items.title)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Content: " + items.content)

            }

            val cellSpace5 = PdfPCell(Phrase(" "))
            cellSpace5.borderColor = BaseColor.WHITE
            table.addCell(cellSpace5)


            val cell5 = PdfPCell(Phrase("SKILLS"))
            cell5.backgroundColor = BaseColor.RED
            table.addCell(cell5)

            for(items in resumes?.skillEntity!!){
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Skill Name: " + items.skillname)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Description: " + items.description)

            }

            val cellSpace6 = PdfPCell(Phrase(" "))
            cellSpace6.borderColor = BaseColor.WHITE
            table.addCell(cellSpace6)


            val cell6 = PdfPCell(Phrase("REFERENCE"))
            cell6.backgroundColor = BaseColor.RED
            table.addCell(cell6)

            for(items in resumes?.referenceEntity!!){
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Refree's Name: " + items.referee)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Job Title: " + items.job)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Company name: " + items.company)
                table.getDefaultCell().setBorderWidth(0f)
                table.addCell("Email: " + items.email)
            }



            //add author of the document (metadata)
            mDoc.addAuthor(mainActivity.resources.getString(R.string.app_name))

            //add paragraph to the document
            mDoc.add(table)

            //close document
            mDoc.close()

            //show file saved message with file name and path
            Toast.makeText(
                requireActivity(),
                "${resumes?.personalInfoEntity?.firstName.toString()}.pdf\nis saved in\n$mFilePath",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            System.out.println("e.message ${e.message}")
            //if anything goes wrong causing exception, get and show exception message
            Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun OnDeleteClicked(resumes: DisplayResumes, position: Int) {
        AlertDialog.Builder(mainActivity).apply {
            setTitle(mainActivity.resources.getString(R.string.delete_resume))
            setMessage(mainActivity.resources.getString(R.string.delete_resume_msg))
            setPositiveButton(mainActivity.resources.getString(R.string.yes)){_,_->
                class deleteResumes: AsyncTask<Void, Void, Void>(){
                    override fun doInBackground(vararg p0: Void?): Void? {
                        resumes.personalInfoEntity?.let {
                            mainActivity.resumeDatabase.resumeDao().deleteResume(
                                it
                            )
                        }
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        getList()
                    }
                }
                deleteResumes().execute()
            }
            setNegativeButton(mainActivity.resources.getString(R.string.no)){_,_->}
        }.show()
    }

}