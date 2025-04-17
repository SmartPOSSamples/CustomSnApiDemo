package com.cloudpos.customsn.activity;

import com.cloudpos.customsn.R;
import com.cloudpos.util.SystemUtil;
import com.cloudpos.util.TextViewUtil;
import com.wizarpos.wizarviewagent.aidl.ICustomSnApi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ConstantActivity implements OnClickListener {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_run3 = (Button) this.findViewById(R.id.btn_run3);
        Button btn_run4 = (Button) this.findViewById(R.id.btn_run4);
        Button btn_run5 = (Button) this.findViewById(R.id.btn_run5);
//		Button btn_run6 = (Button) this.findViewById(R.id.btn_run6);
        log_text = (TextView) this.findViewById(R.id.text_result);
        log_text.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.settings).setOnClickListener(this);
        btn_run3.setOnClickListener(this);
        btn_run4.setOnClickListener(this);
        btn_run5.setOnClickListener(this);
//		btn_run6.setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == DEFAULT_LOG) {
                    log_text.append("\t" + msg.obj + "\n");
                } else if (msg.what == SUCCESS_LOG) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoBlueTextView(log_text, str);
                } else if (msg.what == FAILED_LOG) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoRedTextView(log_text, str);
                } else if (msg.what == CLEAR_LOG) {
                    log_text.setText("");
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        int index = arg0.getId();
        if (index == R.id.btn_run3) {
            addCustomSn();
        } else if (index == R.id.btn_run4) {
            clearCustomSn();
        } else if (index == R.id.btn_run5) {

        }/*else if (index == R.id.btn_run6) {
			
		}*/ else if (index == R.id.settings) {
            log_text.setText("");
        }
    }

    private void addCustomSn() {
        addCustomSn("123456789test");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writerInLog("SN:" + SystemUtil.getCustomSn());
    }

    private void clearCustomSn() {
        addCustomSn("");
    }

    private void addCustomSn(final String sn) {
        startConnectService(this, new ComponentName("com.wizarpos.system.settings", "com.wizarpos.system.settings.service.CustomSnApiService"), new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ICustomSnApi api = ICustomSnApi.Stub.asInterface(service);
                if (api != null) {
                    try {
                        int result = api.writerSn(sn);
                        writerInSuccessLog("writer sn result is " + result);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } finally {
                        MainActivity.this.unbindService(this);
                    }
                }

            }
        });

    }

    protected synchronized boolean startConnectService(Context context, ComponentName comp, ServiceConnection connection) {
        Intent intent = new Intent();
        intent.setPackage(comp.getPackageName());
        intent.setComponent(comp);
        boolean isSuccess = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        writerInSuccessLog("bind service is " + isSuccess);
        return isSuccess;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
