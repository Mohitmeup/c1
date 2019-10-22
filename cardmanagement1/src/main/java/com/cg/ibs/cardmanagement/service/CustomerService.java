package com.cg.ibs.cardmanagement.service;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

public interface CustomerService{
	boolean verifyAccountNumber(BigInteger accountNumber) throws SQLException, IOException;
	boolean setQueryNewDebitCard(BigInteger accountNumber, int myChoice) throws SQLException, IOException;
	boolean setQueryUpgradeDebitCard(BigInteger debitcardNumber, int myChoice) throws SQLException, IOException;
}
