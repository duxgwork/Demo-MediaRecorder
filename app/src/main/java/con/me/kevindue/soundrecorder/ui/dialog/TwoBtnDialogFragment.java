package con.me.kevindue.soundrecorder.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import con.me.kevindue.soundrecorder.R;


/**
 * 双按钮dialog
 */
public class TwoBtnDialogFragment extends DialogFragment {
    private String title = "提示";
    private CharSequence msg = "";
    private String positiveText = "";
    private String negativeText = "";
    private String neutralText = "";
    private boolean cancelable = true;
    private int leftcolor;
    private int rightcolor;

    private TwoButtonDialogOnclickListener dialogOnclickListener = null;


    public static TwoBtnDialogFragment newInstance(CharSequence msg, String positiveText, String negativeText, boolean cancelable) {
        Bundle args = new Bundle();
        args.putCharSequence("msg", msg);
        args.putString("positiveText", positiveText);
        args.putString("negativeText", negativeText);
        args.putString("neutralText", "");
        args.putBoolean("cancelable", cancelable);
        TwoBtnDialogFragment fragment = new TwoBtnDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setDialogOnclickListener(TwoButtonDialogOnclickListener twoButtonDialogOnclickListener) {
        dialogOnclickListener = twoButtonDialogOnclickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title", "提示");
        msg = getArguments().getCharSequence("msg", "");
        positiveText = getArguments().getString("positiveText", "");
        negativeText = getArguments().getString("negativeText", "");
        neutralText = getArguments().getString("neutralText", "");
        cancelable = getArguments().getBoolean("cancelable", false);
        leftcolor = getArguments().getInt("leftcolor", ContextCompat.getColor(getContext(), R.color.color_text_06_blue));
        rightcolor = getArguments().getInt("rightcolor", ContextCompat.getColor(getContext(), R.color.color_text_06_blue));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title(title)
                .content(msg)
                .cancelable(cancelable)
                .positiveText(positiveText)
                .positiveColor(rightcolor)
                .negativeText(negativeText)
                .negativeColor(leftcolor)
                .neutralText(neutralText);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if(dialogOnclickListener != null) {
                    dialogOnclickListener.positiveClick(dialog);
                }
            }
        });

        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if(dialogOnclickListener != null) {
                    dialogOnclickListener.negativeClick(dialog);
                }
            }
        });

        builder.onNeutral(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dismiss();
            }
        });
        return builder.show();
    }

    public interface TwoButtonDialogOnclickListener {
        void positiveClick(MaterialDialog dialog);

        void negativeClick(MaterialDialog dialog);
    }
}
