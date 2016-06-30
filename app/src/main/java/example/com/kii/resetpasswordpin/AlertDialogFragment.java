package example.com.kii.resetpasswordpin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment {
    public static AlertDialogFragment newInstance(int title, String message) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        String message =getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((AlertDialogFragmentCallback)getActivity()).onAlertDialogClick(dialog, whichButton);
                            }
                        }
                )
                .create();
    }

    // Client Activity must implement it.
    public interface AlertDialogFragmentCallback {
        void onAlertDialogClick(DialogInterface dialog, int whichButton);
    }

}

