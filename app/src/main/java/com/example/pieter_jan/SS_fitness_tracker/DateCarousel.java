package com.example.pieter_jan.SS_fitness_tracker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Created by pieter-jan on 3/1/2017.
 */

public class DateCarousel extends HorizontalScrollView implements AbsListView.OnScrollListener {

    private HorizontalScrollView dateCarousel;
    private LinearLayout dateCarouselContainer;
    private LocalDate dt;
    private LayoutInflater mInflater;

    private ArrayList<CarouselClickListener> clickListeners = new ArrayList<>();

    public DateCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.date_carousel, this, true);

        dateCarousel = (HorizontalScrollView) v.findViewById(R.id.dateCarousel);
        dateCarouselContainer = (LinearLayout) v.findViewById(R.id.dateCarouselContainer);

        setupDateCarousel();

    }

    public void addClickListener(CarouselClickListener listener){
        this.clickListeners.add(listener);
    }

    private void setupDateCarousel() {
        dt = new LocalDate();

        int daysExtraToShow = 15;

        LocalDate dateToStartFrom = dt.plusDays(daysExtraToShow);

        for (int i = 31; i >= 0; i--) {
            View itemDateCarouselLayout = mInflater.inflate(R.layout.item_date_carousel, null);
            itemDateCarouselLayout.setPadding(
                    getResources().getDimensionPixelSize(R.dimen.default_margin_1), 0,
                    getResources().getDimensionPixelSize(R.dimen.default_margin_1), 0);

            final TextView dayOfMonthTV = (TextView) itemDateCarouselLayout.findViewById(R.id.dayOfMonthTV);
            final TextView monthOfYearTV = (TextView) itemDateCarouselLayout.findViewById(R.id.monthOfYearTV);

            dayOfMonthTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            monthOfYearTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            dayOfMonthTV.setTypeface(null, Typeface.NORMAL);
            monthOfYearTV.setTypeface(null, Typeface.NORMAL);

            LocalDate dateAsLocal = dateToStartFrom.minusDays(i);
            DateTimeFormatter monthFormatter = DateTimeFormat.forPattern("MMM");

            dayOfMonthTV.setText(String.valueOf(dateAsLocal.getDayOfMonth()));
            monthOfYearTV.setText(String.valueOf(monthFormatter.print(dateAsLocal)));

            dateCarouselContainer.addView(itemDateCarouselLayout);

            if (i == daysExtraToShow) { // this focuses on today's date on startup
                focusOnView(dateCarouselContainer, itemDateCarouselLayout);
            }

            // In case you want some days to be faded and unclickable
//            if (i < daysExtraToShow) {
//                fadeOutView(itemDateCarouselLayout);
//            } else if (i >= daysExtraToShow) {
                itemDateCarouselLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        focusOnView(dateCarouselContainer, view);

                        DateTimeFormatter formatterForDisplay = DateTimeFormat.forPattern("dd/MMM/yyyy");

                         DateTime formattedDate = formatterForDisplay.parseDateTime(
                                dayOfMonthTV.getText().toString()
                                        + "/"
                                        + monthOfYearTV.getText().toString()
                                        + "/"
                                        + dt.year().getAsText());

                        String finalDesiredDate = formattedDate.toLocalDate().toString();
//
//                        String todaysDateFormatted = dt.getYear() + "-" + dt.getMonthOfYear() + "-" + dt.getDayOfMonth();

//                        String finalDesiredDate = formattedDate.getYear() + "-" + formattedDate.getMonthOfYear() + "-" + formattedDate.getDayOfMonth();

//                        DateTimeFormatter formatterForStorage = DateTimeFormat.forPattern("dd/mm/yyyy");

//                        String finalDesiredDate = formattedDate.toString(formatterForStorage);

                        for (CarouselClickListener listener : clickListeners) {
                            listener.handleClick(finalDesiredDate);
                        }
                    }
                });
            }

//        }
    }


    private void focusOnView(final LinearLayout dateCarouselContainer, final View view) {
        TextView dayOfMonthTV;
        TextView monthOfYearTV;
        LinearLayout dateEntryLayout;

        // Loop over all dates and set them to the normal layout
        for (int i = 0; i < dateCarouselContainer.getChildCount(); i++) {
            dateEntryLayout = (LinearLayout) dateCarouselContainer.getChildAt(i).findViewById(R.id.dateEntryLayout);
            dayOfMonthTV = (TextView) dateCarouselContainer.getChildAt(i).findViewById(R.id.dayOfMonthTV);
            monthOfYearTV = (TextView) dateCarouselContainer.getChildAt(i).findViewById(R.id.monthOfYearTV);

            dateEntryLayout.setBackgroundColor(0);
            dayOfMonthTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            monthOfYearTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            dayOfMonthTV.setTypeface(null, Typeface.NORMAL);
            monthOfYearTV.setTypeface(null, Typeface.NORMAL);
        }

        // Add effects to the focused view
        dateEntryLayout = (LinearLayout) view.findViewById(R.id.dateEntryLayout);
        dayOfMonthTV = (TextView) view.findViewById(R.id.dayOfMonthTV);
        monthOfYearTV = (TextView) view.findViewById(R.id.monthOfYearTV);

        dateEntryLayout.setBackgroundColor(getResources().getColor(R.color.offwhite_transparent));
        dayOfMonthTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        monthOfYearTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        dayOfMonthTV.setTypeface(null, Typeface.BOLD);
        monthOfYearTV.setTypeface(null, Typeface.BOLD);
    }

    private void fadeOutView(View view) {
        view.setAlpha(0.5f);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        TODO: updateDateCarousel();
    }


    public interface CarouselClickListener {
//        void handleClick(String todayDate, String finalDate);
        void handleClick(String desiredDate);
    }


}
