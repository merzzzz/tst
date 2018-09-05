package com.javaproject.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.javaproject.data.Address;

/**
 * Created 09/05/18 08:51
 * 
 * @author Mergin
 *
 */
public class AddressBookApp {
	LinkedHashMap<String, List<Address>> addressBooks = new LinkedHashMap<String, List<Address>>();

	public AddressBookApp() {
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * perform add, remove, print functionalities based on user choice getting users
	 * input via scanner and executing API's via switch statement
	 * 
	 * @return void
	 */
	public static void main(String[] args) {
		AddressBookApp app = new AddressBookApp();
		boolean exit = false;
		int count = 0;
		System.out.println("Welcome to address book application");
		while (!exit) {
			try {
				Scanner inp = new Scanner(System.in);
				System.out.println(count == 0 ? "Please enter an option (1.Add, 2.Remove 3.Print 4.exit)"
						: "\nIf you wish to continue, Please enter an option between 1-3 (1.Add, 2.Remove 3.Print).Else press 4 to exit");
				count++;
				int selectedOption;
				selectedOption = inp.nextInt();
				switch (selectedOption) {
				case 1:
					app.addNewContact();
					break;
				case 2:
					app.removeExistingContacts();
					break;
				case 3:
					app.printContactInfo();
					break;
				case 4:
					exit = true;
					break;
				default:
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please enter valid option");
			}
		}
	}

	/**
	 * Add new contact name and number in new/existing address books
	 * 
	 * @return void
	 */
	private void addNewContact() {
		System.out.println("You have selected Add option ");
		String addressBookName = getAndValidateAddressBookName();
		String addName = getAndValidateContactName();
		String addPhoneNum = getAndValidateContactNumber();
		addContactsInAddressBook(addressBookName, addName, addPhoneNum);
	}

	/**
	 * Acceptance Criteria point 5:Users should be able to maintain multiple address
	 * books. So asking user to add address book name
	 * @return addressBookName
	 */
	public String getAndValidateAddressBookName() {
		boolean validBkName = false;
		String addressBookName = null;
		while (!validBkName) {
			System.out.println("Enter the Address book in which the address has to be added\n");
			Scanner inpAddressBook = new Scanner(System.in);
			addressBookName = inpAddressBook.nextLine();
			// Validating book name. It should have minimum of 1 character and maximum
			// of 40 char
			String pattern = "^[a-zA-Z0-9_-]{1,40}$";
			if (null != addressBookName && addressBookName.matches(pattern)) {
				validBkName = true;
			} else {
				System.out.println("Invalid book name. Please enter again");
			}
		}
		return addressBookName;
	}

	/**
	 * if address book already found, add name and phone number in existing address
	 * book, else add entry in new address book
	 * 
	 * @return void
	 */
	private void addContactsInAddressBook(String addressBookName, String addName, String addPhoneNum) {
		if (addressBooks.containsKey(addressBookName)) {
			List<Address> modifiedAddress = new CopyOnWriteArrayList<Address>(addressBooks.get(addressBookName));
			modifiedAddress.add(new Address(addName.trim(), addPhoneNum.trim()));
			addressBooks.put(addressBookName, modifiedAddress);
			System.out.println("Added the entry (" + addName + "," + addPhoneNum + ")in existing Address Book '"
					+ addressBookName + "'");
		} else {
			List<Address> address = new CopyOnWriteArrayList<Address>();
			address.add(new Address(addName.trim(), addPhoneNum.trim()));
			addressBooks.put(addressBookName, address);
			System.out.println("Added a new entry (" + addName + "," + addPhoneNum + ")in new Address Book '"
					+ addressBookName + "'");
		}
	}

	/**
	 * gets contact number from user and validates it
	 * 
	 * @return contact number
	 */
	public String getAndValidateContactNumber() {
		boolean validNum = false;
		String addPhoneNum = null;
		while (!validNum) {
			System.out.print("Enter 10 digit phone number ");
			Scanner inpAddPhoneNum = new Scanner(System.in);
			addPhoneNum = inpAddPhoneNum.nextLine();
			// Validation code for phone number
			String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
			if (null != addPhoneNum && addPhoneNum.matches(pattern)) {
				validNum = true;
			} else {
				System.out.println("Invalid phone number.Please enter the 10 digit phone number ");

			}
		}
		return addPhoneNum;
	}

	/**
	 * gets contact name from user and validates it
	 * 
	 * @return contact name
	 */
	public String getAndValidateContactName() {
		boolean validName = false;
		String addName = null;
		while (!validName) {
			System.out.print("Enter contact name ");
			Scanner inpAddName = new Scanner(System.in);
			addName = inpAddName.nextLine();
			// Validating contact name. It should have minimum of 3 characters and maximum
			// of 40 char
			String pattern = "^[a-zA-Z0-9_-]{3,40}$";
			if (null != addName && addName.matches(pattern)) {
				validName = true;
			} else {
				System.out.println("Invalid contact name.Please enter minimum 3 characters ");
			}
		}
		return addName;
	}

	/**
	 * Remove contacts,if contact exists in memory
	 * 
	 * @return void
	 */
	private void removeExistingContacts() {
		if (addressBooks.isEmpty()) {
			System.out.println("No Contacts found");
		} else {
			// addressBooks.clear();
			Scanner inpAddrBookNum = new Scanner(System.in);
			System.out.print("Please enter address book for which the contacts need to be printed \n");
			String selectedAddrBook = inpAddrBookNum.nextLine();
			List<Address> value = new ArrayList<Address>();
			addressBooks.forEach((key, val) -> {
				for (Address addr : val) {
					if (addr.getName().equals(selectedAddrBook)) {
						value.add(addr);
					}
					val.removeAll(value);
				}
			});
			addressBooks.forEach((k, v) -> {
				System.out.println("key : " + k + " val : " + v);
			});
			System.out.println("Existing Entries Removed");
		}
	}

	/**
	 * printing info based on AC 4(to print all contacts in an address book) and
	 * 6(to print a unique set of all contacts across multiple address books)
	 * 
	 * @return void
	 */
	private void printContactInfo() {
		if (addressBooks.isEmpty()) {
			System.out.println("No Entries found");
		} else {
			boolean endLoop = false;
			while (!endLoop) {
				Scanner inpNum = new Scanner(System.in);
				System.out.print(
						"Please select 1 to print all contacts in an address book and 2 to print a unique set of all contacts across multiple address books \n");
				int selectedNum = inpNum.nextInt();

				if (selectedNum == 1) {
					endLoop = printAnAddressBookContacts();

				} else if (selectedNum == 2) {
					endLoop = printAllUniqueContracts();

				} else {
					endLoop = false;
					System.out.println("Invalid number.Please select either 1 or 2");
				}
			}
		}
	}

	/**
	 * print unique set of contacts across address book's
	 * 
	 * @return boolean if endLoop is true or false
	 */
	private boolean printAllUniqueContracts() {
		boolean endLoop;
		endLoop = true;
		List<Address> address = new ArrayList<Address>();
		addressBooks.forEach((k, v) -> {
			address.addAll(addressBooks.get(k));
		});
		// collecting as Set to avoid duplicate entries
		Set<Address> addressAsSet = address.stream().collect(Collectors.toSet());
		addressAsSet.forEach((setVal) -> System.out.println(setVal.getName() + ": " + setVal.getPhoneNumber()));
		return endLoop;
	}

	/**
	 * print an addressbook's contacts
	 * 
	 * @return boolean if endLoop is true or false
	 */
	private boolean printAnAddressBookContacts() {
		boolean endLoop;
		endLoop = true;
		boolean endSelOption = false;
		while (!endSelOption) {
			Scanner inpAddrBookNum = new Scanner(System.in);
			System.out.print("Please enter address book for which the contacts need to be printed \n");
			String selectedAddrBook = inpAddrBookNum.nextLine();
			if (addressBooks.containsKey(selectedAddrBook)) {
				endSelOption = true;
				System.out.println("Printing all contacts in address book : " + selectedAddrBook);
				System.out.println("---------------------------------------------------");
				List<Address> modifiedAddress = addressBooks.get(selectedAddrBook);
				modifiedAddress
						.forEach(address -> System.out.println(address.getName() + ": " + address.getPhoneNumber()));
			} else {
				endSelOption = false;
				System.out.println("No address book found in name '" + selectedAddrBook
						+ "'.Select one of the Address book from " + addressBooks.keySet());
			}
		}
		return endLoop;
	}
}
