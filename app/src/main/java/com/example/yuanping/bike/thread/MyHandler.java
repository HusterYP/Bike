package com.example.yuanping.bike.thread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.yuanping.bike.Constant;
import com.example.yuanping.bike.R;

/**
 * Created by yuanping on 3/31/18.
 */

public class MyHandler extends Handler {

    private Context context;
    private ProgressDialog dialog;
    public final int SHOW_DIALOG = 0;//显示对话框
    public final int DIS_DIALOG = 1; //取消显示对话框
    public final int NO_BIKE = 2;//没有存放自行车
    public final int DEVICE_OFFILINE = 3;//设备不在线
    public final int DOWN_GET_COMPLETE = 4;//取车过程: 车位下降完成
    public final int DOWN_PUT_COMPLETE = 5; //放车过程: 车位下降完成
    public final int OVER = 6;//过程完成,抬升车位
    public final int REPEAT_PUT = 7; //重复存放,默认一个用户只能存放一辆
    public final int NO_EMPTY = 8; //没有空车位了
    public final int TEST_SQL = 9;
    public final int TEST_POSITION = 10;

    public MyHandler(Context context) {
        this.context = context;
        dialog = Constant.showSpinnerDialog((Activity) context, "请稍后...");
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case SHOW_DIALOG: {
                if (!((Activity) context).isFinishing() && dialog != null) {
                    dialog.show();
                }
                break;
            }
            case DIS_DIALOG: {
                disDialog();
                break;
            }
            case NO_BIKE: {
                disDialog();
                toast("还没有存放哦!");
                break;
            }
            case DEVICE_OFFILINE: {
                disDialog();
                toast("设备不在线哦!请稍后再试!");
                break;
            }
            case DOWN_GET_COMPLETE: {
                disDialog();
                toast("请取走您的自行车！");
                ((Activity) context).findViewById(R.id.get_over).setEnabled(true);
            }
            case DOWN_PUT_COMPLETE: {
                disDialog();
                toast("请存放您的自行车！");
                ((Activity) context).findViewById(R.id.put_over).setEnabled(true);
            }
            case OVER: {
                disDialog();
                toast("谢谢使用!");
                break;
            }
            case REPEAT_PUT: {
                disDialog();
                toast("只能存放一辆自行车哦!");
                break;
            }
            case NO_EMPTY: {
                disDialog();
                toast("没有空车位啦!");
                break;
            }
            case TEST_SQL: {
                String result = (String) msg.obj;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(result).setTitle("数据库数据");
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            }
            case TEST_POSITION: {
                String result = (String) msg.obj;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(result).setTitle("车位信息查询");
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            }
        }
    }

    private void disDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void toast(String msg) {
        Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_SHORT).show();
    }

}
