package utility;

public enum Sheets {
	USER("user"),
	ADMIN ("admin"),
	NONADMIN("nonadmin"); 
	
	private final String sheetValue;

    private Sheets(String sheetValue) {
        this.sheetValue = sheetValue;
    }
    
    public String getSheetValue() {
        return this.sheetValue;
    }
}
