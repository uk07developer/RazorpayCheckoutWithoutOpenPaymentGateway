import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Razorpay
        Checkout.preload(getApplicationContext());
    }

    public void startUPIPayment() {
        try {
            // Create a new order
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", 50000); // amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");

            // Assuming you have created an order on your server and received order_id
            String order_id = "order_DBJOWzybf0sJbb"; 

            // Intent to launch UPI app
            Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "merchant_vpa@bank")
                .appendQueryParameter("pn", "Merchant Name")
                .appendQueryParameter("mc", "")
                .appendQueryParameter("tid", "02125412")
                .appendQueryParameter("tr", order_id)
                .appendQueryParameter("tn", "Payment for order")
                .appendQueryParameter("am", "500.00")
                .appendQueryParameter("cu", "INR")
                .build();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            Intent chooser = Intent.createChooser(intent, "Pay with");

            if (null != chooser.resolveActivity(getPackageManager())) {
                startActivityForResult(chooser, 1);
            } else {
                Log.e("UPI", "No UPI app found");
            }

        } catch (Exception e) {
            Log.e("UPI", "Error in UPI Payment", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String response = data.getStringExtra("response");
                    Log.i("UPI", "onActivityResult: " + response);
                    // Handle the payment response
                }
            } else {
                Log.e("UPI", "Payment failed");
            }
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        // Handle successful payment here
        Log.i("UPI", "Payment Successful: " + razorpayPaymentID);
    }

    @Override
    public void onPaymentError(int code, String description) {
        // Handle failed payment here
        Log.e("UPI", "Payment Error: " + description);
    }
}