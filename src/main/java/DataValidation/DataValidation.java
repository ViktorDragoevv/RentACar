package DataValidation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class DataValidation {

    
public static boolean dataLength(TextField inputTextField, Label inputLabel, String validationText, String requiredLength) {
        boolean isDataLength = true;
        String validationString = null;

        if (!inputTextField.getText().matches("\\b\\w" + "{" + requiredLength + "}")) {
            isDataLength = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isDataLength;

    }

	public static boolean LoginValidationUser(TextField inputTextField, Label inputLabel, String validationText) {
		boolean isAlphabet = true;
		String validationString = null;

		if (!inputTextField.getText().matches("[a-z0-9A-Z]+[^\\s-]")) {
			isAlphabet = false;
			validationString = validationText;

		}
		inputLabel.setText(validationString);
		return isAlphabet;

	}
    public static boolean textAlphabet(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isAlphabet = true;
        String validationString = null;

        if ((!inputTextField.getText().matches("[а-я А-Я]+")) && (!inputTextField.getText().matches("[a-z A-Z]+"))) {
            isAlphabet = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isAlphabet;

    }

    public static boolean textNumeric(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNumeric = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumeric = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isNumeric;

    }
    
    public static boolean textDouble(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNumeric = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[+-]?([0-9]*[.])?[0-9]+")) {
            isNumeric = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);
        return isNumeric;

    }

    public static boolean regNumber(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isZID = true;
        String validationString = null;

        if (!inputTextField.getText().matches("(^[A-Z]{2}[0-9]{4}[A-Z]{2}$)|(^[A-Z]{1}[0-9]{4}[A-Z]{2}$)")) {
            isZID = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isZID;

    }


    public static boolean textFieldIsNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        if (inputTextField.getText().isEmpty()) {
            isNull = true;
            validationString = validationText;
        }
       
        inputLabel.setText(validationString);

        return isNull;

    }

}