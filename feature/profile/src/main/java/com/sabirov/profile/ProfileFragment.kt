package com.sabirov.profile

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sabirov.core_api.entities.auth.User
import com.sabirov.core_impl.SessionKeeper
import com.sabirov.core_impl.network.Error
import com.sabirov.core_impl.network.Loading
import com.sabirov.core_impl.network.Success
import com.sabirov.profile.databinding.FrProfileBinding
import com.sabirov.resources.BundleArguments.Companion.ARG_USER_ID
import com.sabirov.utils.EditButtonClickListener
import com.sabirov.utils.argument
import com.sabirov.utils.registerOnBackPressedCallback
import com.sabirov.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FrProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var navigation: ProfileNavigation
    @Inject
    lateinit var sessionKeeper: SessionKeeper

    private var userId: Number by argument(ARG_USER_ID, null)

    private var editableUser: User? = null

    private val pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            val image = getFile(requireContext(), uri)
            image?.let {
                binding.ivAvatar.setImageURI(Uri.fromFile(it))
                viewModel.uploadAvatar(userId, it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FrProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerOnBackPressedCallback {
            //navigation.returnToCampings()
            navigation.onBackPressed(true)
        }
        binding.apply {
            toolbar.setNavigationOnClickListener {
                //navigation.returnToCampings()
                navigation.onBackPressed(true)
            }
            btnExit.setOnClickListener {
                sessionKeeper.setUserAccount(null)
                navigation.logOut()
            }

            setPhotoInteractions()

            val firstDrawable = com.sabirov.resources.R.drawable.ic_round_edit_24
            val secondDrawable = com.sabirov.resources.R.drawable.ic_round_done_24

            btnEditSurname.setOnClickListener(EditButtonClickListener(
                firstDrawable,
                secondDrawable,
                etSurname
            ) {
                editableUser = editableUser?.copy(lastName = etSurname.text.toString())
                editableUser?.let { viewModel.updateProfile(userId, it) }
            })

            btnEditName.setOnClickListener(EditButtonClickListener(
                firstDrawable,
                secondDrawable,
                etName
            ) {
                editableUser = editableUser?.copy(firstName = etName.text.toString())
                editableUser?.let { viewModel.updateProfile(userId, it) }
            })

            btnEditPatronymic.setOnClickListener(EditButtonClickListener(
                firstDrawable,
                secondDrawable,
                etPatronymic
            ) {
                editableUser = editableUser?.copy(patronymic = etPatronymic.text.toString())
                editableUser?.let { viewModel.updateProfile(userId, it) }
            })

            btnEditPhone.setOnClickListener(EditButtonClickListener(
                firstDrawable,
                secondDrawable,
                etPhone
            ) {
                if (etPhone.rawText.length == 10) {
                    tilPhone.isErrorEnabled = false
                    editableUser = editableUser?.copy(phoneNumber = etPhone.rawText)
                    editableUser?.let { viewModel.updateProfile(userId, it) }
                } else {
                    tilPhone.error = getString(com.sabirov.resources.R.string.wrong_phone)
                }
            })

            btnEditEmail.setOnClickListener(EditButtonClickListener(
                firstDrawable,
                secondDrawable,
                etEmail
            ) {
                if (Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString())
                        .matches() || etEmail.text.toString().isEmpty()
                ) {
                    tilEmail.isErrorEnabled = false
                    editableUser = editableUser?.copy(email = etEmail.text.toString())
                    editableUser?.let { viewModel.updateProfile(userId, it) }
                } else {
                    tilEmail.error = getString(com.sabirov.resources.R.string.wrong_email)
                }
            })

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.loadingState.collect { state ->
                    when (state) {
                        is Loading,
                        null -> {
                            placeholderProgressView.visible(true)
                            clContainer.visible(false)
                            placeholderProgressView.startShimmer()
                        }

                        is Error -> {

                        }

                        is Success -> {
                            (state.item as? User)?.let {
                                editableUser = it.copy()
                                etSurname.setText(it.lastName)
                                etName.setText(it.firstName)
                                etPatronymic.setText(it.patronymic)
                                etPhone.setText(it.phoneNumber)
                                etEmail.setText(it.email)
                                Glide.with(requireContext())
                                    .load(it.photoUrl)
                                    .placeholder(com.sabirov.resources.R.drawable.ic_avatar)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .transform(CircleCrop())
                                    .addListener(object : RequestListener<Drawable>{
                                        override fun onLoadFailed(
                                            e: GlideException?,
                                            model: Any?,
                                            target: Target<Drawable>?,
                                            isFirstResource: Boolean
                                        ): Boolean {
                                            println()
                                            return true
                                        }

                                        override fun onResourceReady(
                                            resource: Drawable?,
                                            model: Any?,
                                            target: Target<Drawable>?,
                                            dataSource: DataSource?,
                                            isFirstResource: Boolean
                                        ): Boolean {
                                            ivAvatar.setImageDrawable(resource)
                                            return true
                                        }
                                    })
                                    .into(ivAvatar)
                            }
                            placeholderProgressView.stopShimmer()
                            placeholderProgressView.visible(false)
                            clContainer.visible(true)
                        }
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.isUploaded.collect {
                    if (it) viewModel.getProfile(userId)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile(userId)
    }

    private fun setPhotoInteractions() {
        binding.apply {
            clAvatar.setOnClickListener {
                if (clAddPhoto.isVisible) {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                } else {
                    clAddPhoto.animate().alpha(1f).setListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {
                            clAddPhoto.visible(true)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            MainScope().launch {
                                delay(1500)
                                clAddPhoto.animate().alpha(0f)
                                    .setListener(object : Animator.AnimatorListener {
                                        override fun onAnimationStart(animation: Animator) {}

                                        override fun onAnimationEnd(animation: Animator) {
                                            clAddPhoto.visible(false)
                                        }

                                        override fun onAnimationCancel(animation: Animator) {}

                                        override fun onAnimationRepeat(animation: Animator) {}

                                    }).start()
                            }
                        }

                        override fun onAnimationCancel(animation: Animator) {}

                        override fun onAnimationRepeat(animation: Animator) {}

                    }).start()
                }
            }

        }
    }

    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri): File? {
        val destinationFilename =
            File(context.filesDir.path + File.separatorChar + queryName(context, uri))
        try {
            context.contentResolver.openInputStream(uri).use { ins ->
                createFileFromStream(
                    ins!!,
                    destinationFilename
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return destinationFilename
    }

    private fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun queryName(context: Context, uri: Uri): String {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }
}