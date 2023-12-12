/**
 * PersonID class is simply a validation class for person's id
 * 
 * PersonID's purpose is to validate and return a validated finnish ID
 *
 * @author Vanhatupa Niilo 
 * 
 */
public class PersonID {
    private String day;
    private String month;
    private String year;
    private char century;
    private String individualNumber;
    private char uniqueIndentifier;
    /**
     * The constructor of PersonID class
     * 
     * The constructor checks the basics of the id 
     * and then calls the setters for more advanced validation
     * 
     * @param id the id that will be validated/made to an actual id
     */
    public PersonID(String id){
        char[] idArray = id.toCharArray();
        if(idArray.length != 11){
            throw new IllegalArgumentException("Invalid id");
        }
        for (int i = 0; i < 6; i++) {
            if(Character.isDigit(idArray[i])){
                switch (i) {
                    case 0:
                        setDay(idArray[i], idArray[i+1]);
                        break;
                    case 2:
                        setMonth(idArray[i], idArray[i+1]);
                        break;
                    case 4:
                        setYear(idArray[i], idArray[i+1]);
                        break;
                    default:
                        break;
                }
            } 
            else{
                return;
            }
        }
        setCentury(idArray[6]);
        String indNum = "";
        for (int i = 7; i <= 9; i++) {
            indNum += idArray[i];
        }
        setIndNum(indNum);
        setIndentifier(idArray[10]);
    }
    /**
     * SetIndefier method of the PersonID class
     * 
     * SetIndefier method checks the indefier of the given id
     * if it is valid or not. 
     * 
     * @param indentifier indefier to validate
     * @throws IllegalArgumentException
     */
    public void setIndentifier(char indentifier){
        if(Character.isDigit(indentifier) || Character.isLetter(indentifier)){
            uniqueIndentifier = indentifier;
        }
        else {
            throw new IllegalArgumentException("Invalid indentifier");
        }
    }
    /**
     * SetDay method of the PersonID class
     * 
     * SetDay Validatess and the sets the day for the ID
     * day cannot be 00 or over 31. 
     * @param firstDigit the first digit of the day to validate
     * @param secondDigit the second digit of the day to validate
     * @throws IllegalArgumentException
     */
    public void setDay(char firstDigit, char secondDigit){
        int tempDigit1 = Character.getNumericValue(firstDigit);
        int tempDigit2 = Character.getNumericValue(secondDigit);

        switch (tempDigit1) {
            case 0:
                if(tempDigit2 == 0){
                    throw new IllegalArgumentException("Day cannot be 00 ");
                }
                day = "0" + Integer.toString(tempDigit2);
                break;
            case 1:
                day = "1" + Integer.toString(tempDigit2);
                break;
            case 2:
                day = "2" + Integer.toString(tempDigit2);
                break;
            case 3:
                if(tempDigit1 < 2){
                    day = "3" + Integer.toString(tempDigit2);
                    break;
                }
                throw new IllegalArgumentException("Day cannot be over 31");
            default:
                throw new IllegalArgumentException("Invalid day");
        }
    }
    /**
     * SetMonth method of the PersonID class
     * 
     * SetMonth Validatess and the sets the month for the ID
     * month cannot be 00 or over 12. 
     * 
     * @param firstDigit the first digit of the month to validate
     * @param secondDigit the second digit of the month to validate
     * @throws IllegalArgumentException
     */
    public void setMonth(char firstDigit, char secondDigit){
        int tempDigit1 = Character.getNumericValue(firstDigit);
        int tempDigit2 = Character.getNumericValue(secondDigit);

        switch (tempDigit1) {
            case 0:
                if(tempDigit2 == 0){
                    throw new IllegalArgumentException("Day cannot be 00 ");
                }
                month = "0" + Integer.toString(tempDigit2);
                break;
            case 1:
                if(tempDigit2 > 2){
                        throw new IllegalArgumentException("Month cannot be over 12");
                    }    
                month = "1" + Integer.toString(tempDigit2);
                break;
            default:
                throw new IllegalArgumentException("Invalid month");
        }
    }
    /**
     * SetYear method of the PersonID class
     * 
     * SetYear sets the year for the ID
     * 
     * @param firstDigit first digit of the year
     * @param secondDigit second digit of the year
     */
    public void setYear(char firstDigit, char secondDigit){
        year = Character.toString(firstDigit) + Character.toString(secondDigit);
    
    }
    /**
     * SetCentury Method of the PersonId class
     * 
     * SetCentury validates the century character of the id
     * 
     * @param c the century indefier to validate
     * @throws IllegalArgumentException 
     */
    public void setCentury(char c){
        switch (c) {
            case '+':
                break;
            case '-':
                break;
            case 'A':
                break; 
            default:
                throw new IllegalArgumentException("Invalid century!");
        }
        century = c;
    }
    /**
     * SetIndNum of the PersonID class 
     * 
     * SetIndnum Validates the indivitual number of the id
     * 
     * @param indNum indefier number to validate
     */
    public void setIndNum(String indNum){
        try {
            Integer.parseInt(indNum);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid individual number!");
        }
        individualNumber = indNum;
        
    }
    /**
     * GetValidatedID method of the PersonID class
     * 
     * method returns id as a String
     * 
     * @return validated ID
     */
    public String getValidatedID(){
        return (day + month + year + century + individualNumber + uniqueIndentifier);
    }
}
