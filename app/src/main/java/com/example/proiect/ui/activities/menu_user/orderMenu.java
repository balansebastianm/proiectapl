package com.example.proiect.ui.activities.menu_user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proiect.R;
import com.example.proiect.ui.activities.singleton;
import com.example.proiect.ui.activities.userActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sql.Orders;
import sql.DatabaseHelperOrders;
import sql.DatabaseHelperPrescriptions;

public class orderMenu extends AppCompatActivity {
    DatabaseHelperPrescriptions db = new DatabaseHelperPrescriptions(this);
    DatabaseHelperOrders dbc = new DatabaseHelperOrders(this);

    TextView totalPrice;
    EditText etFullName, etAddress, etPOCode, etCity, etCounty;
    String fullName, address, city, county;
    int POCode, orderPrice, orderPersonId;
    Button buttonOrder;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        ImageView back = findViewById(R.id.backArrowToolbar);
        back.setOnClickListener(view -> {
            Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
            startActivity(activity2Intent);
        });
        if(!db.checkIsOrder(Integer.parseInt(singleton.getInstance().getId()))){
            Toast.makeText(getApplicationContext(), "Nothing to order.", Toast.LENGTH_SHORT).show();
            Intent activity2Intent = new Intent(getApplicationContext(), orderDrugs.class);
            startActivity(activity2Intent);
            return;
        }

        totalPrice = findViewById(R.id.tvTotalPrice);
        int price = db.getTotalPrice(singleton.getInstance().getId());
        System.out.println(price);
        totalPrice.setText(Integer.toString(price));
        buttonOrder = findViewById(R.id.buttonOrderNow);
        buttonOrder.setOnClickListener(view -> {
            etFullName = findViewById(R.id.etFullNameOrder);
            etAddress = findViewById(R.id.etOrderAddress);
            etPOCode = findViewById(R.id.etPOCode);
            etCity = findViewById(R.id.etOrderCity);
            etCounty = findViewById(R.id.etOrderCounty);
            fullName = etFullName.getText().toString();
            address = etAddress.getText().toString();
            try{
                POCode = Integer.parseInt(etPOCode.getText().toString());
                orderPersonId = Integer.parseInt(singleton.getInstance().getId());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            city = etCity.getText().toString();
            county = etCounty.getText().toString();
            orderPrice = price;

            inputValidation();

            if(!inputValidation()){
                Toast.makeText(getApplicationContext(), "Fill in all fields.", Toast.LENGTH_SHORT).show();
            }
            else if(!db.checkIsOrder(orderPersonId)){
                Toast.makeText(getApplicationContext(), "Nothing to order.", Toast.LENGTH_SHORT).show();
                Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
            else{
                Orders c = new Orders();
                c.setIdUserOrder(orderPersonId);
                c.setFullName(fullName);
                c.setAddress(address);
                c.setPoCode(POCode);
                c.setCity(city);
                c.setCounty(county);
                c.setTotalPrice(orderPrice);


                if(db.deleteOrder(orderPersonId)){
                    dbc.addOrder(c);
                    Toast.makeText(getApplicationContext(), "Order received.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Insufficient stock. Try again later.", Toast.LENGTH_SHORT).show();
                }
                Intent activity2Intent = new Intent(getApplicationContext(), userActivity.class);
                activity2Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(activity2Intent);
            }
        });

    }

    public boolean inputValidation(){
        boolean a = true;
        if(TextUtils.isEmpty(fullName)) {
            etFullName.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(address)) {
            etAddress.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(Integer.toString(POCode))) {
            etPOCode.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(city)) {
            etCity.setError("Empty field");
            a = false;
        }
        if(TextUtils.isEmpty(county)) {
            etCounty.setError("Empty field");
            a = false;
        }
        return a;
    }
}