package com.example.melaniez.photoapp.Profile.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.melaniez.photoapp.Models.User;
import com.example.melaniez.photoapp.Models.UserPrivateInfo;
import com.example.melaniez.photoapp.R;
import com.example.melaniez.photoapp.Utils.MyImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements ReAuthenticateDialog.OnConfirmButtonClickedListener {
    private static final String TAG = "EditProfileActivity";

    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private User mUser;
    private UserPrivateInfo mUserPrivateInfo;
    private boolean mUsernameUnique = true;

    @BindView(R.id.edit_profile_photo) CircleImageView mProfilePhoto;
    @BindView(R.id.editText) EditText mName;
    @BindView(R.id.editText2) EditText mUsername;
    @BindView(R.id.editText3) EditText mWebsite;
    @BindView(R.id.editText4) EditText mBio;

    @BindView(R.id.editText5) EditText mEmail;
    @BindView(R.id.editText6) EditText mPhone;
    @BindView(R.id.editText7) EditText mGender;


    @OnClick(R.id.cancel_button) public void cancelChanges() {
        finish();
    }
    @OnClick(R.id.done_button) public void saveChanges() {
        if (!mUsernameUnique) {
            Toast.makeText(mContext, "The username must be unique.", Toast.LENGTH_SHORT).show();
            return;
        }
        // update user info
        writeToUserInfo();
        if (!mEmail.getText().toString().equals(mUserPrivateInfo.getEmail())){
            new ReAuthenticateDialog().show(getSupportFragmentManager(), "ReAuthenticateDialog");
        } else finish();

    }

    @Override
    public void onConfirmButtonClicked(String password) {
        // Get auth credentials from the user for re-authentication
        final FirebaseUser user = mAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(mContext, "Wrong password.", Toast.LENGTH_SHORT).show();
                    new ReAuthenticateDialog().show(getSupportFragmentManager(), "ReAuthenticateDialog");
                } else {
                    user.updateEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidCredentialsException malformedEmail){
                                    Toast.makeText(mContext, "Email address is not updated (malformed email error).", Toast.LENGTH_SHORT).show();
                                } catch (FirebaseAuthUserCollisionException existingEmail) {
                                    Toast.makeText(mContext, "Email address is not updated (already in use).", Toast.LENGTH_SHORT).show();
                                } catch (Exception e){
                                    Toast.makeText(mContext, "Email address is not updated \nfor unknown reason. Please try again.", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "onComplete: ", e);
                                }
                            } else {
                                Toast.makeText(mContext, "Email address is updated successfully.", Toast.LENGTH_SHORT).show();
                                mDatabase.getReference("user_private_info").child(mAuth.getUid()).child("email").setValue(mEmail.getText().toString());
                            }
                        }
                    });
                    EditProfileActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mContext = EditProfileActivity.this;
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        readUserInfo();
        checkForInvalidEditing();
    }

    /* ************************************ Helper Methods ****************************************/

    private void readUserInfo() {
        mDatabase.getReference("users").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
                MyImageLoader.setImage(mUser.getProfile_photo(), mProfilePhoto, null, "");
                mName.setText(mUser.getFull_name());
                mUsername.setText(mUser.getUsername());
                if ( ! mUser.getWebsite().isEmpty()) mWebsite.setText(mUser.getWebsite());
                if ( ! mUser.getProfile_description().isEmpty()) mBio.setText(mUser.getProfile_description());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        mDatabase.getReference("user_private_info").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUserPrivateInfo = dataSnapshot.getValue(UserPrivateInfo.class);
                mEmail.setText(mUserPrivateInfo.getEmail());
                if ( ! mUserPrivateInfo.getPhone().isEmpty()) mPhone.setText(mUserPrivateInfo.getPhone());
                if ( ! mUserPrivateInfo.getGender().isEmpty()) mGender.setText(mUserPrivateInfo.getGender());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void checkForInvalidEditing(){
        // username uniqueness
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(final Editable editable) {
                // only checks when the user changes to a different username
                String username = editable.toString();
                if (! username.equals(mUser.getUsername())){
                    Query query = mDatabase.getReference("users").orderByChild("username").equalTo(username);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                mUsername.setBackground(getResources().getDrawable(R.drawable.red_rectangle_with_bottom_border));
                                mUsernameUnique = false;
                            } else {
                                mUsername.setBackground(getResources().getDrawable(R.drawable.white_rectangle_grey_bottom_border));
                                mUsernameUnique = true;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }            }
        });
//        // email uniqueness
//        mEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                // only checks when the user changes to a different email
//                String email = editable.toString();
//                if (! email.equals(mUserPrivateInfo.getEmail())) {
//                    mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                            if (task.isSuccessful()) {
//                                if (task.getResult().getSignInMethods().size() != 0) {
//                                    mEmail.setBackground(getResources().getDrawable(R.drawable.red_rectangle_with_bottom_border));
//                                    mEmailUnique = false;
//                                } else {
//                                    mEmail.setBackground(getResources().getDrawable(R.drawable.white_rectangle_grey_bottom_border));
//                                    mEmailUnique = true;
//                                }
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }

    private void writeToUserInfo() {
        DatabaseReference myRef1 = mDatabase.getReference("users").child(mAuth.getUid());
        myRef1.child("full_name").setValue(mName.getText().toString());
        myRef1.child("username").setValue(mUsername.getText().toString());
        myRef1.child("website").setValue(mWebsite.getText().toString());
        myRef1.child("profile_description").setValue(mBio.getText().toString());

        DatabaseReference myRef2 = mDatabase.getReference("user_private_info").child(mAuth.getUid());
        myRef2.child("phone").setValue(mPhone.getText().toString());
        myRef2.child("gender").setValue(mGender.getText().toString());
        myRef2.child("username").setValue(mUsername.getText().toString());
    }
}
