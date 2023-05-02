package codeconverters.loginpage;

import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class MessageClass extends Fragment {

    private EditText editTextMessage;
    private Button buttonSend;
    private String sName;
    private String sEmail;
    private String pName;
    private String bName;
    private String bEmail;

    public void sendEmail(String sellerEmail,String itemDescription, String text) {
        sEmail=sellerEmail;
        String subject = "(no-reply) - "+ itemDescription;
        String autoMsg = "\n\nThis is an auto generated email. Please do not reply to this email.";
        String message = text;
        //SendMail sm = new SendMail(getActivity(), email, subject, message);
        //sm.execute();
    }
}
