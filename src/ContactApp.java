import java.io.*;
import java.util.HashMap;

/**
 * Main class of the application. All the inputs are done here.
 * Main class doesn't do much of the validations, validations are implemented in other classes.
 * 
 * @author Vanhatupa Niilo
 */
class Main{
    public static void main(String[] args) throws Exception {
        Console c = System.console();
        int choice;
        ContactHandler ch = new ContactHandler();

        while (true) {
            System.out.println("ContactsApp \n");        
        
            System.out.println("Choose one of the following: "+
            "\n0: Quit \n1: Add a new contact \n2: Remove Contact"+
            "\n3: Show listed contacts \n4: Modify contact");

            System.out.print("Option: ");
            try {
                choice = Integer.parseInt(c.readLine());
            } catch (NumberFormatException e) {
                System.out.println(e);
                continue;
            }
            catch(Exception e){
                System.out.println(e);
                continue;
            }
            
            switch (choice) {
                case 0:
                    System.out.println("Thank you for using the program!");
                    return;
                case 1:
                    while (true) {
                        System.out.println("Enter the contact info, optional fields can be left empty.");
                        System.out.print("Enter the person's id in finnish format: ");
                        String id = c.readLine();
                        System.out.print("Enter the person's full name: ");
                        String fullName = c.readLine();
                        System.out.print("Enter the person's phone number: ");
                        String phone = c.readLine();
                        System.out.print("[OPTIONAL] Enter the person's address: ");
                        String address = c.readLine();
                        System.out.print("[OPTIONAL] Enter the person's email: ");
                        String email = c.readLine();

                        int nameCheck = fullName.split(" ").length;
                        String[] nameSplitted = fullName.split(" ");
                        if(nameCheck != 2){
                            System.out.println("Format for full name is: 'Matti Meikäläinen' ");   
                            continue;
                        }
                        try {
                            ch.create(new Person(id, nameSplitted[0], nameSplitted[1], phone, address, email));
                            break;
                        } catch (Exception e) {
                            System.out.println(e);
                            continue;
                        }    
                    }
                    break;
                case 2:
                    System.out.print("Please enter the personal id of the contact you want to remove: ");
                    String idToRemove = c.readLine();
                    try {
                        new PersonID(idToRemove);
                        ch.delete(idToRemove);
                    } 
                    catch (IllegalArgumentException e) {
                        System.out.println(e);
                        continue;
                    }
                    catch (Exception e){
                        System.out.println(e);
                        continue;
                    }
                    System.out.println("Contact deleted succesfully");
                    break;
                case 3:
                    System.out.println("Saved contacts:");
                    ch.read().forEach(str -> System.out.println(str));
                    break;
                case 4:
                    HashMap<String, String> infoMap = new HashMap<String, String>();
                    System.out.println("Give the id of the contact who you want to modify (personal id)");
                    System.out.print("ID: ");
                    String input = c.readLine();
                    System.out.println("Enter new values for everything \n if you don't want to update the value press enter\n");
                                        
                    System.out.print("ID: ");
                    infoMap.put(ch.getFormat(formatEnum.ID), c.readLine());
                    System.out.print("First name: ");
                    infoMap.put(ch.getFormat(formatEnum.FIRSTNAME), c.readLine());
                    System.out.print("Last name: ");
                    infoMap.put(ch.getFormat(formatEnum.LASTNAME), c.readLine());
                    System.out.print("Phone number: ");
                    infoMap.put(ch.getFormat(formatEnum.PHONENUM), c.readLine());
                    System.out.print("Address: ");
                    infoMap.put(ch.getFormat(formatEnum.ADDRESS), c.readLine());
                    System.out.print("Email: ");
                    infoMap.put(ch.getFormat(formatEnum.EMAIL), c.readLine());

                    ch.update(input, infoMap);
                    
                    break;
                default:
                    break;
            }
        }
    }
}
