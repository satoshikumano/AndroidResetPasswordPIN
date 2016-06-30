package example.com.kii.resetpasswordpin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiCallback;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.exception.app.AppException;


public class MainActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Kii.initialize(Constants.APP_ID, Constants.APP_KEY, Constants.APP_URL);
        setContentView(R.layout.activity_main);
        Button button = (Button) this.findViewById(R.id.buttonRequestReset);
        final EditText userIdEdit = (EditText) this.findViewById(R.id.editTextUserIdentifier);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userId = userIdEdit.getText().toString();
                KiiUser.resetPassword(userId, KiiUser.NotificationMethod.SMS_PIN, new KiiCallback<Void>() {
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
                            Intent i = new Intent();
                            i.setClass(MainActivity.this.getApplicationContext(),
                                    CompleteResetActivity.class);
                            i.putExtra("USER_ID", userId);
                            startActivity(i);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onAlertDialogClick(DialogInterface dialog, int whichButton) {
    }

    private void showAlertDialog(int titleResourceID, String message) {
        DialogFragment newFragment = AlertDialogFragment.newInstance(
                titleResourceID, message);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }
}
