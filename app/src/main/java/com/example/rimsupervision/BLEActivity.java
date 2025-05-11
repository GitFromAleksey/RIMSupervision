package com.example.rimsupervision;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class BLEActivity extends AppCompatActivity implements View.OnClickListener {

    private final String COUNTER_ADDRESS = "E4:B0:63:43:47:8E";
    EditText et;
    Button btnFind;
    BroadcastReceiver br;
    boolean counterConnected;
    private static final long SCAN_PERIOD = 20000;
    private BluetoothAdapter adapter;
    private BluetoothLeScanner scanner;
    private boolean scanning;
    private Handler handler;
    private ScanCallback leScanCallback;
    private BluetoothGattCallback bluetoothGattCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bleactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        counterConnected = false;

        et = findViewById(R.id.editTextTextMultiLine2);
        btnFind = findViewById(R.id.button_find);
        btnFind.setOnClickListener(this);

        adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null) { Toast.makeText(this, "adapter == null", Toast.LENGTH_LONG).show(); }
        if(!adapter.isEnabled()) { Toast.makeText(this, "adapter not enabled", Toast.LENGTH_LONG).show(); }
        scanner = adapter.getBluetoothLeScanner();
        scanning = false;
        handler = new Handler();

        bluetoothGattCallback = new BluetoothGattCallback() {

            @Override
            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                Log.i("BLE", "onPhyUpdate. gatt:" + gatt.toString() + "; txPhy:" + txPhy + ", rxPhy:" + rxPhy + "; status:" + status);
            }

            @SuppressLint("MissingPermission")
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Log.i("BLE", "onConnectionStateChange: " + gatt.toString() + "; status:" + status + "; newState:" + newState);
                int bondState = gatt.getDevice().getBondState();
                String bondStateTxt = "";
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    switch (bondState)
                    {
                        case BluetoothDevice.BOND_NONE:
                            bondStateTxt = "BOND_NONE";
                            break;
                        case BluetoothDevice.BOND_BONDING:
                            bondStateTxt = "BOND_BONDING";
                            break;
                        case BluetoothDevice.BOND_BONDED:
                            bondStateTxt = "BOND_BONDED";
                            break;
                        default:
                            bondStateTxt = "BOND_UNKNOWN";
                            break;
                    }

                    Log.i("BLE", "successfully connected to the GATT Server:" + gatt.getDevice().getAddress() + "; bondState:" + bondStateTxt);

                    gatt.discoverServices();

                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    counterConnected = false;
                    gatt.close();
                    Log.i("BLE", "disconnected from the GATT Server.");
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);

                String statusStr = "GATT_NOT_SUCCESS";
                if(status == BluetoothGatt.GATT_SUCCESS)
                    statusStr = "GATT_SUCCESS";
                Log.i("BLE", "onServicesDiscovered status:" + statusStr );

                for(BluetoothGattService serv : gatt.getServices() )
                {
                    Log.i("BLE", "Service UUID:" + serv.getUuid() );
                    for(BluetoothGattCharacteristic charact : serv.getCharacteristics())
                    {
                        String propertieTxt = "";

                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_BROADCAST) != 0) // = 1;
                            propertieTxt += "PROPERTY_BROADCAST, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) != 0) //  = 2;
                            propertieTxt += "PROPERTY_READ, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) != 0) // = 4;:
                            propertieTxt += "PROPERTY_WRITE_NO_RESPONSE, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE) != 0) // = 8;:
                            propertieTxt += "PROPERTY_WRITE, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) // = 16;:
                            propertieTxt += "PROPERTY_NOTIFY, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) // = 32;
                            propertieTxt += "PROPERTY_INDICATE, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE) != 0) // = 64;:
                            propertieTxt += "PROPERTY_SIGNED_WRITE, ";
                        if( (charact.getProperties() & BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS) != 0) // = 128;:
                            propertieTxt += "PROPERTY_EXTENDED_PROPS, ";

                        Log.i("BLE", "  Characteristic UUID:" + charact.getUuid() + "; Propertie:" + propertieTxt + " = " + charact.getProperties());
                    }
                }
            }
        };

        leScanCallback = new ScanCallback() {
            @SuppressLint("MissingPermission")
            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice dev = result.getDevice();
                Log.i("BLE", "Address: " + dev.getAddress());
                String name = "";
                try {
                    name = dev.getName();
                }catch (Exception e) {}

                if(dev.getAddress().equals(COUNTER_ADDRESS)) // SPP_SERVER E4:B0:63:43:47:8E
                {
                    String logText = name + "; Address: " + dev.getAddress();
                    Log.i("BLE", name + "; Address: " + dev.getAddress());
                    Log.i("BLE", "result: " + result);

                    counterConnected = true;
                    String showingText = "СЧЁТЧИК ПОДКЛЮЧЕН!\nИмя:" + name + "\nАдрес:" + dev.getAddress();
                    et.setText(showingText);
                    et.setBackgroundColor(Color.GREEN);
                    scanner.stopScan(leScanCallback);
                    dev.connectGatt(BLEActivity.this, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE);
                }

            }
        };
//        ScanLeDevices();
    }

    @SuppressLint("MissingPermission")
    private void ScanLeDevices()
    {
        if(!scanning)
        {
            handler.postDelayed(new Runnable()
            {
                @SuppressLint("MissingPermission")
                @Override
                public void run()
                {
                    scanning = false;
                    scanner.stopScan(leScanCallback);
                    Log.i("BLE", "Scan is stopped!");
                    if(!counterConnected)
                    {
                        et.setText("СЧЁТЧИК НЕ НАЙДЕН!");
                        et.setBackgroundColor(Color.RED);
                    }
                    scanner.stopScan(leScanCallback);
                }
            }, SCAN_PERIOD);

            scanning = true;
            scanner.startScan(leScanCallback);
        }
        else
        {
            scanning = false;
            scanner.stopScan(leScanCallback);
        }
    }


    @Override
    public void onClick(View view)
    {
        if(btnFind == view)
        {
            if(counterConnected)
                return;
            ScanLeDevices();
            et.setText("ПОИСК СЧЁТЧИКА...");
            et.setBackgroundColor(Color.BLACK);
        }
    }
}
