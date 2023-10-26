package au.edu.federation.itech3107.studentattendance30395589;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        EditText etRePassword = findViewById(R.id.et_re_password);
        Button btnCancel = findViewById(R.id.btn_calcel);
        Button btnRegister = findViewById(R.id.btn_register);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String rePassword = etRePassword.getText().toString();
                if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all content", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!rePassword.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "The two passwords are inconsistent", Toast.LENGTH_SHORT).show();
                    return;
                }
                MyDBHelper myDBHelper = new MyDBHelper(RegisterActivity.this);
                if (myDBHelper.register(username, password)) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "User name already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}