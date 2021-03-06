package com.wiev.androidclient;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import client.Checker;
import client.Client;
import client.Md5Hasher;


public class RegistryActivity extends AppCompatActivity {
  private ImageButton registration = null;
  private EditText login = null;
  private EditText password = null;
  private EditText password_confirm = null;
  private EditText birthdate = null;
  private EditText question = null;
  private EditText answer = null;

  private Client client = Client.getInstance();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registry);

    registration = findViewById(R.id.registration);

    login = findViewById(R.id.login);
    password = findViewById(R.id.password);
    password_confirm = findViewById(R.id.password_confirm);
    birthdate = findViewById(R.id.birthdate);
    question = findViewById(R.id.question);
    answer = findViewById(R.id.answer);

    registration.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final String loginToSend = login.getText().toString();
        final String passwordToSend = password.getText().toString();
        String passwordConfirmToSend = password_confirm.getText().toString();
        final String birthdateToSend = birthdate.getText().toString();
        final String questionToSend = question.getText().toString();
        final String answerToSend = answer.getText().toString();
        if (!Checker.verifyLogin(loginToSend)) {
          Message errorMessage = new Message();
          errorMessage.messageTitle = "Error";
          errorMessage.messageToShow = "Incorrect login";
          errorMessage.show(getFragmentManager(), "error_dialog");
        } else if (!Checker.verifyPinCode(passwordToSend) || !passwordToSend.equals(passwordConfirmToSend)) {
          Message errorMessage = new Message();
          errorMessage.messageTitle = "Error";
          errorMessage.messageToShow = "Incorrect password or not confirmed";
          errorMessage.show(getFragmentManager(), "error_dialog");
        } else if (!Checker.verifyDate(birthdateToSend)) {
          Message errorMessage = new Message();
          errorMessage.messageTitle = "Error";
          errorMessage.messageToShow = "Incorrect birthdate";
          errorMessage.show(getFragmentManager(), "error_dialog");
        } else {
          final String hashedPin = Md5Hasher.getMd5Hash(passwordToSend);
          new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                client.sendMessage("registry", hashedPin, loginToSend, birthdateToSend,
                    questionToSend, answerToSend);

              } catch (Exception e) {
                Message errorMessage = new Message();
                errorMessage.messageTitle = "Error";
                errorMessage.messageToShow = e.getMessage();
                errorMessage.show(getFragmentManager(), "error_dialog");
              }
              String[] answer = null;
              try {
                answer = client.getArrayFromMessage();
              } catch (Exception e) {
                Message errorMessage = new Message();
                errorMessage.messageTitle = "Error";
                errorMessage.messageToShow = e.getMessage();
                errorMessage.show(getFragmentManager(), "error_dialog");

              }
              if (answer[0].equals("success")) {
                showActionDialog();
              } else {
                Message message = new Message();
                message.messageTitle = answer[0];
                message.messageToShow = answer[1];
                message.show(getFragmentManager(), "error");
              }
            }
          }).start();
        }
      }
    });
  }

  private void showActionDialog() {
    new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Close")
        .setMessage("Are you sure you want to close this page?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finish();
          }

        })
        .setNegativeButton("No", null)
        .show();
  }
}
