package ibdaa.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ibdaa.bloodbank.R;
import ibdaa.bloodbank.data.model.donations.DonationsData;
import ibdaa.bloodbank.view.activity.BaseActivity;
import ibdaa.bloodbank.view.fragment.homeCycle.DonationInfoFragment;

import static ibdaa.bloodbank.utils.HelperMethod.replaceFragment;

public class

DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {
    private Context context;
    private BaseActivity activity;
    private List<DonationsData> donationsDataList = new ArrayList<>();

    public DonationAdapter(Activity activity, List<DonationsData> donationsDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.donationsDataList = donationsDataList;
    }

    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.ViewHolder holder, final int position) {
        holder.donation_dapter_tv_blood_type.setText(donationsDataList.get(position).getBloodType().getName());
        holder.donation_adapter_tv_address.setText( donationsDataList.get(position).getHospitalAddress());
        holder.donation_adapter_tv_city.setText(donationsDataList.get(position).getCity().getName());
        holder.donation_adapter_tv_name.setText(donationsDataList.get(position).getPatientName());
        holder.call.setOnClickListener(view -> {
            String mobile = donationsDataList.get(position).getPhone();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mobile));
            activity.startActivity(intent);
        });
        holder.info.setOnClickListener(view -> replaceFragment(activity.getSupportFragmentManager(),R.id.home_container, new DonationInfoFragment()));
    }

    /*@Override
    public void onBindViewHolder(@NonNull TestDonationAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }*/

    private void setData(ViewHolder holder, int position) {

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return donationsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        TextView donation_dapter_tv_blood_type,donation_adapter_tv_name,donation_adapter_tv_address,donation_adapter_tv_city;

        ImageButton call,info;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, view);
            donation_dapter_tv_blood_type =  itemView.findViewById(R.id.donation_adapter_tv_blood_type);
            donation_adapter_tv_name =  itemView.findViewById(R.id.donation_adapter_tv_name);
            donation_adapter_tv_address = itemView.findViewById(R.id.donation_adapter_tv_address);
            donation_adapter_tv_city =  itemView.findViewById(R.id.donation_adapter_tv_city);
            call = itemView.findViewById(R.id.donation_adapter_i_btn_call);
            info = itemView.findViewById(R.id.donation_adapter_i_btn_info);
        }
    }
}