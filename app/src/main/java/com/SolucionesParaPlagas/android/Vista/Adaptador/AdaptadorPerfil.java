package com.SolucionesParaPlagas.android.Vista.Adaptador;

import java.util.List;
import android.view.View;
import com.example.sol.R;
import java.util.HashMap;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

public class AdaptadorPerfil extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listGroupTitles;
    private HashMap<String, List<String>> listChildData;
    private OnChildClickListener onChildClickListener; // Listener para los clics

    public AdaptadorPerfil(Context context, List<String> listGroupTitles, HashMap<String, List<String>> listChildData, OnChildClickListener listener) {
        this.context = context;
        this.listGroupTitles = listGroupTitles;
        this.listChildData = listChildData;
        this.onChildClickListener = listener; // Inicializa el listener
    }

    @Override
    public int getGroupCount() {
        return listGroupTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this.listGroupTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listGroupTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChildData.get(this.listGroupTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(childText);

        // Configura el listener para el clic
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChildClickListener != null) {
                    onChildClickListener.onChildClick(groupPosition, childPosition);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // Interfaz para el listener de clics
    public interface OnChildClickListener {
        void onChildClick(int groupPosition, int childPosition);
    }

}
