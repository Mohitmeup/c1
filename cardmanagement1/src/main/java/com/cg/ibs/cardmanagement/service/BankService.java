package com.cg.ibs.cardmanagement.service;

import java.io.IOException;
import java.sql.SQLException;

import com.cg.ibs.cardmanagement.bean.DebitCardBean;

public interface BankService{
	boolean verifyQueryId(String queryId) throws SQLException, IOException;
	boolean actionANDC (String queryId, DebitCardBean debitbean) throws SQLException, IOException;
	boolean blockDC(String queryId) throws SQLException, IOException;
	
}

