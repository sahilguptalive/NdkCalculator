package com.learnz.ndkcalculator.act;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learnz.ndkcalculator.R;

public class CalcUI extends Activity implements OnClickListener {

	private static final String TAG = CalcUI.class.getSimpleName();

	int totalOperations = 0;
	int INVALID = -1;

	List<Integer> textNum = new ArrayList<Integer>(2);
	List<Integer> textNumOps = new ArrayList<Integer>(2);
	StringBuilder textFinalToshow = new StringBuilder(40);

	private boolean canAddOperation = false;
	private boolean canReplaceOperation = false;
	private boolean canAddNumbers = true;
	private boolean emptyZeroAdded = false;

	// clear
	// ops_divide
	// ops_multiply
	// ops_subtract
	// ops_add
	// ops_equalto
	// num_1
	// num_2
	// num_3
	// num_4
	// num_5
	// num_6
	// num_7
	// num_8
	// num_9
	// num_0
	// text_result

	static {

		System.loadLibrary("NdkCalculator");
	}

	public native double calculate(String stringContaingNumbersAndOperators);

	public native int display();

	TextView textviewclear;
	TextView textviewops_divide;
	TextView textviewops_multiply;
	TextView textviewops_subtract;
	TextView textviewops_add;
	TextView textviewops_equalto;
	TextView textviewnum_1;
	TextView textviewnum_2;
	TextView textviewnum_3;
	TextView textviewnum_4;
	TextView textviewnum_5;
	TextView textviewnum_6;
	TextView textviewnum_7;
	TextView textviewnum_8;
	TextView textviewnum_9;
	TextView textviewnum_0;
	TextView textviewtext_result;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		setContentView(R.layout.calc_ui);

		LinearLayout rootlayout = (LinearLayout) findViewById(R.id.root_lyaout);

