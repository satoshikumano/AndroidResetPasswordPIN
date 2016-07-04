package example.com.kii.resetpasswordpin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiCallback;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.exception.app.AppException;

public class CompleteResetActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Kii.initialize(Constants.APP_ID, Constants.APP_KEY, Constants.APP_URL);

        setContentView(R.layout.activity_complete_reset);
        final EditText pinCodeEdit = (EditText) findViewById(R.id.editTextPinCode);
        final EditText newPasswordEdit = (EditText) findViewById(R.id.editTextNewPassword);
        Button completeResetButton = (Button) findViewById(R.id.buttonCompleteReset);

        final String userID = getIntent().getExtras().getString("USER_ID");
        completeResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pinCode = pinCodeEdit.getText().toString();
                String newPassword = newPasswordEdit.getText().toString();

                KiiUser.completeResetPassword(userID, pinCode, newPassword, new KiiCallback<Void>() {
                    @Override
                    public void onComplete(Void aVoid, Exception e) {
                        if (e != null) {
                            AppException ae = (AppException) e;
                            StringBuilder b = new StringBuilder();
                            b.append(ae.getMessage());
                            b.append(" : ");
                            b.append(ae.getStatus());
                            b.append(" : ");
                            b.append(ae.getErrorCode());
                            String message = b.toString();
                            showAlertDialog(R.string.error, message);
                        } else {
                            showAlertDialog(R.string.success, "Reset Completed!");
                        }
                    }
                });
            }
        });
    }

    private void showAlertDialog(int titleResourceID, String message) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(
                titleResourceID, message);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onAlertDialogClick(DialogInterface dialog, int whichButton) {
    }
}
