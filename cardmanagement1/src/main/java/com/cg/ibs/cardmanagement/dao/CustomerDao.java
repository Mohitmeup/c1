package com.cg.ibs.cardmanagement.dao;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;

public interface CustomerDao {
	boolean verifyAccountNumber(BigInteger accountNumber) throws SQLException, IOException;

	boolean setNewQuery(BigInteger accountNumber, BigInteger debitcardNumber, CaseIdBean caseIdObj)
			throws SQLException, IOException;

	BigInteger getAccountNumber(BigInteger debitcardNumber) throws SQLException, IOException;

	int getExistingPin(BigInteger debitCardNumber) throws SQLException, IOException;

	boolean resetDebitPin(BigInteger debitCardNumber, Integer newPin) throws SQLException, IOException;

	List<DebitCardBean> viewAllDebitCards(BigInteger accountNumber);

	List<DebitCardTransaction> getDebitTrans(int days, BigInteger debitCardNumber);


}