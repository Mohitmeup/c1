package com.cg.ibs.cardmanagement.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;

public class CardManagementDaoImpl implements CustomerDao, BankDao {

	@Override
	public boolean verifyAccountNumber(BigInteger accountNumber) throws SQLException, IOException {
		boolean result = false;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_ACCOUNT_NUMBER)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(accountNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
		}
		return result;
	}
	@Override

	public boolean setNewQuery(BigInteger accountNumber, BigInteger debitcardNumber, CaseIdBean caseIdObj)
			throws SQLException, IOException {
		boolean result = false;
		BigInteger uci = null;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement1 = connection
				.prepareStatement(SqlQueries.GET_UCI_FROM_ACCOUNT_NUMBER)) {
			preparedStatement1.setBigDecimal(1, new BigDecimal(accountNumber));
			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				uci = resultSet.getBigDecimal("uci").toBigInteger();
			}
		} catch (SQLException e) {
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SET_QUERY)) {
			preparedStatement.setString(1, caseIdObj.getCaseIdTotal());
			preparedStatement.setString(2, caseIdObj.getCaseIdTotal());
			preparedStatement.setString(3, caseIdObj.getStatusOfQuery());
			preparedStatement.setBigDecimal(4, new BigDecimal(accountNumber));
			preparedStatement.setBigDecimal(5, new BigDecimal(uci));
			preparedStatement.setString(6, caseIdObj.getDefineQuery());
			preparedStatement.setBigDecimal(7, new BigDecimal(caseIdObj.getCardNumber()));
			preparedStatement.setString(8, caseIdObj.getCustomerReferenceId());
			int update = preparedStatement.executeUpdate();
			if (update > 0) {
				result = true;
			}
		} catch (SQLException e) {
		}
		return result;
	}

	public boolean verifyQueryId(String queryId) throws SQLException, IOException {
		boolean result = false;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.VERIFY_QUERY_ID)) {
			preparedStatement.setString(1, queryId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					result = true;
				}
			}
		} catch (SQLException e) {
		}
		return result;
	}

	
	@Override
	public String getNewType(String queryId) throws SQLException, IOException {
		String type = "";
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_NEW_DEBIT_CARD_TYPE)) {
			preparedStatement.setString(1, queryId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					type = resultSet.getString("define_query");
				}
			}
		} catch (SQLException e) {
		}
		return type;
	}

	
	@Override
	public boolean actionANDC(String queryId, DebitCardBean debitbean) throws SQLException, IOException {
		BigInteger accNum = null;
		BigInteger uci = null;
		String firstName = "";
		String lastName = "";
		String name = "";
		boolean result = false;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_ACCOUNT_NUMBER_UCI_FROM_QUERY_ID)) {
			preparedStatement.setString(1, queryId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					accNum = resultSet.getBigDecimal("account_number").toBigInteger();
					uci = resultSet.getBigDecimal("uci").toBigInteger();
				}
			}
		} catch (SQLException e) {
		}
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(SqlQueries.GET_DETAILS_NEW_DEBIT_CARD)) {
			preparedStatement1.setBigDecimal(1, new BigDecimal(uci));
			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
				
					firstName = resultSet.getString("first_name");
					lastName = resultSet.getString("last_name");
					name = firstName.concat("  ").concat(lastName);
				}
			}
		} catch (SQLException e) {
		}
		try (PreparedStatement preparedStatement2 = connection.prepareStatement(SqlQueries.ACTION_NEW_DEBIT_CARD)) {
			preparedStatement2.setBigDecimal(1, new BigDecimal(accNum));
			preparedStatement2.setBigDecimal(2, new BigDecimal(debitbean.getDebitCardNumber()));
			preparedStatement2.setString(3, debitbean.getDebitCardStatus());
			preparedStatement2.setString(4, name);
			preparedStatement2.setInt(5, debitbean.getDebitCvvNum());
			preparedStatement2.setInt(6, debitbean.getDebitCurrentPin());
			preparedStatement2.setDate(7, Date.valueOf(debitbean.getDebitDateOfExpiry()));
			preparedStatement2.setBigDecimal(8, new BigDecimal(uci));
			preparedStatement2.setString(9, debitbean.getDebitCardType());
			if (preparedStatement2.executeUpdate() > 0) {
				result = true;
			}

		} catch (SQLException e) {
		}
		return result;
	}

	
	@Override
	public boolean updateStatus(String queryId, String status) throws SQLException, IOException {
		boolean result = false;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ADMIN_STATUS_UPDATE)) {
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, queryId);
			int output = preparedStatement.executeUpdate();
			if (output > 0) {
				result = true;
			}
		}catch (SQLException e) {
		}
		return result;
	}

	
	@Override
	public BigInteger getAccountNumber(BigInteger debitcardNumber) throws SQLException, IOException {
		BigInteger accountNumber = null;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_ACCOUNT_OF_DEBIT_CARD)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(debitcardNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					 accountNumber = resultSet.getBigDecimal("account_number").toBigInteger();
				}
			}
		} catch (SQLException e) {
		}
		return accountNumber;
	}

	
	@Override
	public int getExistingPin(BigInteger debitCardNumber) throws SQLException, IOException {
		Integer pin = null;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_DEBIT_CARD_PIN)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(debitCardNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					pin = resultSet.getInt("debit_current_pin");
				}
			}
		} catch (SQLException e) {
		}
		return pin;
	}
	

	@Override
	public boolean resetDebitPin(BigInteger debitCardNumber, Integer newPin) throws SQLException, IOException {
		boolean result = false;
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.RESET_DEBIT_PIN)) {
			preparedStatement.setInt(1, newPin);
			preparedStatement.setBigDecimal(2, new BigDecimal(debitCardNumber));
			int output = preparedStatement.executeUpdate();
			if (output > 0) {
				result = true;
			}
		}catch (SQLException e) {
		}
		return result;
	}

	public boolean actionBlockDC(String queryId, String status) throws SQLException, IOException {
		boolean result = false;
		BigInteger debitCardNum = null;
		String debitCardStatus = "";
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_DETAILS_CARD)) {
			preparedStatement.setString(1, queryId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					debitCardNum = resultSet.getBigDecimal("card_num").toBigInteger();
					debitCardStatus = resultSet.getString("define_query");
				}
			}
		} catch (SQLException e) {
		}
		try (PreparedStatement preparedStatement2 = connection.prepareStatement(SqlQueries.ACTION_DEBIT_CARD_BLOCK)) {
			preparedStatement2.setString(1, debitCardStatus);
			preparedStatement2.setBigDecimal(2, new BigDecimal(debitCardNum));
			if (preparedStatement2.executeUpdate() > 0) {
				result = true;
			}
		} catch (SQLException e) {
		}
		return result;
	}

	@Override
	public boolean actionUpgradeDC(String queryId) throws SQLException, IOException {
		boolean result = false;
		BigInteger debitCardNum = null;
		String debitCardType = "";
		Connection connection = ConnectionProvider.getInstance().getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_DETAILS_CARD)) {
			preparedStatement.setString(1, queryId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					debitCardNum = resultSet.getBigDecimal("card_num").toBigInteger();
					debitCardType = resultSet.getString("define_query");
					result = true;
				}
			}
		} catch (SQLException e) {
		}
		try (PreparedStatement preparedStatement2 = connection.prepareStatement(SqlQueries.ACTION_DEBIT_CARD_UPGRADE)) {
			preparedStatement2.setString(1, debitCardType);
			preparedStatement2.setBigDecimal(2, new BigDecimal(debitCardNum));
			preparedStatement2.executeUpdate();
		} catch (SQLException e) {
		}
	return result;
	}
	@Override
	public List<DebitCardBean> viewAllDebitCards(BigInteger accountNumber) {
		List<DebitCardBean> debitCards = new ArrayList<>();
		try (Connection connection = ConnectionProvider.getInstance().getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.SELECT_ALL_DEBIT_CARDS)) {
			preparedStatement.setBigDecimal(1, new BigDecimal(accountNumber));
			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {

					DebitCardBean deb = new DebitCardBean();

					deb.setDebitCardNumber(resultSet.getBigDecimal("debit_card_number").toBigInteger());
					deb.setDebitCardStatus(resultSet.getString("debit_card_status"));
					deb.setNameOnDebitCard(resultSet.getString("name_on_deb_card"));
					deb.setDebitDateOfExpiry(resultSet.getDate("debit_expiry_date").toLocalDate());
					deb.setDebitCardType(resultSet.getString("debit_card_type"));
					debitCards.add(deb);

				}

			} catch (Exception e) {

			}
		} catch (Exception e) {
		}
		return debitCards;

	}
	@Override
	public List<DebitCardTransaction> getDebitTrans(int days, BigInteger debitCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

//	public boolean actionDebitMismatch(String queryId) {
//		boolean result = false;
//		BigInteger debitCardNum = null;
//		String debitCardType = "";
//		Connection connection = ConnectionProvider.getInstance().getConnection();
//		try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.GET_DETAILS_CARD)) {
//			preparedStatement.setString(1, queryId);
//			try (ResultSet resultSet = preparedStatement.executeQuery()) {
//				while (resultSet.next()) {
//					debitCardNum = resultSet.getBigDecimal("card_num").toBigInteger();
//					debitCardType = resultSet.getString("define_query");
//					result = true;
//				}
//			}
//		} catch (SQLException e) {
//		}
//		try (PreparedStatement preparedStatement2 = connection.prepareStatement(SqlQueries.ACTION_DEBIT_CARD_UPGRADE)) {
//			preparedStatement2.setString(1, debitCardType);
//			preparedStatement2.setBigDecimal(2, new BigDecimal(debitCardNum));
//			preparedStatement2.executeUpdate();
//		} catch (SQLException e) {
//		}
//	return result;
//	}
}
