import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ContactHandler class has the methods for writing, reading, deleting and updating 
 * contacts. 
 * 
 * Shortly all the file handling is done in this class.
 *
 * @author Vanhatupa Niilo 
 * 
 */
public class ContactHandler {
    private String fId;
    private String fFirstName;
    private String fLastName;
    private String fPhone;
    private String fEmail;
    private String fAddress; 

    /**
     * The constructer of the ContactHandler 
     * 
     * Constructor only sets the format for how the data will be saved.
     * Also creates the data file for contacts if it doesn't exist
     * 
     */
    public ContactHandler() {
        try {
            createContactsFile();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        setFormat();
    }
    
    /**
     * The create method creates a new contact to the file.
     * 
     * Create method maps the user's entered info to a hashmap and after that,
     * writes the key value pairs to the txt file using fileWriter.
     * 
     * @param p person object
     * @throws Exception 
     */
    public void create(Person p) throws Exception {
        HashMap<String, String> personInfo = new HashMap<String,String>();
        
        personInfo.put(fId, p.getId());
        personInfo.put(fFirstName, p.getFirstName());
        personInfo.put(fLastName, p.getLastName());
        personInfo.put(fPhone, p.getPhoneNumber());
        personInfo.put(fEmail, p.getEmail());
        personInfo.put(fAddress, p.getAddress());
        
        for (String ln : read()) {
            if(ln.contains(p.getId())){
                throw new Exception("Contact with a same id found");
            }
        }

        FileWriter writer = new FileWriter("data.txt", true);
        
        int totalPairs = personInfo.size();
        int counter = 0; 
        for (String key : personInfo.keySet()) {
            counter++;
            writer.write(key+":"+personInfo.get(key));
            if(counter == totalPairs){
                break;
            }
            writer.write(",");   
        }
        writer.write("\n");
        writer.close();
        
    }
    /**
     * The read method of the contacthandler class, reads the whole txt file.
     * 
     * Read method read the contacts file to an arraylist using bufferedreader  
     * and then returns the arraylist. Does not actually display info.
     * 
     * @return infoList
     * @throws IOException
     */
    public ArrayList<String> read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
        ArrayList<String> infoList = new ArrayList<>();

        String line;
        while((line = reader.readLine()) != null){
            infoList.add(line.substring(0, line.length()));
        }
        reader.close();
        return infoList;
        
    }
    /**
     * The update method of the ContactHandler class is used update a contact.
     * 
     * Update method finds the contact to update by the given id, and then updates the
     * values the user wants to update. It'd done by merging old and new values,
     * making a new person object.
     * 
     * @param id the id of the person to updata
     * @param infoMap the hashmap of the updatable info. 
     * @throws Exception
     */
    public void update(String id, HashMap<String, String> infoMap) throws Exception {
        HashMap<String, String> oldInfoMap = new HashMap<String, String>();
        String updateable = "";

        for (String ln : read()) {
            for (String info : ln.split(",")) {
                String[] sepInfo = info.split(":");
                if(id.equals(sepInfo[1])){
                    updateable = ln;
                    delete(id);
                    break;
                }
            }
        }
        if(updateable.length() == 0 || updateable == null){
            throw new Exception("ID not found");
        }

        for (String info : updateable.split(",")) {
            String[] sepInfo = info.split(":");
            String value = sepInfo[1];
            if(value.equals("null"))
                value = "";
            oldInfoMap.put(sepInfo[0], value);
        }

        for (String key : oldInfoMap.keySet()) {
            if(infoMap.get(key).isEmpty()){
                infoMap.replace(key, oldInfoMap.get(key));
            }
        }
        
        create(new Person(infoMap.get("id"), infoMap.get("first name"), infoMap.get("last name"),
                          infoMap.get("phone number"), infoMap.get("address"), infoMap.get("email")));  
              
    }
    /**
     * The delete method of ContactHandler deletes contacts by the given ID
     * 
     * Delete method reads the contact file to an arrayList, and then removes
     * the line/contact that contains the id. Overwrites the contact file with 
     * content of the arraylist without the removed contact.
     * 
     * @param id the id of the removable contact
     * @throws Exception
     */
    public void delete(String id) throws Exception {
        ArrayList<String> infoList = read();
        String lineToRemove = "";
        for (String ln : infoList) {
            if(ln.contains(id)){
                lineToRemove = ln;
                break;
            }
        }
        System.out.println(lineToRemove);
        if(lineToRemove.isEmpty()){
            throw new Exception("ID not found");
        }
        infoList.remove(lineToRemove);

        FileWriter writer = new FileWriter("data.txt", false);
        for (String ln : infoList) {
            writer.write(ln);
            writer.write("\n");
        }
        writer.close();

    }
    /**
     * The setFormat method sets the "key" format for saving contact info
     * 
     */
    public void setFormat() {
        fId = "id";
        fFirstName = "first name";
        fLastName = "last name";
        fPhone = "phone number";
        fEmail = "email";
        fAddress = "address";
    }
    /**
     * creteContactsFile of the ContactHandler class
     * 
     * The method creates the data file where the contacts are stored
     * if it does not exist yet.
     * 
     * @throws Exception
     */
    public void createContactsFile() throws Exception {
        File fileExists = new File("data.txt");
        if(!fileExists.exists())
            fileExists.createNewFile();
    }
    /**
     * GetFormat method returns the correct formats
     * for the contact info.
     * 
     * 
     * @param whatToFormat info that needs formatting.
     * @return format "key" for the info.
     */
    public String getFormat(formatEnum whatToFormat) {
        switch (whatToFormat) {
            case FIRSTNAME:
                return fFirstName;
            case LASTNAME:
                return fLastName;
            case ID:
                return fId;
            case PHONENUM:
                return fPhone;
            case EMAIL:
                return fEmail;
            case ADDRESS:
                return fAddress;
            default:
                return null;
        }
    }
}
