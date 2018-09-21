/*
 * Hardware Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */
package hardwarestore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*; 
import hardwarestore.items.Item;
import hardwarestore.users.User;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



/**
 * This is the main class of the Hardware Store database manager. It provides a
 * console for a user to use the 5 main commands.
 *
 * @author Junye Wen
 */
public class MainApp {
	
	

    // This object will allow us to interact with the methods of the class HardwareStore
//    private final HardwareStore hardwareStore;
    private static HardwareStore hardwareStore; 
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in); // Used to read from System's standard input
	private static final float LEFT_ALIGNMENT = 0;

    /**
     * Default constructor. Initializes a new object of type HardwareStore
     *
     * @throws IOException
     */
    public MainApp() throws IOException {
        hardwareStore = new HardwareStore();
    }

    //Function 1
    /**
     * This method shows all items in the inventory.
     ************EDITED***************************
     */
    public String showAllItems() {
        // We need to sort the item list first
        HardwareStore.sortItemList();
 //       System.out.print(hardwareStore.getAllItemsFormatted());
        String itemList = hardwareStore.getAllItemsFormatted();  
        return itemList; 
    }

    //Function 2
    /**
     * This method will add items quantity with given number. If the item does
     * not exist, it will call another method to add it.
     *
     */
    public void addItemQuantity() {
        String idNumber = "";
        while (true) {
            System.out.println("Please input the ID of item (String, 5 alphanumeric characters).");
            System.out.println("If the item does not exist, it will be added as a new entry.");
            idNumber = CONSOLE_INPUT.nextLine();
            if (!idNumber.matches("[A-Za-z0-9]{5}")) {
                System.out.println("Invalid ID Number: not proper format. "
                        + "ID Number must be 5 alphanumeric characters.");
                continue;
            }
            break;
        }


        int itemIndex = hardwareStore.findItemIndex(idNumber);
        if (itemIndex != -1) { // If item exists in the database
            System.out.println("Item found in database.");
            int quantity = 0;
            while (true) {
                System.out.println("Current quantity: " + hardwareStore.findItem(idNumber).getQuantity());
                System.out.println("Please enter quantity you want to add.");
                quantity = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
                if (quantity <= 0) {
                    System.out.println("Invalid quantity. "
                            + "The addition amount must be larger than 0.");
                    continue;
                }
                break;
            }


            hardwareStore.addQuantity(itemIndex, quantity);
        } else {
            // If it reaches here, the item does not exist. We need to add new one.
            System.out.println("Item with given number does not exist.");

            // Enter name
            System.out.println("Please type the name of item.");
            String name = CONSOLE_INPUT.nextLine();

            // Enter quantity
            int quantity = 0;
            while(true) {
                System.out.println("Please type the quantity of the item (integer).");
                try {
                    quantity = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    if (quantity < 0) {
                        System.out.println("Invalid price. "
                                + "The quantity cannot be smaller than 0.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input an integer.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
                break;
            }


            // Enter price
            float price = 0;
            while (true) {
                System.out.println("Please type the price of the item (float).");
                try {
                    price = CONSOLE_INPUT.nextFloat();
                    CONSOLE_INPUT.nextLine();
                    if (price < 0) {
                        System.out.println("Invalid price. "
                                + "The price cannot be smaller than 0.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input a float.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
                break;

            }

            // Select item type
            while (true) {
                System.out.println("Please select the type of item.");
                System.out.println("1: Small Hardware Items\n2: Appliances");
                int selection = 0;
                try {
                    selection = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    switch (selection) {
                        case 1:
                            // Adding small hardware items
                            // Select category
                            String category = null;
                            System.out.println("Please select the category of item.");
                            System.out.println("1: Door&Window\n2: Cabinet&Furniture\n3: Fasteners\n4: Structural\n5: Other");
                            try {
                                selection = CONSOLE_INPUT.nextInt();
                                CONSOLE_INPUT.nextLine();
                                switch (selection) {
                                    case 1:
                                        category = "Door&Window";
                                        break;
                                    case 2:
                                        category = "Cabinet&Furniture";
                                        break;
                                    case 3:
                                        category = "Fasteners";
                                        break;
                                    case 4:
                                        category = "Structural";
                                        break;
                                    case 5:
                                        category = "Other";
                                        break;
                                    default:
                                        System.out.println("Invalid input.");
                                        continue;
                                }
                            } catch (Exception e) {
                                System.out.println("Illegal input: Must input an integer.");
                                CONSOLE_INPUT.nextLine();
                                continue;
                            }
                            hardwareStore.addNewSmallHardwareItem(idNumber, name, quantity, price, category);
                            return;

                        case 2:
                            // Adding appliances
                            // Input brand
                            System.out.println("Please input the brand of appliance. (String)");
                            String brand = CONSOLE_INPUT.nextLine();
                            // Select type
                            String type = null;
                            System.out.println("Please select the type of appliance.");
                            System.out.println("1: Refrigerators\n2: Washers&Dryers\n3: Ranges&Ovens\n4: Small Appliance");
                            try {
                                selection = CONSOLE_INPUT.nextInt();
                                CONSOLE_INPUT.nextLine();
                                switch (selection) {
                                    case 1:
                                        type = "Door&Window";
                                        break;
                                    case 2:
                                        type = "Washers&Dryers";
                                        break;
                                    case 3:
                                        type = "Ranges&Ovens";
                                        break;
                                    case 4:
                                        type = "Small Appliance";
                                        break;
                                    default:
                                        System.out.println("Invalid input.");
                                        continue;
                                }
                            } catch (Exception e) {
                                System.out.println("Illegal input: Must input an integer.");
                                CONSOLE_INPUT.nextLine();
                                continue;
                            }

                            hardwareStore.addNewAppliance(idNumber, name, quantity, price, brand, type);
                            return;
                        default:
                            System.out.println("Invalid input.");
                            continue;
                    }

                } catch (Exception e) {
                    System.out.println("Illegal input: Must input an integer.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
            }
        }
    }

    //Function 3
    /**
     * This method will remove the item with given ID.
     * If the item does not exist, it will show an appropriate message.
     */
    public void removeItem() {
        System.out.println("\033[31m" + "WARNING: This function will remove the whole item entry. Please use with caution!");
        System.out.println("\033[31m" + "Will return to main menu if you make any mistake inputting information!");
        System.out.println("\033[0m" + "Please input the ID of item.");
        String idNumber = CONSOLE_INPUT.nextLine();
        if (!idNumber.matches("[A-Za-z0-9]{5}")) {
            System.out.println("Invalid ID Number: not proper format. "
                    + "ID Number must be at least 5 alphanumeric characters.");
            System.out.println("Will return to main menu.");
            return;
        }

        int itemIndex = hardwareStore.findItemIndex(idNumber);
        if (itemIndex == -1) {
            System.out.println("Item does not exist.");
            System.out.println("Will return to main menu.");
            return;
        } else {
            System.out.println("\033[31m" + "Item found. Are you sure you want to remove the whole entry?");
            System.out.println("\033[31m" + "(Data cannot be recovered!)");
            System.out.println("\033[31m" + "Please type YES (all capitalized) to confirm deletion.");
            String input = CONSOLE_INPUT.nextLine();
            if (input.equals("YES")) {
                System.out.println("\033[0m" + "User typed " + input + ". Confirm: Item will be removed.");
                hardwareStore.removeItem(itemIndex);
                System.out.println("\033[0m" + "Item removed from inventory.");
            } else {
                System.out.println("\033[0m" + "User typed " + input + ". Abort: Item will not be removed.");
            }

        }
    }

    //Function 4
    /**
     * This method can search item by a given name (part of name.
     * Case-insensitive.) Will display all items with the given name.
     */
//    public void searchItemByName() {
//
//        System.out.println("Please input the name of item.");
//        String name = CONSOLE_INPUT.nextLine();
//
//        String output = hardwareStore.getMatchingItemsByName(name);
//        if (output == null) {
//            System.out.println("Item not found with: " + name + ".");
//        } else {
//            System.out.println(output);
//        }
//    }

    //Function 5
    /**
     * This method shows all users in the system.
     */
    public String showAllUsers() {
        //System.out.print(hardwareStore.getAllUsersFormatted());
        return hardwareStore.getAllUsersFormatted(); 
    }
    //Function 6
    /**
     * This method will add a user (customer or employee) to the system.
     *
     */
    public void addUser() {
        int selection = 0;

        String firstName = "";
        String lastName = "";
        // First select if you want to add customer or employee
        while (true) {
            System.out.println("Please select the type of user.");
            System.out.println("1: Employee\n2: Customer");
            try {
                selection = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();

                switch (selection) {
                    case 1:
                        // Add Employee
                        System.out.println("Please input the first name (String):");
                        firstName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the last name (String):");
                        lastName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the SSN (9-digit integer, no other characters):");
                        int socialSecurityNumber = 0;
                        try {
                            socialSecurityNumber = CONSOLE_INPUT.nextInt();
                            CONSOLE_INPUT.nextLine();
                            if (socialSecurityNumber <= 100000000 || socialSecurityNumber > 999999999) {
                                System.out.println("Invalid social security number. "
                                        + "SSN is a 9-digit integer.");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Illegal input: Must input an integer.");
                            CONSOLE_INPUT.nextLine();
                            continue;
                        }

                        System.out.println("Please input the monthly salary (float):");
                        float monthlySalary = 0;
                        try {
                            monthlySalary = CONSOLE_INPUT.nextFloat();
                            CONSOLE_INPUT.nextLine();
                            if (monthlySalary < 0) {
                                System.out.println("Invalid salary."
                                        + "It must be (at least) 0.");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Illegal input: Must input a float.");
                            CONSOLE_INPUT.nextLine();
                            continue;
                        }

                        hardwareStore.addEmployee(firstName,lastName, socialSecurityNumber, monthlySalary);
                        return;

                    case 2:
                        // Add Customer
                        System.out.println("Please input the first name (String):");
                        firstName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the last name (String):");
                        lastName = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the phone number (String):");
                        String phoneNumber = CONSOLE_INPUT.nextLine();
                        System.out.println("Please input the address (String):");
                        String address = CONSOLE_INPUT.nextLine();
                        hardwareStore.addCustomer(firstName, lastName, phoneNumber, address);
                        return;
                    default:
                        System.out.println("Invalid input.");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
        }
    }

    //Function 7
    /**
     * This method will edit a user (customer or employee).
     *
     */
    public void editUser() {
        int idInput = 0;
        while (true) {
            System.out.println("Please input the ID of user.");
            try {
                idInput = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
            break;
        }


        User editUser = hardwareStore.findUser(idInput);
        if (editUser == null) {
            System.out.println("User not found.");
            System.out.println("Will return to main menu.");
            return;
        }
        String text = " -------------------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-9s| %-12s| %-12s| %-45s|%n", "User Type", "User ID", "First Name", "Last Name", "Special") +
                " -------------------------------------------------------------------------------------------------\n";
        text += editUser.getFormattedText();
        text += " -------------------------------------------------------------------------------------------------\n";

        System.out.println("Current user information:");
        System.out.println(text);
        String firstName = "";
        String lastName = "";
        if (editUser.isEmployee) {
            //User is employee
            int socialSecurityNumber = 0;
            float monthlySalary = 0;
            while (true) {
                System.out.println("Please input the first name (String):");
                firstName = CONSOLE_INPUT.nextLine();
                System.out.println("Please input the last name (String):");
                lastName = CONSOLE_INPUT.nextLine();
                System.out.println("Please input the SSN (9-digit integer, no other characters):");

                try {
                    socialSecurityNumber = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    if (socialSecurityNumber <= 100000000 || socialSecurityNumber > 999999999) {
                        System.out.println("Invalid social security number. "
                                + "SSN is a 9-digit integer.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input an integer.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }

                System.out.println("Please input the monthly salary (float):");
                try {
                    monthlySalary = CONSOLE_INPUT.nextFloat();
                    CONSOLE_INPUT.nextLine();
                    if (monthlySalary < 0) {
                        System.out.println("Invalid salary."
                                + "It must be (at least) 0.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Illegal input: Must input a float.");
                    CONSOLE_INPUT.nextLine();
                    continue;
                }
                break;
            }

            hardwareStore.editEmployeeInformation(idInput, firstName,lastName, socialSecurityNumber, monthlySalary);
            return;

        } else {
            //User is customer
            System.out.println("Please input the first name (String):");
            firstName = CONSOLE_INPUT.nextLine();
            System.out.println("Please input the last name (String):");
            lastName = CONSOLE_INPUT.nextLine();
            System.out.println("Please input the phone number (String):");
            String phoneNumber = CONSOLE_INPUT.nextLine();
            System.out.println("Please input the address (String):");
            String address = CONSOLE_INPUT.nextLine();
            hardwareStore.editCustomerInformation(idInput, firstName, lastName, phoneNumber, address);
            return;
        }
    }

    //Function 8
    /**
     * This method will lead user to complete a transaction.
     */
    public void finishTransaction(){
        String itemID = "";
        int itemIndex = 0;
        int saleQuantity = 0;
        //Get the item ID. Will not break unless got a valid input.
        while (true) {
            System.out.println("Please input the item ID:");
            itemID=CONSOLE_INPUT.nextLine();
            itemIndex = hardwareStore.findItemIndex(itemID);
            if (itemIndex == -1) {
                System.out.println("Item not found. Will return to main menu.");
                return;
            } else {
                Item tempItem = hardwareStore.findItem(itemID);
                System.out.println("Please input the amount of items sold in this transaction (int)");
                System.out.println("Maximum number: " + tempItem.getQuantity());
                try {
                    saleQuantity = CONSOLE_INPUT.nextInt();
                    CONSOLE_INPUT.nextLine();
                    if (saleQuantity <= 0) {
                        System.out.println("Invalid input: must be greater than 0.");
                        continue;
                    } else if (saleQuantity > tempItem.getQuantity()) {
                        System.out.println("Invalid input: Number too big. Transaction cannot progress.");
                        continue;
                    }
                    break;

                } catch (Exception e) {
                    System.out.println("Amount of items sold input invalid: not an integer");
                    continue;
                }
            }

        }

        //Get employee ID. Will check the input: must be a user, and employee.
        int employeeID = 0;
        while (true) {
            System.out.println("Please input the id of the employee.");
            try {
                employeeID = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
                if (hardwareStore.findUserIndex(employeeID) == -1) {
                    System.out.println("User not found.");
                    continue;
                } else if (!hardwareStore.findUser(employeeID).isEmployee) {
                    System.out.println("This user is not an employee.");
                }
                break;

            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
        }

        //Get customer ID. Will check the input: must be a user, and customer.
        int customerID = 0;
        while (true) {
            System.out.println("Please input the id of the customer.");
            try {
                customerID = CONSOLE_INPUT.nextInt();
                CONSOLE_INPUT.nextLine();
                if (hardwareStore.findUserIndex(customerID) == -1) {
                    System.out.println("User not found.");
                    continue;
                } else if (hardwareStore.findUser(customerID).isEmployee) {
                    System.out.println("This user is not a customer.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Illegal input: Must input an integer.");
                CONSOLE_INPUT.nextLine();
                continue;
            }
        }

        hardwareStore.progressTransaction(itemID, saleQuantity, customerID, employeeID, itemIndex);
        System.out.println("Transaction complete.");

    }

    //Function 9
    public String showAllTransactions(){
//        System.out.print(hardwareStore.getAllTransactionsFormatted());
        String transactionList = hardwareStore.getAllTransactionsFormatted(); 
        return transactionList; 
    }

    //Function 10
    /**
     * These method is called to save the database before exit the system.
     * @throws IOException
     */
    public void saveDatabase() throws IOException {
        hardwareStore.writeDatabase();
    }
    
    
    
    private final static Logger logger = Logger.getLogger(MainApp.class.getName()); 
    private static FileHandler fh = null; 
    

    /**
     * 
     */
    public static void init() {
    	try {
    		fh = new FileHandler ("log.txt", false); 
    	} catch (SecurityException | IOException e) {
    		e.printStackTrace(); 
    	}
    	
    	fh.setLevel(Level.CONFIG);
    	fh.setFormatter(new SimpleFormatter());
    	
    	logger.addHandler(fh);
    	
    }
    
    

    /**
     * This method will begin the user interface console. Main uses a loop to
     * continue executing commands until the user types '6'.
     *
     * @param args this program expects no command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MainApp app = new MainApp();
        
        //start logger
        MainApp.init();
        //logger.setLevel(Level.FINER);
        logger.info("Hardware Store app begins...");
        logger.setUseParentHandlers(false);

      
        
    	JFrame frame = new JFrame(); 
    	frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(900,983)); 
    	
        String welcomeMessage = "\nWelcome to the Hardware Store database. Choose one of the following functions:\n\n"
                + "\t 1. Show all existing items records in the database (sorted by ID number).\n"
                + "\t 2. Add new item (or quantity) to the database.\n"
                + "\t 3. Delete an item from a database.\n"
                + "\t 4. Search for an item given its name.\n"
                + "\t 5. Show a list of users in the database.\n"
                + "\t 6. Add new user to the database.\n"
                + "\t 7. Update user info (given their id).\n"
                + "\t 8. Complete a sale transaction.\n"
                + "\t 9. Show completed sale transactions.\n"
                + "\t10. Save.\n";
    	
       // JSplitPane splitPane = new JSplitPane(); 

        //top panel menu
    	JPanel topPanel = new JPanel();
        frame.add(topPanel,BorderLayout.NORTH);
        JTextArea welcome = new JTextArea(welcomeMessage); 
        welcome.setEditable(false);
        topPanel.add(welcome); 
        welcome.setPreferredSize(new Dimension(850,350));
        Font menuFont = new Font("Times Roman", Font.BOLD, 18);
        welcome.setFont(menuFont);
        
        
        //bottom panel
        JPanel bottomPanel = new JPanel(); 
    	bottomPanel.setPreferredSize(new Dimension(850,540));
    	frame.add(bottomPanel, BorderLayout.SOUTH);
   

        
        //middle button panel
    	JPanel buttonPanel = new JPanel(); 
    	frame.add(buttonPanel, BorderLayout.CENTER);
    	buttonPanel.setPreferredSize(new Dimension (850,0));
    	buttonPanel.setBackground(Color.WHITE);
        JButton option1 = new JButton("Option 1"); 
        
        //button 1
        option1.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent event) {
        	  
        	  bottomPanel.removeAll(); 
        	  bottomPanel.updateUI(); 
              bottomPanel.setLayout(new FlowLayout()); 
        	  JTextArea itemList = new JTextArea(app.showAllItems()); 
        	  bottomPanel.add(itemList);  
        	  frame.pack(); 
              frame.setVisible(true); 
              logger.log(Level.INFO, "Item records displayed.");
          }
        }); 
        
        //button 2
        JButton option2 = new JButton("Option 2"); 
        option2.addActionListener(new ActionListener() {
        	
        	String id;  
        	String name;  
        	String category;  
        	String quantity; 
        	String type; 
        	String price; 
        	String brand; 
        	String applianceType; 
            
            @Override
            public void actionPerformed(ActionEvent event) {
            	bottomPanel.removeAll(); 
              	bottomPanel.updateUI();
          	  
             	bottomPanel.setLayout(null);
              	Insets insets = bottomPanel.getInsets(); 
    
              	JLabel prompt1 = new JLabel("**IF THE ITEM IS ALREADY IN THE INVENTORY, ONLY ENTER THE ITEM ID.**"); 
              	prompt1.setForeground(Color.RED);
              	Dimension size = prompt1.getPreferredSize(); 
              	prompt1.setBounds(230 + insets.left, 5 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt1);
              	
              	JLabel prompt2 = new JLabel("**IF THE ITEM DOES NOT EXIST, IT WILL BE ADDED AS THE NEW ENTRY.  ENTER ALL APPROPRIATE FIELDS.**");
              	prompt2.setForeground(Color.RED);
              	size = prompt2.getPreferredSize();
              	prompt2.setBounds(140 + insets.left, 25 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt2);
              	
              	JLabel prompt3 = new JLabel("ID of item: ");
              	size = prompt3.getPreferredSize(); 	
              	prompt3.setBounds(220 + insets.left, 55 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt3); 
              	
              	JTextField itemID = new JTextField(15);
              	size = itemID.getPreferredSize();
              	itemID.setBounds(500 + insets.left, 55 + insets.top, size.width, size.height);
              	bottomPanel.add(itemID);         	    	       	
              	
              	JLabel prompt4 = new JLabel("Name of item:");
              	size = prompt4.getPreferredSize();
              	prompt4.setBounds(220 + insets.left, 85 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt4);

              	JTextField itemName = new JTextField(15);
              	size = itemName.getPreferredSize();
              	itemName.setBounds(500 + insets.left, 85 + insets.top, size.width, size.height);
              	bottomPanel.add(itemName);    	
              	
              	JLabel prompt5 = new JLabel("Quantity of item (integer):");
              	size = prompt5.getPreferredSize();
              	prompt5.setBounds(220 + insets.left, 115 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt5);
              	
              	JTextField itemQuantity = new JTextField(15);
              	size = itemQuantity.getPreferredSize();
              	itemQuantity.setBounds(500 + insets.left, 115 + insets.top, size.width, size.height);
              	bottomPanel.add(itemQuantity);
              	
              	
              	JLabel prompt6 = new JLabel("Price of item (float):");
              	size = prompt6.getPreferredSize();
              	prompt6.setBounds(220 + insets.left, 145 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt6); 
              	
              	JTextField itemPrice = new JTextField(15);
              	size = itemPrice.getPreferredSize();
              	itemPrice.setBounds(500 + insets.left, 145 + insets.top, size.width, size.height);
              	bottomPanel.add(itemPrice);
              	
              	
              	JLabel prompt7 = new JLabel("Select type of item:");
              	size = prompt7.getPreferredSize();
              	prompt7.setBounds(220 + insets.left, 175 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt7);
              	
             	String[] itemTypeChoices = {"Select", "Small Hardware Items", "Appliances"};	
        		final JComboBox<String> itemType = new JComboBox<String>(itemTypeChoices);
              	size = itemType.getPreferredSize();
              	itemType.setBounds(500 + insets.left, 175 + insets.top, size.width, size.height);
        		bottomPanel.add(itemType); 
        		
        		itemType.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox source = (JComboBox) e.getSource(); 
						String userInput = (String) source.getSelectedItem(); 
						if(userInput.equals("Select")) {
							type = "Select"; 
						}
						
						if(userInput.equals("Small Hardware Items")) {
							type = "Small Hardware Items";		
						}
						if (userInput.equals("Appliances")) {
							type = "Appliances";
						}
					}
        		});
              	            	
              	JLabel prompt8 = new JLabel("Select the category of small hardware item:");
              	size = prompt8.getPreferredSize();
              	prompt8.setBounds(220 + insets.left, 205 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt8);
              	
             	String[] itemCategoryChoices = {"Select", "Door&Window", "Cabinet&Furniture", "Fasteners", "Structural", "Other"};	
        		final JComboBox<String> itemCategory = new JComboBox<String>(itemCategoryChoices);
              	size = itemCategory.getPreferredSize();
              	itemCategory.setBounds(500 + insets.left, 205 + insets.top, size.width, size.height);
        		bottomPanel.add(itemCategory);
        		
        		//to capture user input for itemCategory
        		itemCategory.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox source = (JComboBox) e.getSource(); 
						String userInput = (String) source.getSelectedItem(); 
						if(userInput.equals("Select")) {
							category = "Select"; 
						}
						
						if(userInput.equals("Door&Window")) {
							category = "Door&Window";		
						}
						if (userInput.equals("Cabinet&Furniture")) {
							category = "Cabinet&Furniture";
						}

						if(userInput.equals("Fasteners")) {
							category = "Fasteners";
						}
						
						if(userInput.equals("Structural")) {
							category = "Structural"; 
						}
						
						if(userInput.equals("Other")) {
							category = "Other"; 
						}
					}
        		}); 
        		
              	
              	JLabel prompt9 = new JLabel("Brand of appliance: ");
              	size = prompt9.getPreferredSize();
              	prompt9.setBounds(220 + insets.left, 235 + insets.top, size.width, size.height);	
              	bottomPanel.add(prompt9); 
              	
              	JTextField appBrand = new JTextField(15);
              	size = appBrand.getPreferredSize();
              	appBrand.setBounds(500 + insets.left, 235 + insets.top, size.width, size.height);
              	bottomPanel.add(appBrand);
              	
              	//to capture user input for brand
              	appBrand.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

					}
        		});
              	
              	JLabel prompt10 = new JLabel("Type of appliance:");
              	size = prompt10.getPreferredSize();
              	prompt10.setBounds(220 + insets.left, 265 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt10); 
              	
             	String[] applianceTypeChoices = {"Select", "Refrigerators", "Washers&Dryers", "Ranges&Ovens", "Small Appliance"};	
        		final JComboBox<String> appType = new JComboBox<String>(applianceTypeChoices);
              	size = appType.getPreferredSize();
              	appType.setBounds(500 + insets.left, 265 + insets.top, size.width, size.height);
              	bottomPanel.add(appType); 
              	
              	//to capture user input for itemCategory
              	appType.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox source = (JComboBox) e.getSource(); 
						String userInput = (String) source.getSelectedItem(); 
						
						if(userInput.equals("Select")){
							applianceType = "Select"; 
						}
						
						if(userInput.equals("Refrigerators")) {
							applianceType = "Refrigerators";		
						}
						
						if (userInput.equals("Washers&Dryers")) {
							applianceType = "Washers&Dryers";
						}

						if(userInput.equals("Ranges&Ovens")) {
							applianceType = "Ranges&Ovens";
						}
						
						if(userInput.equals("Small Appliance")) {
							applianceType = "Small Appliance"; 
						}
					}
        		}); 
              	
              	
              	JButton done = new JButton("Done"); 
              	size = done.getPreferredSize();
              	done.setBounds(400 + insets.left, 300 + insets.top, size.width, size.height);
              	bottomPanel.add(done);
              	
              	//to capture when user clicks done to add item
        		done.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						boolean flag = true; 
						id = itemID.getText();
						name = itemName.getText(); 
						quantity = itemQuantity.getText();  
						price = itemPrice.getText(); 
						
						bottomPanel.removeAll(); 
						bottomPanel.setLayout(new FlowLayout());
						bottomPanel.updateUI(); 
						
						
						//searching item list to see if item is already exists
		                int itemIndex = hardwareStore.findItemIndex(id);
		                
		                if (itemIndex != -1) { // If item exists in the database
		                	
							bottomPanel.removeAll(); 
							bottomPanel.setLayout(null);
							bottomPanel.updateUI();
							
							JLabel itemExists = new JLabel("ITEM ID FOUND IN INVENTORY. ENTER THE QUANTITY BEING ADDED TO INVENTORY.");
		                  	Dimension size = itemExists.getPreferredSize();
		                  	itemExists.setBounds(220 + insets.left, 5 + insets.top, size.width, size.height);
		                	bottomPanel.add(itemExists); 
		                	
		                  	JLabel prompt4 = new JLabel("Quantity of item (integer):");
		                  	size = prompt4.getPreferredSize();
		                  	prompt4.setBounds(220 + insets.left, 25 + insets.top, size.width, size.height);
		                  	bottomPanel.add(prompt4); 
		                  	
		                  	JTextField quantityAdded = new JTextField(15);
		                  	size = quantityAdded.getPreferredSize();
		                  	quantityAdded.setBounds(480 + insets.left, 25 + insets.top, size.width, size.height);
		                  	bottomPanel.add(quantityAdded);
		                  	
		                  	JButton done = new JButton("Done"); 
		                  	size = done.getPreferredSize();
		                  	done.setBounds(400 + insets.left, 55 + insets.top, size.width, size.height);
		                  	bottomPanel.add(done);
		                  	
		                	bottomPanel.repaint(); 
		                	
		                	done.addActionListener(new ActionListener() { 	
		                 	
		                		@Override
		                		public void actionPerformed(ActionEvent e) {
		                			
		    						bottomPanel.removeAll(); 
		    						bottomPanel.setLayout(new FlowLayout());
		    						bottomPanel.updateUI(); 
									String addedQuantity = quantityAdded.getText(); 
									
		                			//checking if the quantity is in the right format
		                			boolean quantityIsAnInteger = true;
		                			boolean quantityIsValid = true; 
		                			
				                	//checking if all required entries were entered
				                	boolean quantityEntryPopulated = true; 
									if(addedQuantity.matches("")) {
										quantityIsValid = false;
										quantityEntryPopulated = false;
										JLabel restart = new JLabel("BLANK REQUIRED ENTRY. Enter item ID, name, quantity, price, and other respective values.  Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
									
						            	//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User left the quantity entry blank while attempting to add only quantity to an exisistig item entry.");
									}
									
									if(quantityEntryPopulated){
										
			                			try {
			                				Integer.parseInt(addedQuantity); 
								
			                			} catch(NumberFormatException exc) {
			                				quantityIsValid = false;
			                				JLabel restart = new JLabel("INVALID QUANTITY. Enter a positive integer value for the quantity. Try again or choose another option.");
			    							restart.setForeground(Color.RED);
			                				bottomPanel.add(restart); 
			                				bottomPanel.repaint(); 
							
			                				//logging add fail
			                				logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid quantity.");
				
			                			}
										
			                			if(quantityIsAnInteger) {
			                				if (Integer.parseInt(addedQuantity) < 0) {
			                					quantityIsValid = false;
			                					JLabel restart = new JLabel("INVALID QUANTITY. Enter a positive integer value for the quantity.  Try again or choose another option.");
			        							restart.setForeground(Color.RED);
			                					bottomPanel.add(restart); 
			                					bottomPanel.repaint(); 
								
			                					//logging add fail
			                					logger.log(Level.WARNING, "ADD FAIL:  User did not enter a positive quantity.");
					
			                				}
			                			}
			                			
			                			//add if quantity is valid
			                			if(quantityIsValid) {
									
			                				hardwareStore.addQuantity(itemIndex, Integer.parseInt(addedQuantity));
				                	
			                				JLabel restart = new JLabel("Quantity added.  Choose another option.");
			    							restart.setForeground(Color.GREEN);
			                				bottomPanel.add(restart); 
			                				bottomPanel.repaint(); 
						
			                				//logging add
			                				logger.log(Level.INFO, "Quantity of " + addedQuantity + " added to ID#  " + id + ".");
									
			                			}
									}
		                		}
		                	}); 
		                }
		                else {
				
		                	//checking if all required entries were entered
		                	boolean entriesAllPopulated = true; 
							if(id.matches("") || name.matches("") || quantity.matches("") || price.matches("")) {
								flag = false;
								entriesAllPopulated = false;
								JLabel restart = new JLabel("BLANK REQUIRED ENTRY. Enter item ID, name, quantity, price, and other respective values.  Try again or choose another option.");
								restart.setForeground(Color.RED);
								bottomPanel.add(restart); 
								bottomPanel.repaint(); 
							
				            	//logging add fail
								logger.log(Level.WARNING, "ADD FAIL:  User left one of the entries blank while attempting to add a new item.");
							}
						
						
							if(entriesAllPopulated) {
							
								//checking if the item ID is in the right format
								if(!id.matches("[A-Za-z0-9]{5}")) {
									flag = false;
									JLabel restart = new JLabel("Invalid ID number: not proper format. ID number must be 5 alphanumeric characters.  Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
								
					            	//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not enter an ID number in the correct format.");
								
								}
							
								
								
								//checking if the quantity is in the right format
								boolean quantityIsValid = true;
								try {
									Integer.parseInt(quantity); 
								
								} catch(NumberFormatException exc) {
									flag = false;
									JLabel restart = new JLabel("INVALID QUANTITY. Enter a positive integer value for the quantity. Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
							
									//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid quantity.");
				
								}
										
								if(quantityIsValid) {
									if (Integer.parseInt(quantity) < 0) {
										flag = false;
										JLabel restart = new JLabel("INVALID QUANTITY. Enter a positive integer value for the quantity.  Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
								
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User did not enter a postive quantity.");
					
									}
								}
							
								//checking if the price is in the right format
								boolean priceIsValid = true;
								try {
									Float.parseFloat(price); 
								
								} catch(NumberFormatException exc) {
									flag = false;
									JLabel restart = new JLabel("INVALID PRICE. Enter a float value for the price. Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
							
									//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid price.");
				
								}
										
								if(priceIsValid) {
									if (Float.parseFloat(price) < 0) {
										flag = false;
										JLabel restart = new JLabel("INVALID PRICE. Enter a positive integer value for the quantity. Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
								
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid price.");
					
									}
								}

							
								//checking if user selected the item type
								if(itemType.getSelectedItem() != null && itemType.getSelectedItem().equals("Select")) {
									flag = false;
									JLabel restart = new JLabel("Item type not entered.  Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
							
									//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not specify item type.");
					
								}
							
							
								//if user put all entries in the correct value add. 
								if(flag) {
							
									if(type.equals("Appliances")) {
									
										//checking if brand JTextField was entered
										boolean addValid = true; 
										brand = appBrand.getText(); 
										if(brand.matches("")) {
											addValid = false;
											JLabel error = new JLabel("APPLIANCE BRAND WAS LEFT BLANK. Enter a brand for the appliance. Try again or choose another option.");
											error.setForeground(Color.RED);
											bottomPanel.add(error); 
											bottomPanel.repaint(); 
										
											//logging add fail
											logger.log(Level.WARNING, "ADD FAIL:  User did not enter the appliance brand name.");
										}
									
										//if appliance type was selected
										if(appType.getSelectedItem() != null && appType.getSelectedItem().equals("Select")) {
											addValid = false;
											JLabel error = new JLabel("APPLIANCE TYPE NOT SELECTED. Choose appropriate appliance type.  Try again or choose another option.");
											error.setForeground(Color.RED);
											bottomPanel.add(error); 
											bottomPanel.repaint(); 
										
							            	//logging add fail
											logger.log(Level.WARNING, "ADD FAIL:  User did not select appliance type.");
										}
									
										if(addValid) {
											JLabel restart = new JLabel("Appliance added.  Choose another option.");
											restart.setForeground(Color.GREEN);
											bottomPanel.add(restart); 
											bottomPanel.repaint(); 
											hardwareStore.addNewAppliance(id, name, Integer.parseInt(quantity), Float.parseFloat(price), brand, applianceType);
								
											//logging add
											logger.log(Level.INFO, "Item ID#: " + id + " added to inventory.");
										}
									}
								
									if(type.equals("Small Hardware Items")) {	
									
										//checking if 
										boolean smallHardwareItemValid = true; 
										if(itemCategory.getSelectedItem() != null && itemCategory.getSelectedItem().equals("Select")){
											smallHardwareItemValid = false;
											JLabel restart = new JLabel("SMALL HARDWARE ITEM CATEGORY NOT SELECTED.  Choose appropriate small hardware item category.  Try again or choose another option.");
											restart.setForeground(Color.RED);
											bottomPanel.add(restart); 
											bottomPanel.repaint();

									
											//logging add fail
											logger.log(Level.WARNING, "ADD FAIL: User did not select category of small hardware item.");
										}
									
										if(smallHardwareItemValid) {
											JLabel restart = new JLabel("Small hardware item added.  Choose another option.");
											bottomPanel.add(restart); 
											restart.setForeground(Color.GREEN);
											bottomPanel.repaint();

											hardwareStore.addNewSmallHardwareItem(id, name, Integer.parseInt(quantity), Float.parseFloat(price), category);	
									
											//logging add
											logger.log(Level.INFO, "Item ID#: " + id + " added to inventory.");

										}
									}
								}
							}
		                }
					}
        		});
          	  frame.pack();
              frame.setVisible(true); 
            }            
          }); 
        
        
        //button 3
        JButton option3 = new JButton("Option 3"); 
        option3.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
            	bottomPanel.removeAll(); 
              	bottomPanel.updateUI();
              	bottomPanel.setLayout(new FlowLayout());
              	
              	JLabel prompt = new JLabel("Item ID: "); 
              	bottomPanel.add(prompt); 
            	int FIELD_WIDTH = 20;
              	JTextField itemID = new JTextField(FIELD_WIDTH); 
              	bottomPanel.add(itemID); 
              	bottomPanel.repaint(); 
              	
              	itemID.addActionListener(new ActionListener() {	
              		@Override
              		public void actionPerformed(ActionEvent event) {
            			String userInput = itemID.getText(); 
            			int itemIndex = hardwareStore.findItemIndex(userInput); 
            			
            			if (itemIndex == -1) {
            				
            				bottomPanel.removeAll();
            				bottomPanel.updateUI(); 
            				JLabel notFound = new JLabel("Item not found with ID:   " + userInput + ".  Choose another option or try again.");
							notFound.setForeground(Color.RED);
            				bottomPanel.add(notFound); 
            				bottomPanel.repaint(); 
            				
							//logging deletion search failure
				            logger.log(Level.WARNING, "Item ID #:" + userInput + " was attempted to delete but not found in inventory.");

            			} 
            			else {
            				bottomPanel.removeAll(); 
            				bottomPanel.updateUI(); 

            				bottomPanel.updateUI(); 
            				JLabel found = new JLabel("Item found. Are you sure you want to remove the whole entry? ");
            				found.setForeground(Color.RED);
            				bottomPanel.add(found);
            				//found.setAlignmentX(bottomPanel.CENTER_ALIGNMENT); 
            				JRadioButton yes = new JRadioButton("Yes");
            				yes.setMnemonic(KeyEvent.VK_C);
            				yes.setActionCommand("Yes");
            				
            				JRadioButton no = new JRadioButton("No"); 
            				no.setMnemonic(KeyEvent.VK_C);
            				no.setActionCommand("Yes");
            				no.setSelected(true);
            				
            				ButtonGroup group = new ButtonGroup(); 
            				group.add(yes);
            				group.add(no);

            				bottomPanel.add(yes); 
            				bottomPanel.add(no); 
            				
            				JButton confirm = new JButton("Confirm"); 
            				bottomPanel.add(confirm);
                    		confirm.addActionListener(new ActionListener() {
            					@Override
            					public void actionPerformed(ActionEvent e) {
            						if(yes.isSelected()) {
            							bottomPanel.removeAll();
            							bottomPanel.updateUI();
            							JLabel deleted = new JLabel("Item has been deleted.  Choose another option."); 
            							deleted.setForeground(Color.GREEN);
            							bottomPanel.add(deleted); 
            							bottomPanel.repaint(); 
            							hardwareStore.removeItem(itemIndex);
            							
            							//logging deletion 
            				            logger.log(Level.INFO, "Item ID #:" + userInput + " deleted from inventory");
            							
            						}
            						if(no.isSelected()) {
            							bottomPanel.removeAll();
            							bottomPanel.updateUI();
            							JLabel canceled = new JLabel("Deletion cancelled.  Choose another option."); 
            							canceled.setForeground(Color.RED);
            							bottomPanel.add(canceled); 
            							bottomPanel.repaint();
            							
            							//logging cancelled deletion 
            				            logger.log(Level.INFO, "Item ID #:" + userInput + " deleted initiated but cancelled by user.");
            						}
            					}		
            				}); 	
            				bottomPanel.repaint(); 
            			}
              		}  
              	});  
          	  frame.pack();
              frame.setVisible(true); 
            }
        }); 
        
        
        //button 4
        JButton option4 = new JButton("Option 4");
        option4.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent event) {
          	  bottomPanel.removeAll(); 
          	  bottomPanel.updateUI();
              bottomPanel.setLayout(new FlowLayout());
          	  
          	  JLabel prompt = new JLabel("Please input the name of the item.");
          	  
          	  bottomPanel.add(prompt); 
          	  int FIELD_WIDTH = 20; 
          	  JTextField name = new JTextField(FIELD_WIDTH); 
          	  bottomPanel.add(name); 
          	  bottomPanel.repaint(); 
          	  
          	  name.addActionListener(new ActionListener() {
          		  
          		  @Override
          		  public void actionPerformed(ActionEvent event) {
        			  String userInput = name.getText(); 
        			  String output = hardwareStore.getMatchingItemsByName(userInput); 
        			  
        			  if (output == null) {
        				  bottomPanel.removeAll();
        				  bottomPanel.updateUI(); 
        				  JLabel notFound = new JLabel("Item not found with:   " + userInput + ". Choose another option or try again.");
        				  notFound.setForeground(Color.RED);
        				  bottomPanel.add(notFound); 
        				  bottomPanel.repaint(); 
        				  //logging search
        	              logger.log(Level.INFO, "Item with ID#: " + userInput + "searched for.  NOT FOUND.");
        			  } 
        			  else {
        				  bottomPanel.removeAll();
        				  bottomPanel.updateUI(); 
        				  JTextArea found = new JTextArea(output); 
        				  bottomPanel.add(found); 
        				  bottomPanel.repaint(); 
        				
        				  //logging search
        	              logger.log(Level.INFO, "Item with name/partial name '" + userInput + "' searched for.  FOUND.");
        			  }
          		  }  
          	  });
        	  frame.pack();
              frame.setVisible(true); 
           } 
        }); 

        
        //button 5
        JButton option5 = new JButton("Option 5"); 
        option5.addActionListener(new ActionListener() {
			
			 @Override
	            public void actionPerformed(ActionEvent event) {
	            	bottomPanel.removeAll(); 
	              	bottomPanel.updateUI();
	              	bottomPanel.setLayout(new FlowLayout()); 
	              	JTextArea userList = new JTextArea(app.showAllUsers());
	              	userList.setEditable(false);
	              	bottomPanel.add(userList); 
	                frame.pack();
	                frame.setVisible(true); 
	                logger.log(Level.INFO, "User Records Displayed.");
	            }
        	
        });
        
        
        //button 6
        JButton option6 = new JButton("Option 6"); 
        option6.addActionListener(new ActionListener() {
    	String fName;
		String lName;
		String ssnT;
		String monSal;
		String phoneNum;
		String addy;
    	
    	public void actionPerformed(ActionEvent e) {
    		
    		bottomPanel.removeAll();
    		bottomPanel.setLayout(null);
    		bottomPanel.updateUI();
    		Insets insets = bottomPanel.getInsets();
    		
    		JButton done = new JButton("Enter");
			JButton cancel = new JButton("Cancel");
    		
    		JLabel user = new JLabel("User: ");
    		JLabel firstName = new JLabel("First Name: ");
			JLabel lastName = new JLabel("Last Name: ");
			JLabel ssn = new JLabel("Social Security Number: ");
			JLabel monthlySalary = new JLabel("Monthly Salary: ");
			JLabel pNumber = new JLabel("Phone Number: ");
			JLabel address = new JLabel("Address: ");
			 
			JTextField userFname = new JTextField(15);
			JTextField userLname = new JTextField(15);
			JTextField employeeSsn = new JTextField(15);
			JTextField employeeMonthly = new JTextField(15);
			JTextField custPhone = new JTextField(15);
			JTextField custAddress = new JTextField(15);
			
			
			Dimension size = firstName.getPreferredSize();
			firstName.setBounds(150 + insets.left, 5 + insets.top, size.width, size.height);
    	    size = firstName.getPreferredSize(); 
			userFname.setBounds(400 + insets.left, 5 + insets.top, size.width, size.height );
    	    size = userFname.getPreferredSize(); 
			ssn.setBounds(150 + insets.left, 65 + insets.top, size.width, size.height);
    	    size = ssn.getPreferredSize(); 
			monthlySalary.setBounds(150 + insets.left, 95 + insets.top, size.width, size.height);
    	    size = monthlySalary.getPreferredSize(); 
			employeeSsn.setBounds(400 + insets.left, 65 + insets.top, size.width, size.height);
    	    size = employeeSsn.getPreferredSize(); 
			employeeMonthly.setBounds(400 + insets.left, 95 + insets.top, size.width, size.height);
    	    size = employeeMonthly.getPreferredSize(); 
			pNumber.setBounds(150 + insets.left, 65 + insets.top, size.width, size.height);
    	    size = pNumber.getPreferredSize(); 
			address.setBounds(150 + insets.left, 95 + insets.top, size.width, size.height);
    	    size = address.getPreferredSize(); 
			custPhone.setBounds(400 + insets.left, 65 + insets.top, size.width, size.height);
    	    size = custPhone.getPreferredSize(); 
			custAddress.setBounds(400 + insets.left, 95 + insets.top, size.width, size.height);
    	    size = custAddress.getPreferredSize(); 
    	    size = lastName.getPreferredSize(); 
    	    lastName.setBounds(150 + insets.left, 35 + insets.top, size.width, size.height);
    	    size = userLname.getPreferredSize(); 
    	    userLname.setBounds(400 + insets.left, 35 + insets.top, size.width, size.height);
    	    size = done.getPreferredSize(); 
    	    done.setBounds(400 + insets.left, 125 + insets.top, size.width, size.height);
    	    size = cancel.getPreferredSize(); 
    	    cancel.setBounds(140 + insets.left, 125 + insets.top, size.width, size.height);
    	    
    		String[] choices = {"Select One", "Employee", "Customer"};
    		final JComboBox<String> staff = new JComboBox<String>(choices);
    		user.setBounds(0, 0, 50, 50);
    		staff.setBounds(45, 0, 90, 50);
    		bottomPanel.add(user);
    		bottomPanel.add(staff);
    		staff.addActionListener(new ActionListener() {
    			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//System.out.println("Select something.");
					
					
					JComboBox source = (JComboBox) e.getSource();
					String id = (String) source.getSelectedItem();
					
		
					if(id.equals("Employee")) {
						bottomPanel.removeAll();
						bottomPanel.add(firstName);
						bottomPanel.add(userFname);
						bottomPanel.add(lastName);
						bottomPanel.add(userLname);
						bottomPanel.add(ssn);
						bottomPanel.add(employeeSsn);
						bottomPanel.add(monthlySalary);
						bottomPanel.add(employeeMonthly);
						bottomPanel.add(done);
						bottomPanel.add(cancel);

						cancel.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								bottomPanel.removeAll();
								JTextArea restart = new JTextArea();
								restart.setText("Welcome. Please choose an option.");
								restart.setBounds(0,0,300,50);
								bottomPanel.add(restart);
								frame.repaint();
							}
							
						});
						
						done.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								bottomPanel.removeAll();
								bottomPanel.setLayout(new FlowLayout());
								bottomPanel.updateUI();
								boolean flag = true;
								boolean flag2 = false, flag3 = false;
								int counter = 0;
								fName = userFname.getText();
								lName = userLname.getText();
								ssnT = employeeSsn.getText();
								monSal = employeeMonthly.getText();
								
								if(fName.matches("") || lName.matches("") || ssnT.matches("") || monSal.matches("")) {
									flag = false;
									counter = counter + 7;
									logger.log(Level.WARNING, "ADD FAIL - EMPLOYEE: User left at least one entry blank.");
									
								}
								else{
									
									if(ssnT.length() != 9 || !(ssnT.indexOf("[a-zA-z]") == -1)){
										flag = false;
										counter = counter + 2;
										logger.log(Level.WARNING, "ADD FAIL - EMPLOYEE: User entered and invalid entry for SSN.");
										
									}
									if(monSal.matches(".*[a-zA-z].*")) {
										flag2 = true;
										counter = counter + 3;
										logger.log(Level.WARNING, "ADD FAIL - EMPLOYEE: User entered an invalid entry for Monthly Salary.");
									}
									else {
										if(Float.parseFloat(monSal) < 0) {
											flag3 = true;
										    counter = counter + 3;
										    logger.log(Level.WARNING, "ADD FAIL - EMPLOYEE: User entered an invalid entry for Monthly Salary.");
										}
									}
								}
								if(flag2 == true && flag3 == true) {flag = true;}
								else {flag = false;}
								JTextArea restart = new JTextArea();
								JTextArea error1 = new JTextArea();
								JTextArea error2 = new JTextArea();
								JTextArea mainError = new JTextArea();
								mainError.setText("Cannot leave a blank field - ");
								error1.setText("Not a valid SSN - ");
								error2.setText("Not a valid salary- ");
								
								if(flag == true) {
									hardwareStore.addEmployee(fName, lName, Integer.parseInt(ssnT),Float.parseFloat(monSal));
									restart.setText("Employee has been added. Please choose another option.");
									logger.log(Level.INFO, "New Employee has been added.");
								}
								if(flag == false) {
									switch(counter) {
									case 7:
										bottomPanel.add(mainError);
										restart.setText("Error in adding employee. Please choose another option.");
										break;
									case 2:
										bottomPanel.add(error1);
										restart.setText("Error in adding employee. Please choose another option.");
										break;
									case 3:
										bottomPanel.add(error2);
										restart.setText("Error in adding employee. Please choose another option.");
										break;
									case 5:
										bottomPanel.add(error1);
										bottomPanel.add(error2);
										restart.setText("Error in adding employee. Please choose another option.");
										break;
								}
								bottomPanel.add(restart);
								frame.repaint();
							}
							
						}});
						
						frame.repaint();
						
					}
					if(id.equals("Customer")) {
						bottomPanel.removeAll();
						bottomPanel.add(firstName);
						bottomPanel.add(userFname);
						bottomPanel.add(lastName);
						bottomPanel.add(userLname);
						bottomPanel.add(pNumber);
						bottomPanel.add(custPhone);
						bottomPanel.add(address);
						bottomPanel.add(custAddress);
						bottomPanel.add(done);
						bottomPanel.add(cancel);
						cancel.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								bottomPanel.removeAll();
								JTextArea restart = new JTextArea();
								restart.setText("Welcome. Please choose an option.");
								restart.setBounds(0,0,300,50);
								bottomPanel.add(restart);
								frame.repaint();
							}
							
						});
						
						done.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								bottomPanel.removeAll();
								bottomPanel.setLayout(new FlowLayout());
								bottomPanel.updateUI();
								boolean flag = true;
								int counter = 0;
								fName = userFname.getText();
								lName = userLname.getText();
								phoneNum = custPhone.getText();
								addy = custAddress.getText();
								
								if(fName.matches("") || lName.matches("") || phoneNum.matches("") || addy.matches("")) {
									flag = false;
									counter = counter + 7;
									logger.log(Level.WARNING, "ADD FAIL - CUSTOMER : User left at least one entry blank.");
									
								}
								else{

									if(!(phoneNum.length() == 10) || phoneNum.matches(".*[a-zA-z].*")){

										flag = false;
										counter = counter + 2;
										logger.log(Level.WARNING, "ADD FAIL - CUSTOMER: User entered invalid entry for phone number.");
									}
									
								}
								//bottomPanel.removeAll();
								JTextArea restart = new JTextArea();
								JTextArea error1 = new JTextArea();
								JTextArea mainError = new JTextArea();
							
								mainError.setText("Cannot leave a blank field - ");
								error1.setText("Not a valid Phone Number - ");
							
								//restart.setBounds(0,0,500,50);
								
								if(flag == true) {
									hardwareStore.addCustomer(fName, lName, phoneNum, addy);
									restart.setText("Customer has been added. Please choose another option.");
									logger.log(Level.INFO,"New Customer added.");
								}
								if(flag == false) {
									switch(counter) {
									case 7:
										bottomPanel.add(mainError);
										restart.setText("Error in adding customer. Please choose another option.");
										break;
									case 2:
										bottomPanel.add(error1);
										restart.setText("Error in adding customer. Please choose another option.");
										break;
									default:

										break;
								}
								bottomPanel.add(restart);
								frame.repaint();
							}
							
							}});		
						
						frame.repaint();
					}
				}
    		});
    		frame.repaint();
    	}
    });
        
        
        //button 7
        JButton option7 = new JButton("Option 7"); 
        option7.addActionListener(new ActionListener() {
        	String fName;
			String lName;
			String ssnT;
			String monSal;
			String phoneNum;
			String addy;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bottomPanel.removeAll();
        		bottomPanel.setLayout(null);
        		
        		//start
        		JLabel firstName = new JLabel("First Name: ");
    			JLabel lastName = new JLabel("Last Name: ");
    			JLabel ssn = new JLabel("Social Security Number: ");
    			JLabel monthlySalary = new JLabel("Monthly Salary: ");
    			JLabel pNumber = new JLabel("Phone Number: ");
    			JLabel address = new JLabel("Address: ");
    			
    			JTextField userFname = new JTextField(15);
    			JTextField userLname = new JTextField(15);
    			JTextField employeeSsn = new JTextField(15);
    			JTextField employeeMonthly = new JTextField(15);
    			JTextField custPhone = new JTextField(15);
    			JTextField custAddress = new JTextField(15);
    			
    			Dimension size = firstName.getPreferredSize();
    			Insets insets = bottomPanel.getInsets();
    			
    			size = firstName.getPreferredSize(); 
    			firstName.setBounds(150 + insets.left, 5 + insets.top, size.width, size.height);
    			size = userFname.getPreferredSize(); 
    			userFname.setBounds(400 + insets.left, 5 + insets.top, size.width, size.height );
    			size = ssn.getPreferredSize(); 
    			ssn.setBounds(150 + insets.left, 65 + insets.top, size.width, size.height);
    			size = monthlySalary.getPreferredSize(); 
    			monthlySalary.setBounds(150 + insets.left, 95 + insets.top, size.width, size.height);
    			size = employeeSsn.getPreferredSize(); 
    			employeeSsn.setBounds(400 + insets.left, 65 + insets.top, size.width, size.height);
    			size = employeeMonthly.getPreferredSize(); 
    			employeeMonthly.setBounds(400 + insets.left, 95 + insets.top, size.width, size.height);
    			size = pNumber.getPreferredSize(); 
    			pNumber.setBounds(150 + insets.left, 65 + insets.top, size.width, size.height);
    			size = address.getPreferredSize(); 
    			address.setBounds(150 + insets.left, 95 + insets.top, size.width, size.height);
    			size = custPhone.getPreferredSize(); 
    			custPhone.setBounds(400 + insets.left, 65 + insets.top, size.width, size.height);
    			size = custAddress.getPreferredSize(); 
    			custAddress.setBounds(400 + insets.left, 95 + insets.top, size.width, size.height);
    			size = lastName.getPreferredSize(); 
        	    lastName.setBounds(150 + insets.left, 35 + insets.top, size.width, size.height);
    			size = userLname.getPreferredSize(); 
        	    userLname.setBounds(400 + insets.left, 35 + insets.top, size.width, size.height);
        	    
        		JButton done = new JButton("Enter");
				JButton cancel = new JButton("Cancel");
        		
        		JLabel updateUser = new JLabel("Enter user ID you would like to update: ");
        		JTextField updateText = new JTextField(15);
    			
    			size = updateUser.getPreferredSize(); 
    			updateUser.setBounds(150 + insets.left, 5 + insets.top, size.width, size.height);
    			size = updateText.getPreferredSize(); 
    			updateText.setBounds(400 + insets.left, 5 + insets.top, size.width, size.height );
    			
    			size = done.getPreferredSize(); 
    			done.setBounds(400 + insets.left, 125 + insets.top, size.width, size.height);
    			size = cancel.getPreferredSize(); 
        	    cancel.setBounds(140 + insets.left, 125 + insets.top, size.width, size.height);
        	    
    			
    			bottomPanel.add(updateUser);
    			bottomPanel.add(updateText);
    			bottomPanel.add(done);
    			bottomPanel.add(cancel);
    			
    			done.addActionListener(new ActionListener() {
    				int checkValid;
    				boolean testFlag = false;
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String userInput = updateText.getText();
				        if(userInput.matches(".*[a-zA-z].*") || userInput.matches("")) {
				        	bottomPanel.removeAll();
				        	JTextArea restart = new JTextArea();
							restart.setText("Not a valid option. Please choose another option.");
							restart.setBounds(0,0,500,50);
							logger.log(Level.WARNING,"UPDATE FAIL: User entered an invalid option.");
							bottomPanel.add(restart);
							frame.repaint();
				        }
				        User editUser = hardwareStore.findUser(Integer.parseInt(userInput));
				        if (editUser == null) {
				        	bottomPanel.removeAll();
				        	JTextArea restart = new JTextArea();
							restart.setText("User not found. Please choose another option.");
							restart.setBounds(0,0,500,50);
							logger.log(Level.WARNING, "UPDATE FAIL: User entered an entry that doesn't exist.");
							bottomPanel.add(restart);
							frame.repaint();
				            return;
				        }
				        
				        if (editUser.isEmployee && testFlag == false) {
				            //User is employee
							bottomPanel.removeAll();
							bottomPanel.updateUI();
							bottomPanel.setLayout(new FlowLayout());
							String name = "Employee";
							bottomPanel.setName(name);
							bottomPanel.add(firstName);
							bottomPanel.add(userFname);
							bottomPanel.add(lastName);
							bottomPanel.add(userLname);
							bottomPanel.add(ssn);
							bottomPanel.add(employeeSsn);
							bottomPanel.add(monthlySalary);
							bottomPanel.add(employeeMonthly);
							bottomPanel.add(done);
							bottomPanel.add(cancel);
							
							cancel.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									bottomPanel.removeAll();
									JTextArea restart = new JTextArea();
									restart.setText("Welcome. Please choose an option.");
									restart.setBounds(0,0,300,50);
									bottomPanel.add(restart);
									frame.repaint();
								}
								
							});
							
							done.addActionListener(new ActionListener() {
								
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									bottomPanel.removeAll();
									fName = userFname.getText();
									lName = userLname.getText();
									ssnT = employeeSsn.getText();
									monSal = employeeMonthly.getText();
									
									int counter = 0;
									
									boolean flag, flag1 = true, flag2 = true, flag3 = true;
									if(fName.matches("") || lName.matches("") || ssnT.matches("") || monSal.matches("")) {
										flag1 = false;
										flag2 = false;
										flag3 = false;
										counter = counter + 7;
										logger.log(Level.WARNING, "UPDATE FAIL - EMPLOYEE : User left at least one entry blank.");
									}
									else{
										
										if(ssnT.length() != 9 || !(ssnT.indexOf("[a-zA-z]") == -1)){

											flag1 = false;
											counter = counter + 2;
											logger.log(Level.WARNING, "UPDATE FAIL - EMPLOYEE: User did not enter a valid SSN.");
										}
										if(monSal.matches(".*[a-zA-z].*")) {
											flag2 = false;
											counter = counter + 3;
											logger.log(Level.WARNING, "UPDATE FAIL - EMPLOYEE: User did not enter a valid Monthly Salary.");
										}
										else {

											if(Float.parseFloat(monSal) < 0) {
												flag3 = false;
											    counter = counter + 3;
											    logger.log(Level.WARNING, "UPDATE FAIL - EMPLOYEE: User did not enter a valid Monthly Salary.");
											}
										}
										
									}
									if(flag2 == true && flag3 == true && flag1 == true) {flag = true;}
									else {flag = false;}
									JTextArea restart = new JTextArea();
									JTextArea error1 = new JTextArea();
									JTextArea error2 = new JTextArea();
									JTextArea mainError = new JTextArea();
									mainError.setText("Cannot leave a blank field - ");
									error1.setText("Not a valid SSN - ");
									error2.setText("Not a valid salary- ");
									
									if(flag == true) {
										checkValid = 1;
										testFlag = true;
										hardwareStore.editEmployeeInformation(Integer.parseInt(userInput),fName, lName, Integer.parseInt(ssnT),Float.parseFloat(monSal));
										logger.log(Level.INFO, "Updated Completed. Employee has been updated.");
										restart.setText("Employee has been added. Please choose another option.");
										frame.repaint();
									}
									if(flag == false) {
										checkValid = 2;
									    testFlag = true;
									bottomPanel.add(restart);
									frame.repaint();
								}
								
							}});
							
							frame.repaint();
							

				        }else if(!(editUser.isEmployee) && testFlag == false) {

							bottomPanel.removeAll();
							
							String name = "Customer";
							bottomPanel.setName(name);
							bottomPanel.add(firstName);
							bottomPanel.add(userFname);
							bottomPanel.add(lastName);
							bottomPanel.add(userLname);
							bottomPanel.add(pNumber);
							bottomPanel.add(custPhone);
							bottomPanel.add(address);
							bottomPanel.add(custAddress);
							bottomPanel.add(done);
							bottomPanel.add(cancel);
							cancel.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									bottomPanel.removeAll();
									JTextArea restart = new JTextArea();
									restart.setText("Welcome. Please choose an option.");
									restart.setBounds(0,0,300,50);
									bottomPanel.add(restart);
									frame.repaint();
								}
								
							});
							
							done.addActionListener(new ActionListener() {
									
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									bottomPanel.removeAll();
									boolean flag = true;
									int counter = 0;
									fName = userFname.getText();
									lName = userLname.getText();
									phoneNum = custPhone.getText();
									addy = custAddress.getText();
									
									if(fName.matches("") || lName.matches("") || phoneNum.matches("") || addy.matches("")) {
										flag = false;
										counter = counter + 7;
										logger.log(Level.WARNING, "UPDATE FAIL - CUSTOMER : User left at least one entry blank.");
									}
									else{

										if(!(phoneNum.length() == 10) || phoneNum.matches(".*[a-zA-z].*")){
											flag = false;
											counter = counter + 2;
											logger.log(Level.WARNING, "UPDATE FAIL - CUSTOMER: User did not enter a valid phone number.");
										}
										
									}
									JTextArea restart = new JTextArea();
									JTextArea error1 = new JTextArea();
									JTextArea mainError = new JTextArea();

									mainError.setText("Cannot leave a blank field - ");
									error1.setText("Not a valid Phone Number - ");
									
									if(flag == true) {
										checkValid = 3;
										testFlag = true;

										hardwareStore.editCustomerInformation(Integer.parseInt(userInput),fName, lName, phoneNum, addy);
										logger.log(Level.INFO, "Update Completed. Customer has been updated.");
										restart.setText("Employee has been added. Please choose another option.");
									}
									if(flag == false) {
										checkValid = 4;
										testFlag = true;
										bottomPanel.add(restart);
										frame.repaint();
									}
								
							}});		
							
							frame.repaint();
				            
				        }
				        else{
				        	bottomPanel.removeAll();
				        	bottomPanel.setLayout(new FlowLayout());
				        	bottomPanel.updateUI();
				        	JTextArea restart = new JTextArea();
				        	switch(checkValid) {
				        	case 1:
				        		restart.setText("Employee Updated. Choose another option.");
				        		break;
				        	case 2:
				        		restart.setText("Employee failed to update. Please enter correct values next time.Please Choose another option.");
				        		break;
				        	case 3:
				        		restart.setText("Customer updated. Choose another option.");
				        		break;
				        	case 4:
				        		restart.setText("Customer failed to update. Please enter correct vales next time. Please choose another option.");
				        		break;
				        	}
				        	bottomPanel.add(restart);
				        	frame.repaint();
				        	
				        }
				        testFlag = false;
				    }
    				
    			});
    			
				cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						bottomPanel.removeAll();
						JTextArea restart = new JTextArea();
						restart.setText("Welcome. Please choose an option.");
						restart.setBounds(0,0,300,50);
						bottomPanel.add(restart);
						frame.repaint();
					}
					
				});
    			frame.repaint();
			}
        });
        
        //button 8
        JButton option8 = new JButton("Option 8");
        option8.addActionListener(new ActionListener() {
        	
        	String itmID; 
        	String quantity; 
        	String empID;
        	String custID;  	
            
            @Override
            public void actionPerformed(ActionEvent event) {
            	bottomPanel.removeAll(); 
              	bottomPanel.updateUI();
 
              	bottomPanel.setLayout(null);
              	Insets insets = bottomPanel.getInsets(); 
    
              	JLabel prompt1 = new JLabel("Item ID: "); 
              	JTextField itemID = new JTextField(15);
              	Dimension size = prompt1.getPreferredSize(); 
              	prompt1.setBounds(220 + insets.left, 5 + insets.top, size.width, size.height);
              	size = itemID.getPreferredSize();
              	itemID.setBounds(480 + insets.left, 5 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt1); 
              	bottomPanel.add(itemID); 
              	             	
              	JLabel prompt2 = new JLabel("Items sold in the transactions (int)"); 
              	JTextField transQuantity = new JTextField(15); 
              	size = prompt2.getPreferredSize();
              	prompt2.setBounds(220 + insets.left, 35 + insets.top, size.width, size.height);
              	size = transQuantity.getPreferredSize();
              	transQuantity.setBounds(480 + insets.left, 35 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt2); 
              	bottomPanel.add(transQuantity);
              	
              	JLabel prompt3 = new JLabel("Employee ID:"); 
              	JTextField employeeID = new JTextField(15); 
              	size = prompt3.getPreferredSize();
              	prompt3.setBounds(220 + insets.left, 65 + insets.top, size.width, size.height);
              	size = employeeID.getPreferredSize();
              	employeeID.setBounds(480 + insets.left, 65 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt3); 
              	bottomPanel.add(employeeID);
              	
              	JLabel prompt4 = new JLabel("Customer ID:"); 
              	JTextField customerID = new JTextField(15); 
              	size = prompt4.getPreferredSize();
              	prompt4.setBounds(220 + insets.left, 95 + insets.top, size.width, size.height);
              	size = customerID.getPreferredSize();
              	customerID.setBounds(480 + insets.left, 95 + insets.top, size.width, size.height);
              	bottomPanel.add(prompt4); 
              	bottomPanel.add(customerID);
              	
              	JButton done = new JButton("Done"); 
              	size = done.getPreferredSize();
              	done.setBounds(425 + insets.left, 150 + insets.top, size.width, size.height);
              	bottomPanel.add(done); 
              	
        		done.addActionListener(new ActionListener() {
        			
					@Override
					public void actionPerformed(ActionEvent e) {
	                    bottomPanel.removeAll(); 
						bottomPanel.setLayout(new FlowLayout());
	                    bottomPanel.updateUI();
	              		int itemIndex = 0;           		
	                    
	              		itmID = itemID.getText();
	              		quantity = transQuantity.getText(); 
	              		empID = employeeID.getText(); 
	              		custID = customerID.getText(); 
	                    
	                	//checking if all required entries were entered
	                	boolean entriesAllPopulated = true; 
						if(itmID.matches("") || quantity.matches("") || empID.matches("") || custID.matches("")) {
							entriesAllPopulated = false;
							JLabel restart = new JLabel("BLANK REQUIRED ENTRY. Enter item ID, # of items sold, employee ID, and customer ID.  Try again or choose another option.");
							restart.setForeground(Color.RED);
							bottomPanel.add(restart); 
							bottomPanel.repaint(); 
						
			            	//logging add fail
							logger.log(Level.WARNING, "ADD FAIL:  User left one of the entries blank during complete transaction.");
						}
	                    
						if(entriesAllPopulated) {
							
							//checking if all inputs are valid
		                    boolean allEntriesValid = true; 
		                    itemIndex = hardwareStore.findItemIndex(itmID);
				            if (itemIndex == -1) {
								allEntriesValid = false;
								JLabel restart = new JLabel("ITEM ID NOT FOUND. Enter a valid item ID Try again or choose another option.");
								restart.setForeground(Color.RED);
								bottomPanel.add(restart); 
								bottomPanel.repaint(); 
							
								//logging add fail
								logger.log(Level.WARNING, "ADD FAIL:  Item ID not found in item list.");

				            }
				            else { //if item ID found
				            	
								//checking if the quantity is in the right format
								boolean quantityIsValid = true;
								try {
									Integer.parseInt(quantity); 
									
								} catch(NumberFormatException exc) {
									allEntriesValid = false;
									JLabel restart = new JLabel("INVALID QUANTITY. Enter a positive integer value for items sold. Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
								
									//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid number for items sold.");
					
								}
											
								if(quantityIsValid) {
									if (Integer.parseInt(quantity) < 0) {
										allEntriesValid = false;
										JLabel restart = new JLabel("INVALID QUANTITY. Enter a positive integer value for items sold.  Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
									
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid number for items sold.");

									}
									
									Item tempItem = hardwareStore.findItem(itmID);
									if(Integer.parseInt(quantity) > tempItem.getQuantity()) {
										allEntriesValid = false;
										JLabel restart = new JLabel("TRANSACTION QUANTITY EXCEEDS ITEM QUANTITY IN STOCK.  Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
									
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  Transaction quantity exceeds item quantity in stock.");
									}
								}
								
								//checking if entered employee ID is valid
								boolean employeeIDisInt = true;
								try {
									Integer.parseInt(empID); 
									
								} catch(NumberFormatException exc) {
									employeeIDisInt = false;
									allEntriesValid = false;
									JLabel restart = new JLabel("INVALID EMPLOYEE ID.  Enter a valid employee ID. Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
								
									//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid employee ID.");
					
								}
								
								if(employeeIDisInt) {
									
									if (hardwareStore.findUserIndex(Integer.parseInt(empID)) == -1) {
										allEntriesValid = false;
										JLabel restart = new JLabel("EMPLOYEE ID NOT FOUND IN USER LIST. Enter a valid employee ID. Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
									
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User did not enter an employee ID currently in the user list.");
										
									}
									
									if(!hardwareStore.findUser(Integer.parseInt(empID)).isEmployee) {
										allEntriesValid = false;
										JLabel restart = new JLabel("USER ID NOT AN EMPLOYEE ID. Enter a valid employee ID. Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
									
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User did not enter a user ID that is an employee ID.");
										
									}
								}
								
								
								//checking if entered customer ID is valid
								boolean customerIDisInt = true;
								try {
									Integer.parseInt(custID); 
									
								} catch(NumberFormatException exc) {
									customerIDisInt = false;
									allEntriesValid = false;
									JLabel restart = new JLabel("INVALID CUSTOMER ID. Enter a valid employee ID. Try again or choose another option.");
									restart.setForeground(Color.RED);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
								
									//logging add fail
									logger.log(Level.WARNING, "ADD FAIL:  User did not enter a valid employee ID.");
					
								}
								
								if(customerIDisInt) {
									
									if (hardwareStore.findUserIndex(Integer.parseInt(custID)) == -1) {
										allEntriesValid = false;
										JLabel restart = new JLabel("CUSTOMER ID NOT FOUND IN USER LIST. Enter a valid employee ID. Try again or choose another option.");
										restart.setForeground(Color.RED);
										bottomPanel.add(restart); 
										bottomPanel.repaint(); 
									
										//logging add fail
										logger.log(Level.WARNING, "ADD FAIL:  User did not enter an customer ID currently in the user list.");
										
									}
								}

			                    
				              	//if user inputs are all valid, set string to JTextField user input
				              	if(allEntriesValid) {
		 

				                    hardwareStore.progressTransaction(itmID, Integer.parseInt(quantity), Integer.parseInt(custID), Integer.parseInt(empID), itemIndex);
									
				                    //confirmed message
									JLabel restart = new JLabel("Transaction complete.  Choose another option.");
									restart.setForeground(Color.GREEN);
									bottomPanel.add(restart); 
									bottomPanel.repaint(); 
									
						            //logging transaction
									logger.log(Level.INFO, "Transaction completed with ID#: " + itmID + ".");
				              	}	            
							}
						}
				            }
			              	

        		});
          	  frame.pack();  
              frame.setVisible(true); 
            }
          }); 
        
        
        //button 9
        JButton option9 = new JButton("Option 9"); 
        option9.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
            	bottomPanel.removeAll(); 
              	bottomPanel.updateUI();
              	bottomPanel.setLayout(new FlowLayout()); 
              	JTextArea transactionList = new JTextArea(app.showAllTransactions());
              	transactionList.setEditable(false);
              	bottomPanel.add(transactionList); 
                frame.pack();
                frame.setVisible(true); 
                
	            //logging display of transactions list
				logger.log(Level.INFO, "Completed transactions list displayed.");
            }
          }); 
        
        
        //button 10
        JButton option10 = new JButton("Option 10");
        option10.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent event) {
            	bottomPanel.removeAll(); 
              	bottomPanel.updateUI();
              	try {
					app.saveDatabase();
					bottomPanel.setLayout(new FlowLayout());
					JLabel saved = new JLabel("Saved"); 
					bottomPanel.add(saved); 
					bottomPanel.repaint(); 
					
		            //logging display of transactions list
					logger.log(Level.INFO, "All changes saved to database.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		            //logging display of transactions list
					logger.log(Level.SEVERE, "SAVE UNSUCCESSFUL.");
				}
            }
          }); 
        
        //adding buttons to the buttonPanel
        buttonPanel.add(option1);        
        buttonPanel.add(option2);
        buttonPanel.add(option3);
        buttonPanel.add(option4);
        buttonPanel.add(option5);
        buttonPanel.add(option6);
        buttonPanel.add(option7);
        buttonPanel.add(option8); 
        buttonPanel.add(option9);
        buttonPanel.add(option10);
        
  
        frame.pack(); 
        frame.setResizable(false);
       	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }
}


    
