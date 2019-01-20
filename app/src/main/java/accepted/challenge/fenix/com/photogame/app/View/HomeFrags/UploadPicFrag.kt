package accepted.challenge.fenix.com.photogame.app.View.HomeFrags


import accepted.challenge.fenix.com.photogame.Domain.*
import accepted.challenge.fenix.com.photogame.R
import accepted.challenge.fenix.com.photogame.app.Models.RemoteGameUploadDetails
import accepted.challenge.fenix.com.photogame.app.ViewModel.GamingViewModel
import accepted.challenge.fenix.com.photogame.app.ViewModel.ViewModelFactory.GamingViewModelFactory
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_upload_pic.*
import kotlinx.android.synthetic.main.fragment_upload_pic.view.*
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 *
 */
class UploadPicFrag : Fragment() {
    private val captureImage = 1
    private var imageString: String? = null
    private lateinit var gamingViewModel: GamingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFrag()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_upload_pic, container, false)
        view.cameraId.setOnClickListener { dispatchTakePictureIntent() }
        view.uploadPic.setOnClickListener { uploadPicData() }
        view.category.setOnDropDownItemClickListener { _, _, id ->
            view
            view.category.text = view.category.dropdownData[id]
        }
        requireContext().toast(getString(R.string.tap_camera))
        return view
    }

    @SuppressLint("LogNotTimber")
    private fun uploadPicData() {
        val captionText = caption.text.toString()
        val descriptionText = description.text.toString()
        val categoryText = category.text.toString()
        val locationText = location.text.toString()

        if (imageString != null && captionText.isNotBlank()) {
            val gamingModel =
                    RemoteGameUploadDetails(imageString!!,
                            captionText,
                            descriptionText,
                            categoryText,
                            locationText)

            requireContext().toast(getString(R.string.uploading))

            Intent(requireContext(), UploadService::class.java).also { intent ->
                intent.putExtra(MODEL_DATA_KEY, gamingModel)
                requireContext().startService(intent)
            }
        } else requireContext().toast(ErrorMessages.MISSING_DATA.name)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context?.packageManager)?.also {
                startActivityForResult(takePictureIntent, captureImage)
            }
        }
    }

    private fun setupFrag() {
        val gamingFactory = GamingViewModelFactory(requireContext())
        gamingViewModel = ViewModelProviders
                .of(this, gamingFactory)
                .get(GamingViewModel::class.java)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == captureImage && resultCode == RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            cameraId.setImageBitmap(imageBitmap)

            val byteArrayOS = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS)

            val byteImage = byteArrayOS.toByteArray()
            imageString = Base64.encodeToString(byteImage, Base64.DEFAULT)
        }
    }
}
