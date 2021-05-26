package com.example.techapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileOutputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText etUsername,etRetypePassword;

    TextInputEditText etPassword;

    Button btnSign;

    private RadioButton rdoGen;

    private RadioGroup rgGender;

    private Spinner spinCity;

    private CheckBox chkAgree;

    private String str="";

    private String saher="";

    private int counter=0;

    String[] city={"Mumbai","Pune","Nashik"};

    String[] Interest={"Watch","Phones","Laptop","Weareables","TV","Alexa"};

    String user,pwd,rePwd;

    DataBase db;

    private FirebaseAuth mAuth;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        db=new DataBase(this);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,Interest);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        etRetypePassword=findViewById(R.id.etRetypePassword);
        btnSign=findViewById(R.id.btnSign);
        rgGender=findViewById(R.id.rgGender);
        spinCity=findViewById(R.id.spinCity);
        chkAgree=findViewById(R.id.chkAgree);

        user=etUsername.getText().toString();
        pwd=etPassword.getText().toString();
        rePwd=etRetypePassword.getText().toString();


        //authenticate username
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>50){
                    etUsername.setError("Username cannot exceed 50 characters");
                }
                else if(s.length()<4){
                    etUsername.setError("Username must be of 4 characters long");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //authenticate users
                boolean checkPassword= isValidPassword(s);
                if(checkPassword==true)
                    etPassword.setError("password is incorrect");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            FileOutputStream fout = openFileOutput("myfile.txt",0);
            fout.write(user.getBytes());
            fout.close();
        }
        catch(Exception e){

        }

        ArrayAdapter adapter1=new ArrayAdapter(this, android.R.layout.simple_spinner_item,city);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(adapter1);
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saher=city[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        register(user,pwd,rePwd);
    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            recreate();
        }
    }

    public  boolean isValidPassword(CharSequence target) {
        return Pattern.compile("^(?=.*\\\\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{4,12}$").matcher(target).matches();
    }

    public boolean check(String rePass,String pass){
        if(!pass.equals(rePass)){
            etRetypePassword.setError("password not matched");
            return false;
        }
        else{
            return true;
        }
    }
    //handling register button
    public void register(String user,String pwd,String rePwd){
        /*
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedid=rgGender.getCheckedRadioButtonId();
                rdoGen=findViewById(selectedid);
                Intent intent=new Intent(getApplicationContext(),Admin.class);
                intent.putExtra("radio",rdoGen.getText());
                //auth all fields
                if(etUsername.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_LONG).show();
                }
                if(user.equals("") && pwd.equals("")){
                    Toast.makeText(getApplicationContext(),"Invalid username/password",Toast.LENGTH_LONG).show();
                }
                else{
                    intent.putExtra("username",user);
                    intent.putExtra("password",pwd);

                }
                startActivity(intent);
            }
        });

         */
        //handle after register
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //empty field validation
                if(!etUsername.getText().toString().equals("")) {
                    Boolean value = db.insertuserdata(etUsername.getText().toString(), etPassword.getText().toString());
                    if(value){
                        //do nothing
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error occured while inserting data", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    etUsername.setError("username is empty");
                }
                if(!etPassword.getText().toString().equals("")) {
                    Boolean value = db.insertuserdata(etUsername.getText().toString(), etPassword.getText().toString());
                    if(value){
                        //do nothing
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error occured while inserting data", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    etPassword.setError("password is empty");
                }

                //firebase authentication
                try {
                    mAuth.createUserWithEmailAndPassword(etUsername.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (etUsername.getText().toString().equals("")) {
                                        etUsername.setError("please enter username");
                                    }
                                    if (etPassword.getText().toString().equals("")) {
                                        etPassword.setError("please enter password");
                                    }
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("hi", "success");
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("hi", String.valueOf(task.getException()));
                                    }
                                }
                            });
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"username/password is empty",Toast.LENGTH_LONG).show();
                }

                Intent intent=new Intent(getApplicationContext(),DashBoard.class);
                //auth all fields
                intent.putExtra("user-name",etUsername.getText().toString());

                //input fields for user credentials
                EditText etUsername=findViewById(R.id.etUsername);
                EditText etPassword=findViewById(R.id.etPassword);
                EditText etRePassword=findViewById(R.id.etRetypePassword);
                String us=etUsername.getText().toString();
                String pw=etPassword.getText().toString();

                //storing credentials in shared prefernces
                SharedPreferences sp1= getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp1.edit();
                editor.putString("username",us);
                editor.putString("password",pw);
                editor.commit();

                //check password and confirm password matched or not
                boolean checkPass=check(etRePassword.getText().toString(),etPassword.getText().toString());
                intent.putExtra("hobby",str);
                intent.putExtra("city",saher);

                //terms and conditions validation

                if(chkAgree.isChecked()){
                    if(!etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("") && checkPass){
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid username/password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please agree our terms and conditions",Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });
    }
}