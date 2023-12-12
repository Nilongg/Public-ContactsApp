/**
 * Person class is the class for creating saveable contacts/persons
 * 
 * Person must have a firstname, lastname, phone number and an ID.
 * Email and address are optional.
 *
 * @author Vanhatupa Niilo 
 * 
 */
class Person{
    private String personalId;  
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email; 

    enum checkOption {
        DOMAIN,
        USERNAME,
        NAME,
        ADDRESS;
    }
    /**
     * The constructor of the Person class
     * 
     * Method calls the setter methods for validation
     * @param id id of the person
     * @param fName first name of the person
     * @param lName last name of the person
     * @param pNum phone number of the person
     * @param address address of the person
     * @param email email address of the person
     */
    public Person(String id, String fName, String lName, String pNum, String address, String email){
        setId(id);
        setFirstName(fName);
        setLastName(lName);
        setPhoneNumber(pNum);
        setAddress(address);
        setEmail(email);
        
    }
    /**
     * Default constructor of the person class 
     */
    public Person(){
    }
    /**
     * SetLastName method of the person class
     * 
     * Method validates the last name.
     * 
     * @param ln lastname to validate
     * @throws IllegalArgumentException
     */
    public void setLastName(String ln){
        if(ln == null)
            throw new IllegalArgumentException("lastname name cannot be null");
        if(checkForDigit(ln) || ln.length() < 2 || ln.equals(firstName) || checkForSpecial(ln, checkOption.NAME))
            throw new IllegalArgumentException("Invalid last name");
        
        lastName = ln;
        
    }
    /**
     * SetId method of the person class
     * 
     * validates the id using PersonID class
     * 
     * @param id id of the person
     */
    public void setId(String id){
        if(id == null)
            throw new IllegalArgumentException("Id cannot be null");
        PersonID pID = new PersonID(id);
        personalId = pID.getValidatedID();
    
    }
    /**
     * setFirtName method of the person class
     * 
     * Method validates and sets the first name
     * @param fn fistname to validate
     */
    public void setFirstName(String fn){
        if(fn == null)
            throw new IllegalArgumentException("first name cannot be null");
        if(fn.length() < 2 || checkForDigit(fn) || checkForSpecial(fn, checkOption.NAME))
            throw new IllegalArgumentException("Invalid first name");
        firstName = fn;
    }
    /**
     * setPhoneNumber method of the person class
     * 
     * Method validates the phone number using checkForDigit method
     * 
     * @param pNumber the phone number to validate
     */
    public void setPhoneNumber(String pNumber){ 
        if(pNumber == null)
            throw new IllegalArgumentException("Phone number cannot be null");
        if(pNumber.length() < 5 || !(checkForDigit(pNumber))){
            throw new IllegalArgumentException("Phone number must be atleast 5 numbers");
        }
        phoneNumber = pNumber;

    }
    /**
     * setAddress method of the person class
     * 
     * method validates the address and then sets it
     * 
     * @param add address to validate, can be null/empty string
     */
    public void setAddress(String add){
        if(add == null || add.isEmpty()){
            return;
        } 
        String[] splittedString = add.split(" ");
        
        if(splittedString.length != 2){
            throw new IllegalArgumentException("Invalid address format");
        }
        if(checkForSpecial(splittedString[0], checkOption.ADDRESS) || checkForSpecial(splittedString[1], checkOption.ADDRESS)){
            throw new IllegalArgumentException("No special characters");
        }
        boolean hasDigit = checkForDigit(splittedString[0]);
        boolean hasDigit2 = checkForDigit(splittedString[1]);
        if(!hasDigit && hasDigit2 || hasDigit && !hasDigit2){
            address = add;
        }
        else{
            throw new IllegalArgumentException("Invalid address");
        }
        
    }
    /**
     * setEmail method of the person class
     * 
     * Method validates and sets the email for the person
     * 
     * @param email email to validate
     * @throws IllegalArgumentException
     */
    public void setEmail(String email) throws IllegalArgumentException{
        if(email == null || email.length() <= 0){
            return;
        }
        if(!(email.contains("@")) || email.length() < 13){
            throw new IllegalArgumentException("Invalid email");
        }
        String[] emailArray = email.split("@");

        String domain = emailArray[1];
        String username = emailArray[0];

        if(checkForSpecial(username, checkOption.USERNAME) && checkForSpecial(domain, checkOption.DOMAIN)){
            throw new IllegalArgumentException("No special characters in email username!");
        }
       
        String[] domainCheck = domain.split("\\.");
        if(username.length() < 6 || domainCheck[0].length() < 3 || domainCheck[1].length() < 2){
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = email;
    }
    /**
     * checkForDigit method of the person class
     * 
     * checks for digits in a string. 
     * 
     * @param info the info to validate
     * @return true or false depending if the string contains digit
     */
    public boolean checkForDigit(String info){
        char[] ca = info.toCharArray();

        for (char c : ca) {
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }
    /**
     * checkForSpecial method of the person class
     * 
     * Method checks for special characters in a string
     * tranformed to a char array. 
     * 
     * @param info the value to check
     * @param option the option for what to check
     * @return true or false, depending if special characters are found
     */
    private boolean checkForSpecial(String info, checkOption option){
        char[] ca = info.toCharArray();
        
        switch (option) {
            case USERNAME:
                for (char c : ca) {
                    if(!Character.isLetterOrDigit(c)){
                        return true;
                    }
                }
                break;
            case DOMAIN:
                for (char c : ca) {
                    if(!Character.isLetterOrDigit(c) || c != '.'){
                        return true;
                    }
                }
                break;
            case NAME:
                for (char c : ca) {
                    if(!Character.isAlphabetic(c)){
                        return true;
                    }
                }
                break;
            case ADDRESS:
                for (char c : ca) {
                    if(!Character.isLetterOrDigit(c)){
                        return true;
                    }
                }
                break;
            default:
                return false;
        }
        return false;
    }
    /**
     * getLastName method of the person class 
     *
     * @return the lastname of the person
     */
    public String getLastName(){
        return lastName;
    }
    /**
     * getID method of the person class 
     *
     * @return the Id of the person
     */
    public String getId(){
        return personalId;
    }
    /**
     * getFirstName method of the person class 
     *
     * @return the firstname of the person
     */
    public String getFirstName(){
        return firstName;
    }
    /**
     * getPhoneNumber method of the person class 
     *
     * @return the phone number of the person
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
    /**
     * getAddress method of the person class 
     *
     * @return the home address of the person
     */
    public String getAddress(){
        return address;
    }
    /**
     * getEmail method of the person class 
     *
     * @return the email address of the person
     */
    public String getEmail(){
        return email;
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }
}