		textviewclear = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.clear));
		textviewops_divide = (TextView) rootlayout
				.findViewWithTag(getResources().getString(R.string.ops_divide));
		textviewops_multiply = (TextView) rootlayout
				.findViewWithTag(getResources()
						.getString(R.string.ops_multiply));
		textviewops_subtract = (TextView) rootlayout
				.findViewWithTag(getResources()
						.getString(R.string.ops_subtract));
		textviewops_add = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.ops_add));
		textviewops_equalto = (TextView) rootlayout
				.findViewWithTag(getResources().getString(R.string.ops_equalto));
		textviewnum_1 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_1));
		textviewnum_2 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_2));
		textviewnum_3 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_3));
		textviewnum_4 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_4));
		textviewnum_5 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_5));
		textviewnum_6 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_6));
		textviewnum_7 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_7));
		textviewnum_8 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_8));
		textviewnum_9 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_9));
		textviewnum_0 = (TextView) rootlayout.findViewWithTag(getResources()
				.getString(R.string.num_0));
		textviewtext_result = (TextView) rootlayout
				.findViewWithTag(getResources().getString(R.string.text_result));

		textviewclear.setOnClickListener(this);
		textviewops_divide.setOnClickListener(this);
		textviewops_multiply.setOnClickListener(this);
		textviewops_subtract.setOnClickListener(this);
		textviewops_add.setOnClickListener(this);
		textviewops_equalto.setOnClickListener(this);
		textviewnum_1.setOnClickListener(this);
		textviewnum_2.setOnClickListener(this);
		textviewnum_3.setOnClickListener(this);
		textviewnum_4.setOnClickListener(this);
		textviewnum_5.setOnClickListener(this);
		textviewnum_6.setOnClickListener(this);
		textviewnum_7.setOnClickListener(this);
		textviewnum_8.setOnClickListener(this);
		textviewnum_9.setOnClickListener(this);
		textviewnum_0.setOnClickListener(this);

		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {

		if (v == textviewops_divide || v == textviewops_multiply
				|| v == textviewops_subtract || v == textviewops_add
				|| v == textviewops_equalto) {

			int locationToAdd = canAddOperation ? (totalOperations++)
					: (canReplaceOperation ? totalOperations : INVALID);

			int operationToAddOrReplace;

			if (v == textviewops_divide) {

				operationToAddOrReplace = Operation.DIVIDE;

			} else if (v == textviewops_multiply) {

				operationToAddOrReplace = Operation.MUTIPLY;

			} else if (v == textviewops_subtract) {

				operationToAddOrReplace = Operation.DIFFERENCE;

			} else if (v == textviewops_add) {

				operationToAddOrReplace = Operation.SUM;

			} else {

				operationToAddOrReplace = Operation.EQUAL_TO;

			}

			canAddNumbers = (operationToAddOrReplace == Operation.EQUAL_TO) ? false
					: true;

			if (locationToAdd != INVALID) {

				appendOrSet(textNumOps, locationToAdd, operationToAddOrReplace);

				Character opsChar = getTextForOperation(operationToAddOrReplace);

				textFinalToshow = canAddOperation ? setCharacterAtLastPosition(
						textFinalToshow, opsChar)
						: resetCharacterAtLastPosition(textFinalToshow, opsChar);

				textviewtext_result.setText(textFinalToshow);

			}

			canReplaceOperation = canReplaceOperation || !canAddOperation;
			canAddOperation = false;
			emptyZeroAdded = false;

			if (operationToAddOrReplace == Operation.EQUAL_TO) {

				canReplaceOperation = true;
				canAddOperation = false;
				canAddNumbers = false;

				// Log.i(TAG,
				// "final cal result: "+calculate(textFinalToshow.toString()));
				// Log.i(TAG, "final cal result: "+display());

				double valueCalculated = calculate(textFinalToshow.toString());
				textFinalToshow.append(valueCalculated);
				textviewtext_result.setText(textFinalToshow);

			}

		}

		if (v == textviewclear) {

			textNumOps.clear();
			textNum.clear();

			totalOperations = 0;

			clearStringBuilder(textFinalToshow);
			textviewtext_result.setText(textFinalToshow);

			emptyZeroAdded = false;
			canAddOperation = false;
			canReplaceOperation = false;
			canAddNumbers = true;

		}

		if ((v == textviewnum_1 || v == textviewnum_2 || v == textviewnum_3
				|| v == textviewnum_4 || v == textviewnum_6
				|| v == textviewnum_7 || v == textviewnum_8
				|| v == textviewnum_9 || v == textviewnum_0)
				&& canAddNumbers) {

			canAddOperation = true;

			int valueToAdd;

			if (v == textviewnum_1) {

				valueToAdd = 1;

			} else if (v == textviewnum_2) {

				valueToAdd = 2;

			} else if (v == textviewnum_3) {

				valueToAdd = 3;

			} else if (v == textviewnum_4) {

				valueToAdd = 4;

			} else if (v == textviewnum_5) {

				valueToAdd = 5;

			} else if (v == textviewnum_6) {

				valueToAdd = 6;

			} else if (v == textviewnum_7) {

				valueToAdd = 7;

			} else if (v == textviewnum_8) {

				valueToAdd = 8;

			} else if (v == textviewnum_9) {

				valueToAdd = 9;

			} else {

				valueToAdd = 0;

			}

			int curentNumSet = getAtLocation(textNum, totalOperations, 0);
			int newNumToSet = curentNumSet * 10 + valueToAdd;
			appendOrSet(textNum, totalOperations, newNumToSet);

			if (curentNumSet != newNumToSet) {
				emptyZeroAdded = false;
				textFinalToshow.append(valueToAdd);
				textviewtext_result.setText(textFinalToshow);
			} else {
				if (curentNumSet == 0 && !emptyZeroAdded) {
					textFinalToshow.append(valueToAdd);
					textviewtext_result.setText(textFinalToshow);
					emptyZeroAdded = true;

				}
			}

		}

	}

	private char getTextForOperation(int operationToAddOrReplace) {
		char opschar;

		switch (operationToAddOrReplace) {

		case Operation.SUM:
			opschar = '+';
			break;
		case Operation.DIVIDE:
			opschar = '/';
			break;
		case Operation.MUTIPLY:
			opschar = '*';
			break;
		case Operation.DIFFERENCE:
			opschar = '-';
			break;
		case Operation.EQUAL_TO:
			opschar = '=';
			break;

		default:
			throw new IllegalArgumentException(
					"operation passed is not supported : "
							+ operationToAddOrReplace);

		}

		return opschar;
	}

	private int refreshStringToEditForNum() {

		return 0;
	}

	interface Operation {
		int NO_OPS = 0;
		int SUM = 1;
		int DIVIDE = 2;
		int MUTIPLY = 3;
		int DIFFERENCE = 4;
		int EQUAL_TO = 5;
	}

	interface Number {
		int NUM_INITIAL = 0;
		int NUM_1 = 1;
		int NUM_2 = 2;
		int MaxNumbersAllowed = 2;
	}

	private void clearStringBuilder(StringBuilder stringBuilderToClear) {
		stringBuilderToClear.delete(0, stringBuilderToClear.length());

	}

	private StringBuilder resetCharacterAtLastPosition(
			StringBuilder stringBuilder, char characterToReplace) {

		if (stringBuilder != null && stringBuilder.length() > 0) {
			stringBuilder.setCharAt(stringBuilder.length() - 1,
					characterToReplace);
		}
		return stringBuilder;

	}

	private StringBuilder setCharacterAtLastPosition(
			StringBuilder stringBuilder, char characterToAdd) {
		if (stringBuilder != null) {
			stringBuilder.append(characterToAdd);
		}
		return stringBuilder;
	}

	private StringBuilder appendOrSet(StringBuilder stringBuilder,
			int locationToDoOperation, char charToAppendOrSet) {
		if (stringBuilder.length() > locationToDoOperation) {
			stringBuilder.setCharAt(locationToDoOperation, charToAppendOrSet);
		} else if (stringBuilder.length() == locationToDoOperation) {
			stringBuilder.append(charToAppendOrSet);
		} else {
			throw new IllegalArgumentException(
					"length of string builder less than location to do operation");
		}

		return stringBuilder;

	}

	private <T> List<T> appendOrSet(List<T> listToOperationOn,
			int locationToDoOperation, T objectToAppendOrSet) {

		if (listToOperationOn.size() > locationToDoOperation) {

			listToOperationOn.set(locationToDoOperation, objectToAppendOrSet);

		} else if (listToOperationOn.size() == locationToDoOperation) {

			listToOperationOn.add(objectToAppendOrSet);

		} else {

			throw new IllegalArgumentException(
					"length of list less than location to do operation");

		}

		return listToOperationOn;

	}

	private <T> T getAtLocation(List<T> listToOperationOn,
			int locationToDoOperation, T defaultValue) {
		if (listToOperationOn.size() == 0
				|| listToOperationOn.size() <= locationToDoOperation)
			return defaultValue;
		else
			return listToOperationOn.get(locationToDoOperation);

	}

}