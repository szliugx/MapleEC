package com.ascend.wangfeng.wifimanage.delegates.index.device;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.utils.MacUtil;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class NewDeviceAdapter extends BaseMultiItemQuickAdapter<Device,MultipleViewHolder> {

    private OnClickListener mListener;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewDeviceAdapter(List<Device> data) {
        super(data);
        addItemType(0, R.layout.item_device);
    }
    public void setListener(OnClickListener listener){
        mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Device item) {
        helper.setText(R.id.tv_name,item.getShowName());
        helper.setText(R.id.tv_ip,item.getDevIp());
        helper.setText(R.id.tv_brand,item.getVendor());
        helper.setText(R.id.tv_mac, MacUtil.longToString(item.getDmac()));
        helper.getView(R.id.ll_main).setOnClickListener( view-> {
                if (mListener!=null)mListener.click(item);
        });
        CircleImageView cimg = helper.getView(R.id.cimg);
        cimg.setImage(DeviceType.getTypes().get(item.getDtype()).getImgId());
        // 在线判定
        if (System.currentTimeMillis()-item.getLasttime()<=2*60*60*1000){
            cimg.setState(true);
        }else {
            cimg.setState(false);
        }
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
    public interface OnClickListener{
        void click(Device device);
    }
}
