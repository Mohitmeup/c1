package com.cg.ibs.cardmanagement.dao;

public class SqlQueries {

public static final String VERIFY_ACCOUNT_NUMBER = "SELECT account_number FROM accounts WHERE account_number =?";
public static final String SET_QUERY= "INSERT INTO query_log (query_ID,case_timestamp,status_of_query,account_num,UCI,define_query,card_num,customer_reference_ID) VALUES(?,?,?,?,?,?,?,?)";
public static final String GET_UCI_FROM_ACCOUNT_NUMBER = "SELECT uci FROM accounts WHERE account_number=?";
public static final String VERIFY_QUERY_ID = "SELECT query_id FROM query_log WHERE query_id=?";
public static final String GET_NEW_DEBIT_CARD_TYPE = "SELECT define_query FROM query_log WHERE query_id=?";
public static final String GET_ACCOUNT_NUMBER_UCI_FROM_QUERY_ID = "SELECT account_number,uci FROM query_log WHERE queryId=?";
public static final String GET_DETAILS_NEW_DEBIT_CARD = "SELECT first_name,last_name FROM customers WHERE uci=?";
public static final String ACTION_NEW_DEBIT_CARD = "INSERT INTO debit_card(account_number,debit_card_num, debit_card_status, name_on_deb_card, debit_cvv_num,debit_current_pin, debit_expiry_date, uci, debit_card_type)VALUES(?,?,?,?,?,?,?,?,?)";
public static final String ADMIN_STATUS_UPDATE = "UPDATE query_log SET define_query =? WHERE query_id=?";
public static final String GET_ACCOUNT_OF_DEBIT_CARD = "SELECT account_number FROM debit_card WHERE debit_card_num=?";
public static final String GET_DEBIT_CARD_PIN = "SELECT debit_current_pin FROM debit_card WHERE debit_card_num=?";
public static final String RESET_DEBIT_PIN = "UPDATE debit_card SET debit_current_pin WHERE debit_card_num=?";
public static final String GET_DETAILS_CARD = "SELECT card_num,define_query FROM query_log WHERE query_id =?";
public static final String ACTION_DEBIT_CARD_BLOCK = "UPDATE Debit_Card SET debit_card_status=?  WHERE debit_card_num =?";
public static final String ACTION_DEBIT_CARD_UPGRADE = "UPDATE Debit_Card SET debit_card_type=?  WHERE debit_card_num =?";
public static final String SELECT_ALL_DEBIT_CARDS = "SELECT debit_card_number, debit_card_status, name_on_deb_card, debit_expiry_date, debit_card_type FROM debit_card WHERE account_number=?";
}