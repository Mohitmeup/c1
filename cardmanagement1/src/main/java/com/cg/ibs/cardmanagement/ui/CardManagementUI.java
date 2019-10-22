package com.cg.ibs.cardmanagement.ui;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.service.CustomerService;

public class CardManagementUI {
	static boolean success;
	static Scanner scan;
	boolean check;
	BigInteger accountNumber;
	int newCardType;
	CustomerService customerService;
	

	void applyNewDebitCard() {

		success = false;
		System.out.println("You are applying for a new Debit Card");
		while (!success) {
			try {
				System.out.println("Enter Account Number you want to apply debit card for :");

				accountNumber = scan.nextBigInteger();
				check = ;

				success = true;
			} catch (InputMismatchException wrongFormat) {

				// scan.next();
				if (scan.next().equalsIgnoreCase("x"))
					return;
				System.out.println("Renter 11 digit account number");

			} catch (IBSException notFound) {
				System.out.println(notFound.getMessage());

			}
		}
		if (check) {
			success = false;
			while (!success) {
				try {
					System.out.println("We offer three kinds of Debit Cards:");
					System.out.println(".....................................");
					System.out.println("1 for  Platinum");
					System.out.println("2 for  Gold");
					System.out.println("3 for Silver");
					System.out.println("Choose between 1 to 3");

					newCardType = scan.nextInt();

					System.out.println("You have applied for: " + customService.getNewCardtype(newCardType));
					customerReferenceId = customService.applyNewDebitCard(accountNumber,
							customService.getNewCardtype(newCardType));
					System.out.println("Application for new debit card success!!");
					System.out.println("Your reference Id is " + customerReferenceId);
					success = true;

				} catch (InputMismatchException cardNew) {
					if (scan.next().equalsIgnoreCase("x"))
						return;
					System.out.println("Enter 1/2/3");

				} catch (IBSException cardNew) {
					System.out.println(cardNew.getMessage());

				}

			}
		}
	}

}