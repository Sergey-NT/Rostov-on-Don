package ru.rnd_airport.rostov_on_don.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.rnd_airport.rostov_on_don.Constants;
import ru.rnd_airport.rostov_on_don.ObjectPlane;
import ru.rnd_airport.rostov_on_don.R;

public class ObjectPlaneAdapter extends BaseAdapter implements Filterable {

    private List<ObjectPlane> originalList;
    private List<ObjectPlane> filteredList;
    private LayoutInflater layoutInflater;
    private Context context;
    private ItemFilter itemsFilter = new ItemFilter();
    private SharedPreferences settings;

    public ObjectPlaneAdapter(Context context, List<ObjectPlane> list) {
        this.context = context;
        this.originalList = list;
        this.filteredList = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        settings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private static class ViewHolder {
        private TextView itemFlight;
        private TextView itemDirection;
        private TextView itemType;
        private TextView itemTimePlan;
        private TextView itemTimeFact;
        private TextView itemStatus;
        private TextView itemGate;
        private TextView itemBaggageStatus;
        private TextView itemCheckIn;
        private TextView itemCheckInBegin;
        private TextView itemCheckInEnd;
        private TextView itemCombination;
        private TextView descriptionStatus;
        private TextView descriptionBaggage;
        private TextView descriptionGate;
        private TextView descriptionCheckIn;
        private TextView descriptionCheckInBegin;
        private TextView descriptionCheckInEnd;
        private ImageView imageViewTracking;
        private ImageView imageViewLogo;
        private RelativeLayout relativeLayout;
        private boolean activateBackground;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setInfoTracking (int i) {
        boolean tracking;
        ObjectPlane objectPlane = getObjectPlane(i);
        tracking = objectPlane.isPlaneTracking();
        objectPlane.setPlaneTracking(!tracking);
        notifyDataSetChanged();
    }

    public boolean getInfoTracking (int i) {
        boolean tracking;
        ObjectPlane objectPlane = getObjectPlane(i);
        tracking = objectPlane.isPlaneTracking();
        return tracking;
    }

    private void setInfoTrackingToTrue (int i) {
        ObjectPlane objectPlane = getObjectPlane(i);
        objectPlane.setPlaneTracking(true);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_item, viewGroup, false);

            holder = new ViewHolder();

            holder.itemFlight = view.findViewById(R.id.tvPlaneFlight);
            holder.itemDirection = view.findViewById(R.id.tvPlaneDirection);
            holder.itemType = view.findViewById(R.id.tvPlaneType);
            holder.itemTimePlan = view.findViewById(R.id.tvPlaneTimePlan);
            holder.itemTimeFact = view.findViewById(R.id.tvPlaneTimeFact);
            holder.itemStatus = view.findViewById(R.id.tvPlaneStatus);
            holder.itemBaggageStatus = view.findViewById(R.id.tvPlaneBaggage);
            holder.itemGate = view.findViewById(R.id.tvPlaneGate);
            holder.itemCheckIn = view.findViewById(R.id.tvPlaneCheckIn);
            holder.itemCheckInBegin = view.findViewById(R.id.tvPlaneCheckInBegin);
            holder.itemCheckInEnd = view.findViewById(R.id.tvPlaneCheckInEnd);
            holder.itemCombination = view.findViewById(R.id.tvPlaneCombination);
            holder.descriptionStatus = view.findViewById(R.id.tvPlaneStatusDesc);
            holder.descriptionBaggage = view.findViewById(R.id.tvPlaneBaggageDesc);
            holder.descriptionGate = view.findViewById(R.id.tvPlaneGateDesc);
            holder.descriptionCheckIn = view.findViewById(R.id.tvPlaneCheckInDesc);
            holder.descriptionCheckInBegin = view.findViewById(R.id.tvPlaneCheckInBeginDesc);
            holder.descriptionCheckInEnd = view.findViewById(R.id.tvPlaneCheckInEndDesc);
            holder.imageViewTracking = view.findViewById(R.id.imageTracking);
            holder.imageViewLogo = view.findViewById(R.id.imageLogo);
            holder.relativeLayout = view.findViewById(R.id.listViewItem);
            holder.activateBackground = settings.getBoolean(Constants.APP_PREFERENCES_ACTIVATE_BACKGROUND, false);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            holder.activateBackground = settings.getBoolean(Constants.APP_PREFERENCES_ACTIVATE_BACKGROUND, false);
        }

        ObjectPlane objectPlane = getObjectPlane(i);

        holder.itemFlight.setText(objectPlane.getPlaneFlight());
        holder.itemDirection.setText(objectPlane.getPlaneDirection());
        holder.itemType.setText(objectPlane.getPlaneType());
        holder.itemTimePlan.setText(objectPlane.getPlaneTimePlan());
        holder.itemTimeFact.setText(objectPlane.getPlaneTimeFact());
        holder.itemStatus.setText(objectPlane.getPlaneStatus());
        holder.itemBaggageStatus.setText(objectPlane.getPlaneBaggageStatus());
        holder.itemGate.setText(objectPlane.getPlaneGate());
        holder.itemCheckIn.setText(objectPlane.getPlaneCheckIn());
        holder.itemCheckInBegin.setText(objectPlane.getRegistrationBegin());
        holder.itemCheckInEnd.setText(objectPlane.getRegistrationEnd());
        holder.itemCombination.setText(objectPlane.getPlaneCombination());

        holder.itemFlight.setTag(objectPlane.getPlaneRoute());
        holder.itemDirection.setTag(objectPlane.getPlaneRouteStatus());
        holder.itemType.setTag(objectPlane.getCheckInStatus());
        holder.itemTimePlan.setTag(objectPlane.getBoardingEnd());
        holder.itemTimeFact.setTag(objectPlane.getBoardingStatus());
        holder.itemStatus.setTag(objectPlane.getPlaneAirline());

        if (objectPlane.isPlaneTracking()) {
            holder.imageViewTracking.setVisibility(View.VISIBLE);
        } else  {
            holder.imageViewTracking.setVisibility(View.GONE);
        }

        if (holder.imageViewLogo.getVisibility() == View.GONE) {
            holder.imageViewLogo.setVisibility(View.VISIBLE);
        }

        if (holder.itemCombination.length() > 0) {
            holder.itemCombination.setVisibility(View.VISIBLE);
        } else {
            holder.itemCombination.setVisibility(View.GONE);
        }

        holder.itemStatus.setVisibility(View.VISIBLE);
        holder.descriptionStatus.setVisibility(View.VISIBLE);
        holder.itemBaggageStatus.setVisibility(View.GONE);
        holder.descriptionBaggage.setVisibility(View.GONE);
        holder.itemCheckInBegin.setVisibility(View.GONE);
        holder.descriptionCheckInBegin.setVisibility(View.GONE);
        holder.itemCheckInEnd.setVisibility(View.GONE);
        holder.descriptionCheckInEnd.setVisibility(View.GONE);
        holder.itemCheckIn.setVisibility(View.GONE);
        holder.descriptionCheckIn.setVisibility(View.GONE);
        holder.itemGate.setVisibility(View.GONE);
        holder.descriptionGate.setVisibility(View.GONE);

        String status = holder.itemStatus.getText().toString();
        String statusUpperCase = status.toUpperCase();

        int colorGreen = ContextCompat.getColor(context, R.color.colorPrimaryLightGreen);
        int colorRed = ContextCompat.getColor(context, R.color.colorPrimaryRed);
        int colorOrange = ContextCompat.getColor(context, R.color.colorPrimaryDarkAmber);

        holder.itemStatus.setTextColor(colorGreen);
        holder.itemStatus.setText(statusUpperCase);
        holder.itemStatus.setTypeface(null, Typeface.BOLD);
        holder.itemBaggageStatus.setTextColor(colorGreen);
        holder.itemBaggageStatus.setText(holder.itemBaggageStatus.getText().toString().toUpperCase());
        holder.itemBaggageStatus.setTypeface(null, Typeface.BOLD);
        holder.itemGate.setTextColor(colorGreen);
        holder.itemGate.setText(holder.itemGate.getText().toString().toUpperCase());
        holder.itemGate.setTypeface(null, Typeface.BOLD);
        holder.itemCheckInBegin.setTextColor(colorGreen);
        holder.itemCheckInBegin.setText(holder.itemCheckInBegin.getText().toString().toUpperCase());
        holder.itemCheckInBegin.setTypeface(null, Typeface.BOLD);
        holder.itemCheckInEnd.setTextColor(colorGreen);
        holder.itemCheckInEnd.setText(holder.itemCheckInEnd.getText().toString().toUpperCase());
        holder.itemCheckInEnd.setTypeface(null, Typeface.BOLD);
        holder.itemCheckIn.setTextColor(colorGreen);
        holder.itemCheckIn.setText(holder.itemCheckIn.getText().toString().toUpperCase());
        holder.itemCheckIn.setTypeface(null, Typeface.BOLD);
        holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorText));

        if (holder.itemBaggageStatus.length() > 1) {
            holder.itemBaggageStatus.setVisibility(View.VISIBLE);
            holder.descriptionBaggage.setVisibility(View.VISIBLE);
        }

        if (holder.itemStatus.length() < 2) {
            holder.itemStatus.setVisibility(View.GONE);
            holder.descriptionStatus.setVisibility(View.GONE);
        }

        if (status.contains("Идет посадка") || status.contains("Boarding") || status.contains("Регистрация закончена") || status.contains("Check-in-close")) {
            holder.itemGate.setVisibility(View.VISIBLE);
            holder.descriptionGate.setVisibility(View.VISIBLE);
        } else if (status.contains("Идет регистрация") || status.contains("Check-in-open")) {
            holder.itemGate.setVisibility(View.VISIBLE);
            holder.itemCheckIn.setVisibility(View.VISIBLE);
            holder.itemCheckInEnd.setVisibility(View.VISIBLE);
            holder.itemCheckInBegin.setVisibility(View.VISIBLE);
            holder.descriptionGate.setVisibility(View.VISIBLE);
            holder.descriptionCheckIn.setVisibility(View.VISIBLE);
            holder.descriptionCheckInEnd.setVisibility(View.VISIBLE);
            holder.descriptionCheckInBegin.setVisibility(View.VISIBLE);
        } else if (status.contains("Отмена") || status.contains("Cancelled")) {
            holder.itemBaggageStatus.setVisibility(View.GONE);
            holder.descriptionBaggage.setVisibility(View.GONE);
            holder.itemStatus.setTextColor(colorRed);
        } else if (status.contains("Задержка") || status.contains("Delayed")) {
            holder.itemBaggageStatus.setVisibility(View.GONE);
            holder.descriptionBaggage.setVisibility(View.GONE);
            holder.itemStatus.setTextColor(colorOrange);
        }

        setAirlineLogo(holder);

        if (holder.activateBackground) {
            if (status.contains("Прибыл") || status.contains("Arrived") || status.contains("Вылетел") || status.contains("Departed") || status.contains("Посадка закончена") || status.contains("Gate closed") || status.contains("Идет посадка") || status.contains("Boarding") || status.contains("Регистрация закончена") || status.contains("Check-in-close") || status.contains("Идет регистрация") || status.contains("Check-in-open")) {
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemBackgroundGreen));
            } else if (status.contains("Отмена") || status.contains("Cancelled")) {
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemBackgroundRed));
            } else if (status.contains("Задержка") || status.contains("Delayed")) {
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemBackgroundYellow));
            } else {
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorText));
            }
        }
        return view;
    }

    private void setAirlineLogo(ViewHolder holder) {
        switch (holder.itemFlight.getText().toString().substring(0,3)) {
            case "DP-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_pobeda);
                break;
            case "7R-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_rusline);
                break;
            case "SU-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_aeroflot);
                break;
            case "U6-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_ural_airlines);
                break;
            case "KL-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_klm);
                break;
            case "IB-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_iberia);
                break;
            case "9U-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_moldova);
                break;
            case "BA-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_british_airways);
                break;
            case "S7-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_s7_airlines);
                break;
            case "AB-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_berlin);
                break;
            case "TP-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_tap_portugal);
                break;
            case "EY-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_etihad_airways);
                break;
            case "YC-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_ymal);
                break;
            case "KO-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_komiaviatrans);
                break;
            case "AF-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_france);
                break;
            case "A3-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_aegean_airlines);
                break;
            case "LY-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_el_al);
                break;
            case "UT-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_utair);
                break;
            case "FV-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_rossia);
                break;
            case "R2-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_orenair);
                break;
            case "J2-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_azal);
                break;
            case "OK-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_czech_airlines);
                break;
            case "AZ-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_alitalia);
                break;
            case "B2-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_belavia);
                break;
            case "AY-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_finnair);
                break;
            case "O7-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_orenburgie);
                break;
            case "Y7-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_nordstar_airlines);
                break;
            case "KC-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_astana);
                break;
            case "FZ-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_fly_dubai);
                break;
            case "4G-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_gazpromavia);
                break;
            case "7J-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_tajikair);
                break;
            case "ZF-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_azur_air);
                break;
            case "6R-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_alrosa);
                break;
            case "D9-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_donavia);
                break;
            case "JL-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_japan_airlines);
                break;
            case "5B-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_euro_asia_air);
                break;
            case "6Z-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_euro_asia_air);
                break;
            case "GH-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_globus);
                break;
            case "TK-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_turkish_airlines);
                break;
            case "ZM-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_manas);
                break;
            case "R3-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_yakutia);
                break;
            case "SZ-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_somon_air);
                break;
            case "YK-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_avia_traffic_company);
                break;
            case "HY-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_uzbekistan_airways);
                break;
            case "4R-":
            case "RL-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_royal_flight);
                break;
            case "RU-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_bridge_cargo);
                break;
            case "ZG-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_grozny_avia);
                break;
            case "IK-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_pegas_fly);
                break;
            case "EK-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_emirates);
                break;
            case "D2-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_severstal_avia);
                break;
            case "TRH":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_turuhan);
                break;
            case "I4-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_i_fly);
                break;
            case "EL-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_ellinair);
                break;
            case "WZ-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_red_wings);
                break;
            case "N4-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_nordwind_airlines);
                break;
            case "4B-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_aviastar_tu);
                break;
            case "QR-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_qatar_airways);
                break;
            case "QH-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_air_kyrgyzstan);
                break;
            case "KK-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_atlasjet_airlines);
                break;
            case "8Q-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_onur_air);
                break;
            case "I8-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_igavia);
                break;
            case "NN-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_vim_airlines);
                break;
            case "DXT":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_dexter);
                break;
            case "UVT":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_uvt_aero);
                break;
            case "JU-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_airserbia);
                break;
            case "KI-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_krasavia);
                break;
            case "SQ-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_singapore_airlines);
                break;
            case "A4-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_azimut);
                break;
            case "IG-":
                holder.imageViewLogo.setImageResource(R.drawable.drawable_logo_airitaly);
                break;
            default:
                holder.imageViewLogo.setVisibility(View.GONE);
                break;
        }
    }

    private ObjectPlane getObjectPlane(int i) {
        return (ObjectPlane)getItem(i);
    }

    public Filter getFilter() {
        return itemsFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            int count = originalList.size();

            String filterableStringFlight;
            String filterableStringDirection;
            String filterableStringDate;

            List<ObjectPlane> listWithFilter = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                filterableStringFlight = originalList.get(i).getPlaneFlight();
                filterableStringDirection = originalList.get(i).getPlaneDirection();
                filterableStringDate = originalList.get(i).getPlaneTimePlan();

                if (filterableStringFlight.toLowerCase().contains(filterString) || filterableStringDirection.toLowerCase().contains(filterString) || filterableStringDate.toLowerCase().contains(filterString)) {
                    listWithFilter.add(originalList.get(i));
                }
            }
            results.values = listWithFilter;

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<ObjectPlane>) results.values;
            notifyDataSetChanged();
        }
    }

    public void setTrackingInfoFromServer(String sentPlaneFlight, String sentPlaneDirection, String sentPlaneTimePlan) {
        int count = filteredList.size();

        for (int i = 0; i < count; i++) {
            String planeFlight = filteredList.get(i).getPlaneFlight();
            String planeDirection = filteredList.get(i).getPlaneDirection();
            String planeTimePlan = filteredList.get(i).getPlaneTimePlan();

            if (planeFlight.contains(sentPlaneFlight) && planeDirection.contains(sentPlaneDirection) && planeTimePlan.contains(sentPlaneTimePlan)) {
                setInfoTrackingToTrue(i);
            }
        }
    }
}