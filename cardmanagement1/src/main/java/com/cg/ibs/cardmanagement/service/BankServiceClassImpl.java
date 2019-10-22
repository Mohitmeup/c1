package com.cg.ibs.cardmanagement.service;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

import javax.net.ssl.SSLEngineResult.Status;

import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.dao.BankDao;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;

public class BankServiceClassImpl implements BankService{
	Random random = new Random();
	BankDao bankdao;
	public BankServiceClassImpl() {
	bankdao = new CardManagementDaoImpl();
	}

	@Override
	public boolean verifyQueryId(String queryId) throws SQLException, IOException {
		boolean result = false;
		if(bankdao.verifyQueryId(queryId)) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean actionANDC(String queryId, DebitCardBean debitbean) throws SQLException, IOException {
		boolean result = false;
		String cvvString = String.format("%04d", random.nextInt(1000));
		int cvv = Integer.parseInt(cvvString);
		debitbean.setDebitCvvNum(cvv);
		String pinString = String.format("%04d", random.nextInt(10000));
		int pin = Integer.parseInt(pinString);
		debitbean.setDebitCurrentPin(pin);
		Long first14 = (long) (Math.random() * 100000000000000L);
		Long number = 5200000000000000L + first14;
		BigInteger debitCardNumber = BigInteger.valueOf(number);
		debitbean.setDebitCardNumber(debitCardNumber);
		String status = "Active";
		debitbean.setDebitCardStatus(status);
		LocalDate expiry = LocalDate.now().plusYears(5);
		debitbean.setDebitDateOfExpiry(expiry);
		String type = bankdao.getNewType(queryId);
		if (type.equals("Platinum")) {
			debitbean.setDebitCardType("Platinum");
		} else if (type.equals("Gold")) {
			debitbean.setDebitCardType("Gold");
		} else if (type.equals("Silver")) {
			debitbean.setDebitCardType("Silver");
		}
		if (bankdao.actionANDC(queryId, debitbean)) {
			result = true;
		}return result;
	
}

	public boolean updateStatus(String queryId, int myChoice) throws SQLException, IOException {
		boolean result = false;
		String status = "";
		if (myChoice == 1) {
			status = "Approved";
		}
		if (myChoice == 2) {
			status = "In Process";
		}
		if (myChoice == 3) {
			status = "Rejected";
		}
		if (bankdao.updateStatus(queryId, status)) {
			result = true;
		}
		return result;
	}
	
	@Override
	public boolean blockDC(String queryId) throws SQLException, IOException  {
		boolean result = false;
		String status = "Blocked";
	if(bankdao.actionBlockDC(queryId, status)) {
		result = true;
	}return result;
	}
}