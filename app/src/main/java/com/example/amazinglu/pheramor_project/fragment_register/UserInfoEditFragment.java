package com.example.amazinglu.pheramor_project.fragment_register;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazinglu.pheramor_project.BuildConfig;
import com.example.amazinglu.pheramor_project.MainActivity;
import com.example.amazinglu.pheramor_project.R;
import com.example.amazinglu.pheramor_project.base.BaseFragment;
import com.example.amazinglu.pheramor_project.model.User;
import com.example.amazinglu.pheramor_project.utils.ImageUtil;
import com.example.amazinglu.pheramor_project.utils.InputUtil;
import com.example.amazinglu.pheramor_project.utils.PermissionUtil;
import com.example.amazinglu.pheramor_project.utils.ViewUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoEditFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.user_name_layout) TextInputLayout userNameLayout;
    @BindView(R.id.user_zip_code_layout) TextInputLayout userZipcodeLayout;
    @BindView(R.id.user_height_layout) TextInputLayout userHeightLayout;
    @BindView(R.id.height_unit_of_measurement) AppCompatSpinner heightUnitChooser;
    @BindView(R.id.next_button_two) Button nextButton;
    @BindView(R.id.user_image) ImageView userImage;
    @BindView(R.id.user_image_cover) View userImageCover;

    private static final int REQ_CODE_PICK_IMAGE = 202;
    private static final int REQ_CODE_TAKE_PICTURE = 203;

    private User user;
    private String userName;
    private String zipCode;
    private double height;
    private String heightStr;
    private String heightMeasureUnit;

    private String mCurrentPhotoPath;

    private AppCompatEditText editTextUserName;
    private AppCompatEditText editTextZipCode;
    private AppCompatEditText editTextHeight;

    public static UserInfoEditFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.KEY_USER, user);
        UserInfoEditFragment fragment = new UserInfoEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_zipcode_height, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        user = getArguments().getParcelable(MainActivity.KEY_USER);

        editTextUserName = (AppCompatEditText) userNameLayout.getEditText();
        editTextZipCode = (AppCompatEditText) userZipcodeLayout.getEditText();
        editTextHeight = (AppCompatEditText) userHeightLayout.getEditText();

        // setup the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.height_unit_chooser, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightUnitChooser.setAdapter(adapter);
        heightUnitChooser.setVisibility(View.VISIBLE);
        heightUnitChooser.setOnItemSelectedListener(this);

        if (user.name != null) {
            userName = user.name;
            editTextUserName.setText(user.name);
        }
        if (user.zipCode != null) {
            zipCode = user.zipCode;
            editTextZipCode.setText(user.zipCode);
        }
        if (user.height != User.HEIGHT_DEFAULT_VALUE) {
            height = user.height;
            heightMeasureUnit = user.heightMeasureUnit;
            editTextHeight.setText(Double.toString(user.height));
            ViewUtil.setSpinText(heightUnitChooser, user.heightMeasureUnit);
        }
        if (user.userImageUrl != null) {
            ImageUtil.loadImageLocal(getContext(), user.userImageUrl, userImage);
        }

        setUpUserImageSelectListener();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput();
                if (validate()) {
                    user.name = userName;
                    user.zipCode = zipCode;
                    user.height = height;
                    user.heightMeasureUnit = heightMeasureUnit;
                    Toast.makeText(getContext(), "input is valid", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, GenderAndDobFragment.newInstance(user))
                            .commit();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                loadImage(imageUri);
            }
        }

        if (requestCode == REQ_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
                userImage.setImageBitmap(BitmapFactory.decodeStream(ims));
            } catch (FileNotFoundException e) {
                return;
            }

            user.userImageUrl = imageUri;

            // ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // TODO: what to do when get the permission
        if (requestCode == PermissionUtil.REQ_CODE_READ_EXTERNAL_STORAGE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, EmailAndPassWordEditFragment.newInstance(user))
                        .addToBackStack(null)
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserInput() {
        userName = editTextUserName.getText().toString();
        zipCode = editTextZipCode.getText().toString();
        heightStr = editTextHeight.getText().toString();
        if (!heightStr.isEmpty()) {
            height = Double.parseDouble(editTextHeight.getText().toString());
        }
    }

    private boolean validate() {
        editTextUserName.setError(null);
        editTextZipCode.setError(null);
        editTextHeight.setError(null);
        if (InputUtil.isEmpty(userName)) {
            editTextUserName.setError(
                    getActivity().getResources().getString(R.string.user_name_empty_warning));
            return false;
        }

        if (InputUtil.isEmpty(zipCode)) {
            editTextZipCode.setError(
                    getActivity().getResources().getString(R.string.zipcode_empty_warning));
            return false;
        }

        if (InputUtil.isEmpty(heightStr)) {
            editTextHeight.setError(
                    getActivity().getResources().getString(R.string.height_empty_warning));
            return false;
        } else if (!InputUtil.isHeightValid(heightStr)) {
            editTextHeight.setError(
                    getActivity().getResources().getString(R.string.height_not_valid_warning));
            return false;
        }
        return true;
    }

    private void setUpUserImageSelectListener() {
        userImageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    // TODO: dialog to choose whether pick up a image of take photo
//                    pickImage();
                    try {
                        takePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean checkPermission() {
        boolean needReadExternalPermission = false;
        boolean needWriteExternalPermission = false;
        boolean needCameraPermission = false;
        if (!PermissionUtil.checkPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            needReadExternalPermission = true;
        }
        if (!PermissionUtil.checkPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            needWriteExternalPermission = true;
        }
        if (!PermissionUtil.checkPermission(getContext(), Manifest.permission.CAMERA)) {
            needCameraPermission = true;
        }

        if (needReadExternalPermission) {
            PermissionUtil.requestReadExternalStoragePermission(getActivity());
        }
        if (needWriteExternalPermission) {
            PermissionUtil.requestWriteExternalStoragePermission(getActivity());
        }
        if (needCameraPermission) {
            PermissionUtil.requestCameraPermission(getActivity());
        }

        return !needCameraPermission && !needReadExternalPermission && !needWriteExternalPermission;
    }

    /**
     * pick an image from phone
     * */
    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_CODE_PICK_IMAGE);
    }

    /**
     * user camera to take profile photo
     * */
    private void takePhoto() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider", createImageFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQ_CODE_TAKE_PICTURE);
            }
        }
    }

    private File createImageFile() throws IOException {

        // create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void loadImage(Uri imageUri) {
        userImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Uri localUri =  ImageUtil.loadImage(getContext(), imageUri, userImage);
        userImage.setTag(localUri);
        user.userImageUrl = localUri;
    }

    /**
     * OnItemSelectedListener of spinner
     * */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        heightMeasureUnit = ((TextView)view).getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
