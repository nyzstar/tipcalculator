package com.nyzstar.tipcalculator;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nyzstar.tipcalculator.model.TipCalculator;

public class TipCalculatorActivity extends Activity {
	/** Called when the activity is first created. */
	private final static String DEBUG = "TipCalculatorActivity";
	public final static String PREFS = "TipPreference";
	public final static String EASY_MODE = "easy_mode";
	private final static int intMaxInputLength = 10;
	private TextView txtAmount;
	private TextView txtTipPercent;
	private TextView txtTipAmount;
	private TextView txtTotalAmount;
	private TextView txtPeopleNum;
	private TextView txtAvgAmount;
	private TextView txtPeopleNumError;
	private TextView txtAmountError;
	private LinearLayout boxAmount;
	private LinearLayout boxTipPercent;
	private LinearLayout boxTipAmount;
	private LinearLayout boxTotalAmount;
	private LinearLayout boxPeopleNum;
	private LinearLayout boxAvgAmount;

	// private TableRow rowTipPercent;
	// private TableRow rowTipAmount;
	// private TableRow rowTotalAmount;
	// private TableRow rowPeopleNum;
	// private TableRow rowAvgAmount;
	private TextView currentTextView;
	// private TextView txtWarningAmount;
	// private TextView txtWarningPeople;
	private String currentInput;
	TipCalculator TipCalculator;
	private double dbAmount;
	private double dbTipPercent;
	private double dbTipAmount;
	private double dbTotalAmount;
	private int intPeopleNum;
	private double dbAvgAmount;
	private FrameLayout btnEasyTip;
	private FrameLayout btnSmartTip;
	private final static String strWarningAmount = "!";
	private final static String strWarningPeopleNum = "!";

	private final static int intBackgroundColor = 0x55FF0000;
	private final static int intYellowColor = 0x55FFFF00;

	// Facebook facebook = new Facebook("284338331673268");

	// --------------------------------------------------------------------------------
	//
	// --------------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS, 0);
		boolean easyMode = settings.getBoolean(EASY_MODE, true);

		if (!easyMode) {// smart mode
			startSmartTipActivity();
		}

		setContentView(R.layout.home_layout);
		txtAmount = (TextView) findViewById(R.id.txtAmount);
		txtTipAmount = (TextView) findViewById(R.id.txtTipAmount);
		txtTipPercent = (TextView) findViewById(R.id.txtTipPercent);
		txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
		txtPeopleNum = (TextView) findViewById(R.id.txtPeopleNum);
		txtAvgAmount = (TextView) findViewById(R.id.txtAvgAmount);
		txtPeopleNumError = (TextView) findViewById(R.id.txtPeopleNumError);
		txtAmountError = (TextView) findViewById(R.id.txtAmountError);
		boxAmount = (LinearLayout) findViewById(R.id.boxAmount);
		boxTipPercent = (LinearLayout) findViewById(R.id.boxTipPercent);
		boxTipAmount = (LinearLayout) findViewById(R.id.boxTipAmount);
		boxTotalAmount = (LinearLayout) findViewById(R.id.boxTotalAmount);
		boxPeopleNum = (LinearLayout) findViewById(R.id.boxPeopleNum);
		boxAvgAmount = (LinearLayout) findViewById(R.id.boxAvgAmount);
		currentTextView = txtAmount;

		btnSmartTip = (FrameLayout) findViewById(R.id.btnSmartTip1);
		btnEasyTip = (FrameLayout) findViewById(R.id.btnEasyTip1);

		// Initialize the private variables
		dbAmount = 0;
		dbTipPercent = 15;
		dbTipAmount = 0;
		dbTotalAmount = 0;
		intPeopleNum = 1;
		dbAvgAmount = 0;
		currentInput = "";
		TipCalculator = new TipCalculator(dbAmount, dbTipPercent, intPeopleNum);

