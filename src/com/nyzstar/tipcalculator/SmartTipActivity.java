package com.nyzstar.tipcalculator;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.nyzstar.tipcalculator.model.TipCalculator;

public class SmartTipActivity extends Activity {

	private final static String DEBUG = "DEBUG";
	private final static int intMaxInputLength = 10;
    private FrameLayout btnEasyTip;
    private FrameLayout btnSmartTip;
    private TextView txtAmount;
    private TextView txtTipAmount;
    private TextView txtTotalAmount;
    private RatingBar ratingBarMeal;
    private String currentInput; 
    private double bdAmount;
    private TipCalculator tipCalculator;
    private int[] tipPercents = {0, 5, 10, 15, 20, 25};
    
    
    //--------------------------------------------------------------------------------
    // Called when the activity is first created.
    //--------------------------------------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_tip_layout);
        btnEasyTip = (FrameLayout) findViewById(R.id.btnEasyTip2);
        btnSmartTip = (FrameLayout) findViewById(R.id.btnSmartTip2);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        ratingBarMeal = (RatingBar) findViewById(R.id.ratingBarMeal);
        txtTipAmount = (TextView) findViewById(R.id.txtTipAmount);
        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        
        currentInput = "";
        tipCalculator = new TipCalculator(0, 0, 1);
        ratingBarMeal.setRating(3);
        tipCalculator.setTipPercent(tipPercents[Math.round(3)]);
        updateField();
        
        btnEasyTip.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				startEasyTipActivity();
			}
        });
        ratingBarMeal.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){
			public void onRatingChanged(RatingBar arg0, float rating, boolean arg2) {
				tipCalculator.setTipPercent(tipPercents[Math.round(rating)]);
				updateField();
			}
        });
    }
	
	//listens for a click from one of the number buttons, and calls addNumber appropriately
	public void keypadListener(View v)
	{
		int intCurrentInputLength = currentInput.length();
		int intEndIndex = intCurrentInputLength - 1;
		if (v instanceof ImageButton){
//			Log.v(DEBUG, "DEL");
			if(intCurrentInputLength != 0){
				currentInput = currentInput.substring(0, intEndIndex);
			}
			updateField();
		}else{
			Button b = (Button) v;
			String value = (String) b.getText();
			if((value.compareTo("0") == 0 || value.compareTo("00") == 0) && intCurrentInputLength == 0){

			}else{
				if(intCurrentInputLength + value.length() < intMaxInputLength){
					currentInput = currentInput + value;
				}
			}
			updateField();
		}
	}
	
	private void updateField(){
		displayResult(txtAmount, currentInput);
		try{
			if(currentInput.length()==0){
				bdAmount = 0;
			}else{
				bdAmount = Double.valueOf(currentInput);
			}
			tipCalculator.setAmount(bdAmount/100);
			displayResult(txtTotalAmount, BigDecimal.valueOf(tipCalculator.getTotalAmount()));
			displayResult(txtTipAmount, BigDecimal.valueOf(tipCalculator.getTipAmount()));
		}catch(Exception e){
			//Log.v(DEBUG, e.getMessage());
		}
	}
		
	private void displayResult(TextView v, String strIn){
		String str = strIn.substring(0);
		while(str.length() < 3){
			str = "0" + str;
		}
		String strIntegerPart = str.substring(0, str.length()-2);
		String strDecimalPart = str.substring(str.length()-2, str.length());
		v.setText("$" + strIntegerPart + "." + strDecimalPart);
	}
	
	private void displayResult(TextView v, BigDecimal BDIn){
		String strTotal = BDIn.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		while(strTotal.length()<4){
			strTotal = "0" + strTotal;
		}
		v.setText("$" + strTotal);
	}
	
	private void startEasyTipActivity(){
		Intent intent = new Intent(this, TipCalculatorActivity.class);
		startActivity(intent);
		finish();
	}
}