package com.sk.resumemaker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.encodeToString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.room.RoomDatabase
import com.sk.resumemaker.databinding.PersonalinfofragmentBinding
import com.sk.resumemaker.entities.PersonalInfoEntity
import com.sk.resumemaker.room.ResumeDatabase
import java.io.ByteArrayOutputStream
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonalInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var BindingpersonalInfo: PersonalinfofragmentBinding
    lateinit var etfirstname: EditText
    lateinit var etlastname: EditText
    lateinit var etemail: EditText
    lateinit var etphone: EditText
    lateinit var etaddress: EditText
    lateinit var etnationality: EditText
    lateinit var rggender: RadioGroup
    lateinit var rbfemale: RadioButton
    lateinit var rbmale: RadioButton
    lateinit var btnsavepersonalinfo: Button
     var pid = 0
    lateinit var mainActivity: MainActivity
    var imageUri: Uri? = null
    var personalInfoEntity :PersonalInfoEntity = PersonalInfoEntity()
    var bitmap: Bitmap? = null



    var getPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted->
        if (isGranted){
            Toast.makeText(requireContext(),"Granted",Toast.LENGTH_SHORT).show()
            getImage.launch("image/*")
        }else{
            Toast.makeText(requireContext(),"Not Granted",Toast.LENGTH_SHORT).show()
        }
    }
    var getImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        System.out.println("it $it")
        it?.let {
            imageUri = it
            bitmap = MediaStore.Images.Media.getBitmap(mainActivity.contentResolver, it)
            BindingpersonalInfo.ivcamera.setImageBitmap(bitmap)
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
        BindingpersonalInfo = PersonalinfofragmentBinding.inflate(layoutInflater)
        BindingpersonalInfo.ivcamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    READ_EXTERNAL_STORAGE
                )== PackageManager.PERMISSION_GRANTED)
            {
                getImage.launch("image/*")
            }
            else
                getPermission.launch(READ_EXTERNAL_STORAGE)
        }


        BindingpersonalInfo.btnsavepersonalinfo.setOnClickListener {
            if (BindingpersonalInfo.etfirstname.text.isNullOrEmpty()) {
                BindingpersonalInfo.etfirstname.error = resources.getString(R.string.please_enter_FirstName)
                BindingpersonalInfo.etfirstname.requestFocus()
            } else if (BindingpersonalInfo.etlastname.text.isNullOrEmpty()) {
                BindingpersonalInfo.etlastname.error = resources.getString(R.string.please_enter_LastName)
                BindingpersonalInfo.etlastname.requestFocus()

            } else if (BindingpersonalInfo.etemail.text.isNullOrEmpty()) {
                BindingpersonalInfo.etemail.error = resources.getString(R.string.please_enter_email)
                BindingpersonalInfo.etemail.requestFocus()
            } else if (BindingpersonalInfo.etphone.text.isNullOrEmpty()) {
                BindingpersonalInfo.etphone.error = resources.getString(R.string.please_enter_phone)
                BindingpersonalInfo.etphone.requestFocus()
            } else if (BindingpersonalInfo.etaddress.text.isNullOrEmpty()) {
                BindingpersonalInfo.etaddress.error = resources.getString(R.string.please_enter_address)
                BindingpersonalInfo.etaddress.requestFocus()
            } else if (BindingpersonalInfo.etnationality.text.isNullOrEmpty()) {
                BindingpersonalInfo.etnationality.error = resources.getString(R.string.please_enter_nationality)
                BindingpersonalInfo.etnationality.requestFocus()
            }
            else{
                class insert: AsyncTask<Void, Void, Void>(){
                    override fun doInBackground(vararg p0: Void?): Void? {
                        var personalInfoEntity = PersonalInfoEntity()
                        personalInfoEntity.firstName = BindingpersonalInfo.etfirstname.text.toString()
                        personalInfoEntity.lastName = BindingpersonalInfo.etlastname.text.toString()
                        personalInfoEntity.email = BindingpersonalInfo.etemail.text.toString()
                        personalInfoEntity.phone = BindingpersonalInfo.etphone.text.toString()
                        personalInfoEntity.address = BindingpersonalInfo.etaddress.text.toString()
                        personalInfoEntity.nationality = BindingpersonalInfo.etnationality.text.toString()
                        if(BindingpersonalInfo.rbfemale.isChecked){
                            personalInfoEntity.gender = "female"
                        }else if(BindingpersonalInfo.rbmale.isChecked){
                            personalInfoEntity.gender = "male"
                        }
                        if (bitmap != null){
                            personalInfoEntity.image = encodeTobase64(bitmap!!)
                        }
                        var returnValue = ResumeDatabase.getDatabase(mainActivity).resumeDao().insertPersonalEntity(personalInfoEntity)

                        System.out.println("returnValue $returnValue")

                        pid = returnValue.toInt()
                        return null
                    }

                     fun encodeTobase64(image: Bitmap): String? {
                        val baos = ByteArrayOutputStream()
                        image.compress(Bitmap.CompressFormat.PNG,100,baos)
                        val b: ByteArray = baos.toByteArray()
                        val imageEncoded: String = Base64.encodeToString(b, android.util.Base64.DEFAULT)
                        return imageEncoded

                    }
                     fun decodeBase64(input:String?): Bitmap?{
                         val decodedByte: ByteArray = Base64.decode(input,0)
                         return BitmapFactory.decodeByteArray(decodedByte,0,decodedByte.size)
                     }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        var bundle = Bundle()
                        bundle.putInt("id", pid)
                        findNavController().navigate(
                            R.id.action_personalnfoFragment_to_qualification,
                            bundle
                        )
                    }
                }
                insert().execute()
            }
        }
        // Inflate the layout for this fragment
        return (BindingpersonalInfo.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
            PersonalInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
