package com.cg.ibs.cardmanagement.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.cg.ibs.cardmanagement.bean.DebitCardBean;

public interface BankDao {
	boolean verifyQueryId(String queryId) throws SQLException, IOException;

	String getNewType(String queryId) throws SQLException, IOException;
	boolean actionANDC(String queryId, DebitCardBean debitbean) throws SQLException, IOException;
	boolean updateStatus(String queryId, String status) throws SQLException, IOException;
	boolean actionBlockDC(String queryId, String status) throws SQLException, IOException ;
	boolean actionUpgradeDC(String queryId) throws SQLException, IOException;
}