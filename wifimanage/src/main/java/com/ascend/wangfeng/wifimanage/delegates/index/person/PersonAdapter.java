package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonAdapter extends BaseMultiItemQuickAdapter<Person, MultipleViewHolder> {
    private boolean mEdit;
    private LatteDelegate mDelegate;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PersonAdapter(List<Person> data, LatteDelegate delegate) {
        super(data);
        addItemType(0, R.layout.item_person);
        mDelegate = delegate;
    }

    /**
     * 开启编辑模式
     */
    public void setEdit() {
        mEdit = true;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Person item) {
        helper.setText(R.id.tv_name, item.getPname());
        helper.setText(R.id.tv_desc, "描述性信息");
        CircleImageView cimg = helper.getView(R.id.cimg_icon);
        cimg.setImage(Icon.getImgUrl(item.getPimage()));
        cimg.setState(item.isOnline());
        RadioButton button = helper.getView(R.id.rb_choose);
        if (mEdit) {
            button.setVisibility(View.VISIBLE);
            helper.setVisible(R.id.it_right, false);
            button.setChecked(item.isSelected());
            button.setOnClickListener(view -> {
                for (int i = 0; i < getData().size(); i++) {
                    if (getData().get(i).isSelected()) getData().get(i).setSelected(false);
                }
                item.setSelected(true);
                // 通过消息机制,避免绘制时触发更新
                Handler handler = new Handler();
                handler.post(() -> notifyDataSetChanged());
            });
        } else {
            button.setVisibility(View.GONE);
            helper.setVisible(R.id.it_right, true);
            cimg.setState(item.isOnline());
            helper.getView(R.id.ll_person).setOnClickListener(view->{
                // 进入成员详情;
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", item);
                mDelegate.start(PersonDetailDelegate.newInstance(bundle));
            });
        }
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
