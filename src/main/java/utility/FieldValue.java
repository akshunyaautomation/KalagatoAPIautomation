package utility;

public enum FieldValue {
	USERNAME("username"),
	PASSWORD("password"),
	FIRSTNAME("firstname"),
	LASTNAME("lastname"),
	NEWPASSWORD("newpassword"),
	EMAIL("email");
	
	private final String fieldValue;

	private FieldValue(String _fieldValue) {
		this.fieldValue = _fieldValue;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

}
