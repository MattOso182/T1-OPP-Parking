package parkingcontrolsystem.library;

/**
 *
 * @author Team 1 - T.A.P. (The Art of Programming)
 */
public class LicensePlateLibrary {
    private String plateNumber;
    private String provinceCode;  
    private boolean isValid;

   
    public LicensePlateLibrary() {
    }

   
    public LicensePlateLibrary(String plateNumber, String provinceCode) {
        this.plateNumber = plateNumber;
        this.provinceCode = provinceCode;
        this.isValid = validatePlateFormat(plateNumber);
    }

  
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        this.isValid = validatePlateFormat(plateNumber);
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    
    public boolean validatePlateFormat(String plateNumber) {
        
        if (plateNumber == null || plateNumber.length() < 6) {
            return false;
        }

      
        return plateNumber.matches("^[A-Z]{3}\\d{3,4}$");
    }

    public void showPlateInfo() {
        System.out.println("Plate Number: " + plateNumber);
        System.out.println("Province Code: " + provinceCode);
        System.out.println("Is Valid: " + (isValid ? "Yes" : "No"));
    }

    public String getPlateDetails() {
        return "Plate: " + plateNumber +
               "\nProvince Code: " + provinceCode +
               "\nValid Format: " + (isValid ? "Yes" : "No");
    }
}