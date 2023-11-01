package com.example.taskcdp.ui.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.taskcdp.R
import com.example.taskcdp.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var id: Int = -1
    private var image: String = ""

    private val REQUEST_CAMERA_PERMISSION_CODE = 3
    private val REQUEST_STORAGE_PERMISSION_CODE = 4

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickPictureLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    profileViewModel.profileUserState.collect {
                        it?.profile?.let {

                            id = it.id
                            image = it.image

                            binding.tvFullname.text = buildString {
                                append(getString(R.string.fullname))
                                append(it.firstName)
                                append(" ")
                                append(it.lastName)
                            }

                            binding.tvEmail.text = buildString {
                                append(getString(R.string.email))
                                append(it.email)
                            }

                            profileViewModel.updateProfileImage(id, image)

                            Glide.with(this@ProfileFragment)
                                .load(image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image
                                .apply(RequestOptions().centerCrop())
                                .into(binding.cvProfile)
                        }
                    }
                }
            }
        }

        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    saveImageToStorage(imageBitmap)
                }
            }
        pickPictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val imageUri = data?.data
                    imageUri?.let { uri ->
                        try {
                            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                            saveImageToStorage(bitmap)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    // Do something with the image Uri, such as display it in an ImageView
                }
            }
        binding.btnTakePicture.setOnClickListener {
            captureImage()
        }

        binding.btnPickPicture.setOnClickListener {
            pickImage()
        }

        binding.btLogout.setOnClickListener {

        }
    }

    private fun captureImage() {
        if (isCameraPermissionGranted()) {
            dispatchTakePictureIntent()
        } else {
            requestCameraPermission()
        }
    }

    private fun pickImage() {
        if (isStoragePermissionGranted()) {
            dispatchPickPictureIntent()
        } else {
            requestStoragePermission()
        }
    }

    // Launch camera
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)
                ?.also {
                    takePictureLauncher.launch(takePictureIntent)
                }
        }
    }

    fun saveBitmapToFile(context: Context, bitmap: Bitmap): String? {
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        var file: File? = null
        return try {
            file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            file.absolutePath // Return the absolute path of the saved file
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the exception here
            null
        } finally {
            file?.absolutePath
        }
    }

    // Save the image to storage (same as before)
    private fun saveImageToStorage(imageBitmap: Bitmap) {
        // Code to save the image to storage
        // Update the profile picture with the new image

        val image = saveBitmapToFile(requireContext(), imageBitmap)


        image?.let {
            profileViewModel.updateProfileImage(id, it)

            Glide.with(this@ProfileFragment)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image
                .apply(RequestOptions().centerCrop())
                .into(binding.cvProfile)
        }
    }

    private val cameraPermission = arrayOf(Manifest.permission.CAMERA)
    private val storagePermission =
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    // Check if the camera permission is granted
    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request camera permission
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            cameraPermission,
            REQUEST_CAMERA_PERMISSION_CODE
        )
    }

    // Check if the storage permission is granted
    private fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Request storage permission
    private fun requestStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                dispatchPickPictureIntent()
                // You already have the permission; perform your action here.
            }

            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                // Explain why you need the permission and then request it.

                // Provide an explanation for why you need the storage permission
                AlertDialog.Builder(requireContext())
                    .setTitle("Storage Permission Needed")
                    .setMessage("This app needs storage permission to save your files.")
                    .setPositiveButton("OK") { _, _ ->
                        // Request the permission after the user has acknowledged the explanation
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        // Handle the case if the user cancels the permission request
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }

            else -> {
                // You don't have the permission; request it directly.
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    // Override onRequestPermissionsResult to handle permission request results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent()
                } else {
                    showToast(R.string.please_grant_app_permissions)
                }
            }

            REQUEST_STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchPickPictureIntent()
                } else {
                    showToast(R.string.please_grant_app_permissions)
                }
            }
        }
    }

    private fun dispatchPickPictureIntent() {

        dispatchPickPictureIntentForAndroidBelow12()

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Perform your action here.
            } else {
                // Permission is denied. Handle the denied case here.
            }
        }

    // Dispatch pick picture intent for Android versions below 12
    private fun dispatchPickPictureIntentForAndroidBelow12() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        pickPictureLauncher.launch(intent)
    }

    private fun showToast(@StringRes errorString: Int) {
        Toast.makeText(activity?.applicationContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}