package com.cg.ibs.cardmanagement.service;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;
import com.cg.ibs.cardmanagement.dao.CustomerDao;
import com.cg.ibs.cardmanagement.exceptionhandling.ErrorMessages;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;

public class CustomerServiceImpl implements CustomerService {
	CustomerDao customerDao;
	CaseIdBean caseIdObj = new CaseIdBean();
	String caseIdGenOne = " ";
	static String caseIdTotal = " ";
	static int caseIdGenTwo = 0;
	String customerReferenceID = null;

	public CustomerServiceImpl() {
		customerDao = new CardManagementDaoImpl();

	}

	String addToQueryTable(String caseIdGenOne) {
		String caseIdGenTw = String.format("%04d", caseIdGenTwo);
		caseIdTotal = caseIdGenOne + caseIdGenTw;
		caseIdGenTwo++;
		return caseIdTotal;
	}

	@Override
	public boolean verifyAccountNumber(BigInteger accountNumber) throws SQLException, IOException {
		boolean result = false;
		if (customerDao.verifyAccountNumber(accountNumber)) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean setQueryNewDebitCard(BigInteger accountNumber, int myChoice) throws SQLException, IOException {
		boolean result = false;
		BigInteger debitCardNumber = null;
		caseIdGenOne = "ANDC";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		customerReferenceID = (caseIdTotal + accountNumber.toString().substring(4));
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setCaseTimeStamp(LocalDateTime.now());
		caseIdObj.setAccountNumber(accountNumber);
		caseIdObj.setCardNumber(debitCardNumber);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Platinum");
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Gold");
		} else if (myChoice == 3) {
			caseIdObj.setDefineQuery("Silver");
		}
		if (customerDao.setNewQuery(accountNumber, debitCardNumber, caseIdObj)) {
			result = true;
		}
		return result;

	}

	public boolean setQueryUpgradeDebitCard(BigInteger debitCardNumber, int myChoice) throws SQLException, IOException {
		boolean result = false;
		BigInteger accountNumber = null;
		accountNumber = customerDao.getAccountNumber(debitCardNumber);
		caseIdGenOne = "RDCU";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		customerReferenceID = (caseIdTotal + accountNumber.toString().substring(4));
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setCaseTimeStamp(LocalDateTime.now());
		caseIdObj.setAccountNumber(accountNumber);
		caseIdObj.setCardNumber(debitCardNumber);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Platinum");
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Gold");
		} else if (myChoice == 3) {
			caseIdObj.setDefineQuery("Silver");
		}
		if (customerDao.setNewQuery(accountNumber, debitCardNumber, caseIdObj)) {
			result = true;
		}
		return result;
	}

	public boolean setQueryBlockDebitCard(BigInteger debitCardNumber) throws SQLException, IOException {
		boolean result = false;
		BigInteger accountNumber = null;
		accountNumber = customerDao.getAccountNumber(debitCardNumber);
		caseIdGenOne = "RDCL";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		customerReferenceID = (caseIdTotal + accountNumber.toString().substring(4));
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setCaseTimeStamp(LocalDateTime.now());
		caseIdObj.setAccountNumber(accountNumber);
		caseIdObj.setCardNumber(debitCardNumber);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		caseIdObj.setDefineQuery("Blocked");
		if (customerDao.setNewQuery(accountNumber, debitCardNumber, caseIdObj)) {
			result = true;
		}
		return result;
	}

	public boolean setQueryDebitMismatch(BigInteger debitCardNumber, String transactionId) throws SQLException, IOException {
		boolean result = false;
		BigInteger accountNumber = null;
		accountNumber = customerDao.getAccountNumber(debitCardNumber);
		caseIdGenOne = "RDCL";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		customerReferenceID = (caseIdTotal + accountNumber.toString().substring(4));
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setCaseTimeStamp(LocalDateTime.now());
		caseIdObj.setAccountNumber(accountNumber);
		caseIdObj.setCardNumber(debitCardNumber);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		caseIdObj.setDefineQuery(transactionId);
		if (customerDao.setNewQuery(accountNumber, debitCardNumber, caseIdObj)) {
			result = true;
		}
		return result;
	}

	public boolean checkDebitPin(BigInteger debitCardNumber, Integer entryPin) throws SQLException, IOException {
		boolean result = false;
		Integer existingPin = customerDao.getExistingPin(debitCardNumber);
		if (entryPin == existingPin) {
			result = true;
		}
		return result;
	}

	public boolean resetDebitPin(BigInteger debitCardNumber, Integer newPin) throws SQLException, IOException  {
		boolean result = false;
		if(customerDao.resetDebitPin(debitCardNumber, newPin)) {
			result = true;
		}return result;
	}

	public List<DebitCardBean> viewAllDebitCards(BigInteger accountNumber) {

			return customerDao.viewAllDebitCards(accountNumber);
	}

	public List<DebitCardTransaction> getDebitTransactions(int days, BigInteger debitCardNumber) throws IBSException {

		List<DebitCardTransaction> debitCardBeanTrns = customerDao.getDebitTrans(days, debitCardNumber);
		if (debitCardBeanTrns.isEmpty())
			throw new IBSException(ErrorMessages.NO_TRANSACTIONS_MESSAGE);
		return customerDao.getDebitTrans(days, debitCardNumber);

	}
}