package fr.wildcodeschool.kelian.wcstravel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    private ArrayList<TravelModel> mTravelList;

    public TravelAdapter(ArrayList<TravelModel> travelList) {
        this.mTravelList = travelList;
    }

    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewDeparture.setText(mTravelList.get(position).getDeparture());
        holder.textViewDestination.setText(mTravelList.get(position).getDestination());
        holder.textViewCompany.setText(mTravelList.get(position).getCompany());
        holder.textViewPrice.setText(convertPrice(mTravelList.get(position).getPrice(), "USD"));
    }

    @Override
    public int getItemCount() {
        return mTravelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewDestination, textViewDeparture, textViewCompany, textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewDeparture = itemView.findViewById(R.id.textViewDeparture);
            textViewDestination = itemView.findViewById(R.id.textViewDestination);
            textViewCompany = itemView.findViewById(R.id.textViewCompany);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }

    private String convertPrice(Double price, String firstCurrency) {
        Double resultPrice;
        if (firstCurrency.equals("EUR")) {
            resultPrice = price * 1.2366;
            return "$" + String.valueOf(resultPrice);
        } else {
            resultPrice = price * 0.808668931;
            return String.valueOf(resultPrice) + "€";
        }
    }
}
