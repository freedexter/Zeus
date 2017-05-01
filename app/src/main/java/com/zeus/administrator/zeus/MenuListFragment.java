package com.zeus.administrator.zeus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @author wangxg
 *  �����������б�Fragment��������ʾ�б���ͼ 
 */
public class MenuListFragment extends ListFragment {
    private OnHeadlineSelectedListener mCallback;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        String username;
        super.onActivityCreated(savedInstanceState);
        SampleAdapter adapter = new SampleAdapter(getActivity());
        TextView user = (TextView) getActivity().findViewById(R.id.username);
        username = user.getText().toString();
        if( username.equals("admin") != true ) {
            String strs[] = getResources().getStringArray(R.array.main_menu);
            for (int i = 0; i < 2; i++) {
                adapter.add(new SampleItem(strs[i], R.drawable.menu_icon));
            }
        }else{
            String strs[] = getResources().getStringArray(R.array.main_menu_set);
            for (int i = 0; i < 3; i++) {
                adapter.add(new SampleItem(strs[i], R.drawable.menu_icon));
            }
        }
        setListAdapter(adapter);
    }


    private class SampleItem {
        public String tag;
        public int iconRes;
        public SampleItem(String tag, int iconRes) {
            this.tag = tag;
            this.iconRes = iconRes;
        }
    }

    public class SampleAdapter extends ArrayAdapter<SampleItem> {

        public SampleAdapter(Context context) {
            super(context, 0);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate( R.layout.row, null);
            }
            ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
            icon.setImageResource(getItem(position).iconRes);
            TextView title = (TextView) convertView.findViewById(R.id.row_title);
            title.setText(getItem(position).tag);

            return convertView;
        }

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        mCallback.onArticleSelected(position);
/*
        TextView username = (TextView) getActivity().findViewById(R.id.editName);
      //  Log.v("tag",username.getText().toString());
        Intent intent=new Intent();;
        Bundle b = new Bundle();
        b.putString("username", username.getText().toString());

        switch(position) {
            case 0:
                intent.setClass(getActivity(), QryActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent=new Intent( getActivity(),AddActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent=new Intent( getActivity(),ModiActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent=new Intent( getActivity(),CountActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent=new Intent( getActivity(),QryOrderActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent=new Intent( getActivity(),AddOrderActivity.class);
                startActivity(intent);
                break;
            case 6:
                intent=new Intent( getActivity(),ModiOrderActivity.class);
                startActivity(intent);
                break;
            case 7:
                intent=new Intent( getActivity(),CountOrderActivity.class);
                startActivity(intent);
                break;
        }
*/
    }
    // 用来存放fragment的Activtiy必须实现这个接口
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // 这是为了保证Activity容器实现了用以回调的接口。如果没有，它会抛出一个异常。
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}