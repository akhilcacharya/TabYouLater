package xyz.iou.app.iou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import xyz.iou.app.iou.Models.Bill;
import xyz.iou.app.iou.Models.Responses.BillResponse;
import xyz.iou.app.iou.Services.IOUService;

public class BillActivity extends AppCompatActivity {

    String billId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);


        final IOUService service = buildService();

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String id = sharedPreferences.getString("id", "");


        final EditText dollars = (EditText) findViewById(R.id.activity_bill_dollars);

        final ImageView qrCode = (ImageView) findViewById(R.id.activity_bill_qrcode);


        final Button done = (Button) findViewById(R.id.activity_bill_done);
        done.setVisibility(View.INVISIBLE);

        final Button create = (Button) findViewById(R.id.activity_bill_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = new ProgressDialog(BillActivity.this);
                dialog.setTitle("Generating QR Code...");
                dialog.show();

                String dollarsString = dollars.getText().toString();
                double value = 0d;

                try {
                    value = Double.valueOf(dollarsString);
                } catch (NumberFormatException e) {
                    Toast.makeText(BillActivity.this, "Invalid dollar amount", 1).show();
                    return;
                }

                long amount = (long) value * 100;

                service.createBill(id, amount, new Callback<BillResponse>() {
                    @Override
                    public void success(BillResponse billResponse, Response response) {
                        done.setVisibility(View.VISIBLE);
                        create.setVisibility(View.GONE);
                        billId = billResponse.billId;

                        String all = billId + "," + id;

                        qrCode.setImageBitmap(encodeToQrCode(all, qrCode.getWidth(), qrCode.getHeight()));
                        dialog.hide();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(BillActivity.this, error.getMessage(), 1).show();
                    }
                });


            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.collectBill(billId, new Callback<BillResponse>() {
                    @Override
                    public void success(BillResponse response, Response response2) {
                        Toast.makeText(BillActivity.this, "Accepted payments", 1).show();;
                        startActivity(new Intent(BillActivity.this, BaseActivity.class));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(BillActivity.this, error.toString(), 1).show();
                    }
                });
            }
        });



    }

    public static Bitmap encodeToQrCode(String text, int width, int height){
        int qrCodeDimention = width;

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(text, null,
                "TEXT_TYPE", BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

        Bitmap bmp = null;

        try {
            bmp = qrCodeEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bmp;
    }


    private IOUService buildService(){
        String endpoint = getString(R.string.endpoint);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(endpoint).build();
        IOUService service = adapter.create(IOUService.class);
        return service;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