		// Initialize the display
		displayResult(txtAmount, String.valueOf(Math.round(dbAmount)));
		displayResultTipPercent(txtTipPercent,
				String.valueOf(Math.round(dbTipPercent)));
		displayResult(txtTotalAmount, String.valueOf(Math.round(dbTotalAmount)));
		displayResultPeople(txtPeopleNum, String.valueOf(intPeopleNum));
		displayResult(txtTipAmount, String.valueOf(Math.round(dbTipAmount)));
		displayResult(txtAvgAmount, String.valueOf(Math.round(dbAvgAmount)));
		boxAmount.setBackgroundColor(intBackgroundColor);
		currentTextView = txtAmount;
		currentInput = "";
		txtPeopleNumError.setText("");
		txtAmountError.setText(strWarningAmount);

		btnSmartTip.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startSmartTipActivity();
			}
		});

		OnClickListener rowListener = new OnClickListener() {
			public void onClick(View arg0) {
				boxAmount.setBackgroundColor(getResources().getColor(
						R.color.dark_grey_2));
				boxTipPercent.setBackgroundColor(getResources().getColor(
						R.color.dark_grey_2));
				boxTipAmount.setBackgroundColor(getResources().getColor(
						R.color.dark_grey_2));
				boxTotalAmount.setBackgroundColor(getResources().getColor(
						R.color.dark_grey_2));
				boxPeopleNum.setBackgroundColor(getResources().getColor(
						R.color.dark_grey_2));
				arg0.setBackgroundColor(intBackgroundColor);
				currentTextView = (TextView) ((ViewGroup) ((ViewGroup) arg0)
						.getChildAt(1)).getChildAt(0);
				currentInput = "";
			}
		};

		boxAmount.setOnClickListener(rowListener);
		boxTipPercent.setOnClickListener(rowListener);
		boxTipAmount.setOnClickListener(rowListener);
		boxTotalAmount.setOnClickListener(rowListener);
		boxPeopleNum.setOnClickListener(rowListener);
	}

	// listens for a click from one of the number buttons, and calls addNumber
	// appropriately
	public void keypadListener(View v) {

		int intCurrentInputLength = currentInput.length();
		int intEndIndex = intCurrentInputLength - 1;
		if (v instanceof ImageButton) {
			// Log.v(DEBUG, "DEL");
			if (intCurrentInputLength != 0) {
				currentInput = currentInput.substring(0, intEndIndex);
			}
			updateField();
		} else {
			Button b = (Button) v;
			String value = (String) b.getText();
			if ((value.compareTo("0") == 0 || value.compareTo("00") == 0)
					&& intCurrentInputLength == 0) {

			} else {
				if (intCurrentInputLength + value.length() < intMaxInputLength) {
					currentInput = currentInput + value;
				}
			}
			updateField();
		}
	}

	private void updateField() {
		if (currentTextView.equals(txtAmount)) {
			displayResult(currentTextView, currentInput);
			try {
				if (currentInput.length() == 0) {
					dbAmount = 0;
					// TODO
					txtAmountError.setText(strWarningAmount);
				} else {
					dbAmount = Double.valueOf(currentInput);
					// TODO
					txtAmountError.setText("");
					// txtWarningAmount.setText("");
				}
				TipCalculator.setAmount(dbAmount / 100);
				displayResult(txtTotalAmount,
						BigDecimal.valueOf(TipCalculator.getTotalAmount()));
				displayResult(txtTipAmount,
						BigDecimal.valueOf(TipCalculator.getTipAmount()));
				displayResult(txtAvgAmount,
						BigDecimal.valueOf(TipCalculator.getAvgAmount()));
			} catch (Exception e) {
				// Log.v(DEBUG, e.getMessage());
			}

		} else if (currentTextView.equals(txtTipAmount)) {
			displayResult(currentTextView, currentInput);
			try {
				if (currentInput.length() == 0) {
					dbTipAmount = 0;
				} else {
					dbTipAmount = Double.valueOf(currentInput);
				}
				TipCalculator.setTipAmount(dbTipAmount / 100);
				displayResult(txtTotalAmount,
						BigDecimal.valueOf(TipCalculator.getTotalAmount()));
				displayResultTipPercent(txtTipPercent,
						BigDecimal.valueOf(TipCalculator.getTipPercent()));
				displayResult(txtAvgAmount,
						BigDecimal.valueOf(TipCalculator.getAvgAmount()));
			} catch (Exception e) {
				// Log.v(DEBUG, e.getMessage());
			}
		} else if (currentTextView.equals(txtTipPercent)) {
			displayResultTipPercent(currentTextView, currentInput);
			try {
				if (currentInput.length() == 0) {
					dbTipPercent = 0;
				} else {
					dbTipPercent = Double.valueOf(currentInput);
				}
				TipCalculator.setTipPercent(dbTipPercent);
				displayResult(txtTotalAmount,
						BigDecimal.valueOf(TipCalculator.getTotalAmount()));
				displayResult(txtTipAmount,
						BigDecimal.valueOf(TipCalculator.getTipAmount()));
				displayResult(txtAvgAmount,
						BigDecimal.valueOf(TipCalculator.getAvgAmount()));
			} catch (Exception e) {
				// Log.v(DEBUG, e.getMessage());
			}
		} else if (currentTextView.equals(txtPeopleNum)) {
			displayResultPeople(currentTextView, currentInput);
			try {
				if (currentInput.length() == 0) {
					intPeopleNum = 0;
					// TODO
					txtPeopleNumError.setText(strWarningPeopleNum);
					// txtWarningPeople.setText(strWarningPeopleNum);
				} else {
					intPeopleNum = Integer.valueOf(currentInput);
					// TODO
					txtPeopleNumError.setText("");
					// txtWarningPeople.setText("");
				}
				TipCalculator.setPeopleNum(intPeopleNum);
				// displayResult(txtTotalAmount,
				// BigDecimal.valueOf(TipCalculator.getTotalAmount()));
				// displayResult(txtTipAmount,
				// BigDecimal.valueOf(TipCalculator.getTipAmount()));
				displayResult(txtAvgAmount,
						BigDecimal.valueOf(TipCalculator.getAvgAmount()));
			} catch (Exception e) {
				// Log.v(DEBUG, e.getMessage());
			}
		} else {// total amount
			displayResult(currentTextView, currentInput);
			try {
				if (currentInput.length() == 0) {
					dbTotalAmount = 0;
				} else {
					dbTotalAmount = Double.valueOf(currentInput);
				}
				TipCalculator.setTotalAmount(dbTotalAmount / 100);
				displayResult(txtTipAmount,
						BigDecimal.valueOf(TipCalculator.getTipAmount()));
				displayResultTipPercent(txtTipPercent,
						BigDecimal.valueOf(TipCalculator.getTipPercent()));
				displayResult(txtAvgAmount,
						BigDecimal.valueOf(TipCalculator.getAvgAmount()));
			} catch (Exception e) {
				// Log.v(DEBUG, e.getMessage());
			}
		}
	}

	private void displayResult(TextView v, String strIn) {
		String str = strIn.substring(0);
		while (str.length() < 3) {
			str = "0" + str;
		}
		String strIntegerPart = str.substring(0, str.length() - 2);
		String strDecimalPart = str.substring(str.length() - 2, str.length());
		v.setText("$" + strIntegerPart + "." + strDecimalPart);
	}

	private void displayResultTipPercent(TextView v, String strIn) {
		String str = strIn.substring(0);
		if (str.length() == 0) {
			str = "0" + str;
		}
		v.setText(str + "%");
	}

	private void displayResultTipPercent(TextView v, BigDecimal BDIn) {
		String strTotal = BDIn.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
		v.setText(strTotal + "%");
	}

	private void displayResultPeople(TextView v, String strIn) {
		if (strIn.length() == 0) {
			v.setText("0");
		} else {
			v.setText(strIn);
		}
	}

	private void displayResult(TextView v, BigDecimal BDIn) {
		String strTotal = BDIn.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

		while (strTotal.length() < 4) {
			strTotal = "0" + strTotal;
		}
		v.setText("$" + strTotal);
	}

	private void startSmartTipActivity() {

		SharedPreferences settings = getSharedPreferences(PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(EASY_MODE, false);
		editor.commit();
		
		Intent intent = new Intent(this, SmartTipActivity.class);
		startActivity(intent);
		finish();
	}

}