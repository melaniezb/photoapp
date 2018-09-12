package com.example.melaniez.photoapp.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.melaniez.photoapp.MainActivity;
import com.example.melaniez.photoapp.Models.User;
import com.example.melaniez.photoapp.Models.UserPrivateInfo;
import com.example.melaniez.photoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class SignUpFragment extends Fragment {

    /* *************************************** Fields *********************************************/

    private static final String TAG = "SignUpFragment";
    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private boolean mUsernameUnique = true;

    @BindView(R.id.sign_up_email) EditText mEmail;
    @BindView(R.id.sign_up_full_name) EditText mFullName;
    @BindView(R.id.sign_up_username) EditText mUsername;
    @BindView(R.id.sign_up_password) EditText mPassword;
    @OnClick(R.id.sign_up_button) public void onClickButton() {
        if (!mUsernameUnique) {
            Toast.makeText(mContext, "Your username must be unique.", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    newUserInit();
                    getFragmentManager().popBackStack();
                    // TODO: it would be nice to have the email address already on the sign in page when the user returns
                } else {
                    // If sign in fails, display a message to the user.
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException malformedEmail){
                        Toast.makeText(mContext, "Email address is not valid.", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthUserCollisionException existingEmail) {
                        Toast.makeText(mContext, "Email address is already in use by an existing user.", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Toast.makeText(mContext, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "onComplete: ", e);
                    }
                }
            }
        });
    }

    /* ******************************* Fragment Lifecycle Methods *********************************/

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);

        onUsernameUniqueness();
        return view;
    }

    /* ************************************ Helper Methods ****************************************/
    private void newUserInit() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        Log.d(TAG, "createUserWithEmail: success: user id = " + userId);
        User user = new User(
                mFullName.getText().toString(),
                "",
                "",
                0, 0, 0,
                mUsername.getText().toString(),
                ""
        );
        UserPrivateInfo userPrivateInfo = new UserPrivateInfo(
                mEmail.getText().toString(),
                "",
                "",
                mUsername.getText().toString()
        );
        mDatabase.getReference("users").child(userId).setValue(user);
        mDatabase.getReference("user_private_info").child(userId).setValue(userPrivateInfo);
        Toast.makeText(mContext, "Sign-up successful. Sending verification email now.", Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful())
                    Toast.makeText(mContext, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onUsernameUniqueness() {
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUsername.setBackground(getResources().getDrawable(R.drawable.faded_white_rectangle_rounded_corners));
                mUsernameUnique = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Query query = mDatabase.getReference("users").orderByChild("username").equalTo(editable.toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(mContext, "The username already exists. Try another one.", Toast.LENGTH_SHORT).show();
                            mUsername.setBackground(getResources().getDrawable(R.drawable.red_rectangle_rounded_corners));
                            mUsernameUnique = false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
        });
    }

}
